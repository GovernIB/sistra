package org.ibit.rol.form.front.action.expresion;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.ibit.rol.form.front.action.BaseAction;
import org.ibit.rol.form.front.registro.RegistroManager;
import org.ibit.rol.form.persistence.delegate.InstanciaDelegate;

/**
 * Regenera valor del captcha.
 * 
 * @struts.action
 *  name="pantallaForm"
 *  path="/expresion/recaptchaV2"
 *  scope="request"
 *  validate="false"
 *
 * @struts.action
 *  name="pantallaForm"
 *  path="/auth/expresion/recaptchaV2"
 *  scope="request"
 *  validate="false"
 *
 * @struts.action
 *  name="pantallaForm"
 *  path="/secure/expresion/recaptchaV2"
 *  scope="request"
 *  validate="false"
 *
 * @struts.action
 *  name="pantallaForm"
 *  path="/auth/secure/expresion/recaptchaV2"
 *  scope="request"
 *  validate="false"
 *
 */
public class RegenerarCaptchaActionV2 extends BaseAction {

    protected static Log log = LogFactory.getLog(RegenerarCaptchaActionV2.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
    	String res = "OK";
    	try {
    	request.setCharacterEncoding("utf-8");
    	
        InstanciaDelegate delegate = RegistroManager.recuperarInstancia(request);
        if (delegate == null) return null;

        String fieldName = request.getParameter("fieldName");
        if (fieldName == null || fieldName.trim().length() == 0) {
            return null;
        }

        delegate.regenerarCaptcha(fieldName);
        
    	} catch (Exception ex) {
    		res = "ERROR";
    	}
    	
    	response.setContentType("text/plain; charset=UTF-8");
        response.getWriter().print(res);
        return null;
        
    }
}
