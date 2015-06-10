
package es.caib.regtel.model.ws;

public class DatosRepresentado {

    private String nif;
    private String nombreApellidos;
    private IdentificacionInteresadoDesglosada identificacionUsuarioDesglosada;

    public String getNif() {
        return nif;
    }

    public void setNif(String value) {
        this.nif = value;
    }

    public String getNombreApellidos() {
        return nombreApellidos;
    }

    public void setNombreApellidos(String value) {
        this.nombreApellidos = value;
    }

	public IdentificacionInteresadoDesglosada getIdentificacionUsuarioDesglosada() {
		return identificacionUsuarioDesglosada;
	}

	public void setIdentificacionUsuarioDesglosada(
			IdentificacionInteresadoDesglosada identificacionUsuarioDesglosada) {
		this.identificacionUsuarioDesglosada = identificacionUsuarioDesglosada;
	}

}
