package es.caib.sistra.plugins.firma;

import java.io.Serializable;

/**
 * Modeliza la conversi�n de una firma a un fichero descargable por el usuario  
 */
public class FicheroFirma implements Serializable{
	/**
	 * Nombre del fichero con extensi�n
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
	 * Nombre del fichero con extensi�n
	 * @return Nombre del fichero con extensi�n
	 */
	public String getNombreFichero() {
		return nombreFichero;
	}
	/**
	 * Nombre del fichero con extensi�n
	 * @param nombreFichero Nombre del fichero con extensi�n
	 */
	public void setNombreFichero(String nombreFichero) {
		this.nombreFichero = nombreFichero;
	}
	

}
