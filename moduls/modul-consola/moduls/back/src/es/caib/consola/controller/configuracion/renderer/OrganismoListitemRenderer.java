package es.caib.consola.controller.configuracion.renderer;

import org.zkoss.zk.ui.sys.ComponentsCtrl;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import es.caib.sistra.model.OrganoResponsable;

/**
 * Renderer lista organismos.
 */
public class OrganismoListitemRenderer implements ListitemRenderer {

    /**
     * Idioma.
     */
    private final String idioma;
    
    
    /**
     * Instancia renderer.
     * @param pIdioma Idioma
     */
    public OrganismoListitemRenderer(final String pIdioma) {
        idioma = pIdioma;        
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.zkoss.zul.ListitemRenderer#render(org.zkoss.zul.Listitem,
     * java.lang.Object)
     */
    public final void render(final Listitem item, final Object data, final int index) {
        final OrganoResponsable org = (OrganoResponsable) data;
        item.setValue(org);
        
        // Descripcion
        item.appendChild(new Listcell(org.getDescripcion()));
        
        // Doble click
        item.setAttribute("data", data);
        ComponentsCtrl.applyForward(item,
                    "onDoubleClick=onDoubleClick$itemOrganismo");
        
    }
}
