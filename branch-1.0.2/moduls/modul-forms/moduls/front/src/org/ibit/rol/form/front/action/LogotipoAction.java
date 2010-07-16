package org.ibit.rol.form.front.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.ibit.rol.form.model.Archivo;
import org.ibit.rol.form.persistence.delegate.InstanciaDelegate;
import org.ibit.rol.form.front.registro.RegistroManager;

/**
 * @struts.action path="/logotipo1"
 * @struts.action path="/logotipo2"
 *
 * @struts.action path="/auth/logotipo1"
 * @struts.action path="/auth/logotipo2"
 *
 * @struts.action path="/secure/logotipo1"
 * @struts.action path="/secure/logotipo2"
 *
 * @struts.action path="/auth/secure/logotipo1"
 * @struts.action path="/auth/secure/logotipo2"
 */
public class LogotipoAction extends BaseAction {

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {

        InstanciaDelegate delegate = RegistroManager.recuperarInstancia(request);
        if (delegate == null) {
            return null;
        }

        Archivo file = null;
        if ("/logotipo1".endsWith(mapping.getPath())) {
            file = delegate.obtenerLogotipo1();
        } else if ("/logotipo2".endsWith(mapping.getPath())) {
            file = delegate.obtenerLogotipo2();
        }

        if (file == null) {
            return null;
        }

        response.reset();
        response.setDateHeader("Last-Modified", request.getSession(true).getCreationTime());
        response.setContentType(file.getTipoMime());
        response.setContentLength(file.getPesoBytes());
        response.getOutputStream().write(file.getDatos());

        return null;
    }

}
