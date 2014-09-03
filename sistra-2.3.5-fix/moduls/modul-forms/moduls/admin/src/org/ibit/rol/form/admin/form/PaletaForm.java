package org.ibit.rol.form.admin.form;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;
import org.ibit.rol.form.model.Paleta;

import javax.servlet.http.HttpServletRequest;

public class PaletaForm extends ValidatorForm {

    private Paleta values = null;

    public PaletaForm(){
        values = new Paleta();
    }

    public Paleta getValues(){
        return this.values;
    }

    public void setValues(Paleta values){
        this.values = values;
    }

    public void reset(ActionMapping mapping, HttpServletRequest request) {
        values = new Paleta();
    }

}
