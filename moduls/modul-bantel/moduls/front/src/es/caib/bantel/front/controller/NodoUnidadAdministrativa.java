package es.caib.bantel.front.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class NodoUnidadAdministrativa implements Serializable
{
	
	
	private boolean folder;
	private String id;
	private String descripcion;
	private String parentId;
	private String action;	
	private Map parameters = new HashMap();	
	private String descripcionLink;
	private String tipo="INDEFINIDO";
	
	
	public NodoUnidadAdministrativa()
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
	public String getAction()
	{
		return action;
	}
	public void setAction(String action)
	{
		this.action = action;
	}

	public String getDescripcionLink()
	{
		return descripcionLink;
	}

	public void setDescripcionLink(String descripcionLink)
	{
		this.descripcionLink = descripcionLink;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Map getParameters() {
		return parameters;
	}

	public void setParameters(Map parameters) {
		this.parameters = parameters;
	}	
}
