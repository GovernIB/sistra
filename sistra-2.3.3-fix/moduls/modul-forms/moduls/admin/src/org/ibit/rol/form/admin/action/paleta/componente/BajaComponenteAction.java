package org.ibit.rol.form.admin.action.paleta.componente;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.ibit.rol.form.admin.action.BaseAction;
import org.ibit.rol.form.persistence.delegate.ComponenteDelegate;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;

/**
 * @struts.action
 *  path="/admin/componente/baja"
 *
 * @struts.action-forward
 *  name="success" path=".paleta.editar"
 *
 * @struts.action-forward
 *  name="fail" path=".paleta.lista"
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

        String idPaletaString = request.getParameter("idPaleta");
        if (idPaletaString == null || idPaletaString.length() == 0) {
            log.warn("El paràmetre idPaleta és null");
            return mapping.findForward("fail");
        }

        Long idPaleta = new Long(idPaletaString);
        guardarPaleta(mapping, request, idPaleta);

        return mapping.findForward("success");
    }
}
