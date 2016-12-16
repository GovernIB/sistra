package es.caib.sistra.incidencias.servlet;

import java.util.Iterator;
import java.util.List;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class EmailUtil {

	protected static Log log = LogFactory.getLog(EmailUtil.class);

	public static boolean sendEmail(String emailDs, List<String> destinatarios,
			String titulo, String textoHtml, byte[] fileContent, String fileName, String fileContentType) {

		boolean ret = false;
		try {
			InitialContext jndiContext = new InitialContext();
			Session mailSession = (Session) jndiContext.lookup("java:/" + emailDs);
			
			Multipart multipart = new MimeMultipart();
			
			if (fileContent != null) {
				
				// Message Part
				BodyPart htmlBodyPart = new MimeBodyPart(); 
				htmlBodyPart.setContent(textoHtml , "text/html");
				multipart.addBodyPart(htmlBodyPart); 
			
				// Attachment Part
				BodyPart attachmentBodyPart = new MimeBodyPart(); 
				DataSource datasource = new ByteArrayDataSource(fileContent, fileContentType);
				attachmentBodyPart.setDataHandler(new DataHandler(datasource)); 
				attachmentBodyPart.setFileName(fileName);
				multipart.addBodyPart(attachmentBodyPart);
			}
			
			// Send message
			Message msg = new MimeMessage(mailSession);
			InternetAddress[] direcciones = getDirecciones(destinatarios);
			msg.setRecipients(javax.mail.Message.RecipientType.TO, direcciones);
			msg.setSubject(titulo);			
			msg.setHeader("X-Mailer", "JavaMailer");
			String mailFrom = mailSession.getProperty("mail.from");
			msg.setFrom(new InternetAddress(mailFrom));
			
			if (fileContent != null) {
				msg.setContent(multipart);
			} else {
				msg.setContent(textoHtml, "text/html");
			}
			
			
			Transport.send(msg);
			ret = true;
		} catch (Exception ex) {
			log.error("Excepción enviando correo incidencias: " + ex.getMessage(), ex);
		}

		return ret;

	}

	private static InternetAddress[] getDirecciones(List<String> destinatarios)
			throws AddressException {
		InternetAddress[] direcciones = new InternetAddress[destinatarios
				.size()];
		int i = 0;
		for (Iterator it = destinatarios.iterator(); it.hasNext();) {
			direcciones[i++] = new InternetAddress((String) it.next());
		}
		return direcciones;
	}
}
