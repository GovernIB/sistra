package org.ibit.rol.form.back.action.valorposible;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.ibit.rol.form.back.action.BaseAction;

/**
 * Action para consultar un valor posible.
 *
 * @struts.action
 *  path="/back/valorposible/seleccion"
 *
 * @struts.action-forward
 *  name="success" path=".valorposible.editar"
 *
 * @struts.action-forward
 *  name="fail" path=".pantalla.lista"
 *
 */
public class SeleccionValorPosibleAction extends BaseAction {

    protected static Log log = LogFactory.getLog(SeleccionValorPosibleAction.class);

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {

        String idString = request.getParameter("id");
        if (idString == null || idString.length() == 0) {
            log.warn("El paràmetre id és null!!");
            return mapping.findForward("fail");
        }

        log.debug("Seleccionar el valor " + idString);

        Long id = new Long(idString);

        guardarValorPosible(mapping, request, id);
        return mapping.findForward("success");
    }

}
