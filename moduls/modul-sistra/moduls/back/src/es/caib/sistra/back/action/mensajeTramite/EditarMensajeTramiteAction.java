package es.caib.sistra.back.action.mensajeTramite;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.sistra.back.action.BaseAction;
import es.caib.sistra.back.form.MensajeTramiteForm;
import es.caib.sistra.model.MensajeTramite;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.sistra.persistence.delegate.MensajeTramiteDelegate;

/**
 * Action para editar una MensajeTramite.
 *
 * @struts.action
 *  name="mensajeTramiteForm"
 *  scope="session"
 *  validate="true"
 *  input=".mensajeTramite.editar"
 *  path="/back/mensajeTramite/editar"
 *
 * @struts.action-forward
 *  name="alta" path="/back/mensajeTramite/alta.do"
 *
 * @struts.action-forward
 *  name="reload" path=".mensajeTramite.editar"
 *
 * @struts.action-forward
 *  name="success" path=".mensajeTramite.editar"
 *
 * @struts.action-forward
 *  name="cancel" path=".mensajeTramite.lista"
 *
 */
public class EditarMensajeTramiteAction extends BaseAction{

    protected static Log log = LogFactory.getLog(EditarMensajeTramiteAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.info("Entramos en EditarMensajeTramite");
        MensajeTramiteDelegate mensajeTramiteDelegate = DelegateUtil.getMensajeTramiteDelegate();
        MensajeTramiteForm mensajeTramiteForm = (MensajeTramiteForm) form;
        MensajeTramite mensajeTramite = (MensajeTramite) mensajeTramiteForm.getValues();

        if (isCancelled(request)) 
        {
            log.info("isCancelled");
            Long idTramiteVersion = mensajeTramiteForm.getIdTramiteVersion();
            guardarTramiteVersion(mapping, request, idTramiteVersion);
            return mapping.findForward("cancel");
        }
        
        // Elimina traducciones que no son validas
        mensajeTramiteForm.validaTraduccion(mapping, request);
        
        if (isAlta(request) || isModificacion(request)) 
        {
            log.info("isAlta || isModificacio");

            Long idTramiteVersion = mensajeTramiteForm.getIdTramiteVersion();
            mensajeTramiteDelegate.grabarMensajeTramite(mensajeTramite, idTramiteVersion);

            //actualizaPath(request.getSession(true), 2, mensajeTramite.getId().toString());
            log.info("Creat/Actualitzat " + mensajeTramite.getCodigo());

            guardarMensajeTramite(mapping, request, mensajeTramite.getCodigo());
            //request.setAttribute("reloadMenu", "true");
            return mapping.findForward("success");

        }
        
        // Cambio de idioma
        mensajeTramiteForm.reloadLang();
        
        return mapping.findForward("reload");
    }

}
