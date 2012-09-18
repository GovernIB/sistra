package es.caib.xml.movilidad.factoria.impl;

import javax.xml.bind.JAXBException;

import es.caib.xml.EstablecerPropiedadException;
import es.caib.xml.movilidad.modelo.MENSAJESMS;
import es.caib.xml.movilidad.modelo.ObjectFactory;

/** 
 * 
 * Mensaje SMS
 * 
 * 
 * @author magroig
 *
 */
public class MensajeSMS extends NodoBaseMovilidad {
			
	private String telefonos;
	private String texto;
	
	
	MensajeSMS (){	
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


	public static MENSAJESMS toJAXB(MensajeSMS mensaje) throws JAXBException{
		MENSAJESMS mensajeJAXB = null;
		if (mensaje != null){
			mensajeJAXB =(new ObjectFactory()).createMENSAJESMS();		
			mensajeJAXB.setTEXTO(mensaje.getTexto());
			mensajeJAXB.setTELEFONOS(mensaje.getTelefonos());			
		}
		return mensajeJAXB;		
	}
	
	
	public static MensajeSMS fromJAXB(MENSAJESMS mensajeJAXB) throws JAXBException{
		MensajeSMS mensaje = null;
		if (mensajeJAXB != null){
			mensaje = new MensajeSMS();
			mensaje.setTelefonos(mensajeJAXB.getTELEFONOS());
			mensaje.setTexto(mensajeJAXB.getTEXTO());				
		}
		return mensaje;		
	}


	public String getTelefonos() {
		return telefonos;
	}


	public void setTelefonos(String telefonos) {
		this.telefonos = telefonos;
	}


	public String getTexto() {
		return texto;
	}


	public void setTexto(String texto) {
		this.texto = texto;
	}


	

}
