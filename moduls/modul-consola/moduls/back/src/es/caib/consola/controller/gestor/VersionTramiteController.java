package es.caib.consola.controller.gestor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.DefaultTreeModel;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Include;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Window;


import es.caib.consola.ConstantesWEB;
import es.caib.consola.controller.gestor.listener.TreeVersionTramiteEventListener;
import es.caib.consola.controller.gestor.model.NodoArbolVersionTramite;
import es.caib.consola.controller.gestor.model.TypeNodoArbolVersionTramite;
import es.caib.consola.controller.gestor.renderer.TreeVersionTramiteItemRenderer;
import es.caib.consola.model.Usuario;
import es.caib.consola.model.types.TypeModoAcceso;
import es.caib.consola.util.ConsolaUtil;
import es.caib.sistra.model.Documento;
import es.caib.sistra.model.TramiteVersion;
import es.caib.sistra.persistence.delegate.DelegateException;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.zkutils.zk.composer.BaseComposer;

/**
 * Controlador visual de la configuración de una versión de tramite.
 * 
 * @author Indra
 * 
 */
@SuppressWarnings("serial")
public class VersionTramiteController extends BaseComposer {

	/**
     * Ventana version.
     */
    private Window verTramWindow;
    /**
     * Modo de acceso.
     */
    private TypeModoAcceso modo;
    /**
     * Usuario logado.
     */
    private Usuario usuarioLogado;
	
    /**
     * Arbol configuracion.
     */
    private Tree tree;
    /**
     * Include para mostrar los elementos seleccionados en el arbol.
     */
    private Include detalle;
	
    /**
     * Datos version tramite.
     */
    private TramiteVersion version;
          

    /*
     * (non-Javadoc)
     * 
     * @see
     * es.gva.dgm.tra.util.zk.composer.BaseComposer#doAfterCompose(org.zkoss
     * .zk.ui.Component)
     */
    @Override
    public final void doAfterCompose(final Component compVersion) {
        super.doAfterCompose(compVersion);

        
        usuarioLogado = ConsolaUtil.recuperarUsuarioLogado();
        
        Long idVersion = (Long) session.getAttribute(ConstantesWEB.VERSION);
        
        try {
			version = DelegateUtil.getTramiteVersionDelegate().obtenerTramiteVersion(idVersion);
		} catch (DelegateException e) {
			ConsolaUtil.generaDelegateException(e);
		}
               
        tree.setItemRenderer(new TreeVersionTramiteItemRenderer(this));
        tree.setModel(createTreeModel());
        final int alto = Integer.parseInt(session.getAttribute("DesktopHeight").toString()) - ConstantesWEB.HEADER_HEIGHT;
        tree.setHeight(alto + "px");
        
        tree.selectItem((Treeitem) tree.getTreechildren().getItems().iterator().next());
        
        
        tree.addEventListener(Events.ON_CLICK, new TreeVersionTramiteEventListener(this, ConstantesWEB.EVENTO_TREEITEM_CLICK));
        
        final Event event = new Event(Events.ON_CLICK, tree, (Treeitem) tree.getTreechildren().getItems().iterator().next());
        Events.postEvent(event);
        // TODO GESTIONAR MODO DE ACCESO
        
        
        
        
        
        // TODO EVENTO GUARDAR DE CADA ELEMENTO
        /*
        verTramWindow.addEventListener(ConstantesWEB.EVENTO_VERSION_GUARDAR_PROPIEDADES,
                new ModificaVersionEventListener(detalle,
                        ConstantesWEB.EVENTO_VERSION_GUARDAR_PROPIEDADES));        
        verTramWindow.addEventListener(ConstantesWEB.EVENTO_VERSION_GUARDAR_RELLENAR,
                new ModificaVersionEventListener(version, tree, pasosTramitacion, detalle,
                        ConstantesWEB.EVENTO_VERSION_GUARDAR_RELLENAR));
        verTramWindow.addEventListener(ConstantesWEB.EVENTO_VERSION_GUARDAR_CAPTURAR,
                new ModificaVersionEventListener(version, tree, pasosTramitacion, detalle,
                        ConstantesWEB.EVENTO_VERSION_GUARDAR_CAPTURAR));
        verTramWindow.addEventListener(ConstantesWEB.EVENTO_VERSION_GUARDAR_FORMULARIO,
                new ModificaVersionEventListener(version, tree, pasosTramitacion, detalle,
                        ConstantesWEB.EVENTO_VERSION_GUARDAR_FORMULARIO));
        verTramWindow.addEventListener(ConstantesWEB.EVENTO_VERSION_GUARDAR_ANEXAR,
                new ModificaVersionEventListener(version, tree, pasosTramitacion, detalle,
                        ConstantesWEB.EVENTO_VERSION_GUARDAR_ANEXAR));
        verTramWindow.addEventListener(ConstantesWEB.EVENTO_VERSION_GUARDAR_ANEXO,
                new ModificaVersionEventListener(version, tree, pasosTramitacion, detalle,
                        ConstantesWEB.EVENTO_VERSION_GUARDAR_ANEXO));
        verTramWindow.addEventListener(ConstantesWEB.EVENTO_VERSION_GUARDAR_PAGAR,
                new ModificaVersionEventListener(version, tree, pasosTramitacion, detalle,
                        ConstantesWEB.EVENTO_VERSION_GUARDAR_PAGAR));
        verTramWindow.addEventListener(ConstantesWEB.EVENTO_VERSION_GUARDAR_PAGO,
                new ModificaVersionEventListener(version, tree, pasosTramitacion, detalle,
                        ConstantesWEB.EVENTO_VERSION_GUARDAR_PAGO));
        verTramWindow.addEventListener(ConstantesWEB.EVENTO_VERSION_GUARDAR_REGISTRAR,
                new ModificaVersionEventListener(version, tree, pasosTramitacion, detalle,
                        ConstantesWEB.EVENTO_VERSION_GUARDAR_REGISTRAR));
        verTramWindow.addEventListener(ConstantesWEB.EVENTO_VERSION_GUARDAR_INFORMACION,
                new ModificaVersionEventListener(version, tree, pasosTramitacion, detalle,
                        ConstantesWEB.EVENTO_VERSION_GUARDAR_INFORMACION));
        */
    }
    
    
    /**
     * Crea model para el arbol.
     * @return model
     */
    private DefaultTreeModel createTreeModel() {
    
    	// Documentos
    	final List<DefaultTreeNode> formularios = new ArrayList<DefaultTreeNode>();
    	final List<DefaultTreeNode> anexos = new ArrayList<DefaultTreeNode>();
    	final List<DefaultTreeNode> pagos = new ArrayList<DefaultTreeNode>();
    	
    	for (Iterator it = this.version.getDocumentos().iterator(); it.hasNext();) {
    		Documento doc = (Documento) it.next();
    		switch (doc.getTipo()) {
	    		case Documento.TIPO_FORMULARIO:
	    			formularios.add(new DefaultTreeNode(new NodoArbolVersionTramite(TypeNodoArbolVersionTramite.FORMULARIO, doc.getCodigo())));
	    			break;
	    		case Documento.TIPO_ANEXO:
	    			anexos.add(new DefaultTreeNode(new NodoArbolVersionTramite(TypeNodoArbolVersionTramite.ANEXO, doc.getCodigo())));
	    			break;
	    		case Documento.TIPO_PAGO:
	    			pagos.add(new DefaultTreeNode(new NodoArbolVersionTramite(TypeNodoArbolVersionTramite.PAGO, doc.getCodigo())));
	    			break;    			
    		}    		
    	}
    	
    	final List<DefaultTreeNode> fijos = new ArrayList<DefaultTreeNode>();
    	fijos.add(new DefaultTreeNode(new NodoArbolVersionTramite(TypeNodoArbolVersionTramite.PROPIEDADES_VERSION, null)));
    	fijos.add(new DefaultTreeNode(new NodoArbolVersionTramite(TypeNodoArbolVersionTramite.CONTROL_ACCESO, null)));
    	fijos.add(new DefaultTreeNode(new NodoArbolVersionTramite(TypeNodoArbolVersionTramite.MENSAJES_VALIDACION, null)));
    	
    	List<DefaultTreeNode> pasos = new ArrayList<DefaultTreeNode>();
    	pasos.add(new DefaultTreeNode(new NodoArbolVersionTramite(TypeNodoArbolVersionTramite.DEBE_SABER, null)));
        	
    	pasos.add(new DefaultTreeNode(new NodoArbolVersionTramite(TypeNodoArbolVersionTramite.RELLENAR, null), formularios));
    	pasos.add(new DefaultTreeNode(new NodoArbolVersionTramite(TypeNodoArbolVersionTramite.ANEXAR, null), anexos));
    	pasos.add(new DefaultTreeNode(new NodoArbolVersionTramite(TypeNodoArbolVersionTramite.PAGAR, null), pagos));
    	
    	pasos.add(new DefaultTreeNode(new NodoArbolVersionTramite(TypeNodoArbolVersionTramite.REGISTRAR, null)));
    	
		fijos.add(new DefaultTreeNode(new NodoArbolVersionTramite(TypeNodoArbolVersionTramite.LISTA_PASOS, null), pasos));
    	
		final DefaultTreeModel model = new DefaultTreeModel(
                new DefaultTreeNode(null, fijos));
        return model;
    }


	public TramiteVersion getVersion() {
		return version;
	}


	public Include getDetalle() {
		return detalle;
	}


	public TypeModoAcceso getModo() {
		return modo;
	}


	public void setModo(TypeModoAcceso modo) {
		this.modo = modo;
	}


	public Window getVerTramWindow() {
		return verTramWindow;
	}

}
