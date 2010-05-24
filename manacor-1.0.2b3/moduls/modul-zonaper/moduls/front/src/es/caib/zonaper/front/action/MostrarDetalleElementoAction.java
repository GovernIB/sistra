package es.caib.zonaper.front.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.zonaper.front.Constants;
import es.caib.zonaper.front.form.DetalleElementoForm;
import es.caib.zonaper.model.ElementoExpediente;
import es.caib.zonaper.model.EntradaPreregistro;
import es.caib.zonaper.model.EntradaTelematica;
import es.caib.zonaper.model.EventoExpediente;
import es.caib.zonaper.model.NotificacionTelematica;
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
			boolean existeExpe = def.isExpediente();
		
			log.debug("mostrarDetalleElemento: codigo=" + codigo.longValue() + " tipo=" +  tipo + " expediente=" + existeExpe);
						
			
			if (existeExpe){
				ElementoExpediente elementoExpediente;
				if (this.getDatosSesion(request).getNivelAutenticacion() == 'A'){
					elementoExpediente = DelegateUtil.getElementoExpedienteDelegate().obtenerElementoExpedienteAnonimo(tipo,codigo,this.getIdPersistencia(request));
				}else{
					elementoExpediente = DelegateUtil.getElementoExpedienteDelegate().obtenerElementoExpedienteAutenticado(tipo,codigo);
				}
				request.setAttribute("elementoExpediente",elementoExpediente);
				
				// Establecemos id en sesion del expediente para que se muestre en el detalle expediente el expediente asociado
				request.getSession().setAttribute(Constants.ULTIMO_DETALLE_EXPEDIENTE,"E" + elementoExpediente.getExpediente().getCodigo().toString());
			}else{
				// Establecemos id en sesion del expediente para que se muestre en el detalle expediente el expediente asociado
				request.getSession().setAttribute(Constants.ULTIMO_DETALLE_EXPEDIENTE,tipo + codigo.toString());
			}
					
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
				return mapping.findForward("aviso"+anonimo);
			}else if (ElementoExpediente.TIPO_NOTIFICACION.equals(tipo)){
				NotificacionTelematica not;			
				if (this.getDatosSesion(request).getNivelAutenticacion() == 'A'){
					not = DelegateUtil.getNotificacionTelematicaDelegate().obtenerNotificacionTelematicaAnonima(codigo,this.getIdPersistencia(request));
				}else{
					not = DelegateUtil.getNotificacionTelematicaDelegate().obtenerNotificacionTelematicaAutenticada(codigo);
				}
				request.setAttribute("notificacion",not);
				if (not.getFechaAcuse() != null){			
					return mapping.findForward("notificacionRecibida"+anonimo);
				}else{
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
				return mapping.findForward("tramite"+anonimo);
			}else if (ElementoExpediente.TIPO_ENTRADA_PREREGISTRO.equals(tipo)){
				EntradaPreregistro entrada; 
				if (this.getDatosSesion(request).getNivelAutenticacion() == 'A'){
					entrada = DelegateUtil.getEntradaPreregistroDelegate().obtenerEntradaPreregistroAnonima(codigo,this.getIdPersistencia(request));		
				}else{
					entrada = DelegateUtil.getEntradaPreregistroDelegate().obtenerEntradaPreregistroAutenticada(codigo);		
				}
				request.setAttribute("tramite",entrada);
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

	
	
}
