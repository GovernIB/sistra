package es.caib.sistra.back.action.dominio;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.sistra.back.action.BaseAction;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.sistra.persistence.delegate.DominioDelegate;

/**
 * Action para preparar borrar un Dominio.
 *
 * @struts.action
 *  name="dominioForm"
 *  scope="session"
 *  validate="false"
 *  path="/back/dominio/baja"
 *
 * @struts.action-forward
 *  name="success" path=".dominio.lista"
 */
public class BajaDominioAction extends BaseAction{
    protected static Log log = LogFactory.getLog(BajaDominioAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.info("Entramos en BajaDominio");
        DominioDelegate dominioDelegate = DelegateUtil.getDominioDelegate();

        String idString = request.getParameter("codigo");
        if (idString == null || idString.length() == 0) {
            log.warn("El paràmetre codigo és null!!");
            return mapping.findForward("fail");
        }

        Long id = new Long(idString);
        dominioDelegate.borrarDominio(id);
        //request.setAttribute("reloadMenu", "true");

        return mapping.findForward("success");
    }
}
