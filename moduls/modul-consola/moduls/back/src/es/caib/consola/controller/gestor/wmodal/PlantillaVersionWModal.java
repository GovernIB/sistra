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
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;
import org.zkoss.zul.ListModelList;
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
import es.caib.redose.model.Formateador;
import es.caib.redose.model.Plantilla;
import es.caib.redose.model.PlantillaIdioma;
import es.caib.redose.model.Version;
import es.caib.redose.persistence.delegate.DelegateException;
import es.caib.redose.persistence.delegate.DelegateUtil;
import es.caib.zkutils.ConstantesZK;
import es.caib.zkutils.zk.composer.BaseComposer;

/**btnNuevaPlantillaIdioma
 * Ventana de edicion plantilla version.
 */
@SuppressWarnings("serial")
public class PlantillaVersionWModal extends BaseComposer {

	/** Referencia ventana. */
    private Window wPlantillaVersion;
    /** Usuario logeado.*/
	private Usuario usuarioLogado;
    /** Modo edicion. */
    private TypeModoAcceso modo;
    /** Plantilla editado.*/
    private Plantilla plantillaVersion;
    /** Version a la que pertenece la plantilla. */
    private Version versionModelo;
    	
    /** Id. */
    private Textbox codigo;   
    /** Atributo combo formateadores. */
    private Combobox formateador;   
    /** Defecto. */
    private Checkbox defecto;
    /** Barcode. */
    private Checkbox barcode;
    /** Sello. */
    private Checkbox sello;
    
    /** Row plantillas.*/
    private Row plantillasIdiomaRow;
    /** Atributo list plantillas. */
    private Listbox listPlantillasIdioma;
    /** Atributo lista plantillas. */
    private List<PlantillaIdioma> plantillasIdioma = new ArrayList<PlantillaIdioma>();
    
    
    /** Atributo lista formateadores. */
    private List<Formateador> formateadores = new ArrayList<Formateador>();
    
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
     	plantillaVersion = (Plantilla) arg.get(ConstantesWEB.PARAM_OBJETO_EDICION);
     	versionModelo = (Version)  arg.get(ConstantesWEB.PARAM_OBJETO_PADRE);

     	// Usuario autenticado
     	usuarioLogado = ConsolaUtil.recuperarUsuarioLogado();     	
     	
     	// Item renderer lista versiones
     	listPlantillasIdioma.setItemRenderer(new ListitemRenderer() {
            public void render(final Listitem item, final Object data, final int index) {
                final PlantillaIdioma reg = (PlantillaIdioma) data;
                item.setValue(reg);
                item.setLabel(Labels.getLabel("idioma." + reg.getIdioma()));                               
            }
        });
     	
     	// Item renderer 
     	formateador.setItemRenderer(new ComboitemRenderer() {
			public void render(Comboitem item, Object data, final int index) throws Exception {
				final Formateador reg = (Formateador) data;
                item.setValue(reg.getIdentificador() + "");
                item.setLabel(reg.getDescripcion()); 
                item.setAttribute("data", reg);
			}
		});		     	
        
     	//Refrescamos valores
     	refrescarDatos();
     	// Mapea datos a los controles de la pantalla
     	mapearDatos();
     	// Eventos
        manejadoresEventos();
     	
    }
    
    /** After render combo formateador. */
    public final void onAfterRender$formateador() {
    	if (plantillaVersion != null) {		
			this.seleccionarCombo(plantillaVersion.getFormateador().getIdentificador() + "", formateador);
		} else {
			this.seleccionarCombo(formateador.getItemAtIndex(0).getValue() + "", formateador);
		}
    }
    
    /**
     * Eventos.
     */
    private void manejadoresEventos() {
    	// - evento refresco datos tras modificacion
    	wPlantillaVersion.addEventListener(
    			ConstantesZK.EVENTO_POST_MODIFICACION, new EventListener() {
					public void onEvent(final Event pEvent) {
						if (pEvent.getData() instanceof PlantillaIdioma) {
							postModificacionPlantillaIdioma( (PlantillaIdioma) pEvent.getData());
						} 						
                    }
                });
        // - evento refresco datos tras alta
    	wPlantillaVersion.addEventListener(ConstantesZK.EVENTO_POST_ALTA,
                new EventListener() {
                    public void onEvent(final Event pEvent) {                               	
                    	if (pEvent.getData() instanceof PlantillaIdioma) {
							postAltaPlantillaIdioma( (PlantillaIdioma) pEvent.getData());
						}                     	
                    }
                });       
    			
	}
    
    @Override
    protected void postConfirmarBorrado(final Object obj) {    	    	
    	if (obj instanceof PlantillaIdioma) {
    		postConfirmarBorradoPlantillaIdioma(obj);
    	}    	
    }   
    
     /**
     * Si se pasan datos, se refrescan de BBDD para asegurarse q es lo ultimo.
     */
    private void refrescarDatos() {
    	try {            	
    		if (plantillaVersion != null) {
    			plantillaVersion = DelegateUtil.getPlantillaDelegate().obtenerPlantilla(plantillaVersion.getCodigo());    		    			
    			refrescarListaPlantillasIdioma();
    		}        	
    		refrescarComboFormateadores();
		} catch (DelegateException e) {
			ConsolaUtil.generaDelegateException(e);
		}	
    }

    /** Refresca combo formateadoers. */
	private void refrescarComboFormateadores() {		
		try {
			formateadores = DelegateUtil.getFormateadorDelegate().listar();
			formateador.setModel(new ListModelList(formateadores, true));			
		} catch (DelegateException e) {					
			ConsolaUtil.generaDelegateException(e);
		}
	}
    
    /** Refresca lista versiones. */
	private void refrescarListaPlantillasIdioma() {		
		try {
			if (plantillaVersion != null) {
				plantillasIdioma = ConsolaUtil.mapToList(DelegateUtil.getPlantillaIdiomaDelegate().listarPlantillasIdiomaPlantilla(plantillaVersion.getCodigo()));
				listPlantillasIdioma.selectItem(null);
				listPlantillasIdioma.setModel(new BindingListModelList(plantillasIdioma, true));			
			}
		} catch (DelegateException e) {					
			ConsolaUtil.generaDelegateException(e);
		}
	}
    

    /**
     * Mapea datos a los controles de la pantalla.
     */
	private void mapearDatos() {
		if (plantillaVersion != null) {
			codigo.setValue(plantillaVersion.getTipo());	
			defecto.setChecked(plantillaVersion.getDefecto() == 'S');
			barcode.setChecked(plantillaVersion.getBarcode() == 'S');
			sello.setChecked(plantillaVersion.getSello() == 'S');
		}		
		codigo.setReadonly(modo != TypeModoAcceso.ALTA);    
		plantillasIdiomaRow.setVisible(modo != TypeModoAcceso.ALTA);		
	}

	
	 /**
     * Click boton Cancelar.
     */
    public final void onClick$btnCancelar() {
    	wPlantillaVersion.detach();
    }
    
    /**
     * Click boton Guardar.
     */
    public final void onClick$btnGuardar() {
    	Plantilla plantillaModif = null;	   	
    	try {
	    	// Modo consulta: acceso no permitido
	    	if (modo == TypeModoAcceso.CONSULTA) {
	    		ConsolaUtil.generaOperacionNoPermitidaException();
	    	}else if (modo == TypeModoAcceso.ALTA) {
	        	plantillaModif = new Plantilla();  	        	
	        } else {
	        	plantillaModif = plantillaVersion;            	            
	        }
	    	    	
	    	// Verificamos campos obligatorios y establecemos valores
	    	// - Codigo / desc / cacheable
	        String[] nomCampos = {Labels.getLabel("plantillaVersion.codigo")};
	        String[] valCampos = {codigo.getText()};
	        boolean ok = verificarCamposObligatorios(nomCampos, valCampos);	       	        
	    	
	    	// Verifica si ya existe modelo con ese id
	    	if (ok) {
	    		boolean existe = false;
	    		Version m = DelegateUtil.getVersionDelegate().obtenerVersion(versionModelo.getCodigo());
	    		for (Iterator it = m.getPlantillas().iterator(); it.hasNext();) {
	    			Plantilla v = (Plantilla) it.next();
	    			if (v.getTipo().equals(plantillaModif.getTipo()) && !v.getCodigo().equals(plantillaModif.getCodigo())) {
	    				existe = true;
	    				break;
	    			}	    		
	    		}
	    		if (existe) {	
	    			ok = false;
		        	mostrarError(Labels.getLabel("plantillaVersion.codigo") + ": " + Labels.getLabel("error.codigoRepetido"),
		        			 Labels.getLabel("mensaje.atencion"));    
	    		}
	    	}
	    	
	    	 
	    	// Establecemos datos
	    	if (ok) {	    		
	    		plantillaModif.setTipo(codigo.getValue().trim().toUpperCase());	    
	    		plantillaModif.setFormateador( (Formateador)formateador.getSelectedItem().getAttribute("data"));
	    		plantillaModif.setDefecto(defecto.isChecked()?'S':'N');
	    		plantillaModif.setBarcode(barcode.isChecked()?'S':'N');
	    		plantillaModif.setSello(sello.isChecked()?'S':'N');	    		
	    	}
	    	
	        // Guardamos datos
	        if (ok) {                	        	                                       	                        	
	           	// Grabamos organo
	        	Long id = DelegateUtil.getPlantillaDelegate().grabarPlantilla(plantillaModif, versionModelo.getCodigo());
	        	plantillaModif.setCodigo(id);
	        	//Generamos evento            	
	            Events.postEvent(new Event(ConsolaUtil.eventoModoAcesso(modo), wPlantillaVersion.getParent(), plantillaModif));    		    		
	           
	            // Si es alta, pasamos a modo modificacion para poder dar de alta versiones
	            if (modo == TypeModoAcceso.ALTA) {
	            	modo = TypeModoAcceso.EDICION;
	            	plantillaVersion = plantillaModif;
	            	codigo.setReadonly(true);    
	        		plantillasIdiomaRow.setVisible(true);	     
	        		
	        		mostrarInfo(Labels.getLabel("plantillaVersion.altaOk"), Labels.getLabel("mensaje.atencion"));
	        		
	            } else {
	            	wPlantillaVersion.detach();
	            }
	        }
	        
    	
        } catch (Exception e) {
			ConsolaUtil.generaDelegateException(e);
		}
    }     
    
    /**
     * Click boton editar.
     */
    public final void onClick$btnEditaPlantillaIdioma() {
    	final Object valueSelected = obtenerItemSeleccionado(listPlantillasIdioma);
        if (valueSelected != null) {
        	PlantillaIdioma data = (PlantillaIdioma) ((Listitem) listPlantillasIdioma.getSelectedItem()
                    .clone()).getValue();
            final Map<String, Object> map = new HashMap<String, Object>();
            map.put(ConstantesWEB.PARAM_MODO_ACCESO, TypeModoAcceso.EDICION);            
			map.put(ConstantesWEB.PARAM_OBJETO_EDICION, data);            
			map.put(ConstantesWEB.PARAM_OBJETO_PADRE, plantillaVersion);
            final Window ventana = (Window) creaComponente(
                    "/gestor/windows/ges-plantillaIdioma-win.zul", this.self, map);
            abreVentanaModal(ventana);
        } 
    }
    
    /**
     * Click boton nuevo.
     */
    public final void onClick$btnNuevaPlantillaIdioma() {
    	final Map<String, Object> map = new HashMap<String, Object>();   
    	map.put(ConstantesWEB.PARAM_MODO_ACCESO, TypeModoAcceso.ALTA);   
    	map.put(ConstantesWEB.PARAM_OBJETO_PADRE, plantillaVersion);	
        final Window ventana = (Window) creaComponente(
                  "/gestor/windows/ges-plantillaIdioma-win.zul", this.self, map);
        abreVentanaModal(ventana);       
    }
    
    /**
     * Eliminar.
     */
    public void onClick$btnBorraPlantillaIdioma() {
        final Object valueSelected = obtenerItemSeleccionado(listPlantillasIdioma);
        if (valueSelected != null) {
            confirmarBorrado(valueSelected);
        }
    }
    
    
    /**
     * Nuevo.
     */
    public void onClick$PopupNuevaPlantillaIdioma() {
    	onClick$btnNuevaPlantillaIdioma();
    }
    
    /**
     * Editar.
     */
    public void onClick$PopupEditaPlantillaIdioma() {
    	onClick$btnEditaPlantillaIdioma();
    }
    
    /**
     * Eliminar.
     */
    public void onClick$PopupBorraPlantillaIdioma() {
    	onClick$btnBorraPlantillaIdioma();
    }
    
    
    /**
     * Evento post modificacion.
     * @param data Cuenta
     */
    private void postModificacionPlantillaIdioma(PlantillaIdioma os) {		
		// Actualiza datos modelo
    	int index = 0;
		for (final PlantillaIdioma o : this.plantillasIdioma) {
		    if (o.getCodigo().equals(os.getCodigo())) {
		    	plantillasIdioma.set(index, os);
		        break;
		    }
		    index++;
		}
		listPlantillasIdioma.setModel(new BindingListModelList(plantillasIdioma, true));
		
	}
    
    /**
     * Evento post alta.
     * @param data Cuenta
     */
    private void postAltaPlantillaIdioma(PlantillaIdioma data) {		
        refrescarListaPlantillasIdioma();       
	}
    

    /**
     * Post confirmar borrado.
     * @param obj
     */
	private void postConfirmarBorradoPlantillaIdioma(Object obj) {       
		try {
			
            final PlantillaIdioma os = (PlantillaIdioma) obj;
            
            // Verifica si se puede borrar
            // TODO PENDIENTE VERIFICACION            
            
            // Borramos
            DelegateUtil.getPlantillaIdiomaDelegate().borrarPlantillaIdioma(os.getCodigo());
    		                     
            // Actualizamos modelo
            for (final PlantillaIdioma o : plantillasIdioma) {
                if (o.getCodigo().equals(os.getCodigo())) {
                	plantillasIdioma.remove(o);
                    break;
                }
            }
            listPlantillasIdioma.setModel(new BindingListModelList(plantillasIdioma, true));
		} catch (es.caib.redose.persistence.delegate.DelegateException e) {
			ConsolaUtil.generaDelegateException(e);
		}   
    }
    
    
    /**
     * Ayuda.
     * 
     */
    public final void onClick$btnAyuda() {
        GTTUtilsWeb.onClickBotonAyuda("gestor/plantillaVersion");
    }
    
}
