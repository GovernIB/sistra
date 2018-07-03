package es.caib.bantel.back.action.tramite;

import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.bantel.back.action.BaseAction;
import es.caib.bantel.back.json.JSONObject;
import es.caib.bantel.model.Procedimiento;
import es.caib.bantel.modelInterfaz.ConstantesBTE;
import es.caib.bantel.persistence.delegate.DelegateUtil;
import es.caib.bantel.persistence.delegate.ProcedimientoDelegate;
import es.caib.xml.ConstantesXML;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action para consultar los errores (como mucho se guardan dos) de integración de un tramite.
 *
 * @struts.action
 *  path="/mostrarErrores"
 */
public class MostrarErroresAction extends BaseAction{
    protected static Log log = LogFactory.getLog(MostrarErroresAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        String idString = request.getParameter("codigoTramiteError");
        JSONObject jsonObject = new JSONObject();		   
        if (idString == null || idString.length() == 0) {
            log.warn("El paràmetre codigo és null!!");
            jsonObject.put("StringErrors","");
    		jsonObject.put("error",this.getResources(request).getMessage("tramite.error.mostrarError.no.codigo"));
        }else{
        	try {
        		Long id = new Long(idString);
        		ProcedimientoDelegate delegate = DelegateUtil.getTramiteDelegate();
	    		Procedimiento tramite = delegate.obtenerProcedimiento(id);
	    		if(tramite != null && tramite.getErrores() != null){
    			
    				ArrayList errores = new ArrayList();
    				String error = new String( tramite.getErrores(), ConstantesXML.ENCODING );
    				if(error.lastIndexOf(ConstantesBTE.MARCA_ERROR) != -1 && error.lastIndexOf(ConstantesBTE.MARCA_ERROR) != 0){
    					//quiere decir que hay mas de un error en el String
    					errores.add(error.substring(ConstantesBTE.MARCA_ERROR.length(),error.lastIndexOf(ConstantesBTE.MARCA_ERROR)));
    					errores.add(error.substring(error.lastIndexOf(ConstantesBTE.MARCA_ERROR)+ConstantesBTE.MARCA_ERROR.length(),error.length()));
    				}else if(error.lastIndexOf(ConstantesBTE.MARCA_ERROR) != -1){
    					errores.add(error.substring(ConstantesBTE.MARCA_ERROR.length(),error.length()));
    				}
    				String tablaErrores = "";
    				for(int i=0;i<errores.size();i++){
    					tablaErrores = tablaErrores + "<br/> "+errores.get(i)+"<br/>";
    				}
    				jsonObject.put("StringErrors",tablaErrores);
            		jsonObject.put("error","");
    			}else{
	                jsonObject.put("StringErrors","");
	        		jsonObject.put("error",this.getResources(request).getMessage("tramite.error.mostrarError.tramite.no.encontrado"));
	    		}
        	} catch (Exception e) {
				jsonObject.put("StringErrors","");
        		jsonObject.put("error",this.getResources(request).getMessage("tramite.error.mostrarError.error.general"));
			} 
    		
        }
        populateWithJSON(response,jsonObject.toString());
		return null;
	}
	
	
	private static void populateWithJSON(HttpServletResponse response,String json) throws Exception{
		String CONTENT_TYPE="text/x-json;charset=UTF-8";
		response.setContentType(CONTENT_TYPE);
		response.setHeader("Cache-Control", "no-cache");
		response.getWriter().write(json); 		 
	}
}
