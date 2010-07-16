/**
 * 
 */
package es.caib.mobtratel.persistence.delegate;

import es.caib.mobtratel.persistence.delegate.DelegateException;

/**
 * Excepcion lanzada por ante el intento de almacenar 
 * un MensajeEmail o MensajeSMS en el que el invocador no tenga 
 * permiso sobre la cuenta que viene configurada en el Mensaje
 * 
 *
 */
public class FormatoException extends DelegateException {
	


	/**
	 * @param cause
	 */
	public FormatoException(Throwable cause) {
		super(cause);
	}
	

}
