package org.ibit.rol.form.back.form;

import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

public class AyudaForm extends TraduccionValidatorForm{

    private Long idPantalla;
    private Long idPerfil;


    public Long getIdPantalla() {
        return idPantalla;
    }


    public void setIdPantalla(Long idPantalla) {
        this.idPantalla = idPantalla;
    }


    public Long getIdPerfil() {
        return idPerfil;
    }


    public void setIdPerfil(Long idPerfil) {
        this.idPerfil = idPerfil;
    }


    public void reset(ActionMapping mapping, HttpServletRequest request){
        super.reset(mapping, request);
    }

}
