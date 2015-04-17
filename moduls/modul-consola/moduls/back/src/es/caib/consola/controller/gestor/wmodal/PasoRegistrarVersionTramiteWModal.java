package es.caib.consola.controller.gestor.wmodal;

import java.util.HashMap;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Button;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Vlayout;
import org.zkoss.zul.Window;

import es.caib.consola.ConstantesWEB;
import es.caib.consola.controller.gestor.VersionTramiteController;
import es.caib.consola.model.Usuario;
import es.caib.consola.model.types.TypeModoAcceso;
import es.caib.consola.util.ConsolaUtil;
import es.caib.zkutils.zk.composer.BaseComposer;

/**
 * Paso registrar version tramite.
 */
@SuppressWarnings("serial")
public class PasoRegistrarVersionTramiteWModal extends BaseComposer {
	
	// Instancia
    /** Ventana propiedades version. */
    private Window wPasoRegistrarVersion;
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
    /** Destino.*/
    private Radiogroup destino;
    /** Opción destino: registro.*/
    private Radio registro;
    /** Opción destino: bandeja.*/
    private Radio bandeja;
    /** Opción destino: consulta.*/
    private Radio consulta;
    /** Opción destino: asistente.*/
    private Radio asistente;
    /** Campos destino registro.*/
    private Row opcionesRegistroRow;
    /** Campos destino registro.*/
    private Row destinoRegistroRow;
    /** Campos destino registro script.*/
    private Row destinoRegistroScriptRow;
    /** Campos destino registro script.*/
    private Row finalizacionRow;
    /** Campos justificante.*/
    private Row justificanteRegistroRow;
    /** Campos consulta.*/
    private Row datosConsultaRow;
    /** Tipo acceso consulta.*/
    private Radiogroup tipoAccesoConsulta;
    /** Tipo acceso consulta: consultaEJB.*/
    private Radio consultaEJB;
    /** Tipo acceso consulta: consultaWS.*/
    private Radio consultaWS;
    /** Campos consultaEJB.*/
    private Vlayout ejbFields;
    /** Campos consultaEJB.*/
    private Vlayout wsFields;
    /** Tipo localizacion.*/
    private Radiogroup ejbLocalizacion;
    /** Tipo localizacion: local.*/
    private Radio ejbLocalizacionLocal;
    /** Tipo localizacion: remoto.*/
    private Radio ejbLocalizacionRemoto;
    /** Campos url localizacion.*/
    private Hlayout ejbUrlFields;
    
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
    	destino.setSelectedItem(registro);
    	onCheck$destino();
    	
    	tipoAccesoConsulta.setSelectedItem(consultaEJB);
    	onCheck$tipoAccesoConsulta();
    	
    	ejbLocalizacion.setSelectedItem(ejbLocalizacionLocal);
    	onCheck$ejbLocalizacion();
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
	    	wPasoRegistrarVersion.setBorder("normal");
	    	wPasoRegistrarVersion.setWidth("1000px");	    
	    }
    }  

  
    /**
     * Abre ventana de modificacion.
     */
    public final void onClick$btnModificar() {
        final Map<String, Object> map = new HashMap<String, Object>();
        map.put(ConstantesWEB.PARAM_MODO_ACCESO, TypeModoAcceso.EDICION);
        map.put(ConstantesWEB.VERSIONTRAMITE_CONTROLLER, versionTramiteController);
        final Window ventana = (Window) creaComponente(ConstantesWEB.ZUL_PASO_REGISTRAR, null, map);
        abreVentanaModal(ventana);
    }  
    
    /**
     * Click boton Cancelar.
     */
    public final void onClick$btnCancelar() {
    	wPasoRegistrarVersion.detach();
    }             
    
    /**
     * Al cambiar radio de destino mostramos campos asociados.
     */
    public final void onCheck$destino() {
        boolean registrar = (destino.getSelectedItem() == this.registro || destino.getSelectedItem() == this.bandeja);
        boolean consultar = (destino.getSelectedItem() == this.consulta);
        
        opcionesRegistroRow.setVisible(registrar);
        destinoRegistroRow.setVisible(registrar);
        destinoRegistroScriptRow.setVisible(registrar);
        finalizacionRow.setVisible(registrar);
        justificanteRegistroRow.setVisible(registrar);
        datosConsultaRow.setVisible(consultar);        
    }
    
    /**
     * Al cambiar radio de tipoAccesoConsulta mostramos campos asociados.
     */
    public final void onCheck$tipoAccesoConsulta() {
        
    	boolean ejb = (tipoAccesoConsulta.getSelectedItem() == this.consultaEJB);
        boolean ws = (tipoAccesoConsulta.getSelectedItem() == this.consultaWS);
        
        wsFields.setVisible(ws);
        ejbFields.setVisible(ejb);             
    }
    
    /**
     * Al cambiar radio de localizacion mostramos campos asociados.
     */
    public final void onCheck$ejbLocalizacion() {
    	boolean esLocal = (ejbLocalizacion.getSelectedItem() == this.ejbLocalizacionLocal);      
    	ejbUrlFields.setVisible(!esLocal);            
    }
}
