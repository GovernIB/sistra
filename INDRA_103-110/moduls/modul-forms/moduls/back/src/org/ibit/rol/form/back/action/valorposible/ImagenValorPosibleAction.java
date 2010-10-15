package org.ibit.rol.form.back.action.valorposible;

import org.ibit.rol.form.model.Archivo;
import org.ibit.rol.form.persistence.delegate.ValorPosibleDelegate;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.ibit.rol.form.back.action.ArchivoAction;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * Obtiene la imagen de un archivo.
 * @struts.action
 *  path="/back/valorposible/imagen"
 */
public class ImagenValorPosibleAction extends ArchivoAction {

    private static final Log log = LogFactory.getLog(ImagenValorPosibleAction.class);

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

        ValorPosibleDelegate delegate = DelegateUtil.getValorPosibleDelegate();
        return delegate.obtenerImagenValorPosible(id, lang);
    }

}

