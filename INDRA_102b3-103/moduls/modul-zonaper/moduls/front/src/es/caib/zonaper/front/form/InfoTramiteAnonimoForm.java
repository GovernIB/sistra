package es.caib.zonaper.front.form;

import org.apache.struts.validator.ValidatorForm;

public class InfoTramiteAnonimoForm extends ValidatorForm
{
	private String idPersistencia;

	public String getIdPersistencia()
	{
		return idPersistencia;
	}

	public void setIdPersistencia(String idPersistencia)
	{
		this.idPersistencia = idPersistencia;
	}
	
}
