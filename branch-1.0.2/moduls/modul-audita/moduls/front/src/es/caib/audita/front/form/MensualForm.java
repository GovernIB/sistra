package es.caib.audita.front.form;

import java.util.Calendar;

import org.apache.struts.validator.ValidatorForm;

public class MensualForm extends ValidatorForm
{
	
	private int anyoInicio;
	private int mesInicio;
	private int anyoFinal;
	private int mesFinal;
	public int getAnyoFinal() {
		return anyoFinal;
	}
	public void setAnyoFinal(int anyoFinal) {
		this.anyoFinal = anyoFinal;
	}
	public int getAnyoInicio() {
		return anyoInicio;
	}
	public void setAnyoInicio(int anyoInicio) {
		this.anyoInicio = anyoInicio;
	}
	public int getMesFinal() {
		return mesFinal;
	}
	public void setMesFinal(int mesFinal) {
		this.mesFinal = mesFinal;
	}
	public int getMesInicio() {
		return mesInicio;
	}
	public void setMesInicio(int mesInicio) {
		this.mesInicio = mesInicio;
	}
	
	public MensualForm() {
		super();
		Calendar fecha = Calendar.getInstance();
	    this.anyoInicio = fecha.get(Calendar.YEAR);
	    this.mesInicio = fecha.get(Calendar.MONTH)+1;
	    this.anyoFinal = 0;
	    this.mesFinal = 0;
	}

	

}
