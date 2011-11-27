package es.caib.xml.avisonotificacion.factoria.impl;

import es.caib.xml.EstablecerPropiedadException;

/** Objeto de Aviso Notificacion
 * 
 * @author magroig
 *
 */
public class AvisoNotificacion extends NodoBaseAvisoNotificacion  {
			
	private String titulo;
	private String texto;
	private String textoSMS;
	private	Boolean acuseRecibo;
	private Expediente expediente;
	
	
	AvisoNotificacion (){
		titulo = null;
		texto = null;
		acuseRecibo = null;
		expediente = new Expediente();
	}
			

	public void comprobarDatosRequeridos() throws EstablecerPropiedadException {
		validaCampoObligatorio("AvisoNotificacion", "Titulo", this.getTitulo());
		validaCampoObligatorio("AvisoNotificacion", "Texto", this.getTexto());
		validaCampoObligatorio("AvisoNotificacion", "Acuse recibo", this.getAcuseRecibo());
		
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals (Object obj){
		if (obj instanceof AvisoNotificacion){
			
			if (obj == null) return false;
			
			AvisoNotificacion inst = (AvisoNotificacion) obj;
						
			if (!objetosIguales (getTitulo(), inst.getTitulo())) return false;
			if (!objetosIguales (getTexto(), inst.getTexto())) return false;
			if (!objetosIguales (getTextoSMS(), inst.getTextoSMS())) return false;
			if (!objetosIguales (getAcuseRecibo(), inst.getAcuseRecibo())) return false;
			if (!objetosIguales (getExpediente(), inst.getExpediente())) return false;
			
			// OK los objetos son equivalentes
			return true;
		}
		
		return super.equals (obj);
	}


	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}


	public Boolean getAcuseRecibo() {
		return acuseRecibo;
	}


	public void setAcuseRecibo(Boolean acuseRecibo) {
		this.acuseRecibo = acuseRecibo;
	}


	public Expediente getExpediente() {
		return expediente;
	}


	public void setExpediente(Expediente expediente) {
		this.expediente = expediente;
	}


	public String getTextoSMS() {
		return textoSMS;
	}


	public void setTextoSMS(String textoSMS) {
		this.textoSMS = textoSMS;
	}

}
