package es.caib.bantel.front.controller;

import java.util.List;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.tiles.ComponentContext;

import es.caib.bantel.front.util.Dominios;
import es.caib.bantel.persistence.delegate.ConfiguracionDelegate;
import es.caib.bantel.persistence.delegate.DelegateUtil;
import es.caib.sistra.plugins.PluginFactory;
import es.caib.sistra.plugins.login.PluginLoginIntf;

public class AltaAvisoController extends BaseController
{

		
	public void execute(ComponentContext tileContext,
			HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext) throws Exception
	{
		
		// Establecemos lista unidades administrativas
		List unidades=Dominios.listarUnidadesAdministrativas();
		request.setAttribute("unidades",unidades);
		
		// Establecemos url back
		String urlSistra = "";
		try{
			ConfiguracionDelegate delegate = DelegateUtil.getConfiguracionDelegate();
			Properties propsConfig = delegate.obtenerConfiguracion();
			urlSistra = propsConfig.getProperty("sistra.url.back");
		}catch (Exception e) {
			urlSistra = "";
		}
		request.setAttribute( "urlSistraAFirma", urlSistra );
		
		// Firmante
		PluginLoginIntf plgLogin = PluginFactory.getInstance().getPluginLogin();
		String nifFirmante = plgLogin.getNif(this.getPrincipal(request));
		request.setAttribute("nifFirmante", nifFirmante);
		
	}

}