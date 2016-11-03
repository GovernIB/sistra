/**
 * 
 * @author Indra
 */
package es.caib.zkutils.zk.exceptions;

/**
 * Class ShowMessageException.
 */
public class ShowMessageException extends RuntimeException {

    /**
     * Atributo constante serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Instancia un nuevo show message exception de ShowMessageException.
     * 
     * @param msg
     *            Par�metro msg
     * @param ex
     *            Par�metro ex
     */
    public ShowMessageException(final String msg, final Throwable ex) {
        super(msg, ex);
    }
}
