package es.caib.zonaper.filter.front.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.tiles.ComponentContext;
import org.apache.commons.lang.StringUtils;

import es.caib.sistra.plugins.PluginFactory;
import es.caib.sistra.plugins.login.PluginLoginIntf;
import es.caib.util.CredentialUtil;
import es.caib.util.NifCif;
import es.caib.zonaper.filter.front.util.LangUtil;
import es.caib.util.NormalizacionNombresUtil;

public class DatosPADController extends MainController
{
	
	public void performTask(ComponentContext tileContext, HttpServletRequest request, HttpServletResponse response, ServletContext servletContext) throws ServletException, IOException
	{
		String urlOriginal = ( request.getParameter( "urlOriginal" ) );
		Principal principal = request.getUserPrincipal();
		Locale locale = LangUtil.getLocale( request );
		
		PluginLoginIntf plgLogin=null;
		try {
			plgLogin = PluginFactory.getInstance().getPluginLogin();
		} catch (Exception e) {
			throw new ServletException(e);
		}
		String fullName = NormalizacionNombresUtil.capitalizeAllTokens( plgLogin.getNombreCompleto(principal), locale );
		String[] componentes = fullName.split( "\\s" );
		String nombre = "";
		String primerApellido = "";
		String segundoApellido = "";

		
		boolean isPersonaFisica = !NifCif.esCIF( plgLogin.getNif(principal) );
		
		if ( isPersonaFisica )
		{
			if ( componentes != null )
			{
				//nombre = NormalizacionNombresUtil.capitalize( componentes[0], locale );
				nombre = componentes[0];
				
				if ( componentes.length > 1 )
				{
					//primerApellido = NormalizacionNombresUtil.capitalize( componentes[1], locale );
					primerApellido = componentes[1];
				}
				if ( componentes.length > 2 )
				{
					//segundoApellido = componentes[2];
					StringBuffer sbSegundoApellido = new StringBuffer();
					for ( int i = 2; i < componentes.length; i++  )
					{
						//sbSegundoApellido.append( NormalizacionNombresUtil.capitalize( componentes[ i ], locale ) );
						sbSegundoApellido.append( componentes[ i ] );
						if ( i != componentes.length -1 )
							sbSegundoApellido.append( " " );
					}
					segundoApellido = sbSegundoApellido.toString();
				}
			}
		}
		else
		{
			nombre = fullName;
		}
		String keyInstrucciones = plgLogin.getMetodoAutenticacion(principal) == CredentialUtil.NIVEL_AUTENTICACION_USUARIO ? "actualizarDatosPAD.instrucciones.usuario" : "actualizarDatosPAD.instrucciones.certificado";
		
		request.setAttribute( "fullName", fullName );
		request.setAttribute( "nombre", StringUtils.defaultString(request.getParameter( "nombre" ), nombre  ) );
		request.setAttribute( "apellido1", StringUtils.defaultString(request.getParameter( "apellido1" ), primerApellido ) );
		request.setAttribute( "apellido2", StringUtils.defaultString(request.getParameter( "apellido2" ), segundoApellido ) );
		request.setAttribute( "keyInstrucciones", keyInstrucciones );
		request.setAttribute( "urlOriginal", urlOriginal );
		request.setAttribute( "isPersonaFisica", Boolean.valueOf( isPersonaFisica ) );
	}
	
	
}

