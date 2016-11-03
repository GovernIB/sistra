package es.caib.redose.modelInterfaz;

import java.util.Date;

import es.caib.sistra.plugins.firma.FirmaIntf;


/**
 * Clase que modeliza las propiedades visibles del documento al verificarlo a traves del verificador
 * 
 * 
 */
public class DocumentoVerifier  implements java.io.Serializable {
	
	/**
	 * Titulo del documento
	 */	
	private String titulo;
	/**
	 * Nombre del fichero asociado al documento
	 */
	private String nombreFichero;
	/**
	 * Datos del fichero asociado al documento
	 */
	private byte[] datosFichero;
	/**
	 * Nombre del fichero asociado al documento formateado
	 */
	private String nombreFicheroFormateado;
	/**
	 * Datos del fichero asociado al documento formateado
	 */
	private byte[] datosFicheroFormateado;		
	/**
	 * Indica si el documento es estructurado (XML). Depende del modelo del documento.
	 */
	private boolean estructurado; 
	/**
	 * Extensión del fichero asociado al documento
	 */
	private String extensionFichero;	
	/**
	 * Firmas del documento
	 */
	private FirmaIntf[] firmas;	
	/**
	 * Fecha de introducción en el RDS. Este dato es calculado por el RDS.
	 */	
	private Date fechaRDS;
	
	
	public byte[] getDatosFichero() {
		return datosFichero;
	}
	public void setDatosFichero(byte[] datosFichero) {
		this.datosFichero = datosFichero;
	}
	public boolean isEstructurado() {
		return estructurado;
	}
	public void setEstructurado(boolean estructurado) {
		this.estructurado = estructurado;
	}
	public String getExtensionFichero() {
		return extensionFichero;
	}
	public void setExtensionFichero(String extensionFichero) {
		this.extensionFichero = extensionFichero;
	}
	public Date getFechaRDS() {
		return fechaRDS;
	}
	public void setFechaRDS(Date fechaRDS) {
		this.fechaRDS = fechaRDS;
	}
	public FirmaIntf[] getFirmas() {
		return firmas;
	}
	public void setFirmas(FirmaIntf[] firmas) {
		this.firmas = firmas;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getNombreFichero() {
		return nombreFichero;
	}
	public void setNombreFichero(String nombreFichero) {
		this.nombreFichero = nombreFichero;
	}
	public byte[] getDatosFicheroFormateado() {
		return datosFicheroFormateado;
	}
	public void setDatosFicheroFormateado(byte[] datosFicheroFormateado) {
		this.datosFicheroFormateado = datosFicheroFormateado;
	}
	public String getNombreFicheroFormateado() {
		return nombreFicheroFormateado;
	}
	public void setNombreFicheroFormateado(String nombreFicheroFormateado) {
		this.nombreFicheroFormateado = nombreFicheroFormateado;
	} 
		
	
}
