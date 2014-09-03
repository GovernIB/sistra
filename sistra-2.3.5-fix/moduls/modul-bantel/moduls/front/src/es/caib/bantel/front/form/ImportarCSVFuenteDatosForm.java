package es.caib.bantel.front.form;

import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.ValidatorForm;



public class ImportarCSVFuenteDatosForm extends ValidatorForm {
	
	private String identificador;
	private FormFile csv;
	
	
	public FormFile getCsv() {
		return csv;
	}
	public void setCsv(FormFile csv) {
		this.csv = csv;
	}
	public String getIdentificador() {
		return identificador;
	}
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}	
		
}
