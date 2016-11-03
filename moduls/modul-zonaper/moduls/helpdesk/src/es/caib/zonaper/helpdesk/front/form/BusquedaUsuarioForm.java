package es.caib.zonaper.helpdesk.front.form;

import org.apache.struts.validator.ValidatorForm;

public class BusquedaUsuarioForm extends ValidatorForm
{
		
	private String usuarioNif;
	private String usuarioCodi;
	
	public String getUsuarioCodi() {
		return usuarioCodi;
	}
	public void setUsuarioCodi(String usuarioCodi) {
		this.usuarioCodi = usuarioCodi;
	}
	public String getUsuarioNif() {
		return usuarioNif;
	}
	public void setUsuarioNif(String usuarioNif) {
		this.usuarioNif = usuarioNif;
	}
	
	

}
