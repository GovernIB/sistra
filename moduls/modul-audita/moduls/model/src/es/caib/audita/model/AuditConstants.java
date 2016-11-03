package es.caib.audita.model;

public class AuditConstants
{
	/**
	 * Indica que se quiere visualizar el numero de eventos del evento en question
	 */
	public static String TOTAL 				= "T";
	
	/**
	 * Indica que se quiere visualizar el total por idioma
	 */
	public static String TOTAL_IDIOMAS 		= "I";
	
	/**
	 * Indica que se quiere visualizar el total por Nivel de Autenticacion
	 */
	public static String TOTAL_NIVEL_AUTH 	= "N";
	
	/**
	 * Indica que se quiere mostrar el Detalle del evento en question
	 */
	public static String DETALLE 			= "D";
	
	/**
	 * Indica que se quiere mostrar un gr�fico del evento en question
	 */
	public static String GRAFICO 			= "G";
	
	/**
	 * Indica que el evento en question tiene Visualizacion particular
	 */
	public static String PARTICULARIDADES_VISUALIZACION = "X";
	
	
	/**
	 * Indica que el modo de autenticacion es An�nimo
	 */
	public static char MODO_AUTENTICACION_ANONIMO = 'A';
	
	/**
	 * Indica que el modo de autenticaci�n es por Usuario
	 */
	public static char MODO_AUTENTICACION_USUARIO = 'U';
	
	/**
	 * Indica que el modo de autenticaci�n es por Certificado
	 */
	public static char MODO_AUTENTICACION_CERTIFICADO = 'C';
	
	
	/**
	 * Indica que se est� en la visualizacion en modo Anual
	 */
	public static String ANUAL = "ANUAL";

	/**
	 * Indica que se est� en la visualizacion en modo Mensual
	 */

	public static String MENSUAL = "MENSUAL";
	
	/**
	 * Indica que se est� en la visualizacion en modo Diario
	 */

	public static String DIARIO = "DIARIO";
	
	/**
	 * Indica que el evento en question se ha enviado a Registro
	 */

	public static String REGISTRO 	= "R";

	/**
	 * Indica que el evento en question se ha enviado a Bandeja Telematica
	 */

	public static String BANDEJA 	= "B";

	/**
	 * Indica que el evento en question se ha enviado a Consulta
	 */

	public static String CONSULTA 	= "C";
	
	/**
	 * Indica que el evento en question se ha enviado a Asistente
	 */

	public static String ASISTENTE 	= "A";

	/**
	 * Indica que el evento en question se ha enviado a Consulta
	 */

	public static String TELEMATICO 	= "T";

	/**
	 * Indica que el evento en question se ha enviado a Consulta
	 */

	public static String PRESENCIAL 	= "P";

}
