package es.caib.bantel.front.json;



public class UnidadAdministrativa implements java.io.Serializable {

	private String codigo;	
	private String descripcion;	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}	
}
