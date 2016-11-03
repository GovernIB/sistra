package es.caib.consola.controller.gestor.wmodal;

import java.util.HashMap;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Vlayout;
import org.zkoss.zul.Window;

import es.caib.consola.ConstantesWEB;
import es.caib.consola.controller.gestor.VersionTramiteController;
import es.caib.consola.model.Usuario;
import es.caib.consola.model.types.TypeModoAcceso;
import es.caib.consola.util.ConsolaUtil;
import es.caib.zkutils.zk.composer.BaseComposer;

/**
 * Anexo de paso anexar version tramite.
 */
@SuppressWarnings("serial")
public class AnexoVersionTramiteWModal extends BaseComposer {
	
	// Instancia
    /** Ventana. */
    private Window wAnexo;
    /** Ventana version tramite. */
    private VersionTramiteController versionTramiteController;
    /** Modo de apertura. */
    private String modo;
    /** Usuario autenticado. */
    private Usuario usuarioLogado;
    /** Alto. */
    private int alto;
    
	// Botones    
    /** Boton modificar. */
    private Button btnModificar;
    /** Boton guardar. */
    private Button btnGuardar;
    /** Boton guardar y continuar. */
    private Button btnGuardarContinuar;
    /** Boton cancelar. */
    private Button btnCancelar;    
    /** Boton ayuda. */
    private Button btnAyuda;          
        	       
   // Campos
    /** Obligatoriedad.*/
    private Radiogroup obligatoriedad;
    /** Opcion obligatoriedad: obligatoria.*/
    private Radio obligatorio;
    /** Opcion obligatoriedad: opcional.*/
    private Radio opcional;
    /** Opcion obligatoriedad: dependiente.*/
    private Radio dependiente;
    /** Script dependencia.*/
    private Button btnObligatoriedadScript;
    /** Checkbox firma digital.*/
    private Checkbox firmaDigital;
    /** Layout que contiene scripts firmante. */
    private Hlayout firmanteLayout;
    /** Layout que contiene scripts content type. */
    private Hlayout contentTypeLayout;
    /** Presentacion.*/
    private Radiogroup presentacion;
    /** Opcion presentacion: telematica.*/
    private Radio telematica;
    /** Opcion presentacion: presencial.*/
    private Radio presencial;      
    /** Layout que contiene campos opcion telematica. */
    private Vlayout telematicaFields;
    /** Checkbox generico.*/
    private Checkbox generico;
    /** Layout que contiene campos generico. */
    private Hlayout genericoFields;
        
    @Override
    public final void doAfterCompose(final Component compPasoRellenar) {
    	
        super.doAfterCompose(compPasoRellenar);
        
        // Obtenemos referencia a pantalla version tramite
        versionTramiteController = (VersionTramiteController) arg.get(ConstantesWEB.VERSIONTRAMITE_CONTROLLER); 
        
        // Modo de acceso
        modo = (String) arg.get(ConstantesWEB.PARAM_MODO_ACCESO);
        
        // Usuario autenticado
        usuarioLogado = ConsolaUtil.recuperarUsuarioLogado();
        
        // Visualiza componentes
        visualizaComponentes();
        
        // Mapea datos
        mapeaDatos();
        
    }
   
    /**
     * Realiza mapeo de datos.
     */
    private void mapeaDatos() {
    	// TODO PENDIENTE
    	obligatoriedad.setSelectedItem(obligatorio);
    	onCheck$obligatoriedad();  
    	onCheck$firmaDigital();
    	
    	presentacion.setSelectedItem(telematica);
    	onCheck$presentacion();
    	
    	onCheck$generico();
	}

	/**
     * Visualizar componentes.
     */
    public final void visualizaComponentes() {
    	boolean ficha = false;
    	boolean edicion = false;
    	boolean consulta = false;
    	
    	if (ConstantesWEB.MODOFICHA.equals(modo)) {
    		 ficha = true;       	        	   
    	} else if (ConstantesWEB.MODOCONSULTA.equals(modo)) {
    		consulta = true;
    	} else {
    		edicion = true;
    	}
    	
    	btnModificar.setVisible(ficha);
	    btnGuardar.setVisible(edicion);
	    btnGuardarContinuar.setVisible(edicion);
	    btnCancelar.setVisible(edicion || consulta);
	    
	    if (!ficha) {
	    	wAnexo.setBorder("normal");
	    	wAnexo.setWidth("850px");
	    }
    }  

  
    /**
     * Abre ventana de modificacion.
     */
    public final void onClick$btnModificar() {
        final Map<String, Object> map = new HashMap<String, Object>();
        map.put(ConstantesWEB.PARAM_MODO_ACCESO, TypeModoAcceso.EDICION);
        map.put(ConstantesWEB.VERSIONTRAMITE_CONTROLLER, versionTramiteController);
        final Window ventana = (Window) creaComponente(ConstantesWEB.ZUL_ANEXO, null, map);
        abreVentanaModal(ventana);
    }  
    
    /**
     * Click boton Cancelar.
     */
    public final void onClick$btnCancelar() {
    	wAnexo.detach();
    }         
    
    /**
     * Al cambiar radio de obligatoriedad mostramos campos asociados.
     */
    public final void onCheck$obligatoriedad() {
        boolean docDependiente = (obligatoriedad.getSelectedItem() == this.dependiente);
        btnObligatoriedadScript.setVisible(docDependiente);	
    }
    
    /**
     * Si se marca check de firma hacemos visible scripts.
     */
    public final void onCheck$firmaDigital() {
    	firmanteLayout.setVisible(firmaDigital.isChecked());
    	contentTypeLayout.setVisible(firmaDigital.isChecked());
    }
    
    /**
     * Al cambiar radio de presentacion mostramos campos asociados.
     */
    public final void onCheck$presentacion() {
        boolean docPresencial = (presentacion.getSelectedItem() == this.presencial);
        telematicaFields.setVisible(!docPresencial);	
    }   
    
    /**
     * Al cambiar radio de generico mostramos campos asociados.
     */
    public final void onCheck$generico() {
    	genericoFields.setVisible(generico.isChecked());	
    }   
}
