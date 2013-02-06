package es.caib.zonaper.front.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.tiles.ComponentContext;

import es.caib.zonaper.model.DatosSesion;
import es.caib.zonaper.modelInterfaz.PersonaPAD;
import es.caib.zonaper.persistence.delegate.DelegateUtil;
import es.caib.zonaper.persistence.delegate.PadAplicacionDelegate;

public class DatosAlertasController extends BaseController
{

	public void execute(ComponentContext tileContext,
			HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext) throws Exception
	{
		DatosSesion datosUsuario = this.getDatosSesion( request );
		String codigoUsuario = datosUsuario.getCodigoUsuario(); 
		
		
		PadAplicacionDelegate padAplic = DelegateUtil.getPadAplicacionDelegate();
		PersonaPAD pers = null;
		if (request.getAttribute("persona1")!=null){
			pers = (PersonaPAD)request.getAttribute("persona1");
		}else{
			pers = padAplic.obtenerDatosPersonaPADporUsuario(codigoUsuario);
		}
		
		request.setAttribute( "persona", pers );
		
	}
}
