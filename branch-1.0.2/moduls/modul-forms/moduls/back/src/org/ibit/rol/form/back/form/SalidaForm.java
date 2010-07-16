package org.ibit.rol.form.back.form;

import org.apache.struts.validator.ValidatorForm;

/**
 * Formulario de Salida
 */
public class SalidaForm extends ValidatorForm {
    private Long idFormulario;

    public Long getIdFormulario() {
        return idFormulario;
    }

    public void setIdFormulario(Long idFormulario) {
        this.idFormulario = idFormulario;
    }

    private Long idPuntoSalida;

    public Long getIdPuntoSalida() {
        return idPuntoSalida;
    }

    public void setIdPuntoSalida(Long idPuntoSalida) {
        this.idPuntoSalida = idPuntoSalida;
    }
}
