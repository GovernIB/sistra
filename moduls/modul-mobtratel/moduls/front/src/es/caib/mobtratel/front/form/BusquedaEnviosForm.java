package es.caib.mobtratel.front.form;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.struts.validator.ValidatorForm;

public class BusquedaEnviosForm extends ValidatorForm
{
	private static final String TODOS = "T";
		
	private int anyo = new GregorianCalendar().get( Calendar.YEAR );
	private int mes = -1;
	private String tipo;
	private String enviado = TODOS;
	private String cuenta;
	private int pagina;
	
	public int getAnyo()
	{
		return anyo;
	}
	public void setAnyo(int anyo)
	{
		this.anyo = anyo;
	}	
	public int getMes()
	{
		return mes;
	}
	public void setMes(int mes)
	{
		this.mes = mes;
	}
	public int getPagina()
	{
		return pagina;
	}
	public void setPagina(int pagina)
	{
		this.pagina = pagina;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getEnviado() {
		return enviado;
	}
	public void setEnviado(String enviado) {
		this.enviado = enviado;
	}
	public String getCuenta() {
		return cuenta;
	}
	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}
	
	

}
