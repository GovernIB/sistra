package es.caib.mobtratel.back.action.cuenta;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.mobtratel.back.action.BaseAction;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action para consultar una Cuenta.
 *
 * @struts.action
 *  path="/back/cuenta/seleccion"
 *
 * @struts.action-forward
 *  name="success" path=".cuenta.editar"
 *
 * @struts.action-forward
 *  name="fail" path=".cuenta.lista"
 */
public class SeleccionCuentaAction extends BaseAction{
    protected static Log log = LogFactory.getLog(SeleccionCuentaAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

    	log.info("Entramos en SeleccionCuenta");

        String idString = request.getParameter("codigo");
        if (idString == null || idString.length() == 0) {
            log.warn("El paràmetre codigo és null!!");
            return mapping.findForward("fail");
        }

        log.info("Seleccionar la cuenta " + idString);
       
        guardarCuenta(mapping, request, idString);

        request.setAttribute( "idReadOnly", new Boolean( true ) );
        
        return mapping.findForward("success");
    }
}
