package es.caib.consola.controller.gestor.wmodal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Path;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zul.Button;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import es.caib.consola.ConstantesWEB;
import es.caib.consola.controller.gestor.VersionTramiteController;
import es.caib.consola.controller.gestor.wmodal.renderer.PagosListitemRenderer;
import es.caib.consola.model.Usuario;
import es.caib.consola.model.types.TypeModoAcceso;
import es.caib.consola.util.ConsolaUtil;
import es.caib.sistra.model.Documento;
import es.caib.zkutils.ConstantesZK;
import es.caib.zkutils.zk.composer.BaseComposer;

/**
 * Ventana paso pagar.
 */
@SuppressWarnings("serial")
public class PasoPagarWModal extends BaseComposer {
	
	// Instancia
    /** Ventana paso pagar. */
    private Window wPagarTasas;
    /** Ventana version tramite. */
    private VersionTramiteController versionTramiteController;
    /** Modo de apertura. */
    private TypeModoAcceso modo;
    /** Usuario autenticado. */
    private Usuario usuarioLogado;
    /** Alto. */
    private int alto;
    
	// Botones    
    /** Boton borrar pago. */
    private Button btnQuitar;
    /** Boton editar pago. */
    private Button btnEditar;
    /** Boton nuevo pago. */
    private Button btnAnyadir;
    /** Boton subir orden pago. */
    private Button btnSubir;
    /** Boton bajar orden pago. */
    private Button btnBajar;
    /** Boton ayuda. */
    private Button btnAyuda;
    
	// Popup menu
	/** Popup nuevo pago. */
    private Menuitem popupNuevoPago;
    /** Popup borrar pago. */
    private Menuitem popupQuitaPago;
    /** Popup editar pago. */
    private Menuitem popupEditaPago;
    /** Popup subir orden pago. */
    private Menuitem popupSubirPago;
    /** Popup bajar orden pago. */
    private Menuitem popupBajarPago;
        	   
    // Lista pagos    
    /** Lista pagos. */
    private Listbox listPagos;
    /** Model list pagos. */
    private BindingListModelList listPagosDataModel;
          
    @Override
    public final void doAfterCompose(final Component compPasoRellenar) {
    	
        super.doAfterCompose(compPasoRellenar);
        
        // Obtenemos referencia a pantalla version tramite
        versionTramiteController = (VersionTramiteController) arg.get(ConstantesWEB.VERSIONTRAMITE_CONTROLLER); 
        
        // Modo de acceso
        modo = versionTramiteController.getModo();
        
        // Usuario autenticado
        usuarioLogado = ConsolaUtil.recuperarUsuarioLogado();
         
        // Configura lista pagos
        listPagos.setItemRenderer(new PagosListitemRenderer(
                usuarioLogado.getIdioma()));
        listPagosDataModel = new BindingListModelList(generarModel(), true);
        listPagos.setModel(listPagosDataModel);
        
        
        visualizaComponentes();
        
    }

    /**
     * Genera modelo para lista.
     * @return Lista de pagos
     */
	private List<Documento> generarModel() {
		List<Documento> pagos = new ArrayList<Documento>();
        if (versionTramiteController.getVersion().getDocumentos() != null) {
        	for (Iterator it = versionTramiteController.getVersion().getDocumentos().iterator(); it.hasNext();) {
        		Documento doc = (Documento) it.next();
        		if (doc.getTipo() == Documento.TIPO_PAGO) {
        			pagos.add(doc);
        		}
        	}
        }
		return pagos;
	}

    /**
     * Editar pago.
     */
    public final void onClick$btnEditar() {
        if (listPagos.getSelectedIndex() == ConstantesZK.SINSELECCION) {
            mostrarMessageBox(
                    Labels.getLabel("pagarTasas.noSeleccionado"),
                    Labels.getLabel(ConstantesWEB.WARNING), Messagebox.OK,
                    	Messagebox.EXCLAMATION);
        } else {
            editaPago();            
        }
    }

    /**
     * Edita pago.
     */
    private void editaPago() {
        final Map<String, Object> map = new HashMap<String, Object>();
        
        final Listitem item = (Listitem) listPagos.getItems().get(
                listPagos.getSelectedIndex());
        final Documento pago = (Documento) item
                .getValue();
        map.put(ConstantesWEB.PAGO, pago.getCodigo());
        
     // RAFA: Esto xq es asi?? REvisar modos... 
        if (modo.equals(TypeModoAcceso.ALTA)) {
            map.put(ConstantesWEB.PARAM_MODO_ACCESO, TypeModoAcceso.EDICION);
        } else {
            map.put(ConstantesWEB.PARAM_MODO_ACCESO, modo);
        }
        
       // RAFA: Esto xq es asi?? 
        map.put(ConstantesWEB.PADRE,
                    Path.getComponent("/priWindow/contenedorPrincipal/verTramWindow/detalle/wPagarTasas"));
        
        final Window ventana = (Window) creaComponente(
                ConstantesWEB.ZUL_PAGO, null, map);
        
        abreVentanaModal(ventana);
    }

    /**
     * Mostrar pago.
     */
    public final void onDoubleClick$itemPago() {
        onClick$btnEditar();        
    }

    /**
     * Visualizar componentes.
     */
    public final void visualizaComponentes() {
    	/*
    	listFormularios.setContext("popupRellenar");
        
        alto = Integer.parseInt(session.getAttribute("DesktopHeight")
                .toString());
        alto -= ConstantesWEB.HEADER_HEIGHT;
        alto -= ConstantesWEB.HEADER_INCLUDE_HEIGHT;
        */
    	
        alto = ConstantesWEB.FORM_HEIGHT;
        
        
        listPagos.setHeight(alto + "px");       
    }  

  
}
