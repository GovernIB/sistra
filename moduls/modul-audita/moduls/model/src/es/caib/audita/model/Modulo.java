package es.caib.audita.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Modulo implements Serializable
{
	private String modulo;
	private String descripcion;
	private int orden;
	private List eventosAuditados = new ArrayList();
		
	public String getDescripcion()
	{
		return descripcion;
	}
	public void setDescripcion(String descripcion)
	{
		this.descripcion = descripcion;
	}
	public void addEventoAuditado( EventoAuditado evento )
	{
		eventosAuditados.add( evento );
	}
	public List getEventosAuditados()
	{
		return eventosAuditados;
	}
	public void setEventosAuditados(List eventosAuditados)
	{
		this.eventosAuditados = eventosAuditados;
	}
	public String getModulo()
	{
		return modulo;
	}
	public void setModulo(String modulo)
	{
		this.modulo = modulo;
	}
	public int getOrden()
	{
		return orden;
	}
	public void setOrden(int orden)
	{
		this.orden = orden;
	}
}