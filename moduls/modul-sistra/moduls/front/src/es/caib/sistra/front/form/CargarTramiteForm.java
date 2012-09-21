package es.caib.sistra.front.form;

public class CargarTramiteForm extends SistraFrontForm
{
	private String idPersistencia;
	
	private String language;
	
	public String getIdPersistencia()
	{
		return idPersistencia;
	}
	public void setIdPersistencia(String idPersistencia)
	{
		this.idPersistencia = idPersistencia;
	}
	
	public String getLanguage() {
		return language;
	}
	
	public void setLanguage(String language) {
		this.language = language;
	}
}
