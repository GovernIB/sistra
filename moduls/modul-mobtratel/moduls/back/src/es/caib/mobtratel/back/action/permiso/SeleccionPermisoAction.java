package es.caib.mobtratel.back.action.permiso;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.mobtratel.back.action.BaseAction;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action para consultar un Permiso de envio.
 *
 * @struts.action
 *  path="/back/permiso/seleccion"
 *
 * @struts.action-forward
 *  name="success" path=".permiso.editar"
 *
 * @struts.action-forward
 *  name="fail" path=".permiso.lista"
 */
public class SeleccionPermisoAction extends BaseAction{
    protected static Log log = LogFactory.getLog(SeleccionPermisoAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.info("Entramos en SeleccionPermiso");

        String idString = request.getParameter("codigo");
        if (idString == null || idString.length() == 0) {
            log.warn("El paràmetre codigo és null!!");
            return mapping.findForward("fail");
        }

        log.info("Seleccionar el permiso " + idString);

        guardarPermiso(mapping, request, Long.valueOf(idString));
        
        request.setAttribute( "idReadOnly", new Boolean( true ) );

        return mapping.findForward("success");
    }
}
