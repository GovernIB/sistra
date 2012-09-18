package org.ibit.rol.form.admin.action.mascara;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.ibit.rol.form.admin.action.BaseAction;
import org.ibit.rol.form.admin.form.MascaraForm;
import org.ibit.rol.form.persistence.delegate.MascaraDelegate;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.ibit.rol.form.model.Mascara;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action para consultar una Mascara.
 *
 * @struts.action
 *  path="/admin/mascara/seleccion"
 *
 * @struts.action-forward
 *  name="success" path=".mascara.editar"
 *
 * @struts.action-forward
 *  name="fail" path=".mascara.lista"
 */
public class SeleccionMascaraAction extends BaseAction {

    protected static Log log = LogFactory.getLog(SeleccionMascaraAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.debug("Entramos en SeleccionMascara");

        MascaraDelegate mascaraDelegate = DelegateUtil.getMascaraDelegate();

        String idString = request.getParameter("id");
        if (idString == null || idString.length() == 0) {
            log.warn("El paràmetre id és null!!");
            return mapping.findForward("fail");
        }

        log.debug("Seleccionar la mascara " + idString);

        Long id = new Long(idString);
        Mascara mascara = mascaraDelegate.obtenerMascara(id);
        // Ficar dins el form.
        MascaraForm mForm = (MascaraForm) obtenerActionForm(mapping, request, "/admin/mascara/editar");
        mForm.setValues(mascara);

        return mapping.findForward("success");
    }

}
