package es.caib.zonaper.front.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.util.StringUtil;
import es.caib.zonaper.front.Constants;
import es.caib.zonaper.persistence.delegate.DelegateUtil;

/**
 * @web.servlet name="inicio"
 * @web.servlet-mapping url-pattern="/inicio"
 */
public class InicioServlet extends HttpServlet 
{
	protected static Log log = LogFactory.getLog(InicioServlet.class);
	
	
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	try{
	    	// Construimos url destino.Eliminamos de la url el parametro de lenguaje
	    	String urlFin = request.getContextPath() + "/protected/init.do";	     	     		    
	    	String params = (request.getQueryString()!=null?request.getQueryString():"");
			int pos = params.indexOf("lang=");
			if (pos >= 0){
				int posFin = pos+"lang=es".length();			
				params = params.substring(0,pos) + (params.length() > posFin?params.substring(posFin):"");		
				params = StringUtil.replace(params,"&&","&");
				if (params.startsWith("&")) params = params.substring(1);
			}
			urlFin = urlFin + (params.length()>0?"?"+params:"");
				     
			// Comprobamos si debemos invalidar la sesion al iniciar
	     	Boolean resetearSesion;
	     	resetearSesion = (Boolean) this.getServletContext().getAttribute(Constants.RESETEAR_SESION_WEB);
	     	if ( resetearSesion == null){
	     		String prop = "false";
	     		try{
	     			prop = DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion().getProperty("front.resetearSesionInicio");
	     		}catch(Exception ex){
	     			log.error("Excepcion obteniendo propiedad front.resetearSesionInicio",ex);
	     		}
	     		resetearSesion = new Boolean(prop);
	     		this.getServletContext().setAttribute(Constants.RESETEAR_SESION_WEB,resetearSesion);
	     	}
	     	if (resetearSesion.booleanValue()){
	     		log.debug("Invalidamos la sesion");
	     		request.getSession().invalidate();
	     	}
	    	
	    	// Redirigimos
	     	response.sendRedirect(urlFin);	
	    	}catch(Exception e){
    		throw new ServletException("Error redirigiendo a inicio",e);
    	}
	}
}

