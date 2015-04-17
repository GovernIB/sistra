/**
 * 
 * @author Indra
 */
package es.caib.zkutils.zk.exceptions;

/**
 * Class AfterComposeException.
 */
public class AfterComposeException extends RuntimeException {

    /**
     * Atributo constante serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Instancia un nuevo after compose exception de AfterComposeException.
     * 
     * @param msg
     *            Parámetro msg
     * @param ex
     *            Parámetro ex
     */
    public AfterComposeException(final String msg, final Throwable ex) {
        super(msg, ex);
    }
}
