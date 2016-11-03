package es.caib.regtel.model.ws;

import java.util.Map;

public class TramiteSubsanacion {

	private String descripcionTramite;
	private String identificadorTramite;
	private Integer versionTramite;
	private Map<String,String> parametrosTramite;
	
	public String getDescripcionTramite() {
		return descripcionTramite;
	}
	public void setDescripcionTramite(String descripcionTramite) {
		this.descripcionTramite = descripcionTramite;
	}
	public String getIdentificadorTramite() {
		return identificadorTramite;
	}
	public void setIdentificadorTramite(String identificadorTramite) {
		this.identificadorTramite = identificadorTramite;
	}
	public Map<String, String> getParametrosTramite() {
		return parametrosTramite;
	}
	public void setParametrosTramite(Map<String, String> parametrosTramite) {
		this.parametrosTramite = parametrosTramite;
	}
	public Integer getVersionTramite() {
		return versionTramite;
	}
	public void setVersionTramite(Integer versionTramite) {
		this.versionTramite = versionTramite;
	}
	

}
