package es.caib.redose.back.action.erroresGestorDocumental;

import es.caib.redose.back.action.BaseAction;
import es.caib.redose.back.form.ErroresGestorDocumentalForm;
import es.caib.redose.back.form.HistoricoForm;
import es.caib.redose.model.LogGestorDocumentalError;
import es.caib.redose.model.LogOperacion;
import es.caib.redose.persistence.delegate.DelegateUtil;
import es.caib.redose.persistence.delegate.LogGestorDocumentalErroresDelegate;
import es.caib.redose.persistence.delegate.LogOperacionDelegate;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action para editar un Ubicacion.
 *
 * @struts.action
 *  name="erroresGestorForm"
 *  scope="session"
 *  validate="true"
 *  input=".erroresGestorDocumental.editar"
 *  path="/back/erroresGestorDocumental/editar"
 *
 * @struts.action-forward
 *  name="reload" path=".erroresGestorDocumental.editar"
 *
 * @struts.action-forward
 *  name="success" path=".erroresGestorDocumental.editar"
 *
 * @struts.action-forward
 *  name="cancel" path=".erroresGestorDocumental.lista"
 *
 */
public class EditarErroresAction extends BaseAction{

    protected static Log log = LogFactory.getLog(EditarErroresAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.info("Entramos en EditarErroresAction");

        LogGestorDocumentalErroresDelegate logErrorDelegate = DelegateUtil.getLogErrorGestorDocumentalDelegate();
        ErroresGestorDocumentalForm errorForm = (ErroresGestorDocumentalForm) form;
        LogGestorDocumentalError logError = (LogGestorDocumentalError) errorForm.getValues();

        if (isCancelled(request)) {
            log.info("isCancelled");
            return mapping.findForward("cancel");
        }
        return mapping.findForward("reload");
    }

}
