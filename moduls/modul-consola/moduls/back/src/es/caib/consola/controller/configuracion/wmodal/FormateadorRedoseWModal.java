package es.caib.consola.controller.configuracion.wmodal;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import es.caib.consola.ConstantesWEB;
import es.caib.consola.model.Usuario;
import es.caib.consola.model.types.TypeModoAcceso;
import es.caib.consola.util.ConsolaUtil;
import es.caib.consola.util.GTTUtilsWeb;
import es.caib.redose.model.Formateador;
import es.caib.redose.persistence.delegate.DelegateException;
import es.caib.redose.persistence.delegate.DelegateUtil;

import es.caib.zkutils.zk.composer.BaseComposer;

/**
 * Ventana de edicion formateador Redose.
 */
@SuppressWarnings("serial")
public class FormateadorRedoseWModal extends BaseComposer {

	/** Referencia ventana. */
    private Window wFormateadorRedose;
    /** Usuario logeado.*/
	private Usuario usuarioLogado;
    /** Modo edicion. */
    private TypeModoAcceso modo;
    /** Formateador editado.*/
    private Formateador formateador;    
    /** Clase java. */
    private Textbox clase;
    /** Descripcion organo. */
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
     	formateador = (Formateador) arg.get(ConstantesWEB.PARAM_OBJETO_EDICION);   
     	
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
    		if (formateador != null) {
    			formateador = DelegateUtil.getFormateadorDelegate().obtenerFormateador(formateador.getIdentificador());
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
			descripcion.setValue(formateador.getDescripcion());
			clase.setValue(formateador.getClase());
		}
	}
	
	 /**
     * Click boton Cancelar.
     */
    public final void onClick$btnCancelar() {
    	wFormateadorRedose.detach();
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
        final String[] nomCampos = {Labels
                .getLabel("formateador.clase"),
                Labels
                .getLabel("formateador.descripcion")};
        final String[] valCampos = {clase.getText(), descripcion.getText()};
        final boolean ok = verificarCamposObligatorios(nomCampos, valCampos);
        
        // Guardamos datos
        if (ok) {                	
        	Formateador organoModif = null;
            if (modo == TypeModoAcceso.ALTA) {
            	organoModif = new Formateador();            	
            } else {
            	organoModif = formateador;            	            
            }
            
            organoModif.setDescripcion(descripcion.getValue());
            organoModif.setClase(clase.getValue());
            
                        
            try {            	
            	// Grabamos organo
            	if (modo == TypeModoAcceso.ALTA) {
	            	Long id = DelegateUtil.getFormateadorDelegate().grabarFormateadorAlta(organoModif );
	            	organoModif.setIdentificador(id);
            	} else {
            		DelegateUtil.getFormateadorDelegate().grabarFormateadorUpdate(organoModif );
            	}
            	
            	//Generamos evento            	
                Events.postEvent(new Event(ConsolaUtil.eventoModoAcesso(modo), wFormateadorRedose.getParent(), organoModif));
    		} catch (DelegateException e) {
    			ConsolaUtil.generaDelegateException(e);
    		}
    		
    		wFormateadorRedose.detach();
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