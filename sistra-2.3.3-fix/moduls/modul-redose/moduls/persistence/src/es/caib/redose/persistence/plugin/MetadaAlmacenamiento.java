package es.caib.redose.persistence.plugin;

import java.util.Date;

/**
 * Metadatos del documento que se pasan al plugin de almacenamiento.
 * Util para almacenamiento externo.
 * @author Indra
 *
 */
public class MetadaAlmacenamiento {

	/**
	 * Modelo documento.
	 */
	private String modelo;
	
	/**
	 * Version modelo documento;
	 */
	private int version;
	
	/**
	 * Nombre fichero.
	 */
	private String descripcion;
	
	/**
	 * Extension fichero.
	 */
	private String extension;
	
	/**
	 * Fecha de introducción en el RDS.
	 */	
	private Date fecha; 
	
	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String nombreFichero) {
		this.descripcion = nombreFichero;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
}
