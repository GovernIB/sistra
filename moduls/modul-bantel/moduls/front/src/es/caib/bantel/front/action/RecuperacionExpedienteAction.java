package es.caib.bantel.front.action;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.bantel.front.Constants;
import es.caib.bantel.front.form.DetalleExpedienteForm;
import es.caib.bantel.front.util.Dominios;
import es.caib.bantel.front.util.MensajesUtil;
import es.caib.zonaper.modelInterfaz.ExpedientePAD;
import es.caib.zonaper.persistence.delegate.PadBackOfficeDelegate;

/**
 * @struts.action
 *  name="detalleExpedienteForm"
 *  path="/recuperarExpediente"
 *  validate="true"
 *  input=".confirmacionRecuperacionExpediente"
 *  
 * @struts.action-forward
 *  name="success" path=".recuperarExpediente"
 *  
 * @struts.action-forward
 *  name="noExpediente" path=".confirmacionRecuperacionExpediente"
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
		DetalleExpedienteForm expedienteForm = (DetalleExpedienteForm)form;
		ExpedientePAD exp;
	
		try{
			
			// Reseteamos parametros expediente actual
			request.getSession().setAttribute(Constants.EXPEDIENTE_ACTUAL_IDENTIFICADOR_KEY, null);
			request.getSession().setAttribute(Constants.EXPEDIENTE_ACTUAL_UNIDADADMIN_KEY, null);
			request.getSession().setAttribute(Constants.EXPEDIENTE_ACTUAL_CLAVE_KEY, null);
			
			// Recuperamos expediente y en caso correcto almacenamos en sesion los parametros de acceso para futuras llamadas
			exp = ejb.consultaExpediente( new Long(expedienteForm.getUnidadAdm()), expedienteForm.getIdentificadorExp(),expedienteForm.getClaveExp());
			
			if(exp != null){
				// Establecemos parametros expediente actual
				request.getSession().setAttribute(Constants.EXPEDIENTE_ACTUAL_IDENTIFICADOR_KEY, expedienteForm.getIdentificadorExp());
				request.getSession().setAttribute(Constants.EXPEDIENTE_ACTUAL_UNIDADADMIN_KEY, new Long(expedienteForm.getUnidadAdm()));
				request.getSession().setAttribute(Constants.EXPEDIENTE_ACTUAL_CLAVE_KEY, expedienteForm.getClaveExp());
				
				// Establecesmos expediente en la request 
				request.setAttribute("expediente", exp);
				return mapping.findForward( "success" );
			}else{
				ActionErrors errors = new ActionErrors();
				errors.add("altaNotificacion", new ActionError("error.noExpediente"));
				saveErrors(request,errors);
				List unidades = Dominios.listarUnidadesAdministrativas();
				request.setAttribute("unidades",unidades);
				return mapping.findForward("noExpediente");
			}
		}catch(Exception e){
			log.error("Excepcion recuperando expediente",e);
			String mensajeOk = MensajesUtil.getValue("error.expediente.consulta");
			request.setAttribute( Constants.MESSAGE_KEY,mensajeOk + ": " + e.getMessage());
			return mapping.findForward("fail");
		}		
    }
}


