package es.caib.zonaper.back.form;

public class MostrarDocumentoForm extends InitForm
{
	private Long codigoEntrada;
	private Long codigo;

	public Long getCodigo()
	{
		return codigo;
	}

	public void setCodigo(Long codigo)
	{
		this.codigo = codigo;
	}

	public Long getCodigoEntrada() {
		return codigoEntrada;
	}

	public void setCodigoEntrada(Long codigoEntrada) {
		this.codigoEntrada = codigoEntrada;
	}

}
