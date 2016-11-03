package es.caib.sistra.plugins.regtel;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * Indica el resultado de registrar un asiento: n�mero de registro y fecha
 * 
 */
public class ResultadoRegistro implements Serializable{
	
	/**
	 * N�mero de registro, con el siguiente formato: X/N/AAAA. La longitud m�xima del n�mero de registro es 50 car�cteres.
	 * <ul>
	 * <li>X: c�digo de la unidad registral
	 * <li>N: n�mero de registro 
	 * <li>AAAA: a�o de registro (4 car�cteres)
	 * </ul> 
	 */
	private String numeroRegistro;
	
	/**
	 * Fecha de registro
	 */
	private Date fechaRegistro;
	
	/**
	 * Fecha de registro
	 */
	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	/**
	 * Fecha de registro
	 */
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	/**
	 * N�mero de registro, con el siguiente formato: X/N/AAAA. La longitud m�xima del n�mero de registro es 50 car�cteres.
	 * <ul>
	 * <li>X: c�digo de la unidad registral
	 * <li>N: n�mero de registro 
	 * <li>AAAA: a�o de registro (4 car�cteres)
	 * </ul>
	 */
	public String getNumeroRegistro() {
		return numeroRegistro;
	}

	/**
	 * N�mero de registro, con el siguiente formato: X/N/AAAA. La longitud m�xima del n�mero de registro es 50 car�cteres.
	 * <ul>
	 * <li>X: c�digo de la unidad registral
	 * <li>N: n�mero de registro 
	 * <li>AAAA: a�o de registro (4 car�cteres)
	 * </ul>
	 */
	public void setNumeroRegistro(String numeroRegistro) {
		this.numeroRegistro = numeroRegistro;
	}
	
}
