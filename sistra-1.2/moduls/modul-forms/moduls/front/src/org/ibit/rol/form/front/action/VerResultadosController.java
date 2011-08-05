package org.ibit.rol.form.front.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.tiles.ComponentContext;
import org.apache.struts.util.MessageResources;
import org.ibit.rol.form.persistence.delegate.InstanciaDelegate;
import org.ibit.rol.form.persistence.delegate.InstanciaTelematicaDelegate;
import org.ibit.rol.form.persistence.conector.Result;
import org.ibit.rol.form.front.registro.RegistroManager;
import org.ibit.rol.form.model.Formulario;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;

/**
 * Obtiene los recursos necesarios para renderizar la plantilla final.
 */
public class VerResultadosController extends BaseController {

    protected static Log log = LogFactory.getLog(VerResultadosController.class);

    public void execute(ComponentContext tileContext,
                        HttpServletRequest request, HttpServletResponse response,
                        ServletContext servletContext) throws Exception {

        InstanciaDelegate delegate = RegistroManager.recuperarInstancia(request);
        boolean telematic = (delegate instanceof InstanciaTelematicaDelegate);

        // Fijar el titulo.
        MessageResources messages;
        if (telematic) {
            messages = MessageResources.getMessageResources("form-front-messages-caib");
        } else {
            messages = MessageResources.getMessageResources("form-front-messages");
        }
        tileContext.putAttribute("title", messages.getMessage(getLocale(request),"resultados.title"));


        // Fijar formulario
        Formulario formulario = delegate.obtenerFormulario();
        request.setAttribute("formulario", formulario);

        Result[] resultados = RegistroManager.recuperarResultados(request);
        request.setAttribute("resultados", resultados);

        // Path Icongrafia
        request.setAttribute("pathIconografia", delegate.obtenerPathIconografia());

        // Botones.
        request.setAttribute("saveButton", Boolean.FALSE);
        request.setAttribute("discardButton", Boolean.FALSE);
        request.setAttribute("backButton", Boolean.FALSE);
        request.setAttribute("nextButton", Boolean.TRUE);
        request.setAttribute("endButton", Boolean.FALSE);
    }

}
