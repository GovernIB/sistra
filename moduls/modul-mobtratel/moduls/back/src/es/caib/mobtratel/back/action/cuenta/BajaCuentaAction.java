package es.caib.mobtratel.back.action.cuenta;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.mobtratel.back.action.BaseAction;
import es.caib.mobtratel.persistence.delegate.CuentaDelegate;
import es.caib.mobtratel.persistence.delegate.DelegateUtil;


/**
 * Action para preparar borrar una Cuenta.
 *
 * @struts.action
 *  name="cuentaForm"
 *  scope="session"
 *  validate="false"
 *  path="/back/cuenta/baja"
 *
 * @struts.action-forward
 *  name="success" path=".cuenta.lista"
 */
public class BajaCuentaAction extends BaseAction{
    protected static Log log = LogFactory.getLog(BajaCuentaAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.info("Entramos en BajaCuenta");
        String idString = request.getParameter("codigo");
        if (idString == null || idString.length() == 0) {
            log.warn("El paràmetre codigo és null!!");
            return mapping.findForward("fail");
        }

        
        CuentaDelegate cuentaDelegate = DelegateUtil.getCuentaDelegate();

        if (!cuentaDelegate.puedoBorrarCuenta(idString)){
        	ActionErrors messages = new ActionErrors();
        	//messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.elementoNoPuedeBorrarse"));        	
        	messages.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.cuentaNoPuedeBorrarse"));
        	saveErrors(request,messages);  
        	return mapping.findForward("success");
        }
        
        cuentaDelegate.borrarCuenta(idString);
        //request.setAttribute("reloadMenu", "true");
          
         

        return mapping.findForward("success");
    }
}
