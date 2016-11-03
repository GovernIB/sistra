package es.caib.sistra.back.action.mensajeTramite;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.sistra.back.action.BaseAction;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.sistra.persistence.delegate.MensajeTramiteDelegate;

/**
 * Action para preparar borrar una MensajeTramite.
 *
 * @struts.action
 *  name="mensajeTramiteForm"
 *  scope="session"
 *  validate="false"
 *  path="/back/mensajeTramite/baja"
 *
 * @struts.action-forward
 *  name="success" path=".mensajeTramite.lista"
 *  
 * @struts.action-forward
 * name="fail" path=".mensajeTramite.lista"
 */
public class BajaMensajeTramiteAction extends BaseAction{
    protected static Log log = LogFactory.getLog(BajaMensajeTramiteAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.info("Entramos en BajaMensajeTramite");
        String idString = request.getParameter("codigo");
        if (idString == null || idString.length() == 0) {
            log.warn("El paràmetre codigo és null!!");
            return mapping.findForward("fail");
        }

        MensajeTramiteDelegate mensajeTramiteDelegate = DelegateUtil.getMensajeTramiteDelegate();
        Long id = new Long(idString);
        mensajeTramiteDelegate.borrarMensajeTramite(id);
        //request.setAttribute("reloadMenu", "true");

        String idTramiteVersionString = request.getParameter("idTramiteVersion");
        if (idTramiteVersionString == null || idTramiteVersionString.length() == 0) {
            log.warn("El paràmetre idTramiteVersion és null");
            return mapping.findForward("fail");
        }

        Long idTramiteVersion = new Long( idTramiteVersionString );
        guardarTramiteVersion( mapping, request, idTramiteVersion );

        return mapping.findForward("success");
    }

}
