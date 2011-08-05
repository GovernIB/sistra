package es.caib.sistra.back.action.organo;
import org.apache.struts.tiles.ComponentContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.tiles.Controller;

import es.caib.sistra.persistence.delegate.DelegateException;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.sistra.persistence.delegate.OrganoResponsableDelegate;


public class ListaOrganosController implements Controller
{
	protected static Log log = LogFactory.getLog(ListaOrganosController.class);
	
	public void perform(ComponentContext tileContext,
            HttpServletRequest request, HttpServletResponse response,
            ServletContext servletContext)
	throws ServletException, IOException
	{
		try 
		{
            log.info("Entramos en ListaDominiosController");

            OrganoResponsableDelegate organoDelegate = DelegateUtil.getOrganoResponsableDelegate();
            request.setAttribute("organoOptions", organoDelegate.listarOrganoResponsables() );

        } catch (DelegateException e) {
            throw new ServletException(e);
        }
		

	}

}
