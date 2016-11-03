package org.ibit.rol.form.back.action.componente.captcha;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.ibit.rol.form.back.action.BaseAction;
import org.ibit.rol.form.back.form.ComponenteForm;
import org.ibit.rol.form.back.util.Util;
import org.ibit.rol.form.model.Campo;
import org.ibit.rol.form.model.Componente;
import org.ibit.rol.form.model.Validacion;
import org.ibit.rol.form.model.Pantalla;
import org.ibit.rol.form.persistence.delegate.ComponenteDelegate;
import org.ibit.rol.form.persistence.delegate.DelegateException;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.ibit.rol.form.persistence.delegate.MascaraDelegate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action para editar un componente Captcha.
 *
 * @struts.action
 *  name="captchaForm"
 *  scope="session"
 *  validate="true"
 *  input=".captcha.editar"
 *  path="/back/captcha/editar"
 *
 * @struts.action-forward
 *  name="reload" path=".captcha.editar"
 *
 * @struts.action-forward
 *  name="cancel" path=".pantalla.editar"
 *
 * @struts.action-forward
 *  name="error" path=".idOperacion.error"
 *
 * @struts.action-forward
 *  name="success" path=".pantalla.editar"
 */
public class EditarCaptchaAction extends BaseAction{
     protected static Log log = LogFactory.getLog(EditarCaptchaAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.debug("Entramos en EditarCaptchaAction");

        ComponenteDelegate componenteDelegate = DelegateUtil.getComponenteDelegate();
        ComponenteForm componenteForm = (ComponenteForm) form;
        Componente componente = (Componente) componenteForm.getValues();

        if(Util.getOperacionPermitida(request)){ 
        if (isCancelled(request)) {
           log.debug("isCancelled");
           Long idPantalla = componenteForm.getIdPantalla();
           Pantalla pantalla = guardarPantalla(mapping, request, idPantalla);
           return mapping.findForward("cancel");
        }

        // Elimina traducciones que no son validas
        componenteForm.validaTraduccion(mapping, request);

        if (isAlta(request) || isModificacion(request)) {
            log.debug("isAlta || isModificacio");
            
            componenteDelegate.gravarComponentePantalla(componente, componenteForm.getIdPantalla());
            componenteForm.setId(componente.getId());

            log.debug("Creat/Actualitzat " + componente.getId());
            request.setAttribute("reloadMenu", "true");

            guardarPantalla(mapping,request,componenteForm.getIdPantalla());
            return mapping.findForward("success");
        }

      
        // Cambio de idioma
        componenteForm.reloadLang();
       // request.setAttribute("editar", param);
        return mapping.findForward("reload");
        }else{
        	log.debug("Error el id de operación modificado es diferente al id de operación de la sesión.");
        	return mapping.findForward("error");
        }
    }

    private void insertarValidaciones(ComponenteForm componenteForm){

        log.debug("Entra en InsertarValidaciones");

        MascaraDelegate mascaraDelegate = DelegateUtil.getMascaraDelegate();
        try{
            Campo campo = (Campo) componenteForm.getValues();
            campo.getValidaciones().clear();
            for(int aux=0; aux < componenteForm.getValidacion().length; aux++){
                if(componenteForm.getValidacion()[aux].isActivo()){
                    Validacion validacion = new Validacion();
                    Long idMascara = componenteForm.getValidacion()[aux].getMascara_id();
                    validacion.setMascara(mascaraDelegate.obtenerMascara(idMascara));
                    if (componenteForm.getValidacion()[aux].getValores() != null){
                        validacion.setValores(componenteForm.getValidacion()[aux].getValores());
                    }
                    campo.addValidacion(validacion);
                }
            }
        } catch(DelegateException e){
            log.error(e.getMessage());
        }

    }
}
