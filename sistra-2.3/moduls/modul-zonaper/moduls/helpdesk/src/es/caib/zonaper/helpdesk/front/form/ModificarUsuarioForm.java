package es.caib.zonaper.helpdesk.front.form;

import org.apache.struts.validator.ValidatorForm;

public class ModificarUsuarioForm extends ValidatorForm
{	
	private String usuarioCodiOld;
	private String usuarioCodiNew;
	private String usuarioNifNew;
	private String usuarioNombreNew;
	private String usuarioApellido1New;
	private String usuarioApellido2New;
	
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
	public String getUsuarioNombreNew() {
		return usuarioNombreNew;
	}
	public void setUsuarioNombreNew(String usuarioNombreNew) {
		this.usuarioNombreNew = usuarioNombreNew;
	}
	public String getUsuarioApellido1New() {
		return usuarioApellido1New;
	}
	public void setUsuarioApellido1New(String usuarioApellido1New) {
		this.usuarioApellido1New = usuarioApellido1New;
	}
	public String getUsuarioApellido2New() {
		return usuarioApellido2New;
	}
	public void setUsuarioApellido2New(String usuarioApellido2New) {
		this.usuarioApellido2New = usuarioApellido2New;
	}
	

}
