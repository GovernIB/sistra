package org.ibit.rol.form.front.action;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionFormClass;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionError;
import org.apache.struts.config.FormBeanConfig;
import org.apache.struts.config.FormPropertyConfig;
import org.apache.struts.validator.DynaValidatorForm;
import org.apache.struts.validator.Resources;
import org.ibit.rol.form.front.validator.DynValidatorResources;
import org.ibit.rol.form.front.registro.RegistroManager;
import org.ibit.rol.form.model.Campo;
import org.ibit.rol.form.model.Pantalla;
import org.ibit.rol.form.model.TraCampo;
import org.ibit.rol.form.model.ListBox;
import org.ibit.rol.form.model.TreeBox;
import org.ibit.rol.form.persistence.delegate.DelegateException;
import org.ibit.rol.form.persistence.delegate.InstanciaDelegate;
import org.ibit.rol.form.persistence.util.ScriptUtil;

/**
 * @struts.form name="pantallaForm"
 */
public class PantallaForm extends DynaValidatorForm {

    protected static Log log = LogFactory.getLog(PantallaForm.class);

    public void reset(ActionMapping mapping, HttpServletRequest request) {
        log.debug("reset");
        super.reset(mapping, request);
        try {
            InstanciaDelegate delegate = RegistroManager.recuperarInstancia(request);
            Pantalla pantalla = delegate.obtenerPantalla();

            FormBeanConfig config = new FormBeanConfig();
            config.setName("p_" + pantalla.getId());
            config.setType(this.getClass().getName());
            config.setModuleConfig(mapping.getModuleConfig());

            for (int i = 0; i < pantalla.getCampos().size(); i++) {
                Campo campo = (Campo) pantalla.getCampos().get(i);
                config.addFormPropertyConfig(getCampoConfig(campo));
            }

            // Això nomes s'hauria de fer quan desde el back s'actualitza una pantalla - camp.
            // per poder aprofitar el cache the dynaClass.
            DynaActionFormClass.clear();
            dynaClass = DynaActionFormClass.createDynaActionFormClass(config);

            FormPropertyConfig props[] = config.findFormPropertyConfigs();
            for (int i = 0; i < props.length; i++) {
                this.set(props[i].getName(), props[i].initial());
            }

            // Preparar resources de validacion
            ServletContext application = getServlet().getServletContext();
            DynValidatorResources resources =
                    (DynValidatorResources) Resources.getValidatorResources(application, request);
            resources.setPantalla(pantalla);

        } catch (DelegateException e) {
            log.error("Excepción en reset", e);
        } catch (Throwable t) {
            log.error("Error en reset", t);
        }
    }

    private FormPropertyConfig getCampoConfig(Campo campo) {
        FormPropertyConfig config = new FormPropertyConfig();
        config.setName(campo.getNombreLogico());
        if (!campo.isIndexed()) {
            config.setType(campo.getTipoValor());
        } else {
            String type = "java.lang.String";
            if (campo instanceof ListBox || campo instanceof TreeBox) {
                type += "[]";
            }
            config.setType(type);
        }
        return config;
    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = null;
        try {
            errors = super.validate(mapping, request);
            if (errors == null) {
                errors = new ActionErrors();
            }

            InstanciaDelegate delegate = RegistroManager.recuperarInstancia(request);
            Pantalla pantalla = delegate.obtenerPantalla();

            Map dades = ScriptUtil.prefixMap(getMap(), "f_");
            dades.putAll(delegate.obtenerDatosListasElementos());
            dades.putAll(delegate.obtenerDatosAnteriores());


            for (int i = 0; i < pantalla.getCampos().size(); i++) {
                Campo campo = (Campo) pantalla.getCampos().get(i);
                String expresion = campo.getExpresionValidacion();
                if (expresion != null && expresion.length() > 0) {
                    boolean correct = ScriptUtil.evalBoolScript(expresion, dades);
                    if (!correct) {
                        TraCampo traCampo = (TraCampo) campo.getTraduccion();
                        String mensaje = traCampo.getMensajeValidacion();
                        ActionError error;
                        if (mensaje == null || mensaje.length() == 0) {
                            error = new ActionError("errors.invalid", traCampo.getNombre());
                        } else {
                            error = new ActionError("errors.message", mensaje);
                        }
                        errors.add(campo.getNombreLogico(), error);
                    }
                }
            }
        } catch (DelegateException e) {
            log.error("Excepción en validate", e);
        } catch (Throwable t) {
            log.error("Error en validate", t);
        }

        return errors;
    }
}
