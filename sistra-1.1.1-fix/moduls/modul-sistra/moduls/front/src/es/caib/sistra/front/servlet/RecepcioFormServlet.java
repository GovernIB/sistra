package es.caib.sistra.front.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.sistra.front.Constants;
import es.caib.sistra.front.formulario.GestorFlujoFormulario;
import es.caib.sistra.front.util.FlujoFormularioRequestHelper;

/**
 * @web.servlet name="recepcioForm"
 * @web.servlet-mapping url-pattern="/recepcionFormulario"
 */
public class RecepcioFormServlet extends HttpServlet 
{
	private static Log logger = LogFactory.getLog(RecepcioFormServlet.class);
	
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    	logger.debug("Recepció formulari");        
    	logger.debug("Charset: " + request.getCharacterEncoding());
        
        String tokenGestionFormulario = "";
        String xmlInicial = "";
        String xmlFinal = "";
        if (FileUpload.isMultipartContent(request)) {
            try {
                DiskFileUpload fileUpload = new DiskFileUpload();
                List fileItems = fileUpload.parseRequest(request);

                for (int i = 0; i < fileItems.size(); i++) 
                {
                	
                    FileItem fileItem = (FileItem) fileItems.get(i);
                    if ( !fileItem.isFormField() )
                    {
	                    if (fileItem.getFieldName().equals(Constants.GESTOR_FORM_PARAM_XML_DATOS_INI)) 
	                    {
	                        xmlInicial = fileItem.getString();
	                    }
	
	                    if (fileItem.getFieldName().equals(Constants.GESTOR_FORM_PARAM_XML_DATOS_FIN)) 
	                    {
	                        xmlFinal = fileItem.getString();
	                    }
                    }
                    else
                    {
                    	if ( fileItem.getFieldName().equals( Constants.GESTOR_FORM_PARAM_ALMACENAMIENTO_GESTOR_FORMULARIO ) )
                    	{
                    		tokenGestionFormulario = fileItem.getString();
                    	}
                    }
                }
            } catch (FileUploadException e) {
                throw new UnavailableException("Error uploading", 1);
            }
        } else {
            xmlInicial 	= request.getParameter("xmlInicial");
            xmlFinal 	= request.getParameter("xmlFinal");
            tokenGestionFormulario = request.getParameter( Constants.GESTOR_FORM_PARAM_ALMACENAMIENTO_GESTOR_FORMULARIO );
        }

        logger.debug("XML Inicial: " + xmlInicial);
        logger.debug("XML Final: " + xmlFinal);
        
    	String token = null;
    	try
    	{
    		GestorFlujoFormulario gestorFormularios = FlujoFormularioRequestHelper.obtenerGestorFormulario( request,tokenGestionFormulario, false );
    		token = gestorFormularios.guardarDatosFormulario( xmlInicial, xmlFinal );
    	}
    	catch( Exception exc )
    	{
    		this.log( "Error intentando almacenar xml formulario", exc );
    		token = exc.getMessage();
    	}
		
        byte[] encBytes = token.getBytes( "UTF-8" );
		response.reset();
        response.setContentLength(encBytes.length);
        response.setContentType("text/plain; charset=UTF-8");
        response.getOutputStream().write(encBytes);
        response.flushBuffer();
	}
}
