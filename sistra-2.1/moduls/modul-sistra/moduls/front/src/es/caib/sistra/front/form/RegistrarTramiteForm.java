package es.caib.sistra.front.form;

public class RegistrarTramiteForm extends SistraFrontForm
{
	private String asiento;
	private String firma;
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
}
