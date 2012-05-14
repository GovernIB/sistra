package es.caib.mobtratel.persistence.util;

import java.util.Iterator;
import java.util.List;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.InitialContext;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.mobtratel.model.Constantes;
import es.caib.mobtratel.model.Envio;
import es.caib.mobtratel.model.MensajeEmail;
import es.caib.sistra.plugins.PluginFactory;
import es.caib.sistra.plugins.email.EstadoEnvio;
import es.caib.sistra.plugins.email.PluginEmailIntf;
import es.caib.util.StringUtil;
import es.caib.xml.ConstantesXML;




/**
 * Clase de utilidad para enviar correos
 *
 */
public class EmailUtils
{
	
	private static EmailUtils instance = null;
	
	private static PluginEmailIntf pluginEmail = null;
	
	private Log log = LogFactory.getLog( EmailUtils.class  );
	
	protected EmailUtils(){
		
	}
	
	public static EmailUtils getInstance()  throws Exception{
		
		if(instance == null)
		{
			instance = new EmailUtils();
			try {
			 pluginEmail = PluginFactory.getInstance().getPluginEnvioEmail();
			} catch (Exception ex) {
				// Capturamos excepcion por si no esta implementado este plugin
				pluginEmail = null;
			}
		}
		return instance;
	}
	
	
	/*
    public boolean enviar(MensajeEmail me, Envio envio)  throws Exception{
    	try {    		
    		InitialContext jndiContext = new InitialContext();
//    		Session mailSession = (Session)jndiContext.lookup("java:/es.caib.mobtratel.mail");
    		Session mailSession = (Session)jndiContext.lookup("java:/" + envio.getCuenta().getEmail());

    		MimeMessage msg = new MimeMessage(mailSession);
            //msg.setFrom(new InternetAddress(envio.getCuenta().getEmail()));

    		InternetAddress[] direcciones = getDirecciones(new String(me.getDestinatarios()));
    		msg.setRecipients(javax.mail.Message.RecipientType.BCC, direcciones);
    		msg.setSubject(me.getTitulo());
    		
	    	byte[] mensaje = me.getMensaje();

    		//msg.setContent(mensaje != null ? new String( mensaje, ConstantesXML.ENCODING ) : "", "text/html");

    		msg.setHeader("X-Mailer", "JavaMailer");
    		msg.setHeader("Content-Transfert-Encoding", "8Bit");
    		msg.setSentDate(new java.util.Date());
    		msg.setText(mensaje != null ? new String( mensaje, ConstantesXML.ENCODING ) : "", ConstantesXML.ENCODING);
    		log.info("Vamos a enviar Email: " + msg.getContent());
    		try {
    		       Transport elTransport = mailSession.getTransport(direcciones[0]);
    		       elTransport.connect();
    		       elTransport.sendMessage(msg,direcciones);
    		       elTransport.close();

//                Transport.send(msg);
            } catch (Exception ex) { 
        		log.error("Excepción enviando correo "+ envio.getNombre(),ex);
        		throw ex;
            }

    		return true;

    	}catch (Exception ex){
    		log.error("Excepción generando correo "+ envio.getNombre(),ex);
    		throw ex;
    	}
    }    
*/
    public boolean enviar(String prefijoEmail, MensajeEmail me, Envio envio, List destinatarios)  throws Exception{
    	try {    		
    		InitialContext jndiContext = new InitialContext();
    		Session mailSession = (Session)jndiContext.lookup("java:/" + envio.getCuenta().getEmail());

    		MimeMessage msg = new MimeMessage(mailSession);

    		InternetAddress[] direcciones = getDirecciones(destinatarios);
    		msg.setRecipients(javax.mail.Message.RecipientType.BCC, direcciones);
    		
    		// Si se verifica envio añadimos prefijo para poder tracear el envio
    		String prefijo = "";
    		if (me.isVerificarEnvio()) {    			
    			prefijo = " - " + StringUtil.replace(prefijoEmail, "?", me.getCodigo().toString()) ; 
    		}
    		msg.setSubject(me.getTitulo() + prefijo);
    		
	    	byte[] mensaje = me.getMensaje();
	    	
	    	String contenido;
	    	
	    	if (me.isHtml()){	    		
	    		contenido = new String(mensaje, ConstantesXML.ENCODING);
	    	}else{
	    		contenido = StringEscapeUtils.escapeHtml(new String(mensaje, ConstantesXML.ENCODING));
	    	}

	    	msg.setContent(contenido, "text/html");
    		msg.setHeader("X-Mailer", "JavaMailer");
    		msg.setSentDate(new java.util.Date());
    		// log.debug("Vamos a enviar Email: " + msg.getContent());
    		try {
                Transport.send(msg);
    		} catch (Exception ex) { 
        		//log.error("Excepción enviando correo "+ envio.getNombre());
        		throw ex;
            }

    		return true;

    	}catch (Exception ex){
    		//log.error("Excepción generando correo "+ envio.getNombre());
    		throw ex;
    	}
    }    

    
    private InternetAddress[] getDirecciones(String destinatarios) throws AddressException
    {
    	String[] destArray = destinatarios.split(Constantes.SEPARADOR_DESTINATARIOS);
    	InternetAddress[] direcciones = new InternetAddress[destArray.length];
    	for(int i = 0; i<destArray.length;i++)
    	{
    		direcciones[i] = new InternetAddress(destArray[i]);
    	}
    	return direcciones;
    }

    private InternetAddress[] getDirecciones(List destinatarios) throws AddressException
    {
    	InternetAddress[] direcciones = new InternetAddress[destinatarios.size()];
    	int i=0;
    	for(Iterator it=destinatarios.iterator(); it.hasNext();)
    	{
    		direcciones[i++] = new InternetAddress((String) it.next());
    	}
    	return direcciones;
    }
    
    /**
	 * Verifica envio de mensaje
	 * @param ms Mensaje
	 * @throws Exception
	 */
	public EstadoEnvio verificarEnvioMensaje(MensajeEmail ms) throws Exception{
		log.debug("Verificando envio mensaje a traves del plugin");
		
		if (pluginEmail == null) {
			throw new Exception("No se puede verificar envio mensajes ya que no se ha especificado un plugin de email");
		}
		
		EstadoEnvio estado = pluginEmail.consultarEstadoEnvio(ms.getCodigo().toString());
		log.debug("Mensaje verificado a traves del plugin: estado = " + estado.getEstado());
		return estado;
    }   
}	

