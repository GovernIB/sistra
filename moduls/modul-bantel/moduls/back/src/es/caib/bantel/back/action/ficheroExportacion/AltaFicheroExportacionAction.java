package es.caib.bantel.back.action.ficheroExportacion;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.bantel.back.action.BaseAction;
import es.caib.bantel.back.form.FicheroExportacionForm;

/**
 * Action para preparar el alta de un fichero de exportacion.
 *
 * @struts.action
 *  path="/back/ficheroExportacion/alta"
 *
 * @struts.action-forward
 *  name="success" path=".ficheroExportacion.editar"
 */
public class AltaFicheroExportacionAction extends BaseAction {
     public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        FicheroExportacionForm fForm = (FicheroExportacionForm) obtenerActionForm(mapping,request, "/back/ficheroExportacion/editar");
        fForm.destroy( mapping, request );

        return mapping.findForward("success");
    }

}
