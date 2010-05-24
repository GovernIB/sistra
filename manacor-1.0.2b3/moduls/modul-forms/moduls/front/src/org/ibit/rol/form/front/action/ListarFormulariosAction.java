package org.ibit.rol.form.front.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.ibit.rol.form.persistence.delegate.FormularioDelegate;
import org.ibit.rol.form.persistence.delegate.PerfilDelegate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Action que obtiene un listado de formularios y un listado de perfiles
 *
 *
 *	ELIMINAMOS ACCION DE LIST
 *
 * @  struts.action path="/list"
 *
 * @ struts.action-forward name="success" path="/list.jsp"
 */
public class ListarFormulariosAction extends BaseAction {

    protected static Log log = LogFactory.getLog(ListarFormulariosAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        FormularioDelegate formDelegate = DelegateUtil.getFormularioDelegate();
        List formularios = formDelegate.listarUltimasVersionesFormularios();
        request.setAttribute("formularios", formularios);

        PerfilDelegate perfilDelegate = DelegateUtil.getPerfilDelegate();
        List perfiles = perfilDelegate.listarPerfiles();
        request.setAttribute("perfiles", perfiles);

        return mapping.findForward("success");
    }
}
