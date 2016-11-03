package es.caib.sistra.front.form;

import org.apache.struts.validator.ValidatorForm;

public abstract class SistraFrontForm extends ValidatorForm
{
	private String ID_INSTANCIA;

	public String getID_INSTANCIA()
	{
		return ID_INSTANCIA;
	}

	public void setID_INSTANCIA(String id_instancia)
	{
		ID_INSTANCIA = id_instancia;
	}
	
}
