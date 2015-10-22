package es.caib.consola.controller.gestor.renderer;

import org.zkoss.zk.ui.sys.ComponentsCtrl;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import es.caib.consola.model.Usuario;
import es.caib.consola.util.GTTUtilsWeb;
import es.caib.sistra.model.TramiteVersion;


/**
 * Renderer lista versiones tramite.
 */
public class VersionTramiteListitemRenderer implements ListitemRenderer {

    /** Idioma. */
    private final String idioma;
    
    /**
     * Instancia un nuevo version tramite listitem renderer de
     * VersionTramiteListitemRenderer.
     * 
     * @param usuario
     *            Parámetro usuario
     */
    public VersionTramiteListitemRenderer(final Usuario usuario) {
        idioma = usuario.getIdioma();        
    }

   
    public final void render(final Listitem item, final Object data, final int index) {
        final TramiteVersion reg = (TramiteVersion) data;
        item.setValue(reg);
        item.appendChild(new Listcell(String.valueOf(reg.getVersion())));
        String usrBloqueo = "";
        if ("S".equals(reg.getBloqueadoModificacion())) {
        	usrBloqueo = reg.getBloqueadoModificacionPor();
        }
        item.appendChild(GTTUtilsWeb.nuevaCeldaDetalle(GTTUtilsWeb
                    .nuevaEtiquetaDetalle(usrBloqueo),"white-space: nowrap;"));
        
        
        item.setAttribute("data", data);
        ComponentsCtrl.applyForward(item,
                "onDoubleClick=onDoubleClick$itemVersion");
    }

}
