package es.caib.zonaper.front.form;

import org.apache.struts.validator.ValidatorForm;

public class FiltroExpedientesForm extends ValidatorForm{

	private String filtro;

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}
	
	
	
}
