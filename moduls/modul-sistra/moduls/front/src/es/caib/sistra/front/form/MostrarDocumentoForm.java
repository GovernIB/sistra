package es.caib.sistra.front.form;

public class MostrarDocumentoForm  extends DocumentoForm
{
	private char tipo;
	private String identificadorDocumento;
	
	public String getIdentificadorDocumento()
	{
		return identificadorDocumento;
	}
	public void setIdentificadorDocumento(String identificadorDocumento)
	{
		this.identificadorDocumento = identificadorDocumento;
	}

}
