package es.caib.firmaweb.model;

/**
 * Configuracion firma.
 * @author Indra
 *
 */
public class ConfigData {
	
	/**
	 * Idioma (es / ca).
	 */
	private String lang;
	/**
	 * Tipo firma (PAdES / XAdES / CAdES / SMIME).
	 */
	private String signType;
	/**
	 * Modo firma para Xades y Cades (0 - Attached / 1 - Detached).
	 */
	private String signMode;
	/**
	 * Algoritmo firma (SHA-1).
	 */
	private String signAlgorithm;
	
	public String getLang() {
		return lang;
	}
	public void setLang(String configLang) {
		this.lang = configLang;
	}
	public String getSignType() {
		return signType;
	}
	public void setSignType(String configSignType) {
		this.signType = configSignType;
	}
	public String getSignMode() {
		return signMode;
	}
	public void setSignMode(String configSignMode) {
		this.signMode = configSignMode;
	}
	public String getSignAlgorithm() {
		return signAlgorithm;
	}
	public void setSignAlgorithm(String configSignAlgorithm) {
		this.signAlgorithm = configSignAlgorithm;
	}
	
	
	
}
