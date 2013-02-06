package org.ibit.rol.form.admin.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

/**
 *
 */
public class EleccionComponenteForm extends ValidatorForm {

    private String tipo;
    private Long idPaleta;

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Long getIdPaleta() {
        return idPaleta;
    }

    public void setIdPaleta(Long idPaleta) {
        this.idPaleta = idPaleta;
    }

    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        tipo = null;
        idPaleta = null;
    }
}
