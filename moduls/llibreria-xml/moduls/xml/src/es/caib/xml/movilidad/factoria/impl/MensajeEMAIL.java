package es.caib.xml.movilidad.factoria.impl;

import javax.xml.bind.JAXBException;

import es.caib.xml.EstablecerPropiedadException;
import es.caib.xml.movilidad.modelo.MENSAJEEMAIL;
import es.caib.xml.movilidad.modelo.ObjectFactory;

/** 
 * 
 * Mensaje EMAIL
 * 
 * 
 * @author magroig
 *
 */
public class MensajeEMAIL extends NodoBaseMovilidad {
			
	private String titulo;
	private String texto;
	private String emails;
	
	
	MensajeEMAIL (){	
	}
			

	public void comprobarDatosRequeridos() throws EstablecerPropiedadException {		
		
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals (Object obj){
		
		
		/*
		 	TODO SI QUISIERAMOS COMPARAR HAY Q IMPLEMENTAR EQUALS
		 	
		  if (obj instanceof Envio){
			
			if (obj == null) return false;
			
			Envio inst = (Envio) obj;
						
			if (!objetosIguales (getNombre(), inst.getNombre())) return false;
			if (!objetosIguales (getCuenta(), inst.getCuenta())) return false;
			if (!objetosIguales (getFechaCaducidad(), inst.getFechaCaducidad())) return false;
			if (!objetosIguales (getFechaProgramacion(), inst.getFechaProgramacion())) return false;
			
			
			// OK los objetos son equivalentes
			return true;
		}*/
		
		return super.equals (obj);
		
		
	}


	public static MENSAJEEMAIL toJAXB(MensajeEMAIL mensaje) throws JAXBException{
		MENSAJEEMAIL mensajeJAXB = null;
		if (mensaje != null){
			mensajeJAXB =(new ObjectFactory()).createMENSAJEEMAIL();		
			mensajeJAXB.setTEXTO(mensaje.getTexto());
			mensajeJAXB.setTITULO(mensaje.getTitulo());		
			mensajeJAXB.setEMAILS(mensaje.getEmails());
		}
		return mensajeJAXB;		
	}
	
	
	public static MensajeEMAIL fromJAXB(MENSAJEEMAIL mensajeJAXB) throws JAXBException{
		MensajeEMAIL mensaje = null;
		if (mensajeJAXB != null){
			mensaje = new MensajeEMAIL();
			mensaje.setTitulo(mensajeJAXB.getTITULO());
			mensaje.setTexto(mensajeJAXB.getTEXTO());	
			mensaje.setEmails(mensajeJAXB.getEMAILS());	
		}
		return mensaje;		
	}


	public String getTitulo() {
		return titulo;
	}


	public void setTitulo(String telefonos) {
		this.titulo = telefonos;
	}


	public String getTexto() {
		return texto;
	}


	public void setTexto(String texto) {
		this.texto = texto;
	}


	public String getEmails() {
		return emails;
	}


	public void setEmails(String emails) {
		this.emails = emails;
	}


	

}
