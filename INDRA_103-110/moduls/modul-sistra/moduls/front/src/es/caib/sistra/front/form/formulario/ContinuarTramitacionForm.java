package es.caib.sistra.front.form.formulario;

import es.caib.sistra.front.form.SistraFrontForm;

public class ContinuarTramitacionForm extends SistraFrontForm
{
	private String gstfrm;
	private String TOKEN;

	public String getTOKEN()
	{
		return TOKEN;
	}

	public void setTOKEN(String token)
	{
		TOKEN = token;
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
