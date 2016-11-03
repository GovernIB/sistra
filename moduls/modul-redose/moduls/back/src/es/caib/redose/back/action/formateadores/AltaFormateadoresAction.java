package es.caib.redose.back.action.formateadores;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.redose.back.action.BaseAction;
import es.caib.redose.back.form.FormateadoresForm;

/**
 * Action para preparar el alta de un formateador
 *
 * @struts.action
 *  path="/back/formateadores/alta"
 *
 * @struts.action-forward
 *  name="success" path=".formateadores.editar"
 */
public class AltaFormateadoresAction extends BaseAction {
     public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        FormateadoresForm fForm = (FormateadoresForm) obtenerActionForm(mapping,request, "/back/formateadores/editar");
        fForm.destroy( mapping, request );

        return mapping.findForward("success");
    }

}
