package es.caib.sistra.front.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.tiles.ComponentContext;
import org.apache.struts.tiles.Controller;
import org.apache.struts.util.MessageResources;
import org.apache.struts.Globals;

import es.caib.sistra.front.util.TramiteRequestHelper;
import es.caib.sistra.model.RespuestaFront;
import es.caib.sistra.model.TramiteFront;

/**
 * Controller con métodos de utilidad.
 */
public abstract class BaseController implements Controller {

    public final void perform(ComponentContext tileContext,
                              HttpServletRequest request, HttpServletResponse response,
                              ServletContext servletContext)
            throws ServletException, IOException {
        try {
            execute(tileContext, request, response, servletContext);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    abstract public void execute(ComponentContext tileContext,
                                 HttpServletRequest request, HttpServletResponse response,
                                 ServletContext servletContext) throws Exception;

    /**
     * Return the default message resources for the current module.
     * @param request The servlet request we are processing
     */
    protected MessageResources getResources(HttpServletRequest request) {
        return ((MessageResources) request.getAttribute(Globals.MESSAGES_KEY));

    }

    /**
     * Return the user's currently selected Locale.
     * @param request The request we are processing
     */
    protected Locale getLocale(HttpServletRequest request) {
        return TramiteRequestHelper.getLocale( request );

    }
    
    protected RespuestaFront getRespuestaFront( HttpServletRequest request )
    {
    	return TramiteRequestHelper.getRespuestaFront( request );
    }
    
    protected TramiteFront getTramiteFront( HttpServletRequest request )
    {
    	return TramiteRequestHelper.getTramiteFront( request );
    }
    
    protected Principal getPrincipal( HttpServletRequest request )
    {
    	return TramiteRequestHelper.getPrincipal( request );
    }
    
    protected char getMetodoAutenticacion( HttpServletRequest request ) throws Exception
    {
    	return TramiteRequestHelper.getMetodoAutenticacion( request );
    }
    
    protected Map getParametros( HttpServletRequest request )
    {
    	return TramiteRequestHelper.getParametros( request );
    }
}
