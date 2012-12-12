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
		String nombre = "";
		String primerApellido = "";
		String segundoApellido = "";

		
		boolean isPersonaFisica = !NifCif.esCIF( plgLogin.getNif(principal) );
		
		if ( isPersonaFisica )
		{
			String[] componentes = fullName.split( "\\s" );
			if ( componentes != null )
			{
				// Tomamos que el orden sera: apellidos nombre
				String componente0 = "", componente1 = "", componente2 ="";
				componente0 = componentes[0];
				if ( componentes.length > 1 )
				{
					componente1 = componentes[1];
				}
				if ( componentes.length > 2 )
				{
					StringBuffer sbComponente2 = new StringBuffer();
					for ( int i = 2; i < componentes.length; i++  )
					{
						sbComponente2.append( componentes[ i ] );
						if ( i != componentes.length -1 )
							sbComponente2.append( " " );
					}
					componente2 = sbComponente2.toString();
				}
				
				// Si es extranjero puede no tener segundo apellido
				if ("".equals(componente2)) {
					nombre = componente1;
					primerApellido = componente0;
				} else {
					nombre = componente2;
					primerApellido = componente0;
					segundoApellido = componente1;
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

