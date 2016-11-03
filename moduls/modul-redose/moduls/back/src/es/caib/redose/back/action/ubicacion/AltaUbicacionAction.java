package es.caib.redose.back.action.ubicacion;

import es.caib.redose.back.action.BaseAction;
import es.caib.redose.back.form.UbicacionForm;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action para preparar el alta de un Ubicacion.
 *
 * @struts.action
 *  path="/back/ubicacion/alta"
 *
 * @struts.action-forward
 *  name="success" path=".ubicacion.editar"
 */
public class AltaUbicacionAction extends BaseAction {
     public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        UbicacionForm fForm = (UbicacionForm) obtenerActionForm(mapping,request, "/back/ubicacion/editar");
        fForm.destroy( mapping, request );

        return mapping.findForward("success");
    }

}
