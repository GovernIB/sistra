package es.caib.bantel.front.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.bantel.front.Constants;
import es.caib.bantel.front.form.DetalleExpedienteForm;
import es.caib.bantel.front.util.Dominios;
import es.caib.bantel.front.util.MensajesUtil;

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
			List unidades=Dominios.listarUnidadesAdministrativas();
			request.setAttribute("unidades",unidades);
			return mapping.findForward( "success" );
		}catch(Exception e){
			return mapping.findForward("fail");
		}
    }
}
