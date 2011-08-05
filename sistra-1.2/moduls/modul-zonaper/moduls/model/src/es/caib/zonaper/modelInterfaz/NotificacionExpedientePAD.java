package es.caib.zonaper.modelInterfaz;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
* <p>
* Clase que representa una notificaci�n en un expediente de la zona personal
* </p>
*/
public class NotificacionExpedientePAD extends ElementoExpedientePAD{
	/**
	 * Numero de registro
	 */
	private String numeroRegistro;
	/**
	 * Titulo de oficio
	 */
	private String tituloOficio;
	/**
	 * Texto de oficio
	 */
	private String textoOficio;
	/**
	 * Indica si se requiere firma del acuse de recibo
	 */
	private boolean requiereAcuse;
	/**
	 * Fecha de la firma del acuse de recibo
	 */
	private Date fechaFirmaAcuse;
	
	/**
	 * Documentos notificacion
	 */
	private List documentos = new ArrayList();
	
	/**
	 * Obtiene documentos de la notificacion
	 * @see DocumentoExpedientePAD
	 * @return Documentos notificaci�n
	 */
	public List getDocumentos() {
		return documentos;
	}

	/**
	 * Establece documentos de la notificacion
	 * @see DocumentoExpedientePAD
	 * @param documentos Documentos de la notificacion
	 */
	public void setDocumentos(List documentos) {
		this.documentos = documentos;
	}

	/**
	 * Obtiene n�mero de registro de la notificaci�n
	 * @return N�mero de registro 
	 */
	public String getNumeroRegistro() {
		return numeroRegistro;
	}

	/**
	 * Establece n�mero de registro
	 * @param numeroRegistro
	 */
	public void setNumeroRegistro(String numeroRegistro) {
		this.numeroRegistro = numeroRegistro;
	}

	/**
	 * Obtiene fecha de la firma del acuse de recibo
	 * @return Fecha firma acuse (si no se ha accedido a la notificaci�n devuelve nulo)
	 */
	public Date getFechaFirmaAcuse() {
		return fechaFirmaAcuse;
	}

	/**
	 * Establece fecha de la firma del acuse de recibo
	 * @param fechaFirmaAcuse Fecha firma acuse
	 */
	public void setFechaFirmaAcuse(Date fechaFirmaAcuse) {
		this.fechaFirmaAcuse = fechaFirmaAcuse;
	}

	/**
	 * Indica si la notificaci�n requiere firma de acuse de recibo
	 * @return True si requiere firma del acuse de recibo
	 */
	public boolean isRequiereAcuse() {
		return requiereAcuse;
	}

	/**
	 * Establece fecha de la firma del acuse de recibo
	 * @param requiereAcuse True si requiere firma del acuse
	 */
	public void setRequiereAcuse(boolean requiereAcuse) {
		this.requiereAcuse = requiereAcuse;
	}

	/**
	 * Obtiene texto del oficio de remisi�n 
	 * @return Texto del oficio de remisi�n
	 */
	public String getTextoOficio() {
		return textoOficio;
	}

	/**
	 * Establece texto del oficio de remisi�n
	 * @param texto Texto del oficio de remisi�n
	 */
	public void setTextoOficio(String texto) {
		this.textoOficio = texto;
	}

	/**
	 * Titulo del oficio de remisi�n
	 * @return  Titulo del oficio de remisi�n
	 */
	public String getTituloOficio() {
		return tituloOficio;
	}
	
	/**
	 * Titulo del oficio de remisi�n
	 * @param titulo Titulo del oficio de remisi�n
	 */
	public void setTituloOficio(String titulo) {
		this.tituloOficio = titulo;
	}
	
}
