package org.ibit.rol.form.back.action.pantalla;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.ibit.rol.form.back.action.BaseAction;
import org.ibit.rol.form.model.Formulario;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.ibit.rol.form.persistence.delegate.PantallaDelegate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action para preparar borrar una Pantalla.
 *
 * @struts.action
 *  name="pantallaForm"
 *  scope="session"
 *  validate="false"
 *  path="/back/pantalla/baja"
 *
 * @struts.action-forward
 *  name="success" path=".formulario.editar"
 */
public class BajaPantallaAction extends BaseAction{
    protected static Log log = LogFactory.getLog(BajaPantallaAction.class);

    public ActionForward execute(ActionMapping       mapping,
                                 ActionForm          form,
                                 HttpServletRequest  request,
                                 HttpServletResponse response) throws Exception {

        log.debug("Entramos en BajaPantalla");
        String idString = request.getParameter("id");
        if (idString == null || idString.length() == 0) {
            log.warn("El paràmetre id és null!!");
            return mapping.findForward("fail");
        }

        PantallaDelegate pantallaDelegate = DelegateUtil.getPantallaDelegate();
        Long id = new Long(idString);
        pantallaDelegate.borrarPantalla(id);
        request.setAttribute("reloadMenu", "true");

        String idFormularioString = request.getParameter("idFormulario");
        if (idFormularioString == null || idFormularioString.length() == 0) {
            log.warn("El paràmetre idFormulario és null");
            return mapping.findForward("fail");
        }

        Long idFormulario = new Long(idFormularioString);
        Formulario formulario = guardarFormulario(mapping, request, idFormulario);
                
        return mapping.findForward("success");
    }

}
