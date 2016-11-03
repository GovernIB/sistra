package es.caib.zonaper.front.action;


import java.security.Principal;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.util.RequestUtils;

import es.caib.zonaper.front.Constants;
import es.caib.zonaper.front.util.ZonapersFrontRequestHelper;
import es.caib.zonaper.model.DatosSesion;


/**
 * Action Base.
 */
public abstract class BaseAction extends Action
{
	/**
     * Return the user's currently selected Locale.
     * @param request The request we are processing
     */
    public  Locale getLocale(HttpServletRequest request) 
    {
        return ZonapersFrontRequestHelper.getLocale( request );
    }
    
    public String getLang(HttpServletRequest request) {
        return  ZonapersFrontRequestHelper.getLang( request );
    }
    
    public Principal getPrincipal( HttpServletRequest request )
    {
		return ZonapersFrontRequestHelper.getPrincipal( request );
    }
    
    public DatosSesion getDatosSesion( HttpServletRequest request )
    {
    	return ZonapersFrontRequestHelper.getDatosSesion( request );
    }
    
    public void setDatosSesion( HttpServletRequest request, DatosSesion sesion )
    {
    	ZonapersFrontRequestHelper.setDatosSesion( request, sesion ); 
    }

	public abstract ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception;
	
	public void setIdPersistencia(HttpServletRequest request,String idPersistencia){
		request.getSession().setAttribute(Constants.KEY_ANONIMO_ID_PERSISTENCIA,idPersistencia);		
	}
	
	public String getIdPersistencia(HttpServletRequest request){
		return (String) request.getSession().getAttribute(Constants.KEY_ANONIMO_ID_PERSISTENCIA);
	}
	
	protected ActionForm obtenerActionForm(ActionMapping mapping, HttpServletRequest request, String path) {
        ModuleConfig config = mapping.getModuleConfig();
        ActionMapping newMapping = (ActionMapping) config.findActionConfig(path);
        ActionForm newForm = RequestUtils.createActionForm(request, newMapping, config, this.servlet);
        if ("session".equals(newMapping.getScope())) {
            request.getSession(true).setAttribute(newMapping.getAttribute(), newForm);
        } else {
            request.setAttribute(newMapping.getAttribute(), newForm);
        }
        newForm.reset(newMapping, request);
        return newForm;
    }

}
