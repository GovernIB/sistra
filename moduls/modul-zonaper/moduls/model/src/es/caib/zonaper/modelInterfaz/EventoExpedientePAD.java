package es.caib.zonaper.modelInterfaz;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * Clase que representa un evento en un expediente de la zona personal
 * </p>
 * 
 * <p>
 * Hay que tener en cuenta que al crear un expediente se indica el idioma de tramitación del mismo, por lo que los textos asociados deben ir en ese 
 * idioma.
 * </p>
 * 
 *
 */
public class EventoExpedientePAD extends ElementoExpedientePAD
{
	private String titulo;
	private String texto;
	private String textoSMS;
	private String enlaceConsulta;
	private Date fechaConsulta;
	private Boolean accesiblePorClave;
	private List documentos = new ArrayList();	
		
	/**
	 * Devuelve Enlace de consulta del evento. Opcionalmente se puede establecer un link a una url externa relacionada con el evento. 
	 * 
	 * @return String
	 */
	public String getEnlaceConsulta()
	{
		return enlaceConsulta;
	}
	/**
	 * Establece un enlace de consulta para el evento. Opcionalmente se puede establecer un link a una url externa relacionada con el evento.  
	 * @param enlaceConsulta String
	 */
	public void setEnlaceConsulta(String enlaceConsulta)
	{
		this.enlaceConsulta = enlaceConsulta;
	}
	/**
	 * Devuelve el texto del evento
	 * @return
	 */
	public String getTexto()
	{
		return texto;
	}
	/**
	 * Establece el texto del evento
	 * @param texto
	 */
	public void setTexto(String texto)
	{
		this.texto = texto;
	}
	/**
	 * Devuelve una lista de documentos de tipo DocumentoExpedientePAD 
	 * @return
	 * @see es.caib.zonaper.modelInterfaz.DocumentoExpedientePAD
	 */
	public List getDocumentos()
	{
		return documentos;
	}
	/**
	 * Añade un documento de tipo DocumentoExpedientePAD a la lista de documentos del evento
	 * @param documento
	 */
	public void addDocumento( DocumentoExpedientePAD documento )
	{
		documentos.add( documento );
	}
	/**
	 * Borra un documento de la lista de documentos
	 * @param documento
	 */
	public void removeDocumento( DocumentoExpedientePAD documento )
	{
		documentos.remove( documento );
	}
	/**
	 * Titulo del evento
	 * @return
	 */
	public String getTitulo() {
		return titulo;
	}
	/**
	 * Titulo del evento
	 * @param titulo
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	/**
	 * Texto SMS para alerta tramitacion
	 * @return
	 */
	public String getTextoSMS() {
		return textoSMS;		
	}
	/**
	 * Texto SMS para alerta tramitacion
	 * @return
	 */
	public void setTextoSMS(String textoSMS) {
		this.textoSMS = textoSMS;
	}
	public Date getFechaConsulta() {
		return fechaConsulta;
	}
	public void setFechaConsulta(Date fechaConsulta) {
		this.fechaConsulta = fechaConsulta;
	}
	public Boolean getAccesiblePorClave() {
		return accesiblePorClave;
	}
	public void setAccesiblePorClave(Boolean accesiblePorClave) {
		this.accesiblePorClave = accesiblePorClave;
	}
	
}
