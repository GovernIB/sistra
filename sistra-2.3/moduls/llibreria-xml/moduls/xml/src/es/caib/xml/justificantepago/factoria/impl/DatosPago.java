package es.caib.xml.justificantepago.factoria.impl;

import java.util.Date;

import es.caib.xml.EstablecerPropiedadException;
import es.caib.xml.NodoBase;

public class DatosPago extends NodoBase {
	
	private String localizador;
	private String dui;
	private String estado;
	private Date fechaPago;
	
	DatosPago() {}

	public String getDui() {
		return dui;
	}

	public void setDui(String dui) throws EstablecerPropiedadException {
		validaCampoObligatorio ("DatosPago", "Dui", dui);
		this.dui = dui;
	}

	public Date getFechaPago() {
		return fechaPago;
	}

	public void setFechaPago(Date fechaPago) throws EstablecerPropiedadException {
		validaCampoObligatorio ("DatosPago", "FechaPago", fechaPago);
		this.fechaPago = fechaPago;
	}

	public String getLocalizador() {
		return localizador;
	}

	public void setLocalizador(String localizador) throws EstablecerPropiedadException {
		validaCampoObligatorio ("DatosPago", "Localizador", localizador);
		this.localizador = localizador;
	}
	
	public void comprobarDatosRequeridos() throws EstablecerPropiedadException {
		validaCampoObligatorio ("DatosPago", "Dui", getDui ());
		validaCampoObligatorio ("DatosPago", "FechaPago", getFechaPago ());
		validaCampoObligatorio ("DatosPago", "Localizador", getLocalizador ());		
		validaCampoObligatorio ("DatosPago", "Estado", getEstado());
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
}
