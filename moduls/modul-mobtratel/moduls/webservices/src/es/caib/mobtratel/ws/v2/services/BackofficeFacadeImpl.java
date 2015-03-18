package es.caib.mobtratel.ws.v2.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.mobtratel.persistence.delegate.DelegateMobTraTelUtil;
import es.caib.mobtratel.persistence.delegate.MobTraTelDelegate;

@javax.jws.WebService(portName = "BackofficeFacade", serviceName = "BackofficeFacadeService", 
        targetNamespace = "urn:es:caib:mobtratel:ws:v2:services", 
        endpointInterface = "es.caib.mobtratel.ws.v2.services.BackofficeFacade")
public class BackofficeFacadeImpl implements BackofficeFacade {

	private static Log log = LogFactory.getLog(BackofficeFacadeImpl.class);
	
	public String enviarMensaje(es.caib.mobtratel.ws.v2.model.MensajeEnvio mensaje) throws es.caib.mobtratel.ws.v2.services.BackofficeFacadeException {
		try{
			MobTraTelDelegate mttd = DelegateMobTraTelUtil.getMobTraTelDelegate();
			es.caib.mobtratel.modelInterfaz.MensajeEnvio msEnv = mensajeEnvioWSToMensajeEnvioIntf(mensaje);
			String codigo = mttd.envioMensaje(msEnv);
			return codigo;
		}catch(Exception e){
			log.error("Excepcion en webservice: " + e.getMessage(), e);
			throw new es.caib.mobtratel.ws.v2.services.BackofficeFacadeException(e.getMessage(),new es.caib.mobtratel.ws.v2.model.BackofficeFacadeException());
		}
	}

	
	
	//	 --------------------------------------------------------------
	//		FUNCIONES AUXILIARES
	// --------------------------------------------------------------
	
	private es.caib.mobtratel.modelInterfaz.MensajeEnvio mensajeEnvioWSToMensajeEnvioIntf(es.caib.mobtratel.ws.v2.model.MensajeEnvio mensaje){
		es.caib.mobtratel.modelInterfaz.MensajeEnvio msEnv = new es.caib.mobtratel.modelInterfaz.MensajeEnvio();
		if(mensaje != null){
			if(mensaje.getCuentaEmisora() != null){
				msEnv.setCuentaEmisora(mensaje.getCuentaEmisora().getValue());
			}
			if(mensaje.getFechaCaducidad() != null){
				msEnv.setFechaCaducidad(mensaje.getFechaCaducidad().getValue().toGregorianCalendar().getTime());
			}
			if(mensaje.getFechaProgramacionEnvio() != null){
				msEnv.setFechaProgramacionEnvio(mensaje.getFechaProgramacionEnvio().getValue().toGregorianCalendar().getTime());
			}
			msEnv.setInmediato(mensaje.isInmediato());
			msEnv.setNombre(mensaje.getNombre());
			if(mensaje.getEmails() != null){
				msEnv.setEmails(emailsWSToEmailsIntf(mensaje.getEmails().getValue()));
			}
			if(mensaje.getSmss()!= null){
				msEnv.setSmss(smssWSToSmssIntf(mensaje.getSmss().getValue()));
			}
			if (mensaje.getIdProcedimiento() != null) {
				msEnv.setIdProcedimiento(mensaje.getIdProcedimiento().getValue());
			}
		}
		return msEnv;
	}
	
	private List emailsWSToEmailsIntf(es.caib.mobtratel.ws.v2.model.MensajesEnvioEmail mensaje){
		List emails = new ArrayList();
		if(mensaje != null){
			for(int i=0;i<mensaje.getMensajes().size();i++){
				es.caib.mobtratel.modelInterfaz.MensajeEnvioEmail email = new es.caib.mobtratel.modelInterfaz.MensajeEnvioEmail();
				email.setDestinatarios(mensaje.getMensajes().get(i).getDestinatarios().toArray(new String[0]));
				email.setHtml(mensaje.getMensajes().get(i).isHtml());
				email.setTexto(mensaje.getMensajes().get(i).getTexto());
				email.setTitulo(mensaje.getMensajes().get(i).getTitulo());
				if (mensaje.getMensajes().get(i).isVerificarEnvio() != null) {
					email.setVerificarEnvio(mensaje.getMensajes().get(i).isVerificarEnvio().booleanValue());
				}
				emails.add(email);
			}
		}
		return emails;
	}
	
	private List smssWSToSmssIntf(es.caib.mobtratel.ws.v2.model.MensajesEnvioSMS mensaje){
		List smss = new ArrayList();
		if(mensaje != null){
			for(int i=0;i<mensaje.getMensajes().size();i++){
				es.caib.mobtratel.modelInterfaz.MensajeEnvioSms sms = new es.caib.mobtratel.modelInterfaz.MensajeEnvioSms();
				sms.setDestinatarios(mensaje.getMensajes().get(i).getDestinatarios().toArray(new String[0]));
				sms.setTexto(mensaje.getMensajes().get(i).getTexto());
				if (mensaje.getMensajes().get(i).isVerificarEnvio() != null) {
					sms.setVerificarEnvio(mensaje.getMensajes().get(i).isVerificarEnvio().booleanValue());
				}
				smss.add(sms);
			}
		}
		return smss;
	}
}