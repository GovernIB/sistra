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
     * @param numeroRegistro Nº de registro
     * @param fechaRegistro Fecha de registro
     * @param overallCommit Indica el éxito o fracaso de la transacción global
     * 
     * Si se da la siguiente situación:
	 * 1º Existe una línea de log que indica que se ha realizado un ROLLBACK en el resultado global para un nº de registro,
	 * 2º Existe una 'row' en la tabla del sistema de registro para ese mismo nº
	 * 3º No existe además una línea de log COMMIT para ese nº de registro ( es decir, ninguna otra transacción global 
	 * ha reusado el nº de registro )
	 * 
	 * Significa que la transacción global ha fallado pero no se ha realizado rollback 
	 * sobre la tabla del sistema de registro,
	 * y por tanto se DEBE eliminar dicha 'row' de la tabla. 
     * 
     */
	public static void logResultadoRegistro( String numeroRegistro, String fechaRegistro, boolean overallCommit )
	{
    	log.info( numeroRegistro + " " + fechaRegistro + " " + ( overallCommit ? "COMMIT" : "ROLLBACK" ) );
	}
}
