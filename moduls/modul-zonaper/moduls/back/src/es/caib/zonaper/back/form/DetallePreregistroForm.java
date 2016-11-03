package es.caib.zonaper.back.form;

public class DetallePreregistroForm extends InitForm
{
	private Long codigo;
	private String oficina;
	private String municipioBaleares;
	private String municipioFuera;
	
	public Long getCodigo()
	{
		return codigo;
	}

	public void setCodigo(Long codigo)
	{
		this.codigo = codigo;
	}

	public String getMunicipioBaleares()
	{
		return municipioBaleares;
	}

	public void setMunicipioBaleares(String municipioBaleares)
	{
		this.municipioBaleares = municipioBaleares;
	}

	public String getMunicipioFuera()
	{
		return municipioFuera;
	}

	public void setMunicipioFuera(String municipioFuera)
	{
		this.municipioFuera = municipioFuera;
	}

	public String getOficina()
	{
		return oficina;
	}

	public void setOficina(String oficina)
	{
		this.oficina = oficina;
	}
	
}
