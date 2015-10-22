package es.caib.consola.controller.gestor.wmodal.renderer;

import org.zkoss.zk.ui.sys.ComponentsCtrl;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import es.caib.sistra.model.Documento;
import es.caib.sistra.model.TraDocumento;

/**
 * Renderer lista formularios version tramite.
 */
public final class PagosListitemRenderer implements ListitemRenderer {

    /**
     * Idioma.
     */
    private final String idioma;

    /**
     * Constructor.
     * 
     * @param pIdioma
     *            Parametro idioma
     */
    public PagosListitemRenderer(final String pIdioma) {
        idioma = pIdioma;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.zkoss.zul.ListitemRenderer#render(org.zkoss.zul.Listitem,
     * java.lang.Object)
     */   
    public void render(final Listitem item, final Object data, final int index) {

    	final Documento ft = (Documento) data;

    	item.setValue(data);
    	item.setAttribute("data", data);

        item.appendChild(new Listcell(ft.getIdentificador()));
        item.appendChild(new Listcell( ((TraDocumento) ft.getTraduccion(idioma)).getDescripcion()));
        
        
        
        ComponentsCtrl.applyForward(item,"onDoubleClick=onDoubleClick$itemPago");
    }
}
