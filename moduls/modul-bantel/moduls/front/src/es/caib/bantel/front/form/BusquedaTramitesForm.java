package es.caib.bantel.front.form;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.struts.validator.ValidatorForm;

public class BusquedaTramitesForm extends ValidatorForm
{
	private static final char TODOS = 'T';
		
	private int anyo = new GregorianCalendar().get( Calendar.YEAR );
	private int mes = -1;
	private String usuarioNif;
	private String usuarioNombre;
	private char tipo = TODOS;
	private char procesada = TODOS;
	private char nivelAutenticacion = TODOS;
	private String identificadorTramite = "-1";
	private int pagina;
	private String numeroEntrada = "";
	
	public int getAnyo()
	{
		return anyo;
	}
	public void setAnyo(int anyo)
	{
		this.anyo = anyo;
	}	
	public String getIdentificadorTramite()
	{
		return identificadorTramite;
	}
	public void setIdentificadorTramite(String identificadorTramite)
	{
		this.identificadorTramite = identificadorTramite;
	}
	public int getMes()
	{
		return mes;
	}
	public void setMes(int mes)
	{
		this.mes = mes;
	}
	public char getNivelAutenticacion()
	{
		return nivelAutenticacion;
	}
	public void setNivelAutenticacion(char nivelAutenticacion)
	{
		this.nivelAutenticacion = nivelAutenticacion;
	}
	public int getPagina()
	{
		return pagina;
	}
	public void setPagina(int pagina)
	{
		this.pagina = pagina;
	}
	public char getProcesada()
	{
		return procesada;
	}
	public void setProcesada(char procesada)
	{
		this.procesada = procesada;
	}
	public char getTipo()
	{
		return tipo;
	}
	public void setTipo(char tipo)
	{
		this.tipo = tipo;
	}
	public String getUsuarioNif()
	{
		return usuarioNif;
	}
	public void setUsuarioNif(String usuarioNif)
	{
		this.usuarioNif = usuarioNif;
	}
	public String getUsuarioNombre()
	{
		return usuarioNombre;
	}
	public void setUsuarioNombre(String usuarioNombre)
	{
		this.usuarioNombre = usuarioNombre;
	}
	public String getNumeroEntrada() {
		return numeroEntrada;
	}
	public void setNumeroEntrada(String numeroEntrada) {
		this.numeroEntrada = numeroEntrada;
	}

}
