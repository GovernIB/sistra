package es.caib.bantel.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class GestorBandeja implements Serializable
{
	private String seyconID;
	private String email;
	private Long intervaloInforme;
	private Set tramitesGestionados = new HashSet(0);
	private Date ultimoAviso;
	private char permitirCambioEstado = 'N';
	private char permitirCambioEstadoMasivo = 'N';
	
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
	public Set getTramitesGestionados()
	{
		return tramitesGestionados;
	}
	public void setTramitesGestionados(Set tramitesGestionados)
	{
		this.tramitesGestionados = tramitesGestionados;
	}
	public Long getIntervaloInforme() {
		return intervaloInforme;
	}
	public void setIntervaloInforme(Long intervaloInforme) {
		this.intervaloInforme = intervaloInforme;
	}
	
	// FUNCIONES PARA GESTIONAR TRAMITES GESTIONADOS
	public void addTramiteGestionado( Tramite tramite )
	{	
		tramite.getGestores().add(this);		
		this.tramitesGestionados.add( tramite );				
	}
	
	public void removeTramiteGestionado( Tramite tramite )
	{
		tramite.getGestores().remove(this);
		this.tramitesGestionados.remove( tramite );		
	}
	
	public void removeTramitesGestionados()
	{		
		// Eliminamos referencias en Tramite
		for (Iterator it=this.tramitesGestionados.iterator();it.hasNext();){
			 Tramite tramite = (Tramite) it.next();
			 tramite.getGestores().remove(this);
		}
		
		// Eliminamos tramites gestionados		
		this.tramitesGestionados.clear();		
	}
	public Date getUltimoAviso() {
		return ultimoAviso;
	}
	public void setUltimoAviso(Date ultimoAviso) {
		this.ultimoAviso = ultimoAviso;
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
}
