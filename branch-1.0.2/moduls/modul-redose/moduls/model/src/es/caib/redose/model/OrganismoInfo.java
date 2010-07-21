package es.caib.redose.model;

/**
 * Clase que modela la info de un organismo y permite 
 * customizar la presentación de los módulos
 *
 */
public class OrganismoInfo {

	private String nombre;
	private String urlLogo;
	private String urlPortal;
	private String pieContactoHTML;
	private String telefonoIncidencias;
	private String urlSoporteIncidencias;	
	private String urlCssCustom;
	
	public String getUrlCssCustom() {
		return urlCssCustom;
	}
	public void setUrlCssCustom(String urlCssCustom) {
		this.urlCssCustom = urlCssCustom;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getPieContactoHTML() {
		return pieContactoHTML;
	}
	public void setPieContactoHTML(String pieContactoHTML) {
		this.pieContactoHTML = pieContactoHTML;
	}
	public String getTelefonoIncidencias() {
		return telefonoIncidencias;
	}
	public void setTelefonoIncidencias(String telefonoIncidencias) {
		this.telefonoIncidencias = telefonoIncidencias;
	}
	public String getUrlLogo() {
		return urlLogo;
	}
	public void setUrlLogo(String urlLogo) {
		this.urlLogo = urlLogo;
	}
	public String getUrlPortal() {
		return urlPortal;
	}
	public void setUrlPortal(String urlPortal) {
		this.urlPortal = urlPortal;
	}
	public String getUrlSoporteIncidencias() {
		return urlSoporteIncidencias;
	}
	public void setUrlSoporteIncidencias(String urlTramiteIncidencias) {
		this.urlSoporteIncidencias = urlTramiteIncidencias;
	}	
	
}
