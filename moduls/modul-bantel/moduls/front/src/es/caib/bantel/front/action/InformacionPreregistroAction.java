package es.caib.bantel.front.action;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import es.caib.bantel.front.form.ConfirmacionForm;
import es.caib.bantel.model.GestorBandeja;
import es.caib.bantel.model.Procedimiento;
import es.caib.bantel.persistence.delegate.DelegateUtil;
import es.caib.util.StringUtil;
import es.caib.zonaper.modelInterfaz.PreregistroPAD;
import es.caib.zonaper.persistence.delegate.DelegatePADUtil;
import es.caib.zonaper.persistence.delegate.PadDelegate;

/**
 * @struts.action
 *  name="confirmacionForm"
 *  path="/informacionPreregistro"
 *  validate="true"
 *  input=".informacionPreregistro"
 *  
 * @struts.action-forward
 *  name="success" path=".informacionPreregistro"
 *
 * @struts.action-forward
 *  name="fail" path=".error"
 */
public class InformacionPreregistroAction extends BaseAction
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		
		ConfirmacionForm datos = (ConfirmacionForm) form;
		
		// Obtenemos datos preregistro de la zona personal
		PadDelegate pad = DelegatePADUtil.getPadDelegate();
		PreregistroPAD preregistro = pad.obtenerInformacionPreregistro(datos.getNumeroPreregistro());
		
		// Comprobamos si el preregistro no existe
		MessageResources resources = ((MessageResources) request.getAttribute(Globals.MESSAGES_KEY));    	
		if ( preregistro == null )	
		{
			request.setAttribute("message",resources.getMessage( getLocale( request ), "errors.preregistroNoExiste", new Object[] {datos.getNumeroPreregistro()}));			
			return mapping.findForward( "fail" );
		}
		
		// Comprobamos si el preregistro ya ha sido confirmado
		if ( preregistro.getFechaConfirmacion() != null)
		{
			request.setAttribute("message",resources.getMessage( getLocale( request ), "errors.preregistroYaConfirmado", new Object[] {datos.getNumeroPreregistro(),StringUtil.fechaACadena( preregistro.getFechaConfirmacion())}));			
			return mapping.findForward( "fail" );			
		}
		
		// 	------------------------------------------------------------------------------------------------------------------
		// TODO RAFA HABRIA QUE IMPLEMENTARLO EN CAPA DE NEGOCIO		
		// Verificamos que el gestor tenga acceso al tramite
		boolean acceso = false;
		GestorBandeja gestor = DelegateUtil.getGestorBandejaDelegate().obtenerGestorBandeja(request.getUserPrincipal().getName());
		if (gestor.getProcedimientosGestionados() != null)  {
			for (Iterator it=gestor.getProcedimientosGestionados().iterator();it.hasNext();){
					Procedimiento procedimiento = (Procedimiento) it.next();
					if (procedimiento.getIdentificador().equals(preregistro.getIdentificadorProcedimiento())){
						acceso = true;
						break;
					}
			}
		}
		if (!acceso){
			request.setAttribute("message",resources.getMessage( getLocale( request ), "errors.preregistroNoAcceso", new Object[] {preregistro.getIdentificadorProcedimiento()}));			
			return mapping.findForward( "fail" );
		}
		// ------------------------------------------------------------------------------------------------------------------
		
		// Pedimos confirmacion al gestor
		//request.setAttribute( "preregistro", preregistro );
		request.getSession().setAttribute("preregistro",preregistro);
		return mapping.findForward( "success" );
    }
}
