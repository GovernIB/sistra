package org.ibit.rol.form.front.action.expresion;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.ibit.rol.form.front.action.BaseAction;
import org.ibit.rol.form.front.action.PantallaForm;
import org.ibit.rol.form.front.json.JSONArray;
import org.ibit.rol.form.front.registro.RegistroManager;
import org.ibit.rol.form.front.util.JSUtil;
import org.ibit.rol.form.front.util.Util;
import org.ibit.rol.form.persistence.delegate.InstanciaDelegate;

/**
 * Calcula la expresió d'autocalcul d'un camp de la pantalla actual.
 * El resultat es torna com a text pla, per emprar amb objectes XMLHttpRequest
 * desde javascript.
 * @struts.action
 *  name="pantallaForm"
 *  path="/expresion/autocalculoV1"
 *  scope="request"
 *  validate="false"
 *
 * @struts.action
 *  name="pantallaForm"
 *  path="/auth/expresion/autocalculoV1"
 *  scope="request"
 *  validate="false"
 *
 * @struts.action
 *  name="pantallaForm"
 *  path="/secure/expresion/autocalculoV1"
 *  scope="request"
 *  validate="false"
 *
 * @struts.action
 *  name="pantallaForm"
 *  path="/auth/secure/expresion/autocalculoV1"
 *  scope="request"
 *  validate="false"
 *
 */
public class CalcularAutocalculoActionV1 extends BaseAction {

    protected static Log log = LogFactory.getLog(CalcularAutocalculoActionV1.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

    	request.setCharacterEncoding("utf-8");
    	
        InstanciaDelegate delegate = RegistroManager.recuperarInstancia(request);
        if (delegate == null) return null;

        String fieldName = request.getParameter("fieldName");
        if (fieldName == null || fieldName.trim().length() == 0) {
            return null;
        }

        PantallaForm pantallaForm = (PantallaForm) form;
        Map valoresForm = pantallaForm.getMap();
        Map paramsUnescaped = new HashMap();
        for (Iterator it = valoresForm.keySet().iterator();it.hasNext();){        
    		String param = (String) it.next();  
    		
    		Object values = valoresForm.get(param);
    		if (values == null){ 
    			paramsUnescaped.put(param,null);    			
    		}else if (values instanceof java.lang.String[]){
    			String[] arrValues = (String []) values;
    			String[] arrValuesUnescaped = new String[arrValues.length];
    			for (int i=0;i<arrValues.length;i++){
	    			String valueEscaped = arrValues[i];    		
	        		String value = Util.unescape(valueEscaped);
	        		arrValuesUnescaped[i] = value;
    			}
    			paramsUnescaped.put(param,arrValuesUnescaped);
    		}else{
    			String valueEscaped = valoresForm.get(param).toString();    		
        		String value = Util.unescape(valueEscaped);
        		paramsUnescaped.put(param,value);
    		}
    		
    	}                    
        delegate.introducirDatosPantalla(paramsUnescaped);

        // Llamamos a expresion de autocalculo
        Object result = delegate.expresionAutocalculoCampo(fieldName);
        
        // Controlamos si el resultado es un unico String (campos monovaluados) o un array de String (campos multivaluados)
        String resultStr;
        if (result instanceof List){
        	// Campos multivaluados: devolvemos array de String
        	JSONArray array = new JSONArray();
        	List results = (List) result;
            for (int i = 0; i < results.size(); i++) {
                array.put((String) results.get(i));
            }	
            resultStr = "eval('" + StringUtils.replace(array.toString(), "'", "\\'") + "')";
        }else{
        	// Campos monovaluados: devolvemos String normalizando saltos de línea
        	resultStr=JSUtil.escapeStringToJS((String) result);        		
        }                        
        
        response.setContentType("text/plain; charset=UTF-8");
        response.getWriter().print(resultStr);
        return null;
    }
}
