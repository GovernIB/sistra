package es.caib.sistra.front.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.tiles.ComponentContext;

import es.caib.sistra.model.DocumentoConsultaFront;

public class ResultadoConsultaController extends TramiteController
{
	// Se recibirá en params la siguiente informacion :
	// documentosConsulta 
	public void execute(ComponentContext tileContext, HttpServletRequest request, HttpServletResponse response, ServletContext servletContext) throws Exception
	{
		Map params = this.getParametros( request );
		List listaDocumentos = ( List ) params.get( "documentosConsulta" );
		request.setAttribute( "numeroDocumentos", new Integer( listaDocumentos.size() ) );
		if ( listaDocumentos.size() > 0 )
		{
			request.setAttribute( "documentoUnico", listaDocumentos.get( 0 ) );
		}
		else
		{
			request.setAttribute( "documentoUnico", new DocumentoConsultaFront() );
		}
	}

}
