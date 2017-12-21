package es.caib.bantel.back.action.tramite;

import es.caib.bantel.back.action.BaseAction;
import es.caib.bantel.back.form.TramiteForm;
import es.caib.bantel.back.util.MensajesUtil;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action para preparar el alta de un Tramite.
 *
 * @struts.action
 *  path="/back/tramite/alta"
 *
 * @struts.action-forward
 *  name="success" path=".tramite.editar"
 */
public class AltaTramiteAction extends BaseAction {
     public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

    	 MensajesUtil.setMsg(this.getResources(request));
    	 
    	TramiteForm fForm = (TramiteForm) obtenerActionForm(mapping,request, "/back/tramite/editar");
        fForm.destroy( mapping, request );
        
        fForm.setReadOnly("false");
        request.setAttribute( "idReadOnly", "false" );
        
        return mapping.findForward("success");
    }

}
