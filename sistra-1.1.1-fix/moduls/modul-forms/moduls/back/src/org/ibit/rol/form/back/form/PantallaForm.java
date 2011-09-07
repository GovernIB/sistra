package org.ibit.rol.form.back.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.ibit.rol.form.model.Pantalla;
import org.ibit.rol.form.persistence.delegate.DelegateException;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;

public class PantallaForm extends TraduccionValidatorForm {

    private Long idFormulario = null;
    private List perfiles = null;

    public Long getIdFormulario() {
        return this.idFormulario;
    }

    public void setIdFormulario(Long idFormulario) {
        this.idFormulario = idFormulario;
    }

    public List getPerfiles() {
        return perfiles;
    }

    public void setPerfiles(List perfiles) {
        this.perfiles = perfiles;
    }

    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        if (mapping.getPath().endsWith("alta") || mapping.getPath().endsWith("editar")) {
            Pantalla pantalla = (Pantalla) getValues();
            pantalla.setInicial(false);
            pantalla.setUltima(false);
        }
    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = super.validate(mapping, request);
        if (errors == null) {
            errors = new ActionErrors();
        }

        try {
            Pantalla pantalla = (Pantalla) getValues();
            List pantallas = DelegateUtil.getPantallaDelegate().listarPantallasFormulario(idFormulario);

            // Comprovar que el nombre de pantalla no esta repetido.
            String nombre = pantalla.getNombre();
            for (int i = 0; i < pantallas.size(); i++) {
                Pantalla p = (Pantalla) pantallas.get(i);
                if (p.getNombre().equals(nombre) && !p.getId().equals(pantalla.getId())) {
                    errors.add("values.nombre", new ActionError("errors.pantalla.duplicado", nombre));
                    break;
                }
            }

            // Si se ha marcado la pantalla como inicial, comprobar que no hay otras iniciales.
            if (pantalla.isInicial()) {
                for (int i = 0; i < pantallas.size(); i++) {
                    Pantalla p = (Pantalla) pantallas.get(i);
                    if (p.isInicial() && !p.getId().equals(pantalla.getId())) {
                        errors.add("values.inicial", new ActionError("errors.pantalla.inicial"));
                        pantalla.setInicial(false);
                        break;
                    }
                }
            }

        } catch (DelegateException e) {
            log.error(e);
        }

        return errors;
    }

}
