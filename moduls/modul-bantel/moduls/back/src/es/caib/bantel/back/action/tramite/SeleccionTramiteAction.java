package es.caib.bantel.back.action.tramite;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.bantel.back.action.BaseAction;
import es.caib.bantel.back.util.MensajesUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action para consultar un Tramite.
 *
 * @struts.action
 *  path="/back/tramite/seleccion"
 *
 * @struts.action-forward
 *  name="success" path=".tramite.editar"
 *
 * @struts.action-forward
 *  name="fail" path=".tramite.lista"
 */
public class SeleccionTramiteAction extends BaseAction{
    protected static Log log = LogFactory.getLog(SeleccionTramiteAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

    	log.debug("Entramos en SeleccionTramite");
    	MensajesUtil.setMsg(this.getResources(request));

        String idString = request.getParameter("codigo");
        if (idString == null || idString.length() == 0) {
            log.warn("El paràmetre codigo és null!!");
            return mapping.findForward("fail");
        }

        Long id = new Long(idString);
        
        log.debug("Seleccionar el tramite " + id);
       
        guardarTramite(mapping, request, id);

        request.setAttribute( "idReadOnly", "true" );
        request.setAttribute( "codigoTramiteError", idString );
        return mapping.findForward("success");
    }
}
