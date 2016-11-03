
package es.caib.regtel.model.ws;

public class DatosExpediente {

    private long unidadAdministrativa;
    private String identificadorExpediente;
    private String claveExpediente;
  
    public long getUnidadAdministrativa() {
        return unidadAdministrativa;
    }

    public void setUnidadAdministrativa(long value) {
        this.unidadAdministrativa = value;
    }

    public String getIdentificadorExpediente() {
        return identificadorExpediente;
    }

    public void setIdentificadorExpediente(String value) {
        this.identificadorExpediente = value;
    }

    public String getClaveExpediente() {
        return claveExpediente;
    }

    public void setClaveExpediente(String value) {
        this.claveExpediente = value;
    }

}
