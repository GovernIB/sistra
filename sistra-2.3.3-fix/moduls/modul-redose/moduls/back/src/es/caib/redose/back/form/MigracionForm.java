package es.caib.redose.back.form;

import org.apache.struts.validator.ValidatorForm;

public class MigracionForm extends ValidatorForm
{
	private String ubicacionOrigen;
	private String ubicacionDestino;	
	private String desde;
	private String hasta;
	private String numDocsIteracion;
	private String timeoutIteracion;
	private String numMaxDocs;
	private String numMaxErrores;
	private String borrarUbicacionOrigen;
	
	public String getUbicacionOrigen() {
		return ubicacionOrigen;
	}
	public void setUbicacionOrigen(String ubicacionOrigen) {
		this.ubicacionOrigen = ubicacionOrigen;
	}
	public String getUbicacionDestino() {
		return ubicacionDestino;
	}
	public void setUbicacionDestino(String ubicacionDestino) {
		this.ubicacionDestino = ubicacionDestino;
	}
	public String getDesde() {
		return desde;
	}
	public void setDesde(String desde) {
		this.desde = desde;
	}
	public String getHasta() {
		return hasta;
	}
	public void setHasta(String hasta) {
		this.hasta = hasta;
	}
	public String getNumDocsIteracion() {
		return numDocsIteracion;
	}
	public void setNumDocsIteracion(String numDocsIteracion) {
		this.numDocsIteracion = numDocsIteracion;
	}
	public String getTimeoutIteracion() {
		return timeoutIteracion;
	}
	public void setTimeoutIteracion(String timeoutIteracion) {
		this.timeoutIteracion = timeoutIteracion;
	}
	public String getNumMaxDocs() {
		return numMaxDocs;
	}
	public void setNumMaxDocs(String numMaxDocs) {
		this.numMaxDocs = numMaxDocs;
	}
	public String getNumMaxErrores() {
		return numMaxErrores;
	}
	public void setNumMaxErrores(String numMaxErrores) {
		this.numMaxErrores = numMaxErrores;
	}
	public String getBorrarUbicacionOrigen() {
		return borrarUbicacionOrigen;
	}
	public void setBorrarUbicacionOrigen(String borrarUbicacionOrigen) {
		this.borrarUbicacionOrigen = borrarUbicacionOrigen;
	}
	
	
	
	
	
}
