package es.caib.consola.controller.gestor.wmodal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
import es.caib.redose.model.Plantilla;
import es.caib.redose.model.Version;
import es.caib.redose.persistence.delegate.DelegateException;
import es.caib.redose.persistence.delegate.DelegateUtil;
import es.caib.zkutils.ConstantesZK;
import es.caib.zkutils.zk.composer.BaseComposer;

/**
 * Ventana de edicion version modelo documento.
 */
@SuppressWarnings("serial")
public class VersionModeloWModal extends BaseComposer {

	/** Referencia ventana. */
    private Window wVersionModelo;
    /** Usuario logeado.*/
	private Usuario usuarioLogado;
    /** Modo edicion. */
    private TypeModoAcceso modo;
    /** Version Modelo documento editado.*/
    private Version versionModelo;
    /** Modelo al que pertenece la version. */
    private Modelo modeloDocumento;
    	
    /** Id. */
    private Textbox codigo;   
    /** Descripcion. */
    private Textbox descripcion;
    
    /** Row plantillas.*/
    private Row plantillasRow;
    /** Atributo list plantillas. */
    private Listbox listPlantillas;
    /** Atributo lista plantillas. */
    private List<Plantilla> plantillas = new ArrayList<Plantilla>();
    
    
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
     	versionModelo = (Version) arg.get(ConstantesWEB.PARAM_OBJETO_EDICION);
     	modeloDocumento = (Modelo)  arg.get(ConstantesWEB.PARAM_OBJETO_PADRE);

     	// Usuario autenticado
     	usuarioLogado = ConsolaUtil.recuperarUsuarioLogado();     	
     	//Refrescamos valores
     	refrescarDatos();
     	
     	
     	// Item renderer lista versiones
     	listPlantillas.setItemRenderer(new ListitemRenderer() {
            public void render(final Listitem item, final Object data, final int index) {
                final Plantilla reg = (Plantilla) data;
                item.setValue(reg);
                item.setLabel(reg.getTipo());                               
            }
        });
        
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
    	wVersionModelo.addEventListener(
    			ConstantesZK.EVENTO_POST_MODIFICACION, new EventListener() {
					public void onEvent(final Event pEvent) {
						if (pEvent.getData() instanceof Plantilla) {
							postModificacionPlantilla( (Plantilla) pEvent.getData());
						} 						
                    }
                });
        // - evento refresco datos tras alta
    	wVersionModelo.addEventListener(ConstantesZK.EVENTO_POST_ALTA,
                new EventListener() {
                    public void onEvent(final Event pEvent) {                               	
                    	if (pEvent.getData() instanceof Plantilla) {
							postAltaPlantilla( (Plantilla) pEvent.getData());
						}                     	
                    }
                });
		
	}
    
    @Override
    protected void postConfirmarBorrado(final Object obj) {    	    	
    	if (obj instanceof Plantilla) {
    		postConfirmarBorradoPlantilla(obj);
    	}
    }   
    
     /**
     * Si se pasan datos, se refrescan de BBDD para asegurarse q es lo ultimo.
     */
    private void refrescarDatos() {
    	try {            	
    		if (versionModelo != null) {
    			versionModelo = DelegateUtil.getVersionDelegate().obtenerVersion(versionModelo.getCodigo());
    			refrescarListaPlantillas();
    		}        	
		} catch (DelegateException e) {
			ConsolaUtil.generaDelegateException(e);
		}	
    }

    /** Refresca lista versiones. */
	private void refrescarListaPlantillas() {
		try {
			if (versionModelo != null) {
				plantillas = ConsolaUtil.setToList(es.caib.redose.persistence.delegate.DelegateUtil.getPlantillaDelegate().listarPlantillasVersion(versionModelo.getCodigo()));
				listPlantillas.selectItem(null);
				listPlantillas.setModel(new BindingListModelList(plantillas, true));			
			}
		} catch (es.caib.redose.persistence.delegate.DelegateException e) {					
			ConsolaUtil.generaDelegateException(e);
		}
	}
    

    /**
     * Mapea datos a los controles de la pantalla.
     */
	private void mapearDatos() {
		if (versionModelo != null) {
			codigo.setValue(versionModelo.getVersion() + "");			
			descripcion.setValue(versionModelo.getDescripcion());			
		}		
		codigo.setReadonly(modo != TypeModoAcceso.ALTA);    
		plantillasRow.setVisible(modo != TypeModoAcceso.ALTA);		
	}

	
	 /**
     * Click boton Cancelar.
     */
    public final void onClick$btnCancelar() {
    	wVersionModelo.detach();
    }
    
    /**
     * Click boton Guardar.
     */
    public final void onClick$btnGuardar() {
    	Version versionModif = null;	   	
    	try {
	    	// Modo consulta: acceso no permitido
	    	if (modo == TypeModoAcceso.CONSULTA) {
	    		ConsolaUtil.generaOperacionNoPermitidaException();
	    	}else if (modo == TypeModoAcceso.ALTA) {
	        	versionModif = new Version();  	        	
	        } else {
	        	versionModif = versionModelo;            	            
	        }
	    	    	
	    	// Verificamos campos obligatorios y establecemos valores
	    	// - Codigo / desc / cacheable
	        String[] nomCampos = {Labels.getLabel("versionModelo.codigo"), Labels.getLabel("versionModelo.descripcion")};
	        String[] valCampos = {codigo.getText(),descripcion.getText()};
	        boolean ok = verificarCamposObligatorios(nomCampos, valCampos);
	       
	        // Verifica si id tiene formato correcto
	        int numVersion = 0;
	    	if (ok){
	    		try {
	    			numVersion = Integer.parseInt(codigo.getText());
	    		} catch (NumberFormatException nex) {
	    			ok = false;
		        	mostrarError(Labels.getLabel("versionModelo.codigo") + ": " + Labels.getLabel("error.formatoIdentificadorNoValido"),
		        			 Labels.getLabel("mensaje.atencion"));   
	    		}
	    	}
	    	
	    	// Verifica si ya existe modelo con ese id
	    	if (ok) {
	    		boolean existe = false;
	    		Modelo m = DelegateUtil.getModeloDelegate().obtenerModelo(modeloDocumento.getCodigo());
	    		for (Iterator it = m.getVersiones().iterator(); it.hasNext();) {
	    			Version v = (Version) it.next();
	    			if (v.getVersion() == numVersion && !v.getCodigo().equals(versionModif.getCodigo())) {
	    				existe = true;
	    				break;
	    			}	    		
	    		}
	    		if (existe) {	
	    			ok = false;
		        	mostrarError(Labels.getLabel("versionModelo.codigo") + ": " + Labels.getLabel("error.codigoRepetido"),
		        			 Labels.getLabel("mensaje.atencion"));    
	    		}
	    	}
	    	
	    	// Establecemos datos
	    	if (ok) {
	    		versionModif.setVersion(numVersion);	    		
	    		versionModif.setDescripcion(descripcion.getText());	    		
	    	}
	    	
	        // Guardamos datos
	        if (ok) {                	        	                                       	                        	
	           	// Grabamos organo
	        	Long id = DelegateUtil.getVersionDelegate().grabarVersion(versionModif, modeloDocumento.getCodigo());
	        	versionModif.setCodigo(id);
	        	//Generamos evento            	
	            Events.postEvent(new Event(ConsolaUtil.eventoModoAcesso(modo), wVersionModelo.getParent(), versionModif));    		
	            
	            // Si es alta, pasamos a modo modificacion para poder dar de alta versiones
	            if (modo == TypeModoAcceso.ALTA) {
	            	modo = TypeModoAcceso.EDICION;
	            	versionModelo = versionModif;
	            	codigo.setReadonly(true);    
	        		plantillasRow.setVisible(true);	     
	        		
	        		mostrarInfo(Labels.getLabel("versionModelo.altaOk"), Labels.getLabel("mensaje.atencion"));
	        		
	            } else {
	            	wVersionModelo.detach();
	            }
	            
	        }
    	
        } catch (Exception e) {
			ConsolaUtil.generaDelegateException(e);
		}
    }     
    
    /**
     * Click boton editar.
     */
    public final void onClick$btnEditaPlantilla() {
    	final Object valueSelected = obtenerItemSeleccionado(listPlantillas);
        if (valueSelected != null) {
        	Plantilla data = (Plantilla) ((Listitem) listPlantillas.getSelectedItem()
                    .clone()).getValue();
            final Map<String, Object> map = new HashMap<String, Object>();
            map.put(ConstantesWEB.PARAM_MODO_ACCESO, TypeModoAcceso.EDICION);            
			map.put(ConstantesWEB.PARAM_OBJETO_EDICION, data);            
			map.put(ConstantesWEB.PARAM_OBJETO_PADRE, versionModelo);
            final Window ventana = (Window) creaComponente(
                    "/gestor/windows/ges-plantillaVersion-win.zul", this.self, map);
            abreVentanaModal(ventana);
        } 
    }
    
    /**
     * Click boton nuevo.
     */
    public final void onClick$btnNuevaPlantilla() {
    	final Map<String, Object> map = new HashMap<String, Object>();   
    	map.put(ConstantesWEB.PARAM_MODO_ACCESO, TypeModoAcceso.ALTA);   
    	map.put(ConstantesWEB.PARAM_OBJETO_PADRE, versionModelo);
        final Window ventana = (Window) creaComponente(
                  "/gestor/windows/ges-plantillaVersion-win.zul", this.self, map);
        abreVentanaModal(ventana);       
    }
    
    /**
     * Eliminar.
     */
    public void onClick$btnBorraPlantilla() {
        final Object valueSelected = obtenerItemSeleccionado(listPlantillas);
        if (valueSelected != null) {
            confirmarBorrado(valueSelected);
        }
    }
    
    
    /**
     * Nuevo.
     */
    public void onClick$PopupNuevaPlantilla() {
    	onClick$btnNuevaPlantilla();
    }
    
    /**
     * Editar.
     */
    public void onClick$PopupEditaPlantilla() {
    	onClick$btnEditaPlantilla();
    }
    
    /**
     * Eliminar.
     */
    public void onClick$PopupBorraPlantilla() {
    	onClick$btnBorraPlantilla();
    }
    
    
    /**
     * Evento post modificacion.
     * @param data Cuenta
     */
    private void postModificacionPlantilla(Plantilla os) {		
		// Actualiza datos modelo
    	int index = 0;
		for (final Plantilla o : this.plantillas) {
		    if (o.getCodigo().equals(os.getCodigo())) {
		    	plantillas.set(index, os);
		        break;
		    }
		    index++;
		}
		listPlantillas.setModel(new BindingListModelList(plantillas, true));
		
	}
    
    /**
     * Evento post alta.
     * @param data Cuenta
     */
    private void postAltaPlantilla(Plantilla data) {		
        refrescarListaPlantillas();       
	}
    

    /**
     * Post confirmar borrado.
     * @param obj
     */
	private void postConfirmarBorradoPlantilla(Object obj) {       
		try {
			
            final Plantilla os = (Plantilla) obj;
            
            // Verifica si se puede borrar
            // TODO PENDIENTE VERIFICACION            
            
            // Borramos
            DelegateUtil.getPlantillaDelegate().borrarPlantilla(os.getCodigo());
    		                     
            // Actualizamos modelo
            for (final Plantilla o : plantillas) {
                if (o.getCodigo().equals(os.getCodigo())) {
                	plantillas.remove(o);
                    break;
                }
            }
            listPlantillas.setModel(new BindingListModelList(plantillas, true));
		} catch (es.caib.redose.persistence.delegate.DelegateException e) {
			ConsolaUtil.generaDelegateException(e);
		}   
    }
    
    
    /**
     * Ayuda.
     * 
     */
    public final void onClick$btnAyuda() {
        GTTUtilsWeb.onClickBotonAyuda("gestor/versionModelo");
    }
    
}
