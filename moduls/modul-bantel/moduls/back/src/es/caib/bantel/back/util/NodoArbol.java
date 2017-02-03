package es.caib.bantel.back.util;

import java.io.Serializable;

public class NodoArbol implements Serializable
{
	private String idCampo;
	private String idCampoPadre;
	private String id;
	private String descripcion;
	private String parentId;
	private boolean seleccionable = true;
	
	public NodoArbol()
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


	public String getIdCampo() {
		return idCampo;
	}


	public void setIdCampo(String idCampo) {
		this.idCampo = idCampo;
	}


	public boolean isSeleccionable() {
		return seleccionable;
	}


	public void setSeleccionable(boolean seleccionable) {
		this.seleccionable = seleccionable;
	}


	public String getIdCampoPadre() {
		return idCampoPadre;
	}


	public void setIdCampoPadre(String idCampoPadre) {
		this.idCampoPadre = idCampoPadre;
	}
	
}
