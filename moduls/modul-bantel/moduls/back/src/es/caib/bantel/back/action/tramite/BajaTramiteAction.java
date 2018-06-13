package es.caib.bantel.back.action.tramite;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.bantel.back.action.BaseAction;
import es.caib.bantel.persistence.delegate.DelegateUtil;
import es.caib.bantel.persistence.delegate.ProcedimientoDelegate;

/**
 * Action para preparar borrar un Tramite.
 *
 * @struts.action
 *  name="tramiteForm"
 *  scope="session"
 *  validate="false"
 *  path="/back/tramite/baja"
 *
 * @struts.action-forward
 *  name="success" path=".tramite.lista"
 */
public class BajaTramiteAction extends BaseAction{
    protected static Log log = LogFactory.getLog(BajaTramiteAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.debug("Entramos en BajaTramite");
        ProcedimientoDelegate tramiteDelegate = DelegateUtil.getTramiteDelegate();

        String idString = request.getParameter("codigo");
        if (idString == null || idString.length() == 0) {
            log.warn("El paràmetre codigo és null!!");
            return mapping.findForward("fail");
        }
        
        Long id = new Long(idString);
        
        if (!tramiteDelegate.puedoBorrarProcedimiento(id)){
        	ActionErrors messages = new ActionErrors();
        	//messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.elementoNoPuedeBorrarse"));        	
        	messages.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.tramiteNoPuedeBorrarse"));
        	saveErrors(request,messages);  
        	return mapping.findForward("success");
        }
        
        tramiteDelegate.borrarProcedimiento(id);
        //request.setAttribute("reloadMenu", "true");

        return mapping.findForward("success");
    }
}
