package es.caib.sistra.model.admin;

import java.io.Serializable;

import es.caib.sistra.model.Dominio;

public class ElementoAuditoriaDominio implements Serializable
{
	private String nombre;
	private Dominio dominio;
	private String estado;
	public static String ESTADO_NUEVO = "N";
	public static String ESTADO_NUEVO_EN_CUADERNO = "C";
	public static String ESTADO_EXISTENTE = "E";
	public static String ESTADO_EXISTENTE_MODIFICADO_EN_CUADERNO = "M";
	
	public String getNombre()
	{
		return nombre;
	}
	public void setNombre(String nombre)
	{
		this.nombre = nombre;
	}
	public String getEstado()
	{
		return estado;
	}
	public void setEstado(String estado)
	{
		this.estado = estado;
	}
	public Dominio getDominio()
	{
		return dominio;
	}
	public void setDominio(Dominio dominio)
	{
		this.dominio = dominio;
	}
	public boolean equals( Object dominio )
	{
		if ( dominio instanceof ElementoAuditoriaDominio && ( nombre.equals( ( ( ElementoAuditoriaDominio ) dominio ).getNombre() ) ) )
		{
			return true;
		}
		return false;
	}
	public int hashCode()
	{
		return nombre.hashCode();
	}
}
