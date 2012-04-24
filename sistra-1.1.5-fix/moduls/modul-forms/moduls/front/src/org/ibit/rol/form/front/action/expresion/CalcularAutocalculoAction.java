package org.ibit.rol.form.front.action.expresion;

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
import org.ibit.rol.form.persistence.delegate.InstanciaDelegate;

/**
 * Calcula la expresió d'autocalcul d'un camp de la pantalla actual.
 * El resultat es torna com a text pla, per emprar amb objectes XMLHttpRequest
 * desde javascript.
 * @struts.action
 *  name="pantallaForm"
 *  path="/expresion/autocalculo"
 *  scope="request"
 *  validate="false"
 *
 * @struts.action
 *  name="pantallaForm"
 *  path="/auth/expresion/autocalculo"
 *  scope="request"
 *  validate="false"
 *
 * @struts.action
 *  name="pantallaForm"
 *  path="/secure/expresion/autocalculo"
 *  scope="request"
 *  validate="false"
 *
 * @struts.action
 *  name="pantallaForm"
 *  path="/auth/secure/expresion/autocalculo"
 *  scope="request"
 *  validate="false"
 *
 */
public class CalcularAutocalculoAction extends BaseAction {

    protected static Log log = LogFactory.getLog(CalcularAutocalculoAction.class);

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
        delegate.introducirDatosPantalla(pantallaForm.getMap());

        //Object result = delegate.expresionAutocalculoCampo(fieldName);
        String result = (String) delegate.expresionAutocalculoCampo(fieldName);
        String res = ( result!=null?result:"");
        
        response.setContentType("text/plain; charset=UTF-8");
        response.getWriter().print(res);
        return null;
    }
}
