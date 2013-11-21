package es.caib.sistra.admin.action.tramites;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.sistra.admin.action.BaseAction;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.sistra.persistence.delegate.TramiteDelegate;

/**
 * Action para preparar el alta de un Grupo.
 *
 * @struts.action
 * name="tramiteForm"
 *  path="/admin/tramite/editModificar"
 *
 * @struts.action-forward
 *  name="success" path=".tramites.modif"
 *  
 *  @struts.action-forward
 *  name="fail" path=".tramites.lista"
 */
public class ModificarTramiteAction extends BaseAction {
	 protected static Log log = LogFactory.getLog(ModificarTramiteAction.class);
	 
	 public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

    	 String codigo = request.getParameter("codigo");
    	 if (codigo == null || codigo.length() == 0) {
             log.warn("El paràmetre códi és null!!");
             return mapping.findForward("fail");
         }

    	 try{
    		  TramiteDelegate tramiteDelegate =  DelegateUtil.getTramiteDelegate();
              request.setAttribute("tramite", tramiteDelegate.obtenerTramite(new Long(codigo)));
             
    	 }catch (Exception e) {
    		 return mapping.findForward("fail");
		}
   		 return mapping.findForward("success");
    }

}
