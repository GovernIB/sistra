package es.caib.sistra.plugins.regtel;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * Indica el resultado de registrar un asiento: número de registro y fecha
 * 
 */
public class ResultadoRegistro implements Serializable{
	
	/**
	 * Número de registro, con el siguiente formato: X/N/AAAA. La longitud máxima del número de registro es 50 carácteres.
	 * <ul>
	 * <li>X: código de la unidad registral
	 * <li>N: número de registro 
	 * <li>AAAA: año de registro (4 carácteres)
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
	 * Número de registro, con el siguiente formato: X/N/AAAA. La longitud máxima del número de registro es 50 carácteres.
	 * <ul>
	 * <li>X: código de la unidad registral
	 * <li>N: número de registro 
	 * <li>AAAA: año de registro (4 carácteres)
	 * </ul>
	 */
	public String getNumeroRegistro() {
		return numeroRegistro;
	}

	/**
	 * Número de registro, con el siguiente formato: X/N/AAAA. La longitud máxima del número de registro es 50 carácteres.
	 * <ul>
	 * <li>X: código de la unidad registral
	 * <li>N: número de registro 
	 * <li>AAAA: año de registro (4 carácteres)
	 * </ul>
	 */
	public void setNumeroRegistro(String numeroRegistro) {
		this.numeroRegistro = numeroRegistro;
	}
	
}
