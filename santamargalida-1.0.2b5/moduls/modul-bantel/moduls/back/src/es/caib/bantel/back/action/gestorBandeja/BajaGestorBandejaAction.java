package es.caib.bantel.back.action.gestorBandeja;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.bantel.back.action.BaseAction;
import es.caib.bantel.persistence.delegate.DelegateUtil;
import es.caib.bantel.persistence.delegate.GestorBandejaDelegate;

/**
 * Action para preparar borrar un GestorBandeja.
 *
 * @struts.action
 *  name="gestorBandejaForm"
 *  scope="session"
 *  validate="false"
 *  path="/back/gestorBandeja/baja"
 *
 * @struts.action-forward
 *  name="success" path=".gestorBandeja.lista"
 */
public class BajaGestorBandejaAction extends BaseAction{
    protected static Log log = LogFactory.getLog(BajaGestorBandejaAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.info("Entramos en BajaGestorBandeja");
        GestorBandejaDelegate gestorBandejaDelegate = DelegateUtil.getGestorBandejaDelegate();

        String idString = request.getParameter("codigo");
        if (idString == null || idString.length() == 0) {
            log.warn("El paràmetre codigo és null!!");
            return mapping.findForward("fail");
        }

        gestorBandejaDelegate.borrarGestorBandeja(idString);
        //request.setAttribute("reloadMenu", "true");

        return mapping.findForward("success");
    }
}
