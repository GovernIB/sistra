package es.caib.zonaper.front.controller;

import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.tiles.ComponentContext;

import es.caib.zonaper.front.Constants;
import es.caib.zonaper.model.DatosSesion;
import es.caib.zonaper.model.EstadoExpediente;
import es.caib.zonaper.model.Expediente;
import es.caib.zonaper.model.Page;
import es.caib.zonaper.modelInterfaz.ConstantesZPE;
import es.caib.zonaper.persistence.delegate.DelegateUtil;

public class EstadoExpedientesController extends BaseController
{
	private static final int LONGITUD_PAGINA = 10;

	
	public void execute(ComponentContext tileContext, HttpServletRequest request, HttpServletResponse response, ServletContext servletContext) throws Exception
	{		 
		DatosSesion datosSesion = this.getDatosSesion(request);
		
		// Obtenemos pagina a mostrar
		String strPage = request.getParameter( "pagina" );
		if (StringUtils.isEmpty( strPage)){
			strPage =(String) request.getSession().getAttribute(Constants.PAGE_KEY); 
		}
		strPage = StringUtils.isEmpty( strPage ) ? "0" : strPage;
		int pagina = Integer.parseInt( strPage, 10 );	
		
		// Obtenemos filtro a mostrar (almacenado en sesion por action de filtrar)
		String filtro = (String) request.getSession().getAttribute(Constants.FILTRO_KEY); 
		List filtroExpe = null;
		if (StringUtils.isNotEmpty( filtro)){
			filtroExpe =(List) request.getSession().getAttribute(Constants.FILTRO_LISTA_KEY); 			
		}
		
		// Almacenamos en session la pagina xa despues volver a esa pagina
		request.getSession().setAttribute(Constants.PAGE_KEY,strPage);
		
		// Realizamos consulta de la pagina
		Page page = null;
		if (ConstantesZPE.DELEGACION_PERFIL_ACCESO_DELEGADO.equals( datosSesion.getPerfilAcceso())){
			page = DelegateUtil.getEstadoExpedienteDelegate().busquedaPaginadaExpedientesEntidadDelegada( pagina, LONGITUD_PAGINA, datosSesion.getNifEntidad(), filtroExpe );
		}else{
			page = DelegateUtil.getEstadoExpedienteDelegate().busquedaPaginadaExpedientes( pagina, LONGITUD_PAGINA, filtroExpe );
		}
		
		// Comprobamos si hay que poner pie de entrega documentacion presencial
		String pieDocPresencial = "N"; 
		for (Iterator it=page.getList().iterator();it.hasNext();){
			EstadoExpediente ee = (EstadoExpediente) it.next();
			if (ConstantesZPE.ESTADO_SOLICITUD_ENVIADA_PENDIENTE_DOCUMENTACION_PRESENCIAL.equals(ee.getEstado())){
				pieDocPresencial = "S";
				break;
			}
		}
		
		// Pasamos atributos a la pagina
		request.setAttribute( "filtro", filtro );
		request.setAttribute( "page", page );
		request.setAttribute( "pieDocPresencial", pieDocPresencial );
	}
}
