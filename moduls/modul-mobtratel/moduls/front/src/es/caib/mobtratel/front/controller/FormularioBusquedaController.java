package es.caib.mobtratel.front.controller;

import java.util.ArrayList;
import java.util.Calendar;
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


public class FormularioBusquedaController extends BaseController
{

	private static Integer months [] = new Integer[] { 
														new Integer( -1 ),
														new Integer( 0 ),
														new Integer( 1 ),
														new Integer( 2 ),
														new Integer( 3 ),
														new Integer( 4 ),
														new Integer( 5 ),
														new Integer( 6 ),
														new Integer( 7 ),
														new Integer( 8 ),
														new Integer( 9 ),
														new Integer( 10 ),
														new Integer( 11 )
														}; 
	
	public void execute(ComponentContext tileContext,
			HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext) throws Exception
	{
		
		// Establcemos valores combos
		request.setAttribute( "anyos", getYears() );
		request.setAttribute( "meses", getMonths() );
		
		// Establcemos valores cuentas
		List cuentas = getCuentas(request.getUserPrincipal().getName());
		if(cuentas.size() != 0) request.setAttribute( "cuentas", cuentas );

	}
	
	private List getYears()
	{
		ArrayList result = new ArrayList();
		int year = Calendar.getInstance().get(Calendar.YEAR);
		for (int i = 2000; i <= year;i++) {
			result.add( Integer.toString(i));
		}
		return result;
	}
	
	private List getMonths()
	{
		ArrayList result = new ArrayList();
		for ( int i = 0; i < months.length; i++ )
		{
			result.add( months[i] );
		}
		return result;
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
				result.add( permiso.getCuenta() );
			}
		} catch (DelegateException e) {
			e.printStackTrace();
		}
		return result;
	}


}
