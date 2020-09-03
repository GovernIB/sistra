package es.caib.zonaper.modelInterfaz;

import java.util.Date;
import java.util.List;

public class FiltroBusquedaElementosExpedientePAD {

	private String nif;
    private List tipos;
    private Date fechaInicio;
    private Date fechaFin;
    private String idioma;

	/**
	 *	Devuelve nif.
	 * @return nif
	 */
	public String getNif() {
		return nif;
	}
	/**
	 * Establece nif.
	 * @param nif nif
	 */
	public void setNif(String nif) {
		this.nif = nif;
	}
	/**
	 *	Devuelve tipos.
	 * @return tipos
	 */
	public List getTipos() {
		return tipos;
	}
	/**
	 * Establece tipos.
	 * @param tipos tipos
	 */
	public void setTipos(List tipos) {
		this.tipos = tipos;
	}
	/**
	 *	Devuelve fechaInicio.
	 * @return fechaInicio
	 */
	public Date getFechaInicio() {
		return fechaInicio;
	}
	/**
	 * Establece fechaInicio.
	 * @param fechaInicio fechaInicio
	 */
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	/**
	 *	Devuelve fechaFin.
	 * @return fechaFin
	 */
	public Date getFechaFin() {
		return fechaFin;
	}
	/**
	 * Establece fechaFin.
	 * @param fechaFin fechaFin
	 */
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	/**
	 *	Devuelve idioma.
	 * @return idioma
	 */
	public String getIdioma() {
		return idioma;
	}
	/**
	 * Establece idioma.
	 * @param idioma idioma
	 */
	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

}
