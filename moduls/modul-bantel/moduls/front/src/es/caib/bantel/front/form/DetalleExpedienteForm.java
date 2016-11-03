package es.caib.bantel.front.form;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

import es.caib.bantel.front.util.MensajesUtil;
import es.caib.util.ValidacionesUtil;
import es.caib.zonaper.modelInterfaz.ConstantesZPE;



public class DetalleExpedienteForm extends ValidatorForm
{
	private String numeroEntrada;
	private String identificadorExp;
	private String claveExp;
	private String identificadorProcedimiento;
	private String descripcion;
	private String idioma;
	private String usuarioSeycon;
	private String habilitarAvisos;
	private String email;
	private String movil;
	private String nombre;
	private String nif;
	/*tipo si el valor es A significa alta expediente, 
	 * si el valor es E alta expediente desde entrada*/
	private String tipo;
	private String flagValidacion;
	
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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public String getFlagValidacion() {
		return flagValidacion;
	}

	public void setFlagValidacion(String flagValidacion) {
		this.flagValidacion = flagValidacion;
	}
	

	public String getIdentificadorProcedimiento() {
		return identificadorProcedimiento;
	}

	public void setIdentificadorProcedimiento(String identificadorProcedimiento) {
		this.identificadorProcedimiento = identificadorProcedimiento;
	}
	
	/**
	 * Método que valida el formulario. Solo se comprueban los casos en caso de la alta de expediente o cuando se selecciona que tipo de alta 
	 * se realizará
	 */
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = super.validate(mapping, request);
        boolean error = false;
        if (errors == null) 
        {
            errors = new ActionErrors();
        }
        
        
        if("E".equals(tipo)){
        	if(StringUtils.isEmpty(numeroEntrada)){
        		errors.add("altaExpediente", new ActionError("errors.numeroEntrada.vacio"));
        	}
        }
        if(StringUtils.isNotEmpty(flagValidacion) && flagValidacion.equals("altaExpedient")){
        	if(StringUtils.isEmpty(identificadorProcedimiento)){
        		errors.add("altaExpediente", new ActionError("errors.required", MensajesUtil.getValue("expediente.identificadorProcedimiento", request)));
        		error = true;
        	}
        	if(StringUtils.isEmpty(identificadorExp)){
        		errors.add("altaExpediente", new ActionError("errors.required", MensajesUtil.getValue("confirmacion.identificadorExpediente", request)));
        		error = true;
        	}
        	
        	if (StringUtils.isNotEmpty(identificadorExp) && !Pattern.matches(ConstantesZPE.REGEXP_IDENTIFICADOREXPEDIENTE, identificadorExp)) {
        		errors.add("altaExpediente", new ActionError("errors.invalid", MensajesUtil.getValue("confirmacion.identificadorExpediente", request)));
        		error = true;
        	}
        	
        	/* 
        	 * PASA A GENERARSE AUTOMATICAMENTE DESDE MODULO DE BANTELFRONT
        	if(StringUtils.isEmpty(claveExp)){
        		errors.add("altaExpediente", new ActionError("errors.required", MensajesUtil.getValue("confirmacion.claveExpediente")));
        		error = true;
        	}
        	*/
        	if(StringUtils.isEmpty(nif)){
    			errors.add("altaExpediente", new ActionError("errors.required", MensajesUtil.getValue("expediente.nif", request)));
        		error = true;
    		}
    		if(StringUtils.isEmpty(nombre)){
    			errors.add("altaExpediente", new ActionError("errors.required", MensajesUtil.getValue("expediente.nombre", request)));
        		error = true;
    		}
    		if(StringUtils.isEmpty(descripcion)){
        		errors.add("altaExpediente", new ActionError("errors.required", MensajesUtil.getValue("expediente.descripcion", request)));
        		error = true;
        	}
        	if(StringUtils.isNotEmpty(email) && !ValidacionesUtil.validarEmail(email)){
        		errors.add("altaExpediente", new ActionError("error.expediente.emailNOK"));
        		error = true;
        	}
        	if(StringUtils.isNotEmpty(movil) && !ValidacionesUtil.validarMovil(movil)){
        		errors.add("altaExpediente", new ActionError("error.expediente.movilNOK"));
        		error = true;
        	}
        	if ("S".equals(habilitarAvisos) && StringUtils.isEmpty(email)) {
        		errors.add("altaExpediente", new ActionError("error.expediente.emailObligatorio"));
        		error = true;
        	}
        	
        	if(StringUtils.isNotEmpty(descripcion) && descripcion.length() > 500){
        		errors.add("altaExpediente", new ActionError("errors.maxlength", MensajesUtil.getValue("expediente.descripcion", request), "500"));
        	}
        	
        }
        
        if(StringUtils.isNotEmpty(flagValidacion) && flagValidacion.equals("consulta")){
        	if(StringUtils.isEmpty(identificadorExp)){
        		errors.add("altaExpediente", new ActionError("errors.required", MensajesUtil.getValue("confirmacion.identificadorExpediente", request)));
        		error = true;
        	}        	
        }
        
        return errors;
    }

}