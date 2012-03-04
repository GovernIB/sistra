package org.ibit.rol.form.admin.action.paleta.valor;

import org.ibit.rol.form.admin.action.BaseAction;
import org.ibit.rol.form.admin.form.ValorPosibleForm;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.ibit.rol.form.model.Campo;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action para preparar el alta de un valor posible.
 *
 * @struts.action
 *  path="/admin/valorposible/alta"
 *
 * @struts.action-forward
 *  name="success" path=".valorposible.editar"
 */
public class AltaValorPosibleAction extends BaseAction {

    protected static Log log = LogFactory.getLog(AltaValorPosibleAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {


        String idComponenteString = request.getParameter("idComponente");
        if (idComponenteString == null || idComponenteString.length() == 0) {
            log.warn("idComponente es null");
        }

        Long idComponente = new Long(idComponenteString);
        Campo campo = (Campo) DelegateUtil.getComponenteDelegate().obtenerComponente(idComponente);

        ValorPosibleForm vpForm = (ValorPosibleForm) obtenerActionForm(mapping,request, "/admin/valorposible/editar");
        vpForm.destroy(mapping, request);
        vpForm.setIdComponente(idComponente);
        vpForm.setImagen(campo.isImagen());

        return mapping.findForward("success");
    }
}
