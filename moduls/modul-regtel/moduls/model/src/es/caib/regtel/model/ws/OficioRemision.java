
package es.caib.regtel.model.ws;

public class OficioRemision {

    private String titulo;
    private String texto;
    private TramiteSubsanacion tramiteSubsanacion;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String value) {
        this.titulo = value;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String value) {
        this.texto = value;
    }

	public TramiteSubsanacion getTramiteSubsanacion() {
		return tramiteSubsanacion;
	}

	public void setTramiteSubsanacion(TramiteSubsanacion tramiteSubsanacion) {
		this.tramiteSubsanacion = tramiteSubsanacion;
	}

}
