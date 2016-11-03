package org.ibit.rol.form.admin.action.patron;

import org.ibit.rol.form.admin.action.BaseAction;
import org.ibit.rol.form.admin.form.PatronForm;
import org.ibit.rol.form.persistence.delegate.PatronDelegate;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.ibit.rol.form.model.Patron;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action para editar un Patron.
 *
 * @struts.action
 *  name="patronForm"
 *  scope="request"
 *  validate="false"
 *  path="/admin/patron/editar"
 *
 * @struts.action-forward
 *  name="success" path=".patron.lista"
 *
 * @struts.action-forward
 *  name="cancel" path=".patron.lista"
 *  
 *  @struts.action-forward
 *  name="fail" path=".patron.editar"
 */
public class EditarPatronAction extends BaseAction{
    protected static Log log = LogFactory.getLog(EditarPatronAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
        log.debug("Entramos en EditarPatron");
        PatronDelegate patronDelegate = DelegateUtil.getPatronDelegate();
        PatronForm patronForm = (PatronForm) form;
        Patron patron = patronForm.getValues();

        if (isCancelled(request)) {
            log.debug("isCancelled");
            return mapping.findForward("cancel");
        }

        if (isAlta(request) || isModificacio(request)) {
            log.debug("isAlta || isModificacio");
        	Patron pat = patronDelegate.obtenerPatron(patron.getNombre());
        	if(pat != null){
        		if((isModificacio(request) && patron.getId().longValue()!=pat.getId().longValue()) || isAlta(request)){
        			ActionErrors errors = new ActionErrors();
	        		errors.add("version", new ActionError("errors.patron.nombreDuplicado"));
	            	saveErrors(request, errors);
	            	return mapping.findForward("fail");
        		}
        	}
            patronDelegate.gravarPatron(patron);
            log.debug("Creat/Actualitzat " + patron.getNombre());
            return mapping.findForward("success");
        }

        return mapping.findForward("success");
    }

}
