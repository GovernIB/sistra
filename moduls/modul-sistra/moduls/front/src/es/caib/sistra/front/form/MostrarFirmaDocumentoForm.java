package es.caib.sistra.front.form;

public class MostrarFirmaDocumentoForm extends DocumentoForm
{
	private char tipo;
	private String identificadorDocumento;
	private String nif;
	
	public String getIdentificadorDocumento()
	{
		return identificadorDocumento;
	}
	public void setIdentificadorDocumento(String identificadorDocumento)
	{
		this.identificadorDocumento = identificadorDocumento;
	}
	
	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

}
