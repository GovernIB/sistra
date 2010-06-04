package org.ibit.rol.form.admin.form;

import org.apache.struts.validator.ValidatorForm;
import org.apache.struts.action.ActionMapping;
import org.ibit.rol.form.model.Patron;

import javax.servlet.http.HttpServletRequest;

public class PatronForm extends ValidatorForm{

    private Patron values = null;

    public PatronForm(){
        values = new Patron();
    }

    public Patron getValues(){
        return this.values;
    }

    public void setValues(Patron values){
        this.values = values;
    }

    public void reset(ActionMapping mapping, HttpServletRequest request) {
        values = new Patron();
    }

}
