package es.caib.zonaper.filter.front.controller;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.tiles.ComponentContext;

import es.caib.zonaper.filter.AuthException;

public class ErrorPADController extends MainController
{
	private static Log _log = LogFactory.getLog( ErrorPADController.class );
	
	private static String KEY_ERROR_DESCONOCIDO = "actualizarDatosPAD.error.desconocido";
	private static String KEY_ERROR_CONEXION_PAD = "actualizarDatosPAD.error.conexionPAD";
	private static String KEY_ERROR_NIF_NO_CONCUERDA = "actualizarDatosPAD.error.nifNoConcuerda";
	private static String KEY_ERROR_NO_NIF = "actualizarDatosPAD.error.noNif";
	private static String KEY_ERROR_NIF_YA_EXISTE = "actualizarDatosPAD.error.nifYaExiste";
	
	public void performTask(ComponentContext tileContext, HttpServletRequest request, HttpServletResponse response, ServletContext servletContext) throws ServletException, IOException
	{
		int error = Integer.parseInt(( request.getParameter( "error" )));
		_log.debug("Mostrando pantalla error PAD con codigo error " + error);
		
		String keyError = KEY_ERROR_DESCONOCIDO;
		switch (error){
			case AuthException.ERROR_CONEXION_PAD:
				keyError = KEY_ERROR_CONEXION_PAD;
				break;
			case AuthException.ERROR_NIF_NO_CONCUERDA:
				keyError = KEY_ERROR_NIF_NO_CONCUERDA;
				break;			
			case AuthException.ERROR_NO_NIF:
				keyError = KEY_ERROR_NO_NIF;
				break;
			case AuthException.ERROR_NIF_YA_EXISTE:
				keyError = KEY_ERROR_NIF_YA_EXISTE;
				break;
		}
		
		request.setAttribute( "keyError", keyError );				
	}
	
	
}

