package es.caib.consola.controller.gestor.listener;

import java.util.HashMap;
import java.util.Map;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treeitem;

import es.caib.consola.ConstantesWEB;
import es.caib.consola.controller.gestor.VersionTramiteController;
import es.caib.consola.controller.gestor.model.NodoArbolVersionTramite;
import es.caib.consola.model.types.TypeModoAcceso;
import es.caib.zkutils.zk.SimacCommonUtils;

/**
 * Listener eventos arbol version tramite.
 * 
 * @see TreeEventEvent
 */
public class TreeVersionTramiteEventListener implements EventListener {

   
	/**
     * Evento producido.
     */
    private final String evento;
    
    /**
     * Ventana version tramite.
     */
    private VersionTramiteController versionTramiteController; 

    /**
     * Instancia listener.
     * @param pVersionTramiteController Ventana version tramite
     * @param pEvento Evento
     */
    public TreeVersionTramiteEventListener(final VersionTramiteController pVersionTramiteController,
            final String pEvento) {
        super();
        this.versionTramiteController = pVersionTramiteController;
        this.evento = pEvento;
    }

    
    public final void onEvent(final Event event) {
        
    	// Evento CLICK
    	if (evento.equals(ConstantesWEB.EVENTO_TREEITEM_CLICK)) {
            
        	Treeitem o = (Treeitem) event.getData();
            if (o == null) { // evento viene pantalla
                o = ((Tree) event.getTarget()).getSelectedItem();
                if (o == null) return;
            }
            final DefaultTreeNode node = (DefaultTreeNode) o.getValue();
            NodoArbolVersionTramite nodo = (NodoArbolVersionTramite) node.getData();
            
            // Muestra pantalla
            Map<String, Object> map = new HashMap<String, Object>();
            map.put(ConstantesWEB.PARAM_MODO_ACCESO, TypeModoAcceso.EDICION);
            map.put(ConstantesWEB.VERSIONTRAMITE_CONTROLLER, versionTramiteController);
            String ventana = "";
            switch (nodo.getTipo()) {
            	case PROPIEDADES_VERSION:
            		ventana = ConstantesWEB.ZUL_PROPÌEDADES_VERSION;
            		break;
            	case CONTROL_ACCESO:
            		ventana = ConstantesWEB.ZUL_ACCESO_VERSION;
            		break;
            	case MENSAJES_VALIDACION:
            		ventana = ConstantesWEB.ZUL_MENSAJES_VERSION;
            		break;
            	case LISTA_PASOS:
            		ventana = ConstantesWEB.ZUL_PASOS_VERSION;
            		break;
            	case DEBE_SABER:
            		ventana = ConstantesWEB.ZUL_PASO_DEBESABER;
            		break;
            	case RELLENAR:
            		ventana = ConstantesWEB.ZUL_PASO_RELLENAR;
            		break;
            	case FORMULARIO:
            		ventana = ConstantesWEB.ZUL_FORMULARIO;
            		break;
            	case ANEXAR:
            		ventana = ConstantesWEB.ZUL_PASO_ANEXAR;
            		break;
            	case ANEXO:
            		ventana = ConstantesWEB.ZUL_ANEXO;
            		break;
            	case PAGAR:
            		ventana = ConstantesWEB.ZUL_PASO_PAGAR;
            		break;
            	case PAGO:
            		ventana = ConstantesWEB.ZUL_PAGO;
            		break;
            	case REGISTRAR:
            		ventana = ConstantesWEB.ZUL_PASO_REGISTRAR;
            		break;
            }
                                    
            SimacCommonUtils.habilitaInclude(versionTramiteController.getDetalle(),
            		ventana, map);
        }
    }
   
}
