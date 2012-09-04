package es.caib.bantel.back.action.gestorBandeja;

import es.caib.bantel.back.action.BaseAction;
import es.caib.bantel.back.form.GestorBandejaForm;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action para preparar el alta de un GestorBandeja.
 *
 * @struts.action
 *  path="/back/gestorBandeja/alta"
 *
 * @struts.action-forward
 *  name="success" path=".gestorBandeja.editar"
 */
public class AltaGestorBandejaAction extends BaseAction {
     public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        GestorBandejaForm fForm = (GestorBandejaForm) obtenerActionForm(mapping,request, "/back/gestorBandeja/editar");
        fForm.destroy( mapping, request );

        return mapping.findForward("success");
    }

}
