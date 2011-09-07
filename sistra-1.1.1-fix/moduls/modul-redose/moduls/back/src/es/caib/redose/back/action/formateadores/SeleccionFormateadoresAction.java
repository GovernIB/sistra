package es.caib.redose.back.action.formateadores;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.redose.back.action.BaseAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action para consultar un Formateador.
 *
 * @struts.action
 *  path="/back/formateadores/seleccion"
 *
 * @struts.action-forward
 *  name="success" path=".formateadores.editar"
 *
 * @struts.action-forward
 *  name="fail" path=".formateadores.lista"
 */
public class SeleccionFormateadoresAction extends BaseAction{
    protected static Log log = LogFactory.getLog(SeleccionFormateadoresAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.info("Entramos en SeleccionFormateadores");

        String idString = request.getParameter("identificador");
        if (idString == null || idString.length() == 0) {
            log.warn("El paràmetre identificador és null!!");
            return mapping.findForward("fail");
        }

        log.info("Seleccionar el formateador " + idString);

        Long id = new Long(idString);
        guardarFormateador(mapping, request, id);

        return mapping.findForward("success");
    }
}
