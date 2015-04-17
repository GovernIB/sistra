/*
 * 
 */
package es.caib.consola.controller.gestor.wmodal;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Include;
import org.zkoss.zul.Window;

import es.caib.consola.ConstantesWEB;
import es.caib.consola.util.ConsolaUtil;
import es.caib.zkutils.zk.composer.BaseComposer;

/**
 * Class AyudaWModal.
 */

@SuppressWarnings("serial")
public class AyudaWModal extends BaseComposer {

    /**
     * Atributo contenidohtml de AyudaWModal.
     */
    private Include contenidohtml;

    /**
     * Ventana actual;
     */
    private Window wAyuda;
    
    /*
     * (non-Javadoc)
     * 
     * @see
     * es.gva.dgm.tra.util.zk.composer.BaseComposer#doAfterCompose(org.zkoss
     * .zk.ui.Component)
     */

    @Override
    public final void doAfterCompose(final Component compAyuda) {
        
    	super.doAfterCompose(compAyuda);
    	
        final String src = (String) arg.get(ConstantesWEB.SRC);

        if (src != null) {
        	contenidohtml.setSrc("ayuda/windows/" + ConsolaUtil.recuperarUsuarioLogado().getIdioma() + "/" + src + ".html");
        }
    }
    
    /**
     * Click boton Cerrar.
     */
    public final void onClick$btnCerrar() {
    	wAyuda.detach();
    }

}
