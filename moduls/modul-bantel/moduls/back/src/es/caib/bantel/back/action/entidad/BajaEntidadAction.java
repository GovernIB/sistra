package es.caib.bantel.back.action.entidad;

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
import es.caib.bantel.persistence.delegate.EntidadDelegate;

/**
 * Action para preparar borrar Entidad.
 *
 * @struts.action
 *  name="entidadForm"
 *  scope="session"
 *  validate="false"
 *  path="/back/entidad/baja"
 *
 * @struts.action-forward
 *  name="success" path=".entidad.lista"
 */
public class BajaEntidadAction extends BaseAction{
    protected static Log log = LogFactory.getLog(BajaEntidadAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.debug("Entramos en BajaEntidadAction");
        EntidadDelegate dlg = DelegateUtil.getEntidadDelegate();

        String idString = request.getParameter("codigo");
        if (idString == null || idString.length() == 0) {
            log.warn("El paràmetre codigo és null!!");
            return mapping.findForward("fail");
        }
        
        if (!dlg.puedoBorrarEntidad(idString)){
        	ActionErrors messages = new ActionErrors();
        	messages.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.entidadNoPuedeBorrarse"));
        	saveErrors(request,messages);  
        	return mapping.findForward("success");
        }
        
        dlg.borrarEntidad(idString);

        return mapping.findForward("success");
    }
}
