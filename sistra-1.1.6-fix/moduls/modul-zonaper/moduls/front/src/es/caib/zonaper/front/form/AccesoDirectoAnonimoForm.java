package es.caib.zonaper.front.form;

import org.apache.struts.validator.ValidatorForm;

public class AccesoDirectoAnonimoForm extends ValidatorForm
{
	private String idPersistencia;
	
	private long codigo;
	
	private String tipo;

	public String getIdPersistencia()
	{
		return idPersistencia;
	}

	public void setIdPersistencia(String idPersistencia)
	{
		this.idPersistencia = idPersistencia;
	}

	public long getCodigo() {
		return codigo;
	}

	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
}
