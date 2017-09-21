package es.caib.consola.controller.configuracion.wmodal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import es.caib.bantel.model.GestorBandeja;
import es.caib.bantel.model.Procedimiento;
import es.caib.bantel.model.TraProcedimiento;
import es.caib.bantel.persistence.delegate.DelegateException;
import es.caib.bantel.persistence.delegate.DelegateUtil;
import es.caib.bantel.persistence.delegate.ProcedimientoDelegate;
import es.caib.consola.ConstantesWEB;
import es.caib.consola.model.UnidadAdministrativa;
import es.caib.consola.model.Usuario;
import es.caib.consola.model.types.TypeModoAcceso;
import es.caib.consola.util.ConsolaUtil;
import es.caib.consola.util.GTTUtilsWeb;
import es.caib.sistra.model.OrganoResponsable;
import es.caib.sistra.modelInterfaz.ConstantesDominio;
import es.caib.sistra.modelInterfaz.ValoresDominio;
import es.caib.sistra.persistence.util.UtilDominios;
import es.caib.util.StringUtil;
import es.caib.zkutils.zk.composer.BaseComposer;

/**
 * Ventana de edicion procedimiento.
 */
@SuppressWarnings("serial")
public class ProcedimientoWModal extends BaseComposer {

	/** Referencia ventana. */
    private Window wProcedimiento;
    /** Usuario logeado.*/
	private Usuario usuarioLogado;
    /** Modo edicion. */
    private TypeModoAcceso modo;
    /** Procedimiento editado.*/
    private Procedimiento procedimiento;
    	
    /** Id dominio: textbox. */
    private Textbox codigo;    
    /** Descripcion dominio. */
    private Textbox descripcion;
    
    /** Descripcion organismo. */
    private Textbox organismoDesc;
    /** Organismo. */
    private OrganoResponsable organismoProcedimiento;
    
    /** Descripcion unidad administrativa. */
    private Textbox unidadAdministrativaDesc;
    /** Codigo unidad administrativa. */
    private String unidadAdministrativaCodigo;
    /** Check aviso notif. */
    private Checkbox checkAvisoNotificaciones;
    /** Check permitir SMS. */
    private Checkbox checkPermitirSMS;
    
    /** Row gestores. */
    private Row rowGestores;
    /** Lista gestores.*/
    private Listbox listaGestores;
    /** List gestores. */
    private List gestoresList;
           
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
     	
     	// Lista procedimientos
     	listaGestores.setItemRenderer(new ListitemRenderer() {
            public void render(final Listitem item, final Object data, final int index) {
                final GestorBandeja reg = (GestorBandeja) data;
                item.setValue(reg);
                item.setLabel(reg.getSeyconID());
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
    		if (procedimiento != null) {
    			procedimiento = DelegateUtil.getTramiteDelegate().obtenerProcedimiento(procedimiento.getIdentificador());
    		}        	
		} catch (DelegateException e) {
			ConsolaUtil.generaDelegateException(e);
		}	
    }

    /**
     * Mapea datos a los controles de la pantalla.
     */
	private void mapearDatos() {
		try {
			
			gestoresList = new ArrayList();
			
			if (procedimiento != null) {
				codigo.setValue(procedimiento.getIdentificador());
				TraProcedimiento proc = (TraProcedimiento) procedimiento.getTraduccion("ca");
				descripcion.setValue(proc.getDescripcion());		
			
				// Organismo (pendiente)
			
				// Unidad administrativa
				if (procedimiento.getUnidadAdministrativa() != null) {
					unidadAdministrativaCodigo = procedimiento.getUnidadAdministrativa().toString();
					List params = new ArrayList();
					params.add(unidadAdministrativaCodigo);
					ValoresDominio vd = UtilDominios.recuperarValoresDominio(ConstantesDominio.DOMINIO_SAC_NOMBRE_UNIDAD_ADMINISTRATIVA, params);
					if (vd.getNumeroFilas() > 0) {
						String descUA =  vd.getValor(1, "DESCRIPCION");
						unidadAdministrativaDesc.setValue(descUA);
					}				
				}
				
				
				// Checks permitir SMS y notif
				checkAvisoNotificaciones.setChecked("S".equals(procedimiento.getAvisarNotificaciones()));
				checkPermitirSMS.setChecked("S".equals(procedimiento.getPermitirSms()));
				
				// Lista procs
				gestoresList = ConsolaUtil.setToList(procedimiento.getGestores());     
			}
			
			// Lista procs			
	     	listaGestores.setModel(new BindingListModelList(gestoresList, true));
	     	
	     	if (modo != TypeModoAcceso.ALTA) {
	     		codigo.setReadonly(true);	     		
			} else {
				rowGestores.setVisible(false);
			}
			
			
		} catch (Exception e) {
			ConsolaUtil.generaDelegateException(e);
		}
	}
	
	 /**
     * Click boton Cancelar.
     */
    public final void onClick$btnCancelar() {
    	wProcedimiento.detach();
    }
    
    /**
     * Click boton Guardar.
     */
    public final void onClick$btnGuardar() {
    	 try { 
    		 	ProcedimientoDelegate procDelegate = DelegateUtil.getTramiteDelegate();
    		 
		    	// Modo consulta: acceso no permitido
		    	if (modo == TypeModoAcceso.CONSULTA) {
		    		ConsolaUtil.generaOperacionNoPermitidaException();
		    	}
		    	
		    	// Verificamos campos obligatorios    	
		        final String[] nomCampos = {Labels.getLabel("procedimiento.identificador"), 
		        		Labels.getLabel("procedimiento.descripcion"),		        		
		        		Labels.getLabel("procedimiento.organismo")};
		        final String[] valCampos = {codigo.getText(), descripcion.getText(), organismoDesc.getText()};
		        final boolean ok = verificarCamposObligatorios(nomCampos, valCampos);
		        
		        // Guardamos datos
		        if (ok) {       
		        	
		        	Procedimiento procUpdate = null;
		        	
		        	if (modo == TypeModoAcceso.ALTA) {		        		
		        		// Verifica si existe otro proc con ese codigo
		            	if (procDelegate.obtenerProcedimiento(codigo.getValue()) != null) {
		    				mostrarError(Labels.getLabel("error.codigoRepetido"), Labels.getLabel("mensaje.atencion"));
		    				return;
		            	}
		            	// Verifica si id tiene formato correcto
		            	codigo.setText(codigo.getText().toUpperCase());
		            	if (!StringUtil.validarFormatoIdentificador(codigo.getText()) ){		                	
		                	 mostrarError(Labels.getLabel("procedimiento.identificador") + ": " + Labels.getLabel("error.formatoIdentificadorNoValido"),
		                			 Labels.getLabel("mensaje.atencion"));       	
		        		}
		            	procUpdate = new Procedimiento();
		            	procUpdate.setIdentificador(codigo.getValue());
		        	} else {
		        		procUpdate = procedimiento;
		        	}
		        	
		        	//procUpdate.setDescripcion(descripcion.getValue());
		        	procUpdate.setUnidadAdministrativa(StringUtils.isNotBlank(unidadAdministrativaCodigo)?Long.parseLong(unidadAdministrativaCodigo): null);
		        	procUpdate.setPermitirSms(checkAvisoNotificaciones.isChecked()?"S":"N");
		        	procUpdate.setAvisarNotificaciones(checkAvisoNotificaciones.isChecked()?"S":"N");
		        	procUpdate.setGestores(ConsolaUtil.listToSet(gestoresList));
		                                   
		                      	
		            // Grabamos procedimiento
		        	procDelegate.grabarProcedimiento(procUpdate);            	
		            	
		            //Generamos evento            	
		            Events.postEvent(new Event(ConsolaUtil.eventoModoAcesso(modo), wProcedimiento.getParent(), procUpdate));
		    		
		    		
		    		wProcedimiento.detach();
		        }
    	
    	 } catch (DelegateException e) {
 			ConsolaUtil.generaDelegateException(e);
 		}
    }    
    
    /**
     * Click boton seleccion Organismo.
     */
    public final void onClick$btnOrganismo() {
    	ConsolaUtil.abrirSelectorOrganismo(this);
    }
    
    /**
     * Click boton seleccion Unidad Administrativa.
     */
    public final void onClick$btnUnidadAdministrativa() {
    	ConsolaUtil.abrirSelectorUnidadAdministrativa(this);
    }
    
    /**
     * Click boton añadir gestor.
     */
    public final void onClick$btnAddGestor() {
    	ConsolaUtil.abrirSelectorGestor(this);
    }
    
    /**
     * Click boton añadir gestor.
     */
    public final void onClick$btnDelGestor() {
    	final Object valueSelected = obtenerItemSeleccionado(listaGestores);
        if (valueSelected != null) {
        	gestoresList.remove(valueSelected);
        	listaGestores.setModel(new BindingListModelList(gestoresList, true));
        }
    }

	@Override
	protected void onSeleccionSelector(String tipoSelector,
			Object valorSeleccionado) {
		if (ConstantesWEB.SELECTOR_ORGANISMO.equals(tipoSelector)) {
			OrganoResponsable o = (OrganoResponsable) valorSeleccionado;
			organismoDesc.setValue(o.getDescripcion());
			organismoProcedimiento = o;
		}
		
		if (ConstantesWEB.SELECTOR_UNIDAD_ADMINISTRATIVA.equals(tipoSelector)) {
			UnidadAdministrativa o = (UnidadAdministrativa) valorSeleccionado;
			unidadAdministrativaDesc.setValue(o.getDescripcion());
			unidadAdministrativaCodigo = o.getCodigo();
		}
		
		if (ConstantesWEB.SELECTOR_GESTOR.equals(tipoSelector)) {
			GestorBandeja o = (GestorBandeja) valorSeleccionado;
			if (buscarGestorBandeja(gestoresList, o.getSeyconID()) == null) {
				gestoresList.add(o);			
				listaGestores.setModel(new BindingListModelList(gestoresList, true));
			}
		}
		
	}

	/**
	 * Busca en lista.
	 * @param listaGestores Lista gestores
	 * @return gestor o null
	 */
	private GestorBandeja buscarGestorBandeja(List listaGestores, String seyconId) {
		boolean existe = false;
		GestorBandeja gl = null;
		for (Iterator it = listaGestores.iterator(); it.hasNext();){
			gl = (GestorBandeja) it.next();
			if (gl.getSeyconID().equals(seyconId)) {
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
     * Ayuda.
     * 
     */
    public final void onClick$btnAyuda() {
        GTTUtilsWeb.onClickBotonAyuda("configuracion/fichaProcedimiento");
    }
}
