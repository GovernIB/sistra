package es.caib.xml.movilidad.factoria.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBException;

import es.caib.xml.EstablecerPropiedadException;
import es.caib.xml.movilidad.modelo.ENVIO;
import es.caib.xml.movilidad.modelo.MENSAJEEMAIL;
import es.caib.xml.movilidad.modelo.MENSAJES;
import es.caib.xml.movilidad.modelo.MENSAJESMS;
import es.caib.xml.movilidad.modelo.ObjectFactory;

/** 
 * 
 * Envio
 * 
 * 
 * @author magroig
 *
 */
public class Envio extends NodoBaseMovilidad {
			
	private String nombre;
	private String cuenta;
	private String fechaProgramacion;
	private String fechaCaducidad;
	private String inmediato;
	
	private List mensajesSMS = new ArrayList();
	private List mensajesEMAIL = new ArrayList();
	
	
	Envio (){	
	}
			

	public void comprobarDatosRequeridos() throws EstablecerPropiedadException {		
		
		if ( (getFechaCaducidad() != null) && (!getFechaCaducidad().equals("")) && !validaFecha(getFechaCaducidad()))
			throw new EstablecerPropiedadException("No es una fecha válida","String","fechaCaducidad",getFechaCaducidad());
		
		if ( (getFechaProgramacion() != null) && (!getFechaProgramacion().equals("")) && !validaFecha(getFechaProgramacion()))
			throw new EstablecerPropiedadException("No es una fecha válida","String","fechaProgramacion",getFechaProgramacion());
		
		for (Iterator it=mensajesSMS.iterator();it.hasNext();)
			((MensajeSMS) it.next()).comprobarDatosRequeridos();
		for (Iterator it=mensajesEMAIL.iterator();it.hasNext();)
			((MensajeEMAIL) it.next()).comprobarDatosRequeridos();	
		
		
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


	public static ENVIO toJAXB(Envio envio) throws JAXBException{
		ENVIO envioJAXB = null;
		if (envio != null){
			envioJAXB =(new ObjectFactory()).createENVIO();		
			envioJAXB.setNOMBRE(envio.getNombre());
			envioJAXB.setCUENTA(envio.getCuenta());
			envioJAXB.setFECHACADUCIDAD(envio.getFechaCaducidad());
			envioJAXB.setFECHAPROGRAMACION(envio.getFechaProgramacion());
			envioJAXB.setINMEDIATO(envio.getInmediato());
			
			MENSAJES mensajes = (new ObjectFactory()).createMENSAJES();	
			envioJAXB.setMENSAJES(mensajes);
						
			for (Iterator it= envio.getMensajesSMS().iterator();it.hasNext();){			
				envioJAXB.getMENSAJES().getMENSAJESMS().add(MensajeSMS.toJAXB((MensajeSMS) it.next()));
			}	
			
			for (Iterator it= envio.getMensajesEMAIL().iterator();it.hasNext();){			
				envioJAXB.getMENSAJES().getMENSAJEEMAIL().add(MensajeEMAIL.toJAXB((MensajeEMAIL) it.next()));
			}	
		}
		return envioJAXB;		
	}
	
	
	public static Envio fromJAXB(ENVIO envioJAXB) throws JAXBException{
		Envio envio = null;
		if (envioJAXB != null){
			envio = new Envio();
			
			envio.setNombre(envioJAXB.getNOMBRE());
			envio.setCuenta(envioJAXB.getCUENTA());
			envio.setFechaCaducidad(envioJAXB.getFECHACADUCIDAD());
			envio.setFechaProgramacion(envioJAXB.getFECHAPROGRAMACION());
			envio.setInmediato(envioJAXB.getINMEDIATO());
			
			for (Iterator it= envioJAXB.getMENSAJES().getMENSAJESMS().iterator();it.hasNext();){			
				envio.getMensajesSMS().add(MensajeSMS.fromJAXB((MENSAJESMS) it.next()));
			}	
			
			for (Iterator it= envioJAXB.getMENSAJES().getMENSAJEEMAIL().iterator();it.hasNext();){			
				envio.getMensajesEMAIL().add(MensajeEMAIL.fromJAXB((MENSAJEEMAIL) it.next()));
			}	
						
		}
		return envio;		
	}


	public String getCuenta() {
		return cuenta;
	}


	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}


	public String getFechaCaducidad() {
		return fechaCaducidad;
	}


	public void setFechaCaducidad(String fechaCaducidad) {
		this.fechaCaducidad = fechaCaducidad;
	}


	public String getFechaProgramacion() {
		return fechaProgramacion;
	}


	public void setFechaProgramacion(String fechaProgramacion) {
		this.fechaProgramacion = fechaProgramacion;
	}


	public List getMensajesEMAIL() {
		return mensajesEMAIL;
	}


	public void setMensajesEMAIL(List mensajesEMAIL) {
		this.mensajesEMAIL = mensajesEMAIL;
	}


	public List getMensajesSMS() {
		return mensajesSMS;
	}


	public void setMensajesSMS(List mensajesSMS) {
		this.mensajesSMS = mensajesSMS;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getInmediato() {
		return inmediato;
	}


	public void setInmediato(String inmediato) {
		this.inmediato = inmediato;
	}
	

}
