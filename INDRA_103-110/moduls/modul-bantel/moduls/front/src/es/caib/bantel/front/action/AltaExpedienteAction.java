package es.caib.bantel.front.action;

import java.util.List;

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

import es.caib.bantel.front.Constants;
import es.caib.bantel.front.form.DetalleExpedienteForm;
import es.caib.bantel.front.util.Dominios;
import es.caib.bantel.front.util.MensajesUtil;
import es.caib.bantel.model.GestorBandeja;
import es.caib.bantel.persistence.delegate.DelegateUtil;

/**
 * @struts.action
 *  name="detalleExpedienteForm"
 *  path="/altaExpediente"
 *  validate="true"
 *  input = ".entradaAltaExpediente"
 *  
 * @struts.action-forward
 *  name="success" path=".altaExpediente"
 *  
 * @struts.action-forward
 *  name="entraAlta" path=".entradaAltaExpediente"
 *
 * @struts.action-forward
 *  name="fail" path=".error"
 */
public class AltaExpedienteAction extends BaseAction
{
	protected static Log log = LogFactory.getLog(AltaExpedienteAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		
		try{
			
			MensajesUtil.setMsg(this.getResources(request));
			DetalleExpedienteForm expForm = (DetalleExpedienteForm)form;
			
			if(StringUtils.isNotEmpty(expForm.getFlagValidacion()) && expForm.getFlagValidacion().equals("entradaAlta")){
				expForm.setFlagValidacion("");
				return mapping.findForward("entraAlta");
			}
			
			request.getSession().setAttribute(Constants.OPCION_SELECCIONADA_KEY,"3");
			expForm.setNumeroEntrada("");
			
			// Combo unidades administrativas
			List unidades=Dominios.listarUnidadesAdministrativas();
			request.setAttribute("unidades",unidades);
			
			// Combo procedimientos gestor
			GestorBandeja gestor = DelegateUtil.getGestorBandejaDelegate().obtenerGestorBandeja(this.getPrincipal(request).getName());
			if (gestor == null || gestor.getProcedimientosGestionados() == null || gestor.getProcedimientosGestionados().size() == 0) {
				request.setAttribute("message",this.getResources(request).getMessage( getLocale( request ), "errors.noGestor"));
				return mapping.findForward( "fail" );
			}
			request.setAttribute("procedimientosGestor", gestor.getProcedimientosGestionados());
			
			return mapping.findForward( "success" );
			
		}catch(Exception e){
			log.error("Excepcion mostrando alta expediente",e);
			return mapping.findForward("fail");
		}
    }
}
