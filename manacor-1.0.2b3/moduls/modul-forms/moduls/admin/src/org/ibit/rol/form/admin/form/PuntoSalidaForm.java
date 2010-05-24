package org.ibit.rol.form.admin.form;

import org.apache.struts.validator.ValidatorForm;
import org.apache.struts.action.ActionMapping;
import org.ibit.rol.form.model.PuntoSalida;

import javax.servlet.http.HttpServletRequest;

public class PuntoSalidaForm extends ValidatorForm {
    private PuntoSalida values = null;

    public PuntoSalidaForm(){
        values = new PuntoSalida();
    }

    public PuntoSalida getValues(){
        return this.values;
    }

    public void setValues(PuntoSalida values){
        this.values = values;
    }

    public void reset(ActionMapping mapping, HttpServletRequest request) {
        values = new PuntoSalida();
    }
}
