package es.caib.sistra.persistence.plugins;

import java.io.Serializable;
import java.security.Principal;

import org.apache.commons.lang.StringUtils;

import es.caib.sistra.model.DatosSesion;
import es.caib.sistra.plugins.login.ConstantesLogin;
import es.caib.zonaper.modelInterfaz.PersonaPAD;

/**
 * 
 * Clase para que los scripts accedan a datos de la sesión
 *
 * Si existe delegacion se mostraran los datos de la entidad delegada
 *
 */
public class PluginDatosSesion implements Serializable{

	private String idTramitacion;
	private DatosSesion datosSesion;
	private PersonaPAD persona;
	
	public void setDatosSesion(String pIdTramitacion, DatosSesion datosSesion) {
		this.idTramitacion = pIdTramitacion;
		this.datosSesion = datosSesion;
		if (datosSesion.getNivelAutenticacion() != ConstantesLogin.LOGIN_ANONIMO){		
			this.persona = datosSesion.getPersonaPAD();			
		}else{
			// Para anonimos creamos persona vacia para q no hacerlo null safe
			persona = new PersonaPAD();
		}
		
	}
	
	public String getCodigoUsuario() {
		return this.persona.getUsuarioSeycon();
	}
	
	public String getNifUsuario() {
		return this.persona.getNif();
	}
	
	public String getNivelAutenticacion() {
		return Character.toString(datosSesion.getNivelAutenticacion());
	}
	
	public String getNombreCompletoUsuario() {
		return this.persona.getNombreCompleto();
	}
	
	public String getNombreUsuario() {
		return this.persona.getNombre();
	}
	
	public String getApellido1Usuario(){
		return (StringUtils.isNotEmpty(this.persona.getApellido1())?this.persona.getApellido1():"");
	}
	
	public String getApellido2Usuario(){
		return (StringUtils.isNotEmpty(this.persona.getApellido2())?this.persona.getApellido2():"");
	}
	
	public String getIdioma() {
		return datosSesion.getLocale().getLanguage();
	}

	public String getEmail() {
		return this.persona.getEmail();
	}
	
	public String getTelefonoMovil() {
		return this.persona.getTelefonoMovil();
	}
	
	public String getCodigoPostal() {
		return this.persona.getCodigoPostal();
	}
	
	public String getDireccion() {
		return this.persona.getDireccion();
	}
	public String getMunicipio() {
		return this.persona.getMunicipio();
	}
	public String getProvincia() {
		return this.persona.getProvincia();
	}
	public String getTelefonoFijo() {
		return this.persona.getTelefonoFijo();
	}
	
	public String getHabilitarAvisos() {
		return Boolean.toString(this.persona.isHabilitarAvisosExpediente());
	}
	
	public String getNifDelegado() {
		return this.datosSesion.getNifDelegado();
	}
	
	public String getNombreCompletoDelegado() {
		return this.datosSesion.getNombreCompletoDelegado();
	}
	
	public String getNombreDelegado() {
		return this.datosSesion.getNombreDelegado();
	}
	
	public String getApellido1Delegado(){
		return (StringUtils.isNotEmpty(this.datosSesion.getApellido1Delegado())?this.datosSesion.getApellido1Delegado():"");
	}
	
	public String getApellido2Delegado(){
		return (StringUtils.isNotEmpty(this.datosSesion.getApellido2Delegado())?this.datosSesion.getApellido2Delegado():"");
	}
	
	public String getPerfilAcceso(){
		return this.datosSesion.getPerfilAcceso();
	}

	public String getIdTramitacion() {
		return idTramitacion;
	}
	
	public String getRepresentanteNifCertificado() {
		String res = "";
		if (this.datosSesion.getDatosRepresentanteCertificado() != null) {
			res = this.datosSesion.getDatosRepresentanteCertificado().getNif();
		}
		return res;
	}
	
	public String getNombreRepresentanteCertificado() {
		String res = "";
		if (this.datosSesion.getDatosRepresentanteCertificado() != null) {
			res = this.datosSesion.getDatosRepresentanteCertificado().getNombre();
		}
		return res;
	}
	
	public String getApellido1RepresentanteCertificado() {
		String res = "";
		if (this.datosSesion.getDatosRepresentanteCertificado() != null) {
			res = this.datosSesion.getDatosRepresentanteCertificado().getApellido1();
		}
		return res;
	}
	
	public String getApellido2RepresentanteCertificado() {
		String res = "";
		if (this.datosSesion.getDatosRepresentanteCertificado() != null) {
			res = this.datosSesion.getDatosRepresentanteCertificado().getApellido2();
		}
		return res;
	}
	
	
}
