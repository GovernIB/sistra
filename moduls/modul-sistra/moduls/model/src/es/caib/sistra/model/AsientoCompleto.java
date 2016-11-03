package es.caib.sistra.model;

import java.io.Serializable;


/**
 *	Contiene asiento/justificante + datos propios 
 *
 */
public class AsientoCompleto implements Serializable {
	private String justificante;
	private String datosPropios;
	public String getDatosPropios() {
		return datosPropios;
	}
	public void setDatosPropios(String datosPropios) {
		this.datosPropios = datosPropios;
	}
	public String getAsiento() {
		return justificante;
	}
	public void setAsiento(String asiento) {
		this.justificante = asiento;
	}
}
