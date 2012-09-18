package es.caib.zonaper.front.util;


import es.caib.zonaper.model.Delegacion;

/**
 * 
 * Delegaciones de una entidad mas el nombre de delegado
 *
 */
public class DelegacionFront extends Delegacion{	
	
	private String nombreDelegado;

	public String getNombreDelegado() {
		return nombreDelegado;
	}

	public void setNombreDelegado(String nombreDelegado) {
		this.nombreDelegado = nombreDelegado;
	}
	
	
	
}
