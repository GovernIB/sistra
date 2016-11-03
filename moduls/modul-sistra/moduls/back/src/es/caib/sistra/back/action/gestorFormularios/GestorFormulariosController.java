package es.caib.sistra.back.action.gestorFormularios;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.tiles.ComponentContext;

import es.caib.sistra.back.action.BaseController;
import es.caib.sistra.persistence.delegate.DelegateException;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.sistra.persistence.delegate.OrganoResponsableDelegate;

public class GestorFormulariosController extends BaseController
{
	protected static Log log = LogFactory.getLog(GestorFormulariosController.class);
	public void perform(ComponentContext arg0, HttpServletRequest request,
			HttpServletResponse response, ServletContext arg3)
			throws ServletException, IOException
	{
//		try
//		{
//			if( log.isDebugEnabled() )
//				log.debug("Entramos en GestorFormularioController");
//			
//            OrganoResponsableDelegate organoDelegate = DelegateUtil.getOrganoResponsableDelegate();
//            request.setAttribute("organoOptions", organoDelegate.listarOrganoResponsables() );
//			
//		 } catch (DelegateException e) {
//	         throw new ServletException(e);
//	     }

	}

}
