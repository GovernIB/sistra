package org.ibit.rol.form.back.action.propiedadsalida;

import org.ibit.rol.form.back.action.ArchivoAction;
import org.ibit.rol.form.model.Archivo;
import org.ibit.rol.form.model.PropiedadSalida;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.ibit.rol.form.persistence.delegate.PropiedadSalidaDelegate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;

/**
 * Obtiene la plantilla de una propiedad salida
 * @struts.action
 *  path="/back/propiedadsalida/plantilla"
 */
public class PlantillaPropiedadSalidaAction extends ArchivoAction {
    private static final Log log = LogFactory.getLog(PlantillaPropiedadSalidaAction.class);

    public Archivo obtenerArchivo(ActionMapping mapping,
                                  ActionForm form,
                                  HttpServletRequest request) throws Exception {

        String idString = request.getParameter("id");
        if (idString == null || idString.length() == 0) {
            log.warn("El paràmetre id és null");
            return null;
        }

        Long id;
        try {
            id = new Long(idString);
        } catch (NumberFormatException e) {
            log.error("El paràmetre id no és un long", e);
            return null;
        }


        PropiedadSalidaDelegate delegate = DelegateUtil.getPropiedadSalidaDelegate();
        PropiedadSalida propiedad = delegate.obtenerPropiedadSalida(id);

        if (propiedad == null || propiedad.getPlantilla() == null) {
            return null;
        }

        return propiedad.getPlantilla();
    }
}
