package es.caib.sistra.back.action.gestorFormularios;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.sistra.back.action.BaseAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action para consultar un Dominio.
 *
 * @struts.action
 *  path="/back/gestorFormularios/seleccion"
 *
 * @struts.action-forward
 *  name="success" path=".gestorFormularios.editar"
 *
 * @struts.action-forward
 *  name="fail" path=".gestorFormularios.lista"
 */
public class SeleccionFormularioAction extends BaseAction{
    protected static Log log = LogFactory.getLog(SeleccionFormularioAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.info("Entramos en SeleccionFormulario");

        String idString = request.getParameter("identificador");
        if (idString == null || idString.length() == 0) {
            log.warn("El paràmetre identificador és null!!");
            return mapping.findForward("fail");
        }

        log.info("Seleccionar el Formulario " + idString);

        guardarGestorFormulario(mapping, request, idString);

        return mapping.findForward("success");
    }
}
