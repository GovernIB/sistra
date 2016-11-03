
package es.caib.regtel.model.ws;

public class DatosInteresado {

    private Boolean autenticado; // A partir v1.1.0 sustituye en la llamada a identificador usuario
    private String identificadorUsuario;
    private IdentificacionInteresadoDesglosada identificacionUsuarioDesglosada;
    private String nif;
    private String nombreApellidos;
    private String codigoPais;
    private String nombrePais;
    private String codigoProvincia;
    private String nombreProvincia;
    private String codigoLocalidad;
    private String nombreLocalidad;

    public String getIdentificadorUsuario() {
        return identificadorUsuario;
    }

    public void setIdentificadorUsuario(String value) {
        this.identificadorUsuario = value;
    }

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

    public String getCodigoPais() {
        return codigoPais;
    }

    public void setCodigoPais(String value) {
        this.codigoPais = value;
    }

    public String getNombrePais() {
        return nombrePais;
    }

    public void setNombrePais(String value) {
        this.nombrePais = value;
    }

    public String getCodigoProvincia() {
        return codigoProvincia;
    }

    public void setCodigoProvincia(String value) {
        this.codigoProvincia = value;
    }

    public String getNombreProvincia() {
        return nombreProvincia;
    }

    public void setNombreProvincia(String value) {
        this.nombreProvincia = value;
    }

    public String getCodigoLocalidad() {
        return codigoLocalidad;
    }

    public void setCodigoLocalidad(String value) {
        this.codigoLocalidad = value;
    }

    public String getNombreLocalidad() {
        return nombreLocalidad;
    }

    public void setNombreLocalidad(String value) {
        this.nombreLocalidad = value;
    }

	public Boolean getAutenticado() {
		return autenticado;
	}

	public void setAutenticado(Boolean autenticado) {
		this.autenticado = autenticado;
	}

	public IdentificacionInteresadoDesglosada getIdentificacionUsuarioDesglosada() {
		return identificacionUsuarioDesglosada;
	}

	public void setIdentificacionUsuarioDesglosada(
			IdentificacionInteresadoDesglosada identificadorUsuarioDesglosada) {
		this.identificacionUsuarioDesglosada = identificadorUsuarioDesglosada;
	}

}
