package es.caib.sistra.persistence.plugins;

/**
 * Procedimiento destino del tramite  
 */
public class ProcedimientoDestinoTramite {
	
	private boolean calculado;

	private String procedimiento;
	
	public void setCalculado(boolean calculado) {
		this.calculado = calculado;
	}

	public String getProcedimiento() {
		return procedimiento;
	}

	public void setProcedimiento(String procedimiento) {
		this.procedimiento = procedimiento;
	}
	
	
}
