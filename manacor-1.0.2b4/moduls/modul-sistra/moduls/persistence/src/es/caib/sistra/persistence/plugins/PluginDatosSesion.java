package es.caib.sistra.persistence.plugins;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import es.caib.sistra.model.DatosSesion;

/**
 * Clase para que los scripts accedan a datos de la sesión
 *
 */
public class PluginDatosSesion implements Serializable{

	DatosSesion datosSesion;
	public void setDatosSesion(DatosSesion datosSesion) {
		this.datosSesion = datosSesion;
	}
	
	public String getCodigoUsuario() {
		return datosSesion.getCodigoUsuario();
	}
	
	public String getNifUsuario() {
		return datosSesion.getNifUsuario();
	}
	
	public char getNivelAutenticacion() {
		return datosSesion.getNivelAutenticacion();
	}
	
	public String getNombreCompletoUsuario() {
		return 
			datosSesion.getNombreCompletoUsuario();
	}
	
	public String getNombreUsuario() {
		return datosSesion.getNombreUsuario();
	}
	
	public String getApellido1Usuario(){
		return (StringUtils.isNotEmpty(datosSesion.getApellido1Usuario())?datosSesion.getApellido1Usuario():"");
	}
	
	public String getApellido2Usuario(){
		return (StringUtils.isNotEmpty(datosSesion.getApellido2Usuario())?datosSesion.getApellido2Usuario():"");
	}
	
	public String getIdioma() {
		return datosSesion.getLocale().getLanguage();
	}

	public String getEmail() {
		return datosSesion.getEmail();
	}
	
	public String getTelefonoMovil() {
		return datosSesion.getTelefonoMovil();
	}
	
	public String getCodigoPostal() {
		return datosSesion.getCodigoPostal();
	}
	
	public String getDireccion() {
		return datosSesion.getDireccion();
	}
	public String getMunicipio() {
		return datosSesion.getMunicipio();
	}
	public String getProvincia() {
		return datosSesion.getProvincia();
	}
	public String getTelefonoFijo() {
		return datosSesion.getTelefonoFijo();
	}
	
	public String getHabilitarAvisos() {
		return Boolean.toString(datosSesion.getHabilitarAvisos());
	}
	
}
