package org.ibit.rol.form.front.action.expresion;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.ibit.rol.form.front.action.BaseAction;
import org.ibit.rol.form.front.action.PantallaForm;
import org.ibit.rol.form.front.registro.RegistroManager;
import org.ibit.rol.form.persistence.delegate.InstanciaDelegate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Calcula la expresió de dependència d'un camp de la pantalla actual.
 * El resultat es torna com a text pla, per emprar amb objectes XMLHttpRequest
 * desde javascript.
 * @struts.action
 *  name="pantallaForm"
 *  path="/expresion/dependencia"
 *  scope="request"
 *  validate="false"
 *
 * @struts.action
 *  name="pantallaForm"
 *  path="/auth/expresion/dependencia"
 *  scope="request"
 *  validate="false"
 *
 * @struts.action
 *  name="pantallaForm"
 *  path="/secure/expresion/dependencia"
 *  scope="request"
 *  validate="false"
 *
 * @struts.action
 *  name="pantallaForm"
 *  path="/auth/secure/expresion/dependencia"
 *  scope="request"
 *  validate="false"
 *
 */
public class CalcularDependenciaAction extends BaseAction {

    protected static Log log = LogFactory.getLog(CalcularDependenciaAction.class);

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

        boolean result = delegate.expresionDependenciaCampo(fieldName);
        response.setContentType("text/plain; charset=UTF-8");
        response.getWriter().print(result);
        return null;
    }
}
