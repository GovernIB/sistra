package es.caib.mobtratel.front.util;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.mobtratel.model.Envio;
import es.caib.mobtratel.model.MensajeEmail;
import es.caib.mobtratel.model.MensajeSms;
import es.caib.mobtratel.modelInterfaz.ConstantesMobtratel;
import es.caib.sistra.plugins.email.ConstantesEmail;
import es.caib.sistra.plugins.sms.ConstantesSMS;
import es.caib.util.ExcelCSVPrinter;
import es.caib.xml.ConstantesXML;

/**
 * Modeliza datos de trabajo de exportacion
 */

public class CSVExport {
	
	private Log log = LogFactory.getLog( CSVExport.class  );
	

	private Long codigo;
	
	private String nombre;
	
	private List heads = new ArrayList();
	
	private List emails = new ArrayList();
	private List smss = new ArrayList();
	
	
	
	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public List getEmails() {
		return emails;
	}

	public void setEmails(List emails) {
		this.emails = emails;
	}

	public List getSmss() {
		return smss;
	}

	public void setSmss(List smss) {
		this.smss = smss;
	}

	
	public List getHeads() {
		return heads;
	}

	public void setHeads(List heads) {
		this.heads = heads;
	}

	public CSVExport(Envio envio){
		this.setCodigo(envio.getCodigo());
		this.setNombre(envio.getNombre());
		String[] cabeceras = new String[9];
		int i = 0;
		cabeceras[i++] ="Estado";
		cabeceras[i++] ="Tipo";
		cabeceras[i++] ="Titulo";
		cabeceras[i++] ="Texto";
		cabeceras[i++] ="Destinatarios";
		cabeceras[i++] ="Destinatarios Enviados";
		cabeceras[i++] ="Error envio";
		cabeceras[i++] ="Confirmacion envio";
		cabeceras[i++] ="Error confirmacion";
		this.getHeads().add(cabeceras);
		Set emails = envio.getEmails();
		String[] entradas = null;
		//List entradas = null;
		for(Iterator it=emails.iterator(); it.hasNext();)
		{
			i = 0;
			try {
				MensajeEmail me = (MensajeEmail) it.next();
				entradas = new String[cabeceras.length];							
				if (me.getEstado() == ConstantesMobtratel.ESTADOENVIO_ENVIADO ){
					entradas[i++] = "COMPLETADO";
				}else{
					entradas[i++] = "PENDIENTE";
				}				
				entradas[i++] = Envio.TIPO_EMAIL;
				entradas[i++] = me.getTitulo();
				byte[] texto = me.getMensaje();
				entradas[i++] = texto != null ? new String( texto, ConstantesXML.ENCODING ) : "";
				texto = me.getDestinatarios();
				entradas[i++] = texto != null ? new String( texto, ConstantesXML.ENCODING ) : "";
				texto = me.getDestinatariosEnviados();
				entradas[i++] = texto != null ? new String( texto, ConstantesXML.ENCODING ) : "";
				entradas[i++] = me.getError() != null ? me.getError() : "";
				entradas[i++] = getLiteralEstadoVerificacionEnvioEmail(me.getEstadoVerificarEnvio());
				entradas[i++] = me.getErrorVerificarEnvio() != null? me.getErrorVerificarEnvio(): "";
				this.getEmails().add(entradas);
			} catch (UnsupportedEncodingException e) {
				log.error("Error recuperando informacion de MensajeEmail: " + e.getMessage());
			}
		}

		Set smss = envio.getSmss();
		for(Iterator it=smss.iterator(); it.hasNext();)
		{
			i = 0;
			try {
				MensajeSms me = (MensajeSms) it.next();
				entradas = new String[cabeceras.length];
                if (me.getEstado() == ConstantesMobtratel.ESTADOENVIO_ENVIADO ){
  					entradas[i++] = "COMPLETADO";
  				}else{
  					entradas[i++] = "PENDIENTE";
  				}
				// Tipo
				entradas[i++] = Envio.TIPO_SMS;
				// Columna del titulo
				entradas[i++] = "";
				byte[] texto = me.getMensaje();
				entradas[i++] = texto != null ? new String( texto, ConstantesXML.ENCODING ) : "";
				texto = me.getDestinatarios();
				entradas[i++] = texto != null ? new String( texto, ConstantesXML.ENCODING ) : "";
				texto = me.getDestinatariosEnviados();
				entradas[i++] = texto != null ? new String( texto, ConstantesXML.ENCODING ) : "";
				entradas[i++] = me.getError() != null ? me.getError() : "";
				entradas[i++] = getLiteralEstadoVerificacionEnvioSms(me.getEstadoVerificarEnvio());
				entradas[i++] = me.getErrorVerificarEnvio() != null? me.getErrorVerificarEnvio(): "";
				this.getSmss().add(entradas);
			} catch (UnsupportedEncodingException e) {
				log.error("Error recuperando informacion de MensajeSms: " + e.getMessage());
			}
		}

		
	}
	
	public void addMensajeEmailCSV(String[] entradas){
		this.getEmails().add(entradas);
	}

	public void addMensajeSmsCSV(String[] entradas){
		this.getSmss().add(entradas);
	}
	
	public byte[] toCSV() throws Exception{
		
		/*
		String [] csv = new String[this.getHeads().size()];
		int i=0;
		for (Iterator it = this.getHeads().iterator();it.hasNext();){
			csv[i] = (String) it.next();
			i++;
		}
		*/

		// Construimos csv
		ByteArrayOutputStream bos = new ByteArrayOutputStream(this.getHeads().size() * 500);
		// Establecemos charset compatible para guindous
		ExcelCSVPrinter ecsvp = new ExcelCSVPrinter(bos,"Cp1252"); 
		ecsvp.changeDelimiter(';');
		for (Iterator it = this.getHeads().iterator();it.hasNext();){
			ecsvp.writeln( (String[]) it.next());			
		}
		
		// Emails
		for (Iterator it = this.getEmails().iterator();it.hasNext();){
			ecsvp.writeln( (String []) it.next());			
		}

		// Smss
		for (Iterator it = this.getSmss().iterator();it.hasNext();){
			ecsvp.writeln( (String []) it.next());			
		}

		bos.close();
		
		return bos.toByteArray();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	
	private String getLiteralEstadoVerificacionEnvioEmail(String estado)  {
		String res = "";
		if (!StringUtils.isBlank(estado)) {
			if (estado.equals(ConstantesEmail.ESTADO_DESCONOCIDO)) {
				res = "DESCONOCIDO";
			} else if (estado.equals(ConstantesEmail.ESTADO_ENVIADO)) {				
					res = "ENVIADO";	
			} else if (estado.equals(ConstantesEmail.ESTADO_NO_ENVIADO)) {
					res = "NO ENVIADO";
			} else if (estado.equals(ConstantesEmail.ESTADO_NO_IMPLEMENTADO)) {
					res = "NO IMPLEMENTADO";
			} else if (estado.equals(ConstantesEmail.ESTADO_PENDIENTE)) {
					res = "PENDIENTE";
			}
		}
		return res;
	}
	
	private String getLiteralEstadoVerificacionEnvioSms(String estado)  {
		String res = "";
		if (!StringUtils.isBlank(estado)) {
			if (estado.equals(ConstantesSMS.ESTADO_DESCONOCIDO)) {
					res = "DESCONOCIDO";
			} else if (estado.equals(ConstantesSMS.ESTADO_ENVIADO)) {
					res = "ENVIADO";
			} else if (estado.equals(ConstantesSMS.ESTADO_NO_ENVIADO)) {
					res = "NO ENVIADO";
			} else if (estado.equals(ConstantesSMS.ESTADO_NO_IMPLEMENTADO)) {
					res = "NO IMPLEMENTADO";
			} else if (estado.equals(ConstantesSMS.ESTADO_PENDIENTE)) {			
					res = "PENDIENTE";
			}
		}
		return res;
	}
}
