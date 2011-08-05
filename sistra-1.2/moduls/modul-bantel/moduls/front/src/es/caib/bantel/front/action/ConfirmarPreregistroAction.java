package es.caib.bantel.front.action;

import java.sql.Timestamp;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import es.caib.bantel.front.form.ConfirmacionForm;
import es.caib.bantel.model.GestorBandeja;
import es.caib.bantel.model.Tramite;
import es.caib.bantel.persistence.delegate.DelegateUtil;
import es.caib.util.StringUtil;
import es.caib.zonaper.modelInterfaz.PreregistroPAD;
import es.caib.zonaper.persistence.delegate.DelegatePADUtil;
import es.caib.zonaper.persistence.delegate.PadDelegate;

/**
 * @struts.action
 *  name="confirmacionForm"
 *  path="/confirmarPreregistro" 
 *  validate="true"
 *  input=".informacionPreregistro"
 *  
 * @struts.action-forward
 *  name="success" path=".mensaje"
 *
 * @struts.action-forward
 *  name="fail" path=".error"
 */
public class ConfirmarPreregistroAction extends BaseAction
{
	
	protected static Log log = LogFactory.getLog(ConfirmarPreregistroAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		 
		ConfirmacionForm datos = (ConfirmacionForm) form;
		
		// Tratamos numero y fecha de registro		
		Timestamp fechaRegistro  = null;
		String numeroRegistro = null;
		if (StringUtils.isNotEmpty(datos.getNumeroRegistro()) && StringUtils.isNotEmpty(datos.getFechaRegistro())){	
			numeroRegistro = datos.getNumeroRegistro();
			fechaRegistro = new Timestamp(StringUtil.cadenaAFecha(datos.getFechaRegistro()+":00","dd/MM/yyyy HH:mm:ss").getTime());
		}
		
		// Confirmamos preregistro desde la zona personal
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
		
		// Verificamos que el gestor tenga acceso al tramite
		boolean acceso = false;
		GestorBandeja gestor = DelegateUtil.getGestorBandejaDelegate().obtenerGestorBandeja(request.getUserPrincipal().getName());
		for (Iterator it=gestor.getTramitesGestionados().iterator();it.hasNext();){
				Tramite tramite = (Tramite) it.next();
				if (tramite.getIdentificador().equals(StringUtil.getModelo(preregistro.getIdentificadorTramite()))){
					acceso = true;
					break;
				}
		}
		if (!acceso){
			request.setAttribute("message",resources.getMessage( getLocale( request ), "errors.preregistroNoAcceso", new Object[] {preregistro.getIdentificadorTramite()}));			
			return mapping.findForward( "fail" );
		}
		
		// Confirmamos preregistro
		try{
			pad.confirmarPreregistroIncorrecto(preregistro.getCodigoPreregistro(),numeroRegistro,fechaRegistro);
		}catch(Exception ex){
			log.error("Excepcion confirmando preregistro " + preregistro.getNumeroPreregistro(),ex);
			request.setAttribute("message",resources.getMessage( getLocale( request ), "errors.preregistroConfirmado"));			
			return mapping.findForward( "fail" );
		}
		
		// Indicamos que el preregistro se ha confirmado correctamente
		request.setAttribute("message",resources.getMessage( getLocale( request ), "preregistro.preregistroConfirmado"));
		return mapping.findForward( "success" );
    }
}
