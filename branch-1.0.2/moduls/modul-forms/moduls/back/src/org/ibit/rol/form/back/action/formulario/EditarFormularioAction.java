package org.ibit.rol.form.back.action.formulario;

import org.ibit.rol.form.back.action.BaseAction;
import org.ibit.rol.form.back.form.FormularioForm;
import org.ibit.rol.form.back.util.Util;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.ibit.rol.form.persistence.delegate.FormularioDelegate;
import org.ibit.rol.form.model.Formulario;
import org.ibit.rol.form.model.TraFormulario;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Action para editar un Formulario.
 *
 * @struts.action
 *  name="formularioForm"
 *  scope="session"
 *  validate="true"
 *  input=".formulario.editar"
 *  path="/back/formulario/editar"
 *
 * @struts.action-forward
 *  name="reload" path=".formulario.editar"
 *
 * @struts.action-forward
 *  name="success" path=".formulario.editar"
 *
 * @struts.action-forward
 *  name="error" path=".idOperacion.error"
 *
 * @struts.action-forward
 *  name="cancel" path=".formulario.lista"
 *
 */
public class EditarFormularioAction extends BaseAction{

    protected static Log log = LogFactory.getLog(EditarFormularioAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.debug("Entramos en EditarFormulario");

        FormularioDelegate formularioDelegate = DelegateUtil.getFormularioDelegate();
        FormularioForm formularioForm = (FormularioForm) form;
        Formulario formulario = (Formulario) formularioForm.getValues();
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
        
        
        formulario.setModoFuncionamiento(DelegateUtil.getVersionDelegate().obtener(formularioForm.getModoFuncionamientoCod()));


        // Elimina traducciones que no son validas
        formularioForm.validaTraduccion(mapping, request);

        if (isAlta(request) || isModificacion(request)) {
            log.debug("isAlta || isModificacio");

            formularioDelegate.gravarFormulario(formulario);

            request.setAttribute("reloadMenu", "true");
            log.debug("Creat/Actualitzat " + formulario.getModelo() + "v" + formulario.getVersion());
            guardarFormulario(mapping, request, formulario.getId());

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
