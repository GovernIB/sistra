package org.ibit.rol.form.admin.action.patron;

import org.ibit.rol.form.admin.action.BaseAction;
import org.ibit.rol.form.admin.form.PatronForm;
import org.ibit.rol.form.model.Patron;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.ibit.rol.form.persistence.delegate.PatronDelegate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action para consultar un Patron.
 *
 * @struts.action
 *  path="/admin/patron/seleccion"
 *
 * @struts.action-forward
 *  name="success" path=".patron.editar"
 *
 * @struts.action-forward
 *  name="fail" path=".patron.lista"
 */
public class SeleccionPatronAction extends BaseAction {

    protected static Log log = LogFactory.getLog(SeleccionPatronAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.debug("Entramos en SeleccionPatron");

        PatronDelegate patronDelegate = DelegateUtil.getPatronDelegate();

        String idString = request.getParameter("id");
        if (idString == null || idString.length() == 0) {
            log.warn("El paràmetre id és null!!");
            return mapping.findForward("fail");
        }

        log.debug("Seleccionar el patron " + idString);

        Long id = new Long(idString);
        Patron patron = patronDelegate.obtenerPatron(id);
        // Ficar dins el form.
        PatronForm pForm = (PatronForm) obtenerActionForm(mapping, request, "/admin/patron/editar");
        pForm.setValues(patron);

        return mapping.findForward("success");
    }
}
