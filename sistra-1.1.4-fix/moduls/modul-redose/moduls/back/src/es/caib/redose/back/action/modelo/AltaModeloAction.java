package es.caib.redose.back.action.modelo;

import es.caib.redose.back.action.BaseAction;
import es.caib.redose.back.form.ModeloForm;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action para preparar el alta de un Modelo.
 *
 * @struts.action
 *  path="/back/modelo/alta"
 *
 * @struts.action-forward
 *  name="success" path=".modelo.editar"
 */
public class AltaModeloAction extends BaseAction {
     public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        ModeloForm fForm = (ModeloForm) obtenerActionForm(mapping,request, "/back/modelo/editar");
        fForm.destroy( mapping, request );

        return mapping.findForward("success");
    }

}
