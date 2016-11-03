package es.caib.sistra.back.action.mensajePlataforma;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import es.caib.sistra.back.action.BaseAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action para consultar una mensajePlataforma.
 *
 * @struts.action
 *  path="/back/mensajePlataforma/seleccion"
 *
 * @struts.action-forward
 *  name="success" path=".mensajePlataforma.editar"
 *
 * @struts.action-forward
 *  name="fail" path=".mensajePlataforma.lista"
 *
 */
public class SeleccionMensajePlataformaAction extends BaseAction{
    protected static Log log = LogFactory.getLog(SeleccionMensajePlataformaAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.info("Entramos en SeleccionMensajePlataforma");

        String idString = request.getParameter("codigo");
        if (idString == null || idString.length() == 0) {
            log.warn("El paràmetre codigo és null!!");
            return mapping.findForward("fail");
        }

        log.info("Seleccionar la mensajePlataforma " + idString);

        Long id = new Long(idString);
        guardarMensajePlataforma(mapping, request, id);

        return mapping.findForward("success");
    }



}
