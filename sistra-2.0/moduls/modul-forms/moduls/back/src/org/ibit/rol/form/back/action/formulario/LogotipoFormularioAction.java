package org.ibit.rol.form.back.action.formulario;

import org.ibit.rol.form.back.action.ArchivoAction;
import org.ibit.rol.form.model.Archivo;
import org.ibit.rol.form.model.Formulario;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.ibit.rol.form.persistence.delegate.FormularioDelegate;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

import javax.servlet.http.HttpServletRequest;

/**
 * Obtiene los logotipos de un formulario.
 *
 * @struts.action
 *  path="/back/formulario/logotipo1"
 *
 * @struts.action
 *  path="/back/formulario/logotipo2"
 */
public class LogotipoFormularioAction extends ArchivoAction {

    private static final Log log = LogFactory.getLog(LogotipoFormularioAction.class);

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

        FormularioDelegate delegate = DelegateUtil.getFormularioDelegate();
        Formulario formulario = delegate.obtenerFormulario(id);

        if (mapping.getPath().endsWith("logotipo1")) {
            return formulario.getLogotipo1();
        } else {
            return formulario.getLogotipo2();
        }
    }
}
