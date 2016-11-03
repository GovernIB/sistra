package es.caib.mobtratel.front.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.tiles.ComponentContext;
import org.apache.struts.tiles.Controller;
import org.apache.struts.util.MessageResources;
import org.apache.struts.Globals;

import es.caib.mobtratel.front.Constants;
import es.caib.util.StringUtil;


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
    public Locale getLocale(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Locale locale = (Locale) session.getAttribute(Globals.LOCALE_KEY);
        if (locale == null) {
            locale = Locale.getDefault();
        }
        return (locale);
    }
    
    protected List getHoras()
    {
    	List horas = new ArrayList();
    	for(int i=0; i<Constants.HORAS_DIA;i++)
    	{
    		horas.add(StringUtil.lpad(String.valueOf(i),2,'0'));
    	}
    	return horas;
    }
    
    
    
}
