package es.caib.bantel.front.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.bantel.front.Constants;
import es.caib.bantel.front.form.DetalleAvisoForm;
import es.caib.bantel.front.util.Dominios;
import es.caib.bantel.front.util.MensajesUtil;
import es.caib.zonaper.modelInterfaz.ExpedientePAD;
import es.caib.zonaper.persistence.delegate.PadBackOfficeDelegate;

/**
 * @struts.action
 *  name="detalleAvisoForm"
 *  path="/altaAviso"
 *  validate="true"
 *  
 *  
 * @struts.action-forward
 *  name="success" path=".altaAviso"
 *
 * @struts.action-forward
 *  name="fail" path=".error"
 */
public class AltaAvisoAction extends BaseAction
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		DetalleAvisoForm 		avisoForm = (DetalleAvisoForm)form;
		PadBackOfficeDelegate 	ejb = new PadBackOfficeDelegate();
		ExpedientePAD 			exp;
		
		MensajesUtil.setMsg(this.getResources(request));
		request.getSession().setAttribute("documentosAltaAviso",null);
		request.getSession().setAttribute(Constants.OPCION_SELECCIONADA_KEY,"3");
		
		try{
			if(avisoForm.getClaveExpediente()!=null && !"".equals(avisoForm.getClaveExpediente())){
				exp = ejb.consultaExpediente( new Long(avisoForm.getUnidadAdministrativa()), avisoForm.getIdentificadorExpediente(),avisoForm.getClaveExpediente());
			}else{
				exp = ejb.consultaExpediente( new Long(avisoForm.getUnidadAdministrativa()), avisoForm.getIdentificadorExpediente());
			}
			if(exp != null){
				avisoForm.setDescripcionExpediente(exp.getDescripcion());
				avisoForm.setIdioma(exp.getIdioma());
				List unidades=Dominios.listarUnidadesAdministrativas();
				request.setAttribute("unidades",unidades);
			}else{
				return mapping.findForward("fail");
			}
		}catch(Exception e){
			String mensajeOk = MensajesUtil.getValue("error.aviso.Excepcion");
			request.setAttribute( Constants.MESSAGE_KEY,mensajeOk);
			return mapping.findForward("fail");
		}
		
		
		return mapping.findForward( "success" );
    }
}
