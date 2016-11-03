package es.caib.zonaper.helpdesk.front.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.tiles.ComponentContext;

import es.caib.util.CredentialUtil;
import es.caib.zonaper.helpdesk.front.Constants;
import es.caib.zonaper.helpdesk.front.plugins.PluginAudita;
import es.caib.zonaper.helpdesk.front.util.Util;



public class FormularioBusquedaController extends BaseController
{

	
	public void execute(ComponentContext tileContext,
			HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext) throws Exception
	{
		
		ArrayList tiposAutenticacion = new ArrayList();
		tiposAutenticacion.add(String.valueOf(CredentialUtil.NIVEL_AUTENTICACION_ANONIMO));
		tiposAutenticacion.add(String.valueOf(CredentialUtil.NIVEL_AUTENTICACION_CERTIFICADO));
		tiposAutenticacion.add(String.valueOf(CredentialUtil.NIVEL_AUTENTICACION_USUARIO));
		request.setAttribute("nivelAutenticacion",tiposAutenticacion);
		
		List lstTramites = new ArrayList();
		Map descTramites = PluginAudita.getInstance().obtenerDescripcionTramites(Constants.DEFAULT_LANG);
		Set keys = descTramites.entrySet();
		for(Iterator it = keys.iterator(); it.hasNext(); )
		{
			lstTramites.add(it.next());
		}
		request.setAttribute("tramites",Util.concatena(descTramites));
		
	}
	

}
