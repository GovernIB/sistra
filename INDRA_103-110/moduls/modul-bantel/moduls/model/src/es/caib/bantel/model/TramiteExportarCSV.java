package es.caib.bantel.model;

/**
 * Informacion de un tramite a exportar CSV.
 *
 */
public class TramiteExportarCSV {

	/**
	 * Ids procedimiento y tramite separados con separador @#@.
	 */
	private String idProcedimientoTramite;
	
	/**
	 * Descripcion tramite.
	 */
	private String descripcion;


	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getIdProcedimientoTramite() {
		return idProcedimientoTramite;
	}

	public void setIdProcedimientoTramite(String idProcedimientoTramite) {
		this.idProcedimientoTramite = idProcedimientoTramite;
	}

	
	
}
