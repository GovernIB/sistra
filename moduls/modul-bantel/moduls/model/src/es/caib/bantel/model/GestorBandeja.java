package es.caib.bantel.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class GestorBandeja implements Serializable
{
	private String seyconID;
	private String email;
	private String avisarEntradas = "N";
	private String avisarNotificaciones = "N";
	private String avisarMonitorizacion = "N";
	private char permitirCambioEstado = 'N';
	private char permitirCambioEstadoMasivo = 'N';
	private char permitirGestionExpedientes = 'N';
	private Set procedimientosGestionados = new HashSet(0);
	
	
	public String getEmail()
	{
		return email;
	}
	public void setEmail(String email)
	{
		this.email = email;
	}
	public String getSeyconID()
	{
		return seyconID;
	}
	public void setSeyconID(String seyconID)
	{
		this.seyconID = seyconID;
	}
	public Set getProcedimientosGestionados()
	{
		return procedimientosGestionados;
	}
	public void setProcedimientosGestionados(Set tramitesGestionados)
	{
		this.procedimientosGestionados = tramitesGestionados;
	}
	
	// FUNCIONES PARA GESTIONAR TRAMITES GESTIONADOS
	public void addTramiteGestionado( Procedimiento tramite )
	{	
		tramite.getGestores().add(this);		
		this.procedimientosGestionados.add( tramite );				
	}
	
	public void removeTramiteGestionado( Procedimiento tramite )
	{
		tramite.getGestores().remove(this);
		this.procedimientosGestionados.remove( tramite );		
	}
	
	public void removeTramitesGestionados()
	{		
		// Eliminamos referencias en Tramite
		for (Iterator it=this.procedimientosGestionados.iterator();it.hasNext();){
			 Procedimiento tramite = (Procedimiento) it.next();
			 tramite.getGestores().remove(this);
		}
		
		// Eliminamos tramites gestionados		
		this.procedimientosGestionados.clear();		
	}
	
	public char getPermitirCambioEstado() {
		return permitirCambioEstado;
	}
	public void setPermitirCambioEstado(char permitirCambioEstado) {
		this.permitirCambioEstado = permitirCambioEstado;
	}
	public char getPermitirCambioEstadoMasivo() {
		return permitirCambioEstadoMasivo;
	}
	public void setPermitirCambioEstadoMasivo(char permitirCambioEstadoMasivo) {
		this.permitirCambioEstadoMasivo = permitirCambioEstadoMasivo;
	}
	public char getPermitirGestionExpedientes() {
		return permitirGestionExpedientes;
	}
	public void setPermitirGestionExpedientes(char permitirGestionExpedientes) {
		this.permitirGestionExpedientes = permitirGestionExpedientes;
	}
	public String getAvisarNotificaciones() {
		return avisarNotificaciones;
	}
	public void setAvisarNotificaciones(String avisarNotificaciones) {
		this.avisarNotificaciones = avisarNotificaciones;
	}
	public String getAvisarEntradas() {
		return avisarEntradas;
	}
	public void setAvisarEntradas(String avisarEntradas) {
		this.avisarEntradas = avisarEntradas;
	}
	public String getAvisarMonitorizacion() {
		return avisarMonitorizacion;
	}
	public void setAvisarMonitorizacion(String avisarMonitorizacion) {
		this.avisarMonitorizacion = avisarMonitorizacion;
	}
}
