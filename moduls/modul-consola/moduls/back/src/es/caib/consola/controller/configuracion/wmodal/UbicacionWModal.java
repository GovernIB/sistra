package es.caib.consola.controller.configuracion.wmodal;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import es.caib.consola.ConstantesWEB;
import es.caib.consola.model.Usuario;
import es.caib.consola.model.types.TypeModoAcceso;
import es.caib.consola.util.ConsolaUtil;
import es.caib.consola.util.GTTUtilsWeb;
import es.caib.redose.model.Ubicacion;
import es.caib.redose.persistence.delegate.DelegateException;
import es.caib.redose.persistence.delegate.DelegateUtil;
import es.caib.zkutils.zk.composer.BaseComposer;

/**
 * Ventana de edicion ubicacion.
 */
@SuppressWarnings("serial")
public class UbicacionWModal extends BaseComposer {

	/** Referencia ventana. */
    private Window wUbicacion;
    /** Usuario logeado.*/
	private Usuario usuarioLogado;
    /** Modo edicion. */
    private TypeModoAcceso modo;
    /** Ubicacion editado.*/
    private Ubicacion ubicacion;    
    /** Clase java. */
    private Textbox clase;
    /** Codigo. */
    private Textbox codigo;
    /** Descripcion. */
    private Textbox descripcion;
    /** Defecto. */
    private Checkbox defecto;
    
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
     	modo = (TypeModoAcceso) arg.get(ConstantesWEB.PARAM_MODO_ACCESO);
     	ubicacion = (Ubicacion) arg.get(ConstantesWEB.PARAM_OBJETO_EDICION);   
     	
     	// Si se le pasa organo, refrescamos valores
     	refrescarDatos();
     	
     	// Usuario autenticado
     	usuarioLogado = ConsolaUtil.recuperarUsuarioLogado();
        
     	// Mapea datos a los controles de la pantalla
     	mapearDatos();
     	
    }

    /**
     * Si se pasan datos, se refrescan de BBDD para asegurarse q es lo ultimo.
     */
    private void refrescarDatos() {
    	try {            	
    		if (ubicacion != null) {
    			ubicacion = DelegateUtil.getUbicacionDelegate().obtenerUbicacion(ubicacion.getCodigo());
    		}        	
		} catch (DelegateException e) {
			ConsolaUtil.generaDelegateException(e);
		}	
    }

	/**
     * Mapea datos a los controles de la pantalla.
     */
	private void mapearDatos() {
		if (modo != TypeModoAcceso.ALTA) {
			codigo.setValue(ubicacion.getCodigoUbicacion());
			descripcion.setValue(ubicacion.getNombre());
			clase.setValue(ubicacion.getPluginAlmacenamiento());
			defecto.setChecked("S".equals(ubicacion.getDefecto()));
		}
	}
	
	 /**
     * Click boton Cancelar.
     */
    public final void onClick$btnCancelar() {
    	wUbicacion.detach();
    }
    
    /**
     * Click boton Guardar.
     */
    public final void onClick$btnGuardar() {

    	// Modo consulta: acceso no permitido
    	if (modo == TypeModoAcceso.CONSULTA) {
    		ConsolaUtil.generaOperacionNoPermitidaException();
    	}
    	
    	// Verificamos campos obligatorios    	
        final String[] nomCampos = {
        		Labels
                .getLabel("ubicacion.codigo"),
        		Labels
                .getLabel("ubicacion.clase"),
                Labels
                .getLabel("ubicacion.descripcion")};
        final String[] valCampos = {codigo.getText(), clase.getText(), descripcion.getText()};
        final boolean ok = verificarCamposObligatorios(nomCampos, valCampos);
        
        // Guardamos datos
        if (ok) {                	
        	Ubicacion ubicacionModif = null;
            if (modo == TypeModoAcceso.ALTA) {
            	ubicacionModif = new Ubicacion();            	
            } else {
            	ubicacionModif = ubicacion;            	            
            }
            
            ubicacionModif.setCodigoUbicacion(codigo.getValue());
            ubicacionModif.setNombre(descripcion.getValue());
            ubicacionModif.setPluginAlmacenamiento(clase.getValue());
            ubicacionModif.setDefecto(defecto.isChecked()?"S":"N");
                                    
            try {            	
            	// Grabamos 
            	Long id = DelegateUtil.getUbicacionDelegate().grabarUbicacion(ubicacionModif );
            	ubicacionModif.setCodigo(id);            	
            	
            	//Generamos evento            	
                Events.postEvent(new Event(ConsolaUtil.eventoModoAcesso(modo), wUbicacion.getParent(), ubicacionModif));
    		} catch (DelegateException e) {
    			ConsolaUtil.generaDelegateException(e);
    		}
    		
    		wUbicacion.detach();
        }
    	
    	
    }
    
    /**
     * Ayuda.
     * 
     */
    public final void onClick$btnAyuda() {
        GTTUtilsWeb.onClickBotonAyuda("configuracion/fichaFormateador");
    }
    

	
}