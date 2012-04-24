package es.caib.sistra.model.admin;

import java.io.Serializable;

public class ElementoAuditoriaScript implements Serializable
{
	public static String ESTADO_NUEVO = "N";
	public static String ESTADO_MODIFICADO = "M"; 
	
	private String descripcionKey;
	private String estado;
	private String nombre;
	
	private String contenidoScript;
	
	
	public String getEstado()
	{
		return estado;
	}
	public void setEstado(String estado)
	{
		this.estado = estado;
	}
	public String getContenidoScript()
	{
		return contenidoScript;
	}
	public void setContenidoScript(String contenidoScript)
	{
		this.contenidoScript = contenidoScript;
	}
	public String getDescripcionKey()
	{
		return descripcionKey;
	}
	public void setDescripcionKey(String descripcionKey)
	{
		this.descripcionKey = descripcionKey;
	}
	public String getNombre()
	{
		return nombre;
	}
	public void setNombre(String nombre)
	{
		this.nombre = nombre;
	}
}
