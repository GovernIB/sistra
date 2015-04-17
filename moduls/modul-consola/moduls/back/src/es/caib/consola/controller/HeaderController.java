package es.caib.consola.controller;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Div;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.Window;

import es.caib.consola.ConstantesWEB;
import es.caib.consola.model.Idioma;
import es.caib.consola.model.Usuario;
import es.caib.consola.util.ConsolaUtil;
import es.caib.sistra.model.OrganoResponsable;
import es.caib.sistra.persistence.delegate.DelegateException;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.zkutils.zk.composer.BaseComposer;

/**
 * Controlador de la barra de miga de pan del usuario.
 * 
 * @author Indra
 * 
 */
@SuppressWarnings("serial")
public class HeaderController extends BaseComposer {

    /**
     * Atributo label Entorno.
     */
    private Label labelEntorno;

    /**
     * Atributo label organismo.
     */
    private Label labelOrganismo;

    /**
     * Atributo label Tramite.
     */
    private Label labelTramite;

    /**
     * Atributo label Version.
     */
    private Label labelVersion;

    /**
     * Atributo version.
     */
    private Div version;

    /**
     * Atributo tramite.
     */
    private Div tramite;

    /**
     * Atributo contenedor principal.
     */
    private Include contenedorPrincipal;

    /**
     * Atributo usuario logado.
     */
    private Usuario usuarioLogado;
 
    /**
     * Atributo miga de HeaderController.
     */
    private Hbox miga;

    /*
     * (non-Javadoc)
     * 
     * @see
     * es.gva.dgm.tra.util.zk.composer.BaseComposer#doAfterCompose(org.zkoss
     * .zk.ui.Component)
     */
    @Override
    public final void doAfterCompose(final Component comp) {
        super.doAfterCompose(comp);
                
        usuarioLogado = ConsolaUtil.recuperarUsuarioLogado();
        
        // TODO IDIOMAS SOPORTADOS
        if (session.getAttribute(ConstantesWEB.IDIOMAS_PLATAFORMA) == null) {
        	List<Idioma> idiomasDisponiblesPlataforma = new ArrayList<Idioma>();
        	Idioma id = new Idioma();
        	id.setCodigo("es");
        	id.setDescripcion("Espa�ol");
        	idiomasDisponiblesPlataforma.add(id);
        	id = new Idioma();
        	id.setCodigo("ca");
        	id.setDescripcion("Catalan");
        	idiomasDisponiblesPlataforma.add(id);
            session.setAttribute(ConstantesWEB.IDIOMAS_PLATAFORMA,
            		idiomasDisponiblesPlataforma);
        }
            
        
        // Entorno
        if ("PRODUCCION".equals(usuarioLogado.getEntorno())) {
			labelEntorno.setValue(Labels.getLabel("entorno.produccion"));
		} else {
			labelEntorno.setValue(Labels.getLabel("entorno.desarrollo"));
		}			
		
        // Miga: organismo
        try {
			OrganoResponsable org = DelegateUtil.getOrganoResponsableDelegate().obtenerOrganoResponsable(usuarioLogado.getOrganismo());
			labelOrganismo.setValue(org.getDescripcion());
		} catch (DelegateException e) {
			ConsolaUtil.generaDelegateException(e);
		}
        
		// Miga: tramite
        if (comp.getParent().getId().equals("tram") || comp.getParent().getId().equals("verTram")) {
            if (session.getAttribute(ConstantesWEB.TRAMITE) != null) {
            	labelTramite.setValue((String) session.getAttribute(ConstantesWEB.TRAMITE_ID));    			   		
            }
            tramite.setVisible(true);
        }
        
        // Miga: version
        if (comp.getParent().getId().equals("verTram")) {
            if (session.getAttribute(ConstantesWEB.VERSION) != null) {
            	labelVersion.setValue(Labels.getLabel("miga.version")
                        + " "
                        + session.getAttribute(ConstantesWEB.VERSION_NUM));
            }
            tramite.setVisible(true);
            version.setVisible(true);
        }
                 
    }

    /**
     * Método de control del evento On click de la miga entorno de la clase
     * HeaderController.
     */
    public final void onClick$MigaEntorno() {
        final Window ventana = (Window) creaComponente(
                "/gestor/windows/ges-entorno-win.zul", this.self, null);
        abreVentanaModal(ventana);
    }

    /**
     * Método de control del evento On click de la miga organismo de la clase
     * HeaderController.
     */
    public final void onClick$MigaOrga() {
        if (tramite.isVisible()) {
            session.setAttribute(ConstantesWEB.VERSION, null);
            session.setAttribute(ConstantesWEB.VERSION_NUM, null);
            session.setAttribute(ConstantesWEB.TRAMITE, null);
            session.setAttribute(ConstantesWEB.TRAMITE_ID, null);
            habilitaInclude(getContenedorPrincipal(),
                    "/gestor/ges-organismo.zul", null);
        }
    }

    /**
     * Método de control del evento On click de la miga trámite de la clase
     * HeaderController.
     */
    public final void onClick$MigaTram() {
        if (version.isVisible()) {
            session.setAttribute(ConstantesWEB.VERSION, null);
            session.setAttribute(ConstantesWEB.VERSION_NUM, null);
            habilitaInclude(getContenedorPrincipal(),
                    "/gestor/ges-tramite.zul", null);
        }
    }

    /**
     * Asigna el atributo contenedor principal de HeaderController.
     * 
     * @param pContenedorPrincipal
     *            el nuevo valor para contenedor principal
     */
    public final void setContenedorPrincipal(final Include pContenedorPrincipal) {
        this.contenedorPrincipal = pContenedorPrincipal;
    }

    /**
     * Obtiene el atributo contenedor principal de HeaderController.
     * 
     * @return el atributo contenedor principal
     */
    public final Include getContenedorPrincipal() {
        return contenedorPrincipal;
    }

}
