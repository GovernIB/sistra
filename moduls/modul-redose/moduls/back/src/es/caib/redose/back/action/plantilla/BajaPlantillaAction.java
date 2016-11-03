package es.caib.redose.back.action.plantilla;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.redose.back.action.BaseAction;
import es.caib.redose.persistence.delegate.DelegateUtil;
import es.caib.redose.persistence.delegate.PlantillaDelegate;

/**
 * Action para preparar borrar una Plantilla.
 *
 * @struts.action
 *  name="plantillaForm"
 *  scope="session"
 *  validate="false"
 *  path="/back/plantilla/baja"
 *
 * @struts.action-forward
 *  name="success" path=".version.editar"
 */
public class BajaPlantillaAction extends BaseAction{
    protected static Log log = LogFactory.getLog(BajaPlantillaAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.info("Entramos en BajaPlantilla");
        String idString = request.getParameter("codigo");
        if (idString == null || idString.length() == 0) {
            log.warn("El paràmetre codigo és null!!");
            return mapping.findForward("fail");
        }

        PlantillaDelegate plantillaDelegate = DelegateUtil.getPlantillaDelegate();
        Long id = new Long(idString);
        
        // Comprobamos si se puede borrar
        if (!plantillaDelegate.puedoBorrarPlantilla(id)){
        	ActionErrors messages = new ActionErrors();
        	//messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.elementoNoPuedeBorrarse"));        	
        	messages.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.elementoNoPuedeBorrarse"));
        	saveErrors(request,messages);  
        	return mapping.findForward("success");
        }
        
        plantillaDelegate.borrarPlantilla(id);
        request.setAttribute("reloadMenu", "true");

        String idVersionString = request.getParameter("idVersion");
        if (idVersionString == null || idVersionString.length() == 0) {
            log.warn("El paràmetre idVersion és null");
            return mapping.findForward("fail");
        }

        Long idVersion = new Long(idVersionString);
        guardarVersion(mapping, request, idVersion);

        return mapping.findForward("success");
    }

}
