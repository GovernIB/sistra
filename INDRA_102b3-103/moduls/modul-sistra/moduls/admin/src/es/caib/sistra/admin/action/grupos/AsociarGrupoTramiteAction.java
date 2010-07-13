package es.caib.sistra.admin.action.grupos;

import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.sistra.admin.form.GrupoForm;
import es.caib.sistra.admin.json.JSONObject;
import es.caib.sistra.model.Grupos;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.sistra.persistence.delegate.GruposDelegate;





/**
 * Action para preparar el alta de un Grupo.
 *
 * @struts.action
 * path="/admin/tramite/asociar"
 *
 */
public class AsociarGrupoTramiteAction extends Action {
    	 private static Log _log = LogFactory.getLog( AsociarGrupoTramiteAction.class );
    		public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    		{
    			JSONObject json = new JSONObject();
    			try{
    				 request.setCharacterEncoding("UTF-8");
    				 String grupo = request.getParameter("grupo");
    				 String tramite = request.getParameter("idTramite");
    				 String gruposNoAsoc = request.getParameter("gruposNoAsoc");
    				 
    				 if(StringUtils.isNotEmpty(grupo) && StringUtils.isNotEmpty(tramite)){
    					 GruposDelegate gruposDelegate = DelegateUtil.getGruposDelegate();
    					 StringTokenizer st = new StringTokenizer(grupo,",");
    					 while(st.hasMoreTokens()){
    						 String grupoAsociar = st.nextToken().trim();
    						 gruposDelegate.desAsociarGrupo(grupoAsociar,tramite);
    					 }
    				 }
    				 if(StringUtils.isNotEmpty(gruposNoAsoc) && StringUtils.isNotEmpty(tramite)){
    					 GruposDelegate gruposDelegate = DelegateUtil.getGruposDelegate();
    					 StringTokenizer st = new StringTokenizer(gruposNoAsoc,",");
    					 while(st.hasMoreTokens()){
    						 String grupoAsociar = st.nextToken().trim();
    						 gruposDelegate.asociarGrupo(grupoAsociar,tramite);
    					 }
    				 }
    			
    				 json.put("asociado","true");
    				
    			}catch(Exception ex){
   				 	json.put("asociado","false");
    				_log.error(ex);
    			}
    			populateWithJSON(response,json.toString());
    			return null;
		
    }


	private Grupos asignarGrupo(GrupoForm grupoForm) {
		Grupos g = new Grupos();
		g.setCodigo(grupoForm.getCodigo());
		g.setNombre(grupoForm.getNombre());
		g.setDescripcion(grupoForm.getDescripcion());
		return g;
	}
	
	private static void populateWithJSON(HttpServletResponse response,String json) throws Exception{
		String CONTENT_TYPE="text/x-json;charset=UTF-8";
		response.setContentType(CONTENT_TYPE);
		response.setHeader("Cache-Control", "no-cache");
		response.getWriter().write(json); 		 		
	}

}
