package es.caib.consola.controller.gestor.renderer;

import java.util.Iterator;

import org.zkoss.util.resource.Labels;
import org.zkoss.zul.TreeNode;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.Treerow;

import es.caib.consola.controller.gestor.VersionTramiteController;
import es.caib.consola.controller.gestor.model.NodoArbolVersionTramite;
import es.caib.sistra.model.Documento;

/**
 * Renderer arbol configuracion version tramite.
 */
public final class TreeVersionTramiteItemRenderer implements TreeitemRenderer {  
    /**
     * Ventana version tramite.
     */
	private VersionTramiteController versionTramiteController;   

    /**
     * Instancia PasoTramitacionItemRenderer.
     * 
     * @param pVersionTramiteController
     *            Ventana version     
     */
    public TreeVersionTramiteItemRenderer(VersionTramiteController pVersionTramiteController) {
        versionTramiteController = pVersionTramiteController;
    }

    public void render(final Treeitem item, final Object data, final int index) {
    	final Object pt = ((TreeNode) data).getData();
        item.setValue(data);
        final Treerow tr = new Treerow();
        tr.setParent(item);
        item.appendChild(tr);
        item.setOpen(true);
        
        NodoArbolVersionTramite nodo = (NodoArbolVersionTramite) pt;
        switch (nodo.getTipo()) {
        	case PROPIEDADES_VERSION:
        		tr.appendChild(new Treecell(Labels.getLabel("tree.propiedades")));
        		break;
        	case CONTROL_ACCESO:
        		tr.appendChild(new Treecell(Labels.getLabel("tree.controlAcceso")));
        		break;
        	case MENSAJES_VALIDACION:
        		tr.appendChild(new Treecell(Labels.getLabel("tree.mensajes")));
        		break;
        	case LISTA_PASOS:
        		tr.appendChild(new Treecell(Labels.getLabel("tree.pasos")));
        		break;
        	case DEBE_SABER:
        		tr.appendChild(new Treecell(Labels.getLabel("tree.pasoDebeSaber")));
        		break;        	
        	case RELLENAR:
        		tr.appendChild(new Treecell(Labels.getLabel("tree.pasoRellenar")));
        		break;
        	case FORMULARIO:
        		tr.appendChild(new Treecell(getDescripcionDocumento(nodo.getIdentificador())));
        		break;
        	case ANEXAR:
        		tr.appendChild(new Treecell(Labels.getLabel("tree.pasoAnexar")));
        		break;
        	case ANEXO:
        		tr.appendChild(new Treecell(getDescripcionDocumento(nodo.getIdentificador())));
        		break;
        	case PAGAR:
        		tr.appendChild(new Treecell(Labels.getLabel("tree.pasoPagar")));
        		break;
        	case PAGO:
        		tr.appendChild(new Treecell(getDescripcionDocumento(nodo.getIdentificador())));
        		break;
        	case REGISTRAR:
        		tr.appendChild(new Treecell(Labels.getLabel("tree.pasoRegistrar")));
        		break;
        }        
    }

    /**
     * Obtiene descripcion documento.
     * @param idDocumento Id documento
     * @return descripcion documento.
     */
    private String getDescripcionDocumento(Long idDocumento) {
    	String desc = "";
    	for (Iterator it = versionTramiteController.getVersion().getDocumentos().iterator(); it.hasNext();) {
    		Documento doc = (Documento) it.next();
    		if (doc.getCodigo().equals(idDocumento)) {
    			desc = doc.getIdentificador();
    		}
    	}
    	return desc;
    }
       
}

