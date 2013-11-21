/**
 * 
 */
package es.caib.mobtratel.persistence.delegate;

import es.caib.mobtratel.persistence.delegate.DelegateException;

/**
 * Excepcion lanzada por ante el intento de almacenar 
 * un MensajeEmail o MensajeSMS cuyo numero de destinatarios
 * exceda el limite
 *
 */
public class LimiteDestinatariosException extends DelegateException {
	


	/**
	 * @param cause
	 */
	public LimiteDestinatariosException(Throwable cause) {
		super(cause);
	}
	

}
