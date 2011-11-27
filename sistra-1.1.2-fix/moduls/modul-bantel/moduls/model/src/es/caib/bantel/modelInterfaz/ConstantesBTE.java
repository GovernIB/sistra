package es.caib.bantel.modelInterfaz;

public class ConstantesBTE {
	// Tipo Autenticacion
	/**
	 * Tipo de autenticacion mediante certificado digital
	 */
	public final static char AUTENTICACION_CERTIFICADO = 'C';
	/**
	 * Tipo de autenticacion mediante usuario y password
	 */
	public final static char AUTENTICACION_USUARIOPASSWORD = 'U';
	/**
	 * Tipo de autenticacion de forma an�nima
	 */	
	public final static char AUTENTICACION_ANONIMO = 'A';
	
	// Entrada procesada
	/**
	 * Entrada procesada correctamente por el BackOffice
	 */		
	public final static String ENTRADA_PROCESADA = "S";
	/**
	 * Entrada no ha sido procesada por el BackOffice
	 */
	public final static String ENTRADA_NO_PROCESADA = "N";
	/**
	 * Entrada procesada con error por el BackOffice
	 * 
	 */
	public final static String ENTRADA_PROCESADA_ERROR = "X";
	
	// Tipo de confirmacion preregistro/preenvio
	/**
	 * Entrada confirmada mediante el m�dulo de confirmaci�n de preregistros/preenvios en el registro presencial (circuito normal)
	 */		
	public final static String CONFIRMACIONPREREGISTRO_REGISTRO = "R";
	/**
	 * Entrada confirmada por el gestor: la documentaci�n no ha sido entregada en un punto de registro o bien porque en el registro presencial no se ha
	 * confirmado en el m�dulo de confirmaci�n de preregistros/preenvios.
	 */
	public final static String CONFIRMACIONPREREGISTRO_GESTOR = "G";
	/**
	 * Entrada que se confirmada autom�ticamente por la plataforma tras realizarse el preenvio (SOLO PARA PREENVIOS)
	 */
	public final static String CONFIRMACIONPREREGISTRO_AUTOMATICA = "A";
	/**
	 * Entrada que se ha confirmado autom�ticamente por la plataforma tras realizarse el preenvio y
	 * posteriormente ha sido confirmada en el m�dulo de confirmaci�n de preregistros/preenvios de 
	 * registro presencial (SOLO PARA PREENVIOS)
	 */
	public final static String CONFIRMACIONPREREGISTRO_AUTOMATICA_REGISTRO = "X";
	/**
	 * Marca que hay entre un error y otro de una integraci�n
	 */
	public final static String MARCA_ERROR = "\n -- Error en el aviso de entradas -- \n";
	
	
			
}
