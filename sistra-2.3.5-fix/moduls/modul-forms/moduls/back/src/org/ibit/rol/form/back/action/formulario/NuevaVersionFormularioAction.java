package org.ibit.rol.form.back.action.formulario;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.ibit.rol.form.back.action.BaseAction;
import org.ibit.rol.form.back.util.FormularioConfig;
import org.ibit.rol.form.model.Formulario;
import org.ibit.rol.form.persistence.delegate.FormularioDelegate;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action para consultar un Formulario.
 *
 * @struts.action
 *  path="/back/formulario/nversion"
 *
 * @struts.action-forward
 *  name="formulario" path=".formulario.editar"
 *
 * @struts.action-forward
 *  name="formularioseguro" path=".formularioseguro.editar"
 *
 * @struts.action-forward
 *  name="fail" path=".formulario.versiones"
 */
public class NuevaVersionFormularioAction extends BaseAction{
    protected static Log log = LogFactory.getLog(NuevaVersionFormularioAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.debug("Entramos en NuevaVersionFormulario");

        String idString = request.getParameter("id");
        if (idString == null || idString.length() == 0) {
            log.warn("El paràmetre id és null!!");
            return mapping.findForward("fail");
        }

        log.debug("Seleccionar el formulario " + idString);

        Long id = new Long(idString);

        FormularioDelegate delegate = DelegateUtil.getFormularioDelegate();
        Long newId = delegate.gravarNuevoFormulario(id);

        Formulario formulario = guardarFormulario(mapping, request, newId);

        String tipo = FormularioConfig.getTipo(formulario);

        log.debug("mapping findForward " + tipo);
        return mapping.findForward(tipo);
    }
}
