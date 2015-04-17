package es.caib.consola.controller.gestor.wmodal;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import es.caib.consola.ConstantesWEB;
import es.caib.consola.model.Usuario;
import es.caib.consola.util.ConsolaUtil;
import es.caib.sistra.model.TramiteVersion;
import es.caib.zkutils.zk.composer.BaseComposer;

/**
 * Ventana de desbloquear version tramite.
 */
@SuppressWarnings("serial")
public class DesbloquearVersionTramiteWModal extends BaseComposer {

	/** Referencia ventana. */
    private Window wDesbloquearVersionTramite;
    /** Usuario logeado.*/
	private Usuario usuarioLogado;
    /** Modo edicion. */
    private String modo;
    /** Version tramite.*/
	private TramiteVersion version;
   
	/** Id tramite.*/
	private Textbox idTramite;
	/** Version tramite.*/
	private Textbox versionTramite;
	
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
     	modo = (String) arg.get(ConstantesWEB.PARAM_MODO_ACCESO);
     	version = (TramiteVersion) arg.get(ConstantesWEB.PARAMETER_VERSION_TRAMITE);     	 

     	// Usuario autenticado
     	usuarioLogado = ConsolaUtil.recuperarUsuarioLogado();
        
     	// Mapear datos
     	mapearDatos();
    }
	
    // Mapea datos
	 private void mapearDatos() {
		 idTramite.setValue(version.getTramite().getIdentificador());
		 versionTramite.setValue(Integer.toString(version.getVersion()));
	}

	/**
     * Click boton Cancelar.
     */
    public final void onClick$btnCancelar() {
    	wDesbloquearVersionTramite.detach();
    }

}
