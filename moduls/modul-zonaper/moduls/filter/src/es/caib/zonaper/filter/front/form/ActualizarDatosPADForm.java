package es.caib.zonaper.filter.front.form;

import org.apache.struts.validator.ValidatorForm;

public class ActualizarDatosPADForm extends ValidatorForm
{
	private String nombre;
	private String apellido1;
	private String apellido2;
	private String urlOriginal;
	private String look;
	
	public String getApellido1()
	{
		return apellido1;
	}
	public void setApellido1(String apellido1)
	{
		this.apellido1 = apellido1;
	}
	public String getApellido2()
	{
		return apellido2;
	}
	public void setApellido2(String apellido2)
	{
		this.apellido2 = apellido2;
	}
	public String getNombre()
	{
		return nombre;
	}
	public void setNombre(String nombre)
	{
		this.nombre = nombre;
	}
	public String getUrlOriginal()
	{
		return urlOriginal;
	}
	public void setUrlOriginal(String urlOriginal)
	{
		this.urlOriginal = urlOriginal;
	}
	public String getLook()
	{
		return look;
	}
	public void setLook(String look)
	{
		this.look = look;
	}
}
