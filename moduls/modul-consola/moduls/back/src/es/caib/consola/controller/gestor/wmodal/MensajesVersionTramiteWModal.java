package es.caib.consola.controller.gestor.wmodal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zul.Button;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import es.caib.consola.ConstantesWEB;
import es.caib.consola.controller.gestor.VersionTramiteController;
import es.caib.consola.controller.gestor.wmodal.renderer.MensajesListitemRenderer;
import es.caib.consola.model.Usuario;
import es.caib.consola.model.types.TypeModoAcceso;
import es.caib.consola.util.ConsolaUtil;
import es.caib.sistra.model.MensajeTramite;
import es.caib.zkutils.ConstantesZK;
import es.caib.zkutils.zk.composer.BaseComposer;

/**
 * Ventana mensajes version tramite.
 */
@SuppressWarnings("serial")
public class MensajesVersionTramiteWModal extends BaseComposer {
	
	// Instancia
    /** Ventana mensajes version. */
    private Window wMensajesVersion;
    /** Ventana version tramite. */
    private VersionTramiteController versionTramiteController;
    /** Modo de apertura. */
    private TypeModoAcceso modo;
    /** Usuario autenticado. */
    private Usuario usuarioLogado;
    /** Alto. */
    private int alto;
    
	// Botones    
    /** Boton borrar mensaje. */
    private Button btnQuitar;
    /** Boton editar mensaje. */
    private Button btnEditar;
    /** Boton nuevo mensaje. */
    private Button btnAnyadir;    
    /** Boton ayuda. */
    private Button btnAyuda;
    
	// Popup menu
	/** Popup nuevo mensaje. */
    private Menuitem popupNuevoMensaje;
    /** Popup borrar mensaje. */
    private Menuitem popupQuitaMensaje;
    /** Popup editar mensaje. */
    private Menuitem popupEditaMensaje;   
        	   
    // Lista mensajes    
    /** Lista mensajes. */
    private Listbox listMensajes;
    /** Atributo list mensajes data model de RellenarFormulariosWModal. */
    private BindingListModelList listMensajesDataModel;
          
    @Override
    public final void doAfterCompose(final Component compPasoRellenar) {
    	
        super.doAfterCompose(compPasoRellenar);
        
        // Obtenemos referencia a pantalla version tramite
        versionTramiteController = (VersionTramiteController) arg.get(ConstantesWEB.VERSIONTRAMITE_CONTROLLER); 
        
        // Modo de acceso
        modo = versionTramiteController.getModo();
        
        // Usuario autenticado
        usuarioLogado = ConsolaUtil.recuperarUsuarioLogado();
         
        // Configura lista mensajes
        listMensajes.setItemRenderer(new MensajesListitemRenderer(
                usuarioLogado.getIdioma()));
        listMensajesDataModel = new BindingListModelList(generarModel(), true);
        listMensajes.setModel(listMensajesDataModel);
        
        
        visualizaComponentes();
        
    }

    /**
     * Genera modelo para lista.
     * @return Lista de mensajes
     */
	private List<MensajeTramite> generarModel() {
		List<MensajeTramite> mensajes = new ArrayList<MensajeTramite>();
        if (versionTramiteController.getVersion().getMensajes() != null) {
        	for (Iterator it = versionTramiteController.getVersion().getMensajes().keySet().iterator(); it.hasNext();) {
        		MensajeTramite m = (MensajeTramite)  versionTramiteController.getVersion().getMensajes().get(it.next());
        		mensajes.add(m);        		
        	}
        }
		return mensajes;
	}

    /**
     * Editar mensaje.
     */
    public final void onClick$btnEditar() {
        if (listMensajes.getSelectedIndex() == ConstantesZK.SINSELECCION) {
            mostrarMessageBox(
                    //Labels.getLabel("rellenarFormularios.noSeleccionado"),
            		"falta label no seleccionado",
                    Labels.getLabel(ConstantesWEB.WARNING), Messagebox.OK,
                    	Messagebox.EXCLAMATION);
        } else {
            editaMensaje();            
        }
    }

    /**
     * Edita mensaje.
     */
    private void editaMensaje() {
    	/*
        final Map<String, Object> map = new HashMap<String, Object>();
        
        final Listitem item = (Listitem) listMensajes.getItems().get(
                listMensajes.getSelectedIndex());
        final Documento mensaje = (Documento) item
                .getValue();
        map.put(ConstantesWEB.FORMULARIOTRAMITE, mensaje.getCodigo());
        
     // RAFA: Esto xq es asi?? REvisar modos... 
        if (modo.equals(TypeModoAcceso.ALTA)) {
            map.put(ConstantesWEB.MODO, TypeModoAcceso.EDICION);
        } else {
            map.put(ConstantesWEB.MODO, modo);
        }
        
       // RAFA: Esto xq es asi?? 
        map.put(ConstantesWEB.PADRE,
                    Path.getComponent("/priWindow/contenedorPrincipal/verTramWindow/detalle/wRellenarFormularios"));
        
        final Window ventana = (Window) creaComponente(
                ConstantesWEB.ZUL_FORMULARIOTRAMITE, null, map);
        
        abreVentanaModal(ventana);
        */
    }

    /**
     * Mostrar mensaje.
     */
    public final void onDoubleClick$itemMensaje() {
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
        
        
        listMensajes.setHeight(alto + "px");       
    }  

  
}
