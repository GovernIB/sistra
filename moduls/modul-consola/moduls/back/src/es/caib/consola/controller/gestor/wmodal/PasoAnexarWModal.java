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
import es.caib.consola.controller.gestor.wmodal.renderer.AnexosListitemRenderer;
import es.caib.consola.model.Usuario;
import es.caib.consola.model.types.TypeModoAcceso;
import es.caib.consola.util.ConsolaUtil;
import es.caib.sistra.model.Documento;
import es.caib.zkutils.ConstantesZK;
import es.caib.zkutils.zk.composer.BaseComposer;

/**
 * Ventana paso anexar.
 */
@SuppressWarnings("serial")
public class PasoAnexarWModal extends BaseComposer {
	
	// Instancia
    /** Ventana paso rellenar. */
    private Window wAnexarDocumentos;
    /** Ventana version tramite. */
    private VersionTramiteController versionTramiteController;
    /** Modo de apertura. */
    private TypeModoAcceso modo;
    /** Usuario autenticado. */
    private Usuario usuarioLogado;
    /** Alto. */
    private int alto;
    
	// Botones    
    /** Boton borrar documento. */
    private Button btnQuitar;
    /** Boton editar documento. */
    private Button btnEditar;
    /** Boton nuevo documento. */
    private Button btnAnyadir;
    /** Boton subir orden documento. */
    private Button btnSubir;
    /** Boton bajar orden documento. */
    private Button btnBajar;
    /** Boton ayuda. */
    private Button btnAyuda;
    
	// Popup menu
	/** Popup nuevo documento. */
    private Menuitem popupNuevoDocumento;
    /** Popup borrar documento. */
    private Menuitem popupQuitaDocumento;
    /** Popup editar documento. */
    private Menuitem popupEditaDocumento;
    /** Popup subir orden documento. */
    private Menuitem popupSubirDocumento;
    /** Popup bajar orden documento. */
    private Menuitem popupBajarDocumento;
        	   
    // Lista documentos    
    /** Lista documentos. */
    private Listbox listAnexos;
    /** Model list documentos. */
    private BindingListModelList listAnexosDataModel;
          
    @Override
    public final void doAfterCompose(final Component compPasoRellenar) {
    	
        super.doAfterCompose(compPasoRellenar);
        
        // Obtenemos referencia a pantalla version tramite
        versionTramiteController = (VersionTramiteController) arg.get(ConstantesWEB.VERSIONTRAMITE_CONTROLLER); 
        
        // Modo de acceso
        modo = versionTramiteController.getModo();
        
        // Usuario autenticado
        usuarioLogado = ConsolaUtil.recuperarUsuarioLogado();
         
        // Configura lista documentos
        listAnexos.setItemRenderer(new AnexosListitemRenderer(
                usuarioLogado.getIdioma()));
        listAnexosDataModel = new BindingListModelList(generarModel(), true);
        listAnexos.setModel(listAnexosDataModel);
        
        
        visualizaComponentes();
        
    }

    /**
     * Genera modelo para lista.
     * @return Lista de documentos
     */
	private List<Documento> generarModel() {
		List<Documento> documentos = new ArrayList<Documento>();
        if (versionTramiteController.getVersion().getDocumentos() != null) {
        	for (Iterator it = versionTramiteController.getVersion().getDocumentos().iterator(); it.hasNext();) {
        		Documento doc = (Documento) it.next();
        		if (doc.getTipo() == Documento.TIPO_ANEXO) {
        			documentos.add(doc);
        		}
        	}
        }
		return documentos;
	}

    /**
     * Editar documento.
     */
    public final void onClick$btnEditar() {
        if (listAnexos.getSelectedIndex() == ConstantesZK.SINSELECCION) {
            mostrarMessageBox(
                    Labels.getLabel("anexarDocumentos.noSeleccionado"),
                    Labels.getLabel(ConstantesWEB.WARNING), Messagebox.OK,
                    	Messagebox.EXCLAMATION);
        } else {
            editaAnexo();            
        }
    }

    /**
     * Edita documento.
     */
    private void editaAnexo() {
        final Map<String, Object> map = new HashMap<String, Object>();
        
        final Listitem item = (Listitem) listAnexos.getItems().get(
                listAnexos.getSelectedIndex());
        final Documento documento = (Documento) item
                .getValue();
        map.put(ConstantesWEB.ANEXO, documento.getCodigo());
        
     // RAFA: Esto xq es asi?? REvisar modos... 
        if (modo == TypeModoAcceso.ALTA) {
            map.put(ConstantesWEB.PARAM_MODO_ACCESO, TypeModoAcceso.EDICION);
        } else {
            map.put(ConstantesWEB.PARAM_MODO_ACCESO, modo);
        }
        
       // RAFA: Esto xq es asi?? 
        map.put(ConstantesWEB.PADRE,
                    Path.getComponent("/priWindow/contenedorPrincipal/verTramWindow/detalle/wAnexarDocumentos"));
        
        final Window ventana = (Window) creaComponente(
                ConstantesWEB.ZUL_ANEXO, null, map);
        
        abreVentanaModal(ventana);
    }

    /**
     * Mostrar documento.
     */
    public final void onDoubleClick$itemAnexo() {
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
        
        
        listAnexos.setHeight(alto + "px");       
    }  

  
}
