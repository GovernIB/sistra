package es.caib.redose.back.action.plantilla;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import es.caib.redose.back.action.BaseAction;
import es.caib.redose.back.form.PlantillaForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action para preparar el alta de una Plantilla.
 *
 * @struts.action
 *  path="/back/plantilla/alta"
 *
 * @struts.action-forward
 *  name="success" path=".plantilla.editar"
 *
 * @struts.action-forward
 *  name="fail" path=".version.lista"
 *
 */
public class AltaPlantillaAction extends BaseAction{
    protected static Log log = LogFactory.getLog(AltaPlantillaAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        String idVersionString = request.getParameter("idVersion");
        if (idVersionString == null || idVersionString.length() == 0) {
            log.info("idVersion es null");
            return mapping.findForward("fail");
        }

        Long idVersion = new Long(idVersionString);


        PlantillaForm plantillaForm = (PlantillaForm) obtenerActionForm(mapping,request, "/back/plantilla/editar");
        plantillaForm.destroy(mapping, request);

        plantillaForm.setIdVersion(idVersion);
        return mapping.findForward("success");
    }
}
