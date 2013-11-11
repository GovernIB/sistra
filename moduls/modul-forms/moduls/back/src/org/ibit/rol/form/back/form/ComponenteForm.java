package org.ibit.rol.form.back.form;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionMapping;
import org.ibit.rol.form.model.*;
import org.ibit.rol.form.persistence.delegate.DelegateException;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.ibit.rol.form.persistence.delegate.MascaraDelegate;
import org.ibit.rol.form.persistence.delegate.PatronDelegate;

public class ComponenteForm extends TraduccionValidatorForm {

    protected static Log log = LogFactory.getLog(ComponenteForm.class);

    private Long id;
    private Long idPantalla;
    private String tipo;
    private String valorDefecto;
    private Long idPatron;
    private boolean checked;
    private ValidacionBean[] validacion;
    // Si componente pertenece a una pantalla de detalle 
    private boolean pantallaDetalle;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdPantalla() {
        return idPantalla;
    }

    public void setIdPantalla(Long idPantalla) {
        this.idPantalla = idPantalla;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getValorDefecto() {
        valorDefecto = null;
        String lang = getLang();

        // Valor defecto solo esta presente para textbox.
        TextBox textBox = (TextBox) getValues();
        ValorPosible valorPosible = textBox.getValorPosible();

        TraValorPosible traValorPosible = (TraValorPosible) valorPosible.getTraduccion(lang);
        if (traValorPosible != null) {
            valorDefecto = traValorPosible.getEtiqueta();
        }

        return valorDefecto;
    }


    public void setValorDefecto(String valorDefecto) {
        this.valorDefecto = valorDefecto;
        String lang = getLang();
        TextBox textBox = (TextBox) getValues();
        ValorPosible valorPosible = textBox.getValorPosible();
        TraValorPosible traValorPosible = (TraValorPosible) valorPosible.getTraduccion(lang);

        if (valorDefecto != null && valorDefecto.length() > 0) {
            if (traValorPosible == null) {
                traValorPosible = new TraValorPosible();
                valorPosible.setTraduccion(lang, traValorPosible);
            }
            traValorPosible.setEtiqueta(this.valorDefecto);
        } else {
            valorPosible.setTraduccion(lang, null);
        }
    }

    public boolean isChecked() {
        checked = ((CheckBox) getValues()).isValorDefecto();
        return checked;
    }


    public void setChecked(boolean checked) {
        this.checked = checked;
        ((CheckBox) getValues()).setValorDefecto(checked);
    }


    public Long getIdPatron() {
        Campo campo = (Campo) getValues();
        if (campo.getPatron() != null) {
            idPatron = campo.getPatron().getId();
        } else {
            idPatron = new Long(0);
        }
        return idPatron;
    }


    public void setIdPatron(Long idPatron) {
        this.idPatron = idPatron;
        try {
            Campo campo = (Campo) getValues();
            if (idPatron.intValue() == 0) {
                campo.setPatron(null);
            } else {
                PatronDelegate patronDelegate = DelegateUtil.getPatronDelegate();
                campo.setPatron(patronDelegate.obtenerPatron(idPatron));
            }
        } catch (DelegateException e) {
            log.error(e);
        }
    }

    public ValidacionBean[] getValidacion() {
        return validacion;
    }

    public void setValidacion(ValidacionBean[] validacion) {
        this.validacion = validacion;
    }


    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        
        // -- INDRA: LISTA ELEMENTOS
        if (!(getValues() instanceof Label)) {
        	((Campo) getValues()).setMostrarEnTabla(false);
        }
        // -- INDRA:LISTA ELEMENTOS
        
        if (getValues() instanceof CheckBox) {
            setChecked(false);
        }

        if (getValues() instanceof TextBox) {
            cargaValidaciones();
            ((TextBox) getValues()).setMultilinea(false);
            ((TextBox) getValues()).setOculto(false);
        }

        if (getValues() instanceof FileBox) {
            ((FileBox) getValues()).setMultifichero(false);
        }

        if (getValues() instanceof ComboBox) {
            ((ComboBox) getValues()).setObligatorio(false);
        }
    }

    public void setValues(Traducible values) {
        super.setValues(values);
        if (values instanceof TextBox) {
            cargaValidaciones();
            Iterator iterValidaciones = ((Campo) values).getValidaciones().iterator();
            while (iterValidaciones.hasNext()) {
                Validacion val = (Validacion) iterValidaciones.next();

                for (int x = 0; x < validacion.length; x++) {
                    if (validacion[x].getMascara_id().equals(val.getMascara().getId())) {
                        validacion[x].setActivo(true);
                        validacion[x].setValores(val.getValores());
                    }
                }
            }
        }
    }

    public void cargaValidaciones(){
        try {
            MascaraDelegate delegate = DelegateUtil.getMascaraDelegate();
            validacion = ValidacionBean.formMascaras(delegate.listarMascaras());
        } catch (DelegateException e) {
            log.error(e.getMessage());
        }
    }

	public boolean isPantallaDetalle() {
		return pantallaDetalle;
	}

	public void setPantallaDetalle(boolean pantallaDetalle) {
		this.pantallaDetalle = pantallaDetalle;
	}

}
