package es.caib.bantel.back.action.entidad;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.bantel.back.action.BaseAction;
import es.caib.bantel.back.form.EntidadForm;

/**
 * Action para preparar el alta Entidad.
 *
 * @struts.action
 *  path="/back/entidad/alta"
 *
 * @struts.action-forward
 *  name="success" path=".entidad.editar"
 */
public class AltaEntidadAction extends BaseAction {
     public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        EntidadForm fForm = (EntidadForm) obtenerActionForm(mapping,request, "/back/entidad/editar");
        fForm.destroy( mapping, request );

        return mapping.findForward("success");
    }

}
