package es.caib.bantel.back.action.fuenteDatos;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.bantel.back.action.BaseAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action para consultar fuente datos.
 *
 * @struts.action
 *  path="/back/fuenteDatos/seleccion"
 *
 * @struts.action-forward
 *  name="success" path=".fuenteDatos.editar"
 *
 * @struts.action-forward
 *  name="fail" path=".fuenteDatos.lista"
 */
public class SeleccionFuenteDatosAction extends BaseAction{
    protected static Log log = LogFactory.getLog(SeleccionFuenteDatosAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

    	String idString = request.getParameter("identificador");
        if (idString == null || idString.length() == 0) {
            log.warn("El paràmetre codigo és null!!");
            return mapping.findForward("fail");
        }
       
        guardarFuenteDatos(mapping, request, idString);

        return mapping.findForward("success");
    }
}
