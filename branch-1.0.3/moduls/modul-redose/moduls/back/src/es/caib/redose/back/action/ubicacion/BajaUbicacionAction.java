package es.caib.redose.back.action.ubicacion;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.redose.back.action.BaseAction;
import es.caib.redose.persistence.delegate.DelegateUtil;
import es.caib.redose.persistence.delegate.UbicacionDelegate;

/**
 * Action para preparar borrar un Ubicacion.
 *
 * @struts.action
 *  name="ubicacionForm"
 *  scope="session"
 *  validate="false"
 *  path="/back/ubicacion/baja"
 *
 * @struts.action-forward
 *  name="success" path=".ubicacion.lista"
 */
public class BajaUbicacionAction extends BaseAction{
    protected static Log log = LogFactory.getLog(BajaUbicacionAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.info("Entramos en BajaUbicacion");
        UbicacionDelegate ubicacionDelegate = DelegateUtil.getUbicacionDelegate();

        String idString = request.getParameter("codigo");
        if (idString == null || idString.length() == 0) {
            log.warn("El paràmetre codigo és null!!");
            return mapping.findForward("fail");
        }

        Long id = new Long(idString);
        ubicacionDelegate.borrarUbicacion(id);
        request.setAttribute("reloadMenu", "true");

        return mapping.findForward("success");
    }
}
