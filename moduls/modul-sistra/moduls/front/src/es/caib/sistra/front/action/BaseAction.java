package es.caib.sistra.front.action;

import java.security.Principal;
import java.util.Map;

import javax.ejb.NoSuchObjectLocalException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.util.RequestUtils;

import es.caib.sistra.front.SesionCaducadaException;
import es.caib.sistra.front.formulario.GestorFlujoFormulario;
import es.caib.sistra.front.util.FlujoFormularioRequestHelper;
import es.caib.sistra.front.util.TramiteRequestHelper;
import es.caib.sistra.model.MensajeFront;
import es.caib.sistra.model.OrganismoInfo;
import es.caib.sistra.model.ParametrosMensaje;
import es.caib.sistra.model.RespuestaFront;
import es.caib.sistra.model.TramiteFront;
import es.caib.sistra.persistence.delegate.DelegateException;
import es.caib.sistra.persistence.delegate.DelegateUtil;

/**
 * Action Base.
 */
public abstract class BaseAction extends Action
{

    protected String getLang(HttpServletRequest request) 
    {
        return TramiteRequestHelper.getLang( request );
    }
    
    protected Principal getPrincipal( HttpServletRequest request )
    {
    	return TramiteRequestHelper.getPrincipal( request );
    }
    
    protected char getMetodoAutenticacion( HttpServletRequest request ) throws Exception
    {
    	return TramiteRequestHelper.getMetodoAutenticacion( request );
    }
    
    protected void setRespuestaFront( HttpServletRequest request, RespuestaFront respuestaFront ) throws Exception
    {
    	TramiteRequestHelper.setRespuestaFront( request, respuestaFront );
    }
    
    protected RespuestaFront getRespuestaFront( HttpServletRequest request )
    {
    	return TramiteRequestHelper.getRespuestaFront( request );
    }
    
    protected TramiteFront getTramiteFront( HttpServletRequest request )
    {
    	return TramiteRequestHelper.getTramiteFront( request );
    }
    
    protected Map getParametros( HttpServletRequest request )
    {
    	return TramiteRequestHelper.getParametros( request );
    }
    
    protected MensajeFront getMessage( HttpServletRequest request )
    {
    	return TramiteRequestHelper.getMessage( request ); 
    }
    
    protected void setWarnMessage( HttpServletRequest request, String messageKey, Object args[] )
    {
    	TramiteRequestHelper.setWarnMessage( request, messageKey, args );
    }
    
    protected void setWarnMessage( HttpServletRequest request, String messageKey )
    {
    	TramiteRequestHelper.setWarnMessage( request, messageKey );
    }
    
    protected void setWarnMessage( HttpServletRequest request, String messageKey,ParametrosMensaje param )
    {
    	TramiteRequestHelper.setWarnMessage( request, messageKey ,param);
    }
    
    protected void setWarnMessage( HttpServletRequest request, String messageKey, Object args[] ,ParametrosMensaje param )
    {
    	TramiteRequestHelper.setWarnMessage( request, messageKey, args ,param);
    }
    
    
    
    protected void setErrorMessage( HttpServletRequest request, String errorMessageKey, Object args[] )
    {
    	TramiteRequestHelper.setErrorMessage( request, errorMessageKey, args );
    }
    
    protected void setErrorMessage( HttpServletRequest request, String errorMessageKey )
    {
    	TramiteRequestHelper.setErrorMessage( request, errorMessageKey );
    }
    
    protected void setErrorMessage( HttpServletRequest request, String errorMessageKey, Object args[],ParametrosMensaje param )
    {
    	TramiteRequestHelper.setErrorMessage( request, errorMessageKey, args,param );
    }
    
    protected void setErrorMessage( HttpServletRequest request, String errorMessageKey ,ParametrosMensaje param)
    {
    	TramiteRequestHelper.setErrorMessage( request, errorMessageKey,param );
    }
    
    
    
    protected void setErrorRecoverableMessage( HttpServletRequest request, String errorMessageKey, Object args[] )
    {
    	TramiteRequestHelper.setErrorRecoverableMessage( request, errorMessageKey, args );
    }
    
    protected void setErrorRecoverableMessage( HttpServletRequest request, String errorMessageKey )
    {
    	TramiteRequestHelper.setErrorRecoverableMessage( request, errorMessageKey );
    }
    
    protected void setErrorRecoverableMessage( HttpServletRequest request, String errorMessageKey, Object args[] ,ParametrosMensaje param)
    {
    	TramiteRequestHelper.setErrorRecoverableMessage( request, errorMessageKey, args ,param );
    }
    
    protected void setErrorRecoverableMessage( HttpServletRequest request, String errorMessageKey  ,ParametrosMensaje param)
    {
    	TramiteRequestHelper.setErrorRecoverableMessage( request, errorMessageKey ,param );
    }
    
            
    protected void setInfoMessage( HttpServletRequest request, String errorMessageKey, Object args[] )
    {
    	TramiteRequestHelper.setInfoMessage( request, errorMessageKey, args );
    }
    
    protected void setInfoMessage( HttpServletRequest request, String errorMessageKey )
    {
    	TramiteRequestHelper.setInfoMessage( request, errorMessageKey );
    }
    protected void setInfoMessage( HttpServletRequest request, String errorMessageKey, Object args[] ,ParametrosMensaje param)
    {
    	TramiteRequestHelper.setInfoMessage( request, errorMessageKey, args ,param);
    }
    
    protected void setInfoMessage( HttpServletRequest request, String errorMessageKey ,ParametrosMensaje param)
    {
    	TramiteRequestHelper.setInfoMessage( request, errorMessageKey ,param );
    }
    
    
    protected boolean isSetMessage( HttpServletRequest request )
    {
    	return TramiteRequestHelper.isSetMessage( request );
    }
    
    protected boolean isSetError( HttpServletRequest request )
    {
    	return TramiteRequestHelper.isSetError( request );
    }
    
    protected boolean isErrorHandled( HttpServletRequest request )
    {
    	return TramiteRequestHelper.isErrorHandled( request );
    }
    
    protected void setErrorHandled( HttpServletRequest request )
    {
    	TramiteRequestHelper.setErrorHandled( request );
    }
    
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
    	HttpSession session = request.getSession( true );
    	ActionForward result = null;
    	
    	// Ejecutamos tarea
    	synchronized( session )
    	{
    		// Controlamos error de expiracion de ejb de sesion (al realizar activate)
    		try{    		
    			result = executeTask( mapping, form, request, response );
    		}catch(DelegateException de){
    			if (de.getCause().getClass().equals(NoSuchObjectLocalException.class)){
	    			// Error no recuperable: sesion de tramitacion finalizada
    		    	OrganismoInfo oi = DelegateUtil.getConfiguracionDelegate().obtenerOrganismoInfo();
    		    	String refPortal = (String) oi.getReferenciaPortal().get(((java.util.Locale) session.getAttribute(org.apache.struts.Globals.LOCALE_KEY)).getLanguage());
    				TramiteRequestHelper.setErrorMessage(request,"errors.sesionTramitacionFinalizada",new Object[] {refPortal});	    			
	        		return mapping.findForward( "error" );
    			}
    			throw de;
    		} catch(SesionCaducadaException se){
    			// Error no recuperable: sesion de tramitacion finalizada
		    	OrganismoInfo oi = DelegateUtil.getConfiguracionDelegate().obtenerOrganismoInfo();
		    	String refPortal = (String) oi.getReferenciaPortal().get(((java.util.Locale) session.getAttribute(org.apache.struts.Globals.LOCALE_KEY)).getLanguage());
	    		TramiteRequestHelper.setErrorMessage(request,"errors.sesionTramitacionFinalizada",new Object[] {refPortal});	    			
	        	return mapping.findForward( "error" );    			
    		}    	
    	}
    	
    	
    	// Controlamos si se devuelve error
    	if ( this.isSetError( request ) )
    	{
    		MensajeFront mensaje = this.getMessage( request );
    		// Tratamiento error recuperable
    		if ( MensajeFront.TIPO_ERROR_CONTINUABLE  == mensaje.getTipo() )
    		{
    			if ( !isErrorHandled( request ) )
    			{
    				setErrorHandled( request );
    				return mapping.findForward( "pasoActual" );
    			}
    			else // Si el error está tratado, quiere decir que estamos en segunda vuelta, debemos presentar la pagina
    			{
    				return result;
    			}
    		}
    		
    		// Tratamiento error no recuperable
    		return mapping.findForward( "fail" );
    		
    	}
    	return result;

    }
    
    public abstract ActionForward executeTask(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception;
    
    
    protected GestorFlujoFormulario obtenerGestorFormulario( HttpServletRequest request,String token) throws Exception
    {
    	return FlujoFormularioRequestHelper.obtenerGestorFormulario( request, token);
    }
    
    protected GestorFlujoFormulario crearGestorFormulario( HttpServletRequest request,String token) throws Exception
    {
    	return FlujoFormularioRequestHelper.crearGestorFormulario( request, token );
    }
    
    protected void resetGestorFormulario( HttpServletRequest request, String token ) throws Exception
    {
    	FlujoFormularioRequestHelper.resetGestorFormulario( request, token );
    }
    
    protected ActionForm obtenerActionForm(ActionMapping mapping, HttpServletRequest request, String path) 
    {
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
