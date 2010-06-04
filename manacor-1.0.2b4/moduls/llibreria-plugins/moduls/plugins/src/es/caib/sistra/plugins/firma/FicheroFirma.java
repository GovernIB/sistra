package es.caib.sistra.plugins.firma;

import java.io.Serializable;

/**
 * Modeliza la conversión de una firma a un fichero descargable por el usuario  
 */
public class FicheroFirma implements Serializable{
	/**
	 * Nombre del fichero con extensión
	 */
	private String nombreFichero;
	/**
	 * Contenido el fichero
	 */
	private byte[] contenido;
	/**
	 * Contenido del fichero
	 * @return Contenido del fichero
	 */
	public byte[] getContenido() {
		return contenido;	
	}
	/**
	 * Contenido del fichero
	 * @param contenido Contenido del fichero
	 */
	public void setContenido(byte[] contenido) {
		this.contenido = contenido;
	}
	/**
	 * Nombre del fichero con extensión
	 * @return Nombre del fichero con extensión
	 */
	public String getNombreFichero() {
		return nombreFichero;
	}
	/**
	 * Nombre del fichero con extensión
	 * @param nombreFichero Nombre del fichero con extensión
	 */
	public void setNombreFichero(String nombreFichero) {
		this.nombreFichero = nombreFichero;
	}
	

}
