package es.caib.mobtratel.front.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.tiles.ComponentContext;

import es.caib.mobtratel.model.Permiso;
import es.caib.mobtratel.persistence.delegate.DelegateException;
import es.caib.mobtratel.persistence.delegate.DelegateUtil;
import es.caib.mobtratel.persistence.delegate.PermisoDelegate;


public class EditarEnvioSmsController extends BaseController
{

	
	public void execute(ComponentContext tileContext,
			HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext) throws Exception
	{

		// Establcemos valores combos
		List cuentas = getCuentas(request.getUserPrincipal().getName());
		if(cuentas.size() != 0) request.setAttribute( "cuentas", cuentas );
		request.setAttribute("horas",getHoras());
	}
	
	private List getCuentas(String usuarioSeycon)
	{
		PermisoDelegate pd = DelegateUtil.getPermisoDelegate();
		List permisos;
		ArrayList result = new ArrayList();
		try {
			permisos = pd.listarPermisos(usuarioSeycon);
			for (Iterator it=permisos.iterator(); it.hasNext();)
			{
				Permiso permiso = (Permiso) it.next();
				if(permiso.getSms() == Permiso.SI)	result.add( permiso.getCuenta() );
			}
		} catch (DelegateException e) {
			e.printStackTrace();
		}
		return result;
	}


}
