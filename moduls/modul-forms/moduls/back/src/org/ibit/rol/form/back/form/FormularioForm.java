package org.ibit.rol.form.back.form;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionError;
import org.apache.struts.upload.FormFile;
import org.ibit.rol.form.model.Formulario;
import org.ibit.rol.form.model.FormularioSeguro;
import org.ibit.rol.form.persistence.delegate.DelegateException;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;

import javax.servlet.http.HttpServletRequest;

public class FormularioForm extends TraduccionValidatorForm {

    private transient FormFile logotipo1;
    private transient FormFile logotipo2;
    private transient FormFile plantilla;
    private String temp;
    private Long[] validadores_ids;
    private String rolesString;
    private String[] grupos;
    private String[] gruposNoAsoc;
    private String usuario;
    private String flag;

    
    // -- INDRA: MODOS FUNCIONAMIENTO
    private Long modoFuncionamientoCod = new Long(1);
    // -- INDRA: FIN
    
    public FormFile getLogotipo1() {
        return logotipo1;
    }

    public void setLogotipo1(FormFile logotipo1) {
        this.logotipo1 = logotipo1;
    }

    public FormFile getLogotipo2() {
        return logotipo2;
    }

    public void setLogotipo2(FormFile logotipo2) {
        this.logotipo2 = logotipo2;
    }

    public FormFile getPlantilla() {
        return plantilla;
    }

    public void setPlantilla(FormFile plantilla) {
        this.plantilla = plantilla;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

     public Long[] getValidadores_ids() {
        return validadores_ids;
    }

    public void setValidadores_ids(Long[] validadores_ids) {
        this.validadores_ids = validadores_ids;
    }

    public String getRolesString() {
        return rolesString;
    }

    public void setRolesString(String rolesString) {
        this.rolesString = rolesString;
    }

    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        //perque el select multiple s'enteri que no s'ha seleccionat cap
        setValidadores_ids(new Long[0]);
        if (mapping.getPath().endsWith("alta") || mapping.getPath().endsWith("editar")) {
            Formulario formulario = (Formulario) getValues();
            formulario.setHasBarcode(false);
            if( formulario instanceof FormularioSeguro){
                FormularioSeguro formSeguro = (FormularioSeguro)getValues();
                formSeguro.setHttps(false);
                formSeguro.setRequerirCertificado(false);
                formSeguro.setRequerirFirma(false);
                formSeguro.setRequerirLogin(false);
            }
        }

    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = super.validate(mapping, request);
        if (errors == null) {
            errors = new ActionErrors();
        }
        if(StringUtils.isEmpty(flag)){
        try {
            Formulario formulario = (Formulario) getValues();

            // Comprovar que el modelo no existe.
            String modelo = formulario.getModelo();
            int version = formulario.getVersion();
            Formulario formAux = DelegateUtil.getFormularioDelegate().obtenerFormulario(modelo,version);
            if (formAux != null && !formAux.getId().equals(formulario.getId())) {
                errors.add("modelo", new ActionError("errors.formulario.duplicado", modelo));
            }

        } catch (DelegateException e) {
            log.error(e);
        }
        }
        return errors;
    }

	public Long getModoFuncionamientoCod() {
		return modoFuncionamientoCod;
	}

	public void setModoFuncionamientoCod(Long modoFuncionamiento) {
		this.modoFuncionamientoCod = modoFuncionamiento;
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

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}   
	
	

}
