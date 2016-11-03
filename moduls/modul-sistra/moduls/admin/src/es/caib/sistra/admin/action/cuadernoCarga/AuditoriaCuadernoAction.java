package es.caib.sistra.admin.action.cuadernoCarga;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import es.caib.sistra.admin.Constants;
import es.caib.sistra.admin.action.BaseAction;
import es.caib.sistra.model.Dominio;
import es.caib.sistra.model.admin.CuadernoCarga;
import es.caib.sistra.model.admin.DatosAuditoriaCuaderno;
import es.caib.sistra.model.admin.ElementoAuditoriaDominio;
import es.caib.sistra.model.admin.ElementoAuditoriaScript;
import es.caib.sistra.persistence.delegate.AuditoriaCuadernoDelegate;
import es.caib.sistra.persistence.delegate.CuadernoCargaDelegate;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.util.StringUtil;
import es.indra.util.pdf.PDFDocument;
import es.indra.util.pdf.Parrafo;
import es.indra.util.pdf.Propiedad;
import es.indra.util.pdf.Seccion;
import es.indra.util.pdf.Tabla;

/**
 * Action para preparar el alta de un Cuaderno de Carga.
 *
 * @struts.action
 *  path="/admin/cuadernoCarga/auditar"
 *  validate="false"
 *
 * @struts.action-forward
 *  name="success" path="/admin/download.do"
 *
 * @struts.action-forward
 *  name="fail" path="/admin/cuadernoCarga/seleccion"
 *
 */
public class AuditoriaCuadernoAction extends BaseAction
{
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		Long codigoCuadernoCarga = new Long ( request.getParameter( "codigo" ) );
		AuditoriaCuadernoDelegate delegate 		= DelegateUtil.getAuditoriaCuadernoDelegate();
		CuadernoCargaDelegate cuadernoDelegate 	= DelegateUtil.getCuadernoCargaDelegate();
		DatosAuditoriaCuaderno datosAuditoriaCuaderno = delegate.auditoriaCuaderno( codigoCuadernoCarga );
		
		byte[] pdfResultadoAuditoria = pdfAuditoria( cuadernoDelegate.obtenerCuadernoCarga( codigoCuadernoCarga ), datosAuditoriaCuaderno, getResources( request ), this.getLocale( request ));  
		
		if ( pdfResultadoAuditoria == null )
		{
			return mapping.findForward( "fail" );
		}
		
		String nombreFichero = getResources( request ).getMessage( this.getLocale( request ), "nombreFicheroAuditoria" );
		nombreFichero+=codigoCuadernoCarga + ".pdf";
		request.setAttribute( Constants.NOMBREFICHERO_KEY, nombreFichero );
		request.setAttribute( Constants.DATOSFICHERO_KEY, pdfResultadoAuditoria);
		return mapping.findForward( "success" );
    }
	
	private byte[] pdfAuditoria( CuadernoCarga cuadernoCarga, DatosAuditoriaCuaderno datosAuditoriaCuaderno, MessageResources messageResources, Locale locale ) throws Exception 
	{
		String cabecera = messageResources.getMessage( locale, "pdf.cabecera" );
		Set listaDominios = datosAuditoriaCuaderno.getListaDominios();
		List listaScripts  = datosAuditoriaCuaderno.getListaScriptsAuditar();
		
		PDFDocument docPDF;				
		docPDF = new PDFDocument(cabecera);
		float []widths = {30f,70f};
		
		Seccion seccion = new Seccion("1",messageResources.getMessage( locale, "pdf.detalleCuadernoCarga" ));  
    	Propiedad propiedad = new Propiedad(messageResources.getMessage( locale, "pdf.nombreCuaderno" ), cuadernoCarga.getDescripcion(),widths);
    	seccion.addCampo(propiedad);
    	propiedad = new Propiedad(messageResources.getMessage( locale, "pdf.fechaRecepcionCuaderno" ), StringUtil.obtenerCadenaPorDefecto( StringUtil.fechaACadena( cuadernoCarga.getFechaEnvio() ), "" ),widths);
    	seccion.addCampo(propiedad);
    	propiedad = new Propiedad(messageResources.getMessage( locale, "pdf.fechaPrevistaCarga" ),StringUtil.obtenerCadenaPorDefecto( StringUtil.fechaACadena( cuadernoCarga.getFechaCarga() ), "" ), widths);
    	seccion.addCampo(propiedad);
    	propiedad = new Propiedad(messageResources.getMessage( locale, "pdf.fechaGeneracionInforme" ), StringUtil.fechaACadena( new Date() ),widths);
    	seccion.addCampo(propiedad);    	
		docPDF.addSeccion(seccion);
		
    	// Dominios
		seccion = new Seccion("2",messageResources.getMessage( locale, "pdf.dominiosUtilizados" ));    	
    	seccion.setKeepTogether(false);
    	seccion.setSplitLate(false);
		for ( Iterator it = listaDominios.iterator(); it.hasNext(); )
    	{
    		ElementoAuditoriaDominio elementoDominio = ( ElementoAuditoriaDominio ) it.next();
    		addTablaDominio( seccion, elementoDominio, messageResources, locale );
    	}
    	docPDF.addSeccion( seccion );
    	
    	// Scripts
    	seccion = new Seccion( "3", messageResources.getMessage( locale, "pdf.scriptsAAuditar" ) );
    	seccion.setKeepTogether(false);
    	seccion.setSplitLate(false);
    	for ( Iterator it = listaScripts.iterator(); it.hasNext(); )
    	{
    		ElementoAuditoriaScript elementoScript = ( ElementoAuditoriaScript ) it.next();
    		addTablaScript( seccion, elementoScript, messageResources, locale );
    	}
    	docPDF.addSeccion( seccion );
    	
    	ByteArrayOutputStream bos = new ByteArrayOutputStream();
    	docPDF.generate(bos);
    	return bos.toByteArray();
	}
	
	public void addTablaDominio(Seccion seccion, ElementoAuditoriaDominio elementoDominio, MessageResources messageResources, Locale locale )
	{
		Dominio dominio = elementoDominio.getDominio();
		
		Tabla tabla;
    	Vector columnas;
    	Vector campos;
    	Vector cp;
    	int [] ancho = {10,90};
    	
    	columnas = new Vector();
    	columnas.add("");
    	columnas.add( elementoDominio.getNombre() + ( dominio != null ?  " " + dominio.getDescripcion() : "" ) );
    	campos = new Vector();
    	cp = new Vector();
		cp.add( messageResources.getMessage( locale, "pdf.estado" ) );
		cp.add( getEstadoDominioMsg ( locale, messageResources, elementoDominio.getEstado() ) );
		campos.add(cp);
    	
    	if ( dominio != null )
		{
    		// Tipo
    		cp = new Vector();
			cp.add(messageResources.getMessage( locale, "pdf.dominio.tipo" ));
			cp.add(getDependentMessage( locale, messageResources, "pdf.dominio.tipo", "" + dominio.getTipo() ));
			campos.add(cp);
			
			// Cacheable
    		cp = new Vector();
			cp.add(messageResources.getMessage( locale, "pdf.dominio.cacheable" ));
			cp.add(getDependentMessage( locale, messageResources, "pdf.dominio.cacheable", "" + dominio.getCacheable() ));
			campos.add(cp);
			
			// URL
			cp = new Vector();
			cp.add(messageResources.getMessage( locale, "pdf.dominio.url" ));
			cp.add(dominio.getUrl());
			campos.add(cp);
			
			// Propiedades de un dominio tipo SQL
			if ( Dominio.DOMINIO_SQL == dominio.getTipo() || Dominio.DOMINIO_FUENTE_DATOS == dominio.getTipo())
			{
				cp = new Vector();
				cp.add(messageResources.getMessage( locale, "pdf.dominio.sql" ));
				cp.add(dominio.getSql());
				campos.add(cp);
			}
			else
			{
				//Propiedades de un dominio EJB
				if ( Dominio.DOMINIO_EJB == dominio.getTipo() )
				{
					// Nombre JNDI
					cp = new Vector();
					cp.add(messageResources.getMessage( locale, "pdf.dominio.JNDIName" ));
					cp.add(dominio.getJNDIName());
					campos.add(cp);
					
					// Localización
					cp = new Vector();
					cp.add(messageResources.getMessage( locale, "pdf.dominio.localizacionEJB" ));
					cp.add(getDependentMessage( locale, messageResources, "pdf.dominio.EJB.localizacion", "" + dominio.getLocalizacionEJB() ));
					campos.add(cp);
				}
				
				// Tipo autenticacion
				cp = new Vector();
				cp.add(messageResources.getMessage( locale, "pdf.dominio.tipoAutenticacion" ));
				cp.add( getDependentMessage( locale, messageResources, "pdf.dominio.tipoAutenticacion", "" + dominio.getAutenticacionExplicita() ));
				campos.add(cp);
				
				// Usuario auth
				cp = new Vector();
				cp.add( messageResources.getMessage( locale, "pdf.dominio.usr" ) );
				cp.add( dominio.getUsr() );
				campos.add(cp);
				
				// Password auth
				cp = new Vector();
				cp.add( messageResources.getMessage( locale, "pdf.dominio.pwd" ) );
				cp.add( dominio.getPwd() );
				campos.add(cp);
			}
		}
    	
    	tabla =  new Tabla(columnas,campos,ancho);
    	tabla.setMostrarCabeceras(true);
    	seccion.addCampo(tabla);
    	
    	Parrafo p = new Parrafo(" ");    	
    	seccion.addCampo(p);
    	p = new Parrafo(" ");    	
    	seccion.addCampo(p);    	    
	}
	
	public void addTablaScript(Seccion seccion, ElementoAuditoriaScript elementoScript, MessageResources messageResources, Locale locale )
	{	
		Tabla tabla;
    	Vector columnas;
    	Vector campos;
    	Vector cp;
    	int [] ancho = {10,90};
    	
    	columnas = new Vector();
    	columnas.add("");
    	
    	// Descomentar si se quiere una referencia para el campo en el pdf de tal forma que se pueda localizar rápidamente dicho campo
    	//columnas.add( messageResources.getMessage( locale, "pdf." + elementoScript.getDescripcionKey()) + " " + elementoScript.getNombre());
    	columnas.add( messageResources.getMessage( locale, "pdf." + elementoScript.getDescripcionKey()) );
    	campos = new Vector();
    	
    	cp = new Vector();
		cp.add(messageResources.getMessage( locale, "pdf.estado" ));
		cp.add(getEstadoScriptMsg( locale, messageResources, elementoScript.getEstado() ));
		campos.add(cp);
		
		cp = new Vector();
		cp.add("Script");
		cp.add( elementoScript.getContenidoScript() );
		campos.add(cp);
		
    	tabla =  new Tabla(columnas,campos,ancho);
    	tabla.setMostrarCabeceras(true);
    	seccion.addCampo(tabla);
    	
    	Parrafo p = new Parrafo(" ");    	
    	seccion.addCampo(p);
    	p = new Parrafo(" ");    	
    	seccion.addCampo(p);    	    
	}
	
	private String getEstadoDominioMsg( Locale locale, MessageResources messageResources, String estado )
	{
		return getDependentMessage( locale, messageResources, "pdf.estadoDominio", estado );
	}
	
	private String getEstadoScriptMsg( Locale locale, MessageResources messageResources, String estado )
	{
		return getDependentMessage( locale, messageResources, "pdf.estadoScript",  estado );
	}
	
	private String getDependentMessage( Locale locale, MessageResources messageResources, String whichProperty, String whichValue )
	{
		return messageResources.getMessage( locale,  whichProperty + "." + whichValue );
	}
}
