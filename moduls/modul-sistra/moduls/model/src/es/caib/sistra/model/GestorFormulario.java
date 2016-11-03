package es.caib.sistra.model;


public class GestorFormulario  implements java.io.Serializable {
	
	private String identificador;
	private String descripcion;
	private String urlGestor;
	private String urlTramitacionFormulario;
	private String urlRedireccionFormulario;
	
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getIdentificador() {
		return identificador;
	}
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}
	public String getUrlGestor() {
		return urlGestor;
	}
	public void setUrlGestor(String urlGestor) {
		this.urlGestor = urlGestor;
	}
	public String getUrlRedireccionFormulario() {
		return urlRedireccionFormulario;
	}
	public void setUrlRedireccionFormulario(String urlRedireccionFormulario) {
		this.urlRedireccionFormulario = urlRedireccionFormulario;
	}
	public String getUrlTramitacionFormulario() {
		return urlTramitacionFormulario;
	}
	public void setUrlTramitacionFormulario(String urlTramitacionFormulario) {
		this.urlTramitacionFormulario = urlTramitacionFormulario;
	}
	 	

}
