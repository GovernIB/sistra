package es.caib.sistra.front.controller;


import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.tiles.ComponentContext;

import es.caib.sistra.front.Constants;
import es.caib.sistra.model.TramiteFront;

/**
 * El objetivo del controller de tramite es controlar la renderización del paso del trámite,
 * haciendo que se activen los elementos de Interfaz de Usuario correspondientes al paso
 * @author clmora
 *
 */
public class TramiteController extends BaseController
{
	
	public void execute(ComponentContext tileContext,
			HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext) throws Exception
	{
		String zonaPasoTramite = ( String ) tileContext.getAttribute( "zonaPasoTramite" );
		TramiteFront tramite = this.getTramiteFront( request );
		boolean tramiteReducido = tramite != null && tramite.isCircuitoReducido();
		request.setAttribute( Constants.TRAMITE_REDUCIDO_KEY, Boolean.valueOf( tramiteReducido ) );
		
		request.setAttribute( "zonaPasoTramite", StringUtils.defaultString( zonaPasoTramite, "" ) );
		
		String mostrarPasosNav = ( String ) tileContext.getAttribute( "mostrarPasosNav" );
		request.setAttribute( "mostrarPasosNav", StringUtils.defaultString( mostrarPasosNav, "false" ) );
		
		request.setAttribute( Constants.PRINCIPAL_KEY, this.getPrincipal( request ) );
		request.setAttribute( Constants.MODO_AUTENTICACION_KEY, String.valueOf( this.getMetodoAutenticacion( request ) ));
		
	}
	
	

}
