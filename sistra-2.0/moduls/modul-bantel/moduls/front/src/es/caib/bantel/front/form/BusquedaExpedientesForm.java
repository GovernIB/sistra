package es.caib.bantel.front.form;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.struts.validator.ValidatorForm;

public class BusquedaExpedientesForm extends ValidatorForm
{
	private static final char TODOS = 'T';
		
	private int anyo = new GregorianCalendar().get( Calendar.YEAR );
	private int mes = -1;
	private String usuarioNif;
	private String identificadorProcedimiento = "-1";
	private String numeroEntrada = "";
	private int pagina;
	
	public int getAnyo() {
		return anyo;
	}
	public void setAnyo(int anyo) {
		this.anyo = anyo;
	}
	public int getMes() {
		return mes;
	}
	public void setMes(int mes) {
		this.mes = mes;
	}
	public String getUsuarioNif() {
		return usuarioNif;
	}
	public void setUsuarioNif(String usuarioNif) {
		this.usuarioNif = usuarioNif;
	}
	public String getIdentificadorProcedimiento() {
		return identificadorProcedimiento;
	}
	public void setIdentificadorProcedimiento(String identificadorProcedimiento) {
		this.identificadorProcedimiento = identificadorProcedimiento;
	}
	public String getNumeroEntrada() {
		return numeroEntrada;
	}
	public void setNumeroEntrada(String numeroEntrada) {
		this.numeroEntrada = numeroEntrada;
	}
	public int getPagina() {
		return pagina;
	}
	public void setPagina(int pagina) {
		this.pagina = pagina;
	}
	
}
