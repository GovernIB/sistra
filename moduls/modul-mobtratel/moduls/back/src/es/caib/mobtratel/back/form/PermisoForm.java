package es.caib.mobtratel.back.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import es.caib.mobtratel.back.taglib.Constants;
import es.caib.mobtratel.model.Permiso;
import es.caib.mobtratel.persistence.delegate.DelegateException;
import es.caib.mobtratel.persistence.delegate.DelegateUtil;
import es.caib.mobtratel.persistence.delegate.PermisoDelegate;



public class PermisoForm extends MobtratelForm implements InitForm
{
	protected static Log log = LogFactory.getLog(PermisoForm.class);
	
	private String[] cuentas;
	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) 
	{
        ActionErrors errors = super.validate(mapping, request);
        if (errors == null) 
        {
            errors = new ActionErrors();
        }
        try 
        {
        	Permiso permiso = ( Permiso ) this.getValues();
        	
        	
        	
        	// Comprobamos que no exista otro permiso con ese código
        	if (  request.getParameter(Constants.ALTA_PROPERTY) != null  ) {
        		PermisoDelegate delegate = DelegateUtil.getPermisoDelegate();
        		Permiso permisoTmp = delegate.obtenerPermiso( permiso.getUsuarioSeycon(), cuentas[0] );
		    	if ( permisoTmp != null ) 		    	{
		    		errors.add("values.identificador", new ActionError("errors.permiso.duplicado", permiso.getUsuarioSeycon() ));
		    	} 
        	}
        	// Comprobamos que no exista otro permiso con ese código
        	if (  request.getParameter(Constants.MODIFICACIO_PROPERTY) != null  ) {
        		PermisoDelegate delegate = DelegateUtil.getPermisoDelegate();
        		Permiso permisoTmp = delegate.obtenerPermiso( permiso.getUsuarioSeycon(), cuentas[0] );
		    	if ( (permisoTmp != null) && (permisoTmp.getCodigo().compareTo(permiso.getCodigo()) != 0) ){
		    		errors.add("values.identificador", new ActionError("errors.permiso.duplicado", permiso.getUsuarioSeycon(), cuentas[0]));
		    	} 
        	}
        
        }
	    catch (DelegateException e) 
	    {
	        log.error(e);
	    }	   
        
        
	    return errors;

	}
	
	public void reset( ActionMapping mapping, HttpServletRequest request )
	{
		this.setCuentas( null );
		super.reset( mapping, request );
	}

	public String[] getCuentas() {
		return cuentas;
	}

	public void setCuentas(String[] cuentas) {
		this.cuentas = cuentas;
	}

	
}
