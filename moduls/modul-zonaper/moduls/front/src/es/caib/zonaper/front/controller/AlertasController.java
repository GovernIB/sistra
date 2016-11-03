package es.caib.zonaper.front.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.tiles.ComponentContext;

import es.caib.zonaper.persistence.delegate.DelegateUtil;

public class AlertasController extends BaseController
{

	public void execute(ComponentContext tileContext,
			HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext) throws Exception
	{
		String confAlertasSmsZonaPersonal = DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion().getProperty("avisos.smsAlertas");
		String alertasSmsZonaPersonal;
		if (StringUtils.isNotBlank(confAlertasSmsZonaPersonal) && "true".equals(confAlertasSmsZonaPersonal)) {
			alertasSmsZonaPersonal = "true";
		} else {
			alertasSmsZonaPersonal = "false";
		}		
		request.setAttribute("alertasSmsZonaPersonal", alertasSmsZonaPersonal);
	}
}
