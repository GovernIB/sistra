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

import es.caib.bantel.front.form.DetalleTramiteForm;
import es.caib.bantel.model.DocumentoBandeja;
import es.caib.bantel.model.GestorBandeja;
import es.caib.bantel.model.Tramite;
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
 *  name="detalleTramiteForm"
 *  path="/detalleTramite"
 *  scope="request"
 *  validate="false"
 *  
 * @struts.action-forward
 *  name="success" path=".detalle"
 *
 * @struts.action-forward
 *  name="fail" path=".error"
 */
public class DetalleTramiteAction extends BaseAction
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		RdsDelegate rdsDelegate = DelegateRDSUtil.getRdsDelegate();
		Set documentosEstructurados = new HashSet();
		
		DetalleTramiteForm detalleTramiteFormulario = ( DetalleTramiteForm ) form;
		TramiteBandejaDelegate tramiteDelegate = DelegateUtil.getTramiteBandejaDelegate();
		TramiteBandeja tramite = tramiteDelegate.obtenerTramiteBandeja( detalleTramiteFormulario.getCodigo() );
		Set documentosTramite = tramite.getDocumentos();
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
				}
			}
		}		
		request.setAttribute("documentosEstructurados",documentosEstructurados);
		
		// Buscamos si gestor tiene permiso de cambio de estado
		GestorBandejaDelegate gestorBandejaDelegate = DelegateUtil.getGestorBandejaDelegate();
		GestorBandeja gestor = gestorBandejaDelegate.obtenerGestorBandeja(this.getPrincipal(request).getName());
		
		
		//	------------------------------------------------------------------------------------------------------------------
		// TODO RAFA HABRIA QUE IMPLEMENTARLO EN CAPA DE NEGOCIO
		// Verificamos que el gestor tenga acceso al tramite
		boolean acceso = false;		
		for (Iterator it=gestor.getTramitesGestionados().iterator();it.hasNext();){
				Tramite tram = (Tramite) it.next();
				if (tram.getIdentificador().equals(tramite.getTramite().getIdentificador())){
					acceso = true;
					break;
				}
		}
		if (!acceso){
			MessageResources resources = ((MessageResources) request.getAttribute(Globals.MESSAGES_KEY));
			request.setAttribute("message",resources.getMessage( getLocale( request ), "errors.tramiteNoAcceso", new Object[] {tramite.getTramite().getIdentificador()}));			
			return mapping.findForward( "fail" );
		}
		// ------------------------------------------------------------------------------------------------------------------
		
		
		
		request.setAttribute( "tramite", tramite );
		request.setAttribute("permitirCambioEstado",Character.toString(gestor.getPermitirCambioEstado()));
		
		return mapping.findForward( "success" );
    }
}
