package org.ibit.rol.form.admin.form;

import org.apache.struts.validator.ValidatorForm;
import org.apache.struts.action.ActionMapping;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ibit.rol.form.model.Mascara;

import javax.servlet.http.HttpServletRequest;

public class MascaraForm extends ValidatorForm {
    protected static Log log = LogFactory.getLog(MascaraForm.class);


    private Mascara values = new Mascara();
    private String[] variables = null;
    private boolean init = false;

    public MascaraForm(){
        variables = new String[0];
    }

    public Mascara getValues(){
        return this.values;
    }

    public void setValues(Mascara values){
        this.values = values;
    }

    public void numVariables(int x){
        String[] aux = variables;

        if (x >= 0){
            variables = new String[x];

            for (int index=0; index<variables.length; index++){
                variables[index] = "";
            }

            int min = Math.min(variables.length, aux.length);
            for (int index=0; index < min; index++){
                variables[index] = aux[index];
            }

        }
    }

    public String[] getVariables() {
        if (!init){
            variables = getValues().getAllVariables();
            init = true;
        }
        return variables;
    }

    public void setVariables(String[] variables) {
        this.variables = variables;
        getValues().setVariables(variables);
    }


    public void reset(ActionMapping mapping, HttpServletRequest request) {
        values = new Mascara();
        init = false;

        if (mapping.getPath().endsWith("alta")) {
            variables = new String[0];
        }
    }
}
