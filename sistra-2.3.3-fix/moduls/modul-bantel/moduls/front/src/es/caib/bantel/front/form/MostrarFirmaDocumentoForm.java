package es.caib.bantel.front.form;

public class MostrarFirmaDocumentoForm extends InitForm
{
	private Long codigo;
	private String clave;
	private String nif;

	public Long getCodigo()
	{
		return codigo;
	}

	public void setCodigo(Long codigo)
	{
		this.codigo = codigo;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

}
