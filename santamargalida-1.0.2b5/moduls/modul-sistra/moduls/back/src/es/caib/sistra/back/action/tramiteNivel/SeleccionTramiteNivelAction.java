package es.caib.sistra.back.action.tramiteNivel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import es.caib.sistra.back.action.BaseAction;
import es.caib.sistra.model.TramiteNivel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action para consultar una tramiteNivel.
 *
 * @struts.action
 *  path="/back/tramiteNivel/seleccion"
 *
 *
 * @struts.action-forward
 *  name="success" path=".tramiteNivel.editar"
 *
 * @struts.action-forward
 *  name="fail" path=".tramiteNivel.lista"
 *
 */
public class SeleccionTramiteNivelAction extends BaseAction{
    protected static Log log = LogFactory.getLog(SeleccionTramiteNivelAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.info("Entramos en SeleccionTramiteNivel");

        String idString = request.getParameter("codigo");
        if (idString == null || idString.length() == 0) {
            log.warn("El paràmetre codigo és null!!");
            return mapping.findForward("fail");
        }

        log.info("Seleccionar la tramiteNivel " + idString);

        Long id = new Long(idString);
        TramiteNivel tn = guardarTramiteNivel(mapping, request, id);
        
        // Establecemos bloqueo
        this.setBloqueado(request,tn.getTramiteVersion().getBloqueadoModificacion(),tn.getTramiteVersion().getBloqueadoModificacionPor());
        
        return mapping.findForward("success");
    }



}
