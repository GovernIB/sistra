package es.caib.sistra.persistence.util;

/**
 * Resultado script de tramitacion
 *
 */
public class ResultadoScript {
	/**
	 * Resultado
	 */
	private String resultado;
	/**
	 * Código error personalizado
	 */
	private String errorPersonalizado;
	public String getErrorPersonalizado() {
		return errorPersonalizado;
	}
	public void setErrorPersonalizado(String errorPersonalizado) {
		this.errorPersonalizado = errorPersonalizado;
	}
	public String getResultado() {
		return resultado;
	}
	public void setResultado(String resultado) {
		this.resultado = resultado;
	}
	
	
}
