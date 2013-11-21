package es.caib.zonaper.front.form;

public class MostrarDocumentoForm extends InitForm
{
	private Long codigoEntrada;
	private Long codigoDocumento;
	private char tipoEntrada;

	public Long getCodigoDocumento()
	{
		return codigoDocumento;
	}

	public void setCodigoDocumento(Long codigo)
	{
		this.codigoDocumento = codigo;
	}

	public char getTipoEntrada()
	{
		return tipoEntrada;
	}

	public void setTipoEntrada(char tipo)
	{
		this.tipoEntrada = tipo;
	}

	public Long getCodigoEntrada() {
		return codigoEntrada;
	}

	public void setCodigoEntrada(Long codigoEntrada) {
		this.codigoEntrada = codigoEntrada;
	}

}
