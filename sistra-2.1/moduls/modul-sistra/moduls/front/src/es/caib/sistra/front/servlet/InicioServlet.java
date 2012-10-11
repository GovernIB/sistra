package es.caib.sistra.front.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.sistra.front.Constants;
import es.caib.sistra.persistence.delegate.DelegateUtil;

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

    	// Construimos url destino
    	//String urlIni = request.getRequestURL().toString();
     	String urlFin = request.getContextPath() + "/protected/init.do?" + request.getQueryString(); 	
     	
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
	}
}
