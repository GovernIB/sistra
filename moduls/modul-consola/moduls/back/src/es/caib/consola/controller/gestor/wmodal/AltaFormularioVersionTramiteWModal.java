package es.caib.consola.controller.gestor.wmodal;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Window;

import es.caib.consola.ConstantesWEB;
import es.caib.consola.controller.gestor.VersionTramiteController;
import es.caib.consola.model.Usuario;
import es.caib.consola.util.ConsolaUtil;
import es.caib.zkutils.zk.composer.BaseComposer;

/**
 * Alta formulario de paso rellenar version tramite.
 */
@SuppressWarnings("serial")
public class AltaFormularioVersionTramiteWModal extends BaseComposer {
	
	// Instancia
    /** Ventana. */
    private Window wNuevoFormularioTramiteVersion;
    /** Ventana version tramite. */
    private VersionTramiteController versionTramiteController;
    /** Usuario autenticado. */
    private Usuario usuarioLogado;    	   
        	          
          
    @Override
    public final void doAfterCompose(final Component compPasoRellenar) {
    	
        super.doAfterCompose(compPasoRellenar);
        
        // Obtenemos referencia a pantalla version tramite
        versionTramiteController = (VersionTramiteController) arg.get(ConstantesWEB.VERSIONTRAMITE_CONTROLLER); 
       
        // Usuario autenticado
        usuarioLogado = ConsolaUtil.recuperarUsuarioLogado();
        
    }   
    
    /**
     * Click boton Cancelar.
     */
    public final void onClick$btnCancelar() {
    	wNuevoFormularioTramiteVersion.detach();
    }         
    
}
