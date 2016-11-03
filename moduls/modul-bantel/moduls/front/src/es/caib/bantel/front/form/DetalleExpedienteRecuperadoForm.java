package es.caib.bantel.front.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;



public class DetalleExpedienteRecuperadoForm extends ValidatorForm
{
	private String numeroEntrada;
	private String identificadorExp;
	private String unidadAdm;
	private String claveExp;
	private String descripcion;
	private String idioma;
	private String usuarioSeycon;
	private String habilitarAvisos;
	private String email;
	private String movil;
	private String nombre;
	private String nif;
	
	public String getClaveExp() {
		return claveExp;
	}

	public void setClaveExp(String claveExp) {
		this.claveExp = claveExp;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHabilitarAvisos() {
		return habilitarAvisos;
	}

	public void setHabilitarAvisos(String habilitarAvisos) {
		this.habilitarAvisos = habilitarAvisos;
	}

	public String getIdentificadorExp() {
		return identificadorExp;
	}

	public void setIdentificadorExp(String identificadorExp) {
		this.identificadorExp = identificadorExp;
	}

	public String getIdioma() {
		return idioma;
	}

	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

	public String getMovil() {
		return movil;
	}

	public void setMovil(String movil) {
		this.movil = movil;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getUnidadAdm() {
		return unidadAdm;
	}

	public void setUnidadAdm(String unidadAdm) {
		this.unidadAdm = unidadAdm;
	}

	public String getUsuarioSeycon() {
		return usuarioSeycon;
	}

	public void setUsuarioSeycon(String usuarioSeycon) {
		this.usuarioSeycon = usuarioSeycon;
	}

	public String getNumeroEntrada() {
		return numeroEntrada;
	}

	public void setNumeroEntrada(String numeroEntrada) {
		this.numeroEntrada = numeroEntrada;
	}


	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = super.validate(mapping, request);
        if (errors == null) 
        {
            errors = new ActionErrors();
        }
        
        return errors;
    }
}
