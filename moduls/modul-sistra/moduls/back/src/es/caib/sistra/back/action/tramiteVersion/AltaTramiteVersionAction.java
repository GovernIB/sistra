package es.caib.sistra.back.action.tramiteVersion;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import es.caib.sistra.back.action.BaseAction;
import es.caib.sistra.back.form.TramiteVersionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action para preparar el alta de una TramiteVersion.
 *
 * @struts.action
 *  path="/back/tramiteVersion/alta"
 *
 * @struts.action-forward
 *  name="success" path=".tramiteVersion.editar"
 *
 * @struts.action-forward
 *  name="fail" path=".tramiteVersion.lista"
 *
 */
public class AltaTramiteVersionAction extends BaseAction{
    protected static Log log = LogFactory.getLog(AltaTramiteVersionAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        String idTramiteString = request.getParameter("idTramite");
        if (idTramiteString == null || idTramiteString.length() == 0) {
            log.info("idTramite es null");
            return mapping.findForward("fail");
        }

        Long idTramite = new Long(idTramiteString);


        TramiteVersionForm tramiteVersionForm = (TramiteVersionForm) obtenerActionForm(mapping,request, "/back/tramiteVersion/editar");
        tramiteVersionForm.destroy(mapping, request);

        tramiteVersionForm.setIdTramite(idTramite);
        return mapping.findForward("success");
    }
}
