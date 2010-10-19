package es.caib.sistra.back.action.dominio;

import java.io.IOException;
import java.util.List;

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
import es.caib.sistra.persistence.delegate.VersionWSDelegate;

public class DominioController extends BaseController
{
	protected static Log log = LogFactory.getLog(DominioController.class);
	public void perform(ComponentContext arg0, HttpServletRequest request,
			HttpServletResponse response, ServletContext arg3)
			throws ServletException, IOException
	{
		try
		{
			if( log.isDebugEnabled() )
				log.debug("Entramos en DominioController");
			
            OrganoResponsableDelegate organoDelegate = DelegateUtil.getOrganoResponsableDelegate();
            request.setAttribute("organoOptions", organoDelegate.listarOrganoResponsables() );
           
            // Obtenemos lista de versiones
            VersionWSDelegate vWSdelegate = DelegateUtil.getVersionWSDelegate();
            List versiones = vWSdelegate.obtenerVersiones();
            request.setAttribute( "listaVersionesWS", versiones);
          
			
		 } catch (DelegateException e) {
	         throw new ServletException(e);
	     }

	}

}
