package es.caib.redose.model;

import java.util.Date;

/**
 * Parametros migracion
 * 
 * @author rsanz
 *
 */
public class MigracionParametros {

	/**
	 * Ubicacion origen.
	 */
	private Long ubicacionOrigen;	
	
	/**
	 * Ubicacion destino.
	 */
	private Long ubicacionDestino;	
	
	/**
	 * Fecha desde.
	 */
	private Date fechaDesde;
	
	/**
	 * Fecha desde.
	 */
	private Date fechaHasta;
	
	/**
	 * Numero de docs x iteracion.
	 */
	private int numDocsIteracion;
	
	/**
	 * Timeout entre iteraciones.
	 */
	private int timeoutIteracion;
	
	/**
	 * Numero maximo de documentos a migrar.
	 */
	private int numMaxDocs;
	
	/**
	 * Numero maximo de errores.
	 */
	private int numMaxErrores;
	
	/**
	 * Indica si se borra documento de ubicacion origen.
	 */
	private boolean borrarUbicacionOrigen;

	public Long getUbicacionOrigen() {
		return ubicacionOrigen;
	}

	public void setUbicacionOrigen(Long ubicacionOrigen) {
		this.ubicacionOrigen = ubicacionOrigen;
	}

	public Long getUbicacionDestino() {
		return ubicacionDestino;
	}

	public void setUbicacionDestino(Long ubicacionDestino) {
		this.ubicacionDestino = ubicacionDestino;
	}

	public Date getFechaDesde() {
		return fechaDesde;
	}

	public void setFechaDesde(Date fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	public Date getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(Date fechaHasta) {
		this.fechaHasta = fechaHasta;
	}

	public int getNumDocsIteracion() {
		return numDocsIteracion;
	}

	public void setNumDocsIteracion(int numDocsIteracion) {
		this.numDocsIteracion = numDocsIteracion;
	}

	public int getTimeoutIteracion() {
		return timeoutIteracion;
	}

	public void setTimeoutIteracion(int timeoutIteracion) {
		this.timeoutIteracion = timeoutIteracion;
	}

	public int getNumMaxDocs() {
		return numMaxDocs;
	}

	public void setNumMaxDocs(int numMaxDocs) {
		this.numMaxDocs = numMaxDocs;
	}

	public int getNumMaxErrores() {
		return numMaxErrores;
	}

	public void setNumMaxErrores(int numMaxErrores) {
		this.numMaxErrores = numMaxErrores;
	}

	public boolean isBorrarUbicacionOrigen() {
		return borrarUbicacionOrigen;
	}

	public void setBorrarUbicacionOrigen(boolean borrarUbicacionOrigen) {
		this.borrarUbicacionOrigen = borrarUbicacionOrigen;
	}
	
}
