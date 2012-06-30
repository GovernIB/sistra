package es.caib.bantel.front.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.bantel.front.Constants;
import es.caib.bantel.front.util.MensajesUtil;
import es.caib.bantel.model.Procedimiento;
import es.caib.bantel.persistence.delegate.DelegateUtil;
import es.caib.zonaper.modelInterfaz.ExpedientePAD;
import es.caib.zonaper.persistence.delegate.PadBackOfficeDelegate;

/**
 * @struts.action
 *  path="/recuperarExpediente"
 *  
 * @struts.action-forward
 *  name="success" path=".recuperarExpediente"
 *  
 * @struts.action-forward
 *  name="fail" path=".error"
 */
public class RecuperacionExpedienteAction extends BaseAction
{
	protected static Log log = LogFactory.getLog(RecuperacionExpedienteAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		
		PadBackOfficeDelegate ejb = new PadBackOfficeDelegate();
		request.getSession().setAttribute(Constants.OPCION_SELECCIONADA_KEY,"3");
		ExpedientePAD exp;
	
		try{
			
			// Reseteamos parametros expediente actual
			request.getSession().setAttribute(Constants.EXPEDIENTE_ACTUAL_IDENTIFICADOR_KEY, null);
			request.getSession().setAttribute(Constants.EXPEDIENTE_ACTUAL_UNIDADADMIN_KEY, null);
			request.getSession().setAttribute(Constants.EXPEDIENTE_ACTUAL_CLAVE_KEY, null);
			
			// Recuperamos expediente y en caso correcto almacenamos en sesion los parametros de acceso para futuras llamadas
			String identificadorExp = request.getParameter("identificadorExp");
			String unidadAdm = request.getParameter("unidadAdm");
			String claveExp = request.getParameter("claveExp");
			exp = ejb.consultaExpediente( new Long(unidadAdm), identificadorExp, claveExp);
			
			if(exp != null){
				// Establecemos parametros expediente actual
				request.getSession().setAttribute(Constants.EXPEDIENTE_ACTUAL_IDENTIFICADOR_KEY, identificadorExp);
				request.getSession().setAttribute(Constants.EXPEDIENTE_ACTUAL_UNIDADADMIN_KEY, new Long(unidadAdm));
				request.getSession().setAttribute(Constants.EXPEDIENTE_ACTUAL_CLAVE_KEY, claveExp);
				
				// Buscamos descripcion procedimiento y si permite sms
				String descProc = exp.getIdentificadorProcedimiento();
				String permitirSms = "N";
				Procedimiento procedimiento = DelegateUtil.getTramiteDelegate().obtenerProcedimiento(exp.getIdentificadorProcedimiento());
				if (procedimiento != null) {
					descProc += " - " + procedimiento.getDescripcion();
					permitirSms = procedimiento.getPermitirSms();
				}
				
				// Establecemos expediente en la request 
				request.setAttribute("expediente", exp);
				request.setAttribute("descripcionProcedimiento", descProc);
				request.setAttribute("permitirSms", permitirSms);
				return mapping.findForward( "success" );
			}else{
				throw new Exception("No existe expediente");
			}
		}catch(Exception e){
			log.error("Excepcion recuperando expediente",e);
			String mensajeOk = MensajesUtil.getValue("error.expediente.consulta");
			request.setAttribute( Constants.MESSAGE_KEY,mensajeOk + ": " + e.getMessage());
			return mapping.findForward("fail");
		}		
    }
}


