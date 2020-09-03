package es.caib.zonaper.modelInterfaz;

import java.io.Serializable;
import java.util.Date;

public class DetalleElementoExpedientePAD implements Serializable {

	/** Tipo registro. */
	public final static String TIPO_REGISTRO = "E";
	/** Tipo envio. */
	public final static String TIPO_ENVIO = "B";
	/** Tipo preregistro. */
	public final static String TIPO_PREREGISTRO= "P";
	/** Tipo preregistro. */
	public final static String TIPO_PREENVIO= "N";
	/** Tipo notificacion. */
	public final static String TIPO_NOTIFICACION= "S";
	/** Tipo aviso. */
	public final static String TIPO_AVISO= "A";

	private String tipo;
	private String descripcion;
    private Date fecha;
    private boolean pendiente;
    private String url;

	/**
	 *	Devuelve tipo.
	 * @return tipo
	 */
	public String getTipo() {
		return tipo;
	}
	/**
	 * Establece tipo.
	 * @param tipo tipo
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	/**
	 *	Devuelve descripcion.
	 * @return descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}
	/**
	 * Establece descripcion.
	 * @param descripcion descripcion
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	/**
	 *	Devuelve fecha.
	 * @return fecha
	 */
	public Date getFecha() {
		return fecha;
	}
	/**
	 * Establece fecha.
	 * @param fecha fecha
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	/**
	 *	Devuelve pendiente.
	 * @return pendiente
	 */
	public boolean isPendiente() {
		return pendiente;
	}
	/**
	 * Establece pendiente.
	 * @param pendiente pendiente
	 */
	public void setPendiente(boolean pendiente) {
		this.pendiente = pendiente;
	}
	/**
	 *	Devuelve url.
	 * @return url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * Establece url.
	 * @param url url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

}
