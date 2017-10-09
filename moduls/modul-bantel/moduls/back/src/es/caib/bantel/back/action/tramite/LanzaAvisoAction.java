package es.caib.bantel.back.action.tramite;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import es.caib.bantel.back.taglib.Constants;
import es.caib.bantel.back.util.MensajesUtil;
import es.caib.bantel.back.json.JSONObject;
import es.caib.bantel.model.Procedimiento;
import es.caib.bantel.model.TraProcedimiento;
import es.caib.bantel.persistence.delegate.BteProcesosDelegate;
import es.caib.bantel.persistence.delegate.DelegateException;
import es.caib.bantel.persistence.delegate.DelegateUtil;
import es.caib.bantel.persistence.delegate.ProcedimientoDelegate;

/**
 * @struts.action
 *  path="/back/tramite/lanzaAviso"
 * 
 */
public class LanzaAvisoAction extends Action
{
	private static Log _log = LogFactory.getLog( LanzaAvisoAction.class );
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String error = "";
		String idProcedimiento = StringUtils.trim(request.getParameter("proc"));
		JSONObject json = new JSONObject();
		
		if (idProcedimiento == null || idProcedimiento.length() == 0) {
            _log.warn("El paràmetre codigo és null!!");
            json.put("error",this.getResources(request).getMessage("errors.avisoLanzado.no.codigo"));
        }else{
    		try{
    			
    			ProcedimientoDelegate delegate = DelegateUtil.getTramiteDelegate();
        		Procedimiento tramite = delegate.obtenerProcedimiento(idProcedimiento);
        		
        		ArrayList errores = new ArrayList();
        		
        		if ( (tramite.getTipoAcceso() == Procedimiento.ACCESO_EJB && tramite.getLocalizacionEJB() == Procedimiento.EJB_REMOTO) 
            		|| 
            		tramite.getTipoAcceso() == Procedimiento.ACCESO_WEBSERVICE){
    		    		if (StringUtils.isEmpty(tramite.getUrl())){
    		        		errores.add(getMessage(request, "errors.url.vacia"));
    		        	}        			
            	}
            	//version WS
            	if(tramite.getTipoAcceso() == Procedimiento.ACCESO_WEBSERVICE){
            		if (StringUtils.isEmpty(tramite.getVersionWS())){
            			errores.add(getMessage(request, "errors.versionWS.vacia"));
    	        	}
            	}
            		
            	// Jndi
            	if (tramite.getTipoAcceso() == Procedimiento.ACCESO_EJB && StringUtils.isEmpty(tramite.getJndiEJB())){
    		    	errores.add(getMessage(request, "errors.jndi.vacia"));
            	}
            		
            	// Usr y pswd
            	if (tramite.getAutenticacionEJB() == Procedimiento.AUTENTICACION_ESTANDAR){        		
            		if (StringUtils.isEmpty(tramite.getUsr()) || StringUtils.isEmpty(tramite.getPwd()))
            			errores.add(getMessage(request, "errors.userpasswd.vacio"));
            	}
            	
            	String tablaErrores = getMessage(request, "errors.avisoLanzado");
				for(int i=0;i<errores.size();i++){
					tablaErrores = tablaErrores + "\n- "+errores.get(i)+"\n";
				}
        		
        		if (errores.size() == 0) {
        			BteProcesosDelegate delegateProc = DelegateUtil.getBteProcesosDelegate();	
        			delegateProc.avisoBackOffices(idProcedimiento);
        		}else{
        			error = tablaErrores;
        		}
    			
    		}catch(Exception ex){
    			_log.error("Exception en aviso manual de entradas del procedimiento " + idProcedimiento + " : " + ex.getMessage(), ex);
    			error = getMessage(request, "errors.avisoLanzado.errorDesconocido");
    		}
        }
		
		
		// Convertimos a json
		json.put("error", error);
		populateWithJSON(response,json.toString());
		return null;
	}
	
	private static void populateWithJSON(HttpServletResponse response,String json) throws Exception{
		String CONTENT_TYPE="text/x-json;charset=UTF-8";
		response.setContentType(CONTENT_TYPE);
		response.setHeader("Cache-Control", "no-cache");
		response.getWriter().write(json); 		 		
	}
	
	private String getMessage(HttpServletRequest request, String key){
		MessageResources resources = ((MessageResources) request.getAttribute(Globals.MESSAGES_KEY));
		return resources.getMessage( getLocale( request ), key );
	}
	
	
}
