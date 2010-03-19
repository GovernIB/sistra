package es.caib.mobtratel.front.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

import es.caib.mobtratel.front.taglib.Constants;

public class EditarEnvioSmsForm extends ValidatorForm
{
	private String nombre;
	private String mensaje;
	private String destinatarios;
	private String cuenta;
	private String fechaProgramacion;
	private String fechaCaducidad;
	private String horaProgramacion;
	private String horaCaducidad;
	private String inmediato="N";
	



	public String getInmediato() {
		return inmediato;
	}





	public void setInmediato(String inmediato) {
		this.inmediato = inmediato;
	}





	public String getHoraCaducidad() {
		return horaCaducidad;
	}





	public void setHoraCaducidad(String horaCaducidad) {
		this.horaCaducidad = horaCaducidad;
	}





	public String getHoraProgramacion() {
		return horaProgramacion;
	}





	public void setHoraProgramacion(String horaProgramacion) {
		this.horaProgramacion = horaProgramacion;
	}





	public String getCuenta() {
		return cuenta;
	}





	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}





	public String getDestinatarios() {
		return destinatarios;
	}





	public void setDestinatarios(String destinatarios) {
		this.destinatarios = destinatarios;
	}





	public String getFechaCaducidad() {
		return fechaCaducidad;
	}





	public void setFechaCaducidad(String fechaCaducidad) {
		this.fechaCaducidad = fechaCaducidad;
	}





	public String getFechaProgramacion() {
		return fechaProgramacion;
	}





	public void setFechaProgramacion(String fechaProgramacion) {
		this.fechaProgramacion = fechaProgramacion;
	}





	public String getMensaje() {
		return mensaje;
	}





	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}










	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = super.validate(mapping, request);
        if (errors == null) 
        {
            errors = new ActionErrors();
        }
        String nombre = getNombre();
        String mensaje = getMensaje();
        String cuenta = getCuenta();
        String destinatarios = getDestinatarios();
     
        
    	// Comprobamos que no exista otro permiso con ese código
    	if (  request.getParameter(Constants.ALTA_PROPERTY) != null  )
    	{
            if((mensaje == null) || (mensaje.equals("")))
            {
            	errors.add("mensaje", new ActionError("errors.mensaje"));
            }
            if((cuenta == null) || (cuenta.equals("")))
            {
            	errors.add("cuenta", new ActionError("errors.cuenta"));
            }
            if((nombre == null) || (nombre.equals("")))
            {
            	errors.add("nombre", new ActionError("errors.nombre"));
            }
            
            if((destinatarios == null) || (destinatarios.equals("")))
            {
            	errors.add("destinatarios", new ActionError("errors.noDestinatarios"));
            }
            
    	}

    	
        return errors;
    }
	
	private void reset()
	{
		this.setDestinatarios( null );
		this.setFechaCaducidad( null );
		this.setFechaProgramacion( null );
		this.setMensaje( null );
		this.setNombre( null );
	}
	
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		reset();
        super.reset(mapping, request);
    }
	
	public void destroy(ActionMapping mapping, HttpServletRequest request) {
		reset();
    }





	public String getNombre() {
		return nombre;
	}





	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
