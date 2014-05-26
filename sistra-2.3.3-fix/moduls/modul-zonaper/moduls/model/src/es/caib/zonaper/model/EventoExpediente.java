package es.caib.zonaper.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

public class EventoExpediente implements Serializable,ElementoExpedienteItf
{
	private Long codigo;
	private Timestamp fecha;
	private Timestamp fechaConsulta;
	private String titulo;
	private String texto;
	private String textoSMS;	
	private String enlaceConsulta;
	private Set documentos = new HashSet( 0 );
	private String usuarioSeycon;
	private String identificadorPersistencia;
	private boolean accesiblePorClave;
	
	public Long getCodigo()
	{
		return codigo;
	}
	public void setCodigo(Long codigo)
	{
		this.codigo = codigo;
	}
	public Set getDocumentos()
	{
		return documentos;
	}
	public void setDocumentos(Set documentos)
	{
		this.documentos = documentos;
	}
	public String getEnlaceConsulta()
	{
		return enlaceConsulta;
	}
	public void setEnlaceConsulta(String enlaceConsulta)
	{
		this.enlaceConsulta = enlaceConsulta;
	}	
	public String getTexto()
	{
		return texto;
	}
	public void setTexto(String texto)
	{
		this.texto = texto;
	}
	public void addDocumento( DocumentoEventoExpediente doc )
	{
		doc.setEventoExpediente( this );
		documentos.add( doc );
	}
	public void removeDocumento( DocumentoEventoExpediente doc )
	{
		documentos.remove( doc );
	}
	public Timestamp getFecha()
	{
		return fecha;
	}
	public void setFecha(Timestamp fecha)
	{
		this.fecha = fecha;
	}
	public String getUsuarioSeycon()
	{
		return usuarioSeycon;
	}
	public void setUsuarioSeycon(String usuarioSeycon)
	{
		this.usuarioSeycon = usuarioSeycon;
	}
	public Timestamp getFechaConsulta()
	{
		return fechaConsulta;
	}
	public void setFechaConsulta(Timestamp fechaConsulta)
	{
		this.fechaConsulta = fechaConsulta;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getTextoSMS() {
		return textoSMS;
	}
	public void setTextoSMS(String textoSMS) {
		this.textoSMS = textoSMS;
	}
	public String getIdentificadorPersistencia() {
		return identificadorPersistencia;
	}
	public void setIdentificadorPersistencia(String identificadorPersistencia) {
		this.identificadorPersistencia = identificadorPersistencia;
	}
	public boolean isAccesiblePorClave() {
		return accesiblePorClave;
	}
	public void setAccesiblePorClave(boolean accesiblePorClave) {
		this.accesiblePorClave = accesiblePorClave;
	}
	
}
