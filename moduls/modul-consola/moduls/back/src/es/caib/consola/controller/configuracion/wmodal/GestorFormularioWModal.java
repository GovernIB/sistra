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
import es.caib.sistra.model.GestorFormulario;
import es.caib.sistra.persistence.delegate.DelegateException;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.sistra.persistence.delegate.GestorFormularioDelegate;
import es.caib.zkutils.zk.composer.BaseComposer;

/**
 * Ventana de edicion gestor formulario.
 */
@SuppressWarnings("serial")
public class GestorFormularioWModal extends BaseComposer {

	/** Referencia ventana. */
    private Window wGestorFormulario;
    /** Usuario logeado.*/
	private Usuario usuarioLogado;
    /** Modo edicion. */
    private TypeModoAcceso modo;
    /** Procedimiento editado.*/
    private GestorFormulario gestorFormulario;
    	
    /** Id dominio: textbox. */
    private Textbox identificador;    
    /** Descripcion dominio. */
    private Textbox descripcion;
    /** URL gestor */
    private Textbox urlGestor;
    /** URL tramitacion */
    private Textbox urlTramitacion;
    /** URL redireccion */
    private Textbox urlRedireccion;
    
    
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
     	gestorFormulario = (GestorFormulario) arg.get(ConstantesWEB.PARAM_OBJETO_EDICION);     	 

     	 // Refresca datos
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
    		if (gestorFormulario != null) {
    			gestorFormulario = DelegateUtil.getGestorFormularioDelegate().obtener(gestorFormulario.getIdentificador());     				
    		}        	
		} catch (DelegateException e) {
			ConsolaUtil.generaDelegateException(e);
		}	
    }
    
    /**
     * Mapea datos a los controles de la pantalla.
     */
	private void mapearDatos() {
		if (gestorFormulario != null) {
			identificador.setValue(gestorFormulario.getIdentificador());
			descripcion.setValue(gestorFormulario.getDescripcion());	
			urlGestor.setValue(gestorFormulario.getUrlGestor());
			urlTramitacion.setValue(gestorFormulario.getUrlTramitacionFormulario());
			urlRedireccion.setValue(gestorFormulario.getUrlRedireccionFormulario());
		}
		
		identificador.setReadonly(modo != TypeModoAcceso.ALTA);
	
	}
	
	 /**
     * Click boton Cancelar.
     */
    public final void onClick$btnCancelar() {
    	wGestorFormulario.detach();
    }

    /**
     * Click boton Guardar.
     */
    public final void onClick$btnGuardar() {
    	 try { 
    		 	GestorFormularioDelegate delegate = DelegateUtil.getGestorFormularioDelegate();
    		 
		    	// Modo consulta: acceso no permitido
		    	if (modo == TypeModoAcceso.CONSULTA) {
		    		ConsolaUtil.generaOperacionNoPermitidaException();
		    	}
		    	
		    	// Verificamos campos obligatorios    	
		        final String[] nomCampos = {Labels.getLabel("gestorFormulario.identificador"), 
		        		Labels.getLabel("gestorFormulario.descripcion"),
		        		Labels.getLabel("gestorFormulario.urlGestor"),
		        		Labels.getLabel("gestorFormulario.urlTramitacion"),
		        		Labels.getLabel("gestorFormulario.urlRedireccion")};
		        final String[] valCampos = {identificador.getText(), descripcion.getText(), urlGestor.getText(), 
		        		urlTramitacion.getText(), urlRedireccion.getText()};
		        final boolean ok = verificarCamposObligatorios(nomCampos, valCampos);
		        
		        // Guardamos datos
		        if (ok) {       
		        	
		        	GestorFormulario gestorUpdate = null;
		        	
		        	if (modo == TypeModoAcceso.ALTA) {		        		
		        		// Verifica si existe otro proc con ese codigo
		            	if (delegate.obtener(identificador.getValue()) != null) {
		    				mostrarError(Labels.getLabel("error.codigoRepetido"), Labels.getLabel("mensaje.atencion"));
		    				return;
		            	}
		            	gestorUpdate = new GestorFormulario();
		            	gestorUpdate.setIdentificador(identificador.getValue());
		        	} else {
		        		gestorUpdate = gestorFormulario;
		        	}
		        	
		        	gestorUpdate.setDescripcion(descripcion.getValue());
		        	gestorUpdate.setUrlGestor(urlGestor.getValue());
		        	gestorUpdate.setUrlTramitacionFormulario(urlTramitacion.getValue());
		        	gestorUpdate.setUrlRedireccionFormulario(urlRedireccion.getValue());		        	
		        	          	
		            // Grabamos
		        	if (modo == TypeModoAcceso.ALTA) {
		        		delegate.grabarFormularioAlta(gestorUpdate);            	
		        	} else {
		        		delegate.grabarFormularioUpdate(gestorUpdate);
		        	}
		        	
		            //Generamos evento            	
		            Events.postEvent(new Event(ConsolaUtil.eventoModoAcesso(modo), wGestorFormulario.getParent(), gestorUpdate));
		    		
		    		
		            wGestorFormulario.detach();
		        }
    	
    	 } catch (DelegateException e) {
 			ConsolaUtil.generaDelegateException(e);
 		}
    }    
    
    /**
     * Ayuda.
     * 
     */
    public final void onClick$btnAyuda() {
        GTTUtilsWeb.onClickBotonAyuda("configuracion/fichaGestorFormulario");
    }
}
