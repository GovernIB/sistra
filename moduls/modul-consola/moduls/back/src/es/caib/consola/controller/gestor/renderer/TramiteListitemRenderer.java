package es.caib.consola.controller.gestor.renderer;

import org.zkoss.zk.ui.sys.ComponentsCtrl;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import es.caib.consola.util.GTTUtilsWeb;
import es.caib.sistra.model.TraTramite;
import es.caib.sistra.model.Tramite;


/**
 * Class TramiteListitemRenderer.
 */
public class TramiteListitemRenderer implements ListitemRenderer {

    /**
     * Atributo idioma.
     */
    private final String idioma;

    /**
     * Instancia un nuevo tramite listitem renderer de TramiteListitemRenderer.
     * 
     * @param pIdioma
     *            Parámetro idioma
     */
    public TramiteListitemRenderer(final String pIdioma) {
        idioma = pIdioma;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.zkoss.zul.ListitemRenderer#render(org.zkoss.zul.Listitem,
     * java.lang.Object)
     */
    public final void render(final Listitem item, final Object data, final int index) {
        final Tramite reg = (Tramite) data;
        item.setValue(reg);
        item.appendChild(GTTUtilsWeb.nuevaCeldaDetalle(GTTUtilsWeb.nuevaEtiquetaDetalle(reg.getIdentificador()), "white-space: nowrap;"));
        item.appendChild(GTTUtilsWeb.nuevaCeldaDetalle(GTTUtilsWeb.nuevaEtiquetaDetalle(((TraTramite) reg.getTraduccion(idioma)).getDescripcion()), "white-space: nowrap;"));
        item.setAttribute("data", data);
        ComponentsCtrl.applyForward(item,
                "onDoubleClick=onDoubleClick$itemTramite");
    }

    
    
}
