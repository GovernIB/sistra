package es.caib.mobtratel.front.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.mobtratel.front.form.BusquedaEstadisticasSMSForm;
import es.caib.mobtratel.persistence.delegate.DelegateUtil;
import es.caib.mobtratel.persistence.delegate.EnvioDelegate;


/**
 * @struts.action
 *  name="busquedaEstadisticasSMSForm"
 *  path="/busquedaEstadisticasSMS"
 *  scope="session"
 *  validate="false"
 *  
 * @struts.action-forward
 *  name="success" path=".busquedaEstadisticasSMS"
 *
 * @struts.action-forward
 *  name="fail" path=".error"
 */
public class BusquedaEstadisiticasSMSAction extends BaseAction
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		BusquedaEstadisticasSMSForm formularioBusqueda = ( BusquedaEstadisticasSMSForm ) form;
		
		
		EnvioDelegate delegate = DelegateUtil.getEnvioDelegate();
		
		List estadisticas = delegate.obtenerEstadisticasSMS(formularioBusqueda.getAnyo(), formularioBusqueda.getCuenta());
		/*
		CriteriosBusquedaEnvio criterios = new CriteriosBusquedaEnvio();
		criterios.setAnyo( formularioBusqueda.getAnyo() );		
		criterios.setMes( formularioBusqueda.getMes() );
		criterios.setEnviado( formularioBusqueda.getEnviado() );
		criterios.setTipo( formularioBusqueda.getTipo() );
		criterios.setCuenta( formularioBusqueda.getCuenta() );
		
		Page page = delegate.busquedaPaginadaEnvios( criterios, formularioBusqueda.getPagina(), LONGITUD_PAGINA );
		
		request.setAttribute( "page", page );
		*/
		
		request.setAttribute( "estadisticas", estadisticas );
		
		return mapping.findForward( "success" );
    }
	
	
}
