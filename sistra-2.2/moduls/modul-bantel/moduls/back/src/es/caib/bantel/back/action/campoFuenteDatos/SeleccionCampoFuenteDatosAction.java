package es.caib.bantel.back.action.campoFuenteDatos;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.bantel.back.action.BaseAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action para consultar una campoFuenteDatos.
 *
 * @struts.action
 *  path="/back/campoFuenteDatos/seleccion"
 *
 *
 * @struts.action-forward
 *  name="success" path=".campoFuenteDatos.editar"
 *
 * @struts.action-forward
 *  name="fail" path=".campoFuenteDatos.lista"
 *
 */
public class SeleccionCampoFuenteDatosAction extends BaseAction{
    protected static Log log = LogFactory.getLog(SeleccionCampoFuenteDatosAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.info("Entramos en SeleccionVersion");

        String idString = request.getParameter("identificador");
        if (idString == null || idString.length() == 0) {
            log.warn("El paràmetre identificador és null!!");
            return mapping.findForward("fail");
        }

       // Codigo: idfuentedatos#idcampo
        String[] ids = idString.split("#");
        guardarCampoFuenteDatos(mapping, request, ids[0], ids[1]);
        
        return mapping.findForward("success");
    }



}
