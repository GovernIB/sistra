package es.caib.consola.controller.gestor.wmodal.renderer;

import org.zkoss.zk.ui.sys.ComponentsCtrl;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import es.caib.sistra.model.Documento;
import es.caib.sistra.model.TraDocumento;

/**
 * Renderer lista anexos version tramite.
 */
public final class AnexosListitemRenderer implements ListitemRenderer {

    /**
     * Atributo idioma.
     */
    private final String idioma;

    /**
     * Instancia un nuevo formularios listitem renderer de
     * FormulariosListitemRenderer.
     * 
     * @param pIdioma
     *            Parametro idioma
     */
    public AnexosListitemRenderer(final String pIdioma) {
        idioma = pIdioma;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.zkoss.zul.ListitemRenderer#render(org.zkoss.zul.Listitem,
     * java.lang.Object)
     */   
    public void render(final Listitem item, final Object data, int index) {

    	final Documento ft = (Documento) data;

    	item.setValue(data);
    	item.setAttribute("data", data);

        item.appendChild(new Listcell(ft.getIdentificador()));
        item.appendChild(new Listcell( ((TraDocumento) ft.getTraduccion(idioma)).getDescripcion()));
        
        
        
        ComponentsCtrl.applyForward(item,"onDoubleClick=onDoubleClick$itemAnexo");
    }
}
