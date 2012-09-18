package es.caib.mobtratel.persistence.delegate;

/**
 * Excepción producida en la capa delegate.
 */
public class DelegateException extends Exception {

    public DelegateException(Throwable cause) {
        super(cause.getMessage());
    }
    
}
