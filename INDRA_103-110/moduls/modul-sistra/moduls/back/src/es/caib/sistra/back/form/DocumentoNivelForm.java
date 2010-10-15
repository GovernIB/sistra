package es.caib.sistra.back.form;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import es.caib.sistra.back.taglib.Constants;
import es.caib.sistra.back.util.Util;
import es.caib.sistra.model.Documento;
import es.caib.sistra.model.DocumentoNivel;
import es.caib.sistra.model.TraDocumentoNivel;
import es.caib.sistra.model.TramiteVersion;
import es.caib.sistra.persistence.delegate.DelegateException;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.sistra.persistence.delegate.DocumentoDelegate;
import es.caib.sistra.persistence.delegate.DocumentoNivelDelegate;

public class DocumentoNivelForm extends TraduccionValidatorForm
{
	private Long idDocumento;
	private String obligatorioScript;
	private String formularioDatosInicialesScript;
	private String formularioValidacionPostFormScript;
	private String formularioModificacionPostFormScript;
	private String formularioConfiguracionScript;
	private String formularioPlantillaScript;	
	private String pagoCalcularPagoScript;
	private String flujoTramitacionScript;
		
	private String [] nivelesAutenticacionSelected = null;

	public Long getIdDocumento()
	{
		return idDocumento;
	}

	public void setIdDocumento(Long idDocumento)
	{
		this.idDocumento = idDocumento;
	}
	
	public String getFormularioDatosInicialesScript()
	{
		return formularioDatosInicialesScript;
	}

	public void setFormularioDatosInicialesScript(
			String formularioDatosInicialesScript)
	{
		this.formularioDatosInicialesScript = formularioDatosInicialesScript;
	}

	public String getFormularioValidacionPostFormScript()
	{
		return formularioValidacionPostFormScript;
	}

	public void setFormularioValidacionPostFormScript(
			String formularioValidacionPostFormScript)
	{
		this.formularioValidacionPostFormScript = formularioValidacionPostFormScript;
	}

	public String getObligatorioScript()
	{
		return obligatorioScript;
	}

	public void setObligatorioScript(String obligatorioScript)
	{
		this.obligatorioScript = obligatorioScript;
	}

	public String getPagoCalcularPagoScript()
	{
		return pagoCalcularPagoScript;
	}

	public void setPagoCalcularPagoScript(String pagoCalcularPagoScript)
	{
		this.pagoCalcularPagoScript = pagoCalcularPagoScript;
	}
	
	/** Retorna true si se ha pulsado un botón submit de alta */
    protected boolean isAlta(HttpServletRequest request) {
      return (request.getParameter(Constants.ALTA_PROPERTY) != null);
    }
    
    public void destroy(ActionMapping mapping, HttpServletRequest request) 
	{
        super.destroy( mapping, request );
        this.setFormularioDatosInicialesScript( null );
        this.setFormularioValidacionPostFormScript( null );
        this.setFormularioModificacionPostFormScript( null );
        this.setPagoCalcularPagoScript( null );
        this.setObligatorioScript( null );
        this.setNivelesAutenticacionSelected( null );
        this.setFormularioConfiguracionScript( null );
        this.setFormularioPlantillaScript(null);
        this.setFlujoTramitacionScript(null);
    }

	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) 
    {
        ActionErrors errors = super.validate(mapping, request);
        if (errors == null) 
        {
            errors = new ActionErrors();
        }
        try 
        {
        	DocumentoNivel documentoNivel = (DocumentoNivel) getValues();
        	
        	
        	// Validamos script dependencia
        	if (documentoNivel.getObligatorio() == DocumentoNivel.DEPENDIENTE && Util.esCadenaVacia(obligatorioScript)){
        		errors.add("values.obligatorioScript", new ActionError("errors.obligatorioScript.nulo"));
        	}
        	        	
        	// Validamos script pago    
        	DocumentoDelegate docDelegate = DelegateUtil.getDocumentoDelegate();
        	Documento doc = docDelegate.obtenerDocumento(this.idDocumento);
        	if (doc.getTipo() == Documento.TIPO_PAGO && Util.esCadenaVacia(pagoCalcularPagoScript)){
        		errors.add("values.obligatorioScript", new ActionError("errors.pagoCalcularPagoScript.nulo"));
        	}
        	
        	// Para formularios validamos que se rellene el enlace a Forms            	
        	if (doc.getTipo() == Documento.TIPO_FORMULARIO && Util.esCadenaVacia(documentoNivel.getFormularioFormsModelo())){
        		errors.add("values.formularioFormsModelo", new ActionError("errors.modeloForms.nulo"));
        	}
        	if (doc.getTipo() == Documento.TIPO_FORMULARIO && documentoNivel.getFormularioFormsVersion() == null){
        		errors.add("values.formularioFormsVersion", new ActionError("errors.versionForms.nulo"));
        	}
        
        	// Validamos script flujo de tramitación, sólo disponible para nivel con autenticación
        	/*
        	if (!Util.esCadenaVacia(flujoTramitacionScript) && (documentoNivel.getNivelAutenticacion().indexOf("A") != -1)){
        		errors.add("values.flujoTramitacionScript", new ActionError("errors.flujoTramitacionScript.noanonimo"));
        	}
        	*/
        	        	
        	
        	//if ( this.isAlta( request )  )
        	//{
	        
	            DocumentoNivelDelegate delegate = DelegateUtil.getDocumentoNivelDelegate();
	            Set listaVersiones = delegate.listarDocumentoNiveles( getIdDocumento() );
	            
	            // Comprobamos que no esten duplicados los niveles de autenticacion
	            String niveles = "";
	            for (Iterator it = listaVersiones.iterator();it.hasNext();){
	            	DocumentoNivel dn = (DocumentoNivel) it.next();
	            	if ( documentoNivel.getCodigo() != null &&  dn.getCodigo().longValue() == documentoNivel.getCodigo().longValue()) continue;
	            	niveles = niveles + dn.getNivelAutenticacion();
	            }
	            niveles = niveles + Util.concatArrString( this.getNivelesAutenticacionSelected() ); 
	            
	            if  (
	        			niveles.indexOf(DocumentoNivel.AUTENTICACION_ANONIMO) != niveles.lastIndexOf(DocumentoNivel.AUTENTICACION_ANONIMO) ||
	        			niveles.indexOf(DocumentoNivel.AUTENTICACION_CERTIFICADO) != niveles.lastIndexOf(DocumentoNivel.AUTENTICACION_CERTIFICADO) ||
	        			niveles.indexOf(DocumentoNivel.AUTENTICACION_USUARIOPASSWORD) != niveles.lastIndexOf(DocumentoNivel.AUTENTICACION_USUARIOPASSWORD) 
	        		)
	        	{
	        		errors.add("values.nivelAutenticacion", new ActionError("errors.documentoNivel.duplicado", "" + Util.concatArrString( this.getNivelesAutenticacionSelected() ) ));
	        	}
        	//}
        }
    	catch (Exception e) 
    	{
            log.error(e);
        }
    	
    	
    	// Valida idiomas requeridos según la versión del trámite
    	if ( isAlta( request ) || isModificacion( request ) )
        {  
	        try 
	        {  
	        	DocumentoDelegate delegate = DelegateUtil.getDocumentoDelegate();	        	
	        	TramiteVersion tv = delegate.obtenerDocumento(this.getIdDocumento()).getTramiteVersion();
	        	String idiomas = tv.getIdiomasSoportados();
	        	StringTokenizer st = new StringTokenizer(idiomas,",");	        	
	        	
	        	int info = 0;
	        	int numLangs = 0;
	        	
	        	while (st.hasMoreTokens()){	        		
	        		String lang = ( String ) st.nextToken();
	        		numLangs ++;
	        		TraDocumentoNivel traduccion = ( TraDocumentoNivel ) this.getValues().getTraduccion( lang );
	        			        		
	        		// Para campos opcionales comprueba si estan metidos en un idioma y para otro no
	        		if ( traduccion != null && StringUtils.isNotEmpty( traduccion.getInformacion() ) )
	        		{
	        			info ++;
	        		}
	        	}
	        	
	        	if (info > 0 && info != numLangs){	        		
	        		errors.add( "traduccion.informacion", new ActionError("errors.revisarTraducciones","informacion") );
	        	}
	        	
	        	
	        }
	    	catch (DelegateException e) 
	    	{
	            log.error(e);
	        }
        }
    	
    	return errors;
    }
	
	public void validaTraduccion(ActionMapping mapping, HttpServletRequest request) 
	{
        super.validaTraduccion( mapping, request );
        DocumentoNivel docNivel = ( DocumentoNivel ) this.getValues();
        Map mTraducciones = docNivel.getTraducciones();
        HashSet removeSet = new HashSet(0);
        for ( Iterator it = mTraducciones.keySet().iterator(); it.hasNext(); )
        {
        	String lang = ( String ) it.next();
        	TraDocumentoNivel traduccion = ( TraDocumentoNivel ) mTraducciones.get( lang );
//	        	 Elimina la traducción si no tiene ningún campo no vacio, para que la actualización
            // posterior no de errores
            if ( Util.esCadenaVacia( traduccion.getInformacion() ) )
            {
            	//this.getValues().setTraduccion( lang, null );
            	removeSet.add( lang );
            }
        }
        for ( Iterator it = removeSet.iterator(); it.hasNext(); )
        {
        	String lang = ( String ) it.next();
        	this.getValues().setTraduccion( lang, null );
        }
        
    }

	public String getFormularioConfiguracionScript()
	{
		return formularioConfiguracionScript;
	}

	public void setFormularioConfiguracionScript(
			String formularioConfiguracionScript)
	{
		this.formularioConfiguracionScript = formularioConfiguracionScript;
	}

	public String[] getNivelesAutenticacionSelected()
	{
		return nivelesAutenticacionSelected;
	}

	public void setNivelesAutenticacionSelected(
			String[] nivelesAutenticacionSelected)
	{
		this.nivelesAutenticacionSelected = nivelesAutenticacionSelected;
	}

	public String getFormularioPlantillaScript() {
		return formularioPlantillaScript;
	}

	public void setFormularioPlantillaScript(String formularioPlantillaScript) {
		this.formularioPlantillaScript = formularioPlantillaScript;
	}

	public String getFlujoTramitacionScript() {
		return flujoTramitacionScript;
	}

	public void setFlujoTramitacionScript(String flujoTramitacionScript) {
		this.flujoTramitacionScript = flujoTramitacionScript;
	}

	public String getFormularioModificacionPostFormScript() {
		return formularioModificacionPostFormScript;
	}

	public void setFormularioModificacionPostFormScript(
			String formularioModificacionPostFormScript) {
		this.formularioModificacionPostFormScript = formularioModificacionPostFormScript;
	}
}
