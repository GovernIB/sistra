package es.caib.redose.back.action.historico;

import es.caib.redose.back.action.BaseAction;
import es.caib.redose.back.form.HistoricoForm;
import es.caib.redose.model.LogOperacion;
import es.caib.redose.persistence.delegate.DelegateUtil;
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
 *  name="historicoForm"
 *  scope="session"
 *  validate="true"
 *  input=".historico.editar"
 *  path="/back/historico/editar"
 *
 * @struts.action-forward
 *  name="reload" path=".historico.editar"
 *
 * @struts.action-forward
 *  name="success" path=".historico.editar"
 *
 * @struts.action-forward
 *  name="cancel" path=".historico.lista"
 *
 */
public class EditarHistoricoAction extends BaseAction{

    protected static Log log = LogFactory.getLog(EditarHistoricoAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.info("Entramos en EditarHistorico");

        LogOperacionDelegate historicoDelegate = DelegateUtil.getLogOperacionDelegate();
        HistoricoForm historicoForm = (HistoricoForm) form;
        LogOperacion historico = (LogOperacion) historicoForm.getValues();

        if (isCancelled(request)) {
            log.info("isCancelled");
            return mapping.findForward("cancel");
        }
        return mapping.findForward("reload");
    }

}
