package es.caib.zonaper.helpdesk.front.form;

import org.apache.struts.validator.ValidatorForm;

public class ModificarUsuarioForm extends ValidatorForm
{	
	private String usuarioCodiOld;
	private String usuarioCodiNew;
	private String usuarioNifNew;
	
	public String getUsuarioCodiNew() {
		return usuarioCodiNew;
	}
	public void setUsuarioCodiNew(String usuarioCodiNew) {
		this.usuarioCodiNew = usuarioCodiNew;
	}
	public String getUsuarioCodiOld() {
		return usuarioCodiOld;
	}
	public void setUsuarioCodiOld(String usuarioCodiOld) {
		this.usuarioCodiOld = usuarioCodiOld;
	}
	public String getUsuarioNifNew() {
		return usuarioNifNew;
	}
	public void setUsuarioNifNew(String usuarioNifNew) {
		this.usuarioNifNew = usuarioNifNew;
	}
	

}
