package es.caib.consola.controller.gestor.wmodal;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Path;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Window;

import es.caib.consola.ConstantesWEB;
import es.caib.consola.controller.gestor.VersionTramiteController;
import es.caib.consola.model.Usuario;
import es.caib.consola.util.ConsolaUtil;
import es.caib.zkutils.zk.composer.BaseComposer;

/**
 * Controlador visual ventana de pasos de tramitacion de la version de tramite.
 * 
 * @author Indra
 * 
 */

@SuppressWarnings("serial")
public class PasosTramitacionVersionTramiteWModal extends BaseComposer {

    /** Modo edicion. */
    private String modo;
    
    /** Ventana version tramite. */
    private VersionTramiteController versionTramiteController;
    
    /** Padre. */
    private Component padre;

    /** Ventana. */
    private Window wPasosVersion;

    /** Usuario logado. */
    private Usuario usuarioLogado;

    /** Lista pasos. */
    private Listbox listPasosNormalizados;
    
    /*
     * (non-Javadoc)
     * 
     * @see
     * es.gva.dgm.tra.util.zk.composer.BaseComposer#doAfterCompose(org.zkoss
     * .zk.ui.Component)
     */
   @Override
    public final void doAfterCompose(final Component comp) {
        
    	super.doAfterCompose(comp);
    	
    	usuarioLogado = ConsolaUtil.recuperarUsuarioLogado();
    	
    	// RAFA: REVISAR ESTO...
    	padre = Path.getComponent(ConstantesWEB.WPATH_VERSIONES);
    	
        //modo = versionTramiteController.getModo();
        
        final int alto = Integer.parseInt(session.getAttribute("DesktopHeight")
                .toString())
                - ConstantesWEB.HEADER_HEIGHT
                - ConstantesWEB.HEADER_INCLUDE_HEIGHT
                - ConstantesWEB.DIV_TITULO;
        
       // listPasosPersonalizados.setHeight(alto + "px");

        /*
        listPasosNormalizados.setItemRenderer(new PasoListitemRenderer(
                usuarioLogado.getIdioma(), version.getFlujo(), false));
        listPasosNormalizados.setModel(new BindingListModelList(
                pasosTramitacion, true));
        */

    }

   
}
