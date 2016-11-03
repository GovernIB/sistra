package es.caib.sistra.back.action.gestorFormularios;

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
import es.caib.sistra.persistence.delegate.GestorFormularioDelegate;

/**
 * Action para preparar borrar un Dominio.
 *
 * @struts.action
 *  name="dominioForm"
 *  scope="session"
 *  validate="false"
 *  path="/back/gestorFormularios/baja"
 *
 * @struts.action-forward
 *  name="success" path=".gestorFormularios.lista"
 */
public class BajaFormularioAction extends BaseAction{
    protected static Log log = LogFactory.getLog(BajaFormularioAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.info("Entramos en BajaFormulario");
        GestorFormularioDelegate gFormularioDelegate = DelegateUtil.getGestorFormularioDelegate();

        String idString = request.getParameter("identificador");
        if (idString == null || idString.length() == 0) {
            log.warn("El paràmetre identificador és null!!");
            return mapping.findForward("fail");
        }

        gFormularioDelegate.borrarFormulario(idString);
        //request.setAttribute("reloadMenu", "true");

        return mapping.findForward("success");
    }
}
