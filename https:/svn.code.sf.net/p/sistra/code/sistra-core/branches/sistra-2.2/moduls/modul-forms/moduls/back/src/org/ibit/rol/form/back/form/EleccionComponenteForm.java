package org.ibit.rol.form.back.form;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

import javax.servlet.http.HttpServletRequest;

/**
 * Formulario para elegir el tipo de componente a crear y a que
 * pantalla pertenecerà.
 */
public class EleccionComponenteForm extends ValidatorForm {
    private String tipo;
    private Long idPantalla;

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Long getIdPantalla() {
        return idPantalla;
    }

    public void setIdPantalla(Long idPantalla) {
        this.idPantalla = idPantalla;
    }

    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        tipo = null;
        idPantalla = null;
    }
}
