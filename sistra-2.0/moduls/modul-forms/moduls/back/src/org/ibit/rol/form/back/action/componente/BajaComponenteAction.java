package org.ibit.rol.form.back.action.componente;

import org.ibit.rol.form.back.action.BaseAction;
import org.ibit.rol.form.persistence.delegate.ComponenteDelegate;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @struts.action
 *  path="/back/componente/baja"
 *
 * @struts.action-forward
 *  name="success" path=".pantalla.editar"
 *
 * @struts.action-forward
 *  name="fail" path=".pantalla.lista"
 */
public class BajaComponenteAction extends BaseAction {
    protected static Log log = LogFactory.getLog(BajaComponenteAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        log.debug("Entramos en BajaComponenteAction");

        ComponenteDelegate delegate = DelegateUtil.getComponenteDelegate();

        String idString = request.getParameter("id");
        if (idString == null || idString.length() == 0) {
            log.warn("El paràmetre id és null");
            return mapping.findForward("fail");
        }

        log.debug("Borrando el componente " + idString);

        Long id = new Long(idString);
        delegate.borrarComponente(id);
        request.setAttribute("reloadMenu", "true");

        String idPantallaString = request.getParameter("idPantalla");
        if (idPantallaString == null || idPantallaString.length() == 0) {
            log.warn("El paràmetre idPantalla és null");
            return mapping.findForward("fail");
        }

        Long idPantalla = new Long(idPantallaString);
        guardarPantalla(mapping, request, idPantalla);

        return mapping.findForward("success");
    }
}
