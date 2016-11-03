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
public class PermisoException extends DelegateException {
	


	/**
	 * @param cause
	 */
	public PermisoException(Throwable cause) {
		super(cause);
	}
	

}
