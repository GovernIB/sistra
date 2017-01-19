package es.caib.firmaweb.model;

import java.util.Map;

/**
 * Configuracion callback.
 * @author Indra
 *
 */
public class CallbackData {
	
	/**
	 * Url vuelta aplicacion.
	 */
	private String url;
	/**
	 * Parametro donde se devolverá la firma.
	 */
	private String paramSignature;
	/**
	 * Otros parametros con su valor que se devolveran adicionalmente.
	 */
	private Map<String, String> paramOthers;
	
	/**
	 * Url vuelta aplicacion para cancelar.
	 */
	private String urlCancel;
	
	/**
	 * Si se redige el parent o el propio iframe.
	 */
	private String target;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getParamSignature() {
		return paramSignature;
	}
	public void setParamSignature(String paramSignature) {
		this.paramSignature = paramSignature;
	}
	public Map<String, String> getParamOthers() {
		return paramOthers;
	}
	public void setParamOthers(Map<String, String> paramOthers) {
		this.paramOthers = paramOthers;
	}
	public String getUrlCancel() {
		return urlCancel;
	}
	public void setUrlCancel(String urlCancel) {
		this.urlCancel = urlCancel;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
		
}
