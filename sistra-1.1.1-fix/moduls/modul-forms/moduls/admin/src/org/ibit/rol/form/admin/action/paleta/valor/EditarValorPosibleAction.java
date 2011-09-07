package org.ibit.rol.form.admin.action.paleta.valor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.ibit.rol.form.admin.action.BaseAction;
import org.ibit.rol.form.admin.form.ValorPosibleForm;
import org.ibit.rol.form.admin.util.ComponenteConfig;
import org.ibit.rol.form.model.Componente;
import org.ibit.rol.form.model.ValorPosible;
import org.ibit.rol.form.model.TraValorPosible;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.ibit.rol.form.persistence.delegate.ValorPosibleDelegate;

/**
 * Action para editar un Formulario.
 *
 * @struts.action
 *  name="valorPosibleForm"
 *  scope="session"
 *  validate="true"
 *  input=".valorposible.editar"
 *  path="/admin/valorposible/editar"
 *
 * @struts.action-forward
 *  name="reload" path=".valorposible.editar"
 *
 * @struts.action-forward
 *  name="textbox" path=".textbox.editar"
 * @struts.action-forward
 *  name="label" path=".label.editar"
 * @struts.action-forward
 *  name="checkbox" path=".checkbox.editar"
 * @struts.action-forward
 *  name="filebox" path=".filebox.editar"
 * @struts.action-forward
 *  name="combobox" path=".combobox.editar"
 * @struts.action-forward
 *  name="listbox" path=".listbox.editar"
 * @struts.action-forward
 *  name="radiobutton" path=".radiobutton.editar"
 *
 */
public class EditarValorPosibleAction extends BaseAction {

    protected static Log log = LogFactory.getLog(EditarValorPosibleAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        ValorPosibleDelegate valorPosibleDelegate = DelegateUtil.getValorPosibleDelegate();
        ValorPosibleForm vpForm = (ValorPosibleForm) form;
        ValorPosible valorPosible = (ValorPosible) vpForm.getValues();
        TraValorPosible trVp = (TraValorPosible) vpForm.getTraduccion();

        if (isCancelled(request)) {
            log.debug("isCancelled");
            Long idComponente = vpForm.getIdComponente();
            Componente comp = guardarComponente(mapping, request, idComponente);
            String tipo = ComponenteConfig.getTipo(comp);
            return mapping.findForward(tipo);
        }

        if (request.getParameter("borrarImagen") != null) {
            trVp.setArchivo(null);
            return mapping.findForward("reload");
        } else if (archivoValido(vpForm.getArchivo())) {
            trVp.setArchivo(populateArchivo(trVp.getArchivo(), vpForm.getArchivo()));
        }

        // Elimina traducciones que no son validas
        vpForm.validaTraduccion(mapping, request);

        if (isAlta(request) || isModificacio(request)) {
            log.debug("isAlta || isModificacio");

            Long idComponente = vpForm.getIdComponente();
            valorPosibleDelegate.gravarValorPosible(valorPosible, idComponente);

            log.debug("Creat/Actualitzat " + valorPosible.getId());

            Componente comp = guardarComponente(mapping, request, idComponente);
            String tipo = ComponenteConfig.getTipo(comp);
            return mapping.findForward(tipo);
        }

        // Cambio de idioma
        vpForm.reloadLang();
        return mapping.findForward("reload");
    }
}
