package es.caib.redose.back.action.erroresGestorDocumental;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.redose.back.action.BaseAction;
import es.caib.redose.model.LogGestorDocumentalError;
import es.caib.redose.persistence.delegate.DelegateUtil;
import es.caib.redose.persistence.delegate.LogGestorDocumentalErroresDelegate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action para borrar un error del historico.
 *
 * @struts.action
 *  path="/back/erroresGestorDocumental/borrar"
 *
 * @struts.action-forward
 *  name="success" path=".erroresGestorDocumental.lista"
 *
 * @struts.action-forward
 *  name="fail" path=".erroresGestorDocumental.lista"
 */
public class BorrarErrorAction extends BaseAction{
    protected static Log log = LogFactory.getLog(BorrarErrorAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.info("Entramos en BorrarErrorAction");
        try{
	        String idString = request.getParameter("codigo");
	        if (idString == null || idString.length() == 0) {
	            log.warn("El paràmetre codigo és null!!");
	            return mapping.findForward("fail");
	        }
	
	        log.info("Borrar el error " + idString);
	
	        Long id = new Long(idString);
	        LogGestorDocumentalErroresDelegate delegate = DelegateUtil.getLogErrorGestorDocumentalDelegate();
	        delegate.borrarLogGestorDocumentalError(id);
	
	        return mapping.findForward("success");
        }catch(Exception ex){
        	return mapping.findForward("fail");
        }
    }
}
