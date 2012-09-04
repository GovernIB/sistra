package org.ibit.rol.form.admin.action.validador;

import org.ibit.rol.form.admin.action.BaseAction;
import org.ibit.rol.form.admin.form.ValidadorFirmaForm;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.ibit.rol.form.persistence.delegate.ValidadorFirmaDelegate;
import org.ibit.rol.form.model.ValidadorFirma;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action para consultar un Validador Firma.
 *
 * @struts.action
 *  path="/admin/validador/seleccion"
 *
 * @struts.action-forward
 *  name="success" path=".validadorfirma.editar"
 *
 * @struts.action-forward
 *  name="fail" path=".validadorfirma.lista"
 */
public class SeleccionValidadorAction extends BaseAction {
     protected static Log log = LogFactory.getLog(SeleccionValidadorAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.debug("Entramos en SeleccionValidador");

        ValidadorFirmaDelegate validadorDelegate = DelegateUtil.getValidadorFirmaDelegate();

        String idString = request.getParameter("id");
        if (idString == null || idString.length() == 0) {
            log.warn("El paràmetre id és null!!");
            return mapping.findForward("fail");
        }

        log.debug("Seleccionar el validador firma " + idString);

        Long id = new Long(idString);
        ValidadorFirma validador = validadorDelegate.obtenerValidadorFirma(id);
        // Ficar dins el form.
        ValidadorFirmaForm vForm = (ValidadorFirmaForm) obtenerActionForm(mapping, request, "/admin/validador/editar");
        vForm.setValues(validador);

        return mapping.findForward("success");
    }
}
