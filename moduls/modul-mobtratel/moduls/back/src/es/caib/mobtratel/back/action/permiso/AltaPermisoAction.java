package es.caib.mobtratel.back.action.permiso;



import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import es.caib.mobtratel.back.action.BaseAction;
import es.caib.mobtratel.back.form.PermisoForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action para preparar el alta de un Permiso para enviar.
 *
 * @struts.action
 *  path="/back/permiso/alta"
 *
 * @struts.action-forward
 *  name="success" path=".permiso.editar"
 */
public class AltaPermisoAction extends BaseAction {
     public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        PermisoForm fForm = (PermisoForm) obtenerActionForm(mapping,request, "/back/permiso/editar");
        fForm.destroy( mapping, request );

        return mapping.findForward("success");
    }

}
