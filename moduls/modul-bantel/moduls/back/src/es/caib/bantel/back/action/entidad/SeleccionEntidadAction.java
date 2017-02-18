package es.caib.bantel.back.action.entidad;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.bantel.back.action.BaseAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action para consultar Entidad.
 *
 * @struts.action
 *  path="/back/entidad/seleccion"
 *
 * @struts.action-forward
 *  name="success" path=".entidad.editar"
 *
 * @struts.action-forward
 *  name="fail" path=".entidad.lista"
 */
public class SeleccionEntidadAction extends BaseAction{
    protected static Log log = LogFactory.getLog(SeleccionEntidadAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

    	log.debug("Entramos en SeleccionEntidadAction");

        String idString = request.getParameter("identificador");
        if (idString == null || idString.length() == 0) {
            log.warn("El paràmetre identificador és null!!");
            return mapping.findForward("fail");
        }

        log.debug("Seleccionar " + idString);
       
        guardarEntidad(mapping, request, idString);

        request.setAttribute( "idReadOnly", new Boolean( true ) );
        request.setAttribute( "codigoEntidadError", idString );
        return mapping.findForward("success");
    }
}
