package es.caib.consola.controller.configuracion.wmodal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import es.caib.bantel.model.GestorBandeja;
import es.caib.bantel.model.Procedimiento;
import es.caib.bantel.persistence.delegate.DelegateException;
import es.caib.bantel.persistence.delegate.DelegateUtil;
import es.caib.bantel.persistence.delegate.GestorBandejaDelegate;
import es.caib.consola.ConstantesWEB;
import es.caib.consola.model.Usuario;
import es.caib.consola.model.types.TypeModoAcceso;
import es.caib.consola.util.ConsolaUtil;
import es.caib.consola.util.GTTUtilsWeb;
import es.caib.zkutils.zk.composer.BaseComposer;

/**
 * Ventana de edicion gestor.
 */
@SuppressWarnings("serial")
public class GestorBandejaWModal extends BaseComposer {

	/** Referencia ventana. */
    private Window wGestorBandeja;
    /** Usuario logeado.*/
	private Usuario usuarioLogado;
    /** Modo edicion. */
    private TypeModoAcceso modo;
    /** GestorBandeja editado.*/
    private GestorBandeja gestorBandeja;
    	
    /** Id: textbox. */
    private Textbox identificador;    
    /** Email. */
    private Textbox email;
    
   /** Lista procedimientos.*/
    private Listbox listboxProcedimientos;
    /** List procedimientos. */
    private List procedimientosList;
    
    /** Check aviso entradas. */
    private Checkbox avisoEntradas;
    /** Check aviso notificaciones. */
    private Checkbox avisoNotificaciones;
    /** Check aviso monitorizacion. */
    private Checkbox avisoMonitorizacion;
    
    
    /** Check permitir cambio estado. */
    private Checkbox permitirCambioEstado;
    /** Check permitir cambio masivo. */
    private Checkbox permitirCambioEstadoMasivo;
    /** Check permitir gestion expedientes. */
    private Checkbox permitirGestionExpedientes;
    
    
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
     	gestorBandeja = (GestorBandeja) arg.get(ConstantesWEB.PARAM_OBJETO_EDICION);     	 

     	// Usuario autenticado
     	usuarioLogado = ConsolaUtil.recuperarUsuarioLogado();
        
     	// Refresca datos
     	refrescarDatos();
     	
     	// Lista procedimientos
     	listboxProcedimientos.setItemRenderer(new ListitemRenderer() {
            public void render(final Listitem item, final Object data, final int index) {
                final Procedimiento reg = (Procedimiento) data;
                item.setValue(reg);
                item.setLabel(reg.getIdentificador());
                item.setAttribute("data", data);                
            }
        });
     	
     	// Mapea datos a los controles de la pantalla
     	mapearDatos();
     	
    }
    
    /**
     * Si se pasan datos, se refrescan de BBDD para asegurarse q es lo ultimo.
     */
    private void refrescarDatos() {
    	try {            	
    		if (gestorBandeja != null) {
    			gestorBandeja = DelegateUtil.getGestorBandejaDelegate().obtenerGestorBandeja(gestorBandeja.getSeyconID());
    		}        	
		} catch (DelegateException e) {
			ConsolaUtil.generaDelegateException(e);
		}	
    }

    /**
     * Mapea datos a los controles de la pantalla.
     */
	private void mapearDatos() {
		
		procedimientosList = new ArrayList();
		
		if (gestorBandeja != null) {
			identificador.setValue(gestorBandeja.getSeyconID());
			email.setValue(gestorBandeja.getEmail());			
			procedimientosList = ConsolaUtil.setToList(gestorBandeja.getProcedimientosGestionados()); 			
			
			avisoEntradas.setChecked("S".equals(gestorBandeja.getAvisarEntradas()));
			avisoNotificaciones.setChecked("S".equals(gestorBandeja.getAvisarNotificaciones()));
			avisoMonitorizacion.setChecked("S".equals(gestorBandeja.getAvisarMonitorizacion()));
			
			permitirCambioEstado.setChecked(gestorBandeja.getPermitirCambioEstado() == 'S');
			permitirCambioEstadoMasivo.setChecked(gestorBandeja.getPermitirCambioEstadoMasivo() == 'S');
			permitirGestionExpedientes.setChecked(gestorBandeja.getPermitirGestionExpedientes() == 'S');
		}		
		
		listboxProcedimientos.setModel(new BindingListModelList(procedimientosList, true));
		
		if (modo != TypeModoAcceso.ALTA) {
			identificador.setReadonly(true);
		}
	}
	
	 /**
     * Click boton Cancelar.
     */
    public final void onClick$btnCancelar() {
    	wGestorBandeja.detach();
    }

	
    /**
     * Click boton añadir proc.
     */
    public final void onClick$btnAddProc() {
    	ConsolaUtil.abrirSelectorProcedimiento(this);
    }
    
    /**
     * Click boton añadir proc.
     */
    public final void onClick$btnDelProc() {
    	final Object valueSelected = obtenerItemSeleccionado(listboxProcedimientos);
        if (valueSelected != null) {
        	procedimientosList.remove(valueSelected);
        	listboxProcedimientos.setModel(new BindingListModelList(procedimientosList, true));
        }
    }

	@Override
	protected void onSeleccionSelector(String tipoSelector,
			Object valorSeleccionado) {
		if (ConstantesWEB.SELECTOR_PROCEDIMIENTO.equals(tipoSelector)) {
			Procedimiento o = (Procedimiento) valorSeleccionado;
			if (buscarProcedimiento(procedimientosList, o.getIdentificador()) == null) {
				procedimientosList.add(o);			
				listboxProcedimientos.setModel(new BindingListModelList(procedimientosList, true));
			}
		}
	}
	
	/**
	 * Busca en lista.
	 * @param lista Lista
	 * @return valor o null
	 */
	private Procedimiento buscarProcedimiento(List lista, String id) {
		boolean existe = false;
		Procedimiento gl = null;
		for (Iterator it = lista.iterator(); it.hasNext();){
			gl = (Procedimiento) it.next();
			if (gl.getIdentificador().equals(id)) {
				existe = true;
				break;
			}
		}
		if (!existe) {
			 gl = null;
		} 
		return gl;		
	}
	
	 /**
     * Click boton Guardar.
     */
    public final void onClick$btnGuardar() {

    	try {  
    		
	    	GestorBandejaDelegate gestorBandejaDelegate = DelegateUtil.getGestorBandejaDelegate();
	    	
	    	// Modo consulta: acceso no permitido
	    	if (modo == TypeModoAcceso.CONSULTA) {
	    		ConsolaUtil.generaOperacionNoPermitidaException();
	    	}
	    	
	    	// Verificamos campos obligatorios    	
	        final String[] nomCampos = {Labels
	                .getLabel("gestorBandeja.identificador"), Labels
	                .getLabel("gestorBandeja.email")};
	        final String[] valCampos = {identificador.getText(), email.getText()};
	        final boolean ok = verificarCamposObligatorios(nomCampos, valCampos);
	        
	        // Guardamos datos
	        if (ok) {                	
	        	
	        	GestorBandeja gestorUpdate = null;
	            if (modo == TypeModoAcceso.ALTA) {
	            	
	            	// Verifica si existe otro con ese codigo	            	
	    			if (gestorBandejaDelegate.obtenerGestorBandeja(identificador.getText()) != null) {
	    				mostrarError(Labels.getLabel("error.codigoRepetido"), Labels.getLabel("mensaje.atencion"));
	    				return;
	            	}
	            	
	            	gestorUpdate = new GestorBandeja();
	            	gestorUpdate.setSeyconID(identificador.getValue());
	            } else {            	
	            	gestorUpdate = this.gestorBandeja;            	            
	            }
	            
	            
	            gestorUpdate.setEmail(email.getValue());
	            
	            gestorUpdate.setAvisarEntradas(avisoEntradas.isChecked()?"S":"N");
	            gestorUpdate.setAvisarNotificaciones(avisoNotificaciones.isChecked()?"S":"N");
	            gestorUpdate.setAvisarMonitorizacion(avisoMonitorizacion.isChecked()?"S":"N");
	            
	            gestorUpdate.setPermitirCambioEstado(permitirCambioEstado.isChecked()?'S':'N');
	            gestorUpdate.setPermitirCambioEstadoMasivo(permitirCambioEstadoMasivo.isChecked()?'S':'N');
	            gestorUpdate.setPermitirGestionExpedientes(permitirGestionExpedientes.isChecked()?'S':'N');
	          
	            String[] identificadoresProc = new String[procedimientosList.size()];
	            for (int i = 0; i < procedimientosList.size(); i++) {
	            	identificadoresProc[i] =  ((Procedimiento) procedimientosList.get(i)).getIdentificador();
	            }
	                                	
	            // Grabamos
	            gestorBandejaDelegate.grabarGestorBandeja(gestorUpdate, identificadoresProc);
	            	
	            //Generamos evento            	
	            Events.postEvent(new Event(ConsolaUtil.eventoModoAcesso(modo), wGestorBandeja.getParent(), gestorUpdate));
	    		
	            wGestorBandeja.detach();
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
        GTTUtilsWeb.onClickBotonAyuda("configuracion/fichaGestorBandeja");
    }
    
}

