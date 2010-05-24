package org.ibit.rol.form.back.action.propiedadsalida;

import org.ibit.rol.form.back.action.BaseAction;
import org.ibit.rol.form.back.form.PropiedadSalidaForm;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action para dar de alta una propiedad Salida
 *
 * @struts.action
 *  path="/back/propiedadsalida/alta"
 *
 *
 * @struts.action-forward
 *  name="success" path=".propiedadsalida.editar"
 *
 * @struts.action-forward
 *  name="fail" path=".propiedadsalida.lista"
 *
 */
public class AltaPropiedadSalidaAction extends BaseAction {
    protected static Log log = LogFactory.getLog(AltaPropiedadSalidaAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.debug("Entramos en AltaPropiedadSalida");
        String idSalidaString = request.getParameter("idSalida");
        if (idSalidaString == null || idSalidaString.length() == 0) {
            log.debug("idSalida es null");
            return mapping.findForward("fail");
        }

        Long idSalida = new Long(idSalidaString);


        PropiedadSalidaForm propiedadSalidaForm = (PropiedadSalidaForm) obtenerActionForm(mapping,request, "/back/propiedadsalida/editar");
        propiedadSalidaForm.destroy(mapping, request);
        propiedadSalidaForm.setIdSalida(idSalida);

        return mapping.findForward("success");
    }

}
