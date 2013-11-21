package org.ibit.rol.form.back.action.propiedadsalida;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.ibit.rol.form.back.action.BaseAction;
import org.ibit.rol.form.back.form.PropiedadSalidaForm;
import org.ibit.rol.form.model.PropiedadSalida;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.ibit.rol.form.persistence.delegate.PropiedadSalidaDelegate;

/**
 * Action para editar una Propiedad Salida
 *
 * @struts.action
 *  name="propiedadSalidaForm"
 *  scope="session"
 *  validate="true"
 *  input=".propiedadsalida.editar"
 *  path="/back/propiedadsalida/editar"
 *
 * @struts.action-forward
 *  name="alta" path="/back/propiedadsalida/alta.do"
 *
 * @struts.action-forward
 *  name="success" path=".salida.editar"
 *
 * @struts.action-forward
 *  name="cancel" path=".salida.editar"
 *
 */
public class EditarPropiedadSalidaAction extends BaseAction {

    protected static Log log = LogFactory.getLog(EditarPropiedadSalidaAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.debug("Entramos en EditarPropiedadSalida");
        PropiedadSalidaDelegate salidaDelegate = DelegateUtil.getPropiedadSalidaDelegate();
        PropiedadSalidaForm propiedadSalidaForm = (PropiedadSalidaForm) form;
        PropiedadSalida propiedadSalida = propiedadSalidaForm.getValues();

        if (isCancelled(request)) {
            log.debug("isCancelled");
            Long idSalida = propiedadSalidaForm.getIdSalida();
            guardarSalida(mapping ,request, idSalida);            
            return mapping.findForward("cancel");
        }

        if (request.getParameter("borrarPlantilla") != null) {
            salidaDelegate.borrarPlantilla(propiedadSalida.getId(), propiedadSalida.getPlantilla().getId());
            propiedadSalida.setPlantilla(null);
            return mapping.findForward("success");
        } else {
            if (archivoValido(propiedadSalidaForm.getPlantilla())) {
                propiedadSalida.setPlantilla(populateArchivo(propiedadSalida.getPlantilla(), propiedadSalidaForm.getPlantilla()));
            }
        }

        if (isAlta(request) || isModificacion(request)) {
            log.debug("isAlta || isModificacio");

            Long idSalida = propiedadSalidaForm.getIdSalida();
            salidaDelegate.grabarPropiedadSalida(propiedadSalida, idSalida);

            log.debug("Creat/Actualitzat " + propiedadSalida.getId());
            guardarSalida(mapping, request, idSalida);

        }
        return mapping.findForward("success");
    }
}
