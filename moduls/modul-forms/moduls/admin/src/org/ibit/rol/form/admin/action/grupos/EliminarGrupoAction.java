package org.ibit.rol.form.admin.action.grupos;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.ibit.rol.form.admin.action.BaseAction;
import org.ibit.rol.form.model.Grupos;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.ibit.rol.form.persistence.delegate.GruposDelegate;

/**
 * Action para preparar el alta de un Grupo.
 *
 * @struts.action
 * path="/admin/grupo/eliminar"
 *
 * @struts.action-forward
 *  name="success" path=".grupos.lista"
 *  
 *  @struts.action-forward
 *  name="fail" path=".grupos.lista"
 */
public class EliminarGrupoAction extends BaseAction {
     public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

    	 String codigo = request.getParameter("codigo");
    	 if (codigo == null || codigo.length() == 0) {
             log.warn("El paràmetre códi és null!!");
             return mapping.findForward("fail");
         }
    	 try{
    		 GruposDelegate gruposDelegate = DelegateUtil.getGruposDelegate();
    		 Grupos grp = gruposDelegate.obtenerGrupo(codigo);
    		 if(gruposDelegate.existeUsuariosByGrupo(codigo)){
    			 request.setAttribute("message","No se puede eliminar el grupo: "+ codigo +", tiene usuarios asociados");
    			 return mapping.findForward("fail");
    		 }
    		 gruposDelegate.eliminarGrupo(grp);
    		 return mapping.findForward("success");
    	 }catch (Exception e) {
    		 return mapping.findForward("fail");
		}
    }
}
