package es.caib.consola.controller.selectores;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.sys.ComponentsCtrl;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import es.caib.bantel.model.GestorBandeja;
import es.caib.bantel.model.Procedimiento;
import es.caib.bantel.persistence.delegate.DelegateException;
import es.caib.bantel.persistence.delegate.DelegateUtil;
import es.caib.consola.ConstantesWEB;
import es.caib.consola.model.Usuario;
import es.caib.consola.util.ConsolaUtil;
import es.caib.zkutils.ConstantesZK;
import es.caib.zkutils.zk.composer.BaseComposer;
import es.caib.zkutils.zk.model.SeleccionSelector;

/**
 * Selector procedimiento.
 */
@SuppressWarnings("serial")
public class SelectorProcedimientoWModal extends BaseComposer {

    /**
     * Ventana.
     */
    private Window wSelectorProcedimiento;    
	
   /**
     * Listbox resultados.
     */
    private Listbox listboxResultados;

    /**
     * Usuario logeado.
     */
    private Usuario usuarioLogado;

    /**
     * Resultados.
     */
    private List<Procedimiento> resultados = new ArrayList<Procedimiento>();

    
    /** Filtro busqueda. */
    private Textbox filtroBusqueda;
  
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

        try {
			resultados = DelegateUtil.getTramiteDelegate().listarProcedimientos();
		} catch (DelegateException e) {
			ConsolaUtil.generaDelegateException(e);
		}
		
        listboxResultados.setItemRenderer(new ListitemRenderer() {
            public void render(final Listitem item, final Object data, final int index) {
                final Procedimiento reg = (Procedimiento) data;
                item.setValue(reg);
                item.setLabel(reg.getIdentificador());
                item.setAttribute("data", data);
                ComponentsCtrl.applyForward(item,
                        "onDoubleClick=onDoubleClick$itemListbox");
            }
        });

        listboxResultados.setModel(new BindingListModelList(resultados, true));
        
    }

    /**
     * Click boton buscar organismo.
     */
    public final void onClick$btnBuscar() {
    	
    	try {
			resultados = resultados = DelegateUtil.getTramiteDelegate().listarProcedimientos(filtroBusqueda.getValue());
		} catch (DelegateException e) {
			ConsolaUtil.generaDelegateException(e);
		}
    	
    	listboxResultados.selectItem(null);       
    	listboxResultados.setModel(new BindingListModelList(resultados, true));   
    }
    
    
    /**
     * Método para On click$btn aceptar de la clase EntornoWModal.
     */
    public final void onClick$btnAceptar() {
        
    	if (listboxResultados.getSelectedIndex() == ConstantesZK.SINSELECCION) {
        	mostrarMessageBox(Labels.getLabel("error.noSeleccion"),
                    Labels.getLabel(ConstantesWEB.INFO), Messagebox.OK,
                    Messagebox.EXCLAMATION);
        	return;
        }	
            
        Object value = ((Listitem) listboxResultados.getSelectedItem()).getValue();        


        // Generamos evento de seleccion
        final Event event = new Event(ConstantesZK.EVENTO_SELECCION_SELECTOR, padre,
        		new SeleccionSelector(ConstantesWEB.SELECTOR_PROCEDIMIENTO, value));
        Events.postEvent(event);
        
        wSelectorProcedimiento.detach();
        
        

    }

    /**
     * Método para On double click$item organismo de la clase EntornoWModal.
     */
    public final void onDoubleClick$itemListbox() {
        onClick$btnAceptar();
    }
    
    /**
     * Click boton Cerrar.
     */
    public final void onClick$btnCancelar() {
    	wSelectorProcedimiento.detach();
    }
}
