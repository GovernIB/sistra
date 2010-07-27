package es.caib.sistra.back.action.tramite;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import es.caib.sistra.back.action.BaseAction;
import es.caib.sistra.back.form.TramiteForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action para preparar el alta de una Tramite.
 *
 * @struts.action
 *  path="/back/tramite/alta"
 *
 * @struts.action-forward
 *  name="success" path=".tramite.editar"
 *
 * @struts.action-forward
 *  name="fail" path=".tramite.lista"
 *
 */
public class AltaTramiteAction extends BaseAction{
    protected static Log log = LogFactory.getLog(AltaTramiteAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        String idOrganoString = request.getParameter("idOrgano");
        if (idOrganoString == null || idOrganoString.length() == 0) {
            log.info("idOrgano es null");
            return mapping.findForward("fail");
        }

        Long idOrgano = new Long(idOrganoString);


        TramiteForm tramiteForm = (TramiteForm) obtenerActionForm(mapping,request, "/back/tramite/editar");
        tramiteForm.destroy(mapping, request);

        tramiteForm.setIdOrgano(idOrgano);
        return mapping.findForward("success");
    }
}
