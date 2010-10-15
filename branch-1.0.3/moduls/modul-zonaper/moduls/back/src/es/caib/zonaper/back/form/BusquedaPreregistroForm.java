package es.caib.zonaper.back.form;


public class BusquedaPreregistroForm extends DetallePreregistroForm
{
	private String numeroPreregistro;
	private String digitoControl;
	
	private Long codigoSello;


				   
	public String getNumeroPreregistro()
	{
		return numeroPreregistro;
	}

	public void setNumeroPreregistro(String numeroPreregistro)
	{
		this.numeroPreregistro = numeroPreregistro;
	}

	public String getDigitoControl() {
		return digitoControl;
	}

	public void setDigitoControl(String digitoControl) {
		this.digitoControl = digitoControl;
	}

	public Long getCodigoSello() {
		return codigoSello;
	}

	public void setCodigoSello(Long codigoSello) {
		this.codigoSello = codigoSello;
	}
}
