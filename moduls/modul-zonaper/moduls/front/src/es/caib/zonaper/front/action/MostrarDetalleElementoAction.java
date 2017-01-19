package es.caib.zonaper.front.action;

import java.io.ByteArrayInputStream;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.redose.modelInterfaz.ConstantesRDS;
import es.caib.redose.modelInterfaz.DocumentoRDS;
import es.caib.redose.modelInterfaz.ReferenciaRDS;
import es.caib.redose.persistence.delegate.DelegateRDSUtil;
import es.caib.redose.persistence.delegate.RdsDelegate;
import es.caib.sistra.plugins.login.ConstantesLogin;
import es.caib.xml.ConstantesXML;
import es.caib.xml.documentoExternoNotificacion.factoria.FactoriaObjetosXMLDocumentoExternoNotificacion;
import es.caib.xml.documentoExternoNotificacion.factoria.ServicioDocumentoExternoNotificacionXML;
import es.caib.xml.documentoExternoNotificacion.factoria.impl.DocumentoExternoNotificacion;
import es.caib.zonaper.front.Constants;
import es.caib.zonaper.front.form.DetalleElementoForm;
import es.caib.zonaper.model.DatosSesion;
import es.caib.zonaper.model.DocumentoEntradaPreregistro;
import es.caib.zonaper.model.DocumentoEntradaTelematica;
import es.caib.zonaper.model.DocumentoEventoExpediente;
import es.caib.zonaper.model.DocumentoNotificacionTelematica;
import es.caib.zonaper.model.ElementoExpediente;
import es.caib.zonaper.model.EntradaPreregistro;
import es.caib.zonaper.model.EntradaTelematica;
import es.caib.zonaper.model.EventoExpediente;
import es.caib.zonaper.model.NotificacionTelematica;
import es.caib.zonaper.modelInterfaz.ConstantesZPE;
import es.caib.zonaper.persistence.delegate.DelegateUtil;

/**
 * @struts.action
 *  path="/protected/mostrarDetalleElemento"
 *  name="detalleElementoForm"
 *  scope="session"
 *  
 * @struts.action-forward
 *  name="tramite" path=".detalleTramite"
 *
 * @struts.action-forward
 *  name="tramiteAnonimo" path=".detalleTramiteAnonimo"
 *  
 * @struts.action-forward
 *  name="notificacionPendiente" path=".detalleNotificacionPendiente"
 *  
 * @struts.action-forward
 *  name="notificacionPendienteAnonimo" path=".detalleNotificacionPendienteAnonimo"
 *  
 * @struts.action-forward
 *  name="notificacionRecibida" path=".detalleNotificacionRecibida"
 *  
  * @struts.action-forward
 *  name="notificacionRecibidaAnonimo" path=".detalleNotificacionRecibidaAnonimo"
 *  
 * @struts.action-forward
 *  name="aviso" path=".detalleAviso"  
 *  
 * @struts.action-forward
 *  name="avisoAnonimo" path=".detalleAvisoAnonimo" 
 *  
 * @struts.action-forward
 *  name="fail" path=".mensaje"
 */
public class MostrarDetalleElementoAction extends BaseAction {

	private static Log log = LogFactory.getLog( MostrarDocumentoAction.class );
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		try{
			// Recuperamos elemento (codigo + tipo) e info acerca de si esta asociado a un expediente o es un tramite sin expe
			DetalleElementoForm def = (DetalleElementoForm) form;
			Long codigo 	= def.getCodigo();
			String tipo 	= def.getTipo();			
		
			log.debug("mostrarDetalleElemento: codigo=" + codigo.longValue() + " tipo=" +  tipo );
			
			ElementoExpediente elementoExpediente;
			if (this.getDatosSesion(request).getNivelAutenticacion() == 'A'){
				elementoExpediente = DelegateUtil.getElementoExpedienteDelegate().obtenerElementoExpedienteAnonimo(tipo,codigo,this.getIdPersistencia(request));
			}else{
				elementoExpediente = DelegateUtil.getElementoExpedienteDelegate().obtenerElementoExpedienteAutenticado(tipo,codigo);
			}
			request.setAttribute("elementoExpediente",elementoExpediente);
			
			// Establecemos id en sesion del expediente para que se muestre en el detalle expediente el expediente asociado
			request.getSession().setAttribute(Constants.ULTIMO_DETALLE_EXPEDIENTE, elementoExpediente.getExpediente().getCodigo());
				
			String anonimo = (this.getDatosSesion(request).getNivelAutenticacion()=='A'?"Anonimo":"");
			
			// En funcion del elemento redirigimos a la vista correspondiente
			// (los detalles de cada vista se resolveran en el controller correspondiente)
			if (ElementoExpediente.TIPO_AVISO_EXPEDIENTE.equals(tipo)){
				EventoExpediente aviso;
				if (this.getDatosSesion(request).getNivelAutenticacion() == 'A'){
					aviso = DelegateUtil.getEventoExpedienteDelegate().obtenerEventoExpedienteAnonimo(codigo,this.getIdPersistencia(request));
				}else{
					aviso = DelegateUtil.getEventoExpedienteDelegate().obtenerEventoExpedienteAutenticado(codigo);
				}
				request.setAttribute("aviso",aviso);
				cargarFirmas(aviso.getDocumentos(),request,tipo);
				return mapping.findForward("aviso"+anonimo);
			}else if (ElementoExpediente.TIPO_NOTIFICACION.equals(tipo)){
				NotificacionTelematica not;			
				if (this.getDatosSesion(request).getNivelAutenticacion() == 'A'){
					not = DelegateUtil.getNotificacionTelematicaDelegate().obtenerNotificacionTelematicaAnonima(codigo,this.getIdPersistencia(request));
				}else{
					not = DelegateUtil.getNotificacionTelematicaDelegate().obtenerNotificacionTelematicaAutenticada(codigo);
				}
				request.setAttribute("notificacion",not);
				if (def.getTipoFirma() != null) {
					request.setAttribute("tipoFirma",def.getTipoFirma());
				}
				if (not.getFechaAcuse() != null && !not.isRechazada()){
					cargarFirmas(not.getDocumentos(),request,tipo);
					return mapping.findForward("notificacionRecibida"+anonimo);
				}else{
					// Indicamos firmante: si usuario esta autenticado
					String nifFirmante = "";
					DatosSesion datosSesion = this.getDatosSesion(request);
					if (datosSesion.getNivelAutenticacion() != ConstantesLogin.LOGIN_ANONIMO) {
						nifFirmante = datosSesion.getNifUsuario();
					} else {
						nifFirmante = not.getNifRepresentante();
					}
					request.setAttribute("nifFirmante", nifFirmante); 	
					// Redirigimos a not pendiente
					return mapping.findForward("notificacionPendiente"+anonimo);
				}
			}else if (ElementoExpediente.TIPO_ENTRADA_TELEMATICA.equals(tipo)){
				EntradaTelematica entrada;
				if (this.getDatosSesion(request).getNivelAutenticacion() == 'A'){
					entrada = DelegateUtil.getEntradaTelematicaDelegate().obtenerEntradaTelematicaAnonima(codigo,this.getIdPersistencia(request));		
				}else{
					entrada = DelegateUtil.getEntradaTelematicaDelegate().obtenerEntradaTelematicaAutenticada(codigo);		
				}
				request.setAttribute("tramite",entrada);
				cargarFirmas(entrada.getDocumentos(),request,tipo);
				return mapping.findForward("tramite"+anonimo);
			}else if (ElementoExpediente.TIPO_ENTRADA_PREREGISTRO.equals(tipo)){
				EntradaPreregistro entrada; 
				if (this.getDatosSesion(request).getNivelAutenticacion() == 'A'){
					entrada = DelegateUtil.getEntradaPreregistroDelegate().obtenerEntradaPreregistroAnonima(codigo,this.getIdPersistencia(request));		
				}else{
					entrada = DelegateUtil.getEntradaPreregistroDelegate().obtenerEntradaPreregistroAutenticada(codigo);		
				}
				request.setAttribute("tramite",entrada);
				cargarFirmas(entrada.getDocumentos(),request,tipo);
				return mapping.findForward("tramite"+anonimo);
			}else{
				throw new Exception ("Tipo no soportado " + tipo);
			}
		}catch (Exception ex){
			log.error("Error mostrando detalle elemento expediente",ex );
			request.setAttribute(Constants.MENSAJE_TEXTO, "mensaje.texto.errorGenerico" );
			return mapping.findForward( "fail" );
		}
	}

	private void cargarFirmas(Set documentos, HttpServletRequest request, String tipo) throws Exception{
		RdsDelegate rdsDeleg = DelegateRDSUtil.getRdsDelegate();
		
//		vamos a buscar las firmas de los documentos si existen y las meteremos en la request
		if(documentos != null){
			Iterator it = documentos.iterator();
			ReferenciaRDS ref = null;
			Long codigo = null;
			while(it.hasNext()){
				if (ElementoExpediente.TIPO_AVISO_EXPEDIENTE.equals(tipo)){
					DocumentoEventoExpediente docTipo = (DocumentoEventoExpediente)it.next();
					ref = new ReferenciaRDS(docTipo.getRdsCodigo(),docTipo.getRdsClave());
					codigo = docTipo.getCodigo();
				}else if (ElementoExpediente.TIPO_NOTIFICACION.equals(tipo)){
					DocumentoNotificacionTelematica docTipo = (DocumentoNotificacionTelematica)it.next();
					ref = new ReferenciaRDS(docTipo.getCodigoRDS(),docTipo.getClaveRDS());
					codigo = docTipo.getCodigo();
				}else if (ElementoExpediente.TIPO_ENTRADA_TELEMATICA.equals(tipo)){
					DocumentoEntradaTelematica docTipo = (DocumentoEntradaTelematica)it.next();
					ref = new ReferenciaRDS(docTipo.getCodigoRDS(),docTipo.getClaveRDS());
					codigo = docTipo.getCodigo();
				}else {
					DocumentoEntradaPreregistro docTipo = (DocumentoEntradaPreregistro)it.next();
					ref = new ReferenciaRDS(docTipo.getCodigoRDS(),docTipo.getClaveRDS());
					codigo = docTipo.getCodigo();
				}
				
				// En caso de que sea presencial no tendra referencia RDS
				if (ref.getCodigo() > 0){				
					DocumentoRDS doc = rdsDeleg.consultarDocumento(ref,false);
					
					// Establecemos en la request las firmas del documento
					if(doc != null && doc.getFirmas() != null){
						request.setAttribute(codigo.toString(),doc.getFirmas());
					}
					
					// Establecemos en la request si es una referencia a un doc externo (modelo GE0013NOTIFEXT)
					if (doc.getModelo().equals(ConstantesRDS.MODELO_NOTIFICACION_EXTERNO)) {
						// Buscamos url
						doc = rdsDeleg.consultarDocumento(ref, true);
						FactoriaObjetosXMLDocumentoExternoNotificacion factoria = ServicioDocumentoExternoNotificacionXML.crearFactoriaObjetosXML();
						factoria.setEncoding(ConstantesXML.ENCODING);
						factoria.setIndentacion(true);
						DocumentoExternoNotificacion documentoExternoNotificacion = factoria.crearDocumentoExternoNotificacion(new ByteArrayInputStream(doc.getDatosFichero()));
						request.setAttribute("URL-"+codigo,documentoExternoNotificacion.getUrl());
					}
				}
			}
		}
	}

	
}
