package es.caib.sistra.admin.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.ValidatorForm;

import es.caib.sistra.model.Tramite;
import es.caib.sistra.persistence.delegate.DelegateException;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.sistra.persistence.delegate.TramiteDelegate;


public class TramiteForm extends ValidatorForm{
	private Long idOrgano = null;
    private String[] grupos;
    private String[] gruposNoAsoc;
    private String usuario;
    private String flag;
    
    public Long getIdOrgano() {
        return this.idOrgano;
    }

    public void setIdOrgano(Long idOrgano) {
        this.idOrgano = idOrgano;
    }
    
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String[] getGrupos() {
		return grupos;
	}

	public void setGrupos(String[] grupos) {
		this.grupos = grupos;
	}

	public String[] getGruposNoAsoc() {
		return gruposNoAsoc;
	}

	public void setGruposNoAsoc(String[] gruposNoAsoc) {
		this.gruposNoAsoc = gruposNoAsoc;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) 
    {
        ActionErrors errors = super.validate(mapping, request);
        if (errors == null) 
        {
            errors = new ActionErrors();
        }
		return errors;
    }

}
