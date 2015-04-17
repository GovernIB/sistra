package es.caib.consola.controller.gestor.wmodal;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Include;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import es.caib.consola.ConstantesWEB;
import es.caib.consola.model.Usuario;
import es.caib.consola.model.types.TypeModoAcceso;
import es.caib.consola.util.ConsolaUtil;
import es.caib.sistra.model.TraTramite;
import es.caib.sistra.model.Tramite;
import es.caib.sistra.persistence.delegate.DelegateException;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.zkutils.zk.composer.BaseComposer;


/**
 * Ventana edicion tramite.
 */
@SuppressWarnings("serial")
public class TramiteWModal extends BaseComposer {
	 /**
     * Ventana version.
     */
    private Window wTramite;
    /**
     * Ventana padre.
     */
    private Component padre;
    /**
     * Contenedor principal.
     */
    private Include contenedorPrincipal;
    /**
     * Modo edicion.
     */
    private TypeModoAcceso modo;
    /**
     * Usuario autenticado.
     */
    private Usuario usuarioLogado;
    /**
     * Id tramite.
     */
    private Textbox id;
    /**
     * Descripcion.
     */
    private Textbox descripcion;
    /**
     * Tipo.
     */
    private Textbox procedimiento;
    /**
     * Tramite. 
     */
    private Tramite tramite;
    
    
    /*
     * (non-Javadoc)
     * 
     * @see
     * es.gva.dgm.tra.util.zk.composer.BaseComposer#doAfterCompose(org.zkoss
     * .zk.ui.Component)
     */
    @Override
    public final void doAfterCompose(final Component compTramite) {
        super.doAfterCompose(compTramite);

        usuarioLogado = ConsolaUtil.recuperarUsuarioLogado();
        padre = compTramite.getParent();
        modo = (TypeModoAcceso) arg.get(ConstantesWEB.PARAM_MODO_ACCESO);
        contenedorPrincipal = (Include) compTramite.getParent().getParent();        
        
        // TODO ESTABLECER LISTA DE PROCS
        
        // Establecemos ventana segun modo
        if (modo != TypeModoAcceso.ALTA) {
        	Long idTtramite = (Long) arg.get(ConstantesWEB.TRAMITE);
        	try {
				tramite = DelegateUtil.getTramiteDelegate().obtenerTramite(idTtramite);
			} catch (DelegateException e) {
				ConsolaUtil.generaDelegateException(e);
			}
            id.setValue(tramite.getIdentificador());
            descripcion.setValue(((TraTramite) tramite.getTraduccion("es")).getDescripcion());     
            procedimiento.setValue(tramite.getProcedimiento());
            if (modo.equals(ConstantesWEB.MODOCONSULTA)) {
               wTramite.setTitle(Labels.getLabel("tramite.titulo.consulta"));
            }            
        } else {            
            wTramite.setTitle(Labels.getLabel("tramite.titulo.alta"));
            tramite = new Tramite();            
        }

       
        // TODO GESTION MULTIDIOMA
        /*
        wTramite.addEventListener(ConstantesWEB.EVENTO_GUARDAR_MULTIDIOMA,
                new MultidiomaEventListener(mapTextbox, idiomaContexto,
                        ConstantesWEB.EVENTO_GUARDAR_MULTIDIOMA));
        */
    }

    /**
     * Click descripcion multidioma.
     */
    public final void onClick$btnDescripcion() {
    	/*
        GTTUtilsWeb.abreMultidioma(this.self, descripcion,
                traduccionDescripcion, ConstantesWEB.OPCIONAL, modo);
        */
    }

    /**
     * Método para On click$btn guardar de la clase TramiteWModal.
     */
    public final void onClick$btnGuardar() {
    	/*
        GTTUtilsWeb.validarIdentificador(id);
        
        if (StringUtils.isEmpty(tipo.getValue())) {
            throw new WrongValueException(tipo,
                    Labels.getLabel("tramite.incorrecto"));
        }

        // GUARDAR
        final Event event;
        boolean find = false;

        tramite.setId(id.getValue());
        // RECUPERAMOS EL ORGANISMO DEL USUARIO
        tramite.setOrganismo(usuarioLogado.getOrganismo());
        tramite.setDescripcion(traduccionDescripcion);
        for (TipoTramite tt : tiposTramite) {
            if (tt.getId().equals(tipo.getSelectedItem().getValue()) && !find) {
                tramite.setTipo(tt);
                find = true;
            }
        }
        if (!find) {
            // NO DEBERÍA OCURRIR NUNCA
            // DARÁ ERROR EN BBDD
            tramite.setTipo(null);
        }
        
        try {
        final Tramite ret = servicioTramites.save(tramite);
        
        // Validamos si hay algún error de codigos únicos
        if (ret.getCodigo() < 0) {
            // En este caso el único campo único es la descripción y en ese caso
            // el servicio devuelve -1
            throw new WrongValueException(id,
                    Labels.getLabel("error.codigoRepetido"));
        }
        
        if (modo.equals(TypeModoAcceso.ALTA)) {
            // ACTUALIZAR EN CASO DE NUEVO
            tramite = ret;
            // REDIRECCIONAR INCLUDE CONTENEDORPRINCIPAL
            session.setAttribute(ConstantesWEB.TRAMITE, tramite);
            habilitaInclude(contenedorPrincipal, "/gestor/ges-tramite.zul",
                    null);
        } else {
            // se verá en la pantalla de ges-tramites
            event = new Event(ConstantesWEB.ACCION_GUARDAR_TRAMITE, padre, tramite);
            Events.postEvent(event);
        }

        wTramite.detach();
        } catch (PermisosSeguridadException e) {
            mostrarMessageBox(
                    Labels.getLabel(ConstantesWEB.ENTIDAD_MENSAJE_PERMISOS),
                    Labels.getLabel(ConstantesWEB.ERROR), Messagebox.OK,
                    Messagebox.ERROR);
        }
        */
    }

    /**
     * Click cancelar.
     */
    public final void onClick$btnCancelar() {
    	wTramite.detach();
    }
    
    
    /**
     * Click ayuda.
     */
    public final void onClick$btnAyuda() {
    	// GTTUtilsWeb.onClickBotonAyuda("html.ayuda.tramite");
    }	

}
