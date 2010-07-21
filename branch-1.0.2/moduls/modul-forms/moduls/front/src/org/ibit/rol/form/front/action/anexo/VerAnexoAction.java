package org.ibit.rol.form.front.action.anexo;

import org.ibit.rol.form.front.action.BaseAction;
import org.ibit.rol.form.front.registro.RegistroManager;
import org.ibit.rol.form.persistence.delegate.InstanciaDelegate;
import org.ibit.rol.form.model.Pantalla;
import org.ibit.rol.form.model.Campo;
import org.ibit.rol.form.model.FileBox;
import org.ibit.rol.form.model.Anexo;
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
 * @struts.action path="/anexo"
 *
 * @struts.action path="/auth/anexo"
 *
 * @struts.action path="/secure/anexo"
 *
 * @struts.action path="/auth/secure/anexo"
 *
 * @struts.action-forward name="ver" path=".anexo.ver"
 * @struts.action-forward name="editar" path=".anexo.editar"
 */
public class VerAnexoAction extends BaseAction {

    protected static Log log = LogFactory.getLog(VerAnexoAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        InstanciaDelegate delegate = RegistroManager.recuperarInstancia(request);
        if (delegate == null) {
            ActionErrors errors = new ActionErrors();
            errors.add(null, new ActionError("errors.session"));
            saveErrors(request, errors);
            return mapping.findForward("fail");
        }

        String fieldName = request.getParameter("nombre");

        Pantalla pantalla = delegate.obtenerPantalla();
        Campo campo = pantalla.findCampo(fieldName);

        if (campo == null || !(campo instanceof FileBox)) {
            log.error(fieldName + " no és un filebox de la pantalla actual");
            return mapping.findForward("fail");
        }
        request.setAttribute("campo", campo);


        Anexo anexo = delegate.obtenerAnexo(campo.getNombreLogico());
        if (anexo != null) {
            request.setAttribute("anexo", anexo);
            return mapping.findForward("ver");
        } else {
            return mapping.findForward("editar");
        }
    }
}
