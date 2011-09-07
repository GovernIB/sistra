package org.ibit.rol.form.back.action.pantalla;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.ibit.rol.form.back.action.BaseAction;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.ibit.rol.form.persistence.delegate.PantallaDelegate;

/**
 * Action para ordenar Pantallas.
 *
 * @struts.action
 *  name="pantallaForm"
 *  scope="session"
 *  validate="false"
 *  path="/back/pantalla/orden"
 *  parameter="orden"
 *
 * @struts.action
 *  name="pantallaForm"
 *  scope="session"
 *  validate="false"
 *  path="/back/pantalla/cambiaorden"
 *  parameter="cambio"
 *
 * @struts.action-forward
 *  name="success" path="/pantalla/orden.jsp"
 *
 * @struts.action-forward
 *  name="cancel" path=".formulario.editar"
 *
 */

public class OrdenarPantallaAction extends BaseAction{
    protected static Log log = LogFactory.getLog(OrdenarPantallaAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.debug("Entramos en OrdenarPantalla");
        PantallaDelegate pantallaDelegate = DelegateUtil.getPantallaDelegate();
        String param = mapping.getParameter();

        if (isCancelled(request)) {
            return mapping.findForward("cancel");
        }

        if (request.getParameter("idFormulario") != null){
            Long formulario = new Long(request.getParameter("idFormulario").toString());

            if ("cambio".equals(param)) {
                Long codigo1 = new Long(request.getParameter("codigo1"));
                Long codigo2 = new Long(request.getParameter("codigo2"));
                pantallaDelegate.cambiarOrden(codigo1, codigo2);
                request.setAttribute("reloadMenu", "true");
            }

            request.setAttribute("idFormulario", formulario);
            request.setAttribute("pantallaOptions", pantallaDelegate.listarPantallasFormulario(formulario));

            return mapping.findForward("success");
        }

        return mapping.findForward("fail");
    }

}
