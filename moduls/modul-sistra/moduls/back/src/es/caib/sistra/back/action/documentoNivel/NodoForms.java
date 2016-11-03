package es.caib.sistra.back.action.documentoNivel;

import java.io.Serializable;

public class NodoForms implements Serializable
{
	
	
	private boolean folder;
	private String id;
	private String descripcion;
	private String parentId;
	
	private String modelo;
	private String version;
	
	
	public NodoForms()
	{
		super();
	}
	
	
	public String getDescripcion()
	{
		return descripcion;
	}
	public void setDescripcion(String descripcion)
	{
		this.descripcion = descripcion;
	}
	public boolean isFolder()
	{
		return folder;
	}
	public void setFolder(boolean folder)
	{
		this.folder = folder;
	}
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getParentId()
	{
		return parentId;
	}
	public void setParentId(String parentId)
	{
		this.parentId = parentId;
	}


	public String getVersion() {
		return version;
	}


	public void setVersion(String version) {
		this.version = version;
	}


	public String getModelo() {
		return modelo;
	}


	public void setModelo(String modelo) {
		this.modelo = modelo;
	}


	
}
