package es.caib.consola.controller.gestor.wmodal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import es.caib.bantel.model.CampoFuenteDatos;
import es.caib.bantel.model.FuenteDatos;
import es.caib.bantel.model.Procedimiento;
import es.caib.bantel.persistence.delegate.DelegateException;
import es.caib.bantel.persistence.delegate.DelegateUtil;
import es.caib.bantel.persistence.delegate.FuenteDatosDelegate;
import es.caib.consola.ConstantesWEB;
import es.caib.consola.controller.gestor.wmodal.renderer.CampoFuenteDatosListitemRenderer;
import es.caib.consola.model.Usuario;
import es.caib.consola.model.types.TypeModoAcceso;
import es.caib.consola.util.ConsolaUtil;
import es.caib.consola.util.GTTUtilsWeb;
import es.caib.util.StringUtil;
import es.caib.zkutils.ConstantesZK;
import es.caib.zkutils.zk.composer.BaseComposer;

/**
 * Ventana de edicion procesamiento procedimiento.
 */
@SuppressWarnings("serial")
public class FuenteDatosWModal extends BaseComposer {

	/** Referencia ventana. */
    private Window wFuenteDatos;
    /** Usuario logeado.*/
	private Usuario usuarioLogado;
    /** Modo edicion. */
    private TypeModoAcceso modo;
    
    /** Fuente datos editado.*/
    private FuenteDatos fuenteDatos;
    /** Procedimiento de la fuente de datos. */
    private Procedimiento procedimiento;
           	
    /** Id dominio: textbox. */
    private Textbox codigo;    
    /** Descripcion dominio. */
    private Textbox descripcion;        
            
    /** Row campos. */
    private Row rowCampos;
    /** Listbox fuentes datos. */
    private Listbox listboxCamposFuenteDatos;
    /** Lista fuentes datos. */
    private List<CampoFuenteDatos> camposFuentesDatosList = new ArrayList<CampoFuenteDatos>();
    
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
     	fuenteDatos = (FuenteDatos) arg.get(ConstantesWEB.PARAM_OBJETO_EDICION);    
     	procedimiento = (Procedimiento) arg.get(ConstantesWEB.PARAM_OBJETO_PADRE);     
     	
     	// Usuario autenticado
     	usuarioLogado = ConsolaUtil.recuperarUsuarioLogado();
     	
     	// Eventos
     	manejadoresEventos();
     	
     	// Refresca datos
     	refrescarDatos();
        
     	// Mapea datos a los controles de la pantalla
     	mapearDatos();
     	
    }
    
    /**
     * Eventos.
     */
    private void manejadoresEventos() {
	   	 // - evento refresco datos tras modificacion
	   	wFuenteDatos.addEventListener(
	   			ConstantesZK.EVENTO_POST_MODIFICACION, new EventListener() {
						public void onEvent(final Event pEvent) {
							if (pEvent.getData() instanceof CampoFuenteDatos) {
								postModificacionCampoFuenteDatos((CampoFuenteDatos) pEvent.getData());
							} 						
	                   }					
	               });
	    // - evento refresco datos tras alta
	   	wFuenteDatos.addEventListener(ConstantesZK.EVENTO_POST_ALTA,
	               new EventListener() {
	                   public void onEvent(final Event pEvent) {
	                   	if (pEvent.getData() instanceof CampoFuenteDatos) {
								postAltaCampoFuenteDatos( (CampoFuenteDatos) pEvent.getData());
							} 
	                   }
	               });
		
	}

    @Override
    protected void postConfirmarBorrado(final Object obj) {    	
    	if (obj instanceof CampoFuenteDatos) {
    		postConfirmarBorradoCampoFuenteDatos(obj);
    	}     	
    }    

	/**
     * Si se pasan datos, se refrescan de BBDD para asegurarse q es lo ultimo.
     */
    private void refrescarDatos() {
    	try {            	
    		if (fuenteDatos != null) {
    			fuenteDatos = DelegateUtil.getFuenteDatosDelegate().obtenerFuenteDatos(fuenteDatos.getIdentificador());
    		}
		} catch (DelegateException e) {
			ConsolaUtil.generaDelegateException(e);
		}	
    }
    
    /**
     * Mapea datos a los controles de la pantalla.
     */
	private void mapearDatos() {		
		camposFuentesDatosList = new ArrayList<CampoFuenteDatos>();		
		if (fuenteDatos != null) {		
			codigo.setValue(fuenteDatos.getIdentificador());
			descripcion.setValue(fuenteDatos.getDescripcion());		
			camposFuentesDatosList = ConsolaUtil.setToList(fuenteDatos.getCampos());					
		}
		
		listboxCamposFuenteDatos.setItemRenderer(new CampoFuenteDatosListitemRenderer());
		listboxCamposFuenteDatos.setModel(new BindingListModelList(camposFuentesDatosList, true));
		
		
		if (modo == TypeModoAcceso.ALTA) {
			rowCampos.setVisible(false);
		}
	}
	
	 /**
     * Click boton Cancelar.
     */
    public final void onClick$btnCancelar() {
    	wFuenteDatos.detach();
    } 
    
    /**
     * Doble click elemento lista campos.
     */
    public final void onDoubleClick$itemCampoFuenteDatos() {
        onClick$btnEditCampo();     
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
                .getLabel("fuenteDatos.id"),
        		Labels
                .getLabel("fuenteDatos.descripcion")};
        final String[] valCampos = {codigo.getText(), descripcion.getText()};
        boolean ok = verificarCamposObligatorios(nomCampos, valCampos);
        
        String codigoText = codigo.getText().toUpperCase();
        codigo.setText(codigoText);
		if (!StringUtil.validarFormatoIdentificador(codigoText) ){
        	ok = false;
        	 mostrarError(Labels.getLabel("campoFuenteDatos.id") + ": " + Labels.getLabel("error.formatoIdentificadorNoValido"),
        			 Labels.getLabel("mensaje.atencion"));       	
		}
        
        // Guardamos datos
        if (ok) {                	        	                                      
            try {            	
            	// Grabamos 
            	FuenteDatosDelegate fuenteDatosDelegate = DelegateUtil.getFuenteDatosDelegate();
            	
            	if (modo == TypeModoAcceso.ALTA) {
    				fuenteDatosDelegate.altaFuenteDatos(codigoText, procedimiento.getIdentificador(), descripcion.getText());    				
            	} else {            		
                	fuenteDatosDelegate.modificarFuenteDatos(fuenteDatos.getIdentificador(), codigoText, procedimiento.getIdentificador(), descripcion.getText());                	
            	}
            	
            	//Generamos evento
            	FuenteDatos fuenteDatosModif = fuenteDatosDelegate.obtenerFuenteDatos(codigoText);
                Events.postEvent(new Event(ConsolaUtil.eventoModoAcesso(modo), wFuenteDatos.getParent(), fuenteDatosModif));
    		} catch (DelegateException e) {
    			ConsolaUtil.generaDelegateException(e);
    		}
    		
    		wFuenteDatos.detach();
        }
    }            

    /**
     * Click boton editar.
     */
    public final void onClick$btnEditCampo() {
    	final Object valueSelected = obtenerItemSeleccionado(listboxCamposFuenteDatos);
        if (valueSelected != null) {
        	CampoFuenteDatos data = (CampoFuenteDatos) ((Listitem) listboxCamposFuenteDatos.getSelectedItemApi()
                    .clone()).getValue();
            final Map<String, Object> map = new HashMap<String, Object>();
            map.put(ConstantesWEB.PARAM_MODO_ACCESO, TypeModoAcceso.EDICION);            
			map.put(ConstantesWEB.PARAM_OBJETO_EDICION, data);  
			map.put(ConstantesWEB.PARAM_OBJETO_PADRE, fuenteDatos);
            final Window ventana = (Window) creaComponente(
                    "/gestor/windows/ges-campoFuenteDatos-win.zul", this.self, map);
            abreVentanaModal(ventana);
        } 
    }
    
    /**
     * Click boton nuevo.
     */
    public final void onClick$btnAddCampo() {
    	final Map<String, Object> map = new HashMap<String, Object>();   
    	map.put(ConstantesWEB.PARAM_MODO_ACCESO, TypeModoAcceso.ALTA);
    	map.put(ConstantesWEB.PARAM_OBJETO_PADRE, fuenteDatos);
        final Window ventana = (Window) creaComponente(
                  "/gestor/windows/ges-campoFuenteDatos-win.zul", this.self, map);
        abreVentanaModal(ventana);       
    }
    
    /**
     * Eliminar.
     */
    public void onClick$btnDelCampo() {
        final Object valueSelected = obtenerItemSeleccionado(listboxCamposFuenteDatos);
        if (valueSelected != null) {
            confirmarBorrado(valueSelected);
        }
    }
    
    
    /**
     * Nuevo.
     */
    public void onClick$PopupNuevoCampo() {
    	onClick$btnAddCampo();
    }
    
    /**
     * Editar.
     */
    public void onClick$PopupEditaCampo() {
    	onClick$btnEditCampo();
    }
    
    /**
     * Eliminar.
     */
    public void onClick$PopupQuitaCampo() {
    	onClick$btnDelCampo();
    }
    
    /**
     * Evento post modificacion.
     * @param data Cuenta
     */
    private void postModificacionCampoFuenteDatos(CampoFuenteDatos os) {		
		// Actualiza datos modelo	
    	int index = 0;
		for (final CampoFuenteDatos o : this.camposFuentesDatosList) {
		    if (o.getCodigo().equals(os.getCodigo())) {
		    	camposFuentesDatosList.set(index, os);
		        break;
		    }
		    index++;
		}
		listboxCamposFuenteDatos.setModel(new BindingListModelList(camposFuentesDatosList, true));
		
	}
    
    /**
     * Evento post alta.
     * @param data Cuenta
     */
    private void postAltaCampoFuenteDatos(
    		CampoFuenteDatos data) {		
        // Refrescamos datos
        refrescarListaCampoFuenteDatos();       
	}
    
    /** Refresca lista. */
	private void refrescarListaCampoFuenteDatos() {
		try {
			FuenteDatos fd = DelegateUtil.getFuenteDatosDelegate().obtenerFuenteDatos(fuenteDatos.getIdentificador());
			camposFuentesDatosList = ConsolaUtil.setToList(fd.getCampos());
			listboxCamposFuenteDatos.selectItemApi(null);
			listboxCamposFuenteDatos.setModel(new BindingListModelList(camposFuentesDatosList, true));			
		} catch (DelegateException e) {					
			ConsolaUtil.generaDelegateException(e);
		}
	}

    /**
     * Post confirmar borrado.
     * @param obj
     */
	private void postConfirmarBorradoCampoFuenteDatos(Object obj) {       
		try {
			
            final CampoFuenteDatos os = (CampoFuenteDatos) obj;                       
            
            // Borramos
            DelegateUtil.getFuenteDatosDelegate().borrarCampoFuenteDatos(os.getFuenteDatos().getIdentificador(), os.getIdentificador());
    		                     
            // Actualizamos modelo
            for (final CampoFuenteDatos o : camposFuentesDatosList) {
                if (o.getCodigo().equals(os.getCodigo())) {
                	camposFuentesDatosList.remove(o);
                    break;
                }
            }
            listboxCamposFuenteDatos.setModel(new BindingListModelList(camposFuentesDatosList, true));
		} catch (DelegateException e) {
			ConsolaUtil.generaDelegateException(e);
		}   
    }        	
	
	 /**
     * Ayuda.
     * 
     */
    public final void onClick$btnAyuda() {
        GTTUtilsWeb.onClickBotonAyuda("configuracion/fichaFuenteDatos");
    }
   
}
