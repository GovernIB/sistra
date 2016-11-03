package org.ibit.rol.form.admin.action.grupos;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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
 *  path="/admin/grupo/editModificar"
 *
 * @struts.action-forward
 *  name="success" path=".grupos.altaModif"
 *  
 *  @struts.action-forward
 *  name="editar" path=".grupo.editar"
 *  
 *  @struts.action-forward
 *  name="fail" path=".grupo.editar"
 */
public class ModificarGrupoAction extends BaseAction {
     public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

    	 String codigo = request.getParameter("codigo");
    	 if (codigo == null || codigo.length() == 0) {
             log.warn("El paràmetre códi és null!!");
             return mapping.findForward("fail");
         }
    	 String flag = request.getParameter("flag");
    	 
    	 try{
    		 
    		 GruposDelegate gruposDelegate = DelegateUtil.getGruposDelegate();
    		 Grupos grp = gruposDelegate.obtenerGrupo(codigo.trim());
    		 List usuarios = gruposDelegate.obtenerUsuariosByGrupo(codigo.trim());
    		 
    		 request.setAttribute("grupo", grp);
    		 request.setAttribute("usuarios",usuarios);
    	 }catch (Exception e) {
    		 return mapping.findForward("fail");
		}
    	 if(StringUtils.isEmpty(flag))
    		 return mapping.findForward("editar");
    	 else{
    		 request.setAttribute("flagValidacion",flag);
    		 return mapping.findForward("success");
    	 }
    }

}
