package org.ibit.rol.form.back.action.valorposible;

import org.ibit.rol.form.back.action.BaseAction;
import org.ibit.rol.form.back.form.ValorPosibleForm;
import org.ibit.rol.form.model.Campo;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForward;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action para preparar el alta de un Valor Posible.
 *
 * @struts.action
 *  name="valorPosibleForm"
 *  scope="session"
 *  validate="false"
 *  path="/back/valorposible/alta"
 *
 * @struts.action-forward
 *  name="success" path=".valorposible.editar"
 *
 * @struts.action-forward
 *  name="cancel" path="/main.do"
 */
public class AltaValorPosibleAction extends BaseAction{

    protected static Log log = LogFactory.getLog(AltaValorPosibleAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        String idComponenteString = request.getParameter("idComponente");
        if (idComponenteString == null || idComponenteString.length() == 0) {
            log.debug("idComponente es null");
            return mapping.findForward("cancel");
        }

        Long idComponente = new Long(idComponenteString);
        Campo campo = (Campo) DelegateUtil.getComponenteDelegate().obtenerComponente(idComponente);

        ValorPosibleForm vpForm = (ValorPosibleForm) obtenerActionForm(mapping,request, "/back/valorposible/editar");
        vpForm.destroy(mapping, request);
        vpForm.setIdComponente(idComponente);
        vpForm.setImagen(campo.isImagen());

        return mapping.findForward("success");
    }

}
