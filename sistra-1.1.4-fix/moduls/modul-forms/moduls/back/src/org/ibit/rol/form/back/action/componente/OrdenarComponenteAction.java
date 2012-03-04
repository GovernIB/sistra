package org.ibit.rol.form.back.action.componente;

import org.ibit.rol.form.back.action.BaseAction;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.ibit.rol.form.persistence.delegate.ComponenteDelegate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action para ordenar Componentes.
 *
 * @struts.action
 *  name="fakeForm"
 *  scope="request"
 *  validate="false"
 *  path="/back/componente/orden"
 *  parameter="orden"
 *
 * @struts.action
 *  name="fakeForm"
 *  scope="request"
 *  validate="false"
 *  path="/back/componente/cambiaorden"
 *  parameter="cambio"
 *
 * @struts.action-forward
 *  name="success" path="/componente/orden.jsp"
 *
 * @struts.action-forward
 *  name="cancel" path=".pantalla.editar"
 */

public class OrdenarComponenteAction extends BaseAction{
    protected static Log log = LogFactory.getLog(OrdenarComponenteAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.debug("Entramos en OrdenarComponente");
        ComponenteDelegate componenteDelegate = DelegateUtil.getComponenteDelegate();
        String param = mapping.getParameter();


        if (request.getParameter("idPantalla") != null){
            Long idPantalla = new Long(request.getParameter("idPantalla"));

            if (isCancelled(request)) {
                log.debug("isCancelled");
                return mapping.findForward("cancel");
            }

            if ("cambio".equals(param)) {
                Long codigo1 = new Long(request.getParameter("codigo1"));
                Long codigo2 = new Long(request.getParameter("codigo2"));
                componenteDelegate.cambiarOrden(codigo1, codigo2);
                request.setAttribute("reloadMenu", "true");
            }

            request.setAttribute("idPantalla", idPantalla);
            request.setAttribute("componenteOptions", componenteDelegate.listarComponentesPantalla(idPantalla));

            return mapping.findForward("success");
        }

        return mapping.findForward("fail");
    }

}
