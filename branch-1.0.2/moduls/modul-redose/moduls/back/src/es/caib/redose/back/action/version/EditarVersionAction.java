package es.caib.redose.back.action.version;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import es.caib.redose.persistence.delegate.VersionDelegate;
import es.caib.redose.persistence.delegate.DelegateUtil;
import es.caib.redose.back.form.VersionForm;
import es.caib.redose.back.action.BaseAction;
import es.caib.redose.model.Version;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action para editar un Modelo.
 *
 * @struts.action
 *  name="versionForm"
 *  scope="session"
 *  validate="true"
 *  input=".version.editar"
 *  path="/back/version/editar"
 *
 * @struts.action-forward
 *  name="alta" path="/back/version/alta.do"
 *
 * @struts.action-forward
 *  name="reload" path=".version.editar"
 *
 * @struts.action-forward
 *  name="success" path=".version.editar"
 *
 * @struts.action-forward
 *  name="cancel" path=".modelo.editar"
 *
 */
public class EditarVersionAction extends BaseAction{

    protected static Log log = LogFactory.getLog(EditarVersionAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.info("Entramos en EditarVersion");
        VersionDelegate versionDelegate = DelegateUtil.getVersionDelegate();
        VersionForm versionForm = (VersionForm) form;
        Version version = (Version) versionForm.getValues();

        if (isCancelled(request)) {
            log.info("isCancelled");
            Long idModelo = versionForm.getIdModelo();
            guardarModelo(mapping, request, idModelo);
            return mapping.findForward("cancel");
        }


        if (isAlta(request) || isModificacion(request)) {
            log.info("isAlta || isModificacio");

            Long idModelo = versionForm.getIdModelo();
            versionDelegate.grabarVersion(version, idModelo);

            //actualizaPath(request.getSession(true), 2, version.getId().toString());
            log.info("Creat/Actualitzat " + version.getCodigo());

            //guardarModelo(mapping, request, idModelo);
            guardarVersion(mapping, request, version.getCodigo());

            request.setAttribute("reloadMenu", "true");
            return mapping.findForward("success");

        }
        // Cambio de idioma
        return mapping.findForward("reload");
    }

}
