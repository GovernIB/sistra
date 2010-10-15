package es.caib.sistra.back.action.dominio;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.sistra.back.action.BaseAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action para consultar un Dominio.
 *
 * @struts.action
 *  path="/back/dominio/seleccion"
 *
 * @struts.action-forward
 *  name="success" path=".dominio.editar"
 *
 * @struts.action-forward
 *  name="fail" path=".dominio.lista"
 */
public class SeleccionDominioAction extends BaseAction{
    protected static Log log = LogFactory.getLog(SeleccionDominioAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.info("Entramos en SeleccionDominio");

        String idString = request.getParameter("codigo");
        if (idString == null || idString.length() == 0) {
            log.warn("El paràmetre codigo és null!!");
            return mapping.findForward("fail");
        }

        log.info("Seleccionar el dominio " + idString);

        Long id = new Long(idString);
        guardarDominio(mapping, request, id);

        return mapping.findForward("success");
    }
}
