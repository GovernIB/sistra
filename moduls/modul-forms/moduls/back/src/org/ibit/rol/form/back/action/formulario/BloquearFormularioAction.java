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
 * Action para preparar desbloquear un Formulario.
 *
 * @struts.action
 *  path="/back/formulario/bloquear"
 *
 * @struts.action-forward
 *  name="success" path=".formulario.lista"
 *
 * @struts.action-forward
 *  name="fail" path=".formulario.lista"
 *
 * @struts.action-forward
 * name="cancel" path=".formulario.lista"  
 */

public class BloquearFormularioAction extends BaseAction {
    protected static Log log = LogFactory.getLog(BloquearFormularioAction.class);

    public ActionForward execute(ActionMapping       mapping,
                                 ActionForm          form,
                                 HttpServletRequest  request,
                                 HttpServletResponse response) throws Exception {

        log.debug("Entramos en BloquearFormulario");
        if (isCancelled(request)) {
            log.debug("isCancelled");
            return mapping.findForward("cancel");
        }
        //       
        String idString = request.getParameter("id");
        if ( (idString == null) || (idString.length() == 0) ) {
            log.warn("El paràmetre id és null!!");
            return mapping.findForward("fail");
        }
        //
        Long id = new Long(idString);
        FormularioDelegate formularioDelegate = DelegateUtil.getFormularioDelegate();
        formularioDelegate.bloquearFormulario(id);
        //
      //  return mapping.findForward("success");
        
        // Redirigimos a pantalla de seleccion
        response.sendRedirect(request.getContextPath() + "/back/formulario/seleccion.do?id="+idString);
        return null;
    }
}



