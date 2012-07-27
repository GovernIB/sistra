package es.caib.bantel.front.action;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import es.caib.bantel.front.Constants;
import es.caib.bantel.front.form.DetalleExpedienteForm;
import es.caib.bantel.model.GestorBandeja;
import es.caib.bantel.model.Procedimiento;
import es.caib.bantel.modelInterfaz.TramiteBTE;
import es.caib.bantel.persistence.delegate.DelegateBTEUtil;
import es.caib.bantel.persistence.delegate.DelegateException;
import es.caib.bantel.persistence.delegate.DelegateUtil;

/**
 * @struts.action
 * 	name="detalleExpedienteForm"
 *  path="/altaEntradaExpediente"
 *  validate="true"
 *  input = ".entradaAltaExpediente"
 *  
 * @struts.action-forward
 *  name="success" path=".altaExpediente"
 *
 * @struts.action-forward
 *  name="fail" path=".error"
 */
public class AltaEntradaExpedienteAction extends BaseAction
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {		
		MessageResources resources = ((MessageResources) request.getAttribute(Globals.MESSAGES_KEY));
		try{		
			
			// Preparamos alta expediente indicando numero de entrada
			
			DetalleExpedienteForm expForm = (DetalleExpedienteForm)form;
			request.getSession().setAttribute(Constants.OPCION_SELECCIONADA_KEY,"3");
			
			// Consultamos entrada
			TramiteBTE entrada = consultaEntradaBTE(expForm.getNumeroEntrada());	
			if (entrada == null){
				request.setAttribute("message",resources.getMessage( getLocale( request ), "errors.tramiteNoExiste"));
				return mapping.findForward( "fail" );				
			}
				
			// Si es entrada anonima debe tener indicado el nif 
			if (entrada.getNivelAutenticacion() == 'A' && StringUtils.isEmpty(entrada.getUsuarioNif())){
				request.setAttribute("message",resources.getMessage( getLocale( request ), "errors.anomimoSinNif"));
				return mapping.findForward( "fail" );
			}
			
			// Recuperamos procedimientos accesibles por gestor y vemos si es gestor del procedimiento al que pertenece la entrada
			GestorBandeja gestor = DelegateUtil.getGestorBandejaDelegate().obtenerGestorBandeja(this.getPrincipal(request).getName());
			if (gestor == null || gestor.getProcedimientosGestionados() == null || gestor.getProcedimientosGestionados().size() == 0) {
				request.setAttribute("message",resources.getMessage( getLocale( request ), "errors.noGestor"));
				return mapping.findForward( "fail" );
			}
			Procedimiento procExpe = null;
			for (Iterator it = gestor.getProcedimientosGestionados().iterator(); it.hasNext();) {
				Procedimiento p = (Procedimiento) it.next();
				if (p.getIdentificador().equals(entrada.getIdentificadorProcedimiento())) {
					procExpe = p;
					break;
				}
			}
			if (procExpe == null) {
				request.setAttribute("message",resources.getMessage( getLocale( request ), "errors.noGestorEntrada"));
				return mapping.findForward( "fail" );
			}
			
			request.setAttribute("existeEntrada","S");
			expForm.setIdentificadorProcedimiento(entrada.getIdentificadorProcedimiento());
			expForm.setUsuarioSeycon(StringUtils.defaultString(entrada.getUsuarioSeycon()));
			expForm.setDescripcion(StringUtils.defaultString(entrada.getDescripcionTramite()));
			expForm.setIdioma(StringUtils.defaultString(entrada.getIdioma(),"ca"));
			expForm.setNif(StringUtils.defaultString(entrada.getUsuarioNif()));
			expForm.setNombre(StringUtils.defaultString(entrada.getUsuarioNombre()));
			expForm.setNumeroEntrada(entrada.getNumeroEntrada());
			expForm.setEmail(entrada.getAvisoEmail());
			
			if ("S".equals(entrada.getHabilitarAvisos())) {
				expForm.setHabilitarAvisos(entrada.getHabilitarAvisos());
				expForm.setMovil(entrada.getAvisoSMS());
				expForm.setEmail(entrada.getAvisoEmail());
			} else {
				expForm.setHabilitarAvisos("N");
			}
												
			return mapping.findForward( "success" );
			
		}catch(Exception ex){
			request.setAttribute("message",ex.getMessage());
			return mapping.findForward( "fail" );	
		}
		
    }
	
	private TramiteBTE consultaEntradaBTE(String numeroEntrada) throws DelegateException{
		return DelegateBTEUtil.getBteDelegate().obtenerEntrada(numeroEntrada);
	}
	
}
