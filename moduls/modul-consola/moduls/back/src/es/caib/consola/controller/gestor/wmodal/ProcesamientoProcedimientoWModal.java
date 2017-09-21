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
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Spinner;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vlayout;
import org.zkoss.zul.Window;

import es.caib.bantel.model.FuenteDatos;
import es.caib.bantel.model.Procedimiento;
import es.caib.bantel.model.TraProcedimiento;
import es.caib.bantel.persistence.delegate.DelegateException;
import es.caib.bantel.persistence.delegate.DelegateUtil;
import es.caib.bantel.persistence.delegate.ProcedimientoDelegate;
import es.caib.consola.ConstantesWEB;
import es.caib.consola.model.Usuario;
import es.caib.consola.model.types.TypeModoAcceso;
import es.caib.consola.util.ConsolaUtil;
import es.caib.consola.util.GTTUtilsWeb;
import es.caib.sistra.model.Dominio;
import es.caib.util.CifradoUtil;
import es.caib.zkutils.ConstantesZK;
import es.caib.zkutils.zk.composer.BaseComposer;

/**
 * Ventana de edicion procesamiento procedimiento.
 */
@SuppressWarnings("serial")
public class ProcesamientoProcedimientoWModal extends BaseComposer {

	/** Referencia ventana. */
    private Window wProcesamientoProcedimiento;
    /** Usuario logeado.*/
	private Usuario usuarioLogado;
    /** Modo edicion. */
    private TypeModoAcceso modo;
    /** Procedimiento editado.*/
    private Procedimiento procedimiento;
    	
    
    /** Tabbox organismo. */
    private Tabbox tabBoxOrganismo;
    
    /** Id dominio: textbox. */
    private Textbox codigo;    
    /** Descripcion dominio. */
    private Textbox descripcion;
    
    /** Activado. */
    private Checkbox activo;
    /** Intervalo.*/
    private Spinner intervalo;
    /** Inmediato. */
    private Checkbox inmediato;
    
    /** Tipo dominio. */
    private Radiogroup tipo;
    /** Tipo dominio EJB. */
    private Radio ejb;
    /** Tipo dominio WS. */
    private Radio webservice;
  
    /** EJB: atributos.*/
    private Vlayout ejbFields;
    /** EJB: JNDI. */
    private Textbox ejbJndi;
    /** EJB: URL. */
    private Textbox ejbUrl;
    /** EJB: URL (label). */
    private Label lblEjbUrl;
    /** EJB: Localizacion. */
    private Radiogroup ejbLocalizacion;
    /**  EJB: Localizacion local. */
    private Radio ejbLocalizacionLocal;
    /**  EJB: Localizacion remota. */
    private Radio ejbLocalizacionRemoto;
    
    /** WS: atributos. */
    private Vlayout wsFields;
    /** WS: URL. */
    private Textbox wsUrl;
    /** WS: Version. */
    private Combobox wsVersion;
    /** WS: Version 1. */
    private Comboitem wsVersionV1;
    /** WS: Version 2. */
    private Comboitem wsVersionV2;
    
    /** Fila configuracion avisos.*/
    private Row avisosRow;   
    /** Fila configuracion tipo.*/
    private Row tipoRow;
    /** Fila configuracion auth.*/
    private Row autenticacionRow;
    /** Layout usu/pass.*/
    private Hlayout usuPassLayout;
    
    /** Auth. */
    private Radiogroup autenticacionExplicita;
    /**  Aut Explicita no. */
    private Radio autenticacionExplicitaNo;
    /**  Aut Explicita no. */
    private Radio autenticacionExplicitaUsuPass;
    /**  Aut Explicita no. */
    private Radio autenticacionExplicitaOrganismo;
    /** Usu. */
    private Textbox autenticacionExplicitaUsuario;   
    /** Pass. */
    private Textbox autenticacionExplicitaPassword;   
            
    /** Listbox fuentes datos. */
    private Listbox listFuenteDatos;
    /** Lista fuentes datos. */
    private List<FuenteDatos> fuentesDatos = new ArrayList<FuenteDatos>();
    
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
     	procedimiento = (Procedimiento) arg.get(ConstantesWEB.PARAM_OBJETO_EDICION);     	 

     	// Usuario autenticado
     	usuarioLogado = ConsolaUtil.recuperarUsuarioLogado();
     	
     	// Refresca datos
     	refrescarDatos();
     	
     	// Eventos
     	manejadoresEventos();
        
     	// Mapea datos a los controles de la pantalla
     	mapearDatos();
     	
     	afterComposeTabFuentesDatos();
     	
    }
    
    private void manejadoresEventos() {
   	 // - evento refresco datos tras modificacion
   	wProcesamientoProcedimiento.addEventListener(
   			ConstantesZK.EVENTO_POST_MODIFICACION, new EventListener() {
					public void onEvent(final Event pEvent) {
						if (pEvent.getData() instanceof FuenteDatos) {
							postModificacionFuenteDatos((FuenteDatos) pEvent.getData());
						} 						
                   }
               });
       // - evento refresco datos tras alta
   	wProcesamientoProcedimiento.addEventListener(ConstantesZK.EVENTO_POST_ALTA,
               new EventListener() {
                   public void onEvent(final Event pEvent) {
                   	if (pEvent.getData() instanceof FuenteDatos) {
							postAltaFuenteDatos( (FuenteDatos) pEvent.getData());
						} 
                   }
               });
		
	}

    @Override
    protected void postConfirmarBorrado(final Object obj) {    	
    	if (obj instanceof FuenteDatos) {
    		postConfirmarBorradoFuenteDatos(obj);
    	}     	
    }
    
    /**
     * Si se pasan datos, se refrescan de BBDD para asegurarse q es lo ultimo.
     */
    private void refrescarDatos() {
    	try {            	
    		procedimiento = DelegateUtil.getTramiteDelegate().obtenerProcedimiento(procedimiento.getIdentificador());     				    		        
		} catch (DelegateException e) {
			ConsolaUtil.generaDelegateException(e);
		}	
    }
    
    /**
     * Mapea datos a los controles de la pantalla.
     */
	private void mapearDatos() {
		codigo.setValue(procedimiento.getIdentificador());
		TraProcedimiento proc = (TraProcedimiento) procedimiento.getTraduccion("ca");
		descripcion.setValue(proc.getDescripcion());
		
		activo.setChecked(procedimiento.getIntervaloInforme() != null && procedimiento.getIntervaloInforme().longValue() > 0);
		intervalo.setValue( (procedimiento.getIntervaloInforme() != null)? Integer.parseInt(procedimiento.getIntervaloInforme().toString()) :0 );
		inmediato.setChecked('S' == procedimiento.getInmediata());
		
		onCheck$activo();
		
		// En funcion tipo dominio habilitamos campos
		switch (procedimiento.getTipoAcceso()) {			
			case Dominio.DOMINIO_EJB:
				tipo.setSelectedItem(ejb);
				ejbJndi.setValue(procedimiento.getJndiEJB());
				ejbUrl.setValue(procedimiento.getUrl());
				switch (procedimiento.getLocalizacionEJB()) {
		    		case Dominio.EJB_LOCAL:
		    			ejbLocalizacion.setSelectedItem(ejbLocalizacionLocal);
		    			break;
		    		case Dominio.EJB_REMOTO:
		    			ejbLocalizacion.setSelectedItem(ejbLocalizacionRemoto);
		    			break;
		    		default:
		    			ejbLocalizacion.setSelectedItem(ejbLocalizacionLocal);
				}            		
				break;
			case Dominio.DOMINIO_WEBSERVICE:
				tipo.setSelectedItem(webservice);
				wsUrl.setValue(procedimiento.getUrl());
				wsVersion.setSelectedItem( (procedimiento.getVersionWS() == "v1"? wsVersionV1 : wsVersionV2));
				break;	
			default:
				tipo.setSelectedItem(webservice);
		}
		onCheck$tipo();
		
		// Auth
		switch (procedimiento.getAutenticacionEJB()) {	
			case Procedimiento.AUTENTICACION_SIN:
				autenticacionExplicita.setSelectedItem(autenticacionExplicitaNo);
				break;
			case Procedimiento.AUTENTICACION_ESTANDAR:
				autenticacionExplicita.setSelectedItem(autenticacionExplicitaUsuPass);
				try {
					String claveCifrado = (String) DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion().get("clave.cifrado");					 
					autenticacionExplicitaUsuario.setValue(CifradoUtil.descifrar(claveCifrado,procedimiento.getUsr()));
					autenticacionExplicitaPassword.setValue(CifradoUtil.descifrar(claveCifrado,procedimiento.getPwd()));
				} catch (Exception ex) {
					ConsolaUtil.generaDelegateException(ex);
				}				
				break;
			case Procedimiento.AUTENTICACION_ORGANISMO:
				autenticacionExplicita.setSelectedItem(autenticacionExplicitaOrganismo);
				break;
			default:
				autenticacionExplicita.setSelectedItem(autenticacionExplicitaNo);									
		}
		onCheck$autenticacionExplicita();
		
	}
	
	 /**
     * Click boton Cancelar.
     */
    public final void onClick$btnCancelar() {
    	wProcesamientoProcedimiento.detach();
    }

	 /**
     * Manejador cambio radio tipo dominio.
     */
    public final void onCheck$tipo() {
        if (tipo.getSelectedItem() == ejb) {
        	ejbFields.setVisible(true);
            wsFields.setVisible(false);                        
        	onCheck$ejbLocalizacion();            
        } else if (tipo.getSelectedItem() == webservice) {
        	ejbFields.setVisible(false);
            wsFields.setVisible(true);            
        } 
    }
    
    /**
     * Manejador cambio radio localizacion EJB.
     */
    public final void onCheck$ejbLocalizacion() {
    	// Muestra URL solo si remoto
    	boolean mostrarUrl = (ejbLocalizacion.getSelectedItem() == ejbLocalizacionRemoto); 
    	lblEjbUrl.setVisible(mostrarUrl);
    	ejbUrl.setVisible(mostrarUrl);
    }
    
    /**
     * Si se marca check de activo mostramos opciones configuracion.
     */
    public final void onCheck$activo() {
    	avisosRow.setVisible(activo.isChecked());
    	tipoRow.setVisible(activo.isChecked());
    	autenticacionRow.setVisible(activo.isChecked());
    	
    }
    
    /**
     * Manejador cambio radio auth.
     */
    public final void onCheck$autenticacionExplicita() {
    	usuPassLayout.setVisible(autenticacionExplicita.getSelectedItem() == autenticacionExplicitaUsuPass); 
    }
    
    /**
     * Click boton Guardar.
     */
    public final void onClick$btnGuardar() {
    	 try { 
    		 	 ProcedimientoDelegate delegate = DelegateUtil.getTramiteDelegate();
    		 
		    	// Modo consulta: acceso no permitido
		    	if (modo == TypeModoAcceso.CONSULTA) {
		    		ConsolaUtil.generaOperacionNoPermitidaException();
		    	}
		    	
		    	// Verificamos campos obligatorios y establecemos valores   	
		        boolean ok = true;
		        
		        if (!activo.isChecked()) {
		        	// No activo: intervalo = 0
		        	procedimiento.setIntervaloInforme(new Long(0));
		        } else {		        	
		        	// Activo: intervalo positivo
    				if (intervalo.getValue() == null || intervalo.getValue().intValue() <= 0) {
    					ok = false;
    					mostrarError(Labels.getLabel("error.mayorCero") + " '"
    		                    + Labels.getLabel("procesamientoProcedimiento.intervalo") + "'", Labels.getLabel("mensaje.atencion"));
    				}	
    				procedimiento.setIntervaloInforme(new Long(intervalo.getValue().intValue()));
		        	
		        	// Acceso EJB
    				if (ok && ejb.isChecked()) {
		    			// JNDI Obligatorio
		    			ok = verificarCampoObligatorio(Labels.getLabel("procesamientoProcedimiento.ejb.jndi"), ejbJndi.getText()); 		    				
		    			// Url obligario para remoto
		    			if (ejbLocalizacionRemoto.isChecked()) {
		    				ok = ok && verificarCampoObligatorio(Labels.getLabel("procesamientoProcedimiento.ejb.url"), ejbUrl.getText());
		    			}
		    			
		    			if (ok) {
		    				procedimiento.setTipoAcceso(Dominio.DOMINIO_EJB);
		    				procedimiento.setLocalizacionEJB(ejbLocalizacionRemoto.isChecked()?Dominio.EJB_REMOTO:Dominio.EJB_LOCAL);
		    				procedimiento.setJndiEJB(ejbJndi.getText());
		    				procedimiento.setUrl(ejbUrl.getText());
		    			}
    				}	    				
    				
    				// Acceso WS
    				if (ok && webservice.isChecked()) {
						// Version ws
	    				if (wsVersion.getSelectedItem() == null) {
	    					mostrarError(Labels.getLabel("error.campoObligatorio") + " '"
	    		                    + Labels.getLabel("procesamientoProcedimiento.ws.version") + "'", Labels.getLabel("mensaje.atencion"));
	    					ok = false;
	    				}
	    				// Url 
	    				ok = ok && verificarCampoObligatorio(Labels.getLabel("procesamientoProcedimiento.ws.url"), wsUrl.getText());
	    				
	    				if (ok) {
	    					procedimiento.setTipoAcceso(Dominio.DOMINIO_WEBSERVICE);
	    					procedimiento.setVersionWS((String) wsVersion.getSelectedItem().getValue());
	    					procedimiento.setUrl(wsUrl.getText());
	    				}
    				}		
			    	
		        }
		        
		        // - Autenticacion
		        if (ok) {
			        procedimiento.setInmediata(inmediato.isChecked()?'S':'N');
			        procedimiento.setAutenticacionEJB(((String) autenticacionExplicita.getSelectedItem().getValue()).charAt(0));
			        String claveCifrado = (String) DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion().get("clave.cifrado");					 
			        procedimiento.setUsr(CifradoUtil.cifrar(claveCifrado,autenticacionExplicitaUsuario.getValue()));
			        procedimiento.setPwd(CifradoUtil.cifrar(claveCifrado,autenticacionExplicitaPassword.getValue()));
		        }		        		        
		        
		        // Guardamos datos
		        if (ok) {       		        	
		        	delegate.grabarProcedimiento(procedimiento);            	
		            Events.postEvent(new Event(ConsolaUtil.eventoModoAcesso(modo), wProcesamientoProcedimiento.getParent(), procedimiento));
		    		wProcesamientoProcedimiento.detach();
		        }
    	
    	 } catch (Exception e) {
 			ConsolaUtil.generaDelegateException(e);
 		}
    }    
    
    // TAB FICHEROS EXPORTACION
    
    /**
     * After compose tab.
     */
	private void afterComposeTabFuentesDatos() {		
	}
    
	/**
     * Método para On click$tab.
     * 
     */
    public final void onClick$tabFuentesDatos() {    	    
    	if (fuentesDatos.size() == 0) {
			refrescarListaFuenteDatos();		
    	}    	
    }   

    /** Refresca lista. */
	private void refrescarListaFuenteDatos() {
		try {
			fuentesDatos = DelegateUtil.getFuenteDatosDelegate().listarFuentesDatos(procedimiento.getIdentificador());			
			listFuenteDatos.selectItem(null);
			listFuenteDatos.setModel(new BindingListModelList(fuentesDatos, true));			
		} catch (DelegateException e) {					
			ConsolaUtil.generaDelegateException(e);
		}
	}

    /**
     * Doble click item.
     * 
     */
    public final void onDoubleClick$itemFuenteDatos() {
    	onClick$btnEditaFuenteDatos();
    }
    
    /**
     * Click boton editar.
     */
    public final void onClick$btnEditaFuenteDatos() {
    	final Object valueSelected = obtenerItemSeleccionado(listFuenteDatos);
        if (valueSelected != null) {
        	FuenteDatos data = (FuenteDatos) ((Listitem) listFuenteDatos.getSelectedItem()
                    .clone()).getValue();
            final Map<String, Object> map = new HashMap<String, Object>();
            map.put(ConstantesWEB.PARAM_MODO_ACCESO, TypeModoAcceso.EDICION);            
			map.put(ConstantesWEB.PARAM_OBJETO_EDICION, data);  
			map.put(ConstantesWEB.PARAM_OBJETO_PADRE, procedimiento);
            final Window ventana = (Window) creaComponente(
                    "/gestor/windows/ges-fuenteDatos-win.zul", this.self, map);
            abreVentanaModal(ventana);
        } 
    }
    
    /**
     * Click boton nuevo.
     */
    public final void onClick$btnNuevoFuenteDatos() {
    	final Map<String, Object> map = new HashMap<String, Object>();   
    	map.put(ConstantesWEB.PARAM_MODO_ACCESO, TypeModoAcceso.ALTA);   
    	map.put(ConstantesWEB.PARAM_OBJETO_PADRE, procedimiento);
        final Window ventana = (Window) creaComponente(
                  "/gestor/windows/ges-fuenteDatos-win.zul", this.self, map);
        abreVentanaModal(ventana);       
    }
    
    /**
     * Eliminar.
     */
    public void onClick$btnQuitaFuenteDatos() {
        final Object valueSelected = obtenerItemSeleccionado(listFuenteDatos);
        if (valueSelected != null) {
            confirmarBorrado(valueSelected);
        }
    }
    
    
    /**
     * Nuevo.
     */
    public void onClick$PopupNuevoFuenteDatos() {
    	onClick$btnNuevoFuenteDatos();
    }
    
    /**
     * Editar.
     */
    public void onClick$PopupEditaFuenteDatos() {
    	onClick$btnEditaFuenteDatos();
    }
    
    /**
     * Eliminar.
     */
    public void onClick$PopupQuitaFuenteDatos() {
    	onClick$btnQuitaFuenteDatos();
    }
    
    /**
     * Evento post modificacion.
     * @param data Cuenta
     */
    private void postModificacionFuenteDatos(FuenteDatos os) {		
		// Actualiza datos modelo	
    	int index = 0;
		for (final FuenteDatos o : this.fuentesDatos) {
		    if (o.getCodigo().equals(os.getCodigo())) {
		    	fuentesDatos.set(index, os);
		        break;
		    }
		    index++;
		}
		listFuenteDatos.setModel(new BindingListModelList(fuentesDatos, true));
		
	}
    
    /**
     * Evento post alta.
     * @param data Cuenta
     */
    private void postAltaFuenteDatos(
    		FuenteDatos data) {		
        // Refrescamos datos
        refrescarListaFuenteDatos();       
	}
    

    /**
     * Post confirmar borrado.
     * @param obj
     */
	private void postConfirmarBorradoFuenteDatos(Object obj) {       
		try {
			
            final FuenteDatos os = (FuenteDatos) obj;                       
            
            // Borramos
            DelegateUtil.getFuenteDatosDelegate().borrarFuenteDatos(os.getIdentificador());
    		                     
            // Actualizamos modelo
            for (final FuenteDatos o : fuentesDatos) {
                if (o.getIdentificador().equals(os.getIdentificador())) {
                	fuentesDatos.remove(o);
                    break;
                }
            }
            listFuenteDatos.setModel(new BindingListModelList(fuentesDatos, true));
		} catch (DelegateException e) {
			ConsolaUtil.generaDelegateException(e);
		}   
    }
    
	 /**
     * Ayuda.
     * 
     */
    public final void onClick$btnAyuda() {
        GTTUtilsWeb.onClickBotonAyuda("gestor/fichaProcesamientoProcedimiento_" + tabBoxOrganismo.getSelectedTab().getId());
    }
	
}
