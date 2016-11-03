package es.caib.consola.controller.gestor.wmodal.renderer;

import org.zkoss.zk.ui.sys.ComponentsCtrl;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import es.caib.bantel.model.CampoFuenteDatos;

/**
 * Renderer campo fuente datos.
 */
public final class CampoFuenteDatosListitemRenderer implements ListitemRenderer {
   
    /*
     * (non-Javadoc)
     * 
     * @see org.zkoss.zul.ListitemRenderer#render(org.zkoss.zul.Listitem,
     * java.lang.Object)
     */   
    public void render(final Listitem item, final Object data, int index) {

    	final CampoFuenteDatos ft = (CampoFuenteDatos) data;

    	item.setValue(data);
    	item.setAttribute("data", data);

        item.appendChild(new Listcell(ft.getIdentificador()));
        item.appendChild(new Listcell("S".equals(ft.getEsPK())?"X":""));
        
        ComponentsCtrl.applyForward(item,"onDoubleClick=onDoubleClick$itemCampoFuenteDatos");
    }
}
