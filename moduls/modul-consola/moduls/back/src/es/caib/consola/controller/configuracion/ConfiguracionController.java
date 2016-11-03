package es.caib.consola.controller.configuracion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zul.Button;
import org.zkoss.zul.Include;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import es.caib.bantel.model.GestorBandeja;
import es.caib.bantel.model.Procedimiento;
import es.caib.consola.ConstantesWEB;
import es.caib.consola.controller.configuracion.renderer.OrganismoListitemRenderer;
import es.caib.consola.model.AlertaPlataforma;
import es.caib.consola.model.Usuario;
import es.caib.consola.model.types.TypeModoAcceso;
import es.caib.consola.util.ConsolaUtil;
import es.caib.consola.util.GTTUtilsWeb;
import es.caib.mobtratel.model.Cuenta;
import es.caib.redose.model.Formateador;
import es.caib.redose.model.Ubicacion;
import es.caib.sistra.model.GestorFormulario;
import es.caib.sistra.model.OrganoResponsable;
import es.caib.sistra.persistence.delegate.DelegateException;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.zkutils.ConstantesZK;
import es.caib.zkutils.zk.composer.BaseComposer;


/**
 * Controller ventana configuracion.
 */
@SuppressWarnings("serial")
public class ConfiguracionController extends BaseComposer {

	/** Ventana configuracion. */
    private Window wConfiguracion;
    /** Componente padre. */
    private Component padre;
    /** Usuario autenticado. */
    private Usuario usuarioLogado;
    /** Tabs. **/
    private Tabbox configuracion;
    
    // TAB ORGANISMO
    /** Filtro actual. */
    private String filtroActualOrganismo = "";
    /** Textbos filtro organismo. */
    private Textbox filtroOrganismo;
    /** Listbox organismos. */
    private Listbox listOrganismos;
    /** Lista de organismos. */
    private List<OrganoResponsable> organismos = new ArrayList<OrganoResponsable>();
    
    // TAB PROCEDIMIENTO
    /** Filtro actual. */
    private String filtroActualProcedimiento = "";
    /** Atributo list procedimientos. */
    private Listbox listProcedimientos;
    /** Atributo filtro procedimiento. */
    private Textbox filtroProcedimiento;
    /** Atributo btn edita procedimiento. */
    private Button btnEditaProcedimiento;
    /** Atributo popup edita procedimiento. */
    private Menuitem popupEditaProcedimiento;
    /** Atributo procedimientos. */
    private List<Procedimiento> procedimientos = new ArrayList<Procedimiento>();
    
    // TAB GESTOR
    /** Filtro actual. */
    private String filtroActualGestores = "";
    /** Atributo list gestores. */
    private Listbox listGestoresBandeja;
    /** Atributo filtro procedimiento. */
    private Textbox filtroGestorBandeja;
    /** Atributo btn edita procedimiento. */
    private Button btnEditaGestorBandeja;
    /** Atributo popup edita procedimiento. */
    private Menuitem popupEditaGestorBandeja;
    /** Atributo gestores. */
    private List<GestorBandeja> gestoresBandeja = new ArrayList<GestorBandeja>();
    
    // TAB CUENTAS ENVIO
    /** Filtro actual. */
    private String filtroActualCuentasEnvio = "";
    /** Atributo list cuentasEnvio. */
    private Listbox listCuentasEnvio;
    /** Atributo filtro cuentaEnvio. */
    private Textbox filtroCuentaEnvio;
    /** Atributo btn edita cuentaEnvio. */
    private Button btnEditaCuentaEnvio;
    /** Atributo popup edita cuentaEnvio. */
    private Menuitem popupEditaCuentaEnvio;
    /** Atributo cuentasEnvio. */
    private List<Cuenta> cuentasEnvio = new ArrayList<Cuenta>();
    
    // TAB ERRORES
    /** Filtro actual. */
    private String filtroActualErrores = "";
    /** Atributo list errores. */
    private Listbox listErrores;
    /** Atributo filtro error. */
    private Textbox filtroError;
    /** Atributo btn edita error. */
    private Button btnEditaError;
    /** Atributo popup edita error. */
    private Menuitem popupEditaError;
    /** Atributo errors. */
    private List<AlertaPlataforma> errores = new ArrayList<AlertaPlataforma>();
    

    // TAB GESTOR FORMULARIOS
    /** Filtro actual. */
    private String filtroActualGestoresFormulario = "";
    /** Atributo list gestorFormularios. */
    private Listbox listGestoresFormulario;
    /** Atributo filtro gestorFormulario. */
    private Textbox filtroGestorFormulario;
    /** Atributo btn edita gestorFormulario. */
    private Button btnEditaGestorFormulario;
    /** Atributo popup edita gestorFormulario. */
    private Menuitem popupEditaGestorFormulario;
    /** Atributo gestorFormularios. */
    private List<GestorFormulario> gestoresFormulario = new ArrayList<GestorFormulario>();
    
    // TAB MANTENIMIENTO REDOSE
    /** Atributo list formateadores. */
    private Listbox listFormateadores;
    /** Atributo lista formateadores. */
    private List<Formateador> formateadores = new ArrayList<Formateador>();
    /** Atributo list ubicaciones. */
    private Listbox listUbicaciones;
    /** Atributo lista formateadores. */
    private List<Ubicacion> ubicaciones = new ArrayList<Ubicacion>();
       
    
    
    /*
     * (non-Javadoc)
     * 
     * @see
     * es.gva.dgm.tra.util.zk.composer.BaseComposer#doAfterCompose(org.zkoss
     * .zk.ui.Component)
     */
    @Override
    public final void doAfterCompose(final Component compConfiguracion) {
        
    	super.doAfterCompose(compConfiguracion);

    	usuarioLogado = ConsolaUtil.recuperarUsuarioLogado();
    	padre = compConfiguracion.getParent();
        
        afterComposeTabOrganismos();
        afterComposeTabProcedimientos();
        afterComposeTabGestoresBandeja();
        afterComposeTabCuentasEnvio();
        afterComposeTabGestoresFormulario();
        afterComposeTabMantenimientoRedose();
        
        manejadoresEventos();
        
    }
    
    
    private void manejadoresEventos() {
    	 // - evento refresco datos tras modificacion
    	wConfiguracion.addEventListener(
    			ConstantesZK.EVENTO_POST_MODIFICACION, new EventListener() {
					public void onEvent(final Event pEvent) {
						if (pEvent.getData() instanceof OrganoResponsable) {
							postModificacionOrganoResponsable( (OrganoResponsable) pEvent.getData());
						} 
						if (pEvent.getData() instanceof Procedimiento) {
							postModificacionProcedimiento( (Procedimiento) pEvent.getData());
						} 
						if (pEvent.getData() instanceof GestorBandeja) {
							postModificacionGestorBandeja( (GestorBandeja) pEvent.getData());
						} 
						if (pEvent.getData() instanceof Cuenta) {
							postModificacionCuentaEnvio((Cuenta) pEvent.getData());
						}
						if (pEvent.getData() instanceof GestorFormulario) {
							postModificacionGestorFormulario((GestorFormulario) pEvent.getData());
						}
						if (pEvent.getData() instanceof Formateador) {
							postModificacionFormateador((Formateador) pEvent.getData());
						}
						if (pEvent.getData() instanceof Ubicacion) {
							postModificacionUbicacion((Ubicacion) pEvent.getData());
						}
                    }
                });
        // - evento refresco datos tras alta
    	wConfiguracion.addEventListener(ConstantesZK.EVENTO_POST_ALTA,
                new EventListener() {
                    public void onEvent(final Event pEvent) {
                    	if (pEvent.getData() instanceof OrganoResponsable) {
							postAltaOrganoResponsable( (OrganoResponsable) pEvent.getData());
						} 
                    	if (pEvent.getData() instanceof Procedimiento) {
							postAltaProcedimiento( (Procedimiento) pEvent.getData());
						} 
                    	if (pEvent.getData() instanceof GestorBandeja) {
							postAltaGestorBandeja( (GestorBandeja) pEvent.getData());
						}
                    	if (pEvent.getData() instanceof Cuenta) {
                    		postAltaCuentaEnvio((Cuenta) pEvent.getData());
						}
                    	if (pEvent.getData() instanceof GestorFormulario) {
                    		postAltaGestorFormulario((GestorFormulario) pEvent.getData());
						}
                    	if (pEvent.getData() instanceof Formateador) {
							postAltaFormateador((Formateador) pEvent.getData());
						}
                    	if (pEvent.getData() instanceof Ubicacion) {
							postAltaUbicacion((Ubicacion) pEvent.getData());
						}
                    }
                });
		
	}
    
    @Override
    protected void postConfirmarBorrado(final Object obj) {    	
    	if (obj instanceof OrganoResponsable) {
    		postConfirmarBorradoOrganismo(obj);
    	} 
    	if (obj instanceof Procedimiento) {
    		postConfirmarBorradoProcedimiento(obj);
    	}
    	if (obj instanceof GestorBandeja) {
    		postConfirmarBorradoGestorBandeja(obj);
    	}
    	if (obj instanceof Cuenta) {
    		postConfirmarBorradoCuentaEnvio(obj);
    	}
    	if (obj instanceof GestorFormulario) {
    		postConfirmarBorradoGestorFormulario(obj);
    	}
    	if (obj instanceof Formateador) {
    		postConfirmarBorradoFormateador(obj);
    	}
    	if (obj instanceof Ubicacion) {
    		postConfirmarBorradoUbicacion(obj);
    	}
    }

	/**
     * Click cerrar configuracion.
     */
    public final void onClick$btnCerrar() {
    	// Refresca pantalla principal
    	habilitaInclude(  (Include) padre
                        .getFellow(ConstantesWEB.CONTENEDOR_PRINCIPAL),  "/gestor/ges-organismo.zul", null);
    	// Cierra pantalla configuracion
        wConfiguracion.detach();
    }
    
    /**
     * Ayuda.
     * 
     */
    public final void onClick$btnAyuda() {
        GTTUtilsWeb.onClickBotonAyuda("configuracion/" + configuracion.getSelectedTab().getId());
    }
    
    // TAB ORGANISMOS

    /**
     * After compose tab organismos.
     */
	private void afterComposeTabOrganismos() {
		listOrganismos.setItemRenderer(new OrganismoListitemRenderer(
                usuarioLogado.getIdioma()));
        refrescarListaOrganismos();	       
	}
	
	 
    /**
     * Filtra organismos.
     * 
     */
    public final void onClick$btnBuscarOrganismos() {    	
    	filtroActualOrganismo = filtroOrganismo.getValue();
    	refrescarListaOrganismos();			    			
    }

    /** Refresca lista organismos. */
	private void refrescarListaOrganismos() {
		try {
    		organismos = DelegateUtil.getOrganoResponsableDelegate().listarOrganoResponsables(filtroActualOrganismo);
			listOrganismos.selectItem(null);	       
	        listOrganismos.setModel(new BindingListModelList(organismos, true));			
		} catch (es.caib.sistra.persistence.delegate.DelegateException e) {					
			ConsolaUtil.generaDelegateException(e);
		}
	}
	
    /**
     * Doble click item organismo: abre edicion organismo.
     */
    public final void onDoubleClick$itemOrganismo() {
        onClick$btnEditaOrganismo();
    }
    
    /**
     * Click boton editar procedimiento.
     */
    public final void onClick$btnEditaOrganismo() {    	
    	final Object valueSelected = obtenerItemSeleccionado(listOrganismos);
        if (valueSelected != null) {
        	OrganoResponsable organismo = (OrganoResponsable) ((Listitem) listOrganismos.getSelectedItem()
                    .clone()).getValue();
        	final Map<String, Object> map = new HashMap<String, Object>();
            map.put(ConstantesWEB.PARAM_MODO_ACCESO, TypeModoAcceso.EDICION);            
			map.put(ConstantesWEB.PARAM_OBJETO_EDICION, organismo);            
            final Window ventana = (Window) creaComponente(
                    "/configuracion/windows/con-organismo-win.zul", this.self, map);
            abreVentanaModal(ventana);
        }    	
    }
    
    /**
     * Click boton nuevo organismo.
     */
    public final void onClick$btnNuevoOrganismo() {
    	final Map<String, Object> map = new HashMap<String, Object>();
    	map.put(ConstantesWEB.PARAM_MODO_ACCESO, TypeModoAcceso.ALTA);            			         
        final Window ventana = (Window) creaComponente(
                  "/configuracion/windows/con-organismo-win.zul", this.self, map);
        abreVentanaModal(ventana);       
    }
    
    /**
     * Eliminar organismo.
     */
    public void onClick$btnQuitaOrganismo() {
        final Object valueSelected = obtenerItemSeleccionado(listOrganismos);
        if (valueSelected != null) {
            confirmarBorrado(valueSelected);
        }
    }
    
    /**
     * Nuevo organismo.
     */
    public void onClick$PopupNuevoOrganismo() {
    	onClick$btnNuevoOrganismo();
    }
    
    /**
     * Editar organismo.
     */
    public void onClick$PopupEditaOrganismo() {
    	onClick$btnEditaOrganismo();
    }
    
    /**
     * Eliminar organismo.
     */
    public void onClick$PopupQuitaOrganismo() {
    	onClick$btnQuitaOrganismo();
    }
    
    
    /**
     * Evento post modificacion organo.
     * @param data Organo
     */
    private void postModificacionOrganoResponsable(OrganoResponsable os) {		
		// Actualiza datos modelo
    	int index = 0;
		for (final OrganoResponsable o : this.organismos) {
		    if (o.getCodigo().equals(os.getCodigo())) {
		    	organismos.set(index, os);
		        break;
		    }
		    index++;		 
		}
		listOrganismos.setModel(new BindingListModelList(organismos, true));
		
	}
    
    /**
     * Evento post alta organo.
     * @param data Organo
     */
    private void postAltaOrganoResponsable(
			OrganoResponsable data) {
		
    	// Obtenemos pagina actual
        final int paginaActual = listOrganismos.getActivePage();
        
        // Refrescamos datos (teniendo en cuentra filtro busqueda)
        refrescarListaOrganismos();
        
        // Vamos a pagina anterior
        if (listOrganismos.getPageCount() < paginaActual) {
        	listOrganismos.setActivePage(paginaActual);
        }
		
	}
    

    /**
     * Post confirmar borrado organismo.
     * @param obj
     */
	private void postConfirmarBorradoOrganismo(Object obj) {       
		try {
			
            final OrganoResponsable os = (OrganoResponsable) obj;
            
            // Verifica si se puede borrar
            if (DelegateUtil.getTramiteDelegate().listarTramitesOrganoResponsable(os.getCodigo()).size() > 0
            		|| DelegateUtil.getDominioDelegate().listarDominios(os.getCodigo()).size() > 0) {
            	ConsolaUtil.generaNoPermitidoBorrarException();
            }
            
            // Borramos
            DelegateUtil.getOrganoResponsableDelegate().borrarOrganoResponsable(os.getCodigo());
    		                     

            // Actualizamos modelo
            for (final OrganoResponsable o : organismos) {
                if (o.getCodigo().equals(os.getCodigo())) {
                	organismos.remove(o);
                    break;
                }
            }
            listOrganismos.setModel(new BindingListModelList(organismos, true));
		} catch (DelegateException e) {
			ConsolaUtil.generaDelegateException(e);
		}   
    }
	
	
	

    
    // TAB PROCEDIMIENTOS
    
    /**
     * After compose tab procedimientos.
     */
	private void afterComposeTabProcedimientos() {		
	}
    
	/**
     * Método para On click$tab procedimiento.
     * 
     */
    public final void onClick$tabProcedimientos() {
    	// TODO NO SE FUERZA A RECUPERAR, SOLO LA 1º VEZ...    
    	if (procedimientos.size() == 0) {
    		refrescarListaProcedimientos();		
    	}		
    }
    
    /**
     * Filtra organismos.
     * 
     */
    public final void onClick$btnBuscarProcedimiento() {    	
    	filtroActualProcedimiento = filtroProcedimiento.getValue();
    	refrescarListaProcedimientos();			    			
    }

    /** Refresca lista organismos. */
	private void refrescarListaProcedimientos() {
		try {
			procedimientos = es.caib.bantel.persistence.delegate.DelegateUtil.getTramiteDelegate().listarProcedimientos(filtroActualProcedimiento);
			listProcedimientos.selectItem(null);	       
			listProcedimientos.setModel(new BindingListModelList(procedimientos, true));			
		} catch (es.caib.bantel.persistence.delegate.DelegateException e) {					
			ConsolaUtil.generaDelegateException(e);
		}
	}

    /**
     * Doble click item procedimiento.
     * 
     */
    public final void onDoubleClick$itemProcedimiento() {
    	onClick$btnEditaProcedimiento();
    }
    
    /**
     * Click boton editar procedimiento.
     */
    public final void onClick$btnEditaProcedimiento() {
    	final Object valueSelected = obtenerItemSeleccionado(listProcedimientos);
        if (valueSelected != null) {
        	Procedimiento procedimiento = (Procedimiento) ((Listitem) listProcedimientos.getSelectedItem()
                    .clone()).getValue();
            final Map<String, Object> map = new HashMap<String, Object>();
            map.put(ConstantesWEB.PARAM_MODO_ACCESO, TypeModoAcceso.EDICION);            
			map.put(ConstantesWEB.PARAM_OBJETO_EDICION, procedimiento);            
            final Window ventana = (Window) creaComponente(
                    "/configuracion/windows/con-procedimiento-win.zul", this.self, map);
            abreVentanaModal(ventana);
        } 
    }
    
    /**
     * Click boton nuevo organismo.
     */
    public final void onClick$btnNuevoProcedimiento() {
    	final Map<String, Object> map = new HashMap<String, Object>();    
    	map.put(ConstantesWEB.PARAM_MODO_ACCESO, TypeModoAcceso.ALTA);  
        final Window ventana = (Window) creaComponente(
                  "/configuracion/windows/con-procedimiento-win.zul", this.self, map);
        abreVentanaModal(ventana);       
    }
    
    /**
     * Eliminar procedimiento.
     */
    public void onClick$btnQuitaProcedimiento() {
        final Object valueSelected = obtenerItemSeleccionado(listProcedimientos);
        if (valueSelected != null) {
            confirmarBorrado(valueSelected);
        }
    }
    
    
    /**
     * Nuevo Procedimiento.
     */
    public void onClick$PopupNuevoProcedimiento() {
    	onClick$btnNuevoProcedimiento();
    }
    
    /**
     * Editar Procedimiento.
     */
    public void onClick$PopupEditaProcedimiento() {
    	onClick$btnEditaProcedimiento();
    }
    
    /**
     * Eliminar Procedimiento.
     */
    public void onClick$PopupQuitaProcedimiento() {
    	onClick$btnQuitaProcedimiento();
    }
    
    /**
     * Evento post modificacion procedimiento.
     * @param data Organo
     */
    private void postModificacionProcedimiento(Procedimiento os) {		
		// Actualiza datos modelo
    	int index = 0;
		for (final Procedimiento o : this.procedimientos) {
		    if (o.getIdentificador().equals(os.getIdentificador())) {
		    	procedimientos.set(index, os);
		        break;
		    }
		    index++;
		}
		listProcedimientos.setModel(new BindingListModelList(procedimientos, true));
		
	}
    
    /**
     * Evento post alta procedimiento.
     * @param data Organo
     */
    private void postAltaProcedimiento(
    		Procedimiento data) {
		
    	// Obtenemos pagina actual
        final int paginaActual = listProcedimientos.getActivePage();
        
        // Refrescamos datos (teniendo en cuentra filtro busqueda)
        refrescarListaProcedimientos();
        
        // Vamos a pagina anterior
        if (listProcedimientos.getPageCount() < paginaActual) {
        	listProcedimientos.setActivePage(paginaActual);
        }
		
	}
    

    /**
     * Post confirmar borrado procedimiento.
     * @param obj
     */
	private void postConfirmarBorradoProcedimiento(Object obj) {       
		try {
			
            final Procedimiento os = (Procedimiento) obj;
            
            // Verifica si se puede borrar
            // TODO PENDIENTE VERIFICACION
            mostrarMessageBox("PENDIENTE VERIFICAR SI SE PUEDE BORRAR",
                    Labels.getLabel(ConstantesWEB.WARNING), Messagebox.OK,
                    Messagebox.EXCLAMATION);
            /*
            if (DelegateUtil.getTramiteDelegate().listarTramitesOrganoResponsable(os.getCodigo()).size() > 0
            		|| DelegateUtil.getDominioDelegate().listarDominios(os.getCodigo()).size() > 0) {
            	ConsolaUtil.generaNoPermitidoBorrarException();
            }
            */
            
            // Borramos
            es.caib.bantel.persistence.delegate.DelegateUtil.getTramiteDelegate().borrarProcedimiento(os.getIdentificador());
    		                     
            // Actualizamos modelo
            for (final Procedimiento o : procedimientos) {
                if (o.getIdentificador().equals(os.getIdentificador())) {
                	procedimientos.remove(o);
                    break;
                }
            }
            listProcedimientos.setModel(new BindingListModelList(procedimientos, true));
		} catch (es.caib.bantel.persistence.delegate.DelegateException e) {
			ConsolaUtil.generaDelegateException(e);
		}   
    }
    
    // TAB GESTORES BANDEJA
    
    /**
     * After compose tab gestoresBandeja.
     */
	private void afterComposeTabGestoresBandeja() {		
	}
    
	/**
     * Método para On click$tab gestor.
     * 
     */
    public final void onClick$tabGestoresBandeja() {    	    
    	if (gestoresBandeja.size() == 0) {
			refrescarListaGestoresBandeja();		
    	}		
    }
    
    
    /**
     * Filtra gestores.
     * 
     */
    public final void onClick$btnBuscarGestorBandeja() {    	
    	filtroActualGestores = filtroGestorBandeja.getValue();
    	refrescarListaGestoresBandeja();			    			
    }

    /** Refresca lista gestores. */
	private void refrescarListaGestoresBandeja() {
		try {
			gestoresBandeja = es.caib.bantel.persistence.delegate.DelegateUtil.getGestorBandejaDelegate().listarGestoresBandeja(filtroActualGestores);
			listGestoresBandeja.selectItem(null);
			listGestoresBandeja.setModel(new BindingListModelList(gestoresBandeja, true));			
		} catch (es.caib.bantel.persistence.delegate.DelegateException e) {					
			ConsolaUtil.generaDelegateException(e);
		}
	}

    /**
     * Doble click item gestor.
     * 
     */
    public final void onDoubleClick$itemGestorBandeja() {
    	onClick$btnEditaGestorBandeja();
    }
    
    /**
     * Click boton editar gestor.
     */
    public final void onClick$btnEditaGestorBandeja() {
    	final Object valueSelected = obtenerItemSeleccionado(listGestoresBandeja);
        if (valueSelected != null) {
        	GestorBandeja data = (GestorBandeja) ((Listitem) listGestoresBandeja.getSelectedItem()
                    .clone()).getValue();
            final Map<String, Object> map = new HashMap<String, Object>();
            map.put(ConstantesWEB.PARAM_MODO_ACCESO, TypeModoAcceso.EDICION);            
			map.put(ConstantesWEB.PARAM_OBJETO_EDICION, data);            
            final Window ventana = (Window) creaComponente(
                    "/configuracion/windows/con-gestorBandeja-win.zul", this.self, map);
            abreVentanaModal(ventana);
        } 
    }
    
    /**
     * Click boton nuevo gestor.
     */
    public final void onClick$btnNuevoGestorBandeja() {
    	final Map<String, Object> map = new HashMap<String, Object>();   
    	map.put(ConstantesWEB.PARAM_MODO_ACCESO, TypeModoAcceso.ALTA);   
        final Window ventana = (Window) creaComponente(
                  "/configuracion/windows/con-gestorBandeja-win.zul", this.self, map);
        abreVentanaModal(ventana);       
    }
    
    /**
     * Eliminar gestor.
     */
    public void onClick$btnQuitaGestorBandeja() {
        final Object valueSelected = obtenerItemSeleccionado(listGestoresBandeja);
        if (valueSelected != null) {
            confirmarBorrado(valueSelected);
        }
    }
    
    
    /**
     * Nuevo gestor.
     */
    public void onClick$PopupNuevoGestorBandeja() {
    	onClick$btnNuevoGestorBandeja();
    }
    
    /**
     * Editar gestor.
     */
    public void onClick$PopupEditaGestorBandeja() {
    	onClick$btnEditaGestorBandeja();
    }
    
    /**
     * Eliminar gestor.
     */
    public void onClick$PopupQuitaGestorBandeja() {
    	onClick$btnQuitaGestorBandeja();
    }
    
    /**
     * Evento post modificacion gestor.
     * @param data gestor
     */
    private void postModificacionGestorBandeja(GestorBandeja os) {		
		// Actualiza datos modelo
    	int index = 0;
		for (final GestorBandeja o : this.gestoresBandeja) {			
		    if (o.getSeyconID().equals(os.getSeyconID())) {
		    	gestoresBandeja.set(index, os);
		        break;
		    }
		    index++;
		}
		listGestoresBandeja.setModel(new BindingListModelList(gestoresBandeja, true));
		
	}
    
    /**
     * Evento post alta gestor.
     * @param data Gestor
     */
    private void postAltaGestorBandeja(
    		GestorBandeja data) {
		
    	// Obtenemos pagina actual
        final int paginaActual = listGestoresBandeja.getActivePage();
        
        // Refrescamos datos (teniendo en cuentra filtro busqueda)
        refrescarListaGestoresBandeja();
        
        // Vamos a pagina anterior
        if (listGestoresBandeja.getPageCount() < paginaActual) {
        	listGestoresBandeja.setActivePage(paginaActual);
        }
		
	}
    

    /**
     * Post confirmar borrado gestor.
     * @param obj
     */
	private void postConfirmarBorradoGestorBandeja(Object obj) {       
		try {
			
            final GestorBandeja os = (GestorBandeja) obj;
            
            // Verifica si se puede borrar
            // TODO PENDIENTE VERIFICACION
            mostrarMessageBox("PENDIENTE VERIFICAR SI SE PUEDE BORRAR",
                    Labels.getLabel(ConstantesWEB.WARNING), Messagebox.OK,
                    Messagebox.EXCLAMATION);
            /*
            if (DelegateUtil.getTramiteDelegate().listarTramitesOrganoResponsable(os.getCodigo()).size() > 0
            		|| DelegateUtil.getDominioDelegate().listarDominios(os.getCodigo()).size() > 0) {
            	ConsolaUtil.generaNoPermitidoBorrarException();
            }
            */
            
            // Borramos
            es.caib.bantel.persistence.delegate.DelegateUtil.getGestorBandejaDelegate().borrarGestorBandeja(os.getSeyconID());
    		                     
            // Actualizamos modelo
            for (final GestorBandeja o : gestoresBandeja) {
                if (o.getSeyconID().equals(os.getSeyconID())) {
                	gestoresBandeja.remove(o);
                    break;
                }
            }
            listGestoresBandeja.setModel(new BindingListModelList(gestoresBandeja, true));
		} catch (es.caib.bantel.persistence.delegate.DelegateException e) {
			ConsolaUtil.generaDelegateException(e);
		}   
    }
    
    
    // TAB CUENTAS ENVIO
    
    /**
     * After compose tab cuentasEnvio.
     */
	private void afterComposeTabCuentasEnvio() {		
	}
    
	/**
     * Método para On click$tab.
     * 
     */
    public final void onClick$tabCuentasEnvio() {    	    
    	if (cuentasEnvio.size() == 0) {
			refrescarListaCuentasEnvio();		
    	}		
    }
    
    
    /**
     * Filtra.
     * 
     */
    public final void onClick$btnBuscarCuentasEnvio() {    	
    	filtroActualCuentasEnvio = filtroCuentaEnvio.getValue();
    	refrescarListaCuentasEnvio();			    			
    }

    /** Refresca lista. */
	private void refrescarListaCuentasEnvio() {
		try {
			cuentasEnvio = es.caib.mobtratel.persistence.delegate.DelegateUtil.getCuentaDelegate().listarCuentas(filtroActualCuentasEnvio);
			listCuentasEnvio.selectItem(null);
			listCuentasEnvio.setModel(new BindingListModelList(cuentasEnvio, true));			
		} catch (es.caib.mobtratel.persistence.delegate.DelegateException e) {					
			ConsolaUtil.generaDelegateException(e);
		}
	}

    /**
     * Doble click item.
     * 
     */
    public final void onDoubleClick$itemCuentaEnvio() {
    	onClick$btnEditaCuentaEnvio();
    }
    
    /**
     * Click boton editar.
     */
    public final void onClick$btnEditaCuentaEnvio() {
    	final Object valueSelected = obtenerItemSeleccionado(listCuentasEnvio);
        if (valueSelected != null) {
        	Cuenta data = (Cuenta) ((Listitem) listCuentasEnvio.getSelectedItem()
                    .clone()).getValue();
            final Map<String, Object> map = new HashMap<String, Object>();
            map.put(ConstantesWEB.PARAM_MODO_ACCESO, TypeModoAcceso.EDICION);            
			map.put(ConstantesWEB.PARAM_OBJETO_EDICION, data);            
            final Window ventana = (Window) creaComponente(
                    "/configuracion/windows/con-cuentaEnvio-win.zul", this.self, map);
            abreVentanaModal(ventana);
        } 
    }
    
    /**
     * Click boton nuevo.
     */
    public final void onClick$btnNuevoCuentaEnvio() {
    	final Map<String, Object> map = new HashMap<String, Object>();   
    	map.put(ConstantesWEB.PARAM_MODO_ACCESO, TypeModoAcceso.ALTA);   
        final Window ventana = (Window) creaComponente(
                  "/configuracion/windows/con-cuentaEnvio-win.zul", this.self, map);
        abreVentanaModal(ventana);       
    }
    
    /**
     * Eliminar.
     */
    public void onClick$btnQuitaCuentaEnvio() {
        final Object valueSelected = obtenerItemSeleccionado(listCuentasEnvio);
        if (valueSelected != null) {
            confirmarBorrado(valueSelected);
        }
    }
    
    
    /**
     * Nuevo.
     */
    public void onClick$PopupNuevoCuentaEnvio() {
    	onClick$btnNuevoCuentaEnvio();
    }
    
    /**
     * Editar.
     */
    public void onClick$PopupEditaCuentaEnvio() {
    	onClick$btnEditaCuentaEnvio();
    }
    
    /**
     * Eliminar.
     */
    public void onClick$PopupQuitaCuentaEnvio() {
    	onClick$btnQuitaCuentaEnvio();
    }
    
    /**
     * Evento post modificacion.
     * @param data Cuenta
     */
    private void postModificacionCuentaEnvio(Cuenta os) {		
		// Actualiza datos modelo
    	int index = 0;
		for (final Cuenta o : this.cuentasEnvio) {
		    if (o.getCodigo().equals(os.getCodigo())) {
		    	cuentasEnvio.set(index, os);
		        break;
		    }
		    index++;
		}
		listCuentasEnvio.setModel(new BindingListModelList(cuentasEnvio, true));
		
	}
    
    /**
     * Evento post alta.
     * @param data Cuenta
     */
    private void postAltaCuentaEnvio(
    		Cuenta data) {
		
    	// Obtenemos pagina actual
        final int paginaActual = listCuentasEnvio.getActivePage();
        
        // Refrescamos datos (teniendo en cuentra filtro busqueda)
        refrescarListaCuentasEnvio();
        
        // Vamos a pagina anterior
        if (listCuentasEnvio.getPageCount() < paginaActual) {
        	listCuentasEnvio.setActivePage(paginaActual);
        }
		
	}
    

    /**
     * Post confirmar borrado.
     * @param obj
     */
	private void postConfirmarBorradoCuentaEnvio(Object obj) {       
		try {
			
            final Cuenta os = (Cuenta) obj;
            
            // Verifica si se puede borrar
            // TODO PENDIENTE VERIFICACION
            mostrarMessageBox("PENDIENTE VERIFICAR SI SE PUEDE BORRAR",
                    Labels.getLabel(ConstantesWEB.WARNING), Messagebox.OK,
                    Messagebox.EXCLAMATION);
            /*
            if (DelegateUtil.getTramiteDelegate().listarTramitesOrganoResponsable(os.getCodigo()).size() > 0
            		|| DelegateUtil.getDominioDelegate().listarDominios(os.getCodigo()).size() > 0) {
            	ConsolaUtil.generaNoPermitidoBorrarException();
            }
            */
            
            // Borramos
            es.caib.mobtratel.persistence.delegate.DelegateUtil.getCuentaDelegate().borrarCuenta(os.getCodigo());
    		                     
            // Actualizamos modelo
            for (final Cuenta o : cuentasEnvio) {
                if (o.getCodigo().equals(os.getCodigo())) {
                	cuentasEnvio.remove(o);
                    break;
                }
            }
            listCuentasEnvio.setModel(new BindingListModelList(cuentasEnvio, true));
		} catch (es.caib.mobtratel.persistence.delegate.DelegateException e) {
			ConsolaUtil.generaDelegateException(e);
		}   
    }

    
 // TAB GESTORES FORMULARIO
    
    /**
     * After compose tab gestoresFormulario.
     */
	private void afterComposeTabGestoresFormulario() {		
	}
    
	/**
     * Método para On click$tab.
     * 
     */
    public final void onClick$tabGestoresFormulario() {    	    
    	if (gestoresFormulario.size() == 0) {
			refrescarListaGestoresFormulario();		
    	}		
    }
    
    
    /**
     * Filtra.
     * 
     */
    public final void onClick$btnBuscarGestoresFormulario() {    	
    	filtroActualGestoresFormulario = filtroGestorFormulario.getValue();
    	refrescarListaGestoresFormulario();			    			
    }

    /** Refresca lista. */
	private void refrescarListaGestoresFormulario() {
		try {
			gestoresFormulario = es.caib.sistra.persistence.delegate.DelegateUtil.getGestorFormularioDelegate().listar(filtroActualGestoresFormulario);
			listGestoresFormulario.selectItem(null);
			listGestoresFormulario.setModel(new BindingListModelList(gestoresFormulario, true));			
		} catch (es.caib.sistra.persistence.delegate.DelegateException e) {					
			ConsolaUtil.generaDelegateException(e);
		}
	}

    /**
     * Doble click item.
     * 
     */
    public final void onDoubleClick$itemGestorFormulario() {
    	onClick$btnEditaGestorFormulario();
    }
    
    /**
     * Click boton editar.
     */
    public final void onClick$btnEditaGestorFormulario() {
    	final Object valueSelected = obtenerItemSeleccionado(listGestoresFormulario);
        if (valueSelected != null) {
        	GestorFormulario data = (GestorFormulario) ((Listitem) listGestoresFormulario.getSelectedItem()
                    .clone()).getValue();
            final Map<String, Object> map = new HashMap<String, Object>();
            map.put(ConstantesWEB.PARAM_MODO_ACCESO, TypeModoAcceso.EDICION);            
			map.put(ConstantesWEB.PARAM_OBJETO_EDICION, data);            
            final Window ventana = (Window) creaComponente(
                    "/configuracion/windows/con-gestorFormulario-win.zul", this.self, map);
            abreVentanaModal(ventana);
        } 
    }
    
    /**
     * Click boton nuevo.
     */
    public final void onClick$btnNuevoGestorFormulario() {
    	final Map<String, Object> map = new HashMap<String, Object>();   
    	map.put(ConstantesWEB.PARAM_MODO_ACCESO, TypeModoAcceso.ALTA);   
        final Window ventana = (Window) creaComponente(
                  "/configuracion/windows/con-gestorFormulario-win.zul", this.self, map);
        abreVentanaModal(ventana);       
    }
    
    /**
     * Eliminar.
     */
    public void onClick$btnQuitaGestorFormulario() {
        final Object valueSelected = obtenerItemSeleccionado(listGestoresFormulario);
        if (valueSelected != null) {
            confirmarBorrado(valueSelected);
        }
    }
    
    
    /**
     * Nuevo.
     */
    public void onClick$PopupNuevoGestorFormulario() {
    	onClick$btnNuevoGestorFormulario();
    }
    
    /**
     * Editar.
     */
    public void onClick$PopupEditaGestorFormulario() {
    	onClick$btnEditaGestorFormulario();
    }
    
    /**
     * Eliminar.
     */
    public void onClick$PopupQuitaGestorFormulario() {
    	onClick$btnQuitaGestorFormulario();
    }
    
    /**
     * Evento post modificacion.
     * @param data Cuenta
     */
    private void postModificacionGestorFormulario(GestorFormulario os) {		
		// Actualiza datos modelo
    	int index = 0;
		for (final GestorFormulario o : this.gestoresFormulario) {
		    if (o.getIdentificador().equals(os.getIdentificador())) {
		    	gestoresFormulario.set(index, os);
		        break;
		    }
		    index++;
		}
		listGestoresFormulario.setModel(new BindingListModelList(gestoresFormulario, true));
		
	}
    
    /**
     * Evento post alta.
     * @param data Cuenta
     */
    private void postAltaGestorFormulario(
    		GestorFormulario data) {
		
    	// Obtenemos pagina actual
        final int paginaActual = listGestoresFormulario.getActivePage();
        
        // Refrescamos datos (teniendo en cuentra filtro busqueda)
        refrescarListaGestoresFormulario();
        
        // Vamos a pagina anterior
        if (listGestoresFormulario.getPageCount() < paginaActual) {
        	listGestoresFormulario.setActivePage(paginaActual);
        }
		
	}
    

    /**
     * Post confirmar borrado.
     * @param obj
     */
	private void postConfirmarBorradoGestorFormulario(Object obj) {       
		try {
			
            final GestorFormulario os = (GestorFormulario) obj;
            
            // Verifica si se puede borrar
            // TODO PENDIENTE VERIFICACION
            mostrarMessageBox("PENDIENTE VERIFICAR SI SE PUEDE BORRAR",
                    Labels.getLabel(ConstantesWEB.WARNING), Messagebox.OK,
                    Messagebox.EXCLAMATION);
            /*
            if (DelegateUtil.getTramiteDelegate().listarTramitesOrganoResponsable(os.getCodigo()).size() > 0
            		|| DelegateUtil.getDominioDelegate().listarDominios(os.getCodigo()).size() > 0) {
            	ConsolaUtil.generaNoPermitidoBorrarException();
            }
            */
            
            // Borramos
            es.caib.sistra.persistence.delegate.DelegateUtil.getGestorFormularioDelegate().borrarFormulario(os.getIdentificador());
    		                     
            // Actualizamos modelo
            for (final GestorFormulario o : gestoresFormulario) {
                if (o.getIdentificador().equals(os.getIdentificador())) {
                	gestoresFormulario.remove(o);
                    break;
                }
            }
            listGestoresFormulario.setModel(new BindingListModelList(gestoresFormulario, true));
		} catch (es.caib.sistra.persistence.delegate.DelegateException e) {
			ConsolaUtil.generaDelegateException(e);
		}   
    }
    
    // TAB ERRORES
    
   /**
     * Método para On click$tab error.
     * 
     */
    public final void onClick$tabErrores() {
    	// TODO NO SE FUERZA A RECUPERAR, SOLO LA 1º VEZ...    
    	if (errores.size() == 0) {
			
    		// TODO IMPLEMENTAR
    		AlertaPlataforma e;
    		
    		e = new AlertaPlataforma();
			e.setDescripcion("Errores críticos");
			errores.add(e);						
			
			e = new AlertaPlataforma();
			e.setDescripcion("Errores alto - medio");		
			errores.add(e);
    		
			listErrores.setModel(new BindingListModelList(errores, true));						
    	}		
    }

    /**
     * Doble click item error.
     * 
     */
    public final void onDoubleClick$itemError() {
    	onClick$btnEditaError();
    }
    
    /**
     * Click boton editar error.
     */
    public final void onClick$btnEditaError() {
    	final Map<String, Object> map = new HashMap<String, Object>();
        if (listErrores.getSelectedIndex() == ConstantesZK.SINSELECCION) {
            mostrarMessageBox(Labels.getLabel("alertaPlataforma.noSeleccionado"),
                    Labels.getLabel(ConstantesWEB.WARNING), Messagebox.OK,
                    Messagebox.EXCLAMATION);
        } else {
        	// TODO VER TEMA PERMISOS
        	AlertaPlataforma error = (AlertaPlataforma) ((Listitem) listErrores.getSelectedItem()
                    .clone()).getValue();
            map.put(ConstantesWEB.PARAM_MODO_ACCESO, TypeModoAcceso.EDICION);            
			map.put(ConstantesWEB.ERROR_PLATAFORMA, error);            
            final Window ventana = (Window) creaComponente(
                    "/configuracion/windows/con-alertaPlataforma-win.zul", this.self, map);
            abreVentanaModal(ventana);
        }
    }
    
    
    // TAB MANTENIMIENTO REDOSE
    
    /**
     * After compose tab MantenimientoRedose.
     */
	private void afterComposeTabMantenimientoRedose() {		
	}
    
	/**
     * Método para On click$tab.
     * 
     */
    public final void onClick$tabMantenimientoRedose() {    	    
    	if (formateadores.size() == 0) {
			refrescarListaFormateadores();		
    	}
    	if (ubicaciones.size() == 0) {
			refrescarListaUbicaciones();		
    	}
    }   

    /** Refresca lista. */
	private void refrescarListaFormateadores() {
		try {
			formateadores = es.caib.redose.persistence.delegate.DelegateUtil.getFormateadorDelegate().listar();
			listFormateadores.selectItem(null);
			listFormateadores.setModel(new BindingListModelList(formateadores, true));			
		} catch (es.caib.redose.persistence.delegate.DelegateException e) {					
			ConsolaUtil.generaDelegateException(e);
		}
	}

    /**
     * Doble click item.
     * 
     */
    public final void onDoubleClick$itemFormateador() {
    	onClick$btnEditaFormateador();
    }
    
    /**
     * Click boton editar.
     */
    public final void onClick$btnEditaFormateador() {
    	final Object valueSelected = obtenerItemSeleccionado(listFormateadores);
        if (valueSelected != null) {
        	Formateador data = (Formateador) ((Listitem) listFormateadores.getSelectedItem()
                    .clone()).getValue();
            final Map<String, Object> map = new HashMap<String, Object>();
            map.put(ConstantesWEB.PARAM_MODO_ACCESO, TypeModoAcceso.EDICION);            
			map.put(ConstantesWEB.PARAM_OBJETO_EDICION, data);            
            final Window ventana = (Window) creaComponente(
                    "/configuracion/windows/con-formateadorRedose-win.zul", this.self, map);
            abreVentanaModal(ventana);
        } 
    }
    
    /**
     * Click boton nuevo.
     */
    public final void onClick$btnNuevoFormateador() {
    	final Map<String, Object> map = new HashMap<String, Object>();   
    	map.put(ConstantesWEB.PARAM_MODO_ACCESO, TypeModoAcceso.ALTA);   
        final Window ventana = (Window) creaComponente(
                  "/configuracion/windows/con-formateadorRedose-win.zul", this.self, map);
        abreVentanaModal(ventana);       
    }
    
    /**
     * Eliminar.
     */
    public void onClick$btnQuitaFormateador() {
        final Object valueSelected = obtenerItemSeleccionado(listFormateadores);
        if (valueSelected != null) {
            confirmarBorrado(valueSelected);
        }
    }
    
    
    /**
     * Nuevo.
     */
    public void onClick$PopupNuevoFormateador() {
    	onClick$btnNuevoFormateador();
    }
    
    /**
     * Editar.
     */
    public void onClick$PopupEditaFormateador() {
    	onClick$btnEditaFormateador();
    }
    
    /**
     * Eliminar.
     */
    public void onClick$PopupQuitaFormateador() {
    	onClick$btnQuitaFormateador();
    }
    
    /**
     * Evento post modificacion.
     * @param data Cuenta
     */
    private void postModificacionFormateador(Formateador os) {		
		// Actualiza datos modelo	
    	int index = 0;
		for (final Formateador o : this.formateadores) {
		    if (o.getIdentificador().equals(os.getIdentificador())) {
		    	formateadores.set(index, os);
		        break;
		    }
		    index++;
		}
		listFormateadores.setModel(new BindingListModelList(formateadores, true));
		
	}
    
    /**
     * Evento post alta.
     * @param data Cuenta
     */
    private void postAltaFormateador(
    		Formateador data) {		
        // Refrescamos datos (teniendo en cuentra filtro busqueda)
        refrescarListaFormateadores();       
	}
    

    /**
     * Post confirmar borrado.
     * @param obj
     */
	private void postConfirmarBorradoFormateador(Object obj) {       
		try {
			
            final Formateador os = (Formateador) obj;
            
            // Verifica si se puede borrar
            // TODO PENDIENTE VERIFICACION
            mostrarMessageBox("PENDIENTE VERIFICAR SI SE PUEDE BORRAR",
                    Labels.getLabel(ConstantesWEB.WARNING), Messagebox.OK,
                    Messagebox.EXCLAMATION);
            /*
            if (DelegateUtil.getTramiteDelegate().listarTramitesOrganoResponsable(os.getCodigo()).size() > 0
            		|| DelegateUtil.getDominioDelegate().listarDominios(os.getCodigo()).size() > 0) {
            	ConsolaUtil.generaNoPermitidoBorrarException();
            }
            */
            
            // Borramos
            es.caib.redose.persistence.delegate.DelegateUtil.getFormateadorDelegate().borrarFormateador(os.getIdentificador());
    		                     
            // Actualizamos modelo
            for (final Formateador o : formateadores) {
                if (o.getIdentificador().equals(os.getIdentificador())) {
                	formateadores.remove(o);
                    break;
                }
            }
            listFormateadores.setModel(new BindingListModelList(formateadores, true));
		} catch (es.caib.redose.persistence.delegate.DelegateException e) {
			ConsolaUtil.generaDelegateException(e);
		}   
    }
   
	
    /** Refresca lista. */
	private void refrescarListaUbicaciones() {
		try {
			ubicaciones = es.caib.redose.persistence.delegate.DelegateUtil.getUbicacionDelegate().listarUbicaciones();
			listUbicaciones.selectItem(null);
			listUbicaciones.setModel(new BindingListModelList(ubicaciones, true));			
		} catch (es.caib.redose.persistence.delegate.DelegateException e) {					
			ConsolaUtil.generaDelegateException(e);
		}
	}

    /**
     * Doble click item.
     * 
     */
    public final void onDoubleClick$itemUbicacion() {
    	onClick$btnEditaUbicacion();
    }
    
    /**
     * Click boton editar.
     */
    public final void onClick$btnEditaUbicacion() {
    	final Object valueSelected = obtenerItemSeleccionado(listUbicaciones);
        if (valueSelected != null) {
        	Ubicacion data = (Ubicacion) ((Listitem) listUbicaciones.getSelectedItem()
                    .clone()).getValue();
            final Map<String, Object> map = new HashMap<String, Object>();
            map.put(ConstantesWEB.PARAM_MODO_ACCESO, TypeModoAcceso.EDICION);            
			map.put(ConstantesWEB.PARAM_OBJETO_EDICION, data);            
            final Window ventana = (Window) creaComponente(
                    "/configuracion/windows/con-ubicacion-win.zul", this.self, map);
            abreVentanaModal(ventana);
        } 
    }
    
    /**
     * Click boton nuevo.
     */
    public final void onClick$btnNuevoUbicacion() {
    	final Map<String, Object> map = new HashMap<String, Object>();   
    	map.put(ConstantesWEB.PARAM_MODO_ACCESO, TypeModoAcceso.ALTA);   
        final Window ventana = (Window) creaComponente(
                  "/configuracion/windows/con-ubicacion-win.zul", this.self, map);
        abreVentanaModal(ventana);       
    }
    
    /**
     * Eliminar.
     */
    public void onClick$btnQuitaUbicacion() {
        final Object valueSelected = obtenerItemSeleccionado(listUbicaciones);
        if (valueSelected != null) {
            confirmarBorrado(valueSelected);
        }
    }
    
    
    /**
     * Nuevo.
     */
    public void onClick$PopupNuevoUbicacion() {
    	onClick$btnNuevoUbicacion();
    }
    
    /**
     * Editar.
     */
    public void onClick$PopupEditaUbicacion() {
    	onClick$btnEditaUbicacion();
    }
    
    /**
     * Eliminar.
     */
    public void onClick$PopupQuitaUbicacion() {
    	onClick$btnQuitaUbicacion();
    }
    
    /**
     * Evento post modificacion.
     * @param data Cuenta
     */
    private void postModificacionUbicacion(Ubicacion os) {		
		// Actualiza datos modelo
    	int index = 0;
		for (final Ubicacion o : this.ubicaciones) {
		    if (o.getCodigo().equals(os.getCodigo())) {
		    	ubicaciones.set(index, os);
		        break;
		    }
		    index++;
		}
		listUbicaciones.setModel(new BindingListModelList(ubicaciones, true));
		
	}
    
    /**
     * Evento post alta.
     * @param data Cuenta
     */
    private void postAltaUbicacion(
    		Ubicacion data) {		
        // Refrescamos datos (teniendo en cuentra filtro busqueda)
        refrescarListaUbicaciones();       
	}
    

    /**
     * Post confirmar borrado.
     * @param obj
     */
	private void postConfirmarBorradoUbicacion(Object obj) {       
		try {
			
            final Ubicacion os = (Ubicacion) obj;
            
            // Verifica si se puede borrar
            // TODO PENDIENTE VERIFICACION
            mostrarMessageBox("PENDIENTE VERIFICAR SI SE PUEDE BORRAR",
                    Labels.getLabel(ConstantesWEB.WARNING), Messagebox.OK,
                    Messagebox.EXCLAMATION);
            /*
            if (DelegateUtil.getTramiteDelegate().listarTramitesOrganoResponsable(os.getCodigo()).size() > 0
            		|| DelegateUtil.getDominioDelegate().listarDominios(os.getCodigo()).size() > 0) {
            	ConsolaUtil.generaNoPermitidoBorrarException();
            }
            */
            
            // Borramos
            es.caib.redose.persistence.delegate.DelegateUtil.getUbicacionDelegate().borrarUbicacion(os.getCodigo());
    		                     
            // Actualizamos modelo
            for (final Ubicacion o : ubicaciones) {
                if (o.getCodigo().equals(os.getCodigo())) {
                	ubicaciones.remove(o);
                    break;
                }
            }
            listUbicaciones.setModel(new BindingListModelList(ubicaciones, true));
		} catch (es.caib.redose.persistence.delegate.DelegateException e) {
			ConsolaUtil.generaDelegateException(e);
		}   
    }
   
    
    
}
