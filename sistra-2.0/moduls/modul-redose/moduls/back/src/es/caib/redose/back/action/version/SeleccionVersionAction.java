package es.caib.redose.back.action.version;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import es.caib.redose.back.action.BaseAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action para consultar una version.
 *
 * @struts.action
 *  path="/back/version/seleccion"
 *
 *
 * @struts.action-forward
 *  name="success" path=".version.editar"
 *
 * @struts.action-forward
 *  name="fail" path=".version.lista"
 *
 */
public class SeleccionVersionAction extends BaseAction{
    protected static Log log = LogFactory.getLog(SeleccionVersionAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.info("Entramos en SeleccionVersion");

        String idString = request.getParameter("codigo");
        if (idString == null || idString.length() == 0) {
            log.warn("El paràmetre id és null!!");
            return mapping.findForward("fail");
        }

        log.info("Seleccionar la version " + idString);

        Long id = new Long(idString);
        guardarVersion(mapping, request, id);

        return mapping.findForward("success");
    }



}
