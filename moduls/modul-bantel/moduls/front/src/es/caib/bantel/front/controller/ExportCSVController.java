package es.caib.bantel.front.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.tiles.ComponentContext;

import es.caib.bantel.model.FicheroExportacion;
import es.caib.bantel.model.GestorBandeja;
import es.caib.bantel.model.Procedimiento;
import es.caib.bantel.model.TramiteExportarCSV;
import es.caib.bantel.persistence.delegate.DelegateUtil;
import es.caib.bantel.persistence.delegate.FicheroExportacionDelegate;
import es.caib.bantel.persistence.delegate.GestorBandejaDelegate;
import es.caib.bantel.persistence.delegate.TramiteBandejaDelegate;

public class ExportCSVController extends BaseController
{

	public void execute(ComponentContext tileContext,
			HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext) throws Exception
	{
		
		
		// Obtenemos tramites con ficheros de exportacion de procedimientos accesibles al gestor
		GestorBandejaDelegate gestorBandejaDelegate = DelegateUtil.getGestorBandejaDelegate();
		GestorBandeja gestor = gestorBandejaDelegate.obtenerGestorBandeja(this.getPrincipal(request).getName());
		TramiteBandejaDelegate tramiteBandejaDelegate = DelegateUtil.getTramiteBandejaDelegate();
		FicheroExportacionDelegate ficheroExportacionDelegate = DelegateUtil.getFicheroExportacionDelegate();
		List lstTramitesCSV = new ArrayList();
		if (gestor != null && gestor.getProcedimientosGestionados() != null ){			
			for (Iterator it = gestor.getProcedimientosGestionados().iterator(); it.hasNext();){
				Procedimiento procedimiento = (Procedimiento) it.next();
				// Buscamos tramites del procedimiento
				String[] idsTramite = tramiteBandejaDelegate.obtenerIdTramitesProcedimiento(procedimiento.getIdentificador(), null, null, null);
				// Comprobamos si los tramites del procedimiento tienen ficheros de exportacion
				for (int i = 0 ; i < idsTramite.length ; i++){
					FicheroExportacion fic = ficheroExportacionDelegate.findFicheroExportacion(idsTramite[i]);
					if (fic != null) {
						TramiteExportarCSV tcsv = new TramiteExportarCSV();
						tcsv.setIdProcedimientoTramite(procedimiento.getCodigo() + "@#@" + fic.getIdentificadorTramite());
						tcsv.setDescripcion("Procedimiento: " + procedimiento.getIdentificador() + " - Trámite: " + fic.getIdentificadorTramite());
						lstTramitesCSV.add(tcsv);
					}
				}				
			}
		}
		
		// Establcemos valores combos
		request.setAttribute( "tramitesCSV", lstTramitesCSV );
				
	}
	
}
