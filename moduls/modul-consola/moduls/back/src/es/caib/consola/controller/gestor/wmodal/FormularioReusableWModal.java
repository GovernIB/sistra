package es.caib.consola.controller.gestor.wmodal;

import java.util.HashMap;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import es.caib.consola.ConstantesWEB;
import es.caib.consola.model.FormularioReusable;
import es.caib.consola.model.Usuario;
import es.caib.consola.util.ConsolaUtil;
import es.caib.zkutils.zk.composer.BaseComposer;
import es.caib.consola.model.types.TypeModoAcceso;

/**
 * Ventana de formulario reusable.
 */
@SuppressWarnings("serial")
public class FormularioReusableWModal extends BaseComposer {

	/** Referencia ventana. */
    private Window wFormularioReusable;
    /** Usuario logeado.*/
	private Usuario usuarioLogado;
    /** Modo edicion. */
    private TypeModoAcceso modo;
    /** Formulario reusable.*/
    FormularioReusable formulario;
   
	/** Identificador.*/
	private Textbox identificador;
	/** Descripción.*/
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
     	modo = (TypeModoAcceso) arg.get(ConstantesWEB.PARAM_MODO_ACCESO);
     	formulario = (FormularioReusable) arg.get(ConstantesWEB.PARAMETER_FORMULARIO_REUSABLE);     	 

     	// Usuario autenticado
     	usuarioLogado = ConsolaUtil.recuperarUsuarioLogado();
        
     	// Mapear datos
     	mapearDatos();
    }
	
    // Mapea datos
	 private void mapearDatos() {
		 identificador.setValue(formulario.getIdentificador());
		 descripcion.setValue(formulario.getDescripcion());
	}

	/**
     * Click boton Cancelar.
     */
    public final void onClick$btnCancelar() {
    	wFormularioReusable.detach();
    }
    
    /**
     * Click boton Editar diseño.
     */
    public final void onClick$btnDisenyo() {
    	final Map<String, Object> map = new HashMap<String, Object>();       
        final Window ventana = (Window) creaComponente(
                "/gestor/windows/ges-disenyoFormulario-win.zul", this.self, map);
        abreVentanaModal(ventana);
    }


}
