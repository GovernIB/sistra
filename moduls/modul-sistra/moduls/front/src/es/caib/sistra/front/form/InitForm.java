package es.caib.sistra.front.form;


public class InitForm extends SistraFrontForm
{
	private String modelo;
	private int version;
	private String language;
	private String idPersistencia;
	
	public String getModelo()
	{
		return modelo;
	}
	public void setModelo(String modelo)
	{
		this.modelo = modelo;
	}
	public int getVersion()
	{
		return version;
	}
	public void setVersion(int version)
	{
		this.version = version;
	}
	public String getLanguage()
	{
		return language;
	}
	public void setLanguage(String locale)
	{
		this.language = locale;
	}
	public String getIdPersistencia()
	{
		return idPersistencia;
	}
	public void setIdPersistencia(String idPersistencia)
	{
		this.idPersistencia = idPersistencia;
	}
}
