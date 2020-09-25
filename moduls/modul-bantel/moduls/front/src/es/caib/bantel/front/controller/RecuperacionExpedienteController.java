package es.caib.bantel.front.controller;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.tiles.ComponentContext;

import es.caib.bantel.front.Constants;
import es.caib.bantel.front.json.UnidadAdministrativa;
import es.caib.bantel.front.util.Dominios;

public class RecuperacionExpedienteController extends BaseController
{

	public void execute(ComponentContext tileContext,
			HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext) throws Exception
	{
		// Recuperamos descripcion unidad administrativa
		Long uniAdm = (Long) request.getSession().getAttribute(Constants.EXPEDIENTE_ACTUAL_UNIDADADMIN_KEY);
		List unidades = Dominios.getDescUnidadesAdministrativas(uniAdm);
		if(unidades!=null){
			UnidadAdministrativa uni = (UnidadAdministrativa) unidades.get(0);
			request.setAttribute("nombreUnidad",uni.getDescripcion());
		}

		// Indica si son obligatorios los avisos en la creacion del expediente
		request.setAttribute("obligatorioAvisos", request.getSession().getServletContext().getAttribute(Constants.GESTIONEXPEDIENTES_OBLIGATORIOAVISOS));

		// Indica si se permiten generar notificaciones
		request.setAttribute("generarNotificaciones", request.getSession().getServletContext().getAttribute(Constants.GESTIONEXPEDIENTES_GENERARNOTIFICACIONES));
	}

}