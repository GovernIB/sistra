package es.caib.zonaper.front.form;

import es.caib.zonaper.front.form.InitForm;

public class AbrirNotificacionForm extends InitForm
{
	Long codigo;
	String asiento;
	String firma;
	
	public String getAsiento()
	{
		return asiento;
	}
	public void setAsiento(String asiento)
	{
		this.asiento = asiento;
	}
	public String getFirma()
	{
		return firma;
	}
	public void setFirma(String firma)
	{
		this.firma = firma;
	}
	public Long getCodigo()
	{
		return codigo;
	}
	public void setCodigo(Long codigo)
	{
		this.codigo = codigo;
	}
	
}
