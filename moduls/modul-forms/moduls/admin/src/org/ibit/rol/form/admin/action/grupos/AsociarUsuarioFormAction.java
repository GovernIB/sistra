package org.ibit.rol.form.admin.action.grupos;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.ibit.rol.form.admin.json.JSONObject;
import org.ibit.rol.form.model.RolUsuarioFormulario;
import org.ibit.rol.form.model.RolUsuarioFormularioId;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.ibit.rol.form.persistence.delegate.GruposDelegate;

/**
 * Action para preparar el alta de un Grupo.
 *
 * @struts.action
 * path="/admin/formulario/asociarUsuario"
 *
 */
public class AsociarUsuarioFormAction extends Action {
    	 private static Log _log = LogFactory.getLog( AsociarUsuarioFormAction.class );
    		public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    		{
    			List usuarios = new ArrayList();
    			String tabla = "";
    			try{
    				 GruposDelegate gruposDelegate = DelegateUtil.getGruposDelegate();
    				 request.setCharacterEncoding("UTF-8");
    				 String usuario = request.getParameter("usuario");
    				 String formulario = request.getParameter("formulario");
    				 String flag = request.getParameter("flag");
    				 //si viene de modificación
    				 if(StringUtils.isNotBlank(usuario) && StringUtils.isNotBlank(formulario) && StringUtils.isEmpty(flag)){
    					RolUsuarioFormulario userForm = new RolUsuarioFormulario(new RolUsuarioFormularioId(usuario.trim(),new Long(formulario)));
    					gruposDelegate.asociarUsuarioFormulario(userForm);
    				 }else if(StringUtils.isNotEmpty(flag)){
     					RolUsuarioFormulario userForm = gruposDelegate.obtenerUsuarioForm(new RolUsuarioFormularioId(usuario.trim(), new Long(formulario)));
     					gruposDelegate.eliminarUsuarioFormulario(userForm);
    				 }
   					 if(StringUtils.isNotBlank(formulario)){
     					usuarios = gruposDelegate.listarUsuariosByForm(new Long(formulario));
    				 }
    				 //formatea los datos para la recarga del div
    				 if(usuarios!=null && usuarios.size()>0){
						tabla = "<table class=\"marc\">";
						for(int i=0;i<usuarios.size();i++){
							RolUsuarioFormulario usuarioForm  = (RolUsuarioFormulario) usuarios.get(i);
							tabla += "<tr><td class=\"outputd\">" + usuarioForm.getId().getCodiUsuario() + " </td>";
							tabla += " <td align=\"left\"><button class=\"button\" type=\"button\" onclick=\"eliminar('" + usuarioForm.getId().getCodiUsuario() 
									+	"')\">Eliminar</button></td></tr>";
						}
						tabla += "</table>";
    				 }else{
    					 tabla = "<table class=\"marc\"><tr><td class=\"alert\">No hay usuarios asociados a este grupo</td></tr></table>";
    				 }
					JSONObject jsonObject = new JSONObject();		        
					jsonObject.put("taula",tabla);
					populateWithJSON(response,jsonObject.toString());
    			}catch(Exception ex){
    				_log.error(ex);
    			}
    			
    			return null;
		
    }

	
	private static void populateWithJSON(HttpServletResponse response,String json) throws Exception{
		String CONTENT_TYPE="text/x-json;charset=UTF-8";
		response.setContentType(CONTENT_TYPE);
		response.setHeader("Cache-Control", "no-cache");
		response.getWriter().write(json); 		 		
	}

}
