package es.caib.bantel.front.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.tiles.ComponentContext;

import es.caib.bantel.model.GestorBandeja;
import es.caib.bantel.model.Procedimiento;
import es.caib.bantel.persistence.delegate.DelegateUtil;
import es.caib.bantel.persistence.delegate.GestorBandejaDelegate;

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
		// Obtenemos tramites accesibles al gestor
		GestorBandejaDelegate gestorBandejaDelegate = DelegateUtil.getGestorBandejaDelegate();
		GestorBandeja gestor = gestorBandejaDelegate.obtenerGestorBandeja(this.getPrincipal(request).getName());
		List procedimientos = new ArrayList();
		// Si no existe devolvemos lista de tramites gestionados vacía
		String permitirCambioEstadoMasivo = "N";
		if (gestor != null && gestor.getProcedimientosGestionados() != null){			
			for (Iterator it = gestor.getProcedimientosGestionados().iterator(); it.hasNext();){
				Procedimiento proc = (Procedimiento) it.next();
				procedimientos.add(proc);
			}
			permitirCambioEstadoMasivo = Character.toString(gestor.getPermitirCambioEstadoMasivo());
		}
		
		// Establcemos valores combos
		request.setAttribute( "procedimientos", procedimientos );
		request.setAttribute( "anyos", getYears() );
		request.setAttribute( "meses", getMonths() );
		request.setAttribute("permitirCambioEstadoMasivo",permitirCambioEstadoMasivo);
	}
	
	private List getYears()
	{
		ArrayList result = new ArrayList();
		result.add( "2001" );
		result.add( "2002" );
		result.add( "2003" );
		result.add( "2004" );
		result.add( "2005" );
		result.add( "2006" );
		result.add( "2007" );
		result.add( "2008" );
		result.add( "2009" );
		result.add( "2010" );
		result.add( "2011" );
		result.add( "2012" );
		result.add( "2013" );
		result.add( "2014" );
		result.add( "2015" );
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

}
