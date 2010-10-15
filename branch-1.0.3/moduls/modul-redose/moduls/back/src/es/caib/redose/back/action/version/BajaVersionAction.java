package es.caib.redose.back.action.version;

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
import es.caib.redose.persistence.delegate.VersionDelegate;

/**
 * Action para preparar borrar una Version.
 *
 * @struts.action
 *  name="versionForm"
 *  scope="session"
 *  validate="false"
 *  path="/back/version/baja"
 *
 * @struts.action-forward
 *  name="success" path=".modelo.editar"
 */
public class BajaVersionAction extends BaseAction{
    protected static Log log = LogFactory.getLog(BajaVersionAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.info("Entramos en BajaVersion");
        String idString = request.getParameter("codigo");
        if (idString == null || idString.length() == 0) {
            log.warn("El paràmetre codigo és null!!");
            return mapping.findForward("fail");
        }

        VersionDelegate versionDelegate = DelegateUtil.getVersionDelegate();
        Long id = new Long(idString);
        
        // Comprobamos si se puede borrar
        if (!versionDelegate.puedoBorrarVersion(id)){
        	ActionErrors messages = new ActionErrors();
        	//messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.elementoNoPuedeBorrarse"));        	
        	messages.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.elementoNoPuedeBorrarse"));
        	saveErrors(request,messages);  
        	return mapping.findForward("success");
        }
        
        
        versionDelegate.borrarVersion(id);
        request.setAttribute("reloadMenu", "true");

        String idModeloString = request.getParameter("idModelo");
        if (idModeloString == null || idModeloString.length() == 0) {
            log.warn("El paràmetre idModelo és null");
            return mapping.findForward("fail");
        }

        Long idModelo = new Long(idModeloString);
        guardarModelo(mapping, request, idModelo);

        return mapping.findForward("success");
    }

}
