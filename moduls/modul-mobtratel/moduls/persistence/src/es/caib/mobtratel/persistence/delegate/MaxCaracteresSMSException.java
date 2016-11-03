/**
 * 
 */
package es.caib.mobtratel.persistence.delegate;

import es.caib.mobtratel.persistence.delegate.DelegateException;

/**
 * Excepcion lanzada al intentar introducir un SMS
 * que supera el m�ximo tama�o permitido 
 * 
 *
 */
public class MaxCaracteresSMSException extends DelegateException {
	


	/**
	 * @param cause
	 */
	public MaxCaracteresSMSException(Throwable cause) {
		super(cause);
	}
	

}
