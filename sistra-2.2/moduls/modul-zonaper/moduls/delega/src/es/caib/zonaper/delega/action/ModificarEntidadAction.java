package es.caib.zonaper.delega.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.zonaper.delega.form.DetalleEntidadForm;
import es.caib.zonaper.modelInterfaz.PersonaPAD;
import es.caib.zonaper.persistence.delegate.DelegacionDelegate;
import es.caib.zonaper.persistence.delegate.DelegateUtil;
import es.caib.zonaper.persistence.delegate.PadAplicacionDelegate;

/**
 * @struts.action
 * 	name="detalleEntidadForm"
 *   path="/modificarEntidad"
 *  validate="true"
 *  
 * @struts.action-forward
 *  name="success" path=".altaEntidad"
 *
 * @struts.action-forward
 *  name="fail" path=".mainLayout"
 */
public class ModificarEntidadAction extends BaseAction
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		DetalleEntidadForm entForm = (DetalleEntidadForm)form;
		String nif = request.getParameter("nif");
		PadAplicacionDelegate padAplic = DelegateUtil.getPadAplicacionDelegate();
		DelegacionDelegate deleg = DelegateUtil.getDelegacionDelegate();
		if(!StringUtils.isBlank(nif)){
			try{
				//solo nos devolvera una, ya que el nif es primary key
				List personas = padAplic.buscarEntidades(nif);
				if(personas != null && personas.size() > 0){
					PersonaPAD persona = (PersonaPAD)personas.get(0); 
					entForm.setPersona(persona);
					entForm.setModificacio("S");
					if(request.getSession().getAttribute("altaCorrecta") != null && StringUtils.isNotBlank((String)request.getSession().getAttribute("altaCorrecta"))){
						entForm.setAltaCorrecta((String)request.getSession().getAttribute("altaCorrecta"));
						request.getSession().setAttribute("altaCorrecta",null);
					}else{
						entForm.setAltaCorrecta("N");
					}
					request.setAttribute("provinciaEntidad",persona.getProvincia());
				}else{
					ActionErrors messages = new ActionErrors();
		        	messages.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.nifDelegante.no.existe"));
		        	saveErrors(request,messages);  
					return mapping.findForward( "fail" );
				}
				return mapping.findForward( "success" );
			}catch(Exception e){
				ActionErrors messages = new ActionErrors();
	        	messages.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.recuperar.entidad"));
	        	saveErrors(request,messages);  
				return mapping.findForward( "fail" );
			}
		}else{
			ActionErrors messages = new ActionErrors();
        	messages.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.nif.entidad.vacio"));
        	saveErrors(request,messages);  
			return mapping.findForward( "fail" );
		}
    }
}
