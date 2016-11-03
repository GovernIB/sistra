/**
 * 
 */
package es.caib.mobtratel.persistence.delegate;

import es.caib.mobtratel.persistence.delegate.DelegateException;

/**
 * Excepcion lanzada por cualquier error genérico al 
 * almacenar el MensajeEmail o MensajeSMS
 *
 */
public class MobilidadException extends DelegateException {
		
	/**
	 * @param cause
	 */
	public MobilidadException(Throwable cause) {
		super(cause);
	}
	

}
