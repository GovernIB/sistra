package es.caib.zonaper.delega.form;


import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

import es.caib.util.NifCif;
import es.caib.zonaper.modelInterfaz.PersonaPAD;
import es.caib.zonaper.persistence.delegate.DelegacionDelegate;
import es.caib.zonaper.persistence.delegate.DelegateUtil;

public class DetalleEntidadForm extends ValidatorForm
{
	private PersonaPAD persona;
	private String modificacio;
	private String altaCorrecta;
	
	public String getTipo() {
		if (NifCif.esCIF(persona.getNif())) {
			return "CIF";
		} else {
			return "NIF";
		}
	}

	public void setTipo(String tipo) {}

	public PersonaPAD getPersona() {
		if(persona == null){
			persona = new PersonaPAD();
		}
		return persona;
	}

	public void setPersona(PersonaPAD persona) {
		this.persona = persona;
	}

	public String getModificacio() {
		return modificacio;
	}

	public void setModificacio(String modificacio) {
		this.modificacio = modificacio;
	}

	public String getHabilitada() {
		try{
			if(persona != null && !StringUtils.isBlank(persona.getNif())){
				DelegacionDelegate dele = DelegateUtil.getDelegacionDelegate();
				if(dele.habilitadaDelegacion(persona.getNif())){
					return "S";
				}else{
					return "N";
				}
			}else{
				return "N";
			}
		}catch(Exception e){
			return "N";
		}
	}

	public void setHabilitada(String habilitada) {}
	
	public String getRepresentante() {
		try{
			if(persona != null && !StringUtils.isBlank(persona.getNif())){
				DelegacionDelegate dele = DelegateUtil.getDelegacionDelegate();
				if(dele.obtenerRepresentanteEntidad(persona.getNif()) != null){
					return "S";
				}else{
					return "N";
				}
			}else{
				return "N";
			}
		}catch(Exception e){
			return "N";
		}
	}

	public void setRepresentante(String representante) {}


	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = super.validate(mapping, request);
        if (errors == null) 
        {
            errors = new ActionErrors();
        }
        return errors;
    }

	public String getAltaCorrecta() {
		return altaCorrecta;
	}

	public void setAltaCorrecta(String altaCorrecta) {
		this.altaCorrecta = altaCorrecta;
	}


}
