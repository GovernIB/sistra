package es.caib.mobtratel.back.action.permiso;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.mobtratel.back.action.BaseAction;
import es.caib.mobtratel.persistence.delegate.DelegateUtil;
import es.caib.mobtratel.persistence.delegate.PermisoDelegate;

/**
 * Action para preparar borrar un GestorBandeja.
 *
 * @struts.action
 *  name="permisoForm"
 *  scope="session"
 *  validate="false"
 *  path="/back/permiso/baja"
 *
 * @struts.action-forward
 *  name="success" path=".permiso.lista"
 */
public class BajaPermisoAction extends BaseAction{
    protected static Log log = LogFactory.getLog(BajaPermisoAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.info("Entramos en BajaPermiso");
        
        PermisoDelegate permisoDelegate = DelegateUtil.getPermisoDelegate();

        String idString = request.getParameter("codigo");
        if (idString == null || idString.length() == 0) {
            log.warn("El paràmetre codigo és null!!");
            return mapping.findForward("fail");
        }


        permisoDelegate.borrarPermiso(Long.valueOf(idString));
        //request.setAttribute("reloadMenu", "true");
         
         

        return mapping.findForward("success");
    }
}
