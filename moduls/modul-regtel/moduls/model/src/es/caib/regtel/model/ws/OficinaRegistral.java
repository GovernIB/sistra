
package es.caib.regtel.model.ws;

public class OficinaRegistral {

	private String entidad;
    private String codigoOficina;
    private String codigoOrgano;

    public String getCodigoOficina() {
        return codigoOficina;
    }

    public void setCodigoOficina(String value) {
        this.codigoOficina = value;
    }

    public String getCodigoOrgano() {
        return codigoOrgano;
    }

    public void setCodigoOrgano(String value) {
        this.codigoOrgano = value;
    }

	public String getEntidad() {
		return entidad;
	}

	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}

}
