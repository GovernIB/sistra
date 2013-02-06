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
 * path="/admin/grupo/asociarUsuario"
 *
 */
public class AsociarUsuarioGrupoAction extends Action {
    	 private static Log _log = LogFactory.getLog( AsociarUsuarioGrupoAction.class );
    		public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    		{
    			List usuarios = new ArrayList();
    			String tabla = "";
    			try{
    				 request.setCharacterEncoding("UTF-8");
    				 String usuario = request.getParameter("usuario");
    				 String grupo = request.getParameter("grupo");
    				 String flag = request.getParameter("flag");
    					GruposDelegate gruposDelegate = DelegateUtil.getGruposDelegate();
    				 //si viene de modificación
    				 if(StringUtils.isNotBlank(usuario) && StringUtils.isNotBlank(grupo) && StringUtils.isNotEmpty(flag)){
    					GrupoUsuario grpUser = new GrupoUsuario(new GrupoUsuarioId(grupo,usuario.trim()));
    					gruposDelegate.asociarUsuario(grpUser);
    					usuarios = gruposDelegate.obtenerUsuariosByGrupo(grupo);
    				 }
    				 //si viene de alta
    				 else if(StringUtils.isNotBlank(usuario)){
    					boolean trobat = false;
     					if(request.getSession().getAttribute("usuarios")!=null){
    						usuarios = (List) request.getSession().getAttribute("usuarios");
       						for(int i=0;i<usuarios.size() && !trobat;i++){
       							GrupoUsuario gu = (GrupoUsuario)usuarios.get(i);
       							if(gu.getId() != null && usuario.trim().equals(gu.getId().getUsuario())){
       								trobat = true;
       							}
       						}
       					}
       					if(!trobat){
       						GrupoUsuarioId id = new GrupoUsuarioId();
       						id.setUsuario(usuario.trim());
       						GrupoUsuario grpUser = new GrupoUsuario(id);
       						usuarios.add(grpUser);
       						request.getSession().setAttribute("usuarios",usuarios);
    					}
    				 }else{
    					 if(request.getSession().getAttribute("usuarios") != null){
    						 usuarios = (List)request.getSession().getAttribute("usuarios");
    					 }else{
    						 if(StringUtils.isNotBlank(grupo)){
    							 usuarios = gruposDelegate.obtenerUsuariosByGrupo(grupo); 
    						 }
    					 }
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
