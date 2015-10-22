package es.caib.consola.controller.gestor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zul.Button;
import org.zkoss.zul.Include;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Window;

import es.caib.bantel.model.Procedimiento;
import es.caib.consola.ConstantesWEB;
import es.caib.consola.comparator.TraduccionComparator;
import es.caib.consola.controller.gestor.renderer.TramiteListitemRenderer;
import es.caib.consola.model.FormularioReusable;
import es.caib.consola.model.Usuario;
import es.caib.consola.model.types.TypeModoAcceso;
import es.caib.consola.util.ConsolaUtil;
import es.caib.consola.util.GTTUtilsWeb;
import es.caib.redose.model.Modelo;
import es.caib.sistra.model.Dominio;
import es.caib.sistra.model.OrganoResponsable;
import es.caib.sistra.model.Tramite;
import es.caib.sistra.persistence.delegate.DelegateException;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.zkutils.ConstantesZK;
import es.caib.zkutils.zk.composer.BaseComposer;


/**
 * Controlador visual de la configuración de un organismo.
 * 
 * @author Indra
 * 
 */

@SuppressWarnings("serial")
public class OrganismoController extends BaseComposer {
	
	// ATRIBUTOS VENTANA
    /** Referencia a window. */
    private Window orgWindow;
    /** Referencia a contenedor principal. */
    private Include contenedorPrincipal;
    /** Atributo alto de OrganismoController. */
    private int alto;
    /** Atributo usuario logado de OrganismoController. */
    private Usuario usuarioLogado;
    /** Atributo organismo de OrganismoController. */
    private Tabbox organismo;
    /** Atributo sin organismo de OrganismoController. */
    private Vbox sinOrganismo;
    /** Modo de edicion: consulta / edicion. */
    private TypeModoAcceso modo;

    // TAB TRAMITES
    /** Atributo model tramites de OrganismoController. */
    private BindingListModelList modelTramites;
    /** Atributo filtro tramite. */
    private Textbox filtroTramite;
    /** Atributo list tramites. */
    private Listbox listTramites;
    /** Atributo descripcion tramite lh de OrganismoController. */
    private Listheader descripcionTramiteLH;
    /** Atributo btn nuevo tramite. */
    private Button btnNuevoTramite;
    /** Atributo btn edita tramite. */
    private Button btnEditaTramite;
    /** Atributo btn quita tramite. */
    private Button btnQuitaTramite;
    /** Atributo popup nuevo tramite. */
    private Menuitem popupNuevoTramite;
    /** Atributo popup edita tramite. */
    private Menuitem popupEditaTramite;
    /** Atributo popup quita tramite. */
    private Menuitem popupQuitaTramite;
    /** Atributo tramites. */
    private List<Tramite> tramites = new ArrayList<Tramite>();

    // TAB DOMINIOS
    /** Filtro actual. */
    private String filtroActualDominio = "";
    /** Textbos filtro organismo. */
    private Textbox filtroDominio;
    /** Atributo list dominios. */
    private Listbox listDominios;   
    /** Atributo btn nuevo dominio. */
    private Button btnNuevoDominio;
    /** Atributo btn edita dominio. */
    private Button btnEditaDominio;
    /** Atributo btn quita dominio. */
    private Button btnQuitaDominio;
    /** Atributo popup nuevo dominio. */
    private Menuitem popupNuevoDominio;
    /** Atributo popup edita dominio. */
    private Menuitem popupEditaDominio;
    /** Atributo popup quita dominio. */
    private Menuitem popupQuitaDominio;
    /** Atributo dominios. */
    private List<Dominio> dominios = new ArrayList<Dominio>();
    
    
    // TAB DOCUMENTOS
    /** Filtro actual. */
    private String filtroActualDocumento = "";
    /** Atributo list documentos. */
    private Listbox listDocumentos;
    /** Atributo filtro documento. */
    private Textbox filtroDocumento;
    /** Atributo btn nuevo documento. */
    private Button btnNuevoDocumento;
    /** Atributo btn edita documento. */
    private Button btnEditaDocumento;
    /** Atributo btn quita documento. */
    private Button btnQuitaDocumento;
    /** Atributo popup nuevo documento. */
    private Menuitem popupNuevoDocumento;
    /** Atributo popup edita documento. */
    private Menuitem popupEditaDocumento;
    /** Atributo popup quita documento. */
    private Menuitem popupQuitaDocumento;
    /** Atributo documentos. */
    private List<Modelo> documentos = new ArrayList<Modelo>();
    
    // TAB PROCEDIMIENTO
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
    /** Filtro actual. */
    private String filtroActualProcedimiento = "";
    
    // TAB FORMULARIOS
    /** Atributo list formularios. */
    private Listbox listFormularios;
    /** Atributo filtro formulario. */
    private Textbox filtroFormulario;
    /** Atributo btn nuevo formulario. */
    private Button btnNuevoFormulario;
    /** Atributo btn edita formulario. */
    private Button btnEditaFormulario;
    /** Atributo btn quita formulario. */
    private Button btnQuitaFormulario;
    /** Atributo popup nuevo formulario. */
    private Menuitem popupNuevoFormulario;
    /** Atributo popup edita formulario. */
    private Menuitem popupEditaFormulario;
    /** Atributo popup quita formulario. */
    private Menuitem popupQuitaFormulario;
    /** Atributo formularios. */
    private List<FormularioReusable> formularios = new ArrayList<FormularioReusable>();
   
    /*
     * (non-Javadoc)
     * 
     * @see
     * es.gva.dgm.tra.util.zk.composer.BaseComposer#doAfterCompose(org.zkoss
     * .zk.ui.Component)
     */
    public final void doAfterCompose(final Component compOrganismo) {
        
    	super.doAfterCompose(compOrganismo);
        
    	// Recupera usuario autenticado
    	usuarioLogado = ConsolaUtil.recuperarUsuarioLogado();
    	modo = usuarioLogado.getModoAcceso(); 
    		
    	// Calculamos alto
        calculaAlto();
        
        // Si usuario no tiene organismo por defecto, mostramos mensaje sin organismo
        sinOrganismo.setVisible(false);
        if (usuarioLogado.getOrganismo() == null) {
            // USUARIO sin Organismo
            organismo.setVisible(false);
            sinOrganismo.setVisible(true);
            sinOrganismo.setHeight(alto + "px");
            return;
        } 
        
        // Preparamos tabs	
        afterComposeTabTramites();
        afterComposeTabDominio();
        afterComposeTabDocumentos();
        afterComposeTabProcedimientos();
        
        // Visualizamos componentes
        visualizaComponentes();
       
        // Eventos
        manejadoresEventos();
    }
    

    
    /**
     * Método para Visualiza componentes de la clase OrganismoController.
     */
    private final void visualizaComponentes() {
        actualizaAltoListBox();
        organismo.setHeight(alto + "px");
        // visualizarOperacionesTramite();
        // visualizarOperacionesDominio();        
    }
   
    /*
    private void visualizarOperacionesTramite() {
    	// TODO VER TEMA DE PERMISOS
        boolean permitirOperacion = true;
        btnNuevoTramite.setVisible(permitirOperacion);
        btnEditaTramite.setLabel(Labels.getLabel(ConstantesWEB.EDITAR));
        popupNuevoTramite.setVisible(permitirOperacion);
        popupEditaTramite.setLabel(Labels.getLabel(ConstantesWEB.EDITAR));
        btnQuitaTramite.setVisible(permitirOperacion);
        popupQuitaTramite.setVisible(permitirOperacion);    
    }

    void visualizarOperacionesDominio() {
		// TODO PENDIENTE PERMISOS
    	boolean permitirOperacion = true;
        btnNuevoDominio.setVisible(permitirOperacion);
        popupNuevoDominio.setVisible(permitirOperacion);
        btnEditaDominio.setLabel(Labels.getLabel(ConstantesWEB.EDITAR));
        popupEditaDominio.setLabel(Labels.getLabel(ConstantesWEB.EDITAR));
        btnQuitaDominio.setVisible(permitirOperacion);
        popupQuitaDominio.setVisible(permitirOperacion);                 
	}
	*/

	/**
     * Método para Actualiza alto list box de la clase OrganismoController.
     */
    private void actualizaAltoListBox() {
        int filasListbox = (alto - ConstantesWEB.TABS_HEIGHT - ConstantesWEB.PAGINACION_HEIGHT)
                / ConstantesWEB.FILA_HEIGHT;
        int filasListboxCheck = (alto - ConstantesWEB.TABS_HEIGHT - ConstantesWEB.PAGINACION_HEIGHT)
                / ConstantesWEB.FILA_CHECK_HEIGHT;
        if (filasListbox <= 0) {
            filasListbox = 1;
        }
        if (filasListboxCheck <= 0) {
            filasListboxCheck = 1;
        }
        listTramites.setPageSize(filasListboxCheck);
        listDominios.setPageSize(filasListbox);        
    }

    /**
     * Método para Calcula alto de la clase OrganismoController.
     */
    private void calculaAlto() {
        if (session.getAttribute("DesktopHeight") != null) {
            alto = Integer.parseInt(session.getAttribute("DesktopHeight")
                    .toString());
        } else {
            alto = 805;
        }
        alto -= ConstantesWEB.HEADER_HEIGHT;
    }
    
    /**
     * Eventos.
     */
    private void manejadoresEventos() {
    	 // - evento refresco datos tras modificacion
    	orgWindow.addEventListener(
    			ConstantesZK.EVENTO_POST_MODIFICACION, new EventListener() {
					public void onEvent(final Event pEvent) {
						if (pEvent.getData() instanceof Procedimiento) {
							postModificacionProcedimiento( (Procedimiento) pEvent.getData());
						} 
						if (pEvent.getData() instanceof Dominio) {
							postModificacionDominio( (Dominio) pEvent.getData());
						}
						if (pEvent.getData() instanceof Modelo) {
							postModificacionModelo( (Modelo) pEvent.getData());
						}
                    }
                });
        // - evento refresco datos tras alta
    	orgWindow.addEventListener(ConstantesZK.EVENTO_POST_ALTA,
                new EventListener() {
                    public void onEvent(final Event pEvent) {                               	
                    	if (pEvent.getData() instanceof Dominio) {
							postAltaDominio( (Dominio) pEvent.getData());
						} 
                    	if (pEvent.getData() instanceof Modelo) {
							postAltaModelo( (Modelo) pEvent.getData());
						} 
                    }
                });
		
	}
    
    @Override
    protected void postConfirmarBorrado(final Object obj) {    	    	
    	if (obj instanceof Dominio) {
    		postConfirmarBorradoDominio(obj);
    	}
    	if (obj instanceof Modelo) {
    		postConfirmarBorradoModelo(obj);
    	}
    }        

    // TAB TRAMITES
    

	/**
	 *  Prepara tab de tramties after compose.
	 */
	private void afterComposeTabTramites() {
		// Recupera lista tramites organismo
		try {
			Set tramsLista;
			tramsLista = DelegateUtil.getTramiteDelegate().listarTramitesOrganoResponsable(usuarioLogado.getOrganismo());
			tramites = new ArrayList<Tramite>(tramsLista);
		} catch (DelegateException e) {
			ConsolaUtil.generaDelegateException(e);
		}
		
		// Renderizamos tabla
		listTramites.setItemRenderer(new TramiteListitemRenderer(
		        usuarioLogado.getIdioma()));
		modelTramites = new BindingListModelList(tramites, true);
		listTramites.setModel(modelTramites);
		descripcionTramiteLH.setSortAscending(new TraduccionComparator(
		        "descripcion", true, usuarioLogado));
		descripcionTramiteLH.setSortDescending(new TraduccionComparator(
		        "descripcion", false, usuarioLogado));
	}
	
    /**
     * Método para On click$btn ayuda organismo tramites de la clase
     * OrganismoController.
     * 
     */
    public final void onClick$btnAyudaOrganismoTramites() {
        GTTUtilsWeb.onClickBotonAyuda("gestor/tabOrganismoTramites");
    }
    
    /**
     * Boton buscar tramites.
     * 
     */
    public final void onClick$btnBuscarTramites() {
    	
    	// TODO PENDIENTE (PASAR A LISTENER??)
    	mostrarMessageBox("NO IMPLEMENTADO",
                Labels.getLabel(ConstantesWEB.WARNING), Messagebox.OK,
                Messagebox.EXCLAMATION);
    	
    	/*
        tramites.clear();
        if (!StringUtils.isEmpty(filtroTramite.getValue())) {
            tramites.addAll(servicioTramites.getTramites(
                    filtroTramite.getValue(), tramiteActivo.isChecked()));
            listTramites.setModel(modelTramites);
        } else {
            tramites.addAll(servicioTramites.getTramites(null,
                    tramiteActivo.isChecked()));
            listTramites.setModel(modelTramites);
        }
        */
    }

    /**
     * Boton nuevo tramite: abre ventana nuevo tramite.
     * 
     */
    public final void onClick$btnNuevoTramite() {
    	if (modo == TypeModoAcceso.EDICION) {
	    	final Map<String, Object> map = new HashMap<String, Object>();
	        map.put(ConstantesWEB.PARAM_MODO_ACCESO, TypeModoAcceso.ALTA);
	        final Window ventana = (Window) creaComponente(
	                "/gestor/windows/ges-organismos-tramite-win.zul", this.self,
	                map);
	        abreVentanaModal(ventana);
    	}
    }

    /**
     * Boton editar tramite: abre ventana edicion tramite.
     * 
     */
    public final void onClick$btnEditaTramite() {
    	if (modo == TypeModoAcceso.EDICION) {
	        if (listTramites.getSelectedIndex() == ConstantesZK.SINSELECCION) {
	            mostrarMessageBox(Labels.getLabel("tramites.noSeleccionado"),
	                    Labels.getLabel(ConstantesWEB.WARNING), Messagebox.OK,
	                    Messagebox.EXCLAMATION);
	        } else {
	        	// Metemos en sesion el codigo tramite que se esta editando
	        	final Listitem item = (Listitem) listTramites.getSelectedItem()
	                    .clone();
	            final Tramite tramite = (Tramite) item.getValue();
	            session.setAttribute(ConstantesWEB.TRAMITE, tramite.getCodigo());
	            session.setAttribute(ConstantesWEB.TRAMITE_ID, tramite.getIdentificador());
	            // Mostramos pantalla tramite
	            habilitaInclude(contenedorPrincipal, "/gestor/ges-tramite.zul",
	                    null);
	        }
    	}
    }

    /**
     * Boton mover tramite.
     */
    public final void onClick$btnMoverTramite() {
        final Window ventana = (Window) creaComponente(
                "/gestor/windows/ges-entorno-win.zul", this.self, null);
        abreVentanaModal(ventana);
    }
    
    
    /**
     * Boton borrar tramite: elimina tramite.
     */
    public final void onClick$btnQuitaTramite() {
    	if (modo == TypeModoAcceso.EDICION) {
    		
    		// TODO PENDIENTE (PASAR A LISTENER??)
    		mostrarMessageBox("NO IMPLEMENTADO",
                    Labels.getLabel(ConstantesWEB.WARNING), Messagebox.OK,
                    Messagebox.EXCLAMATION);
    		
    		/*
	        if (listTramites.getSelectedIndex() == ConstantesWEB.SINSELECCION) {
	            mostrarMessageBox(Labels.getLabel("tramites.noSeleccionado"),
	                    Labels.getLabel(ConstantesWEB.WARNING), Messagebox.OK,
	                    Messagebox.EXCLAMATION);
	        } else {
	            final Component comp = this.self;
	            mostrarMessageBox(Labels.getLabel("tramites.estaSeguro"),
	                    Labels.getLabel(ConstantesWEB.WARNING), Messagebox.YES
	                            + Messagebox.NO, Messagebox.EXCLAMATION,
	                    new EventListener() {
	                        public void onEvent(final Event event) {
	                            if (((Integer) event.getData()).intValue() == Messagebox.YES) {
	                                final Listitem selectedItem = (Listitem) listTramites
	                                        .getSelectedItem();
	                                final Tramite r = (Tramite) selectedItem
	                                        .getValue();
	
	                                try {
	                                    final List<InfoEliminarEntidad> mensajesEliminarTramite = servicioTramites
	                                            .delete(r);
	
	                                    if (mensajesEliminarTramite.size() > 0) {
	                                        final Map<String, Object> map = new HashMap<String, Object>();
	                                        map.put(ConstantesWEB.MENSAJESELIMINARORGANISMO,
	                                                mensajesEliminarTramite);
	
	                                        final Window ventana = (Window) creaComponente(
	                                                ConstantesWEB.ZUL_INFO_ELIMINAR,
	                                                comp, map);
	                                        abreVentanaModal(ventana);
	                                    } else {
	                                        for (final Iterator<Tramite> it = tramites
	                                                .iterator(); it.hasNext();) {
	                                            final Tramite next = it.next();
	                                            if (next.getCodigo() == r
	                                                    .getCodigo()) {
	                                                it.remove();
	                                                break;
	                                            }
	                                        }
	                                        listTramites.setModel(modelTramites);
	                                    }
	
	                                } catch (final PermisosSeguridadException e) {
	                                    mostrarErroresPermisos();
	
	                                }
	
	                            }
	                        }
	                    });
	        }
	        */
    	}
    }

    /**
     * Boton editar tramite.
     * 
     */
    public final void onDoubleClick$itemTramite() {
        onClick$btnEditaTramite();
    }

    /**
     * Boton popup nuevo tramite.
     * 
     */
    public final void onClick$popupNuevoTramite() {
        onClick$btnNuevoTramite();
    }

    /**
     * Boton popup editar tramite.
     * 
     */
    public final void onClick$popupEditaTramite() {
        onClick$btnEditaTramite();
    }

    /**
     * Boton popup borrar tramite.
     * 
     */
    public final void onClick$popupQuitaTramite() {
        onClick$btnQuitaTramite();
    }
    
    
    
    // TAB DOMINIOS
    
    /**
     *  Prepara tab de dominios after compose.
     */
    private void afterComposeTabDominio() {    	
	}
        

    /**
     * Método para On click$tab dominio de la clase OrganismoController.
     * 
     */
    public final void onClick$tabDominio() {
    	if (dominios.size() == 0) {
    		refrescarListaDominios();
    	}		
    }
    
    /**
     * Click ayuda dominios.
     * 
     */
    public final void onClick$btnAyudaOrganismoDominios() {
        GTTUtilsWeb.onClickBotonAyuda("gestor/tabOrganismoDominios");
    }
    
    /**
     * Click boton buscar dominios.
     * 
     */
    public final void onClick$btnBuscarDominios() {
    	filtroActualDominio = filtroDominio.getValue();
    	refrescarListaDominios();	
    }
    
    /** Refresca lista organismos. */
	private void refrescarListaDominios() {
		try {
			dominios = DelegateUtil.getDominioDelegate().listarDominios(usuarioLogado.getOrganismo(), filtroActualDominio);
			listDominios.selectItem(null);	       
			listDominios.setModel(new BindingListModelList(dominios, true));			
		} catch (DelegateException e) {					
			ConsolaUtil.generaDelegateException(e);
		}
	}

    /**
     * Click boton nuevo dominio.
     */
    public final void onClick$btnNuevoDominio() {
    	final Map<String, Object> map = new HashMap<String, Object>();
        map.put(ConstantesWEB.PARAM_MODO_ACCESO, TypeModoAcceso.ALTA);            
		map.put(ConstantesWEB.PARAM_OBJETO_PADRE, obtenerOrganismo(usuarioLogado.getOrganismo()));
        final Window ventana = (Window) creaComponente(
                "/gestor/windows/ges-dominio-win.zul", this.self, map);
        abreVentanaModal(ventana);        
    }

    /**
     * Click boton editar dominio.
     */
    public final void onClick$btnEditaDominio() {
    	final Object valueSelected = obtenerItemSeleccionado(listDominios);
        if (valueSelected != null) {
        	Dominio dominio = (Dominio) ((Listitem) listDominios.getSelectedItem()
                    .clone()).getValue();
            final Map<String, Object> map = new HashMap<String, Object>();
            map.put(ConstantesWEB.PARAM_MODO_ACCESO, TypeModoAcceso.EDICION);            
			map.put(ConstantesWEB.PARAM_OBJETO_EDICION, dominio);     
			map.put(ConstantesWEB.PARAM_OBJETO_PADRE, obtenerOrganismo(usuarioLogado.getOrganismo()));
            final Window ventana = (Window) creaComponente(
                    "/gestor/windows/ges-dominio-win.zul", this.self, map);
            abreVentanaModal(ventana);
        } 
    }

     
    /**
     * Devuelve organo.
     * @param codOrg cod
     * @return organo
     */
	private OrganoResponsable obtenerOrganismo(Long codOrg) {
		try {
			return DelegateUtil.getOrganoResponsableDelegate().obtenerOrganoResponsable(codOrg);
		} catch (DelegateException e) {
			ConsolaUtil.generaDelegateException(e);
			return null;
		}
	}

    /**
     * Click boton eliminar dominio.
     * 
     */
    public final void onClick$btnQuitaDominio() {
    	final Object valueSelected = obtenerItemSeleccionado(listDominios);
        if (valueSelected != null) {
            confirmarBorrado(valueSelected);
        }
    }

    /**
     * Doble click item dominio.
     * 
     */
    public final void onDoubleClick$itemDominio() {
        onClick$btnEditaDominio();
    }
    
    /**
     * Popup nuevo dominio.
     * 
     */
    public final void onClick$popupNuevoDominio() {
        onClick$btnNuevoDominio();
    }

    /**
     * Popup editar dominio.
     * 
     */
    public final void onClick$popupEditaDominio() {
        onClick$btnEditaDominio();
    }

    /**
     * Popup eliminar dominio.
     * 
     */
    public final void onClick$popupQuitaDominio() {
        onClick$btnQuitaDominio();
    }
    
    /**
     * Evento post modificacion.
     * @param data data
     */
    private void postModificacionDominio(Dominio os) {		
		// Actualiza datos modelo
    	int index = 0;
		for (final Dominio o : this.dominios) {
		    if (o.getCodigo().equals(os.getCodigo())) {
		    	this.dominios.set(index, os);		    	
		        break;
		    }
		    index++;
		}
		listDominios.setModel(new BindingListModelList(dominios, true));	
	}
      
    /**
     * Post confirmar borrado.
     * @param obj
     */
	private void postConfirmarBorradoDominio(Object obj) {       
		try {			
            final Dominio os = (Dominio) obj;           
            
            // Borramos
            DelegateUtil.getDominioDelegate().borrarDominio(os.getCodigo());
    		                     
            // Actualizamos modelo
            for (final Dominio o : dominios) {
                if (o.getCodigo().equals(os.getCodigo())) {
                	dominios.remove(o);
                    break;
                }
            }
            listDominios.setModel(new BindingListModelList(dominios, true));
		} catch (DelegateException e) {
			ConsolaUtil.generaDelegateException(e);
		}   
    }
	
	/**
     * Evento post alta.
     * @param data data
     */
    private void postAltaDominio(
    		Dominio data) {
		
    	// Obtenemos pagina actual
        final int paginaActual = listDominios.getActivePage();
        
        // Refrescamos datos (teniendo en cuentra filtro busqueda)
        refrescarListaDominios();
        
        // Vamos a pagina anterior
        if (listDominios.getPageCount() < paginaActual) {
        	listDominios.setActivePage(paginaActual);
        }
		
	}

  
    // TAB DOCUMENTOS
    /**
     *  Prepara tab  after compose.
     */
    private void afterComposeTabDocumentos() {    	
	}
        

    /**
     * Método para On click$tab de la clase OrganismoController.
     * 
     */
    public final void onClick$tabDocumento() {
    	if (documentos.size() == 0) {
    		refrescarListaDocumentos();
    	}		
    }
    
    /**
     * Click boton buscar.
     * 
     */
    public final void onClick$btnBuscarDocumentos() {
    	filtroActualDocumento = filtroDocumento.getValue();
    	refrescarListaDocumentos();	
    }
    
    /** Refresca lista. */
	private void refrescarListaDocumentos() {
		try {
			documentos = es.caib.redose.persistence.delegate.DelegateUtil.getModeloDelegate().listarModelos(filtroActualDocumento);
			listDocumentos.selectItem(null);	       
			listDocumentos.setModel(new BindingListModelList(documentos, true));			
		} catch (es.caib.redose.persistence.delegate.DelegateException e) {					
			ConsolaUtil.generaDelegateException(e);
		}
	}

    /**
     * Click boton nuevo.
     */
    public final void onClick$btnNuevoDocumento() {
    	final Map<String, Object> map = new HashMap<String, Object>();
        map.put(ConstantesWEB.PARAM_MODO_ACCESO, TypeModoAcceso.ALTA);            
		map.put(ConstantesWEB.PARAM_OBJETO_PADRE, obtenerOrganismo(usuarioLogado.getOrganismo()));
        final Window ventana = (Window) creaComponente(
                "/gestor/windows/ges-modeloDocumento-win.zul", this.self, map);
        abreVentanaModal(ventana);        
    }

    /**
     * Click boton editar.
     */
    public final void onClick$btnEditaDocumento() {
    	final Object valueSelected = obtenerItemSeleccionado(listDocumentos);
        if (valueSelected != null) {
        	Modelo modelo = (Modelo) ((Listitem) listDocumentos.getSelectedItem()
                    .clone()).getValue();
            final Map<String, Object> map = new HashMap<String, Object>();
            map.put(ConstantesWEB.PARAM_MODO_ACCESO, TypeModoAcceso.EDICION);            
			map.put(ConstantesWEB.PARAM_OBJETO_EDICION, modelo);     
			map.put(ConstantesWEB.PARAM_OBJETO_PADRE, obtenerOrganismo(usuarioLogado.getOrganismo()));
            final Window ventana = (Window) creaComponente(
                    "/gestor/windows/ges-modeloDocumento-win.zul", this.self, map);
            abreVentanaModal(ventana);
        } 
    }   

    /**
     * Click boton eliminar.
     * 
     */
    public final void onClick$btnQuitaDocumento() {
    	final Object valueSelected = obtenerItemSeleccionado(listDocumentos);
        if (valueSelected != null) {
            confirmarBorrado(valueSelected);
        }
    }

    /**
     * Doble click item.
     * 
     */
    public final void onDoubleClick$itemDocumento() {
        onClick$btnEditaDocumento();
    }
    
    /**
     * Popup nuevo.
     * 
     */
    public final void onClick$popupNuevoDocumento() {
        onClick$btnNuevoDocumento();
    }

    /**
     * Popup editar.
     * 
     */
    public final void onClick$popupEditaDocumento() {
        onClick$btnEditaDocumento();
    }

    /**
     * Popup eliminar.
     * 
     */
    public final void onClick$popupQuitaDocumento() {
        onClick$btnQuitaDocumento();
    }
    
    /**
     * Evento post modificacion.
     * @param data data
     */
    private void postModificacionModelo(Modelo os) {		
		// Actualiza datos modelo
    	int index = 0;
		for (final Modelo o : this.documentos) {
		    if (o.getCodigo().equals(os.getCodigo())) {
		    	this.documentos.set(index, os);		    	
		        break;
		    }
		    index++;
		}
		listDocumentos.setModel(new BindingListModelList(documentos, true));	
	}
      
    /**
     * Post confirmar borrado.
     * @param obj
     */
	private void postConfirmarBorradoModelo(Object obj) {       
		try {			
            final Modelo os = (Modelo) obj;           
            
            // Borramos
            es.caib.redose.persistence.delegate.DelegateUtil.getModeloDelegate().borrarModelo(os.getCodigo());            
    		                     
            // Actualizamos modelo
            for (final Modelo o : documentos) {
                if (o.getCodigo().equals(os.getCodigo())) {
                	documentos.remove(o);
                    break;
                }
            }
            listDocumentos.setModel(new BindingListModelList(documentos, true));
		} catch (es.caib.redose.persistence.delegate.DelegateException e) {
			ConsolaUtil.generaDelegateException(e);
		}   
    }
	
	/**
     * Evento post alta.
     * @param data data
     */
    private void postAltaModelo(
    		Modelo data) {
		
    	// Obtenemos pagina actual
        final int paginaActual = listDocumentos.getActivePage();
        
        // Refrescamos datos (teniendo en cuentra filtro busqueda)
        refrescarListaDocumentos();
        
        // Vamos a pagina anterior
        if (listDocumentos.getPageCount() < paginaActual) {
        	listDocumentos.setActivePage(paginaActual);
        }
		
	}
    
    /**
     * Click ayuda.
     * 
     */
    public final void onClick$btnAyudaOrganismoDocumentos() {
        GTTUtilsWeb.onClickBotonAyuda("gestor/tabOrganismoDocumentos");
    }
    
        
    // ------ PROCEDIMIENTOS -----------------------------------------
    
    /**
     * After compose tab procedimientos.
     */
	private void afterComposeTabProcedimientos() {		
	}     

    /**
     * Método para On click$tab procedimiento.
     * 
     */
    public final void onClick$tabProcedimiento() {
    	// NO SE FUERZA A RECUPERAR, SOLO LA 1º VEZ...    
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
			// TODO FILTRO X ORGANO
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
                    "/gestor/windows/ges-procesamientoProcedimiento-win.zul", this.self, map);
            abreVentanaModal(ventana);
        } 
    }     
    
    /**
     * Editar Procedimiento.
     */
    public void onClick$popupEditaProcedimiento() {
    	onClick$btnEditaProcedimiento();
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
		    	this.procedimientos.set(index, os);		    	
		        break;
		    }
		    index++;
		}
		listProcedimientos.setModel(new BindingListModelList(procedimientos, true));	
	}
    
    
    
    /**
     * Click ayuda.
     * 
     */
    public final void onClick$btnAyudaOrganismoProcesamiento() {
        GTTUtilsWeb.onClickBotonAyuda("gestor/tabOrganismoProcesamiento");
    }
    // --------- FORMULARIOS --------------
    /**
     * Método para On click$tab documento de la clase OrganismoController.
     * 
     */
    public final void onClick$tabFormulario() {
    	// TODO NO SE FUERZA A RECUPERAR, SOLO LA 1º VEZ...    
    	if (formularios.size() == 0) {
			// TODO FILTRAR X ORGANISMO
			List<FormularioReusable> lstFormularios = new ArrayList<FormularioReusable>();
			
			FormularioReusable f = new FormularioReusable();
			f.setIdentificador("REUSABLE1");
			f.setDescripcion("Petición de datos personales");
			lstFormularios.add(f);
			
			f = new FormularioReusable();
			f.setIdentificador("REUSABLE2");
			f.setDescripcion("Petición de conformidad");
			lstFormularios.add(f);
			
			formularios.addAll(lstFormularios);
			
		    listFormularios.setModel(new BindingListModelList(formularios, true));
					
    	}		
    }
    
    /**
     * Doble click item formulario.
     * 
     */
    public final void onDoubleClick$itemFormulario() {
    	onClick$btnEditaFormulario();
    }
    
    /**
     * Click boton editar procedimiento.
     */
    public final void onClick$btnEditaFormulario() {
    	final Map<String, Object> map = new HashMap<String, Object>();
        if (listFormularios.getSelectedIndex() == ConstantesZK.SINSELECCION) {
            mostrarMessageBox(Labels.getLabel("formularioReusable.noSeleccionado"),
                    Labels.getLabel(ConstantesWEB.WARNING), Messagebox.OK,
                    Messagebox.EXCLAMATION);
        } else {
        	// TODO VER TEMA PERMISOS
        	FormularioReusable formulario = (FormularioReusable) ((Listitem) listFormularios.getSelectedItem()
                    .clone()).getValue();
            map.put(ConstantesWEB.PARAM_MODO_ACCESO, TypeModoAcceso.EDICION);            
			map.put(ConstantesWEB.PARAMETER_FORMULARIO_REUSABLE, formulario);            
            final Window ventana = (Window) creaComponente(
                    "/gestor/windows/ges-formularioReusable-win.zul", this.self, map);
            abreVentanaModal(ventana);
        }
    }

}
