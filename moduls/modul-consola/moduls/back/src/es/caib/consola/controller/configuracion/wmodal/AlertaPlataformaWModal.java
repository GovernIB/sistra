package es.caib.consola.controller.configuracion.wmodal;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import es.caib.consola.ConstantesWEB;
import es.caib.consola.model.AlertaPlataforma;
import es.caib.consola.model.Usuario;
import es.caib.consola.util.ConsolaUtil;
import es.caib.zkutils.zk.composer.BaseComposer;

/**
 * Ventana de edicion alerta plataforma.
 */
@SuppressWarnings("serial")
public class AlertaPlataformaWModal extends BaseComposer {

	/** Referencia ventana. */
    private Window wAlertaPlataforma;
    /** Usuario logeado.*/
	private Usuario usuarioLogado;
    /** Modo edicion. */
    private String modo;
    /** Alerta editado.*/
    private AlertaPlataforma alertaPlataforma;
    /** Titulo alerta.*/
    private Textbox descripcion;
    
    
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
     	alertaPlataforma = (AlertaPlataforma) arg.get(ConstantesWEB.ERROR_PLATAFORMA);     	 

     	// Usuario autenticado
     	usuarioLogado = ConsolaUtil.recuperarUsuarioLogado();
        
     	// Mapea datos a los controles de la pantalla
     	mapearDatos();
     	
    }

    /**
     * Mapea datos a los controles de la pantalla.
     */
	private void mapearDatos() {
		descripcion.setValue(alertaPlataforma.getDescripcion());	
		
	
	}
	
	 /**
     * Click boton Cancelar.
     */
    public final void onClick$btnCancelar() {
    	wAlertaPlataforma.detach();
    }

	
}
