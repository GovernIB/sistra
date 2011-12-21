package es.caib.zonaper.front.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

import es.caib.util.ValidacionesUtil;


public class ActualizarDatosPersonalesForm extends ValidatorForm
{
	private String tipoPersona; // NIF(Fisica) / CIF (Juridica)
	private String nif;
	private String nombre;
	private String apellido1;
	private String apellido2;
	private String direccion;
    private String codigoPostal;
    private String provincia;
    private String municipio;
    private String telefonoFijo;
    private String telefonoMovil;
    private String email;
    
    public String getTipoPersona() {
		return tipoPersona;
	}
	public void setTipoPersona(String tipoPersona) {
		this.tipoPersona = tipoPersona;
	}
	public String getApellido1() {
		return apellido1;
	}
	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}
	public String getApellido2() {
		return apellido2;
	}
	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}
	public String getCodigoPostal() {
		return codigoPostal;
	}
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMunicipio() {
		return municipio;
	}
	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public String getTelefonoFijo() {
		return telefonoFijo;
	}
	public void setTelefonoFijo(String telefonoFijo) {
		this.telefonoFijo = telefonoFijo;
	}
	public String getTelefonoMovil() {
		return telefonoMovil;
	}
	public void setTelefonoMovil(String telefonoMovil) {
		this.telefonoMovil = telefonoMovil;
	}
	public String getNif() {
		return nif;
	}
	public void setNif(String nif) {
		this.nif = nif;
	}
	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = super.validate(mapping, request);
        if (errors == null) {
            errors = new ActionErrors();
        }

        // Validacion email
        if (StringUtils.isNotEmpty(getEmail())){
        	if (!ValidacionesUtil.validarEmail(getEmail())){
        		errors.add("email", new ActionError("actualizarDatosPersonales.error.mail"));
        	}
        }
        
        // Validacion movil
        if (StringUtils.isNotEmpty(getTelefonoMovil())){
        	if (!ValidacionesUtil.validarMovil(getTelefonoMovil())){
        		errors.add("email", new ActionError("actualizarDatosPersonales.error.movil"));
        	}
        }

        return errors;
    }
	
}
