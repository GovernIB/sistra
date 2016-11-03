package es.caib.consola.controller.selectores;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.DefaultTreeModel;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Window;

import es.caib.consola.ConstantesWEB;
import es.caib.consola.controller.selectores.renderer.UnidadAdministrativaItemRenderer;
import es.caib.consola.model.UnidadAdministrativa;
import es.caib.consola.model.Usuario;
import es.caib.consola.util.ConsolaUtil;
import es.caib.sistra.modelInterfaz.ConstantesDominio;
import es.caib.sistra.modelInterfaz.ValoresDominio;
import es.caib.sistra.persistence.util.UtilDominios;
import es.caib.zkutils.ConstantesZK;
import es.caib.zkutils.zk.composer.BaseComposer;
import es.caib.zkutils.zk.model.SeleccionSelector;

/**
 * Selector unidad administrativa.
 */
@SuppressWarnings("serial")
public class SelectorUnidadAdministrativaWModal extends BaseComposer {

	/** Arbol UA. */
    private Tree treeUA;

    /**
     * Usuario logeado.
     */
    private Usuario usuarioLogado;    

    /**
     * Ventana.
     */
    private Window wSelectorUnidadAdministrativa;    
    
    /** Padre. */
    private Component padre;

    /*
     * (non-Javadoc)
     * 
     * @see
     * es.gva.dgm.tra.util.zk.composer.BaseComposer#doAfterCompose(org.zkoss
     * .zk.ui.Component)
     */
    @Override
    public final void doAfterCompose(final Component compEntorno) {          	
    	super.doAfterCompose(compEntorno);
    	padre = compEntorno.getParent();

        usuarioLogado = ConsolaUtil.recuperarUsuarioLogado();              

        treeUA.setItemRenderer(new UnidadAdministrativaItemRenderer());
		treeUA.setModel(createModelUA());
		treeUA.addEventListener(Events.ON_DOUBLE_CLICK,
				new EventListener() {
		            public void onEvent(final Event pEvent) {
		            	onClick$btnAceptar();
		            }		
		        });
    }

    
    /**
     * Crea modelo tree.
     * @return
     */    
    private DefaultTreeModel createModelUA() {
     	DefaultTreeModel model = null;
    	try {
    		ValoresDominio vd = UtilDominios.recuperarValoresDominio(ConstantesDominio.DOMINIO_SAC_ARBOL_UNIDADES_ADMINISTRATIVAS);    				        		       
		    final List<DefaultTreeNode> arbol = buscarHijos(vd, null);
		    model = new DefaultTreeModel(new DefaultTreeNode(null, arbol));		    
    	 } catch (Exception e) {
 			ConsolaUtil.generaDelegateException(e);
 		}
 		return model;
    }        

	/**
     * Busca hijos de forma recursiva.
     * @param vd
     * @param codigoPadre
     * @return
     */
    private List<DefaultTreeNode> buscarHijos(ValoresDominio vd, String codigoPadre) {
    	final List<DefaultTreeNode> hijos = new ArrayList<DefaultTreeNode>();    	
    	for (int i = 1; i <= vd.getNumeroFilas(); i++) {
    		if ((StringUtils.isBlank(codigoPadre) && StringUtils.isBlank(vd.getValor(i, "PARENT"))) || 
    			StringUtils.equals(codigoPadre, vd.getValor(i, "PARENT"))) {
    			UnidadAdministrativa unidadAdministrativa = new UnidadAdministrativa(vd.getValor(i, "CODIGO"), vd.getValor(i, "DESCRIPCION"), vd.getValor(i, "PARENT"));
    			final List<DefaultTreeNode> nietos = buscarHijos(vd, unidadAdministrativa.getCodigo());
    			if (nietos.size() > 0) {
    				hijos.add(new DefaultTreeNode(unidadAdministrativa, nietos));
    			} else {
    				hijos.add(new DefaultTreeNode(unidadAdministrativa));
    			}
    		}	        	
	    }
    	return hijos;
	}

    
    /**
     * Método para On click$btn aceptar de la clase EntornoWModal.
     */
    public final void onClick$btnAceptar() {
    	if (treeUA.getSelectedItem()  == null ) {
        	mostrarMessageBox(Labels.getLabel("error.noSeleccion"),
                    Labels.getLabel(ConstantesWEB.INFO), Messagebox.OK,
                    Messagebox.EXCLAMATION);
        	return;
        }	
            
        UnidadAdministrativa value = (UnidadAdministrativa) ((DefaultTreeNode) treeUA.getSelectedItem().getValue()).getData();        

        // Generamos evento de seleccion
        final Event event = new Event(ConstantesZK.EVENTO_SELECCION_SELECTOR, padre,
        		new SeleccionSelector(ConstantesWEB.SELECTOR_UNIDAD_ADMINISTRATIVA, value));
        Events.postEvent(event);
       
        
    	wSelectorUnidadAdministrativa.detach();                
    }    
    
    /**
     * Click boton Cerrar.
     */
    public final void onClick$btnCancelar() {
    	wSelectorUnidadAdministrativa.detach();
    }

    /**
     * Método para On double click$item organismo de la clase EntornoWModal.
     */
    public final void onDoubleClick$itemOrganismo() {
        onClick$btnAceptar();
    }
   
}
