package es.caib.consola.controller.mensajes;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Window;

import es.caib.consola.model.Usuario;
import es.caib.consola.util.ConsolaUtil;
import es.caib.zkutils.zk.composer.BaseComposer;


/**
 * Controller ventana mensajes.
 */
@SuppressWarnings("serial")
public class MensajesController extends BaseComposer {

	/** Ventana configuracion. */
    private Window wMensajes;
    /** Componente padre. */
    private Component padre;
    /** Usuario autenticado. */
    private Usuario usuarioLogado;
    	
    /*
     * (non-Javadoc)
     * 
     * @see
     * es.gva.dgm.tra.util.zk.composer.BaseComposer#doAfterCompose(org.zkoss
     * .zk.ui.Component)
     */
    @Override
    public final void doAfterCompose(final Component compConfiguracion) {
        
    	super.doAfterCompose(compConfiguracion);

    	usuarioLogado = ConsolaUtil.recuperarUsuarioLogado();
    	padre = compConfiguracion.getParent();       
        
    }
    
    /**
     * Click cerrar configuracion.
     */
    public final void onClick$btnCerrar() {
    	// Cierra pantalla configuracion
        wMensajes.detach();
    }

}
