package es.caib.sistra.front.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.sistra.front.Constants;
import es.caib.sistra.front.formulario.GestorFlujoFormulario;
import es.caib.sistra.front.util.FlujoFormularioRequestHelper;

/**
 * @web.servlet name="cancelarForm"
 * @web.servlet-mapping url-pattern="/cancelarForm"
 */
public class CancelarFormServlet extends HttpServlet {

	private static Log logger = LogFactory.getLog(CancelarFormServlet.class);
	
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    	String tokenGestionFormulario = request.getParameter( Constants.GESTOR_FORM_PARAM_ALMACENAMIENTO_GESTOR_FORMULARIO );
    	
    	logger.debug( "DEBUGFORM: CancelarFormServlet - Id gestor form: " + tokenGestionFormulario + " - Charset: " + request.getCharacterEncoding());
    	
    	String token = null;
    	String msgError = null;
    	try
    	{
    		GestorFlujoFormulario gestorFormularios = FlujoFormularioRequestHelper.obtenerGestorFormulario( request, tokenGestionFormulario);
    		if (gestorFormularios == null) {
    			throw new Exception("No se encuentra gestor de formularios en contexto");
    		}
    		token = gestorFormularios.cancelarFormulario();
    		if (token == null) {
    			msgError = "Error cancelando datos formulario en gestor formulario";
    		}
    	}
    	catch( Exception exc )
    	{
    		logger.error( "Error intentando cancelar proceso formulario", exc );
    		msgError = "Excepcion: " + exc.getMessage();
    	}
    	
        
    	if (token == null) {
    		response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, msgError);    	    
    	} else {
	    	byte[] encBytes = token.getBytes( "UTF-8" );
	        response.reset();
	        response.setContentLength(encBytes.length);
	        response.setContentType("text/plain; charset=UTF-8");
	        response.getOutputStream().write(encBytes);
	        response.flushBuffer();
    	}
    }
}
