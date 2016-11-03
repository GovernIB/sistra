package es.caib.redose.back.action.version;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import es.caib.redose.back.action.BaseAction;
import es.caib.redose.back.form.VersionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action para preparar el alta de una Version.
 *
 * @struts.action
 *  path="/back/version/alta"
 *
 * @struts.action-forward
 *  name="success" path=".version.editar"
 *
 * @struts.action-forward
 *  name="fail" path=".modelo.lista"
 *
 */
public class AltaVersionAction extends BaseAction{
    protected static Log log = LogFactory.getLog(AltaVersionAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        String idModeloString = request.getParameter("idModelo");
        if (idModeloString == null || idModeloString.length() == 0) {
            log.info("idModelo es null");
            return mapping.findForward("fail");
        }

        Long idModelo = new Long(idModeloString);


        VersionForm versionForm = (VersionForm) obtenerActionForm(mapping,request, "/back/version/editar");
        versionForm.destroy(mapping, request);

        versionForm.setIdModelo(idModelo);
        return mapping.findForward("success");
    }
}
