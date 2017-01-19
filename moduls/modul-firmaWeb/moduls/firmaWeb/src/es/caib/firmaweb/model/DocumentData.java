package es.caib.firmaweb.model;

/**
 * Datos documento a firmar.
 * @author Indra
 *
 */
public class DocumentData {
	
	/**
	 * Documento en B64 Url Safe.
	 */
	private String documentoB64UrlSafe;
	/**
	 * File name.
	 */
	private String documentoFilename;
	/**
	 * Nif firmante.
	 */
	private String documentoNif;
	
	public String getDocumentoB64UrlSafe() {
		return documentoB64UrlSafe;
	}
	public void setDocumentoB64UrlSafe(String documentoB64UrlSafe) {
		this.documentoB64UrlSafe = documentoB64UrlSafe;
	}
	public String getDocumentoFilename() {
		return documentoFilename;
	}
	public void setDocumentoFilename(String documentoFilename) {
		this.documentoFilename = documentoFilename;
	}
	public String getDocumentoNif() {
		return documentoNif;
	}
	public void setDocumentoNif(String documentoNif) {
		this.documentoNif = documentoNif;
	}
	
}
