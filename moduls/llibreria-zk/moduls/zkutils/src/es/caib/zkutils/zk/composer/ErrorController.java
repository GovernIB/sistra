/**
 * 
 * @author Indra
 */
package es.caib.zkutils.zk.composer;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Label;
import org.zkoss.zul.Window;

/**
 * Class ErrorController.
 */
@SuppressWarnings("serial")
public final class ErrorController extends BaseComposer {

	/**
	 * Ventana actual.
	 */
	private Window wError;
	
    /**
     * Atributo texto excepcion.
     */
    private Label textoExcepcion;

    /* (non-Javadoc)
     * @see es.gva.dgm.tra.util.zk.composer.BaseComposer#doAfterCompose(org.zkoss.zk.ui.Component)
     */
    @Override
    public void doAfterCompose(final Component comp) {

        // ErrorController
        super.doAfterCompose(comp);
        final String name = requestScope.get("javax.servlet.error.exception")
                .getClass().getName();
        final int idx = name.lastIndexOf(".");
        String texto = Labels.getLabel("excepcion"
                + name.substring(idx, name.length()));
        if (texto == null) {
            texto = (String) requestScope.get("javax.servlet.error.message");
        }
        textoExcepcion.setValue(texto);

    }
    

    /**
     * Click boton Aceptar.
     */
    public final void onClick$btnAceptar() {
    	wError.detach();
    }
}
