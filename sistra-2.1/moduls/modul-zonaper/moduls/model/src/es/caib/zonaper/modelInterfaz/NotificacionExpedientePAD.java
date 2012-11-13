package es.caib.zonaper.modelInterfaz;

import java.util.ArrayList;
import java.util.List;

/**
* <p>
* Clase que representa una notificación en un expediente de la zona personal
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
	 * Indica si se permite acceder anonimamente mediante clave
	 */
	private Boolean accesiblePorClave;
	/**
	 * Indica si se requiere firma del acuse de recibo
	 */
	private boolean firmarPorClave;
	/**
	 * Detalle acuse recibo.
	 */
	private DetalleAcuseRecibo detalleAcuseRecibo;
	/**
	 * Documentos notificacion
	 */
	private List documentos = new ArrayList();
	
	/**
	 * Obtiene documentos de la notificacion
	 * @see DocumentoExpedientePAD
	 * @return Documentos notificación
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
	 * Obtiene número de registro de la notificación
	 * @return Número de registro 
	 */
	public String getNumeroRegistro() {
		return numeroRegistro;
	}

	/**
	 * Establece número de registro
	 * @param numeroRegistro
	 */
	public void setNumeroRegistro(String numeroRegistro) {
		this.numeroRegistro = numeroRegistro;
	}	

	/**
	 * Indica si la notificación requiere firma de acuse de recibo
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
	 * Obtiene texto del oficio de remisión 
	 * @return Texto del oficio de remisión
	 */
	public String getTextoOficio() {
		return textoOficio;
	}

	/**
	 * Establece texto del oficio de remisión
	 * @param texto Texto del oficio de remisión
	 */
	public void setTextoOficio(String texto) {
		this.textoOficio = texto;
	}

	/**
	 * Titulo del oficio de remisión
	 * @return  Titulo del oficio de remisión
	 */
	public String getTituloOficio() {
		return tituloOficio;
	}
	
	/**
	 * Titulo del oficio de remisión
	 * @param titulo Titulo del oficio de remisión
	 */
	public void setTituloOficio(String titulo) {
		this.tituloOficio = titulo;
	}

	public DetalleAcuseRecibo getDetalleAcuseRecibo() {
		return detalleAcuseRecibo;
	}

	public void setDetalleAcuseRecibo(DetalleAcuseRecibo detalleAcuseRecibo) {
		this.detalleAcuseRecibo = detalleAcuseRecibo;
	}

	public Boolean getAccesiblePorClave() {
		return accesiblePorClave;
	}

	public void setAccesiblePorClave(Boolean accesiblePorClave) {
		this.accesiblePorClave = accesiblePorClave;
	}

	public boolean isFirmarPorClave() {
		return firmarPorClave;
	}

	public void setFirmarPorClave(boolean firmarPorClave) {
		this.firmarPorClave = firmarPorClave;
	}
	
}
