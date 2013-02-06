package es.caib.sistra.persistence.ejb;

import es.caib.regtel.model.ResultadoRegistroTelematico;

/**
 * Excepción en proceso de registro telemático
 * Sirve para controlar el commit/roolback de registro
 *
 */

public class RegistroTelematicoException extends ProcessorException
{
	private ResultadoRegistroTelematico resultadoRegistro = null;
	
	/**
	 * 
	 * @param message
	 * @param cause
	 * @param resultadoRegistro
	 */
	public RegistroTelematicoException( String message, String codError, Throwable cause, ResultadoRegistroTelematico resultadoRegistro )
	{
		super( message, codError, cause );
		this.resultadoRegistro = resultadoRegistro;
	}
	
	/**
	 * 
	 * @param message
	 * @param codError
	 */
	public RegistroTelematicoException( String message, String codError )
	{
		super( message, codError );
	}
	
	/**
	 * 
	 * @param message
	 * @param codError
	 * @param cause
	 */
	public RegistroTelematicoException( String message, String codError, Throwable cause )
	{
		super( message, codError, cause );
	}

	/**
	 * @return Returns the resultadoRegistro.
	 */
	public ResultadoRegistroTelematico getResultadoRegistroTelematico()
	{
		return resultadoRegistro;
	}
	
	public boolean isRegistroTelematicoEfectuado()
	{
		return resultadoRegistro != null;
	}

}
