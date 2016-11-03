package es.caib.sistra.back.action.tramiteVersion;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import es.caib.sistra.back.action.BaseAction;
import es.caib.sistra.model.TramiteVersion;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action para consultar una tramiteVersion.
 *
 * @struts.action
 *  path="/back/tramiteVersion/seleccion"
 *
 *
 * @struts.action-forward
 *  name="success" path=".tramiteVersion.editar"
 *
 * @struts.action-forward
 *  name="fail" path=".tramiteVersion.lista"
 *
 */
public class SeleccionTramiteVersionAction extends BaseAction{
    protected static Log log = LogFactory.getLog(SeleccionTramiteVersionAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.info("Entramos en SeleccionTramiteVersion");

        String idString = request.getParameter("codigo");
        if (idString == null || idString.length() == 0) {
            log.warn("El paràmetre codigo és null!!");
            return mapping.findForward("fail");
        }

        log.info("Seleccionar la tramiteVersion " + idString);

        Long id = new Long(idString);
        TramiteVersion tramiteVersion = guardarTramiteVersion(mapping, request, id);
        
        // Establecemos bloqueo
        this.setBloqueado(request,tramiteVersion.getBloqueadoModificacion(),tramiteVersion.getBloqueadoModificacionPor());
                
        return mapping.findForward("success");
    }



}
