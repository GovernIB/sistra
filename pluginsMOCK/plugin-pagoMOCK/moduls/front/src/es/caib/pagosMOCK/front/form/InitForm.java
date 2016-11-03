package es.caib.pagosMOCK.front.form;

import org.apache.struts.validator.ValidatorForm;

public class InitForm extends ValidatorForm
{
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
		
}
