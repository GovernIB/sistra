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
import es.caib.consola.controller.gestor.wmodal.renderer.FormulariosListitemRenderer;
import es.caib.consola.model.Usuario;
import es.caib.consola.model.types.TypeModoAcceso;
import es.caib.consola.util.ConsolaUtil;
import es.caib.sistra.model.Documento;
import es.caib.zkutils.ConstantesZK;
import es.caib.zkutils.zk.composer.BaseComposer;

/**
 * Ventana paso rellenar.
 */
@SuppressWarnings("serial")
public class PasoRellenarWModal extends BaseComposer {
	
	// Instancia
    /** Ventana paso rellenar. */
    private Window wRellenarFormularios;
    /** Ventana version tramite. */
    private VersionTramiteController versionTramiteController;
    /** Modo de apertura. */
    private TypeModoAcceso modo;
    /** Usuario autenticado. */
    private Usuario usuarioLogado;
    /** Alto. */
    private int alto;
    
	// Botones    
    /** Boton borrar formulario. */
    private Button btnQuitar;
    /** Boton editar formulario. */
    private Button btnEditar;
    /** Boton nuevo formulario. */
    private Button btnAnyadir;
    /** Boton subir orden formulario. */
    private Button btnSubir;
    /** Boton bajar orden formulario. */
    private Button btnBajar;
    /** Boton ayuda. */
    private Button btnAyuda;
    
	// Popup menu
	/** Popup nuevo formulario. */
    private Menuitem popupNuevoFormulario;
    /** Popup borrar formulario. */
    private Menuitem popupQuitaFormulario;
    /** Popup editar formulario. */
    private Menuitem popupEditaFormulario;
    /** Popup subir orden formulario. */
    private Menuitem popupSubirFormulario;
    /** Popup bajar orden formulario. */
    private Menuitem popupBajarFormulario;
        	   
    // Lista formularios    
    /** Lista formularios. */
    private Listbox listFormularios;
    /** Atributo list formularios data model de RellenarFormulariosWModal. */
    private BindingListModelList listFormulariosDataModel;
          
    @Override
    public final void doAfterCompose(final Component compPasoRellenar) {
    	
        super.doAfterCompose(compPasoRellenar);
        
        // Obtenemos referencia a pantalla version tramite
        versionTramiteController = (VersionTramiteController) arg.get(ConstantesWEB.VERSIONTRAMITE_CONTROLLER); 
        
        // Modo de acceso
        modo = versionTramiteController.getModo();
        
        // Usuario autenticado
        usuarioLogado = ConsolaUtil.recuperarUsuarioLogado();
         
        // Configura lista formularios
        listFormularios.setItemRenderer(new FormulariosListitemRenderer(
                usuarioLogado.getIdioma()));
        listFormulariosDataModel = new BindingListModelList(generarModel(), true);
        listFormularios.setModel(listFormulariosDataModel);
        
        
        visualizaComponentes();
        
    }

    /**
     * Genera modelo para lista.
     * @return Lista de formularios
     */
	private List<Documento> generarModel() {
		List<Documento> formularios = new ArrayList<Documento>();
        if (versionTramiteController.getVersion().getDocumentos() != null) {
        	for (Iterator it = versionTramiteController.getVersion().getDocumentos().iterator(); it.hasNext();) {
        		Documento doc = (Documento) it.next();
        		if (doc.getTipo() == Documento.TIPO_FORMULARIO) {
        			formularios.add(doc);
        		}
        	}
        }
		return formularios;
	}

    /**
     * Editar formulario.
     */
    public final void onClick$btnEditar() {
        if (listFormularios.getSelectedIndex() == ConstantesZK.SINSELECCION) {
            mostrarMessageBox(
                    Labels.getLabel("rellenarFormularios.noSeleccionado"),
                    Labels.getLabel(ConstantesWEB.WARNING), Messagebox.OK,
                    	Messagebox.EXCLAMATION);
        } else {
            editaFormulario();            
        }
    }

    /**
     * Edita formulario.
     */
    private void editaFormulario() {
        final Map<String, Object> map = new HashMap<String, Object>();
        
        // TODO REVISAR PARAMETROS
        
        final Listitem item = (Listitem) listFormularios.getItems().get(
                listFormularios.getSelectedIndex());
        final Documento formulario = (Documento) item
                .getValue();
        map.put(ConstantesWEB.FORMULARIOTRAMITE, formulario.getCodigo());
        
        // RAFA: Esto xq es asi?? REvisar modos... 
        if (modo.equals(TypeModoAcceso.ALTA)) {
            map.put(ConstantesWEB.PARAM_MODO_ACCESO, TypeModoAcceso.EDICION);
        } else {
            map.put(ConstantesWEB.PARAM_MODO_ACCESO, modo);
        }
        
       // RAFA: Esto xq es asi?? 
        map.put(ConstantesWEB.PADRE,
                    Path.getComponent(ConstantesWEB.WPATH_PASO_RELLENAR));
        
        
        map.put(ConstantesWEB.VERSIONTRAMITE_CONTROLLER, this.versionTramiteController);
        
        final Window ventana = (Window) creaComponente(
                ConstantesWEB.ZUL_FORMULARIO, null, map);
        
        abreVentanaModal(ventana);
    }

    /**
     * Mostrar formulario.
     */
    public final void onDoubleClick$itemFormulario() {
        onClick$btnEditar();        
    }

    /**
     * Alta formulario.
     */
    public final void onClick$btnAnyadir() {
    	final Map<String, Object> map = new HashMap<String, Object>();
        map.put(ConstantesWEB.VERSIONTRAMITE_CONTROLLER, this.versionTramiteController);
        final Window ventana = (Window) creaComponente(
                "/gestor/windows/ges-versionTramite-formulario-alta-win.zul", null, map);
        abreVentanaModal(ventana);                  
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
        
        
        listFormularios.setHeight(alto + "px");       
    }  

  
}
