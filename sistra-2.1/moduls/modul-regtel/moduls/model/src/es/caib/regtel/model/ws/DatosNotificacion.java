
package es.caib.regtel.model.ws;

public class DatosNotificacion {

    private String idioma;
    private String tipoAsunto;
    private boolean acuseRecibo;
    private Boolean accesiblePorClave;
    private boolean firmaPorClave;
    private Aviso aviso;
    private OficioRemision oficioRemision;

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String value) {
        this.idioma = value;
    }

    public String getTipoAsunto() {
        return tipoAsunto;
    }

    public void setTipoAsunto(String value) {
        this.tipoAsunto = value;
    }

    public boolean isAcuseRecibo() {
        return acuseRecibo;
    }

    public void setAcuseRecibo(boolean value) {
        this.acuseRecibo = value;
    }

    public Aviso getAviso() {
        return aviso;
    }

    public void setAviso(Aviso value) {
        this.aviso = value;
    }

    public OficioRemision getOficioRemision() {
        return oficioRemision;
    }

    public void setOficioRemision(OficioRemision value) {
        this.oficioRemision = value;
    }

	public Boolean getAccesiblePorClave() {
		return accesiblePorClave;
	}

	public void setAccesiblePorClave(Boolean accesiblePorClave) {
		this.accesiblePorClave = accesiblePorClave;
	}

	public boolean isFirmaPorClave() {
		return firmaPorClave;
	}

	public void setFirmaPorClave(boolean firmaPorClave) {
		this.firmaPorClave = firmaPorClave;
	}

}
