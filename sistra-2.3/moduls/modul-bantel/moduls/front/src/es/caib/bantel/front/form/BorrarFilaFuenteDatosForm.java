package es.caib.bantel.front.form;

import org.apache.struts.validator.ValidatorForm;

public class BorrarFilaFuenteDatosForm extends ValidatorForm
{
	private String identificador;
	
	private int numfila;

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public int getNumfila() {
		return numfila;
	}

	public void setNumfila(int numfila) {
		this.numfila = numfila;
	}
	
}
