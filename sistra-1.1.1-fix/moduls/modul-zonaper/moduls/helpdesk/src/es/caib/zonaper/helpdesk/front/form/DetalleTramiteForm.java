package es.caib.zonaper.helpdesk.front.form;

import org.apache.struts.validator.ValidatorForm;

public class DetalleTramiteForm extends ValidatorForm
{
	private String claveTramite;

	public String getClaveTramite()
	{
		return claveTramite;
	}

	public void setClaveTramite(String claveTramite)
	{
		this.claveTramite = claveTramite;
	}

}
