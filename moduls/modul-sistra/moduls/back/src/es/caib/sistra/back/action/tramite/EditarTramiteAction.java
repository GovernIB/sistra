package es.caib.sistra.back.action.tramite;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.sistra.back.action.BaseAction;
import es.caib.sistra.back.action.menu.Nodo;
import es.caib.sistra.back.form.TramiteForm;
import es.caib.sistra.model.RolUsuarioTramite;
import es.caib.sistra.model.RolUsuarioTramiteId;
import es.caib.sistra.model.Tramite;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.sistra.persistence.delegate.GruposDelegate;
import es.caib.sistra.persistence.delegate.TramiteDelegate;

/**
 * Action para editar una Tramite.
 *
 * @struts.action
 *  name="tramiteForm"
 *  scope="session"
 *  validate="true"
 *  input=".tramite.editar"
 *  path="/back/tramite/editar"
 *
 * @struts.action-forward
 *  name="alta" path="/back/tramite/alta.do"
 *
 * @struts.action-forward
 *  name="reload" path=".tramite.editar"
 *
 * @struts.action-forward
 *  name="success" path=".tramite.editar"
 *
 * @struts.action-forward
 *  name="cancel" path=".organo.editar"
 *
 */
public class EditarTramiteAction extends BaseAction{

    protected static Log log = LogFactory.getLog(EditarTramiteAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.info("Entramos en EditarTramite");
        TramiteDelegate tramiteDelegate = DelegateUtil.getTramiteDelegate();
        TramiteForm tramiteForm = (TramiteForm) form;
        Tramite tramite = (Tramite) tramiteForm.getValues();
        //TraTramite traduccion = ( TraTramite ) tramite.getTraduccion( tramiteForm.getLang() );
        //TraTramite traduccion = ( TraTramite ) tramite.getTraduccion();

        if (isCancelled(request)) 
        {
            log.info("isCancelled");
            Long idOrgano = tramiteForm.getIdOrgano();
            guardarOrgano(mapping, request, idOrgano);
            return mapping.findForward("cancel");
        }
        
        // Elimina traducciones que no son validas
        tramiteForm.validaTraduccion(mapping, request);
        
        //log.info( "EDICION DE PLANTILLA; PLANTILLA PERSISTENTE " + tramite );

        if (isAlta(request) || isModificacion(request)) 
        {
            log.info("isAlta || isModificacio");
            
            
            //log.info( "EDICION DE PLANTILLA; BEFORE SAVE " + tramite );

            Long idOrgano = tramiteForm.getIdOrgano();
            
            boolean recargarArbol = false;
            if ( isAlta( request ) )
            {
            	recargarArbol =  true;
            }
            else
            {
            	Tramite tramiteAntesGrabar = tramiteDelegate.obtenerTramite( tramite.getCodigo() );
            	recargarArbol = !tramiteAntesGrabar.getIdentificador().equals( tramite.getIdentificador() );
            }
            
            if(StringUtils.isNotEmpty(request.getUserPrincipal().getName())){
            tramiteDelegate.grabarTramite(tramite, idOrgano);
            	// Para nuevos tramites,da de alta el usuario como usuario del tramite
            	if(isAlta(request)){
            		GruposDelegate gruposDelegate = DelegateUtil.getGruposDelegate();
            		RolUsuarioTramite userTramite = new RolUsuarioTramite(new RolUsuarioTramiteId(request.getUserPrincipal().getName(),tramite.getCodigo()));
					gruposDelegate.asociarUsuarioTramite(userTramite);
				 }
            log.info("Creat/Actualitzat " + tramite.getCodigo());
            guardarTramite(mapping, request, tramite.getCodigo());
            }
            
            if ( recargarArbol )
            {
            	this.setReloadTree( request, Nodo.IR_A_DEFINICION_TRAMITE, tramite.getCodigo() );
            }

            //request.setAttribute("reloadMenu", "true");
            return mapping.findForward("success");

        }
        
        
        // Cambio de idioma
        tramiteForm.reloadLang();
        
        
        //log.info( "EDICION DE PLANTILLA; BEFORE FORWARD " + tramite );
        
        
        return mapping.findForward("reload");
    }
    


}
