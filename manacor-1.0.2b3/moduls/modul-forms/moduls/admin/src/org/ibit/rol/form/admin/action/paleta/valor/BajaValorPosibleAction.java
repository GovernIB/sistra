package org.ibit.rol.form.admin.action.paleta.valor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.ibit.rol.form.admin.action.BaseAction;
import org.ibit.rol.form.admin.util.ComponenteConfig;
import org.ibit.rol.form.model.Componente;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.ibit.rol.form.persistence.delegate.ValorPosibleDelegate;

/**
 * @struts.action
 *  path="/admin/valorposible/baja"
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
 * @struts.action-forward
 *  name="fail" path=".paleta.lista"
 */
public class BajaValorPosibleAction extends BaseAction {

    protected static Log log = LogFactory.getLog(BajaValorPosibleAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        log.debug("Entramos en BajaValorPosibleAction");

        ValorPosibleDelegate delegate = DelegateUtil.getValorPosibleDelegate();

        String idString = request.getParameter("id");
        if (idString == null || idString.length() == 0) {
            log.warn("El paràmetre id és null");
            return mapping.findForward("fail");
        }

        log.debug("Borrando el valor " + idString);

        Long id = new Long(idString);
        delegate.borrarValorPosible(id);

        String idComponenteString = request.getParameter("idComponente");
        if (idComponenteString == null || idComponenteString.length() == 0) {
            log.warn("El paràmetre idComponente és null");
            return mapping.findForward("fail");
        }

        Long idComponente = new Long(idComponenteString);
        Componente comp = guardarComponente(mapping, request, idComponente);

        String tipo = ComponenteConfig.getTipo(comp);
        return mapping.findForward(tipo);
    }
}
