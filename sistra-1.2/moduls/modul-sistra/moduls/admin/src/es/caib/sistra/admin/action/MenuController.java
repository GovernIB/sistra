package es.caib.sistra.admin.action;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.tiles.ComponentContext;

import es.caib.sistra.admin.util.Util;

public class MenuController extends BaseController
{

	public void perform(ComponentContext componentContext, HttpServletRequest request,
            HttpServletResponse response, ServletContext servletContext)
		throws ServletException, IOException 
	{
		request.setAttribute( "isAuditor", new Boolean( Util.hasRoleAuditor( request ) ) );
		request.setAttribute( "isAdmin", new Boolean( Util.hasRoleAdmin( request ) ) );

	}

}
