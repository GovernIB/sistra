package es.caib.bantel.back.action.fuenteDatos;
import org.apache.struts.Globals;
import org.apache.struts.tiles.ComponentContext;




import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.tiles.Controller;

import es.caib.bantel.model.Procedimiento;
import es.caib.bantel.persistence.delegate.DelegateException;
import es.caib.bantel.persistence.delegate.DelegateUtil;
import es.caib.bantel.persistence.delegate.ProcedimientoDelegate;


public class FuenteDatosController implements Controller
{
	public void perform(ComponentContext tileContext,
            HttpServletRequest request, HttpServletResponse response,
            ServletContext servletContext)
	throws ServletException, IOException
	{
		try 
		{
			Locale local = (Locale) request.getSession().getAttribute(Globals.LOCALE_KEY);
			
            ProcedimientoDelegate tramiteDelegate = DelegateUtil.getTramiteDelegate();
            List procs = tramiteDelegate.listarProcedimientos();
            for (Iterator it = procs.iterator(); it.hasNext();) {
            	Procedimiento p = (Procedimiento) it.next();
            	p.setCurrentLang(local.getLanguage());
            }
			request.setAttribute("procedimientosOptions", procs );
            
        } catch (DelegateException e) {
            throw new ServletException(e);
        }
		

	}

}
