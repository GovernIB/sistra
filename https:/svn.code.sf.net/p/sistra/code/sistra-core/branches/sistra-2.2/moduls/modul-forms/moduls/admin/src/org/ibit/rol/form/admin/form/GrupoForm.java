package org.ibit.rol.form.admin.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;
import org.ibit.rol.form.persistence.delegate.DelegateException;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.ibit.rol.form.persistence.delegate.GruposDelegate;

public class GrupoForm extends ValidatorForm {
	
	private String codigo;
	private String nombre;
	private String descripcion;
	private String flagValidacion;
	private String usuario;

	public String getFlagValidacion() {
		return flagValidacion;
	}

	public void setFlagValidacion(String flagValidacion) {
		this.flagValidacion = flagValidacion;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
    }

    public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = super.validate(mapping, request);
        boolean error = false;
        if (errors == null) {
            errors = new ActionErrors();
        }
        if(StringUtils.isBlank(codigo)){
        	errors.add("grupo", new ActionError("errors.required","Código"));
        	error = true;
        }
        if(StringUtils.isBlank(nombre)){
        	errors.add("grupo", new ActionError("errors.required","Nombre"));
        	error = true;
        }
        if(error){
        	GruposDelegate gruposDelegate = DelegateUtil.getGruposDelegate();
        	List usuarios = null;
        	if(StringUtils.isBlank(codigo))
        		usuarios = (List) request.getSession().getAttribute("usuarios");
        	else{
        		try {
					usuarios = gruposDelegate.obtenerUsuariosByGrupo(codigo);
				} catch (DelegateException e) {
					e.printStackTrace();
				}	
        	}
        	request.setAttribute("usuarios",usuarios);
        	request.getSession().setAttribute("error", error);
        }
        return errors;
    }

	public void destroy(ActionMapping mapping, HttpServletRequest request) {
		// TODO Auto-generated method stub
		
	}

}
