package es.caib.sistra.front.servlet;

import java.io.IOException;

import javax.ejb.NoSuchObjectLocalException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.sistra.front.util.InstanciaManager;
import es.caib.sistra.persistence.delegate.DelegateException;
import es.caib.sistra.persistence.delegate.InstanciaDelegate;

/**
 * @web.servlet name="mantenimientoSesionForm"
 * @web.servlet-mapping url-pattern="/protected/mantenimientoSesionForm"
 */
public class MantenimientoSesionFormServlet extends HttpServlet {

	private static Log logger = LogFactory.getLog(MantenimientoSesionFormServlet.class);
	
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    	String idInstancia = (String) request.getParameter(InstanciaManager.ID_INSTANCIA);
    	
    	logger.debug("Mantenimiento sesion sistra - comprobando ejb de sesion de tramitacion [ID_INSTANCIA: " + idInstancia + "]");
        
        boolean sesionActiva = false;
    	try
    	{
    		// Invocamos a un metodo del ejb de sesion para mantenerlo activo
    		request.setAttribute(InstanciaManager.ID_INSTANCIA,idInstancia);
    		InstanciaDelegate delegate = InstanciaManager.recuperarInstancia( request );
    		String idPersistencia = delegate.obtenerIdPersistencia();
    		sesionActiva=true;
    		logger.debug("Mantenimiento sesion sistra - ejb de sesion de tramitacion activo");
    	}catch(DelegateException de){
			if (de.getCause().getClass().equals(NoSuchObjectLocalException.class)){
    			// Sesion de tramitacion finalizada: no existe EJB de sesion    				
    			sesionActiva=false;
    			logger.debug("Mantenimiento sesion sistra - ejb de sesion de tramitacion ya no existe. Sesion de tramitacion finalizada.");
			}else{
				// Sesion de tramitacion finalizada: error desconocido    				
    			sesionActiva=false;
    			logger.debug("Mantenimiento sesion sistra - no se ha podido comprobar si el ejb de sesion de tramitacion existe.",de);
			}
			
		}    
    	catch( Exception exc )
    	{
    		logger.debug("Mantenimiento sesion sistra - no se ha podido comprobar si el ejb de sesion de tramitacion existe.",exc);    		
    	}

    	
    	logger.debug("Mantenimiento sesion sistra - retornando sesion tramitacion activa = " + sesionActiva);
        
    	byte[] encBytes = (sesionActiva + "").getBytes( "UTF-8" );
        response.reset();
        response.setContentLength(encBytes.length);
        response.setContentType("text/plain; charset=UTF-8");
        response.getOutputStream().write(encBytes);
        response.flushBuffer();
    }
}
