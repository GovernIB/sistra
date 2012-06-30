package es.caib.sistra.front.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import es.caib.sistra.front.util.InstanciaManager;
import es.caib.sistra.front.util.TramiteRequestHelper;
import es.caib.sistra.persistence.delegate.DelegateException;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.sistra.persistence.delegate.InstanciaDelegate;
import es.caib.util.ValidacionesUtil;

public class SeleccionNotificacionAvisosForm  extends SistraFrontForm
{
	private String seleccionNotificacion; // "true" / "false"
	private String seleccionAvisos; // "true" / "false"
	private String emailSeleccionAviso;
	private String smsSeleccionAviso;
	public String getSeleccionNotificacion() {
		return seleccionNotificacion;
	}
	public void setSeleccionNotificacion(String seleccionNotificacion) {
		this.seleccionNotificacion = seleccionNotificacion;
	}
	public String getSeleccionAvisos() {
		return seleccionAvisos;
	}
	public void setSeleccionAvisos(String seleccionAvisos) {
		this.seleccionAvisos = seleccionAvisos;
	}
	public String getEmailSeleccionAviso() {
		return emailSeleccionAviso;
	}
	public void setEmailSeleccionAviso(String emailSeleccionAviso) {
		this.emailSeleccionAviso = emailSeleccionAviso;
	}
	public String getSmsSeleccionAviso() {
		return smsSeleccionAviso;
	}
	public void setSmsSeleccionAviso(String smsSeleccionAviso) {
		this.smsSeleccionAviso = smsSeleccionAviso;
	}
	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = super.validate(mapping, request);
       
        if (errors == null) 
        {
            errors = new ActionErrors();
        }
        if(StringUtils.isNotEmpty(emailSeleccionAviso) && !ValidacionesUtil.validarEmail(this.emailSeleccionAviso)){
    		errors.add("emailSeleccionAviso", new ActionError("finalizacion.avisos.emailError"));    		
    	}
    	if(StringUtils.isNotEmpty(smsSeleccionAviso) && !ValidacionesUtil.validarMovil(smsSeleccionAviso)){
    		errors.add("smsSeleccionAviso", new ActionError("finalizacion.avisos.smsError"));    	
    	}
    	if ("true".equals(this.seleccionAvisos) && StringUtils.isEmpty(this.emailSeleccionAviso)) {
    		errors.add("emailSeleccionAviso", new ActionError("finalizacion.avisos.emailObligatorio"));
    	}
    	
    	try {
			boolean obligAvisosNotif = Boolean.parseBoolean(StringUtils.defaultString(DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion().getProperty("sistra.avisoObligatorioNotificaciones"), "false"));
			if (obligAvisosNotif && ("true".equals(seleccionNotificacion)) && !("true".equals(seleccionAvisos))) {
				errors.add("seleccionAvisos", new ActionError("finalizacion.avisos.avisoObligatorioNotificaciones"));
			}
		} catch (DelegateException ex) {
			throw new RuntimeException("No se ha podido acceder a configuracion", ex);
		}
    	
    	// Metemos info paso actual en la request
    	try {
    		InstanciaDelegate delegate = InstanciaManager.recuperarInstancia( request );		
    		TramiteRequestHelper.setRespuestaFront( request, delegate.pasoActual() );
    	} catch (Exception ex) {
    		throw new RuntimeException("No se ha encontrado instancia en sesion", ex);
    	}
    	
    	return errors;
	}
	
}
