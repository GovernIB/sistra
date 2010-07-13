package org.ibit.rol.form.back.action.formularioseguro;

import org.ibit.rol.form.back.action.BaseAction;
import org.ibit.rol.form.persistence.delegate.FormularioDelegate;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action para preparar borrar un Formulario Seguro.
 *
 * @struts.action
 *  name="formularioSeguroForm"
 *  scope="session"
 *  validate="false"
 *  path="/back/formularioseguro/baja"
 *
 * @struts.action-forward
 *  name="success" path=".formulario.lista"
 */
public class BajaFormularioSeguroAction extends BaseAction {
    protected static Log log = LogFactory.getLog(BajaFormularioSeguroAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.debug("Entramos en BajaFormularioSeguro");
        FormularioDelegate formularioDelegate = DelegateUtil.getFormularioDelegate();

        String idString = request.getParameter("id");
        if (idString == null || idString.length() == 0) {
            log.warn("El paràmetre id és null!!");
            return mapping.findForward("fail");
        }

        Long id = new Long(idString);
        formularioDelegate.borrarFormulario(id);
        request.setAttribute("reloadMenu", "true");

        return mapping.findForward("success");
    }

}
