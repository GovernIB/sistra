package org.ibit.rol.form.admin.form;

import org.apache.struts.validator.ValidatorForm;
import org.apache.struts.action.ActionMapping;
import org.ibit.rol.form.model.ValidadorFirma;

import javax.servlet.http.HttpServletRequest;


public class ValidadorFirmaForm extends ValidatorForm {
    private ValidadorFirma values = null;

    public ValidadorFirmaForm(){
        values = new ValidadorFirma();
    }

    public ValidadorFirma getValues(){
        return this.values;
    }

    public void setValues(ValidadorFirma values){
        this.values = values;
    }

    public void reset(ActionMapping mapping, HttpServletRequest request) {
        values = new ValidadorFirma();
    }
}
