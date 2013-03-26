package es.caib.pagos.front.controller;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;
import org.apache.struts.tiles.ComponentContext;
import org.apache.struts.tiles.Controller;
import org.apache.struts.util.MessageResources;

import es.caib.pagos.front.util.PagosFrontRequestHelper;
import es.caib.pagos.persistence.delegate.SesionPagoDelegate;

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

    protected MessageResources getResources(HttpServletRequest request) {
        return ((MessageResources) request.getAttribute(Globals.MESSAGES_KEY));

    }
        
	public  Locale getLocale(HttpServletRequest request) 
    {
        return PagosFrontRequestHelper.getLocale( request );
    }
    
    public String getLang(HttpServletRequest request) {
        return  PagosFrontRequestHelper.getLang( request );
    }
    
    public SesionPagoDelegate getSesionPago(HttpServletRequest request){
    	return PagosFrontRequestHelper.getSesionPago(request);
    }
    
    public String getUrlRetornoSistra( HttpServletRequest request )
    {
    	return  PagosFrontRequestHelper.getUrlRetornoSistra(request);
    }
       
}
