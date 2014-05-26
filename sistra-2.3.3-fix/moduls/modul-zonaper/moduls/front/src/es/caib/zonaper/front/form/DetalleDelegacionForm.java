package es.caib.zonaper.front.form;

public class DetalleDelegacionForm extends InitForm
{
	private String documentoB64;
	private String firma;
	private String nifDelegado;
	private String permiso;
	private String fechaInicioDelegacion;
	private String fechaFinDelegacion;
	
	public String getDocumentoB64() {
		return documentoB64;
	}
	public void setDocumentoB64(String documentoB64) {
		this.documentoB64 = documentoB64;
	}
	public String getFechaFinDelegacion() {
		return fechaFinDelegacion;
	}
	public void setFechaFinDelegacion(String fechaFinDelegacion) {
		this.fechaFinDelegacion = fechaFinDelegacion;
	}
	public String getFechaInicioDelegacion() {
		return fechaInicioDelegacion;
	}
	public void setFechaInicioDelegacion(String fechaInicioDelegacion) {
		this.fechaInicioDelegacion = fechaInicioDelegacion;
	}
	public String getFirma() {
		return firma;
	}
	public void setFirma(String firma) {
		this.firma = firma;
	}
	public String getNifDelegado() {
		return nifDelegado;
	}
	public void setNifDelegado(String nifDelegado) {
		this.nifDelegado = nifDelegado;
	}
	public String getPermiso() {
		return permiso;
	}
	public void setPermiso(String permiso) {
		this.permiso = permiso;
	}
	
	
}
