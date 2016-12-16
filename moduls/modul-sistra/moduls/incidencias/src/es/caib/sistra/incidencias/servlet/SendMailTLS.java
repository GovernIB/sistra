package es.caib.sistra.incidencias.servlet;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.apache.commons.io.IOUtils;

public class SendMailTLS {

	public static void main(String[] args) {

		final String username = "mail.indra.test@gmail.com";
		final String password = "1ndr@t3st";

		Properties props = new Properties();
		props.put("mail.from", "mail.indra.test@gmail.com");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session mailSession = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });

		try {
			
			List<String> destinatarios = new ArrayList<String>();
			destinatarios.add("rsanz@indra.es");
			String titulo = "Hola";
			String textoHtml;
			byte[] fileContent = "hola torpedo".getBytes("UTF-8");
			String fileName = "hola.txt";
			String fileContentType = "text/plain";
			
			
			InputStream isHtml = SendMailTLS.class.getResourceAsStream("mailIncidencia.html");
			byte[] content  = IOUtils.toByteArray(isHtml);
			textoHtml = new String(content, "UTF-8");

			Multipart multipart = new MimeMultipart();
			
			// Message Part
			BodyPart htmlBodyPart = new MimeBodyPart(); 
			htmlBodyPart.setContent(textoHtml , "text/html");
			multipart.addBodyPart(htmlBodyPart); 
			
			// Attachment Part
			if (fileContent != null) {
				BodyPart attachmentBodyPart = new MimeBodyPart(); 
				DataSource datasource = new ByteArrayDataSource(fileContent, fileContentType);
				attachmentBodyPart.setDataHandler(new DataHandler(datasource)); 
				attachmentBodyPart.setFileName(fileName);
				multipart.addBodyPart(attachmentBodyPart);
			}
			
			
			// Send message
			Message msg = new MimeMessage(mailSession);
			InternetAddress[] direcciones = getDirecciones(destinatarios);
			msg.setRecipients(javax.mail.Message.RecipientType.BCC, direcciones);
			msg.setSubject(titulo);			
			msg.setHeader("X-Mailer", "JavaMailer");
			String mailFrom = mailSession.getProperty("mail.from");
			msg.setFrom(new InternetAddress(mailFrom));
			msg.setContent(multipart);
			
			
			Transport.send(msg);

			System.out.println("Done");

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
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