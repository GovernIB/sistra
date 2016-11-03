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
import es.caib.mobtratel.model.Cuenta;
import es.caib.mobtratel.persistence.delegate.CuentaDelegate;
import es.caib.mobtratel.persistence.delegate.DelegateException;
import es.caib.mobtratel.persistence.delegate.DelegateUtil;
import es.caib.zkutils.zk.composer.BaseComposer;

/**
 * Ventana de edicion cuenta envio.
 */
@SuppressWarnings("serial")
public class CuentaEnvioWModal extends BaseComposer {

	/** Referencia ventana. */
    private Window wCuentaEnvio;
    /** Usuario logeado.*/
	private Usuario usuarioLogado;
    /** Modo edicion. */
    private TypeModoAcceso modo;
    /** CuentaEnvio editado.*/
    private Cuenta cuenta;
    	
    /** Id. */
    private Textbox identificador;    
    /** Descripcion. */
    private Textbox nombre;
    /** Email.*/
    private Textbox email;
    /** Sms.*/
    private Textbox sms;
    /** Defecto.*/
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
     	cuenta = (Cuenta) arg.get(ConstantesWEB.PARAM_OBJETO_EDICION);     	 
    
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
    		if (cuenta != null) {
    			cuenta = DelegateUtil.getCuentaDelegate().obtenerCuenta(cuenta.getCodigo());     				
    		}        	
		} catch (DelegateException e) {
			ConsolaUtil.generaDelegateException(e);
		}	
    }

    /**
     * Mapea datos a los controles de la pantalla.
     */
	private void mapearDatos() {
		if (cuenta != null) {
			identificador.setValue(cuenta.getCodigo());
			nombre.setValue(cuenta.getNombre());	
			email.setValue(cuenta.getEmail());
			sms.setValue(cuenta.getSms());
			defecto.setChecked(cuenta.getDefecto() == 1);
		}
		
		identificador.setReadonly(modo != TypeModoAcceso.ALTA);
		
	}
	
	 /**
     * Click boton Cancelar.
     */
    public final void onClick$btnCancelar() {
    	wCuentaEnvio.detach();
    }
    
    /**
     * Click boton Guardar.
     */
    public final void onClick$btnGuardar() {
    	 try { 
    		 	CuentaDelegate delegate = DelegateUtil.getCuentaDelegate();
    		 
		    	// Modo consulta: acceso no permitido
		    	if (modo == TypeModoAcceso.CONSULTA) {
		    		ConsolaUtil.generaOperacionNoPermitidaException();
		    	}
		    	
		    	// Verificamos campos obligatorios    	
		        final String[] nomCampos = {Labels.getLabel("cuentaEnvio.identificador"), 
		        		Labels.getLabel("cuentaEnvio.nombre")};
		        final String[] valCampos = {identificador.getText(), nombre.getText()};
		        final boolean ok = verificarCamposObligatorios(nomCampos, valCampos);
		        
		        // Guardamos datos
		        if (ok) {       
		        	
		        	Cuenta cuentaUpdate = null;
		        	
		        	if (modo == TypeModoAcceso.ALTA) {		        		
		        		// Verifica si existe otro proc con ese codigo
		            	if (delegate.obtenerCuenta(identificador.getValue()) != null) {
		    				mostrarError(Labels.getLabel("error.codigoRepetido"), Labels.getLabel("mensaje.atencion"));
		    				return;
		            	}
		            	cuentaUpdate = new Cuenta();
		            	cuentaUpdate.setCodigo(identificador.getValue());
		        	} else {
		        		cuentaUpdate = cuenta;
		        	}
		        	
		        	cuentaUpdate.setNombre(nombre.getValue());
		        	cuentaUpdate.setEmail(email.getValue());
		        	cuentaUpdate.setSms(sms.getValue());
		        	cuentaUpdate.setDefecto(defecto.isChecked()?1:0);
		        	          	
		            // Grabamos
		        	delegate.grabarCuenta(cuentaUpdate);            	
		            	
		            //Generamos evento            	
		            Events.postEvent(new Event(ConsolaUtil.eventoModoAcesso(modo), wCuentaEnvio.getParent(), cuentaUpdate));
		    		
		    		
		            wCuentaEnvio.detach();
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
        GTTUtilsWeb.onClickBotonAyuda("configuracion/fichaCuentaEnvio");
    }
	
}
