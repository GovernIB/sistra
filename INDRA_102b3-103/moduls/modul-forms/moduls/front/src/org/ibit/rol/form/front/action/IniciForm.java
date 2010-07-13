package org.ibit.rol.form.front.action;

import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.ValidatorForm;

/**
 * @struts.form name="iniciForm"
 */
public class IniciForm extends ValidatorForm {

    /*
     * La generación automatica de validation.xml con xdoclet no funciona
     * con commons-validator-1.0 i por tanto està desactivada.
     */


    private String modelo;

    public String getModelo() {
        return modelo;
    }

    /**
     * @struts.validator type="required,mask"
     * @struts.validator-var name="mask" value="^\d{3}$"
     */
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    private int version = 1;

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    private String perfil;

    public String getPerfil() {
        return perfil;
    }

    /**
     * @struts.validator type="required"
     */
    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    private transient FormFile fichero;

    public FormFile getFichero() {
        return fichero;
    }

    public void setFichero(FormFile fichero) {
        this.fichero = fichero;
    }
}
