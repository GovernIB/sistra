package es.caib.zonaper.delega.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

public class BusquedaEntidadesForm extends ValidatorForm
{
		
	private String entidadNif;
	
	private String entidadNombre;
		
	public String getEntidadNombre() {
		return entidadNombre;
	}
	public void setEntidadNombre(String entidadNombre) {
		this.entidadNombre = entidadNombre;
	}
	public String getEntidadNif() {
		return entidadNif;
	}
	public void setEntidadNif(String entidadNif) {
		this.entidadNif = entidadNif;
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
