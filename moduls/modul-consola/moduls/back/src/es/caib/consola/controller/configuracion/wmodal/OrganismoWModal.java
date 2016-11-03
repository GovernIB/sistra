package es.caib.consola.controller.configuracion.wmodal;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import es.caib.consola.ConstantesWEB;
import es.caib.consola.model.Usuario;
import es.caib.consola.model.types.TypeModoAcceso;
import es.caib.consola.util.ConsolaUtil;
import es.caib.consola.util.GTTUtilsWeb;
import es.caib.sistra.model.OrganoResponsable;
import es.caib.sistra.persistence.delegate.DelegateException;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.zkutils.zk.composer.BaseComposer;

/**
 * Ventana de edicion organismo.
 */
@SuppressWarnings("serial")
public class OrganismoWModal extends BaseComposer {

	/** Referencia ventana. */
    private Window wOrganismo;
    /** Usuario logeado.*/
	private Usuario usuarioLogado;
    /** Modo edicion. */
    private TypeModoAcceso modo;
    /** Organo editado.*/
    private OrganoResponsable organo;    	   
    /** Descripcion organo. */
    private Textbox descripcion;
    
    
    
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
     	organo = (OrganoResponsable) arg.get(ConstantesWEB.PARAM_OBJETO_EDICION);   
     	
     	// Si se le pasa organo, refrescamos valores
     	refrescarDatos();
     	
     	// Usuario autenticado
     	usuarioLogado = ConsolaUtil.recuperarUsuarioLogado();
        
     	// Mapea datos a los controles de la pantalla
     	mapearDatos();
     	
    }

    /**
     * Si se pasan datos, se refrescan de BBDD para asegurarse q es lo ultimo.
     */
    private void refrescarDatos() {
    	try {            	
    		if (organo != null) {
    			organo = DelegateUtil.getOrganoResponsableDelegate().obtenerOrganoResponsable(organo.getCodigo());
    		}        	
		} catch (DelegateException e) {
			ConsolaUtil.generaDelegateException(e);
		}	
    }

	/**
     * Mapea datos a los controles de la pantalla.
     */
	private void mapearDatos() {
		if (modo != TypeModoAcceso.ALTA) {
			descripcion.setValue(organo.getDescripcion());	
		}
	}
	
	 /**
     * Click boton Cancelar.
     */
    public final void onClick$btnCancelar() {
    	wOrganismo.detach();
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
        final String[] nomCampos = {Labels
                .getLabel("organismo.descripcion")};
        final String[] valCampos = {descripcion.getText()};
        final boolean ok = verificarCamposObligatorios(nomCampos, valCampos);
        
        // Guardamos datos
        if (ok) {                	
        	OrganoResponsable organoModif = null;
            if (modo == TypeModoAcceso.ALTA) {
            	organoModif = new OrganoResponsable();
            	organoModif.setDescripcion(descripcion.getValue());
            } else {
            	organo.setDescripcion(descripcion.getValue());
            	organoModif = organo;            	            
            }
                        
            try {            	
            	// Grabamos organo
            	Long idOrganismo = DelegateUtil.getOrganoResponsableDelegate().grabarOrganoResponsable( organoModif );
            	organoModif.setCodigo(idOrganismo);
            	
            	//Generamos evento            	
                Events.postEvent(new Event(ConsolaUtil.eventoModoAcesso(modo), wOrganismo.getParent(), organoModif));
    		} catch (DelegateException e) {
    			ConsolaUtil.generaDelegateException(e);
    		}
    		
    		wOrganismo.detach();
        }
    	
    	
    }
    
    /**
     * Ayuda.
     * 
     */
    public final void onClick$btnAyuda() {
        GTTUtilsWeb.onClickBotonAyuda("configuracion/fichaOrganismo");
    }
    

	
}