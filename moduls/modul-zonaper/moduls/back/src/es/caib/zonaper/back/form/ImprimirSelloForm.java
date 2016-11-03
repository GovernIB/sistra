package es.caib.zonaper.back.form;

import org.apache.struts.validator.ValidatorForm;

public class ImprimirSelloForm extends ValidatorForm
{
	private String codigo;

	public String getCodigo()
	{
		return codigo;
	}

	public void setCodigo(String codigo)
	{
		this.codigo = codigo;
	}
		
}
