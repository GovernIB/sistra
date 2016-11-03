package es.caib.sistra.back.action.mensajeTramite;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import es.caib.sistra.back.action.BaseAction;
import es.caib.sistra.back.form.MensajeTramiteForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action para preparar el alta de una MensajeTramite.
 *
 * @struts.action
 *  path="/back/mensajeTramite/alta"
 *
 * @struts.action-forward
 *  name="success" path=".mensajeTramite.editar"
 *
 * @struts.action-forward
 *  name="fail" path=".mensajeTramite.lista"
 *
 */
public class AltaMensajeTramiteAction extends BaseAction{
    protected static Log log = LogFactory.getLog(AltaMensajeTramiteAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        String idTramiteVersionString = request.getParameter("idTramiteVersion");
        if (idTramiteVersionString == null || idTramiteVersionString.length() == 0) {
            log.info("idTramiteVersion es null");
            return mapping.findForward("fail");
        }

        Long idTramiteVersion = new Long(idTramiteVersionString);


        MensajeTramiteForm mensajeTramiteForm = (MensajeTramiteForm) obtenerActionForm(mapping,request, "/back/mensajeTramite/editar");
        mensajeTramiteForm.destroy(mapping, request);

        mensajeTramiteForm.setIdTramiteVersion(idTramiteVersion);
        return mapping.findForward("success");
    }
}
