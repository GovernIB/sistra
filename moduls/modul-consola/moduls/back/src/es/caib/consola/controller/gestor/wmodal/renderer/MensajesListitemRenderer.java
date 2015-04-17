package es.caib.consola.controller.gestor.wmodal.renderer;

import org.zkoss.zk.ui.sys.ComponentsCtrl;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import es.caib.sistra.model.MensajeTramite;
import es.caib.sistra.model.TraMensajeTramite;

/**
 * Renderer lista mensajes version tramite.
 */
public final class MensajesListitemRenderer implements ListitemRenderer {

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
    public MensajesListitemRenderer(final String pIdioma) {
        idioma = pIdioma;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.zkoss.zul.ListitemRenderer#render(org.zkoss.zul.Listitem,
     * java.lang.Object)
     */   
    public void render(final Listitem item, final Object data) {

    	final MensajeTramite ft = (MensajeTramite) data;

    	item.setValue(data);
    	item.setAttribute("data", data);

        item.appendChild(new Listcell(ft.getIdentificador()));
        item.appendChild(new Listcell( ((TraMensajeTramite) ft.getTraduccion(idioma)).getMensaje()));
        
        ComponentsCtrl.applyForward(item,"onDoubleClick=onDoubleClick$itemMensaje");
    }
}
