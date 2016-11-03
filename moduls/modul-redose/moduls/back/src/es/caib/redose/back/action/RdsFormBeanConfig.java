package es.caib.redose.back.action;

import org.apache.struts.action.ActionFormBean;

/**
 * Extensión de la clase de configuración de forms de Struts para
 * permitir indicar propiedades adicionales para formularios de
 * entidades traducidas.
 */
public class RdsFormBeanConfig extends ActionFormBean {

    protected String valuesClassName;

    public String getValuesClassName() {
        return valuesClassName;
    }

    public void setValuesClassName(String valuesClassName) {
        if (configured) {
            throw new IllegalStateException("Configuration is frozen");
        }
        this.valuesClassName = valuesClassName;
    }
    
}
