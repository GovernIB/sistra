package es.caib.bantel.front.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.bantel.front.Constants;
import es.caib.bantel.front.form.DetalleNotificacionForm;
import es.caib.bantel.front.util.MensajesUtil;
import es.caib.bantel.model.Procedimiento;
import es.caib.bantel.persistence.delegate.ConfiguracionDelegate;
import es.caib.bantel.persistence.delegate.DelegateUtil;
import es.caib.zonaper.modelInterfaz.ExpedientePAD;
import es.caib.zonaper.modelInterfaz.PersonaPAD;
import es.caib.zonaper.persistence.delegate.PadBackOfficeDelegate;

/**
 * @struts.action
 * 	name="detalleNotificacionForm"
 *  path="/altaNotificacion"
 *  validate="true"
 *  
 * @struts.action-forward
 *  name="success" path=".altaNotificacion"
 *
 * @struts.action-forward
 *  name="fail" path=".error"
 */
public class AltaNotificacionAction extends BaseAction
{
	protected static Log log = LogFactory.getLog(AltaNotificacionAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		DetalleNotificacionForm notificacionForm = (DetalleNotificacionForm)form;
		request.getSession().setAttribute(Constants.OPCION_SELECCIONADA_KEY,"3");
		PadBackOfficeDelegate ejb = new PadBackOfficeDelegate();
		request.getSession().setAttribute("documentosAltaNotificacion",null);
		request.getSession().setAttribute("parametrosAltaNotificacion",null);
		MensajesUtil.setMsg(this.getResources(request));
		ExpedientePAD exp;
		String codMensajeError = "error.notificacio.Excepcion";
		
		// Recuperamos de sesion el expediente actual
		String idExpe = (String) request.getSession().getAttribute(Constants.EXPEDIENTE_ACTUAL_IDENTIFICADOR_KEY);
		Long uniAdm = (Long) request.getSession().getAttribute(Constants.EXPEDIENTE_ACTUAL_UNIDADADMIN_KEY);
		String claveExpe = (String) request.getSession().getAttribute(Constants.EXPEDIENTE_ACTUAL_CLAVE_KEY);
		
		try{		 
			
			// Recuperamos expediente
			exp = ejb.consultaExpediente(uniAdm, idExpe,claveExpe);
			if(exp == null){
				codMensajeError = "error.expediente.consulta";
				throw new Exception("No se ha encontrado expediente");
			}
			
			// Recuperamos procedimiento
			Procedimiento procedimiento = DelegateUtil.getTramiteDelegate().obtenerProcedimiento(exp.getIdentificadorProcedimiento());
			
			// Verificamos que el expediente tenga nif
			if(StringUtils.isBlank(exp.getNifRepresentante())) {
				codMensajeError = "error.notificacio.noNif";
				throw new Exception("Expediente no tiene habilitado los avisos");
			}
			
			// Verificamos que el expediente tenga activados los avisos (si la notif requiere avisos)
			ConfiguracionDelegate delegate = DelegateUtil.getConfiguracionDelegate();
			String strAvisosNotif = delegate.obtenerConfiguracion().getProperty("sistra.avisoObligatorioNotificaciones");
			boolean avisosNotif = false;
			if (!StringUtils.isBlank(strAvisosNotif)) {
				avisosNotif = Boolean.parseBoolean(strAvisosNotif);
			}
			if (avisosNotif && 
					!(exp.getConfiguracionAvisos() != null && exp.getConfiguracionAvisos().getHabilitarAvisos() != null && exp.getConfiguracionAvisos().getHabilitarAvisos().booleanValue()) ) {
				codMensajeError = "error.notificacio.AvisosObligatorios";
				throw new Exception("Expediente no tiene habilitado los avisos");
			}
			
			
			// Precargamos datos expediente
			notificacionForm.setDescripcionExpediente(exp.getDescripcion());
			notificacionForm.setIdiomaExp(exp.getIdioma());
			notificacionForm.setIdioma(exp.getIdioma());
			notificacionForm.setCodigoPais("ESP");
			notificacionForm.setCodigoProvincia("7");
			notificacionForm.setCodigoMunicipio("");
			if (exp.getConfiguracionAvisos() != null && exp.getConfiguracionAvisos().getHabilitarAvisos() != null && 
					exp.getConfiguracionAvisos().getHabilitarAvisos().booleanValue() && StringUtils.isNotBlank(exp.getConfiguracionAvisos().getAvisoSMS())) {
				notificacionForm.setPermitirSms("S");
			}
			
			
			notificacionForm.setNif(exp.getNifRepresentante());
			
			// Intentamos obtener nombre de la persona si esta registrada en zonaper
			PersonaPAD p = DelegateUtil.getConsultaPADDelegate().obtenerDatosPADporNif(exp.getNifRepresentante());
			if (p != null){					
				notificacionForm.setUsuarioSey(p.getUsuarioSeycon());
				notificacionForm.setApellidos(p.getApellidosNombre());				
			}
			
			// Acceso por defecto por clave			
			notificacionForm.setAccesoPorClave(procedimiento.getAccesoClaveDefecto());
					
			// Oficina y organo registro
			notificacionForm.setOficinaRegistro(procedimiento.getOficinaRegistro());
			notificacionForm.setOrganoDestino(procedimiento.getOrganoRegistro());
			
			// Plazo x defecto
			notificacionForm.setDiasPlazo("0");
						
			return mapping.findForward( "success" );							
			
		}catch(Exception e){
			log.error("Excepcion mostrando alta notificacion",e);
			String mensajeError = MensajesUtil.getValue(codMensajeError, request);
			request.setAttribute( Constants.MESSAGE_KEY,mensajeError);
			request.setAttribute("enlace","true");
			request.setAttribute("enlaceUrl","recuperarExpediente.do?identificadorExp=" + idExpe + "&unidadAdm=" + uniAdm + "&claveExp=" + claveExpe);			
			return mapping.findForward("fail");
		}		
    }
	
	
}
