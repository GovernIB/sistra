 package es.caib.sistra.plugins.login;

import java.util.List;

/**
 * Evidencias autenticacion.
 *
 * @author Indra
 *
 */
public class EvidenciasAutenticacion {

	/** Lista evidencias. */
	private List<PropiedadAutenticacion> evidencias;

	/** Huella electrónica. */
	private String huellaElectronica;

	/**
	 *	Devuelve evidencias.
	 * @return evidencias
	 */
	public List<PropiedadAutenticacion> getEvidencias() {
		return evidencias;
	}

	/**
	 * Establece evidencias.
	 * @param evidencias evidencias
	 */
	public void setEvidencias(List<PropiedadAutenticacion> evidencias) {
		this.evidencias = evidencias;
	}

	/**
	 *	Devuelve huellaElectronica.
	 * @return huellaElectronica
	 */
	public String getHuellaElectronica() {
		return huellaElectronica;
	}

	/**
	 * Establece huellaElectronica.
	 * @param huellaElectronica huellaElectronica
	 */
	public void setHuellaElectronica(String huellaElectronica) {
		this.huellaElectronica = huellaElectronica;
	}



}
