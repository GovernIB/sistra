package es.caib.bantel.front.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import es.caib.bantel.front.Constants;
import es.caib.bantel.front.util.MensajesUtil;
import es.caib.bantel.model.Procedimiento;
import es.caib.bantel.persistence.delegate.DelegateUtil;
import es.caib.util.StringUtil;
import es.caib.zonaper.modelInterfaz.DetalleAcuseRecibo;
import es.caib.zonaper.modelInterfaz.DetalleAviso;
import es.caib.zonaper.modelInterfaz.ElementoExpedientePAD;
import es.caib.zonaper.modelInterfaz.ExpedientePAD;
import es.caib.zonaper.modelInterfaz.NotificacionExpedientePAD;
import es.caib.zonaper.persistence.delegate.PadBackOfficeDelegate;

/**
 * @struts.action
 *  path="/recuperarExpediente"
 *  
 * @struts.action-forward
 *  name="success" path=".recuperarExpediente"
 *  
 * @struts.action-forward
 *  name="fail" path=".error"
 */
public class RecuperacionExpedienteAction extends BaseAction
{
	protected static Log log = LogFactory.getLog(RecuperacionExpedienteAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		
		MessageResources resources = this.getResources(request);
		PadBackOfficeDelegate ejb = new PadBackOfficeDelegate();
		request.getSession().setAttribute(Constants.OPCION_SELECCIONADA_KEY,"3");
		ExpedientePAD exp;
		
	
		try{
			
			// Reseteamos parametros expediente actual
			request.getSession().setAttribute(Constants.EXPEDIENTE_ACTUAL_IDENTIFICADOR_KEY, null);
			request.getSession().setAttribute(Constants.EXPEDIENTE_ACTUAL_UNIDADADMIN_KEY, null);
			request.getSession().setAttribute(Constants.EXPEDIENTE_ACTUAL_CLAVE_KEY, null);
			
			// Recuperamos expediente y en caso correcto almacenamos en sesion los parametros de acceso para futuras llamadas
			String identificadorExp = request.getParameter("identificadorExp");
			String unidadAdm = request.getParameter("unidadAdm");
			String claveExp = request.getParameter("claveExp");
			exp = ejb.consultaExpediente( new Long(unidadAdm), identificadorExp, claveExp);
			if(exp == null){
				throw new Exception("No existe expediente");
			}
			String identificadorProc = exp.getIdentificadorProcedimiento();
			
			// Buscamos descripcion procedimiento y si permite sms
			String descProc = exp.getIdentificadorProcedimiento();
			String permitirSms = "N";
			String permitirPlazoNotifVble = "N";			
			Procedimiento procedimiento = DelegateUtil.getTramiteDelegate().obtenerProcedimiento(exp.getIdentificadorProcedimiento());
			if (procedimiento != null) {
				descProc += " - " + procedimiento.getDescripcion();
				permitirSms = procedimiento.getPermitirSms();
				permitirPlazoNotifVble = procedimiento.getPermitirPlazoNotificacionesVariable();
			}
			
			// Establecemos observaciones particulares para los elementos del expediente
			List observacionesElemento = new ArrayList();			
			if (exp.getElementos() != null) {				
				for (Iterator it = exp.getElementos().iterator(); it.hasNext();) {
					ElementoExpedientePAD elemento = (ElementoExpedientePAD) it.next();
					String observaciones = "";
					boolean primerConfir = true;
					if (elemento instanceof NotificacionExpedientePAD) {
						NotificacionExpedientePAD notif = (NotificacionExpedientePAD) elemento;
						
						// Mostramos avisos
						if (notif.getDetalleAcuseRecibo().getAvisos() != null) {									
							for (Iterator it2 = notif.getDetalleAcuseRecibo().getAvisos().iterator(); it2.hasNext();){
								DetalleAviso da = (DetalleAviso) it2.next();
								
								if (primerConfir) {
									primerConfir = false;
								} else {
									observaciones += "<br/>";
								}
								
								observaciones += da.getTipo() + ":";
								
								if (da.isEnviado()) {
									observaciones += resources.getMessage(this.getLocale(request), "notificacion.avisoEnviado");
								} else {
									observaciones += resources.getMessage(this.getLocale(request), "notificacion.avisoNoEnviado");										
								}
								
								if ( DetalleAviso.CONFIRMADO_ENVIADO.equals(da.getConfirmadoEnvio())) {
									observaciones += " [" + resources.getMessage(this.getLocale(request), "notificacion.avisoConfirmado.OK") + "]";
								}
								
								if ( DetalleAviso.CONFIRMADO_NO_ENVIADO.equals(da.getConfirmadoEnvio())) {
									observaciones += " [" + resources.getMessage(this.getLocale(request), "notificacion.avisoConfirmado.KO") + "]";
								}
																										
							}
						}
						
						// Si requiere acuse y esta pendiente mostramos fin de plazo
						if (notif.isRequiereAcuse()) {
							// Fin de plazo
							if (DetalleAcuseRecibo.ESTADO_PENDIENTE.equals(notif.getDetalleAcuseRecibo().getEstado()) && 
									notif.getDetalleAcuseRecibo().getFechaFinPlazo() != null) {
								if (primerConfir) {
									primerConfir = false;
								} else {
									observaciones += "<br/>";
								}
								observaciones += resources.getMessage(this.getLocale(request), "notificacion.finPlazo") + StringUtil.fechaACadena(notif.getDetalleAcuseRecibo().getFechaFinPlazo(), StringUtil.FORMATO_FECHA);
							}														
						}
					}
					observacionesElemento.add(observaciones);					
				}				
			}
			
			// Establecemos parametros expediente actual
			request.getSession().setAttribute(Constants.EXPEDIENTE_ACTUAL_IDENTIFICADOR_KEY, identificadorExp);
			request.getSession().setAttribute(Constants.EXPEDIENTE_ACTUAL_UNIDADADMIN_KEY, new Long(unidadAdm));
			request.getSession().setAttribute(Constants.EXPEDIENTE_ACTUAL_CLAVE_KEY, claveExp);
			request.getSession().setAttribute(Constants.EXPEDIENTE_ACTUAL_PROCEDIMIENTO_KEY, identificadorProc);
			
			// Establecemos expediente en la request 
			request.setAttribute("expediente", exp);
			request.setAttribute("descripcionProcedimiento", descProc);
			request.setAttribute("permitirSms", permitirSms);
			request.setAttribute("permitirPlazoNotifVble", permitirPlazoNotifVble);			
			request.setAttribute("observacionesElemento", observacionesElemento);
			return mapping.findForward( "success" );
		
		}catch(Exception e){
			log.error("Excepcion recuperando expediente",e);
			String mensajeOk = MensajesUtil.getValue("error.expediente.consulta", request);
			request.setAttribute( Constants.MESSAGE_KEY,mensajeOk + ": " + e.getMessage());
			return mapping.findForward("fail");
		}		
    }
}


