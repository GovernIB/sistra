package es.caib.mobtratel.back.action;

import org.apache.struts.action.ActionFormBean;

/**
 * Extensión de la clase de configuración de forms de Struts para
 * permitir indicar propiedades adicionales para formularios de
 * entidades traducidas.
 */
public class TraduccionFormBeanConfig extends ActionFormBean {

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

    protected String traduccionClassName;

    public String getTraduccionClassName() {
        return traduccionClassName;
    }

    public void setTraduccionClassName(String traduccionClassName) {
        if (configured) {
            throw new IllegalStateException("Configuration is frozen");
        }
        this.traduccionClassName = traduccionClassName;
    }

}
