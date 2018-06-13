package es.caib.zonaper.front.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.tiles.ComponentContext;
import org.apache.commons.lang.StringUtils;

import es.caib.zonaper.front.util.ZonapersFrontRequestHelper;
import es.caib.zonaper.persistence.delegate.DelegateUtil;

public class MenuAutenticadoController extends BaseController
{
	public void execute(ComponentContext tileContext,
			HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext) throws Exception
	{
		Map infoNotificaciones = new HashMap();
		
		String notExternas = StringUtils.defaultString(DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion().getProperty("notificaciones.enlaceExterno"), "");
		String infoNotNoSistra = StringUtils.defaultString(DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion().getProperty("notificaciones.noSistra.info.es"), "");
		
		infoNotificaciones.put("es", infoNotNoSistra);
		
		infoNotNoSistra = StringUtils.defaultString(DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion().getProperty("notificaciones.noSistra.info.ca"), "");
		infoNotificaciones.put("ca", infoNotNoSistra);
		
		infoNotNoSistra = StringUtils.defaultString(DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion().getProperty("notificaciones.noSistra.info.en"), "");
		infoNotificaciones.put("en", infoNotNoSistra);
		
		request.setAttribute("habilitarAlertas", Boolean.toString(ZonapersFrontRequestHelper.isHabilitarApartadoAlertas(request)));
		request.setAttribute("notificacionesExternas", notExternas);
		request.setAttribute("infoNotificacionsNoSistra", infoNotificaciones);
		
	}
	
	

}
