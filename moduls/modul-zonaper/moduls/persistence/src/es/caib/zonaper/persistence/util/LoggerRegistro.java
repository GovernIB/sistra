package es.caib.zonaper.persistence.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *	
 * 	Realiza un log de los registros de entrada que se han completado
 *  y de los que se debe haber hecho rollback
 * 
 */ 
public class LoggerRegistro
{
	private static Log log = LogFactory.getLog(LoggerRegistro.class);
	
	/**
     * 	Realiza un log de los registros de entrada que se han completado
     *  y de los que se debe haber hecho rollback
     * 
     * @param numeroRegistro N� de registro
     * @param fechaRegistro Fecha de registro
     * @param overallCommit Indica el �xito o fracaso de la transacci�n global
     * 
     * Si se da la siguiente situaci�n:
	 * 1� Existe una l�nea de log que indica que se ha realizado un ROLLBACK en el resultado global para un n� de registro,
	 * 2� Existe una 'row' en la tabla del sistema de registro para ese mismo n�
	 * 3� No existe adem�s una l�nea de log COMMIT para ese n� de registro ( es decir, ninguna otra transacci�n global 
	 * ha reusado el n� de registro )
	 * 
	 * Significa que la transacci�n global ha fallado pero no se ha realizado rollback 
	 * sobre la tabla del sistema de registro,
	 * y por tanto se DEBE eliminar dicha 'row' de la tabla. 
     * 
     */
	public static void logResultadoRegistro( String numeroRegistro, String fechaRegistro, boolean overallCommit )
	{
    	log.info( numeroRegistro + " " + fechaRegistro + " " + ( overallCommit ? "COMMIT" : "ROLLBACK" ) );
	}
}
