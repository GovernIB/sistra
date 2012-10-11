package org.ibit.rol.form.admin.action.validador;

import org.ibit.rol.form.admin.action.BaseAction;
import org.ibit.rol.form.admin.form.ValidadorFirmaForm;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.ibit.rol.form.persistence.delegate.ValidadorFirmaDelegate;
import org.ibit.rol.form.model.ValidadorFirma;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action para editar un Validador Firma
 *
 * @struts.action
 *  name="validadorFirmaForm"
 *  scope="request"
 *  validate="false"
 *  path="/admin/validador/editar"
 *
 * @struts.action-forward
 *  name="success" path=".validadorfirma.lista"
 *
 * @struts.action-forward
 *  name="cancel" path=".validadorfirma.lista"
 */
public class EditarValidadorAction extends BaseAction {
    protected static Log log = LogFactory.getLog(EditarValidadorAction.class);

       public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {

           log.debug("Entramos en EditarPatron");
           ValidadorFirmaDelegate validadorDelegate = DelegateUtil.getValidadorFirmaDelegate();
           ValidadorFirmaForm validadorForm = (ValidadorFirmaForm) form;
           ValidadorFirma validador = validadorForm.getValues();

           if (isCancelled(request)) {
               log.debug("isCancelled");
               return mapping.findForward("cancel");
           }

           if (isAlta(request) || isModificacio(request)) {
               log.debug("isAlta || isModificacio");
               validadorDelegate.gravarValidadorFirma(validador);
               log.debug("Creat/Actualitzat " + validador.getNombre());
               return mapping.findForward("success");
           }

           return mapping.findForward("success");
       }
    
}
