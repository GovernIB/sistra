package es.caib.bantel.front.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

import es.caib.util.StringUtil;


public class ConfirmacionForm extends ValidatorForm
{
	private String numeroPreregistro;
	private String numeroRegistro;
	private String fechaRegistro;
	
	public String getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(String fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	public String getNumeroRegistro() {
		return numeroRegistro;
	}
	public void setNumeroRegistro(String numeroRegistro) {
		this.numeroRegistro = numeroRegistro;
	}
	public String getNumeroPreregistro() {
		return numeroPreregistro;
	}
	public void setNumeroPreregistro(String numeroPreregistro) {
		this.numeroPreregistro = numeroPreregistro;
	}
	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = super.validate(mapping, request);
        if (errors == null) 
        {
            errors = new ActionErrors();
        }
              
    	// Comprobamos formato fecha registro
    	if (StringUtils.isNotEmpty(getFechaRegistro())){
    		Date fecha =StringUtil.cadenaAFecha(getFechaRegistro()+":00","dd/MM/yyyy HH:mm:ss");
    		if (fecha == null){
    			errors.add("fechaRegistro", new ActionError("errors.date","Fecha registro"));
    		}
    	}
    	
    	// Se debe introducir los dos parametros de registro o ninguno
    	if (
    			(StringUtils.isNotEmpty(getFechaRegistro()) && (StringUtils.isEmpty(getNumeroRegistro())))
    			||
    			(StringUtils.isNotEmpty(getNumeroRegistro()) && (StringUtils.isEmpty(getFechaRegistro())))
    		){    		
    			errors.add("numeroRegistro", new ActionError("errors.numeroYFechaRegistro"));    		
    	}
    	
        return errors;
    }
	
}
