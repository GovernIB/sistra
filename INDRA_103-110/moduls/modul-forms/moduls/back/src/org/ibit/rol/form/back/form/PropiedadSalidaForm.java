package org.ibit.rol.form.back.form;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;
import org.apache.struts.upload.FormFile;
import org.ibit.rol.form.model.PropiedadSalida;

import javax.servlet.http.HttpServletRequest;

/**
 * Formulario de PropiedadSalida
 */
public class PropiedadSalidaForm extends ValidatorForm {

    protected static Log log = LogFactory.getLog(PropiedadSalidaForm.class);
    private transient FormFile plantilla;



    public PropiedadSalidaForm() {
        log.debug("Instancia Creada");
    }

    private Long idSalida = null;

    public Long getIdSalida() {
        return idSalida;
    }

    public void setIdSalida(Long idSalida) {
        this.idSalida = idSalida;
    }

    private PropiedadSalida values;

    public PropiedadSalida getValues() {
        if(values == null){
            values = new PropiedadSalida();
        }
        return values;
    }

    public void setValues(PropiedadSalida values) {
        this.values = values;
    }

    public FormFile getPlantilla() {
        return plantilla;
    }

    public void setPlantilla(FormFile plantilla) {
        this.plantilla = plantilla;
    }


    public void reset(ActionMapping mapping, HttpServletRequest request) {
        log.debug("llamada a reset");
        if (mapping.getPath().endsWith("alta") || mapping.getPath().endsWith("editar")) {
            PropiedadSalida propiedad = (PropiedadSalida) getValues();
        }
    }

     public void destroy(ActionMapping mapping, HttpServletRequest request) {
        log.debug("llamada a destroy " + mapping.getName());
        setValues(null);
    }
}
