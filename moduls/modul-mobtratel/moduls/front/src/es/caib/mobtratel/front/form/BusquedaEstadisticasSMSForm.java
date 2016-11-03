package es.caib.mobtratel.front.form;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.struts.validator.ValidatorForm;

public class BusquedaEstadisticasSMSForm extends ValidatorForm
{
	private int anyo = new GregorianCalendar().get( Calendar.YEAR );
	private String cuenta;
	
	public int getAnyo()
	{
		return anyo;
	}
	public void setAnyo(int anyo)
	{
		this.anyo = anyo;
	}
	public String getCuenta() {
		return cuenta;
	}
	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}	
}
