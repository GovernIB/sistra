package es.caib.sistra.admin.action.grupos;

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

import es.caib.sistra.admin.action.BaseAction;
import es.caib.sistra.admin.json.JSONObject;
import es.caib.sistra.model.RolUsuarioTramite;
import es.caib.sistra.model.RolUsuarioTramiteId;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.sistra.persistence.delegate.GruposDelegate;


/**
 * Action para preparar el alta de un Grupo.
 *
 * @struts.action
 * path="/admin/tramite/asociarUsuario"
 *
 */
public class AsociarUsuarioTramiteAction extends BaseAction {
    	 private static Log _log = LogFactory.getLog( AsociarUsuarioTramiteAction.class );
    		public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    		{
    			List usuarios = new ArrayList();
    			String tabla = "";
    			try{
    				 GruposDelegate gruposDelegate = DelegateUtil.getGruposDelegate();
    				 request.setCharacterEncoding("UTF-8");
    				 String usuario = request.getParameter("usuario");
    				 String tramite = request.getParameter("tramite");
    				 String flag = request.getParameter("flag");
    				 //si viene de modificación
    				 if(StringUtils.isNotBlank(usuario) && StringUtils.isNotBlank(tramite) && StringUtils.isEmpty(flag)){
    					RolUsuarioTramite userForm = new RolUsuarioTramite(new RolUsuarioTramiteId(usuario.trim(),new Long(tramite)));
    					gruposDelegate.asociarUsuarioTramite(userForm);
    				 }else if(StringUtils.isNotEmpty(flag)){
   						RolUsuarioTramite userForm = gruposDelegate.obtenerUsuarioTramite(new RolUsuarioTramiteId(usuario.trim(), new Long(tramite)));
						gruposDelegate.eliminarUsuarioTramite(userForm);
    				 }
    				 if(StringUtils.isNotBlank(tramite)){
   						usuarios = gruposDelegate.listarUsuariosByTramite(new Long(tramite));
    				 }
    				 //formatea los datos para la recarga del div
    				 if(usuarios!=null && usuarios.size()>0){
						tabla = "<table class=\"marc\">";
						for(int i=0;i<usuarios.size();i++){
							RolUsuarioTramite usuarioForm  = (RolUsuarioTramite) usuarios.get(i);
							tabla += "<tr><td class=\"outputd\">" + usuarioForm.getId().getCodiUsuario() + " </td>";
							tabla +=  " <td align=\"left\"><button class=\"button\" type=\"button\" onclick=\"eliminar('" + usuarioForm.getId().getCodiUsuario() 
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
