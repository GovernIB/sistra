package es.caib.sistra.persistence.plugins;

import java.util.regex.Pattern;


/**
 * Plugin que permite establecer propiedades del tramite de forma dinamica
 * 
 */
public class ConfiguracionDinamicaTramitePlugin {
	
	/**
	 * Plazo dinamico: permite indicar dinamicamente un plazo de presentación
	 * Las fechas deben ir en formato YYYYMMDDHHMISS
	 */
	private boolean plazoDinamico = false;
	private String plazoInicio=null;
	private String plazoFin=null;
	private final String FORMATOFECHA="[0-9]{14}";
	
	public String getPlazoFin() {
		return plazoFin;
	}
	public void setPlazoFin(String fechaFin) throws Exception{
		if (!Pattern.matches(FORMATOFECHA, fechaFin)) {
			throw new Exception("Formato de fecha plazo fin no válido. Debe ser YYYYMMDDHHMISS y es " + fechaFin );
		}
		this.plazoFin = fechaFin;
	}
	public String getPlazoInicio() {
		return plazoInicio;
	}
	public void setPlazoInicio(String fechaInicio) throws Exception{
		if ( !Pattern.matches(FORMATOFECHA, fechaInicio)) {
			throw new Exception("Formato de fecha plazo inicio no válido. Debe ser YYYYMMDDHHMISS y es " + fechaInicio );
		}
		this.plazoInicio = fechaInicio;
	}	
	public boolean isPlazoDinamico() {
		return plazoDinamico;
	}
	public void setPlazoDinamico(boolean plazoDinamico) {
		this.plazoDinamico = plazoDinamico;
	}		
}
