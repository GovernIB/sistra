package org.ibit.rol.form.back.action.formularioseguro;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.ibit.rol.form.back.action.BaseAction;
import org.ibit.rol.form.back.form.FormularioForm;
import org.ibit.rol.form.back.util.Util;
import org.ibit.rol.form.model.FormularioSeguro;
import org.ibit.rol.form.model.TraFormulario;
import org.ibit.rol.form.model.ValidadorFirma;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.ibit.rol.form.persistence.delegate.FormularioDelegate;
import org.ibit.rol.form.persistence.delegate.ValidadorFirmaDelegate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Action para editar un Formulario Seguro.
 *
 * @struts.action
 *  name="formularioSeguroForm"
 *  scope="session"
 *  validate="true"
 *  input=".formularioseguro.editar"
 *  path="/back/formularioseguro/editar"
 *
 * @struts.action-forward
 *  name="reload" path=".formularioseguro.editar"
 *
 * @struts.action-forward
 *  name="success" path=".formularioseguro.editar"
 *
 * @struts.action-forward
 *  name="error" path=".idOperacion.error"
 *
 * @struts.action-forward
 *  name="cancel" path=".formulario.lista"
 *
 */
public class EditarFormularioSeguroAction extends BaseAction {
    protected static Log log = LogFactory.getLog(EditarFormularioSeguroAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.debug("Entramos en EditarFormulario");

        FormularioDelegate formularioDelegate = DelegateUtil.getFormularioDelegate();
        FormularioForm formularioForm = (FormularioForm) form;
        FormularioSeguro formulario = (FormularioSeguro) formularioForm.getValues();
        TraFormulario traForm = (TraFormulario) formularioForm.getTraduccion();

        if(Util.getOperacionPermitida(request)){
        
        if (isCancelled(request)) {
            log.debug("isCancelled");
            formularioForm.destroy(mapping, request);
            return mapping.findForward("cancel");
        }

        // Tratamiento de los ficheros
        if (request.getParameter("borrarLogo1") != null) {
            formularioDelegate.borrarLogotipo1(formulario.getId(), formulario.getLogotipo1().getId());
            formulario.setLogotipo1(null);
            return mapping.findForward("reload");
        } else {
            if (archivoValido(formularioForm.getLogotipo1())) {
                formulario.setLogotipo1(populateArchivo(formulario.getLogotipo1(), formularioForm.getLogotipo1()));
            }
        }

        if (request.getParameter("borrarLogo2") != null) {
            formularioDelegate.borrarLogotipo2(formulario.getId(), formulario.getLogotipo2().getId());
            formulario.setLogotipo2(null);
            return mapping.findForward("reload");
        } else {
            if (archivoValido(formularioForm.getLogotipo2())) {
                formulario.setLogotipo2(populateArchivo(formulario.getLogotipo2(), formularioForm.getLogotipo2()));
            }
        }

        if (request.getParameter("borrarPlantilla") != null) {
            formularioDelegate.borrarPlantilla(formulario.getId(), formularioForm.getLang());
            traForm.setPlantilla(null);
            return mapping.findForward("reload");
        } else {
            if (archivoValido(formularioForm.getPlantilla())) {
                traForm.setPlantilla(populateArchivo(traForm.getPlantilla(), formularioForm.getPlantilla()));
            }
        }

        if(formulario.isRequerirLogin()){
            formulario.removeAllRoles();
            String rolesString = formularioForm.getRolesString();
            String[] roles = rolesString.split(",");
            if(roles.length>0){
                for(int i = 0; i < roles.length; i++){
                    formulario.addRole(roles[i]);
                }
            }
        } else{
            formulario.removeAllRoles();
        }


        if(formulario.isRequerirFirma()){
            formulario.removeAllValidadores();
            Long[] ids = formularioForm.getValidadores_ids();
            if(ids.length > 0){
                ValidadorFirmaDelegate validadorDelegate = DelegateUtil.getValidadorFirmaDelegate();
                List validadores = validadorDelegate.obtenerValidadoresFirma(ids);
                for (int i = 0; i < validadores.size(); i++) {
                    ValidadorFirma validador =  (ValidadorFirma)validadores.get(i);
                    formulario.addValidador(validador);
                }
            }
        }else {
            formulario.removeAllValidadores();
        }




        // Elimina traducciones que no son validas
        formularioForm.validaTraduccion(mapping, request);



        if (isAlta(request) || isModificacion(request)) {
            log.debug("isAlta || isModificacio");
            formularioDelegate.gravarFormulario(formulario);
            request.setAttribute("reloadMenu", "true");
            log.debug("Creat/Actualitzat " + formulario.getModelo());

            guardarFormularioSeguro(mapping, request, formulario.getId());

            return mapping.findForward("success");

        }

        

        // Cambio de idioma
        formularioForm.reloadLang();
        return mapping.findForward("reload");
        }else{
       		log.debug("Error el id de operación modificado es diferente al id de operación de la sesión.");
        	return mapping.findForward("error");
       	}
    }
}
