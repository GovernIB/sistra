package es.caib.sistra.back.action.organo;

import es.caib.sistra.back.action.BaseAction;
import es.caib.sistra.back.form.OrganoForm;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action para preparar el alta de un Organo.
 *
 * @struts.action
 *  path="/back/organo/alta"
 *
 * @struts.action-forward
 *  name="success" path=".organo.editar"
 */
public class AltaOrganoAction extends BaseAction {
     public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        OrganoForm fForm = (OrganoForm) obtenerActionForm(mapping,request, "/back/organo/editar");
        fForm.destroy( mapping, request );

        return mapping.findForward("success");
    }

}
