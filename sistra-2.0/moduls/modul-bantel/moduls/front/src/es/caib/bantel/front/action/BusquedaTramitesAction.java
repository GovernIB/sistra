package es.caib.bantel.front.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.bantel.front.form.BusquedaTramitesForm;
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
	private static final int LONGITUD_PAGINA = 10;
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		BusquedaTramitesForm formularioBusqueda = ( BusquedaTramitesForm ) form;
		TramiteBandejaDelegate delegate = DelegateUtil.getTramiteBandejaDelegate();
		CriteriosBusquedaTramite criterios = new CriteriosBusquedaTramite();
		criterios.setAnyo( formularioBusqueda.getAnyo() );	
		if (!("-1".equals(formularioBusqueda.getIdentificadorProcedimiento()))) {
			criterios.setIdentificadorProcedimiento( formularioBusqueda.getIdentificadorProcedimiento() );
		}		
		if (StringUtils.isNotBlank(formularioBusqueda.getIdentificadorTramite())) {
			criterios.setIdentificadorTramite(formularioBusqueda.getIdentificadorTramite());
		}
		criterios.setMes( formularioBusqueda.getMes() );
		criterios.setNivelAutenticacion( formularioBusqueda.getNivelAutenticacion() );
		criterios.setProcesada( formularioBusqueda.getProcesada() );
		criterios.setTipo( formularioBusqueda.getTipo() );
		criterios.setUsuarioNif( formularioBusqueda.getUsuarioNif() );
		criterios.setUsuarioNombre( formularioBusqueda.getUsuarioNombre() );
		criterios.setNumeroEntrada(formularioBusqueda.getNumeroEntrada());
		Page page = delegate.busquedaPaginadaTramites( criterios, formularioBusqueda.getPagina(), LONGITUD_PAGINA );
		
		request.setAttribute( "page", page );
		
		return mapping.findForward( "success" );
    }
	
	
}
