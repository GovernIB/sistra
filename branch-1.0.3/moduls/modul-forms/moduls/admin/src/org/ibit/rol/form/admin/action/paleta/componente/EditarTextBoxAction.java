package org.ibit.rol.form.admin.action.paleta.componente;

import org.ibit.rol.form.persistence.delegate.*;
import org.ibit.rol.form.model.Componente;
import org.ibit.rol.form.model.Validacion;
import org.ibit.rol.form.model.Campo;
import org.ibit.rol.form.admin.action.BaseAction;
import org.ibit.rol.form.admin.form.ComponenteForm;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action para editar un componente TextBox.
 *
 * @struts.action
 *  name="textboxForm"
 *  scope="session"
 *  validate="true"
 *  input=".textbox.editar"
 *  path="/admin/textbox/editar"
 *
 * @struts.action-forward
 *  name="reload" path=".textbox.editar"
 *
 * @struts.action-forward
 *  name="cancel" path=".paleta.editar"
 *
 * @struts.action-forward
 *  name="success" path=".paleta.editar"
 */
public class EditarTextBoxAction extends BaseAction {
    protected static Log log = LogFactory.getLog(EditarTextBoxAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.debug("Entramos en EditarTextBox");

        ComponenteDelegate componenteDelegate = DelegateUtil.getComponenteDelegate();
        ComponenteForm componenteForm = (ComponenteForm) form;
        Componente componente = (Componente) componenteForm.getValues();

        if (isCancelled(request)) {
            log.debug("isCancelled");
            Long idPaleta = componenteForm.getIdPaleta();
            guardarPaleta(mapping, request, idPaleta);

            return mapping.findForward("cancel");
        }

        // Elimina traducciones que no son validas
        componenteForm.validaTraduccion(mapping, request);

        if (isAlta(request) || isModificacio(request)) {
            log.debug("isAlta || isModificacio");

            if (componente.getId() != null) {
                componenteDelegate.borrarValidacionesCampo(componente.getId());
            }
            insertarValidaciones(componenteForm);

            Long idPaleta = componenteForm.getIdPaleta();
            componenteDelegate.gravarComponentePaleta(componente, idPaleta);

            log.debug("Creat/Actualitzat " + componente.getId());

            guardarPaleta(mapping, request, idPaleta);

            return mapping.findForward("success");
        }

        // Cambio de idioma
        componenteForm.reloadLang();
        request.setAttribute("idComponente", componente.getId());

        return mapping.findForward("reload");
    }

    private void insertarValidaciones(ComponenteForm componenteForm) {

        log.debug("Entra en InsertarValidaciones");

        MascaraDelegate mascaraDelegate = DelegateUtil.getMascaraDelegate();
        try {
            Campo campo = (Campo) componenteForm.getValues();
            campo.getValidaciones().clear();
            for (int aux = 0; aux < componenteForm.getValidacion().length; aux++) {
                if (componenteForm.getValidacion()[aux].isActivo()) {
                    Validacion validacion = new Validacion();
                    Long idMascara = componenteForm.getValidacion()[aux].getMascara_id();
                    validacion.setMascara(mascaraDelegate.obtenerMascara(idMascara));
                    if (componenteForm.getValidacion()[aux].getValores() != null) {
                        validacion.setValores(componenteForm.getValidacion()[aux].getValores());
                    }
                    campo.addValidacion(validacion);
                }
            }
        } catch (DelegateException e) {
            log.error(e.getMessage());
        }

    }
}
