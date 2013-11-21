package es.caib.redose.back.action.formateadores;

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
import es.caib.redose.persistence.delegate.FormateadorDelegate;
import es.caib.redose.persistence.delegate.UbicacionDelegate;

/**
 * Action para preparar borrar un formateador
 *
 * @struts.action
 *  name="formateadoresForm"
 *  scope="session"
 *  validate="false"
 *  path="/back/formateadores/baja"
 *
 *  @struts.action-forward
 *  name="fail" path=".formateadores.lista"
 *
 * @struts.action-forward
 *  name="success" path=".formateadores.lista"
 */
public class BajaFormateadoresAction extends BaseAction{
    protected static Log log = LogFactory.getLog(BajaFormateadoresAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.info("Entramos en BajaFormateadores");
        FormateadorDelegate fDelegate = DelegateUtil.getFormateadorDelegate();

        String idString = request.getParameter("identificador");
        if (idString == null || idString.length() == 0) {
            log.warn("El paràmetre identificador és null!!");
            return mapping.findForward("fail");
        }

        Long id = new Long(idString);
        if(!fDelegate.puedoBorrarFormateador(id)){
        	ActionErrors messages = new ActionErrors();
        	messages.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.elementoNoPuedeBorrarse"));
        	saveErrors(request,messages);  
        	return mapping.findForward("success");
        }
        fDelegate.borrarFormateador(id);
        request.setAttribute("reloadMenu", "true");
        
        return mapping.findForward("success");
    }
}
