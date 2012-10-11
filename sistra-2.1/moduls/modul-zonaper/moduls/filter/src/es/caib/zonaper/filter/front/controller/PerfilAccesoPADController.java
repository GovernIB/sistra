package es.caib.zonaper.filter.front.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.tiles.ComponentContext;

import es.caib.zonaper.model.Delegacion;
import es.caib.zonaper.modelInterfaz.PersonaPAD;
import es.caib.zonaper.persistence.delegate.DelegateException;
import es.caib.zonaper.persistence.delegate.DelegatePADUtil;
import es.caib.zonaper.persistence.delegate.DelegateUtil;
import es.caib.zonaper.persistence.delegate.PadDelegate;

public class PerfilAccesoPADController extends MainController
{
	
	public void performTask(ComponentContext tileContext, HttpServletRequest request, HttpServletResponse response, ServletContext servletContext) throws ServletException, IOException
	{
		String urlOriginal = ( request.getParameter( "urlOriginal" ) );
		request.setAttribute( "urlOriginal", urlOriginal );	
		
		try {
			// Obtenemos lista de delegaciones
			List delegaciones = DelegateUtil.getDelegacionDelegate().obtenerDelegacionesUsuario();
			
			// Obtenemos datos de entidades delegadas
			List datosPersonas = new ArrayList();
			PadDelegate pad = DelegatePADUtil.getPadDelegate();			
			for (Iterator it = delegaciones.iterator();it.hasNext();){
				Delegacion delegacion = (Delegacion) it.next();
				PersonaPAD p = pad.obtenerDatosPersonaPADporNif(delegacion.getNifDelegante());
				datosPersonas.add(p);
			}
			request.setAttribute( "listaEntidadesDelegadas", datosPersonas );
			
		} catch (DelegateException ex) {
			throw new ServletException("Error accediendo a lista de delegaciones usuario",ex);
		}
		
	}
	
	
}

