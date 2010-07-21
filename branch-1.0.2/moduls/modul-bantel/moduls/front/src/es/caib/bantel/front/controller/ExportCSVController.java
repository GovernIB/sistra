package es.caib.bantel.front.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.tiles.ComponentContext;

import es.caib.bantel.model.GestorBandeja;
import es.caib.bantel.model.Tramite;
import es.caib.bantel.persistence.delegate.DelegateUtil;
import es.caib.bantel.persistence.delegate.GestorBandejaDelegate;

public class ExportCSVController extends BaseController
{

	public void execute(ComponentContext tileContext,
			HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext) throws Exception
	{
		// Obtenemos tramites accesibles al gestor
		GestorBandejaDelegate gestorBandejaDelegate = DelegateUtil.getGestorBandejaDelegate();
		GestorBandeja gestor = gestorBandejaDelegate.obtenerGestorBandeja(this.getPrincipal(request).getName());
		List lstTramites = new ArrayList();
		// Si no existe devolvemos lista de tramites gestionados vacía
		if (gestor != null){			
			for (Iterator it = gestor.getTramitesGestionados().iterator(); it.hasNext();){
				Tramite tramite = (Tramite) it.next();				
				if (tramite.getArchivoFicheroExportacion() != null) lstTramites.add(tramite);
			}
		}
		
		// Establcemos valores combos
		request.setAttribute( "tramites", lstTramites );		
	}
	
}
