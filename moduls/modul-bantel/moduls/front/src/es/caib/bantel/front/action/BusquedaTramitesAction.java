package es.caib.bantel.front.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.bantel.front.form.BusquedaTramitesForm;
import es.caib.bantel.front.util.UtilBantelFront;
import es.caib.bantel.model.CriteriosBusquedaTramite;
import es.caib.bantel.model.Page;
import es.caib.bantel.persistence.delegate.DelegateUtil;
import es.caib.bantel.persistence.delegate.TramiteBandejaDelegate;

/**
 * @struts.action
 *  name="busquedaTramitesForm"
 *  path="/busquedaTramites"
 *  scope="session"
 *  validate="false"
 *  
 * @struts.action-forward
 *  name="success" path=".busqueda"
 *
 * @struts.action-forward
 *  name="fail" path=".error"
 */
public class BusquedaTramitesAction extends BaseAction
{	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		BusquedaTramitesForm formularioBusqueda = ( BusquedaTramitesForm ) form;
		TramiteBandejaDelegate delegate = DelegateUtil.getTramiteBandejaDelegate();
		
		CriteriosBusquedaTramite criterios = UtilBantelFront.crearCriteriosBusquedaTramite(formularioBusqueda);
		
		Page page = delegate.busquedaPaginadaTramites( criterios, formularioBusqueda.getPagina(), formularioBusqueda.getLongitudPagina() );
		
		request.setAttribute( "page", page );
		
		return mapping.findForward( "success" );
    }

	
	
}
