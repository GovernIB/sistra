package es.caib.bantel.front.action;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.bantel.front.Constants;
import es.caib.bantel.front.form.DetalleExpedienteForm;
import es.caib.bantel.front.json.UnidadAdministrativa;
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
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		PadBackOfficeDelegate ejb = new PadBackOfficeDelegate();
		request.getSession().setAttribute(Constants.OPCION_SELECCIONADA_KEY,"3");
		DetalleExpedienteForm expedienteForm = (DetalleExpedienteForm)form;
		ExpedientePAD exp;
	
		try{
			
			if(expedienteForm.getClaveExp()!=null && !"".equals(expedienteForm.getClaveExp())){
				exp = ejb.consultaExpediente( new Long(expedienteForm.getUnidadAdm()), expedienteForm.getIdentificadorExp(),expedienteForm.getClaveExp());
			}else{
				exp = ejb.consultaExpediente( new Long(expedienteForm.getUnidadAdm()), expedienteForm.getIdentificadorExp());
			}
			if(exp != null){
				List unidades = Dominios.getDescUnidadesAdministrativas(exp.getUnidadAdministrativa());
				request.setAttribute("expediente", exp);
				if(unidades!=null){
					UnidadAdministrativa uni = (UnidadAdministrativa) unidades.get(0);
					request.setAttribute("nombreUnidad",uni.getDescripcion());
				}
			}else{
				ActionErrors errors = new ActionErrors();
				errors.add("altaNotificacion", new ActionError("error.noExpediente"));
				saveErrors(request,errors);
				List unidades = Dominios.listarUnidadesAdministrativas();
				request.setAttribute("unidades",unidades);
				return mapping.findForward("noExpediente");
			}
		}catch(Exception e){
			String mensajeOk = MensajesUtil.getValue("error.expediente.consulta");
			request.setAttribute( Constants.MESSAGE_KEY,mensajeOk);
			return mapping.findForward("fail");
		}
		return mapping.findForward( "success" );
    }
}


