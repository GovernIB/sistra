package es.caib.consola.controller.gestor.wmodal;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.commons.io.IOUtils;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.East;
import org.zkoss.zul.Iframe;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import es.caib.consola.ConstantesWEB;
import es.caib.consola.model.Usuario;
import es.caib.consola.util.ConsolaUtil;
import es.caib.zkutils.zk.composer.BaseComposer;

/**
 * Ventana diseño formulario.
 */
@SuppressWarnings("serial")
public class DisenyoFormularioWModal extends BaseComposer {

	/** Referencia ventana. */
    private Window wDisenyoFormulario;
    /** Usuario logeado.*/
	private Usuario usuarioLogado;
    /** Modo edicion. */
    private String modo;
    
    /** Layout diseño. */
    private Borderlayout blDisenyo;
    /** Zona propiedades dentro layout diseño. */
    private East barraPropiedadesDisenyo;
    /** Ventana propiedades elemento. */
    private Component wProps;
    /** Iframe contenedor HTML. */
    private Iframe iframeContenedorHTML;
    
    /** Layout estructura. */
    private Borderlayout blEstructura;
      
	/**
     * After compose.
     * 
     * @param compDominio
     *            Parámetro comp dominio
     */
    @Override
    public final void doAfterCompose(final Component compDominio) {
     	super.doAfterCompose(compDominio);
    	
     	// Parametros apertura
     	modo = (String) arg.get(ConstantesWEB.PARAM_MODO_ACCESO);
     	
     	// Usuario autenticado
     	usuarioLogado = ConsolaUtil.recuperarUsuarioLogado();
             	
     	// Carga html
		try {
			String html = IOUtils.toString(this.getClass().getResourceAsStream("formTest.html"), "UTF-8");
			cargarHtml(html);
		} catch (IOException e) {
			ConsolaUtil.generaDelegateException(e); 
		}
     	
     	
     	// Ajustar tamaño
        final int height = (Integer) session.getAttribute("DesktopHeight");
        final int heightRecalculado = (int) (height * 0.6768);
        blDisenyo.setHeight(Double.toString(heightRecalculado) + "px");
        blEstructura.setHeight(Double.toString(heightRecalculado) + "px");
    }
	
    /**
     * Carga html.
     * @param pathHtml
     */
    private void cargarHtml(String pathHtml){
    	final String ctype = "text/html";
        final String format = "html";
        // Evetamos problemas de cacheo modificando la url
        final String name = "contenedorHTML" + new Random().nextLong();
        final AMedia media = new AMedia(name, format, ctype, pathHtml);
        iframeContenedorHTML.setContent(media);        
    }
    
	/**
     * Click boton Cancelar.
     */
    public final void onClick$btnCancelar() {
    	wDisenyoFormulario.detach();
    }
    
    /**
     * Seleccion elemento en iframe.
     * 
     * @param event
     *            Parámetro event
     */
    public final void onSelect$iframeContenedorHTML(final Event event) {
        final Event origin = ((ForwardEvent) event).getOrigin();
        final String id = (String) origin.getData();
        
        mostrarPropiedadesElemento(id) ;
    }
    
    
    /**
     * Muestra propiedades elemento.
     * @param idElemento Id elemento
     */
    private final void mostrarPropiedadesElemento(String idElemento) {
    	final Map<String, Object> mapAtributos = new HashMap<String, Object>();
    	mapAtributos.put(ConstantesWEB.PARAM_MODO_ACCESO, modo);
    	mapAtributos.put(ConstantesWEB.PARAMETER_ID_ELEMENTO_FORMULARIO, idElemento);
    	
    	final String zul = "/gestor/windows/ges-disenyoFormulario-propiedadesElemento-win.zul";
        if (wProps != null) {
            wProps.detach();
            wProps = null;
        }
        wProps = creaComponente(zul, barraPropiedadesDisenyo, mapAtributos);
        barraPropiedadesDisenyo.setOpen(true);
    }

}
