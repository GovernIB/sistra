package es.caib.sistra.front.controller;


import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.Globals;
import org.apache.struts.tiles.ComponentContext;
import org.apache.struts.util.MessageResources;

import es.caib.sistra.front.Constants;
import es.caib.sistra.model.OrganismoInfo;
import es.caib.sistra.model.TramiteFront;
import es.caib.sistra.persistence.delegate.ConfiguracionDelegate;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.util.ContactoUtil;

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
		
		String urlSistra = "";
		ConfiguracionDelegate delegate = DelegateUtil.getConfiguracionDelegate();
		Properties propsConfig = delegate.obtenerConfiguracion();
		urlSistra = propsConfig.getProperty("sistra.url");
		
		request.setAttribute( "urlSistraAFirma", urlSistra );
		
		
		// Generamos literal de contacto
		MessageResources messages = (MessageResources) request.getAttribute(Globals.MESSAGES_KEY);
		OrganismoInfo oi = (OrganismoInfo) servletContext.getAttribute(Constants.ORGANISMO_INFO_KEY);
		
		String telefono = oi.getTelefonoIncidencias();
		String email = oi.getEmailSoporteIncidencias();
		String url = oi.getUrlSoporteIncidencias();
		boolean formulario = oi.getFormularioIncidencias();
		String tituloTramite = null;
		if (tramite != null) {
			tituloTramite = tramite.getDescripcion();
		}
		String lang = ((java.util.Locale) request.getSession().getAttribute(org.apache.struts.Globals.LOCALE_KEY)).getLanguage();
		
		/*
		String literalContacto = ContactoUtil.generarLiteralContacto(messages, telefono, email, url,
			tituloTramite, lang);
			*/
		String literalContacto = ContactoUtil.generarLiteralContacto(telefono, email, url,
				tituloTramite, formulario, lang);
		
		request.setAttribute("literalContacto", literalContacto);
		
	}
	
	

}
