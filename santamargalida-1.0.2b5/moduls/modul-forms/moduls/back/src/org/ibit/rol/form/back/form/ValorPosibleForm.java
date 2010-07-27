package org.ibit.rol.form.back.form;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionError;
import org.apache.struts.upload.FormFile;
import org.ibit.rol.form.model.ValorPosible;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;

import javax.servlet.http.HttpServletRequest;

import java.util.Iterator;

public class ValorPosibleForm extends TraduccionValidatorForm {

    private Long idComponente;

    public Long getIdComponente() {
        return idComponente;
    }

    public void setIdComponente(Long idComponente) {
        this.idComponente = idComponente;
    }

    private boolean imagen;

    public boolean isImagen() {
        return imagen;
    }

    public void setImagen(boolean imagen) {
        this.imagen = imagen;
    }

    private transient FormFile archivo;

    public FormFile getArchivo() {
        return archivo;
    }

    public void setArchivo(FormFile archivo) {
        this.archivo = archivo;
    }

    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        imagen = false;
        archivo = null;
        ((ValorPosible) getValues()).setDefecto(false);
    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = super.validate(mapping, request);
        if (errors == null) {
            errors = new ActionErrors();
        }

        try {
            ValorPosible valorPosible = ((ValorPosible) getValues());
            if (valorPosible.isDefecto()) {
                Iterator valores = DelegateUtil.getValorPosibleDelegate().listarValoresPosiblesCampo(idComponente).iterator();
                while (valores.hasNext()) {
                    ValorPosible valorAux = (ValorPosible) valores.next();
                    if (valorAux.isDefecto() && !valorAux.getId().equals(valorPosible.getId())) {
                        ActionError errorDefecto = new ActionError("errors.valorposible.defecto");
                        errors.add("values.defecto", errorDefecto);
                        valorPosible.setDefecto(false);
                        break;
                    }
                }
            }

        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return errors;
    }

}
