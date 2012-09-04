package org.ibit.rol.form.front.action.anexo;

import org.ibit.rol.form.front.action.BaseAction;
import org.ibit.rol.form.front.registro.RegistroManager;
import org.ibit.rol.form.persistence.delegate.InstanciaDelegate;
import org.ibit.rol.form.model.Pantalla;
import org.ibit.rol.form.model.Campo;
import org.ibit.rol.form.model.FileBox;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionError;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Prepara los datos para el formulario que recibirà un anexo.
 *
 * @struts.action path="/delAnexo"
 *
 * @struts.action path="/auth/delAnexo"
 *
 * @struts.action path="/secure/delAnexo"
 *
 * @struts.action path="/auth/secure/delAnexo"
 *
 * @struts.action-forward name="success" path=".anexo.editar"
 */
public class DelAnexoAction extends BaseAction {

    protected static Log log = LogFactory.getLog(DelAnexoAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
        
        InstanciaDelegate delegate = RegistroManager.recuperarInstancia(request);
        if (delegate == null) {
            ActionErrors errors = new ActionErrors();
            errors.add(null, new ActionError("errors.session"));
            saveErrors(request, errors);
            return mapping.findForward("fail");
        }

        String nombre = request.getParameter("nombre");
        Pantalla pantalla = delegate.obtenerPantalla();
        Campo campo = pantalla.findCampo(nombre);

        if (campo == null || !(campo instanceof FileBox)) {
            log.error(nombre + " no és un filebox de la pantalla actual");
            return mapping.findForward("fail");
        }

        request.setAttribute("campo", campo);
        delegate.introducirAnexo(nombre, null);

        return mapping.findForward("success");
    }
}
