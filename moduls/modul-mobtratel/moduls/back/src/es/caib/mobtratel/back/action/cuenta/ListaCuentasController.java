package es.caib.mobtratel.back.action.cuenta;
import org.apache.struts.tiles.ComponentContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.tiles.Controller;

import es.caib.mobtratel.persistence.delegate.CuentaDelegate;
import es.caib.mobtratel.persistence.delegate.DelegateException;
import es.caib.mobtratel.persistence.delegate.DelegateUtil;




public class ListaCuentasController implements Controller
{
	protected static Log log = LogFactory.getLog(ListaCuentasController.class);
	
	public void perform(ComponentContext tileContext,
            HttpServletRequest request, HttpServletResponse response,
            ServletContext servletContext)
	throws ServletException, IOException
	{
		
		try 
		{
            log.info("Entramos en ListaCuentasController");

            CuentaDelegate cuentaDelegate = DelegateUtil.getCuentaDelegate();
            request.setAttribute("cuentaOptions", cuentaDelegate.listarCuentas() );

        } catch (DelegateException e) {
            throw new ServletException(e);
        }
        
		

	}

}
