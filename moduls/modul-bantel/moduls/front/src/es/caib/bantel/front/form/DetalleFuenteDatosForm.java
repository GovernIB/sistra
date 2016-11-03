package es.caib.bantel.front.form;

import org.apache.struts.validator.ValidatorForm;

public class DetalleFuenteDatosForm extends ValidatorForm
{
	private String identificador;

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}
	
}
