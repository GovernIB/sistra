package es.caib.bantel.back.action.tramite;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.apache.struts.tiles.ComponentContext;
import org.apache.struts.tiles.Controller;

import es.caib.bantel.model.Procedimiento;
import es.caib.bantel.persistence.delegate.DelegateException;
import es.caib.bantel.persistence.delegate.DelegateUtil;
import es.caib.bantel.persistence.delegate.ProcedimientoDelegate;


public class ListaTramitesController implements Controller
{
	protected static Log log = LogFactory.getLog(ListaTramitesController.class);
	
	public void perform(ComponentContext tileContext,
            HttpServletRequest request, HttpServletResponse response,
            ServletContext servletContext)
	throws ServletException, IOException
	{
		try 
		{
            log.debug("Entramos en ListaTramitesController");

            Locale local = (Locale) request.getSession().getAttribute(Globals.LOCALE_KEY);
            
            ProcedimientoDelegate tramiteDelegate = DelegateUtil.getTramiteDelegate();
            List procs = tramiteDelegate.listarProcedimientos();
            for (Iterator it = procs.iterator(); it.hasNext();) {
            	Procedimiento p = (Procedimiento) it.next();
            	p.setCurrentLang(local.getLanguage());
            }
			request.setAttribute("tramiteOptions", procs );
            
        } catch (DelegateException e) {
            throw new ServletException(e);
        }
		

	}

}
