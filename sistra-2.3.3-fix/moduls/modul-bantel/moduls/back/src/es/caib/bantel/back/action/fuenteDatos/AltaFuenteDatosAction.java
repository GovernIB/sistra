package es.caib.bantel.back.action.fuenteDatos;

import es.caib.bantel.back.action.BaseAction;
import es.caib.bantel.back.form.FuenteDatosForm;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action para preparar el alta de Fuente Datos.
 *
 * @struts.action
 *  path="/back/fuenteDatos/alta"
 *
 * @struts.action-forward
 *  name="success" path=".fuenteDatos.editar"
 */
public class AltaFuenteDatosAction extends BaseAction {
     public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

    	FuenteDatosForm fForm = (FuenteDatosForm) obtenerActionForm(mapping,request, "/back/fuenteDatos/editar");
        fForm.destroy( mapping, request );

        return mapping.findForward("success");
    }

}
