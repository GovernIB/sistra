/**
 * 
 * @author Indra
 */
package es.caib.zkutils.zk.exceptions;

/**
 * Class AbreVentanaModalException.
 */
public class AbreVentanaModalException extends RuntimeException {

    /**
     * Atributo constante serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Instancia un nuevo abre ventana modal exception de
     * AbreVentanaModalException.
     * 
     * @param msg
     *            Par�metro msg
     * @param ex
     *            Par�metro ex
     */
    public AbreVentanaModalException(final String msg, final Throwable ex) {
        super(msg, ex);
    }
}
