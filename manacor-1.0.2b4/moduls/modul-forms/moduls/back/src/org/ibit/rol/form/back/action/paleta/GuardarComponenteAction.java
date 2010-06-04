package org.ibit.rol.form.back.action.paleta;

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
import javax.servlet.http.HttpSession;

/**
 * Action para asociar un componente de una paleta a una pantalla
 *
 * @struts.action
 *  path="/back/componente/guarda"
 *
 * @struts.action-forward
 *  name="success" path=".pantalla.editar"
 *
 * @struts.action-forward
 *  name="cancel" path="/main.do"
 */
public class GuardarComponenteAction extends BaseAction{
    protected static Log log = LogFactory.getLog(GuardarComponenteAction.class);

      public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                   HttpServletResponse response) throws Exception {

          HttpSession session = request.getSession();
          log.debug("Entramos en guardar componente");
          ComponenteDelegate delegate = DelegateUtil.getComponenteDelegate();

          Long idComponente = new Long(request.getParameter("id"));
          Long idPantalla = (Long) session.getAttribute("idPantalla");

          delegate.copiarComponente(idComponente,idPantalla);
          request.setAttribute("reloadMenu", "true");

          return mapping.findForward("success");
      }

}
