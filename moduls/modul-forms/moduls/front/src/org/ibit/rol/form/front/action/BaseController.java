package org.ibit.rol.form.front.action;

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
import org.ibit.rol.form.front.Constants;
import org.ibit.rol.form.model.OrganismoInfo;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;

/**
 * Controller con métodos de utilidad.
 */
public abstract class BaseController implements Controller {

    public final void perform(ComponentContext tileContext,
                              HttpServletRequest request, HttpServletResponse response,
                              ServletContext servletContext)
            throws ServletException {
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
        HttpSession session = request.getSession();
        Locale locale = (Locale) session.getAttribute(Globals.LOCALE_KEY);
        if (locale == null) {
            locale = Locale.getDefault();
        }
        return (locale);

    }
    
 protected OrganismoInfo getOrganismoInfo(HttpServletRequest request) throws Exception {
    	
    	OrganismoInfo oi = null;
    	
    	// Obtenemos organismo info sesion
    	oi = (OrganismoInfo) request.getSession().getAttribute(Constants.ORGANISMO_INFO_KEY);
    	
    	// Obtenemos organismo info generico
    	if (oi == null) {
    		oi = (OrganismoInfo) request.getSession().getServletContext().getAttribute(Constants.ORGANISMO_INFO_KEY);
    	}
    	
    	if (oi == null) {
    		oi = DelegateUtil.getConfiguracionDelegate().obtenerOrganismoInfo();
    	}
    	
    	return oi;
    	
    }
    
}
