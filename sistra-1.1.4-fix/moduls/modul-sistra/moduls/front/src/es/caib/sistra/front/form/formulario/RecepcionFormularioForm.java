package es.caib.sistra.front.form.formulario;

import es.caib.sistra.front.form.SistraFrontForm;

public class RecepcionFormularioForm extends SistraFrontForm
{
	private String xmlInicial;
	private String xmlActual;
	private String gstfrm;
	
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
	public String getGstfrm()
	{
		return gstfrm;
	}
	public void setGstfrm(String gstfrm)
	{
		this.gstfrm = gstfrm;
	}
}
