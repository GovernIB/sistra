package es.caib.sistra.plugins.pagos;

/**
 * Constantes interfaz de pagos
 *
 */
public class ConstantesPago {

	// TIPOS DE PAGOS
	/**
	 * Tipo de pago: tipo de pago telemático
	 */
	public final static char TIPOPAGO_TELEMATICO = 'T';
	/**
	 * Tipo de pago: tipo de pago presencial
	 */
	public final static char TIPOPAGO_PRESENCIAL = 'P';
	/**
	 * Tipo de pago: tipo de pago telemático y presencial
	 */
	public final static char TIPOPAGO_AMBOS = 'A';
		
	// SESION DE PAGOS
	/**
	 * No existe sesión de pagos para un determinado localizador de sesión
	 */
	public final static int SESIONPAGO_NO_EXISTE_SESION = 0;
	/**
	 * Sesión de pagos en curso. Se deberá retomar la sesión de pagos existente.
	 */
	public final static int SESIONPAGO_EN_CURSO = 1;
	/**
	 * Sesión de pagos finalizada con pago confirmado
	 */
	public final static int SESIONPAGO_PAGO_CONFIRMADO = 2;
	/**
	 * Sesión de pagos pendiente de confirmación (en espera de que la pasarela de pagos indique si se ha realizado el pago)
	 */
	public final static int SESIONPAGO_PAGO_PENDIENTE_CONFIRMAR = 3;
	
}
