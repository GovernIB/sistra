package es.caib.bantel.front.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.bantel.front.form.BusquedaExpedientesForm;
import es.caib.bantel.model.GestorBandeja;
import es.caib.bantel.model.Procedimiento;
import es.caib.bantel.persistence.delegate.DelegateUtil;
import es.caib.bantel.persistence.delegate.GestorBandejaDelegate;
import es.caib.zonaper.modelInterfaz.FiltroBusquedaExpedientePAD;
import es.caib.zonaper.modelInterfaz.PaginaPAD;

/**
 * @struts.action
 *  name="busquedaExpedientesForm"
 *  path="/busquedaExpedientes"
 *  scope="session"
 *  validate="false"
 *  
 * @struts.action-forward
 *  name="success" path=".busquedaExpedientes"
 *
 * @struts.action-forward
 *  name="fail" path=".error"
 */
public class BusquedaExpedientesAction extends BaseAction
{
	private static final int LONGITUD_PAGINA = 10;
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		BusquedaExpedientesForm formularioBusqueda = ( BusquedaExpedientesForm ) form;
		
		
		FiltroBusquedaExpedientePAD filtro = new FiltroBusquedaExpedientePAD();
		List idsProc = new ArrayList();
		if (!("-1".equals(formularioBusqueda.getIdentificadorProcedimiento()))) {
			idsProc.add(formularioBusqueda.getIdentificadorProcedimiento());			
		} else {
			// Obtenemos tramites accesibles al gestor
			GestorBandejaDelegate gestorBandejaDelegate = DelegateUtil.getGestorBandejaDelegate();
			GestorBandeja gestor = gestorBandejaDelegate.obtenerGestorBandeja(this.getPrincipal(request).getName());
			if (gestor != null){			
				for (Iterator it = gestor.getProcedimientosGestionados().iterator(); it.hasNext();){
					Procedimiento proc = (Procedimiento) it.next();
					idsProc.add(proc.getIdentificador());
				}
			}
		}
		filtro.setIdentificadorProcedimientos(idsProc);
		if (formularioBusqueda.getAnyo() > 0) {
			filtro.setAnyo(formularioBusqueda.getAnyo());
			if (formularioBusqueda.getMes() > 0) {
				filtro.setMes(formularioBusqueda.getMes());
			}
		}
		filtro.setNifRepresentante(formularioBusqueda.getUsuarioNif());
		filtro.setNumeroEntradaBTE(formularioBusqueda.getNumeroEntrada());
		
		
		PaginaPAD page = es.caib.zonaper.persistence.delegate.DelegatePADUtil.getPadDelegate().busquedaPaginadaExpedientesGestor(filtro, formularioBusqueda.getPagina(), LONGITUD_PAGINA );
		
		request.setAttribute( "page", page );
		
		return mapping.findForward( "success" );
    }
	
	
}
