package es.caib.xml.justificantepago.factoria.impl;

import es.caib.xml.EstablecerPropiedadException;
import es.caib.xml.NodoBase;

public class JustificantePago extends NodoBase {
	private DatosPago datosPago;
	private String firma;

	JustificantePago() {}

	public DatosPago getDatosPago() {
		return datosPago;
	}

	public void setDatosPago(DatosPago datosPago) throws EstablecerPropiedadException {
		validaCampoObligatorio ("JustificantePago", "DatosPago", datosPago);
		this.datosPago = datosPago;
	}

	public String getFirma() {
		return firma;
	}

	public void setFirma(String firma) throws EstablecerPropiedadException {
		validaCampoObligatorio ("JustificantePago", "Firma", firma);
		this.firma = firma;
	}
	
	public void comprobarDatosRequeridos() throws EstablecerPropiedadException {
		validaCampoObligatorio ("JustificantePago", "Firma", firma);
		validaCampoObligatorio ("JustificantePago", "DatosPago", getDatosPago ());
		
		getDatosPago ().comprobarDatosRequeridos();		
	}

}
