package es.caib.sistra.model;

import java.io.Serializable;
import java.util.Date;


/**
 * 
 * Permite modificar la configuracion del tramite de una forma dinamica al iniciar un tramite
 *
 */
public class ConfiguracionTramiteDinamica implements Serializable{
		
	/**	 
	 Plazo dinamico: permite indicar dinamicamente un plazo de presentación
	 */
	private boolean plazoDinamico=false;
	private Date plazoInicio;
	private Date plazoFin;
	
	public Date getPlazoFin() {
		return plazoFin;
	}
	public void setPlazoFin(Date fechaFin) {
		this.plazoFin = fechaFin;
	}
	public Date getPlazoInicio() {
		return plazoInicio;
	}
	public void setPlazoInicio(Date fechaInicio) {
		this.plazoInicio = fechaInicio;
	}
	public boolean isPlazoDinamico() {
		return plazoDinamico;
	}
	public void setPlazoDinamico(boolean plazoDinamico) {
		this.plazoDinamico = plazoDinamico;
	}
	
}
