package org.ibit.rol.form.admin.form;

import java.io.Serializable;
import java.util.List;

import org.ibit.rol.form.model.Mascara;

/**
 *
 */
public class ValidacionBean implements Serializable {

    private boolean activo;

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    private Long mascara_id;

    public Long getMascara_id() {
        return mascara_id;
    }

    public void setMascara_id(Long mascara_id) {
        this.mascara_id = mascara_id;
    }

    public String nombre;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    private String valores[];

    public String[] getValores() {
        return valores;
    }

    public void setValores(String[] valores) {
        this.valores = valores;
    }

    public static ValidacionBean fromMascara(Mascara mascara) {
        ValidacionBean vb = new ValidacionBean();
        vb.setMascara_id(mascara.getId());
        vb.setNombre(mascara.getNombre());
        vb.setActivo(false);
        if (mascara.getAllVariables() != null){
            vb.setValores(new String[mascara.getAllVariables().length]);
        }
        return vb;
    }

    public static ValidacionBean[] formMascaras(List mascaras) {
        ValidacionBean[] vbs = new ValidacionBean[mascaras.size()];
        for (int i = 0; i < mascaras.size(); i++) {
            Mascara mascara = (Mascara) mascaras.get(i);
            vbs[i] = fromMascara(mascara);
        }
        return vbs;
    }
}
