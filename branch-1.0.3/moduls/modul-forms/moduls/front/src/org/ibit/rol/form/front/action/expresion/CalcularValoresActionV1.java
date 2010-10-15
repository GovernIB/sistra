package org.ibit.rol.form.front.action.expresion;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.ibit.rol.form.front.action.BaseAction;
import org.ibit.rol.form.front.action.PantallaForm;
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
 *  path="/expresion/valoresV1"
 *  scope="request"
 *  validate="false"
 *
 * @struts.action
 *  name="pantallaForm"
 *  path="/auth/expresion/valoresV1"
 *  scope="request"
 *  validate="false"
 *
 * @struts.action
 *  name="pantallaForm"
 *  path="/secure/expresion/valoresV1"
 *  scope="request"
 *  validate="false"
 *
 * @struts.action
 *  name="pantallaForm"
 *  path="/auth/secure/expresion/valoresV1"
 *  scope="request"
 *  validate="false"
 *
 */
public class CalcularValoresActionV1 extends BaseAction {

    protected static Log log = LogFactory.getLog(CalcularValoresActionV1.class);

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
        
        // Obtenemos lista de valores posibles y los traducimos a un array de js
        List valores = delegate.expresionValoresPosiblesCampo(fieldName);       
        String jsValores = JSUtil.valoresPosiblesToJS(valores);
        if (jsValores != null) {
        	response.setContentType("text/plain; charset=UTF-8");
        	response.getWriter().print(jsValores);
        }
        
        return null;
        
    }
}
