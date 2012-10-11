package es.caib.zonaper.model;

import java.io.Serializable;

public class DocumentoEventoExpediente implements Serializable
{
	private Long codigo;
	private EventoExpediente eventoExpediente;
    private Long rdsCodigo;
    private String rdsClave;
    private String titulo;
    private Integer orden;
	
	public Long getCodigo()
	{
		return codigo;
	}
	public void setCodigo(Long codigo)
	{
		this.codigo = codigo;
	}
	public EventoExpediente getEventoExpediente()
	{
		return eventoExpediente;
	}
	public void setEventoExpediente(EventoExpediente eventoExpediente)
	{
		this.eventoExpediente = eventoExpediente;
	}
	public String getRdsClave()
	{
		return rdsClave;
	}
	public void setRdsClave(String rdsClave)
	{
		this.rdsClave = rdsClave;
	}
	public Long getRdsCodigo()
	{
		return rdsCodigo;
	}
	public void setRdsCodigo(Long rdsCodigo)
	{
		this.rdsCodigo = rdsCodigo;
	}
	public String getTitulo()
	{
		return titulo;
	}
	public void setTitulo(String titulo)
	{
		this.titulo = titulo;
	}
	public Integer getOrden() {
		return orden;
	}
	public void setOrden(Integer orden) {
		this.orden = orden;
	}
}
