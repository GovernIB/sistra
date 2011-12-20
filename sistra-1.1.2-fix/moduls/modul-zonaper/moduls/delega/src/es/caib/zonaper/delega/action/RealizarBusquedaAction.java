package es.caib.zonaper.delega.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.zonaper.delega.form.BusquedaEntidadesForm;
import es.caib.zonaper.persistence.delegate.DelegateUtil;
import es.caib.zonaper.persistence.delegate.PadAplicacionDelegate;

/**
 * @struts.action
 * 	name="busquedaEntidadesForm"
 *  path="/busquedaEntidades"
 *  scope="request"
 *  validate="false"
 *  
 * @struts.action-forward
 *  name="success" path=".busquedaEntidades"
 *
 * @struts.action-forward
 *  name="fail" path=".mainLayout"
 */
public class RealizarBusquedaAction extends BaseAction
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		BusquedaEntidadesForm busquedaForm = (BusquedaEntidadesForm)form;
		PadAplicacionDelegate padAplic = DelegateUtil.getPadAplicacionDelegate();
		try{
			List entidades;
			if (StringUtils.isNotBlank(busquedaForm.getEntidadNif())) {			
				entidades = padAplic.buscarEntidades(busquedaForm.getEntidadNif());
			} else {
				entidades = padAplic.buscarEntidadesPorNombre(busquedaForm.getEntidadNombre());
			}
			request.setAttribute("entidades", entidades);
			return mapping.findForward( "success" );
		}catch(Exception e){
			ActionErrors messages = new ActionErrors();
        	messages.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.realizando.busqueda"));
        	saveErrors(request,messages);  
			return mapping.findForward( "fail" );
		}
		
    }
}
