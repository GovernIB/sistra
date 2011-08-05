package es.caib.sistra.back.action.dominio;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.tiles.ComponentContext;

import es.caib.sistra.back.action.BaseController;

public class PingDominioController extends BaseController
{
	protected static Log log = LogFactory.getLog(PingDominioController.class);
	public void perform(ComponentContext arg0, HttpServletRequest request,
			HttpServletResponse response, ServletContext arg3)
			throws ServletException, IOException
	{
		
	}

}
