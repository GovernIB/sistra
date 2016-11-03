package es.caib.sistra.front.form;

public abstract class DocumentoForm extends SistraFrontForm
{
	private String identificador;
	private int instancia;
	public String getIdentificador()
	{
		return identificador;
	}
	public void setIdentificador(String identificador)
	{
		this.identificador = identificador;
	}
	public int getInstancia()
	{
		return instancia;
	}
	public void setInstancia(int instancia)
	{
		this.instancia = instancia;
	}
}
