package org.ibit.rol.form.front.validator;

import java.util.Collections;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.Form;
import org.apache.commons.validator.ValidatorResources;
import org.ibit.rol.form.model.Pantalla;

/**
 * Validator resources con soporte para devolver forms
 * dinamicos dependiendo de las pantallas.
 */
public class DynValidatorResources extends ValidatorResources {

    protected static Log log = LogFactory.getLog(DynValidatorResources.class);

    private ThreadLocal pantalla = new ThreadLocal();

    public Pantalla getPantalla() {
        return (Pantalla) this.pantalla.get();
    }

    public void setPantalla(Pantalla pantalla) {
        this.pantalla.set(pantalla);
    }

    public Form get(Locale locale, Object formKey) {
        return get(
                locale.getLanguage(),
                locale.getCountry(),
                locale.getVariant(),
                formKey);
    }

    public Form get(
            String language,
            String country,
            String variant,
            Object formKey) {
        Form form = super.get(language, country, variant, formKey);
        if (form == null && formKey != null && getPantalla() != null) {
            log.debug("Generating Form for formkey: " + formKey);
            form = new DynForm();
            ((DynForm) form).initialize(getPantalla());
            form.process(hConstants, Collections.EMPTY_MAP);
        }

        return form;
    }
}
