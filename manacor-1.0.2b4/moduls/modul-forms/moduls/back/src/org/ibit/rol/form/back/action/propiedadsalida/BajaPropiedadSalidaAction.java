package org.ibit.rol.form.back.action.propiedadsalida;

import org.ibit.rol.form.back.action.BaseAction;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.ibit.rol.form.persistence.delegate.PropiedadSalidaDelegate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action para preparar borrar una Propiedad Salida
 *
 * @struts.action
 *  name="propiedadSalidaForm"
 *  scope="session"
 *  validate="false"
 *  path="/back/propiedadsalida/baja"
 *
 * @struts.action-forward
 *  name="success" path=".salida.editar"
 */
public class BajaPropiedadSalidaAction extends BaseAction {

     protected static Log log = LogFactory.getLog(BajaPropiedadSalidaAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.debug("Entramos en BajaPropiedadSalida");
        String idString = request.getParameter("idPropiedad");
        if (idString == null || idString.length() == 0) {
            log.warn("El paràmetre idPropiedad és null!!");
            return mapping.findForward("fail");
        }

        PropiedadSalidaDelegate propiedadDelegate = DelegateUtil.getPropiedadSalidaDelegate();
        Long idPropiedad = new Long(idString);
        propiedadDelegate.borrarPropiedadSalida(idPropiedad);
       // request.setAttribute("reloadMenu", "true");

        String idSalidaString = request.getParameter("idSalida");
        if (idSalidaString == null || idSalidaString.length() == 0) {
            log.warn("El paràmetre idSalida és null");
            return mapping.findForward("fail");
        }

        Long idSalida = new Long(idSalidaString);
        guardarSalida(mapping, request, idSalida);        

        return mapping.findForward("success");
    }
}
