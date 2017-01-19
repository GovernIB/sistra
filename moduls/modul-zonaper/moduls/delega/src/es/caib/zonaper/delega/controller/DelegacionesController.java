package es.caib.zonaper.delega.controller;

import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.tiles.ComponentContext;

import es.caib.sistra.plugins.PluginFactory;
import es.caib.sistra.plugins.login.PluginLoginIntf;
import es.caib.zonaper.persistence.delegate.DelegateUtil;


public class DelegacionesController extends BaseController
{

	public void execute(ComponentContext tileContext,
			HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext) throws Exception
	{
		Properties props = DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion();
		String firmarDelegacionRepresentante = props.getProperty("delega.firmarDelegacionRepresentante");
		if (firmarDelegacionRepresentante == null || !firmarDelegacionRepresentante.equals("false")) {
			firmarDelegacionRepresentante = "true";
		}
		request.setAttribute("firmarDelegacionRepresentante",firmarDelegacionRepresentante);	
		
		String urlSistra = props.getProperty("sistra.url.back");
		request.setAttribute( "urlSistraAFirma", urlSistra );
		
		PluginLoginIntf plgLogin = PluginFactory.getInstance().getPluginLogin();
		String nifFirmante = plgLogin.getNif(this.getPrincipal(request));
		request.setAttribute("nifFirmante", nifFirmante);
		
	}
	
}
