package es.caib.audita.model;

import java.io.Serializable;

public class TipoEvento implements Serializable
{
	private String tipo;
	private String modulo;
	private String auditar; //S/N
	private String descripcion;
	private int orden;
	private String propiedadesVisualizacion;
	private String handler;
	private String ayuda;
	
	
	public String getAuditar()
	{
		return auditar;
	}
	public void setAuditar(String auditar)
	{
		this.auditar = auditar;
	}
	public String getDescripcion()
	{
		return descripcion;
	}
	public void setDescripcion(String descripcion)
	{
		this.descripcion = descripcion;
	}
	public String getTipo()
	{
		return tipo;
	}
	public void setTipo(String tipo)
	{
		this.tipo = tipo;
	}
	public String getModulo()
	{
		return modulo;
	}
	public void setModulo(String modulo)
	{
		this.modulo = modulo;
	}
	public String getAyuda()
	{
		return ayuda;
	}
	public void setAyuda(String ayuda)
	{
		this.ayuda = ayuda;
	}
	public String getHandler()
	{
		return handler;
	}
	public void setHandler(String handler)
	{
		this.handler = handler;
	}
	public int getOrden()
	{
		return orden;
	}
	public void setOrden(int orden)
	{
		this.orden = orden;
	}
	public String getPropiedadesVisualizacion()
	{
		return propiedadesVisualizacion;
	}
	public void setPropiedadesVisualizacion(String propiedadesVisualizacion)
	{
		this.propiedadesVisualizacion = propiedadesVisualizacion;
	}
}
