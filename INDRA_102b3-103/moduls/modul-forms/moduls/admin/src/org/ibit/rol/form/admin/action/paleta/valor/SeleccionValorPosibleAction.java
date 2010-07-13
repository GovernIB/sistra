package org.ibit.rol.form.admin.action.paleta.valor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ibit.rol.form.admin.action.BaseAction;
import org.ibit.rol.form.admin.form.ValorPosibleForm;
import org.ibit.rol.form.model.ValorPosible;
import org.ibit.rol.form.persistence.delegate.ValorPosibleDelegate;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Action para consultar un valor posible.
 *
 * @struts.action
 *  path="/admin/valorposible/seleccion"
 *
 * @struts.action-forward
 *  name="success" path=".valorposible.editar"
 *
 * @struts.action-forward
 *  name="fail" path=".paleta.lista"
 *
 */
public class SeleccionValorPosibleAction extends BaseAction {

    protected static Log log = LogFactory.getLog(SeleccionValorPosibleAction.class);

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {

        String idString = request.getParameter("id");
        if (idString == null || idString.length() == 0) {
            log.warn("El paràmetre id és null!!");
            return mapping.findForward("fail");
        }

        log.debug("Seleccionar el valor " + idString);

        Long id = new Long(idString);

        ValorPosibleDelegate delegate = DelegateUtil.getValorPosibleDelegate();
        ValorPosible valor = delegate.obtenerValorPosible(id);

        ValorPosibleForm vpForm = (ValorPosibleForm) obtenerActionForm(mapping,request, "/admin/valorposible/editar");
        vpForm.setValues(valor);
        vpForm.setIdComponente(valor.getCampo().getId());
        vpForm.setImagen(valor.getCampo().isImagen());

        return mapping.findForward("success");
    }


}
