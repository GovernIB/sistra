package es.caib.redose.back.action.modelo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.redose.back.action.BaseAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action para consultar un Modelo.
 *
 * @struts.action
 *  path="/back/modelo/seleccion"
 *
 * @struts.action-forward
 *  name="success" path=".modelo.editar"
 *
 * @struts.action-forward
 *  name="fail" path=".modelo.lista"
 */
public class SeleccionModeloAction extends BaseAction{
    protected static Log log = LogFactory.getLog(SeleccionModeloAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.info("Entramos en SeleccionModelo");

        String idString = request.getParameter("codigo");
        if (idString == null || idString.length() == 0) {
            log.warn("El paràmetre codigo és null!!");
            return mapping.findForward("fail");
        }

        log.info("Seleccionar el modelo " + idString);

        Long id = new Long(idString);
        guardarModelo(mapping, request, id);

        return mapping.findForward("success");
    }
}
