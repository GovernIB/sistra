package es.caib.zonaper.helpdesk.front.form;

public class DetallePagoForm extends InitForm
{
	private Long codigo;
	private String clave;
	private String idioma;
	public String getClave() {
		return clave;
	}
	public void setClave(String claveRDS) {
		this.clave = claveRDS;
	}
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigoRDS) {
		this.codigo = codigoRDS;
	}
	public String getIdioma() {
		return idioma;
	}
	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}


}
