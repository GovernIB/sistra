package org.ibit.rol.form.admin.action.grupos;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.ibit.rol.form.admin.action.BaseAction;
import org.ibit.rol.form.admin.form.GrupoForm;
import org.ibit.rol.form.model.GrupoUsuario;
import org.ibit.rol.form.model.Grupos;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.ibit.rol.form.persistence.delegate.GruposDelegate;

/**
 * Action para preparar el alta de un Grupo.
 *
 * @struts.action
 * name="grupoForm"
 * path="/admin/grupo/guardar"
 * validate="true"
 * input=".grupos.altaModif"
 *
 * @struts.action-forward
 *  name="success" path=".grupos.lista"
 *  
 *  @struts.action-forward
 *  name="fail" path=".grupos.lista"
 */
public class GuardarGrupoAction extends BaseAction {
     public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

    	 GrupoForm grupoForm = (GrupoForm) form;
    	 Grupos grupo = asignarGrupo(grupoForm);
    	 try{
	    	 GruposDelegate gruposDelegate = DelegateUtil.getGruposDelegate();
	    	 if(grupoForm.getFlagValidacion()!=null && grupoForm.getFlagValidacion().equals("modificar")){
	    		 gruposDelegate.modificarGrupo(grupo);
	    	 }
	    	 else{
	    		 gruposDelegate.guardarGrupo(grupo); 
	    		 if(request.getSession().getAttribute("usuarios")!=null){
	    			 List usuarios = (List) request.getSession().getAttribute("usuarios");
	 				 Iterator it = usuarios.iterator();
	    			 while(it.hasNext()){
	    				 GrupoUsuario grpUser = (GrupoUsuario) it.next();
	    				 grpUser.getId().setCodiGrup(grupo.getCodigo());
	    				 gruposDelegate.asociarUsuario(grpUser);
	    			 }
	    		 }
	    	 }
	    	 return mapping.findForward("success");
	    	
    	 }catch (Exception e) {
    		 return mapping.findForward("fail");
		}
		
    }

	private Grupos asignarGrupo(GrupoForm grupoForm) {
		Grupos g = new Grupos();
		g.setCodigo(grupoForm.getCodigo().trim());
		g.setNombre(grupoForm.getNombre().trim());
		g.setDescripcion(grupoForm.getDescripcion());
		return g;
	}

}
