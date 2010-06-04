package es.caib.sistra.back.action.mensajeTramite;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import es.caib.sistra.back.action.BaseAction;
import es.caib.sistra.model.MensajeTramite;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action para consultar una mensajeTramite.
 *
 * @struts.action
 *  path="/back/mensajeTramite/seleccion"
 *
 *
 * @struts.action-forward
 *  name="success" path=".mensajeTramite.editar"
 *
 * @struts.action-forward
 *  name="fail" path=".mensajeTramite.lista"
 *
 */
public class SeleccionMensajeTramiteAction extends BaseAction{
    protected static Log log = LogFactory.getLog(SeleccionMensajeTramiteAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.info("Entramos en SeleccionMensajeTramite");

        String idString = request.getParameter("codigo");
        if (idString == null || idString.length() == 0) {
            log.warn("El paràmetre codigo és null!!");
            return mapping.findForward("fail");
        }

        log.info("Seleccionar la mensajeTramite " + idString);

        Long id = new Long(idString);
        MensajeTramite mt = guardarMensajeTramite(mapping, request, id);
        // Establecemos bloqueo
        this.setBloqueado(request,mt.getTramiteVersion().getBloqueadoModificacion(),mt.getTramiteVersion().getBloqueadoModificacionPor());
        
        return mapping.findForward("success");
    }



}
