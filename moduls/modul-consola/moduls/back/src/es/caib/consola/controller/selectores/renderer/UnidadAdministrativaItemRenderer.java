package es.caib.consola.controller.selectores.renderer;

import org.zkoss.zul.TreeNode;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.Treerow;

import es.caib.consola.model.UnidadAdministrativa;

/**
 * Unidad administrativa item renderer.
 */
public final class UnidadAdministrativaItemRenderer implements TreeitemRenderer {

    /**
     * Constructor.
     */
    public UnidadAdministrativaItemRenderer() {       
    }
   
    /**
     * Renderiza nodo.
     */
    public void render(final Treeitem item, final Object data) {
    	final Object pt = ((TreeNode) data).getData();
    	UnidadAdministrativa ua = (UnidadAdministrativa) pt;
    	
        item.setValue(data);
        final Treerow tr = new Treerow();
        tr.setParent(item);
        item.appendChild(tr);
        item.setOpen(ua.getCodigoPadre() == null);
        
		tr.appendChild(new Treecell(ua.getDescripcion()));        
    }

}
