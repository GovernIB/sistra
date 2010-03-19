package es.caib.redose.model;

import java.io.Serializable;

public class RdsDoc implements Serializable 
{
	private Long id;
	private String nombre;
	private String blob_Documento;
	public String getBlob_Documento() {
		return blob_Documento;
	}
	public void setBlob_Documento(String blob_Documento) {
		this.blob_Documento = blob_Documento;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String toString()
	{
		return "[id : " + id + "; attr1:  " + nombre + "; attr2:  " + blob_Documento + "]";
	}

}
