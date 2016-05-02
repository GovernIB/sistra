package es.caib.bantel.back.action.ficheroExportacion;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.bantel.back.action.BaseAction;
import es.caib.bantel.persistence.delegate.DelegateUtil;
import es.caib.bantel.persistence.delegate.FicheroExportacionDelegate;

/**
 * Action para preparar borrar un Fichero exportacion.
 *
 * @struts.action
 *  name="ficheroExportacionForm"
 *  scope="session"
 *  validate="false"
 *  path="/back/ficheroExportacion/baja"
 *
 * @struts.action-forward
 *  name="success" path=".ficheroExportacion.lista"
 */
public class BajaFicheroExportacionAction extends BaseAction{
    protected static Log log = LogFactory.getLog(BajaFicheroExportacionAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        FicheroExportacionDelegate delegate = DelegateUtil.getFicheroExportacionDelegate();

        String idString = request.getParameter("codigo");
        if (idString == null || idString.length() == 0) {
            log.warn("El paràmetre codigo és null!!");
            return mapping.findForward("fail");
        }

        delegate.borrarFicheroExportacion(idString);
        //request.setAttribute("reloadMenu", "true");

        return mapping.findForward("success");
    }
}
