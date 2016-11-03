package es.caib.consola.controller.gestor.wmodal.renderer;

import org.zkoss.zk.ui.sys.ComponentsCtrl;
import org.zkoss.zul.Image;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import es.caib.consola.controller.gestor.model.ModeloVisualizarPlugin;


/**
 * Render lista de plugins.
 */
public class PluginScriptListitemRender implements ListitemRenderer {

    public final void render(final Listitem item, final Object data, final int index) {
        final ModeloVisualizarPlugin entidad = (ModeloVisualizarPlugin) data;
        
        item.setValue(entidad);
        item.setAttribute("expandir", false);
        
        // Icono + nombre
        final Listcell listCellNombre = new Listcell(
                entidad.getDescripcion());
        listCellNombre.setId(entidad.getDescripcion());
        final Image iconoPlugin = new Image();
        iconoPlugin.setSrc("images/light_16x16.gif");
        if (entidad.getNombrePadre() != null) {
            listCellNombre.setStyle("padding-left:20px;font-size:11px;");
            iconoPlugin.setSrc("images/about1_16x16.gif");
        }
        item.appendChild(listCellNombre);
        ComponentsCtrl.applyForward(listCellNombre,
        	"onDoubleClick=onDoubleClick$plugin");
        
        // Icono ayuda
        final Listcell listCellImage = new Listcell();
        listCellImage.setPopup("AyudaPopUp");
        listCellImage.appendChild(iconoPlugin);
        item.appendChild(listCellImage);        
        ComponentsCtrl.applyForward(listCellImage, "onClick=onClick$plugin");
    }
}
