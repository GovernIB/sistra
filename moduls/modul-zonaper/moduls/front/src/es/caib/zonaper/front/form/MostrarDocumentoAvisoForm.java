package es.caib.zonaper.front.form;

public class MostrarDocumentoAvisoForm extends InitForm
{
	private Long codigoAviso;
	private Long codigo;

	public Long getCodigo()
	{
		return codigo;
	}

	public void setCodigo(Long codigo)
	{
		this.codigo = codigo;
	}

	public Long getCodigoAviso() {
		return codigoAviso;
	}

	public void setCodigoAviso(Long codigoAviso) {
		this.codigoAviso = codigoAviso;
	}
}
