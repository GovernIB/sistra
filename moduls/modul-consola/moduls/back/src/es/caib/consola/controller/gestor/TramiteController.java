package es.caib.consola.controller.gestor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Button;
import org.zkoss.zul.Include;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import es.caib.consola.ConstantesWEB;
import es.caib.consola.controller.gestor.renderer.VersionTramiteListitemRenderer;
import es.caib.consola.model.Usuario;
import es.caib.consola.model.types.TypeModoAcceso;
import es.caib.consola.util.ConsolaUtil;
import es.caib.consola.util.GTTUtilsWeb;
import es.caib.sistra.model.TraTramite;
import es.caib.sistra.model.Tramite;
import es.caib.sistra.model.TramiteVersion;
import es.caib.sistra.persistence.delegate.DelegateException;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.zkutils.ConstantesZK;
import es.caib.zkutils.zk.composer.BaseComposer;
/**
 * Class TramiteController.
 */
@SuppressWarnings("serial")
public class TramiteController extends BaseComposer {

    /** Usuario logeado. */
    private Usuario usuarioLogado;

    /** Modo edicion. */
    private TypeModoAcceso modo;
    
    /** Tramite. */
    private Tramite tramite;

    /** Atributo tram window de TramiteController. */
    private Window tramWindow;

    /** Atributo contenedor principal de TramiteController. */
    private Include contenedorPrincipal;

	/** Listbox de versiones. */
    private Listbox listVersiones;

    /** Lista con las versiones. */
    private List<TramiteVersion> versiones;

    /** Id tramite. */
    private Textbox id;

    /** Procedimiento. */
    private Textbox procedimiento;
    
    /** Descripcion tramite. */
    private Textbox descripcion;

    /** Atributo tram de TramiteController. */
    private Include tram;

    /** Atributo alto de TramiteController. */
    private int alto;

    /** Atributo version tabbox de TramiteController. */
    private Tabbox versionTabbox;

    /** Atributo blocked by de TramiteController. */
    private Listheader blockedBy;
    
    /** Boton editar tramite. */
    private Button btnEditaTramite;
    
    /** Boton nueva version. */
    private Button btnNuevaVersion;

    /** Boton editar version. */
    private Button btnEditaVersion;

    /** Boton quitar version. */
    private Button btnQuitaVersion;

    /** Boton bloquear version. */
    private Button btnBloquear;

    /** Boton desbloquear version. */
    private Button btnDesbloquear;
    
    /** Popup nueva version. */
    private Menuitem popupNuevaVersion;

    /** Popup edita version. */
    private Menuitem popupEditaVersion;

    /** Popup quita version. */
    private Menuitem popupQuitaVersion;

    /** Popup bloquear version. */
    private Menuitem popupBloquear;

    /** Popup desbloquear version. */
    private Menuitem popupDesbloquear;

    @Override
    public final void doAfterCompose(final Component compTramite) {
        
    	super.doAfterCompose(compTramite);
        
        usuarioLogado = ConsolaUtil.recuperarUsuarioLogado();
        modo = TypeModoAcceso.EDICION;
        
        try {
	        Long codigoTramite = (Long) session.getAttribute(ConstantesWEB.TRAMITE);
	        tramite = DelegateUtil.getTramiteDelegate().obtenerTramite(codigoTramite);
	        versiones = new ArrayList<TramiteVersion> (DelegateUtil.getTramiteVersionDelegate().listarTramiteVersiones(codigoTramite));
        } catch (DelegateException e) {
			ConsolaUtil.generaDelegateException(e);
		}
        
        listVersiones.setItemRenderer(new VersionTramiteListitemRenderer(usuarioLogado));
        
        listVersiones.setModel(new ListModelList(versiones));
        
        if (tramite != null) {
            id.setValue(tramite.getIdentificador());
            descripcion.setValue( ((TraTramite) tramite.getTraduccion(usuarioLogado.getIdioma())).getDescripcion() );    
            procedimiento.setValue(tramite.getProcedimiento());
        }

        visualizaComponentes();

       // TODO eVENT LISTENER GUARDAR??
        /*
        tramWindow.addEventListener(ConstantesWEB.ACCION_GUARDAR_TRAMITE,
                new TramiteEventListener(mapEvento,
                        ConstantesWEB.ACCION_GUARDAR_TRAMITE));
        */
        
        
    }

    /**
     * Establece alto.
     */
    private void actualizaAltoListBox() {
        final int filasListbox = (alto - ConstantesWEB.TABS_HEIGHT - ConstantesWEB.PAGINACION_HEIGHT)
                / ConstantesWEB.FILA_HEIGHT;
        final int filasListboxCheck = (alto - ConstantesWEB.TABS_HEIGHT - ConstantesWEB.PAGINACION_HEIGHT)
                / ConstantesWEB.FILA_CHECK_HEIGHT;
        listVersiones.setPageSize(filasListboxCheck);
    }

    /**
     * Seleccion version.
     * 
     * @return true, si es satisfactorio
     */
    public final boolean onSelect$listVersiones() {
    	// Establece botones segun version
        boolean okBotones = false;
        if (versionSeleccionada()) {
            btnQuitaVersion.setDisabled(true);
            popupQuitaVersion.setDisabled(true);
            
            btnEditaVersion.setLabel(Labels.getLabel(ConstantesWEB.CONSULTAR));
            popupEditaVersion.setLabel(Labels.getLabel(ConstantesWEB.CONSULTAR));
            
            final Listitem item = (Listitem) listVersiones.getSelectedItemApi();
            final TramiteVersion version = (TramiteVersion) item.getValue();
            
            if ( "S".equals(version.getBloqueadoModificacion())
                    && TypeModoAcceso.EDICION.equals(modo)
                    && usuarioLogado.getUsername().equals(version.getBloqueadoModificacionPor())) {
                btnEditaVersion.setLabel(Labels.getLabel(ConstantesWEB.EDITAR));
                popupEditaVersion.setLabel(Labels.getLabel(ConstantesWEB.EDITAR));
            } 
            
            if ("N".equals(version.getBloqueadoModificacion())) {
                okBotones = true;
                btnQuitaVersion.setDisabled(false);
                popupQuitaVersion.setDisabled(false);               
            }
            
        }
        return okBotones;
    }

    /**
     * Despues renderizar versiones.
     */
    public final void onAfterRender$listVersiones() {
        listVersiones.clearSelection();
        btnEditaVersion.setLabel(Labels.getLabel(ConstantesWEB.EDITAR));
        popupEditaVersion.setLabel(Labels.getLabel(ConstantesWEB.EDITAR));
        btnQuitaVersion.setDisabled(false);
        popupQuitaVersion.setDisabled(false);                   
    }

    /**
     * Click boton nueva version.
     */
    public final void onClick$btnNuevaVersion() {
    	// TODO PENDIENTE
    	/*
        final Window ventana = (Window) creaComponente(
                "/gestor/windows/ges-tramites-nuevaVersionTramite-win.zul",
                this.self, null);
        abreVentanaModal(ventana);
        */
    }

    /**
     * Click editar version.
     */
    public final void onClick$btnEditaVersion() {
    	
    	this.mostrarInfo("No implementado", "Atención");
    	if (true) return;
    	
        if (versionSeleccionada()) {
            final Listitem item = (Listitem) listVersiones.getSelectedItemApi()
                    .clone();
            TramiteVersion version = (TramiteVersion) item.getValue();
            session.setAttribute(ConstantesWEB.VERSION, version.getCodigo());
            session.setAttribute(ConstantesWEB.VERSION_NUM, version.getVersion());
            final Map<String, Object> map = new HashMap<String, Object>();
            map.put(ConstantesWEB.PARAM_MODO_ACCESO, modo);
            habilitaInclude(contenedorPrincipal,
                    "/gestor/ges-versionTramite.zul", map);
        }
    }

    /**
     * Click borrar version.
     */
    public final void onClick$btnQuitaVersion() {
    	/*
        if (onSelect$listVersiones()) {
            mostrarMessageBox(Labels.getLabel("versiones.estaSeguro"),
                    Labels.getLabel(ConstantesWEB.WARNING), Messagebox.YES
                            + Messagebox.NO, Messagebox.EXCLAMATION,
                    new EventListener() {
                        @Override
                        public void onEvent(final Event event) {
                            if (((Integer) event.getData()).intValue() == Messagebox.YES) {
                                final Listitem selectedItem = (Listitem) listVersiones
                                        .getSelectedItemApi();
                                final Version r = (Version) selectedItem
                                        .getValue();
                                try {
                                    servicioVersiones.delete(r.getCodigo());
                                    for (final Iterator<Version> it = versiones
                                            .iterator(); it.hasNext();) {
                                        final Version next = it.next();
                                        if (next.getCodigo() == r.getCodigo()) {
                                            it.remove();
                                            break;
                                        }
                                    }
                                    listVersiones
                                            .setModel(new BindingListModelList(
                                                    versiones, true));
                                } catch (final PermisosSeguridadException e) {
                                    mostrarErrorPermisos();
                                }

                            }
                        }

                    });
        }
        */
    }

    /**
     * Click bloquear tramite.
     */
    public final void onClick$btnBloquear() {
    	/*
        if (versionSeleccionada()) {
            final Listitem item = (Listitem) listVersiones.getSelectedItemApi()
                    .clone();
            final Version version = (Version) item.getValue();

            if (!version.isBloqueada()) {
                mostrarMessageBox(
                        Labels.getLabel("versiones.bloquear.estaSeguro"),
                        Labels.getLabel(ConstantesWEB.WARNING), Messagebox.YES
                                + Messagebox.NO, Messagebox.EXCLAMATION,
                        new EventListener() {
                            @Override
                            public void onEvent(final Event event) {
                                if (((Integer) event.getData()).intValue() == Messagebox.YES) {
                                    version.setBloqueada(true);
                                    version.setIdUsuarioBloqueo(usuarioLogado
                                            .getUsername());
                                    version.setDescUsuarioBloqueo(usuarioLogado
                                            .getApellidosNombre());
                                    // actualiza botones ya que la version
                                    // seleccionada acaba de ser bloqueada
                                    // por el usuario
                                    btnQuitaVersion.setDisabled(true);
                                    popupQuitaVersion.setDisabled(true);
                                    btnDuplicar.setDisabled(true);
                                    popupDuplicar.setDisabled(true);
                                    btnEditaVersion.setLabel(Labels
                                            .getLabel(ConstantesWEB.EDITAR));
                                    popupEditaVersion.setLabel(Labels
                                            .getLabel(ConstantesWEB.EDITAR));
                                    // actualiza en BBDD
                                    try {
                                        servicioVersiones
                                                .bloquearVersion(version);
                                        for (final Iterator<Version> it = versiones
                                                .iterator(); it.hasNext();) {
                                            final Version next = it.next();
                                            if (next.getCodigo() == version
                                                    .getCodigo()) {
                                                versiones.set(
                                                        versiones.indexOf(next),
                                                        version);
                                                break;
                                            }
                                        }
                                    } catch (final PermisosSeguridadException e) {
                                        mostrarErrorPermisos();

                                    }
                                    listVersiones
                                            .setModel(new BindingListModelList(
                                                    versiones, true));

                                }
                            }
                        });
            } else {
                mostrarMessageBox(Labels.getLabel("versiones.noBloquear"),
                        Labels.getLabel(ConstantesWEB.WARNING), Messagebox.OK,
                        Messagebox.EXCLAMATION);
            }
        }
        */
    }
    
    /**
     * Click desbloquear.
     */
    public final void onClick$btnExportar() {
    	
    	this.mostrarInfo("No implementado", "Atención");
    	if (true) return;
    	
    	// TODO CONTROLAR CUANDO SE PUEDE EXPORTAR
    	if (versionSeleccionada()) {
    		final Listitem item = (Listitem) listVersiones.getSelectedItemApi()
            .clone();
    		final TramiteVersion version = (TramiteVersion) item.getValue();
	    	 final Map<String, Object> map = new HashMap<String, Object>();
	         map.put(ConstantesWEB.PARAMETER_VERSION_TRAMITE, version);
	         final Window ventana = (Window) creaComponente(
	                 "/gestor/windows/ges-exportarVersionTramite-win.zul", this.self,
	                 map);
	         abreVentanaModal(ventana);
    	}
    }

    /**
     * Click desbloquear.
     */
    public final void onClick$btnDesbloquear() {
    	if (versionSeleccionada()) {
            final Listitem item = (Listitem) listVersiones.getSelectedItemApi()
                    .clone();
            final TramiteVersion version = (TramiteVersion) item.getValue();

           // Comprobamos si esta bloqueada 
           // TODO DESBLOQUEAR TB POR ALGUN ADMIN??            
           if ("S".equals(version.getBloqueadoModificacion())) {
        	   // Si esta bloqueada por usuario logado, abrimos ventana desbloquear
        	   // TODO CONTROL DESBLOQUEO USUARIO
        	   //if (usuarioLogado.getUsername().equals(
               //        version.getBloqueadoModificacionPor())) {
        		   abreDesbloquear(version);
        	   //}        	   
           } else {
        	   mostrarMessageBox(Labels.getLabel("versiones.noDesbloquear"),
                       Labels.getLabel(ConstantesWEB.WARNING), Messagebox.OK,
                       Messagebox.EXCLAMATION);
           }
    	}            
    }

    /**
     * Abre ventana desbloquear.
     * @param version Version tramite
     */
    private void abreDesbloquear(final TramiteVersion version) {
        final Map<String, Object> map = new HashMap<String, Object>();
        map.put(ConstantesWEB.PARAMETER_VERSION_TRAMITE, version);
        final Window ventana = (Window) creaComponente(
                "/gestor/windows/ges-desbloquearVersionTramite-win.zul", this.self,
                map);
        abreVentanaModal(ventana);
    }
    
    /**
     * Indica si esta seleccionada una version.
     * 
     * @return true si esta seleccionada
     */
    private boolean versionSeleccionada() {
        Boolean ret = true;
        if (listVersiones.getSelectedIndex() == ConstantesZK.SINSELECCION) {
            mostrarMessageBox(Labels.getLabel("versiones.noSeleccionado"),
                    Labels.getLabel(ConstantesWEB.WARNING), Messagebox.OK,
                    Messagebox.EXCLAMATION);
            ret = false;
        }
        return ret;
    }

    /**
     * Doble click version.
     */
    public final void onDoubleClick$itemVersion() {
        onClick$btnEditaVersion();
    }

    /**
     * Popup nueva version.
     */
    public final void onClick$popupNuevaVersion() {
        onClick$btnNuevaVersion();
    }

    /**
     * Popup editar version.
     */
    public final void onClick$popupEditaVersion() {
        onClick$btnEditaVersion();
    }

    /**
     * Popup borrar version.
     */
    public final void onClick$popupQuitaVersion() {
        onClick$btnQuitaVersion();
    }
   
    /**
     * Popup bloquear version.
     */
    public final void onClick$popupBloquear() {
        onClick$btnBloquear();
    }

    /**
     * Popup desbloquear version.
     */
    public final void onClick$popupDesbloquear() {
        onClick$btnDesbloquear();
    }   

    /**
     * Ayuda versiones tramite.
     */
    public final void onClick$btnAyudaVersionesTramite() {
        GTTUtilsWeb.onClickBotonAyuda("versionesTramite");
    }
    
    /**
     * Boton editar propiedades tramite.
     */
    public final void onClick$btnEditaTramite() {
        final Map<String, Object> map = new HashMap<String, Object>();
        map.put(ConstantesWEB.PARAM_MODO_ACCESO, modo);
        map.put(ConstantesWEB.TRAMITE, tramite.getCodigo());
        final Window ventana = (Window) creaComponente(
                "/gestor/windows/ges-organismos-tramite-win.zul", this.self,
                map);
        abreVentanaModal(ventana);
    }

    /**
     * Ayuda propiedades tramite.
     */
    public final void onClick$btnAyudaPropiedadesTramite() {
        GTTUtilsWeb.onClickBotonAyuda("propiedadesTramite");
    }

    /**
     * Obtiene lista versiones.
     * 
     * @return el atributo versiones
     */
    public final List<TramiteVersion> getVersiones() {
        return versiones;
    }

    /**
     * Visualiza componentes.
     */
    private void visualizaComponentes() {
        alto = Integer.parseInt(session.getAttribute("DesktopHeight")
                .toString());
        alto -= ConstantesWEB.HEADER_HEIGHT;
        actualizaAltoListBox();
        versionTabbox.setHeight(alto + "px");
        visualizarOperacionesVersiones();
        visualizarOperacionesTramite();
    }

    /**
     * Visualiza operaciones sobre tramite.
     */
    private void visualizarOperacionesTramite() {
    	// TODO PERMISOS
        boolean permitirOperacion;
        permitirOperacion = true;
        btnEditaTramite.setVisible(true);
        btnEditaTramite.setLabel(Labels.getLabel(ConstantesWEB.EDITAR));
    }

    /**
     * Visualiza operaciones sobre versiones.
     */
    private void visualizarOperacionesVersiones() {
        // TODO PERMISOS
        boolean permitirOperacion = true;
        
        btnNuevaVersion.setVisible(permitirOperacion);
        popupNuevaVersion.setVisible(permitirOperacion);
        
        btnEditaVersion.setVisible(true);
        popupEditaVersion.setVisible(true);
        btnEditaVersion.setLabel(Labels.getLabel(ConstantesWEB.EDITAR));
        popupEditaVersion.setLabel(Labels.getLabel(ConstantesWEB.EDITAR));
        
        btnQuitaVersion.setVisible(permitirOperacion);
        popupQuitaVersion.setVisible(permitirOperacion);        
                
        btnBloquear.setVisible(permitirOperacion);
        popupBloquear.setVisible(permitirOperacion);
        
        btnDesbloquear.setVisible(permitirOperacion);
        popupDesbloquear.setVisible(permitirOperacion);        
    }

}
