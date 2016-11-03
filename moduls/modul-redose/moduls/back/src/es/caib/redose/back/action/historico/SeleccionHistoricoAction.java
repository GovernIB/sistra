package es.caib.redose.back.action.historico;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.redose.back.action.BaseAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action para consultar un Historico.
 *
 * @struts.action
 *  path="/back/historico/seleccion"
 *
 * @struts.action-forward
 *  name="success" path=".historico.editar"
 *
 * @struts.action-forward
 *  name="fail" path=".historico.lista"
 */
public class SeleccionHistoricoAction extends BaseAction{
    protected static Log log = LogFactory.getLog(SeleccionHistoricoAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.info("Entramos en SeleccionHistorico");

        String idString = request.getParameter("codigo");
        if (idString == null || idString.length() == 0) {
            log.warn("El paràmetre codigo és null!!");
            return mapping.findForward("fail");
        }

        log.info("Seleccionar el historico " + idString);

        Long id = new Long(idString);
        guardarHistorico(mapping, request, id);

        return mapping.findForward("success");
    }
}
