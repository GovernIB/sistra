package org.ibit.rol.form.back.action.formulario;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.ibit.rol.form.back.action.ArchivoAction;
import org.ibit.rol.form.model.Archivo;
import org.ibit.rol.form.model.Formulario;
import org.ibit.rol.form.model.TraFormulario;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.ibit.rol.form.persistence.delegate.FormularioDelegate;

/**
 * Obtiene la imagen de un archivo.
 * @struts.action
 *  path="/back/formulario/plantilla"
 */
public class PlantillaFormularioAction extends ArchivoAction {

    private static final Log log = LogFactory.getLog(PlantillaFormularioAction.class);

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

        String lang = request.getParameter("lang");
        if (lang == null || lang.length() == 0) {
            log.warn("El paràmetre lang és null");
            return null;
        }

        FormularioDelegate delegate = DelegateUtil.getFormularioDelegate();
        Formulario formulario = delegate.obtenerFormulario(id);
        TraFormulario traForm = (TraFormulario) formulario.getTraduccion(lang);
        if (traForm == null || traForm.getPlantilla() == null) {
            return null;
        }

        return traForm.getPlantilla();
    }

}

