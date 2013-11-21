package es.caib.redose.back.action.plantilla;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import es.caib.redose.back.action.BaseAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action para consultar una plantilla.
 *
 * @struts.action
 *  path="/back/plantilla/seleccion"
 *
 *
 * @struts.action-forward
 *  name="success" path=".plantilla.editar"
 *
 * @struts.action-forward
 *  name="fail" path=".plantilla.lista"
 *
 */
public class SeleccionPlantillaAction extends BaseAction{
    protected static Log log = LogFactory.getLog(SeleccionPlantillaAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.info("Entramos en SeleccionPlantilla");

        String idString = request.getParameter("codigo");
        if (idString == null || idString.length() == 0) {
            log.warn("El paràmetre codigo és null!!");
            return mapping.findForward("fail");
        }

        log.info("Seleccionar la plantilla " + idString);

        Long id = new Long(idString);
        guardarPlantilla(mapping, request, id);

        return mapping.findForward("success");
    }



}
