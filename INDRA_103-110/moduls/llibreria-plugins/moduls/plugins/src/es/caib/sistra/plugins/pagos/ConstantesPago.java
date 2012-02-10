package es.caib.sistra.plugins.pagos;

/**
 * Constantes interfaz de pagos
 *
 */
public class ConstantesPago {

	// TIPOS DE PAGOS
	/**
	 * Tipo de pago: tipo de pago telem�tico
	 */
	public final static char TIPOPAGO_TELEMATICO = 'T';
	/**
	 * Tipo de pago: tipo de pago presencial
	 */
	public final static char TIPOPAGO_PRESENCIAL = 'P';
	/**
	 * Tipo de pago: tipo de pago telem�tico y presencial
	 */
	public final static char TIPOPAGO_AMBOS = 'A';
		
	// SESION DE PAGOS
	/**
	 * No existe sesi�n de pagos para un determinado localizador de sesi�n
	 */
	public final static int SESIONPAGO_NO_EXISTE_SESION = 0;
	/**
	 * Sesi�n de pagos en curso. Se deber� retomar la sesi�n de pagos existente.
	 */
	public final static int SESIONPAGO_EN_CURSO = 1;
	/**
	 * Sesi�n de pagos finalizada con pago confirmado
	 */
	public final static int SESIONPAGO_PAGO_CONFIRMADO = 2;
	/**
	 * Sesi�n de pagos pendiente de confirmaci�n (en espera de que la pasarela de pagos indique si se ha realizado el pago)
	 */
	public final static int SESIONPAGO_PAGO_PENDIENTE_CONFIRMAR = 3;
	
}
