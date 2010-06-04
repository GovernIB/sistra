package es.caib.sistra.front.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import es.caib.sistra.front.Constants;
import es.caib.sistra.front.formulario.GestorFlujoFormulario;
import es.caib.sistra.front.util.FlujoFormularioRequestHelper;

/**
 * @web.servlet name="cancelarForm"
 * @web.servlet-mapping url-pattern="/cancelarForm"
 */
public class CancelarFormServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        log("Cancel�laci� formulari");
        
        String tokenGestionFormulario = request.getParameter( Constants.GESTOR_FORM_PARAM_ALMACENAMIENTO_GESTOR_FORMULARIO );
    	String token = null;
    	try
    	{
    		GestorFlujoFormulario gestorFormularios = FlujoFormularioRequestHelper.obtenerGestorFormulario( request, tokenGestionFormulario, false );
    		token = gestorFormularios.cancelarFormulario();
    	}
    	catch( Exception exc )
    	{
    		this.log( "Error intentando cancelar proceso formualario", exc );
    		token = exc.getMessage();
    	}
        log("Tornant token: " + token);
        
    	byte[] encBytes = token.getBytes( "UTF-8" );
        response.reset();
        response.setContentLength(encBytes.length);
        response.setContentType("text/plain; charset=UTF-8");
        response.getOutputStream().write(encBytes);
        response.flushBuffer();
    }
}
