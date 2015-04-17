package es.caib.consola.controller.gestor.wmodal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import es.caib.consola.ConstantesWEB;
import es.caib.consola.model.Usuario;
import es.caib.consola.model.types.TypeModoAcceso;
import es.caib.consola.util.ConsolaUtil;
import es.caib.consola.util.GTTUtilsWeb;
import es.caib.redose.model.Modelo;
import es.caib.redose.model.Version;
import es.caib.redose.persistence.delegate.DelegateException;
import es.caib.redose.persistence.delegate.DelegateUtil;
import es.caib.sistra.model.OrganoResponsable;
import es.caib.util.StringUtil;
import es.caib.zkutils.ConstantesZK;
import es.caib.zkutils.zk.composer.BaseComposer;

/**
 * Ventana de edicion modelo documento.
 */
@SuppressWarnings("serial")
public class ModeloDocumentoWModal extends BaseComposer {

	/** Referencia ventana. */
    private Window wModeloDocumento;
    /** Usuario logeado.*/
	private Usuario usuarioLogado;
    /** Modo edicion. */
    private TypeModoAcceso modo;
    /** Modelo documento editado.*/
    private Modelo modeloDocumento;
    /** Organo responsable. */
    private OrganoResponsable organo;
    	
    /** Id. */
    private Textbox codigo;
    /** Nombre. */
    private Textbox nombre;
    /** Descripcion. */
    private Textbox descripcion;
    /** Estructurado. */
    private Checkbox estructurado;
    /** Custodiar. */
    private Checkbox custodiar;
    
    /** Row versiones.*/
    private Row versionesRow;
    /** Atributo list versiones. */
    private Listbox listVersiones;
    /** Atributo lista versiones. */
    private List<Version> versiones = new ArrayList<Version>();
    
    
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
     	modeloDocumento = (Modelo) arg.get(ConstantesWEB.PARAM_OBJETO_EDICION);
     	organo = (OrganoResponsable)  arg.get(ConstantesWEB.PARAM_OBJETO_PADRE);

     	// Usuario autenticado
     	usuarioLogado = ConsolaUtil.recuperarUsuarioLogado();
     	
     	// Item renderer lista versiones
     	listVersiones.setItemRenderer(new ListitemRenderer() {
            public void render(final Listitem item, final Object data) {
                final Version reg = (Version) data;
                item.setValue(reg);
                item.setLabel(reg.getVersion() + (StringUtils.isNotBlank(reg.getDescripcion()) ? "-" + reg.getDescripcion() : ""));                               
            }
        });
     	
     	//Refrescamos valores
     	refrescarDatos();
        
     	// Mapea datos a los controles de la pantalla
     	mapearDatos();
     	
     	// Eventos
        manejadoresEventos();
     	
    }
    
    /**
     * Eventos.
     */
    private void manejadoresEventos() {
    	 // - evento refresco datos tras modificacion
    	wModeloDocumento.addEventListener(
    			ConstantesZK.EVENTO_POST_MODIFICACION, new EventListener() {
					public void onEvent(final Event pEvent) {
						if (pEvent.getData() instanceof Version) {
							postModificacionVersion( (Version) pEvent.getData());
						} 						
                    }
                });
        // - evento refresco datos tras alta
    	wModeloDocumento.addEventListener(ConstantesZK.EVENTO_POST_ALTA,
                new EventListener() {
                    public void onEvent(final Event pEvent) {                               	
                    	if (pEvent.getData() instanceof Version) {
							postAltaVersion( (Version) pEvent.getData());
						}                     	
                    }
                });
		
	}
    
    @Override
    protected void postConfirmarBorrado(final Object obj) {    	    	
    	if (obj instanceof Version) {
    		postConfirmarBorradoVersion(obj);
    	}
    }   
    
     /**
     * Si se pasan datos, se refrescan de BBDD para asegurarse q es lo ultimo.
     */
    private void refrescarDatos() {
    	try {            	
    		if (modeloDocumento != null) {
    			modeloDocumento = DelegateUtil.getModeloDelegate().obtenerModelo(modeloDocumento.getCodigo());
    			refrescarListaVersiones();
    		}        	
		} catch (DelegateException e) {
			ConsolaUtil.generaDelegateException(e);
		}	
    }

    /** Refresca lista versiones. */
	private void refrescarListaVersiones() {
		try {
			if (modeloDocumento != null) {
				versiones = ConsolaUtil.setToList(es.caib.redose.persistence.delegate.DelegateUtil.getVersionDelegate().listarVersionesModelo(modeloDocumento.getCodigo()));
				listVersiones.selectItemApi(null);
				listVersiones.setModel(new BindingListModelList(versiones, true));			
			}
		} catch (es.caib.redose.persistence.delegate.DelegateException e) {					
			ConsolaUtil.generaDelegateException(e);
		}
	}
    

    /**
     * Mapea datos a los controles de la pantalla.
     */
	private void mapearDatos() {
		if (modeloDocumento != null) {
			codigo.setValue(modeloDocumento.getModelo());
			nombre.setValue(modeloDocumento.getNombre());
			descripcion.setValue(modeloDocumento.getDescripcion());
			estructurado.setChecked(modeloDocumento.getEstructurado() == 'S');
			custodiar.setChecked(modeloDocumento.getCustodiar() == 'S');
		}		
		codigo.setReadonly(modo != TypeModoAcceso.ALTA);    
		versionesRow.setVisible(modo != TypeModoAcceso.ALTA);		
	}

	
	 /**
     * Click boton Cancelar.
     */
    public final void onClick$btnCancelar() {
    	wModeloDocumento.detach();
    }
    
    /**
     * Click boton Guardar.
     */
    public final void onClick$btnGuardar() {
    	Modelo modeloModif = null;	   	
    	try {
	    	// Modo consulta: acceso no permitido
	    	if (modo == TypeModoAcceso.CONSULTA) {
	    		ConsolaUtil.generaOperacionNoPermitidaException();
	    	}else if (modo == TypeModoAcceso.ALTA) {
	        	modeloModif = new Modelo();  	        	
	        } else {
	        	modeloModif = modeloDocumento;            	            
	        }
	    	    	
	    	// Verificamos campos obligatorios y establecemos valores
	    	// - Codigo / desc / cacheable
	        String[] nomCampos = {Labels.getLabel("modeloDocumento.id"), Labels.getLabel("modeloDocumento.nombre")};
	        String[] valCampos = {codigo.getText(),nombre.getText()};
	        boolean ok = verificarCamposObligatorios(nomCampos, valCampos);
	       
	        // Verifica si id tiene formato correcto
	        codigo.setText(codigo.getText().toUpperCase());
	    	if (ok && !StringUtil.validarFormatoIdentificador(codigo.getText()) ){
	    		ok = false;
	        	mostrarError(Labels.getLabel("modeloDocumento.id") + ": " + Labels.getLabel("error.formatoIdentificadorNoValido"),
	        			 Labels.getLabel("mensaje.atencion"));       	
			}
	    	
	    	// Verifica si ya existe modelo con ese id
	    	if (ok) {
	    		Modelo m = DelegateUtil.getModeloDelegate().obtenerModelo(codigo.getText());
	    		if (m != null && !m.getCodigo().equals(modeloModif.getCodigo())) {
	    			ok = false;
		        	mostrarError(Labels.getLabel("modeloDocumento.id") + ": " + Labels.getLabel("error.codigoRepetido"),
		        			 Labels.getLabel("mensaje.atencion"));    
	    		}
	    	}
	    	
	    	// Establecemos datos
	    	if (ok) {
	    		modeloModif.setModelo(codigo.getText());
	    		modeloModif.setNombre(nombre.getText());
	    		modeloModif.setDescripcion(descripcion.getText());
	    		modeloModif.setCustodiar(custodiar.isChecked()?'S':'N');
	    		modeloModif.setEstructurado(estructurado.isChecked()?'S':'N');
	    	}
	    	
	        // Guardamos datos
	        if (ok) {                	        	                                       	                        	
	           	// Grabamos organo
	        	Long id = DelegateUtil.getModeloDelegate().grabarModelo(modeloModif);
	        	modeloModif.setCodigo(id);
	        	
	        	//Generamos evento            	
	            Events.postEvent(new Event(ConsolaUtil.eventoModoAcesso(modo), wModeloDocumento.getParent(), modeloModif));    	
	            
	            // Si es alta, pasamos a modo modificacion para poder dar de alta versiones
	            if (modo == TypeModoAcceso.ALTA) {
	            	modo = TypeModoAcceso.EDICION;
	            	modeloDocumento = modeloModif;
	            	codigo.setReadonly(true);    
	        		versionesRow.setVisible(true);	     
	        		
	        		mostrarInfo(Labels.getLabel("modeloDocumento.altaOk"), Labels.getLabel("mensaje.atencion"));
	        		
	            } else {
	            	wModeloDocumento.detach();
	            }
	        }
    	
        } catch (Exception e) {
			ConsolaUtil.generaDelegateException(e);
		}
    }     
    
    /**
     * Click boton editar.
     */
    public final void onClick$btnEditaVersion() {
    	final Object valueSelected = obtenerItemSeleccionado(listVersiones);
        if (valueSelected != null) {
        	Version data = (Version) ((Listitem) listVersiones.getSelectedItemApi()
                    .clone()).getValue();
            final Map<String, Object> map = new HashMap<String, Object>();
            map.put(ConstantesWEB.PARAM_MODO_ACCESO, TypeModoAcceso.EDICION);            
			map.put(ConstantesWEB.PARAM_OBJETO_EDICION, data);            
			map.put(ConstantesWEB.PARAM_OBJETO_PADRE, modeloDocumento);
            final Window ventana = (Window) creaComponente(
                    "/gestor/windows/ges-versionModelo-win.zul", this.self, map);
            abreVentanaModal(ventana);
        } 
    }
    
    /**
     * Click boton nuevo.
     */
    public final void onClick$btnNuevaVersion() {
    	final Map<String, Object> map = new HashMap<String, Object>();   
    	map.put(ConstantesWEB.PARAM_MODO_ACCESO, TypeModoAcceso.ALTA);   
    	map.put(ConstantesWEB.PARAM_OBJETO_PADRE, modeloDocumento);
        final Window ventana = (Window) creaComponente(
                  "/gestor/windows/ges-versionModelo-win.zul", this.self, map);
        abreVentanaModal(ventana);       
    }
    
    /**
     * Eliminar.
     */
    public void onClick$btnBorraVersion() {
        final Object valueSelected = obtenerItemSeleccionado(listVersiones);
        if (valueSelected != null) {
            confirmarBorrado(valueSelected);
        }
    }
    
    
    /**
     * Nuevo.
     */
    public void onClick$PopupNuevaVersion() {
    	onClick$btnNuevaVersion();
    }
    
    /**
     * Editar.
     */
    public void onClick$PopupEditaVersion() {
    	onClick$btnEditaVersion();
    }
    
    /**
     * Eliminar.
     */
    public void onClick$PopupBorraVersion() {
    	onClick$btnBorraVersion();
    }
    
    
    /**
     * Evento post modificacion.
     * @param data Cuenta
     */
    private void postModificacionVersion(Version os) {		
		// Actualiza datos modelo
    	int index = 0;
		for (final Version o : this.versiones) {
		    if (o.getCodigo().equals(os.getCodigo())) {
		    	versiones.set(index, os);
		        break;
		    }
		    index++;
		}
		listVersiones.setModel(new BindingListModelList(versiones, true));
		
	}
    
    /**
     * Evento post alta.
     * @param data Cuenta
     */
    private void postAltaVersion(Version data) {		
        refrescarListaVersiones();       
	}
    

    /**
     * Post confirmar borrado.
     * @param obj
     */
	private void postConfirmarBorradoVersion(Object obj) {       
		try {
			
            final Version os = (Version) obj;
            
            // Verifica si se puede borrar
            // TODO PENDIENTE VERIFICACION            
            
            // Borramos
            DelegateUtil.getVersionDelegate().borrarVersion(os.getCodigo());
    		                     
            // Actualizamos modelo
            for (final Version o : versiones) {
                if (o.getCodigo().equals(os.getCodigo())) {
                	versiones.remove(o);
                    break;
                }
            }
            listVersiones.setModel(new BindingListModelList(versiones, true));
		} catch (es.caib.redose.persistence.delegate.DelegateException e) {
			ConsolaUtil.generaDelegateException(e);
		}   
    }
    
    
    /**
     * Ayuda.
     * 
     */
    public final void onClick$btnAyuda() {
        GTTUtilsWeb.onClickBotonAyuda("gestor/modeloDocumento");
    }
    
    
    
}
