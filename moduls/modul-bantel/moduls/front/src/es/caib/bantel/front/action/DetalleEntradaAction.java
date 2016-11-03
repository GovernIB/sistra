package es.caib.bantel.front.action;

import java.io.ByteArrayInputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import es.caib.bantel.front.form.DetalleEntradaForm;
import es.caib.bantel.front.form.DetalleTramiteForm;
import es.caib.bantel.front.util.DocumentosUtil;
import es.caib.bantel.model.DocumentoBandeja;
import es.caib.bantel.model.GestorBandeja;
import es.caib.bantel.model.Procedimiento;
import es.caib.bantel.model.TramiteBandeja;
import es.caib.bantel.persistence.delegate.DelegateUtil;
import es.caib.bantel.persistence.delegate.GestorBandejaDelegate;
import es.caib.bantel.persistence.delegate.TramiteBandejaDelegate;
import es.caib.redose.modelInterfaz.DocumentoRDS;
import es.caib.redose.modelInterfaz.ReferenciaRDS;
import es.caib.redose.persistence.delegate.DelegateRDSUtil;
import es.caib.redose.persistence.delegate.RdsDelegate;
import es.caib.xml.datospropios.factoria.FactoriaObjetosXMLDatosPropios;
import es.caib.xml.datospropios.factoria.ServicioDatosPropiosXML;
import es.caib.xml.datospropios.factoria.impl.DatosPropios;
import es.caib.xml.registro.factoria.ConstantesAsientoXML;

/**
 * @struts.action
 *  name="detalleEntradaForm"
 *  path="/detalleEntrada"
 *  scope="request"
 *  validate="false"
 *  
 * @struts.action-forward
 *  name="success" path=".detalle"
 *
 * @struts.action-forward
 *  name="fail" path=".error"
 */
public class DetalleEntradaAction extends BaseAction
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		DetalleEntradaForm detalleTramiteFormulario = ( DetalleEntradaForm ) form;
		Set documentosEstructurados = new HashSet();
		
		// Indicamos que es pantalla de enlace (-> quita tabs y el enlace de volver atras hará un history.back)
		request.setAttribute("enlace","true");
		
		RdsDelegate rdsDelegate = DelegateRDSUtil.getRdsDelegate();
		TramiteBandejaDelegate tramiteDelegate = DelegateUtil.getTramiteBandejaDelegate();
		TramiteBandeja tramite = tramiteDelegate.obtenerTramiteBandeja( detalleTramiteFormulario.getNumeroEntrada() );
		if (tramite == null){			
			MessageResources resources = ((MessageResources) request.getAttribute(Globals.MESSAGES_KEY));
			request.setAttribute("message",resources.getMessage( getLocale( request ), "errors.tramiteNoExiste"));			
			return mapping.findForward( "fail" );		
		}
		
		
		// Cargamos firma asiento
		DocumentoRDS documentoRDSAsiento = rdsDelegate.consultarDocumento( new ReferenciaRDS(tramite.getCodigoRdsAsiento(), tramite.getClaveRdsAsiento()), false );
		DocumentosUtil.cargarFirmasDocumentoRDS(documentoRDSAsiento, request);
		
		
		Set documentosTramite = tramite.getDocumentos();
		
		// Recorremos documentos:
		//	- Identificamos documento de datos propios
		//	- Identificamos que documentos son estrucutrados para dar opción de poder descargar el xml
		//  - Cargamos las firmas para poder mostrarlas
		for ( Iterator it = documentosTramite.iterator(); it.hasNext(); )
		{
			DocumentoBandeja documento = ( DocumentoBandeja ) it.next();
			
			if ( documento.getIdentificador().startsWith( ConstantesAsientoXML.IDENTIFICADOR_DATOS_PROPIOS ) )
			{
				// Acceder al documento rds con su referencia y parsear el xml para construir la informacion
				// de datos propios:
					// --instrucciones -> Instrucciones presencial
					// --solicitud     -> Datos propios de la solicitud
				ReferenciaRDS referenciaRDS = new ReferenciaRDS();
				referenciaRDS.setCodigo( documento.getRdsCodigo().longValue() );
				referenciaRDS.setClave( documento.getRdsClave() );
								
				DocumentoRDS documentoRDS = rdsDelegate.consultarDocumento( referenciaRDS );
				byte[] byteArraySolicitud = documentoRDS.getDatosFichero();
				
				FactoriaObjetosXMLDatosPropios factoria = ServicioDatosPropiosXML.crearFactoriaObjetosXML();
				DatosPropios datosPropios = factoria.crearDatosPropios (
							new ByteArrayInputStream(byteArraySolicitud)
						);
				
				request.setAttribute( "datosPropios", datosPropios );
				
			}else{
				// Detectamos que docs telematicos son de tipo estructurado (xml) para dar opcion a descargar el xml
				if (documento.getRdsCodigo() != null) {
					DocumentoRDS documentoRDS = rdsDelegate.consultarDocumento(new ReferenciaRDS(documento.getRdsCodigo().longValue(),documento.getRdsClave()),false);
					if (documentoRDS.isEstructurado()){
						documentosEstructurados.add(documento.getCodigo());
					}
					// Cargamos firmas
					DocumentosUtil.cargarFirmasDocumentoRDS(documentoRDS, request);
				}
				
			}
			
		}
		
		request.setAttribute("documentosEstructurados",documentosEstructurados);
		
		GestorBandejaDelegate gestorBandejaDelegate = DelegateUtil.getGestorBandejaDelegate();
		GestorBandeja gestor = gestorBandejaDelegate.obtenerGestorBandeja(this.getPrincipal(request).getName());
		
		
		// ------------------------------------------------------------------------------------------------------------------
		// TODO RAFA HABRIA QUE IMPLEMENTARLO EN CAPA DE NEGOCIO
		// Verificamos que el gestor tenga acceso al tramite
		boolean acceso = false;	
		if (gestor.getProcedimientosGestionados() != null) {
			for (Iterator it=gestor.getProcedimientosGestionados().iterator();it.hasNext();){
					Procedimiento tram = (Procedimiento) it.next();
					if (tram.getIdentificador().equals(tramite.getProcedimiento().getIdentificador())){
						acceso = true;
						break;
					}
			}
		}
		if (!acceso){
			MessageResources resources = ((MessageResources) request.getAttribute(Globals.MESSAGES_KEY));
			request.setAttribute("message",resources.getMessage( getLocale( request ), "errors.tramiteNoAcceso", new Object[] {tramite.getProcedimiento().getIdentificador()}));			
			return mapping.findForward( "fail" );
		}
		// ------------------------------------------------------------------------------------------------------------------
		
		
		request.setAttribute( "tramite", tramite );
		
		// No permitimos cambio de estado
		//request.setAttribute("permitirCambioEstado",Character.toString(gestor.getPermitirCambioEstado()));
		request.setAttribute("permitirCambioEstado","N");
		
		// Creamos objeto detalleTramFormulario para compatibilizar con otra accion 
		DetalleTramiteForm detalleTramFormulario = new DetalleTramiteForm();
		detalleTramFormulario.setCodigo(detalleTramFormulario.getCodigo());
		request.setAttribute("detalleTramiteForm",detalleTramFormulario);
		
		return mapping.findForward( "success" );
    }
}
