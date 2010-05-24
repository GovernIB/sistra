package org.ibit.rol.form.back.action.formulario;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.ibit.rol.form.back.action.BaseAction;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.ibit.rol.form.persistence.delegate.FormularioDelegate;

/**
 * Action para preparar borrar un Formulario.
 *
 * @struts.action
 *  name="formularioForm"
 *  scope="session"
 *  validate="false"
 *  path="/back/formulario/baja"
 *
 * @struts.action-forward
 *  name="success" path=".formulario.lista"
 *
 *  @struts.action-forward
 *  name="fail" path=".formulario.lista"
 */
public class BajaFormularioAction extends BaseAction{
    protected static Log log = LogFactory.getLog(BajaFormularioAction.class);

    public ActionForward execute(ActionMapping       mapping, 
                                 ActionForm          form,
                                 HttpServletRequest  request,
                                 HttpServletResponse response) throws Exception {

        log.debug("Entramos en BajaFormulario");
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
