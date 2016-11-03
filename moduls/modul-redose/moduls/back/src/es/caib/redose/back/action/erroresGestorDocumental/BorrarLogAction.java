package es.caib.redose.back.action.erroresGestorDocumental;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.redose.back.action.BaseAction;
import es.caib.redose.persistence.delegate.DelegateUtil;
import es.caib.redose.persistence.delegate.LogGestorDocumentalErroresDelegate;

/**
 * Action para borrar un error del historico.
 *
 * @struts.action
 *  path="/back/erroresGestorDocumental/borrarLog"
 *
 * @struts.action-forward
 *  name="success" path=".erroresGestorDocumental.lista"
 *
 * @struts.action-forward
 *  name="fail" path=".erroresGestorDocumental.lista"
 */
public class BorrarLogAction extends BaseAction{
    protected static Log log = LogFactory.getLog(BorrarLogAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.info("Entramos en BorrarLogAction");
        try{
	        LogGestorDocumentalErroresDelegate delegate = DelegateUtil.getLogErrorGestorDocumentalDelegate();
	        delegate.borrarLogGestorDocumentalErrores();
	        return mapping.findForward("success");
        }catch(Exception ex){
        	return mapping.findForward("fail");
        }
    }
}
