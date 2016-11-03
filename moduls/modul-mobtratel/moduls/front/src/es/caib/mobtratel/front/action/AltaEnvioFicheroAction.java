package es.caib.mobtratel.front.action;



import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import es.caib.mobtratel.front.form.EditarEnvioFicheroForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action para preparar el alta de Envío a partir de un Fichero
 *
 * @struts.action
 *  path="/altaEnvioFichero"
 *
 * @struts.action-forward
 *  name="success" path=".editarFichero"
 */
public class AltaEnvioFicheroAction extends BaseAction {
     public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

    	 EditarEnvioFicheroForm fForm = (EditarEnvioFicheroForm) obtenerActionForm(mapping,request, "/editarEnvioFichero");
        fForm.destroy( mapping, request );

        return mapping.findForward("success");
    }

}
