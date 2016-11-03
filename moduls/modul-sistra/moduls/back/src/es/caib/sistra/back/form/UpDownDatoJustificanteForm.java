package es.caib.sistra.back.form;

import org.apache.struts.validator.ValidatorForm;

public class UpDownDatoJustificanteForm extends ValidatorForm
{
	private Long idEspecTramiteNivel;
	private Long idTramiteNivel;
	private Long codigo;
	
	public Long getIdEspecTramiteNivel()
	{
		return idEspecTramiteNivel;
	}
	
	public void setIdEspecTramiteNivel(Long idEspecTramiteNivel)
	{
		this.idEspecTramiteNivel = idEspecTramiteNivel;
	}
	
	public Long getIdTramiteNivel()
	{
		return idTramiteNivel;
	}


	public void setIdTramiteNivel(Long idTramiteNivel)
	{
		this.idTramiteNivel = idTramiteNivel;
	}

	public Long getCodigo()
	{
		return codigo;
	}

	public void setCodigo(Long codigo)
	{
		this.codigo = codigo;
	}
}
