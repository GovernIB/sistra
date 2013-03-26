package es.caib.pagosMOCK.front.action;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.util.RequestUtils;

import es.caib.pagosMOCK.front.util.PagosFrontRequestHelper;
import es.caib.pagosMOCK.persistence.delegate.SesionPagoDelegate;

/**
 * Action Base.
 */
public abstract class BaseAction extends Action {
	
	public Locale getLocale(HttpServletRequest request) {
		return PagosFrontRequestHelper.getLocale(request);
	}

	public String getLang(HttpServletRequest request) {
		return PagosFrontRequestHelper.getLang(request);
	}
	
	public SesionPagoDelegate getSesionPago(HttpServletRequest request){
    	return PagosFrontRequestHelper.getSesionPago(request);
    }
	
	public void setSesionPago(HttpServletRequest request,SesionPagoDelegate sesionPago){
    	PagosFrontRequestHelper.setSesionPago(request,sesionPago);
    }

	public abstract ActionForward execute(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception;

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
	
	public String getUrlRetornoSistra( HttpServletRequest request )
    {
    	return  PagosFrontRequestHelper.getUrlRetornoSistra(request);
    }
    
    public void setUrlRetornoSistra( HttpServletRequest request, String url )
    {
    	PagosFrontRequestHelper.setUrlRetornoSistra(request, url );
    }
}
