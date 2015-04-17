package es.caib.consola.controller.gestor.wmodal;

import java.util.HashMap;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Button;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Window;

import es.caib.consola.ConstantesWEB;
import es.caib.consola.controller.gestor.VersionTramiteController;
import es.caib.consola.model.Usuario;
import es.caib.consola.model.types.TypeModoAcceso;
import es.caib.consola.util.ConsolaUtil;
import es.caib.zkutils.zk.composer.BaseComposer;

/**
 * Pago de paso pagar version tramite.
 */
@SuppressWarnings("serial")
public class PagoVersionTramiteWModal extends BaseComposer {
	
	// Instancia
    /** Ventana. */
    private Window wPago;
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
    /** Opción obligatoriedad: obligatoria.*/
    private Radio obligatorio;
    /** Opción obligatoriedad: opcional.*/
    private Radio opcional;
    /** Opción obligatoriedad: dependiente.*/
    private Radio dependiente;
    /** Script dependencia.*/
    private Button btnObligatoriedadScript;    
    /** Tipo pago.*/
    private Radiogroup tipoPago;
    /** Tipo pago: telematico.*/
    private Radio telematico;
    /** Tipo pago: presencial.*/
    private Radio presencial;
    /** Tipo pago: ambos.*/
    private Radio ambos;
    
          
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
    	
    	tipoPago.setSelectedItem(ambos);
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
	    	wPago.setBorder("normal");
	    	wPago.setWidth("850px");
	    }
    }  

  
    /**
     * Abre ventana de modificacion.
     */
    public final void onClick$btnModificar() {
        final Map<String, Object> map = new HashMap<String, Object>();
        map.put(ConstantesWEB.PARAM_MODO_ACCESO, TypeModoAcceso.EDICION);
        map.put(ConstantesWEB.VERSIONTRAMITE_CONTROLLER, versionTramiteController);
        final Window ventana = (Window) creaComponente(ConstantesWEB.ZUL_PAGO, null, map);
        abreVentanaModal(ventana);
    }  
    
    /**
     * Click boton Cancelar.
     */
    public final void onClick$btnCancelar() {
    	wPago.detach();
    }         
    
    /**
     * Al cambiar radio de obligatoriedad mostramos campos asociados.
     */
    public final void onCheck$obligatoriedad() {
        boolean docDependiente = (obligatoriedad.getSelectedItem() == this.dependiente);
        btnObligatoriedadScript.setVisible(docDependiente);	
    }
   
}
