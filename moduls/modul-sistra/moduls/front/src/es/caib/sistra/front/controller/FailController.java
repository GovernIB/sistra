package es.caib.sistra.front.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.tiles.ComponentContext;

import es.caib.sistra.front.util.InstanciaManager;
import es.caib.sistra.front.util.TramiteRequestHelper;
import es.caib.sistra.model.MensajeFront;
import es.caib.sistra.persistence.delegate.DelegateException;

public class FailController extends BaseController
{

	public void execute(ComponentContext tileContext,
			HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext) throws Exception
	{
		
		MensajeFront messageSet = TramiteRequestHelper.getMessage( request ); 
		
		// En principio, la situacion más grave que se puede producir.
		// Se trata de un error no recuperable, posiblemente una excepción ya que
		// ni siquiera ha llegado un mensaje
		if ( messageSet == null )
		{
			//TramiteRequestHelper.setErrorMessage( request, )
			//TramiteRequestHelper.setErrorMessage( request, "errors.exception", new Object[]{ request.getAttribute( "exception" ) } );
			//TramiteRequestHelper.setErrorMessage( request, "errors.errorNoContinuable" );
			
			// Mostramos info de debug de la excepcion
			Exception ex = (Exception) request.getAttribute("exception");
			String exceptionMessage = null;
			if (ex != null){
				if ( (ex instanceof DelegateException) && (ex.getCause() != null) ){
					exceptionMessage = ex.getCause().getMessage();
				}else{
					exceptionMessage = ex.getMessage();
				}				
			}
			
			TramiteRequestHelper.setErrorMessage( request, "errors.errorNoContinuable", null, null, exceptionMessage );
			messageSet = TramiteRequestHelper.getMessage( request ); 
		}
		// Tratamiento de errores no continuables
		// Si es posible, desactivar el ejb de sesion.
		if ( messageSet.getTipo() == MensajeFront.TIPO_ERROR )
		{
			String idInstancia = (String ) request.getAttribute( InstanciaManager.ID_INSTANCIA );
			if ( idInstancia != null )
			{
				InstanciaManager.desregistrarInstancia( request );
			}
		}
		// Tratamiento de errores continuables

	}

}
