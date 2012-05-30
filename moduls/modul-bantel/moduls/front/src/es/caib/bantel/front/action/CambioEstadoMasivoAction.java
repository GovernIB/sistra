package es.caib.bantel.front.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import es.caib.bantel.front.form.CambioEstadoMasivoForm;
import es.caib.bantel.model.CriteriosBusquedaTramite;
import es.caib.bantel.persistence.delegate.DelegateUtil;
import es.caib.bantel.persistence.delegate.TramiteBandejaDelegate;

/**
 * @struts.action
 *  name="cambioEstadoMasivoForm"
 *  path="/cambioEstadoMasivo"
 *  scope="session"
 *  validate="false"
 *  
 * @struts.action-forward
 *  name="success" path=".mensaje"
 *
 * @struts.action-forward
 *  name="fail" path=".error"
 */
public class CambioEstadoMasivoAction extends BaseAction
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		MessageResources resources = ((MessageResources) request.getAttribute(Globals.MESSAGES_KEY));  
		
		// Construimos criterios de busqueda
		CambioEstadoMasivoForm formularioBusqueda = ( CambioEstadoMasivoForm ) form;
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
		
		
		// Marcamos entradas con nuevo estado
		TramiteBandejaDelegate delegate = DelegateUtil.getTramiteBandejaDelegate();
		delegate.procesarEntradas(criterios,Character.toString(formularioBusqueda.getEstadoNuevo()), "Estado cambiado por gestor " + this.getPrincipal(request).getName());
		
		// Indicamos que el cambio se ha realizado correctamente
		request.setAttribute("message",resources.getMessage( getLocale( request ), "cambioEstadoMasivo.realizado"));
		return mapping.findForward( "success" );
    }
	
	
}
