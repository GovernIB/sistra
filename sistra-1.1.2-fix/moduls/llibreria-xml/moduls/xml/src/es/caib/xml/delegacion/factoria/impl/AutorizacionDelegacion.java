package es.caib.xml.delegacion.factoria.impl;

import javax.xml.bind.JAXBException;

import es.caib.xml.EstablecerPropiedadException;
import es.caib.xml.delegacion.modelo.AUTORIZACIONDELEGACION;
import es.caib.xml.delegacion.modelo.ObjectFactory;

/** 
 * 
 * Autorizacion delegacion
 * 
 * 
 * @author magroig
 *
 */
public class AutorizacionDelegacion extends NodoBaseDelegacion {
			
	private String entidadId;
	private String entidadNombre;
	private String delegadoId;
	private String delegadoNombre;
	private String permisos;
	private String fechaInicio;
	private String fechaFin;
	
	
	AutorizacionDelegacion (){	
	}
			

	public void comprobarDatosRequeridos() throws EstablecerPropiedadException {		
		
		if ( (getFechaInicio() != null) && (!getFechaInicio().equals("")) && !validaFecha(getFechaInicio()))
			throw new EstablecerPropiedadException("No es una fecha válida","String","fechaInicio",getFechaInicio());
		
		if ( (getFechaFin() != null) && (!getFechaFin().equals("")) && !validaFecha(getFechaFin()))
			throw new EstablecerPropiedadException("No es una fecha válida","String","fechaFin",getFechaFin());
		
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals (Object obj){
		return super.equals (obj);		
	}


	public static AUTORIZACIONDELEGACION toJAXB(AutorizacionDelegacion delegacion) throws JAXBException{
		AUTORIZACIONDELEGACION delegacionJAXB = null;
		if (delegacion != null){
			delegacionJAXB =(new ObjectFactory()).createAUTORIZACIONDELEGACION();		
			delegacionJAXB.setENTIDADID(delegacion.getEntidadId());
			delegacionJAXB.setENTIDADNOMBRE(delegacion.getEntidadNombre());
			delegacionJAXB.setDELEGADOID(delegacion.getDelegadoId());
			delegacionJAXB.setDELEGADONOMBRE(delegacion.getDelegadoNombre());
			delegacionJAXB.setPERMISOS(delegacion.getPermisos());
			delegacionJAXB.setFECHAINICIO(delegacion.getFechaInicio());
			delegacionJAXB.setFECHAFIN(delegacion.getFechaFin());			
		}
		return delegacionJAXB;		
	}
	
	
	public static AutorizacionDelegacion fromJAXB(AUTORIZACIONDELEGACION delegacionJAXB) throws JAXBException{
		AutorizacionDelegacion delegacion = null;
		if (delegacionJAXB != null){
			delegacion = new AutorizacionDelegacion();
			delegacion.setEntidadId(delegacionJAXB.getENTIDADID());
			delegacion.setEntidadNombre(delegacionJAXB.getENTIDADNOMBRE());
			delegacion.setDelegadoId(delegacionJAXB.getDELEGADOID());
			delegacion.setDelegadoNombre(delegacionJAXB.getDELEGADONOMBRE());
			delegacion.setPermisos(delegacionJAXB.getPERMISOS());
			delegacion.setFechaInicio(delegacionJAXB.getFECHAINICIO());
			delegacion.setFechaFin(delegacionJAXB.getFECHAFIN());								
		}
		return delegacion;		
	}


	public String getDelegadoId() {
		return delegadoId;
	}


	public void setDelegadoId(String delegadoNif) {
		this.delegadoId = delegadoNif;
	}


	public String getDelegadoNombre() {
		return delegadoNombre;
	}


	public void setDelegadoNombre(String delegadoNombre) {
		this.delegadoNombre = delegadoNombre;
	}


	public String getEntidadId() {
		return entidadId;
	}


	public void setEntidadId(String deleganteNif) {
		this.entidadId = deleganteNif;
	}


	public String getEntidadNombre() {
		return entidadNombre;
	}


	public void setEntidadNombre(String deleganteNombre) {
		this.entidadNombre = deleganteNombre;
	}


	public String getFechaFin() {
		return fechaFin;
	}


	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}


	public String getFechaInicio() {
		return fechaInicio;
	}


	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}


	public String getPermisos() {
		return permisos;
	}


	public void setPermisos(String permisos) {
		this.permisos = permisos;
	}




}
