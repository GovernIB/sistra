package es.caib.bantel.front.form;

import org.apache.struts.validator.ValidatorForm;

public class ExportCSVTramitesForm extends ValidatorForm
{
	private String identificadorTramite;
	private String procesada;
	private String desde;
	private String hasta;
	
	
	
	public String getIdentificadorTramite() {
		return identificadorTramite;
	}
	public void setIdentificadorTramite(String identificadorTramite) {
		this.identificadorTramite = identificadorTramite;
	}
	public String getProcesada() {
		return procesada;
	}
	public void setProcesada(String procesada) {
		this.procesada = procesada;
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
	
	
	
	
	
}
