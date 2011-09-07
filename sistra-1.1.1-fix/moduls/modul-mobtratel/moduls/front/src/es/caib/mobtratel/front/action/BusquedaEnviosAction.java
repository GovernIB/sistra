package es.caib.mobtratel.front.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.mobtratel.front.form.BusquedaEnviosForm;
import es.caib.mobtratel.model.CriteriosBusquedaEnvio;
import es.caib.mobtratel.model.Page;
import es.caib.mobtratel.persistence.delegate.DelegateUtil;
import es.caib.mobtratel.persistence.delegate.EnvioDelegate;


/**
 * @struts.action
 *  name="busquedaEnviosForm"
 *  path="/busquedaEnvios"
 *  scope="session"
 *  validate="false"
 *  
 * @struts.action-forward
 *  name="success" path=".busqueda"
 *
 * @struts.action-forward
 *  name="fail" path=".error"
 */
public class BusquedaEnviosAction extends BaseAction
{
	private static final int LONGITUD_PAGINA = 10;
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		BusquedaEnviosForm formularioBusqueda = ( BusquedaEnviosForm ) form;
		EnvioDelegate delegate = DelegateUtil.getEnvioDelegate();
		CriteriosBusquedaEnvio criterios = new CriteriosBusquedaEnvio();
		criterios.setAnyo( formularioBusqueda.getAnyo() );		
		criterios.setMes( formularioBusqueda.getMes() );
		criterios.setEnviado( formularioBusqueda.getEnviado() );
		criterios.setTipo( formularioBusqueda.getTipo() );
		criterios.setCuenta( formularioBusqueda.getCuenta() );
		
		Page page = delegate.busquedaPaginadaEnvios( criterios, formularioBusqueda.getPagina(), LONGITUD_PAGINA );
		
		request.setAttribute( "page", page );
		
		return mapping.findForward( "success" );
    }
	
	
}
