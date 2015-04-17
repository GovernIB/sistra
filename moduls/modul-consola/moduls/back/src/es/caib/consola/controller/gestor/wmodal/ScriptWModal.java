package es.caib.consola.controller.gestor.wmodal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.zkoss.codemirror.Codemirror;
import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zul.Button;
import org.zkoss.zul.East;
import org.zkoss.zul.Iframe;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.North;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Window;
import org.zkoss.zul.api.Textbox;

import es.caib.consola.ConstantesWEB;
import es.caib.consola.controller.gestor.model.ModeloVisualizarPlugin;
import es.caib.consola.controller.gestor.model.Plugin;
import es.caib.consola.controller.gestor.wmodal.renderer.PluginScriptListitemRender;
import es.caib.consola.model.Usuario;
import es.caib.consola.util.ConsolaUtil;
import es.caib.sistra.model.Dominio;
import es.caib.zkutils.zk.composer.BaseComposer;


/**
 * Ventana de script.
 */
@SuppressWarnings("serial")
public class ScriptWModal extends BaseComposer {

	 /** Referencia ventana. */
    private Window wScript;
    
    /** Componente padre. */
    private Component padre;	

    /** Atributo datos de ScriptWModal. */
    private North datos;

    /** Atributo sirve de ScriptWModal. */
    private Label sirve;

    /** Atributo devuelve de ScriptWModal. */
    private Label devuelve;

    /** Atributo script de ScriptWModal. */
    private Codemirror script;

    /** Atributo s2 de ScriptWModal. */
    private East s2;

    /** Atributo tabs de ScriptWModal. */
    private Tabbox tabs;

    /** Atributo tab plugin de ScriptWModal. */
    private Tab tabPlugin;

    /** Atributo tab dominios de ScriptWModal. */
    private Tab tabDominios;

    /** Atributo tab mensajes de ScriptWModal. */
    private Tab tabMensajes;

    /** Atributo tab formularios de ScriptWModal. */
    private Tab tabFormularios;

    /** Atributo tree forms de ScriptWModal. */
    private Tree treeForms;

    /** Atributo plugins de ScriptWModal. */
    private List<Plugin> plugins = new ArrayList<Plugin>();

    /** Atributo dominios de ScriptWModal. */
    private List<Dominio> dominios = new ArrayList<Dominio>();

    /** Atributo list plugins de ScriptWModal. */
    private Listbox listPlugins;

    /** Atributo list dominios de ScriptWModal. */
    private Listbox listDominios;

    /** Atributo list mensajes de ScriptWModal. */
    private Listbox listMensajes;

    /** Atributo msg de ScriptWModal. */
    private Label msg;

    /** Atributo btn aceptar de ScriptWModal. */
    private Button btnAceptar;

    /** Atributo btn cancelar de ScriptWModal. */
    private Button btnCancelar;

    /** Atributo btn eliminar de ScriptWModal. */
    private Button btnEliminar;
   
    /** Atributo altow script version de ScriptWModal. */
    private int altowScriptVersion;

    /** Atributo anchow script version de ScriptWModal. */
    private int anchowScriptVersion;

    /** Atributo alto script de ScriptWModal. */
    private int altoScript;

    /** Atributo alto tabs de ScriptWModal. */
    private int altoTabs;

    /** Atributo ayuda pop up de ScriptWModal. */
    private Popup ayudaPopUp;

    /** Atributo modo de ScriptWModal. */
    private String modo;

    /** Atributo map plugins de ScriptWModal. */
    private Map<String, Plugin> mapPlugins;

    /** Atributo numero instancias de ScriptWModal. */
    private Integer numeroInstancias;

    /** Atributo mostrar tab formulario de ScriptWModal. */
    private boolean mostrarTabFormulario = true;

   
    /** Atributo model list mensajes de ScriptWModal. */
    private BindingListModelList modelListMensajes;

    /** Atributo usuario logado de ScriptWModal. */
    private Usuario usuarioLogado;

    /** Atributo btn nuevo mensaje de ScriptWModal. */
    private Button btnNuevoMensaje;

    /** Atributo btn edita mensaje de ScriptWModal. */
    private Button btnEditaMensaje;

    /** Atributo btn elimina mensaje de ScriptWModal. */
    private Button btnEliminaMensaje;

    /** Atributo ayudahtml de ScriptWModal. */
    private Iframe ayudahtml;

    private Textbox textoCopiarClipboard;

    /*        
    private List<ElementoFormulario> elementosActuales = new ArrayList<ElementoFormulario>();
    private List<LiteralScript> mensajesScript;
     */
    
    /*
     * (non-Javadoc)
     * 
     * @see es.gva.dgm.tra.util.zk.composer.BaseComposer#doAfterCompose(org
     * .zkoss .zk.ui.Component)
     */
    @Override
    public final void doAfterCompose(final Component compScript) {
       
    	super.doAfterCompose(compScript);

    	usuarioLogado = ConsolaUtil.recuperarUsuarioLogado();
    	
    	// Plugins
    	renderPlugins(null);
    	
    	// Ajuste visualizacion componentea
    	visualizarComponentes();
    	
    	// Ajuste datos script
    	// TODO MOCK
    	sirve.setValue("Indica el NIF del representante");
    	devuelve.setValue("Cadena con el NIF, sin espacios, ni guiones y con la letra en mayúscula");
    }

    /**
     * Muestra en list de plugins los plugins.
     * @param pluginPadre
     */
    private void renderPlugins(String pluginPadre) {
    	List<ModeloVisualizarPlugin> listaValores = generarModelPlugins(pluginPadre);
    	final BindingListModelList lml = new BindingListModelList(listaValores, true);
        listPlugins.setModel(lml);
        listPlugins.setItemRenderer(new PluginScriptListitemRender());
    }
    
    /**
     * Genera lista de plugins.
     * @return lista de plugins.
     */
    private List<ModeloVisualizarPlugin> generarModelPlugins(String pluginPadre){
    	List<ModeloVisualizarPlugin> res = new ArrayList<ModeloVisualizarPlugin>();
    	
    	// TODO REVISAR PROPS PLUGINS
    	
    	ModeloVisualizarPlugin plg;
    	
    	plg = new ModeloVisualizarPlugin();
    	plg.setNombre("ERRORSCRIPT");
	    plg.setDescripcion("ERRORSCRIPT");
	    plg.setFicheroAyuda("ERRORSCRIPT");
	    plg.setPlugin(true);
	    res.add(plg);
	    
    	if ("ERRORSCRIPT".equals(pluginPadre)) {
    		plg = new ModeloVisualizarPlugin();
    		plg.setNombrePadre("ERRORSCRIPT");
    		plg.setNombre("ERRORSCRIPT.setExisteError");
    		plg.setDescripcion("setExisteError");	    	
	    	plg.setFicheroAyuda("ERRORSCRIPT_setExisteError");
	    	plg.setTextoCopiar("ERRORSCRIPT.setExisteError(true);");
	    	plg.setMetodo(true);
	    	res.add(plg);
	    	
	    	plg = new ModeloVisualizarPlugin();
    		plg.setNombrePadre("ERRORSCRIPT");
    		plg.setNombre("ERRORSCRIPT.setMensajeError");
	    	plg.setDescripcion("setMensajeError");	    	
	    	plg.setFicheroAyuda("ERRORSCRIPT_setMensajeError");
	    	plg.setTextoCopiar("ERRORSCRIPT.setMensajeError('');");
	    	plg.setMetodo(true);
	    	res.add(plg);
	    	
	    	plg = new ModeloVisualizarPlugin();
    		plg.setNombrePadre("ERRORSCRIPT");
    		plg.setNombre("ERRORSCRIPT.setMensajeDinamicoError");
	    	plg.setDescripcion("setMensajeDinamicoError");
	    	plg.setFicheroAyuda("ERRORSCRIPT_setMensajeDinamicoError");
	    	plg.setTextoCopiar("ERRORSCRIPT.setMensajeDinamicoError('');");
	    	plg.setMetodo(true);
	    	res.add(plg);
    	}
    	
    	plg = new ModeloVisualizarPlugin();
    	plg.setNombre("PLUGIN_FORMULARIOS");
	    plg.setDescripcion("PLUGIN_FORMULARIOS");
	    plg.setFicheroAyuda("PLUGIN_FORMULARIOS");
	    plg.setPlugin(true);
	    res.add(plg);
	    
    	if ("PLUGIN_FORMULARIOS".equals(pluginPadre)) {
    		plg = new ModeloVisualizarPlugin();
    		plg.setNombrePadre("PLUGIN_FORMULARIOS");
    		plg.setNombre("PLUGIN_FORMULARIOS.getDatoFormulario");
    		plg.setDescripcion("getDatoFormulario");
	    	plg.setFicheroAyuda("PLUGIN_FORMULARIOS_getDatoFormulario");
	    	plg.setTextoCopiar("PLUGIN_FORMULARIOS.getDatoFormulario('',1,'/FORMULARIO/')");
	    	plg.setMetodo(true);
	    	res.add(plg);
	    	
	    	plg = new ModeloVisualizarPlugin();
    		plg.setNombrePadre("PLUGIN_FORMULARIOS");
	    	plg.setNombre("PLUGIN_FORMULARIOS.getNumeroValoresCampo");
	    	plg.setDescripcion("getNumeroValoresCampo");
	    	plg.setFicheroAyuda("PLUGIN_FORMULARIOS_getNumeroValoresCampo");
	    	plg.setTextoCopiar("PLUGIN_FORMULARIOS.getNumeroValoresCampo('',1,'/FORMULARIO/')");
	    	plg.setMetodo(true);
	    	res.add(plg);
    	}
    	
    	plg = new ModeloVisualizarPlugin();
    	plg.setNombre("PLUGIN_DATOSSESION");
	    plg.setDescripcion("PLUGIN_DATOSSESION");
	    plg.setFicheroAyuda("PLUGIN_DATOSSESION");
	    plg.setPlugin(true);
	    res.add(plg);
    	
	    
	    plg = new ModeloVisualizarPlugin();
    	plg.setNombre("PLUGIN_DOMINIOS");
	    plg.setDescripcion("PLUGIN_DOMINIOS");
	    plg.setFicheroAyuda("PLUGIN_DOMINIOS");
	    plg.setPlugin(true);
	    res.add(plg);
	    
	    plg = new ModeloVisualizarPlugin();
    	plg.setNombre("PLUGIN_TRAMITE");
	    plg.setDescripcion("PLUGIN_TRAMITE");
	    plg.setFicheroAyuda("PLUGIN_TRAMITE");
	    plg.setPlugin(true);
	    res.add(plg);
	    
	    plg = new ModeloVisualizarPlugin();
    	plg.setNombre("PLUGIN_PARAMETROSINICIO");
	    plg.setDescripcion("PLUGIN_PARAMETROSINICIO");
	    plg.setFicheroAyuda("PLUGIN_PARAMETROSINICIO");
	    plg.setPlugin(true);
	    res.add(plg);
	    
	    plg = new ModeloVisualizarPlugin();
    	plg.setNombre("PLUGIN_MENSAJES");
	    plg.setDescripcion("PLUGIN_MENSAJES");
	    plg.setFicheroAyuda("PLUGIN_MENSAJES");
	    plg.setPlugin(true);
	    res.add(plg);
	    
	    plg = new ModeloVisualizarPlugin();
    	plg.setNombre("PLUGIN_VALIDACIONES");
	    plg.setDescripcion("PLUGIN_VALIDACIONES");
	    plg.setFicheroAyuda("PLUGIN_VALIDACIONES");
	    plg.setPlugin(true);
	    res.add(plg);
	    
	    plg = new ModeloVisualizarPlugin();
    	plg.setNombre("PLUGIN_LOG");
	    plg.setDescripcion("PLUGIN_LOG");
	    plg.setFicheroAyuda("PLUGIN_LOG");
	    plg.setPlugin(true);
	    res.add(plg);
	    
	    
    	return res;    
    }
    
    /**
     * Click sobre plugin: muestra ayuda plugin.
     */
    public final void onClick$plugin() {
        ayudahtml.setSrc("script/ayuda/fondo.html");
        if (listPlugins.getSelectedItems() != null) {
        	Listitem item = listPlugins.getSelectedItem();
	        final ModeloVisualizarPlugin visualizaPlugin = (ModeloVisualizarPlugin) item
	                .getValue();
	        ayudahtml.setWidth(anchowScriptVersion + "px");
	        ayudahtml.setHeight(altoScript + "px");
	        ayudahtml.setSrc("ayuda/script/HOME.html?archivo="
	                + usuarioLogado.getIdioma() + "/" + visualizaPlugin.getFicheroAyuda() + ".html" + "#top");
	        ayudaPopUp.open(listPlugins, "start_before");
            
        }
    }
    
    /**
     * Doble click sobre plugin: si es plugin lista metodos, si es metodo copia. 
     */
    public final void onDoubleClick$plugin() {
    
    	if (listPlugins.getSelectedItem() == null){
        	return;
        }
    	
    	Listitem itemSelected = listPlugins.getSelectedItem();
    	final ModeloVisualizarPlugin visualizaPlugin = (ModeloVisualizarPlugin) itemSelected.getValue();
    	
    	if (visualizaPlugin.getNombrePadre() != null) {
    		// Es metodo -> copiamos
    		script.setValue(script.getValue() + visualizaPlugin.getTextoCopiar());
    	} else {
    		// Es plugin -> desplegamos metodos
    		renderPlugins(visualizaPlugin.getNombre());
    	}   
    
    }
    
    /**
     * Ajusta visualizacion componentes.
     * @param porcentaje Porcentaje
     */
    private void visualizarComponentes() {
    	int porcentajeScript = 80;
    	if (!datos.isOpen()) {        	
    		porcentajeScript = 97;
        }
    	
        final int altoPantalla = (Integer) session
                .getAttribute("DesktopHeight");
        final int anchoPantalla = (Integer) session
        .getAttribute("DesktopWidth");

        String tempS = wScript.getHeight();
        double tempD1;
        double tempD2;

        if (tempS.indexOf("%") >= 0) {
            tempS = tempS.substring(0, tempS.indexOf("%"));
            tempD1 = Double.valueOf(tempS) / 100;
        } else {
            tempD1 = Double.valueOf(tempS);
        }

        tempD2 = tempD1 * altoPantalla;
        altowScriptVersion = (int) tempD2;

        tempD1 = Double.valueOf(porcentajeScript) / 100;
        tempD2 = altowScriptVersion;
        tempD2 -= ConstantesWEB.HEADER_FOOTER_WIN_HEIGHT;
        tempD2 -= 30; // cabecera del script
        tempD2 = tempD1 * (int) tempD2;
        altoScript = (int) tempD2;
        altoTabs = altoScript;
        altoScript -= 10; // recortar un poco
        script.setHeight(altoScript + "px");

        tabs.setHeight(altoTabs + "px");
        
        tempD2 = tempD1 * anchoPantalla;
        tempD2 -= 40;
        anchowScriptVersion = (int) tempD2;

    }
    
    /**
     * Si se muestran datos ajustamos pantalla.
     */
    public final void onOpen$datos() {
    	// Ajuste visualizacion componentea
    	visualizarComponentes();
    }
    
    /**
     * Click boton Cancelar.
     */
    public final void onClick$btnCancelar() {
    	wScript.detach();
    }
}
