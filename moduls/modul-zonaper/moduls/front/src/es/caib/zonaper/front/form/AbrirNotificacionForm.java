package es.caib.zonaper.front.form;

import es.caib.zonaper.front.form.InitForm;

public class AbrirNotificacionForm extends InitForm
{
	private Long codigo;
	private String asiento;
	private String firma;
	private String tipoFirma;
	
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
	public String getTipoFirma() {
		return tipoFirma;
	}
	public void setTipoFirma(String tipoFirma) {
		this.tipoFirma = tipoFirma;
	}
	
}
