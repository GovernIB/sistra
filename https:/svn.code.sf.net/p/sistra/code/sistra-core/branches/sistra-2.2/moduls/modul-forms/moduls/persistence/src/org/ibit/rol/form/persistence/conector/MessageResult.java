package org.ibit.rol.form.persistence.conector;

/**
 * Resultado de la ejecución de un conector representando un mensaje parametrizado.
 */
public class MessageResult implements Result {

    public String message;
    public Object[] parameters;

    public MessageResult(String message) {
        this.message = message;
        this.parameters = new Object[]{};
    }

    public MessageResult(String message, Object parameter) {
        this.message = message;
        this.parameters = new Object[]{parameter};
    }

    public MessageResult(String message, Object parameter1, Object parameter2) {
        this.message = message;
        this.parameters = new Object[]{parameter1, parameter2};
    }

    public MessageResult(String message, Object[] parameters) {
        this.message = message;
        this.parameters = parameters;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }

}
