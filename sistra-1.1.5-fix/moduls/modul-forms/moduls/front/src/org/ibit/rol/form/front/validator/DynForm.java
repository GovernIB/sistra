package org.ibit.rol.form.front.validator;

import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.Arg;
import org.apache.commons.validator.Field;
import org.apache.commons.validator.Form;
import org.ibit.rol.form.model.Campo;
import org.ibit.rol.form.model.Mascara;
import org.ibit.rol.form.model.Pantalla;
import org.ibit.rol.form.model.TraCampo;
import org.ibit.rol.form.model.Validacion;

/**
 * Form de validación que se construye a partir de una pantalla.
 */
public class DynForm extends Form {

    protected static Log log = LogFactory.getLog(DynForm.class);

    public void initialize(Pantalla pantalla) {
        hFields.setFast(false);
        hFields.clear();
        lFields.clear();
        setName("pantallaForm");
        for (int i = 0; i < pantalla.getCampos().size(); i++) {
            Campo campo = (Campo) pantalla.getCampos().get(i);
           
            Field field = new Field();
            field.setProperty(campo.getNombreLogico());

            Arg arg0 = new Arg();
            arg0.setKey(((TraCampo) campo.getTraduccion()).getNombre());
            arg0.setResource(false);
            field.addArg0(arg0);

            String depends = "";
            for (Iterator iterator = campo.getValidaciones().iterator(); iterator.hasNext();) {
                Validacion validacion = (Validacion) iterator.next();
                Mascara mascara = validacion.getMascara();

                if (depends.length() > 0) {
                    depends += ",";
                }
                depends += mascara.getNombre();
                
                
                if (mascara.getAllVariables() != null) {
                    // Aquest codi per ficar arguments està limitat
                    // per la implementacio de Field i els metodes addArgX.
                    for (int j = 0; j < mascara.getAllVariables().length && j < 3; j++) {
                        String varName = mascara.getAllVariables()[j];
                        
                        /*INDRA: BUG AL IMPORTAR FORMULARIO: CADENAS VACIAS SE INSERTAN COMO NULOS */
                        String varValue;
                        if ( j < validacion.getValores().length )
                        	varValue = validacion.getValores()[j];
                        else
                        	varValue = "";
                        varValue = (varValue != null?varValue:"");
                        /*INDRA: BUG AL IMPORTAR FORMULARIO: CADENAS VACIAS SE INSERTAN COMO NULOS */
                        
                        field.addVarParam(varName, varValue, null);

                        Arg arg = new Arg();
                        arg.setName(mascara.getNombre());
                        arg.setKey("${var:" + varName + "}");
                        arg.setResource(false);
                        switch (j) {
                            case 0:
                                field.addArg1(arg);
                                break;
                            case 1:
                                field.addArg2(arg);
                                break;
                            case 2:
                                field.addArg3(arg);
                                break;
                        }
                    }
                }
            }
            field.setDepends(depends);
            addField(field);
        }
    }

}
