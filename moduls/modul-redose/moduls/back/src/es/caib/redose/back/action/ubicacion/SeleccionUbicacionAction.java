package es.caib.redose.back.action.ubicacion;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.redose.back.action.BaseAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action para consultar un Ubicacion.
 *
 * @struts.action
 *  path="/back/ubicacion/seleccion"
 *
 * @struts.action-forward
 *  name="success" path=".ubicacion.editar"
 *
 * @struts.action-forward
 *  name="fail" path=".ubicacion.lista"
 */
public class SeleccionUbicacionAction extends BaseAction{
    protected static Log log = LogFactory.getLog(SeleccionUbicacionAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.info("Entramos en SeleccionUbicacion");

        String idString = request.getParameter("codigo");
        if (idString == null || idString.length() == 0) {
            log.warn("El paràmetre codigo és null!!");
            return mapping.findForward("fail");
        }

        log.info("Seleccionar el ubicacion " + idString);

        Long id = new Long(idString);
        guardarUbicacion(mapping, request, id);

        return mapping.findForward("success");
    }
}
