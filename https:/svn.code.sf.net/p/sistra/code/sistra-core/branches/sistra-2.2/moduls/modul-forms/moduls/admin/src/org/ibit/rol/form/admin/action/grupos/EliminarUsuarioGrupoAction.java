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
import org.ibit.rol.form.model.GrupoUsuario;
import org.ibit.rol.form.model.GrupoUsuarioId;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.ibit.rol.form.persistence.delegate.GruposDelegate;




/**
 * Action para preparar el alta de un Grupo.
 *
 * @struts.action
 * path="/admin/grupo/eliminarUsuario"
 *
 */
public class EliminarUsuarioGrupoAction extends Action {
    	 private static Log _log = LogFactory.getLog( EliminarUsuarioGrupoAction.class );
    		public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    		{
    			List usuarios = new ArrayList();
    			String tabla = "";
    			try{
    				 request.setCharacterEncoding("UTF-8");
    				 String usuario = request.getParameter("usuario");
    				 String grupo = request.getParameter("grupo");
    				 String flag = request.getParameter("flag");
    				 
    				 
    				 //comprueba si viene desde la pantalla de modificacion
    				 if(usuario!=null && StringUtils.isNotEmpty(usuario) && StringUtils.isNotEmpty(grupo) && StringUtils.isNotEmpty(flag)){
    					GruposDelegate gruposDelegate = DelegateUtil.getGruposDelegate();
    					GrupoUsuario grpUser = gruposDelegate.obtenerUsuarioGrupo(new GrupoUsuarioId(grupo,usuario));
    					gruposDelegate.eliminarUsuarioGrupo(grpUser);
    					usuarios = gruposDelegate.obtenerUsuariosByGrupo(grupo);
    				 }
    				 //si viene desde la pantalla de alta
    				 else if(StringUtils.isNotEmpty(usuario)){
    					if(request.getSession().getAttribute("usuarios")!=null){
    						usuarios = (List) request.getSession().getAttribute("usuarios");
    					}
    					if(usuarios!=null && usuarios.size()>0){
    						for (int i = 0; i < usuarios.size(); i++) {
    							GrupoUsuario grpUser = (GrupoUsuario) usuarios.get(i);
    							if(usuario.equals(grpUser.getId().getUsuario())){
    								usuarios.remove(grpUser);
    							}
							}
    					}
     					request.getSession().setAttribute("usuarios",usuarios);
    				 }
    				 //formatea los datos para la recarga del div
    				 if(usuarios!=null && usuarios.size()>0){
						tabla = "<table class=\"marc\">";
						for(int i=0;i<usuarios.size();i++){
							GrupoUsuario grupousuario = (GrupoUsuario) usuarios.get(i);
							tabla += "<tr><td class=\"outputd\">" + grupousuario.getId().getUsuario() + " </td>";
							
							tabla +=  " <td align=\"left\"><button class=\"button\" type=\"button\" onclick=\"eliminar('" + grupousuario.getId().getUsuario() 
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
