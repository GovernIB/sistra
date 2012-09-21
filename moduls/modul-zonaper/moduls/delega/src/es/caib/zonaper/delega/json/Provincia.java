package es.caib.zonaper.delega.json;

import java.util.HashSet;
import java.util.Set;


public class Provincia implements java.io.Serializable {

	private String codigo;	
	private String descripcion;	
	
	private Set localidades = new HashSet(0);
	
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
	public Set getLocalidades() {
		return localidades;
	}
	public void setLocalidades(Set localidades) {
		this.localidades = localidades;
	}	
}
