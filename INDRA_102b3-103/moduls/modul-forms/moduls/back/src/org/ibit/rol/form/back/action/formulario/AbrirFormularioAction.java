package org.ibit.rol.form.back.action.formulario;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.ibit.rol.form.back.action.BaseAction;
import org.ibit.rol.form.model.Formulario;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;

/**
 * Action para consultar un Formulario.
 *
 * @struts.action
 *  path="/back/formulario/abrirFormulario"
 *
 * 
 */
public class AbrirFormularioAction extends BaseAction{
    protected static Log log = LogFactory.getLog(AbrirFormularioAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.debug("Entramos en AbrirFormularioAction");
        String modelo = request.getParameter("modelo");
        int version = Integer.parseInt(request.getParameter("version"));
        log.debug("Abrir el formulario: " + modelo + " - " + version);
        Formulario f = DelegateUtil.getFormularioDelegate().obtenerFormulario(modelo,version);
        response.sendRedirect(request.getContextPath() + "/back/formulario/seleccion.do?id="+f.getId());
        return null;
    }
}
