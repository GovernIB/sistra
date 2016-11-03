package es.caib.sistra.admin.action;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.tiles.ComponentContext;
import org.apache.struts.tiles.Controller;

public abstract class BaseController implements Controller
{
	public abstract void perform(ComponentContext arg0, HttpServletRequest arg1,
			HttpServletResponse arg2, ServletContext arg3)
			throws ServletException, IOException;

}
