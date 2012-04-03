package es.caib.mobtratel.modelInterfaz;

public class ConstantesMobtratel {

	// ESTADO ENVIO
	public static int ESTADOENVIO_PENDIENTE = 0;
	public static int ESTADOENVIO_ENVIADO = 1;
	public static int ESTADOENVIO_ERROR = 2;
	public static int ESTADOENVIO_CANCELADO = 3;
	public static int ESTADOENVIO_PROCESANDO = 4;
	
	// CONFIRMACION ENVIO
	/**
	 * Indica que la confirmacion de envio esta pendiente
	 */
	public static int CONFIRMACION_PENDIENTE = 0;
	/**
	 * Indica que se ha confirmado que el envio se ha realizado
	 */
	public static int CONFIRMACION_OK = 1;
	/**
	 * Indica que se ha confirmado que el envio no se ha realizado 
	 */
	public static int CONFIRMACION_KO = -1;	
	
}
