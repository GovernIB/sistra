package es.caib.audita.front.form;

import java.util.Calendar;

import org.apache.struts.validator.ValidatorForm;

public class AnualForm extends ValidatorForm
{
	private int anyoInicio;
	private int anyoFinal;
	public int getAnyoFinal()
	{
		return anyoFinal;
	}
	public void setAnyoFinal(int anyoFinal)
	{
		this.anyoFinal = anyoFinal;
	}
	public int getAnyoInicio()
	{
		return anyoInicio;
	}
	public void setAnyoInicio(int anyoInicio)
	{
		this.anyoInicio = anyoInicio;
	}
	public AnualForm() {
		super();
		Calendar fecha = Calendar.getInstance();
	    this.anyoInicio = fecha.get(Calendar.YEAR);
	    this.anyoFinal = 0;
	}
	
	
	
}
