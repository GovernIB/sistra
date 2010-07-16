
package es.caib.regtel.model.ws;

public class DatosAsunto {

    private String idioma;
    private String asunto;
    private String tipoAsunto;
    private long codigoUnidadAdministrativa;

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String value) {
        this.idioma = value;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String value) {
        this.asunto = value;
    }

    public String getTipoAsunto() {
        return tipoAsunto;
    }

    public void setTipoAsunto(String value) {
        this.tipoAsunto = value;
    }

    public long getCodigoUnidadAdministrativa() {
        return codigoUnidadAdministrativa;
    }

    public void setCodigoUnidadAdministrativa(long value) {
        this.codigoUnidadAdministrativa = value;
    }

}
