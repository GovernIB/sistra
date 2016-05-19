package es.caib.bantel.back.action.tramite;

import java.io.Serializable;

public class NodoProcs implements Serializable
{
	
	
	private boolean folder;
	private String id;
	private String parentId;
	
	private String identificador;
	private String descripcion;
	
	
	public NodoProcs()
	{
		super();
	}
	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getParentId() {
		return parentId;
	}


	public void setParentId(String parentId) {
		this.parentId = parentId;
	}


	public boolean isFolder() {
		return folder;
	}


	public void setFolder(boolean folder) {
		this.folder = folder;
	}


	public String getIdentificador() {
		return identificador;
	}


	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
	

	
}
