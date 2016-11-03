package es.caib.zonaper.filter.front.form;

import org.apache.struts.validator.ValidatorForm;

import es.caib.zonaper.modelInterfaz.ConstantesZPE;

public class ElegirPerfilAccesoPADForm extends ValidatorForm
{
	private String perfil = ConstantesZPE.DELEGACION_PERFIL_ACCESO_CIUDADANO;
	private String urlOriginal;
	private String nifEntidad;
	
	public String getPerfil() {
		return perfil;
	}
	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}
	public String getUrlOriginal() {
		return urlOriginal;
	}
	public void setUrlOriginal(String urlOriginal) {
		this.urlOriginal = urlOriginal;
	}
	public String getNifEntidad() {
		return nifEntidad;
	}
	public void setNifEntidad(String usuarioEntidad) {
		this.nifEntidad = usuarioEntidad;
	}


}
