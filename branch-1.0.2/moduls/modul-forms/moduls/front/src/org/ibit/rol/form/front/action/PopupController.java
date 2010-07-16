package org.ibit.rol.form.front.action;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.tiles.ComponentContext;
import org.ibit.rol.form.model.Formulario;
import org.ibit.rol.form.persistence.delegate.InstanciaDelegate;
import org.ibit.rol.form.front.registro.RegistroManager;

/**
 * Fija los recursos necesários para qualquier popup.
 */
public class PopupController extends BaseController {

    protected static Log log = LogFactory.getLog(PopupController.class);

    public void execute(ComponentContext tileContext,
                        HttpServletRequest request, HttpServletResponse response,
                        ServletContext servletContext) throws Exception {

        InstanciaDelegate delegate = RegistroManager.recuperarInstancia(request);
        Formulario formulario = delegate.obtenerFormulario();

        // Path Icongrafia
        request.setAttribute("pathIconografia", delegate.obtenerPathIconografia());
    }
}
