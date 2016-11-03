package es.caib.bantel.front.util;

import org.apache.commons.lang.StringUtils;

import es.caib.util.ConvertUtil;
import es.caib.zonaper.modelInterfaz.DocumentoExpedientePAD;


/**
 * Clase que contiene un DocumentoExpedientePAD y nos indica si lo tenemos que firmar o no
 *
 */
public class DocumentoFirmar extends DocumentoExpedientePAD{

	private boolean firmar;
	private String contentType;
	private String rutaFichero;
	private String firma;
	private boolean vistoPDF;
	private String tipoDocumento; // FICHERO / URL
	private String url;
	private boolean firmable; // Marca si es firmable
	
	public boolean isVistoPDF() {
		return vistoPDF;
	}
	public void setVistoPDF(boolean vistoPDF) {
		this.vistoPDF = vistoPDF;
	}
	public boolean isFirmar() {
		return firmar;
	}
	public void setFirmar(boolean firmar) {
		this.firmar = firmar;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public String getContentType() {
		return contentType;
	}
	public String getRutaFichero() {
		return rutaFichero;
	}
	public void setRutaFichero(String rutaFichero) {
		this.rutaFichero = rutaFichero;
	}
	public String getFirma() {
		return firma;
	}
	public void setFirma(String firma) {
		this.firma = firma;
	}
	public String getTituloB64() {
		if(StringUtils.isNotEmpty(getTitulo())){
			try {
				return ConvertUtil.cadenaToBase64UrlSafe(getTitulo());
			} catch (Exception e) {
				return "";
			}
		}
		return "";
	}
	public String getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public boolean isFirmable() {
		return firmable;
	}
	public void setFirmable(boolean firmable) {
		this.firmable = firmable;
	}	
	
}
