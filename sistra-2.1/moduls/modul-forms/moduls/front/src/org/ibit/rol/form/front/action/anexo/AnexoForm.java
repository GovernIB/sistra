package org.ibit.rol.form.front.action.anexo;

import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.ValidatorForm;

/**
 * @struts.form name="anexoForm"
 */
public class AnexoForm extends ValidatorForm {

    private String nombre;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    private transient FormFile fichero;

    public FormFile getFichero() {
        return fichero;
    }

    public void setFichero(FormFile fichero) {
        this.fichero = fichero;
    }
}
