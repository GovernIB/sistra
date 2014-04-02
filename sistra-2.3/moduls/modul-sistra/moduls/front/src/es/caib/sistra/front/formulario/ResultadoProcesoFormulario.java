package es.caib.sistra.front.formulario;

import java.io.Serializable;

import es.caib.sistra.model.DocumentoFront;

public class ResultadoProcesoFormulario implements Serializable
{
	private DocumentoFront formulario;
	private String xmlInicial;
	private String xmlActual;
	private boolean guardadoSinFinalizar;
	
	public String getXmlActual()
	{
		return xmlActual;
	}
	public void setXmlActual(String xmlActual)
	{
		this.xmlActual = xmlActual;
	}
	public String getXmlInicial()
	{
		return xmlInicial;
	}
	public void setXmlInicial(String xmlInicial)
	{
		this.xmlInicial = xmlInicial;
	}
	public DocumentoFront getFormulario()
	{
		return formulario;
	}
	public void setFormulario(DocumentoFront formulario)
	{
		this.formulario = formulario;
	}
	public boolean isGuardadoSinFinalizar() {
		return guardadoSinFinalizar;
	}
	public void setGuardadoSinFinalizar(boolean guardadoSinFinalizar) {
		this.guardadoSinFinalizar = guardadoSinFinalizar;
	}
	
}
