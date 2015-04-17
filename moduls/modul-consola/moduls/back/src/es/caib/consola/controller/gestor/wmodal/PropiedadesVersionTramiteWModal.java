package es.caib.consola.controller.gestor.wmodal;

import java.util.HashMap;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Hlayout;
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
 * Ventana propiedades tramite.
 */
@SuppressWarnings("serial")
public class PropiedadesVersionTramiteWModal extends BaseComposer {
	
	
	// Instancia
    /** Ventana propiedades version. */
    private Window wPropiedadesVersion;
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
    /** Checkbox circuito reducido.*/
    private Checkbox circuitoReducido;
    /** Checkbox ir a url fin tras terminar (para circuito reducido).*/
    private Checkbox circuitoReducidoIrUrlFin;
    /** Radio permitir notificacion telematica.*/
    private Radiogroup permiteNotificacionTelematica;
    /** Opción no permitir notificacion telematica.*/
    private Radio noPermitida;
    /** Opción permitir notificacion telematica.*/
    private Radio permitida;
    /** Opción obligatoria notificacion telematica.*/
    private Radio obligatoria;
    /** Checkbox notificacionPermitirSMS*/
    private Checkbox notificacionPermitirSMS;
    /** Layout que contiene scripts notificacion. */
    private Hlayout scriptsNotif;
    /** Layout que contiene script SMS. */
    private Hlayout scriptSMS;
          
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
    	permiteNotificacionTelematica.setSelectedItem(noPermitida);
    	onCheck$permiteNotificacionTelematica();
    	onCheck$notificacionPermitirSMS();
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
	    	 wPropiedadesVersion.setBorder("normal");
	    	 wPropiedadesVersion.setWidth("850px");
	    }
    }  

  
    /**
     * Abre ventana de modificacion.
     */
    public final void onClick$btnModificar() {
        final Map<String, Object> map = new HashMap<String, Object>();
        map.put(ConstantesWEB.PARAM_MODO_ACCESO, TypeModoAcceso.EDICION);
        map.put(ConstantesWEB.VERSIONTRAMITE_CONTROLLER, versionTramiteController);
        final Window ventana = (Window) creaComponente(ConstantesWEB.ZUL_PROPÌEDADES_VERSION, null, map);
        abreVentanaModal(ventana);
    }  
    
    /**
     * Click boton Cancelar.
     */
    public final void onClick$btnCancelar() {
    	wPropiedadesVersion.detach();
    }
    
    /**
     * Click boton Rpdo.
     */
    public final void onClick$btnNifRpteScript() {
    	 final Map<String, Object> map = new HashMap<String, Object>();
         map.put(ConstantesWEB.PARAM_MODO_ACCESO, TypeModoAcceso.EDICION);
         map.put(ConstantesWEB.VERSIONTRAMITE_CONTROLLER, versionTramiteController);
         final Window ventana = (Window) creaComponente(ConstantesWEB.ZUL_SCRIPT, null, map);
         abreVentanaModal(ventana);
    }    
    
    /**
     * Si se marca check de circuito reducido hacemos visible check de ir a url fin.
     */
    public final void onCheck$circuitoReducido() {
    	circuitoReducidoIrUrlFin.setVisible(circuitoReducido.isChecked());
    }
    
    /**
     * Al cambiar radio de permitir notificacion telematica mostramos campos asociados.
     */
    public final void onCheck$permiteNotificacionTelematica() {
        boolean noPermitidaCheck = (permiteNotificacionTelematica.getSelectedItem() == noPermitida);
        notificacionPermitirSMS.setVisible(!noPermitidaCheck);
        scriptsNotif.setVisible(!noPermitidaCheck);	
    }
    
    /**
     * Si se marca check de permitir SMS hacemos visible script SMS.
     */
    public final void onCheck$notificacionPermitirSMS() {
    	scriptSMS.setVisible(notificacionPermitirSMS.isChecked());
    }
}
