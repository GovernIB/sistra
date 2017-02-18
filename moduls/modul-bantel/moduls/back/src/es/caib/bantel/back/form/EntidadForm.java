package es.caib.bantel.back.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import es.caib.bantel.back.taglib.Constants;
import es.caib.bantel.model.Entidad;
import es.caib.bantel.model.Procedimiento;
import es.caib.bantel.persistence.delegate.DelegateException;
import es.caib.bantel.persistence.delegate.DelegateUtil;
import es.caib.bantel.persistence.delegate.EntidadDelegate;
import es.caib.bantel.persistence.delegate.ProcedimientoDelegate;


public class EntidadForm extends BantelForm implements InitForm
{

	protected static Log log = LogFactory.getLog(EntidadForm.class);
	
	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) 
	{		
        ActionErrors errors = super.validate(mapping, request);
        
       
        if (errors == null) 
        {
            errors = new ActionErrors();
        }
        try 
        {
        	Entidad entidad = ( Entidad ) this.getValues();
        	
        	if (StringUtils.isBlank(entidad.getIdentificador()) ){
        		errors.add("values.identificador", new ActionError("errors.required", "Codigo"));        	
    		}
        	
        	if (StringUtils.isBlank(entidad.getDescripcion()) ){
        		errors.add("values.descripcion", new ActionError("errors.required", "Descripcion"));        	
    		}
        	        	
        	// Comprobamos que no exista otro con ese código
        	if (request.getParameter(Constants.ALTA_PROPERTY) != null  ) {
		    	EntidadDelegate delegate = DelegateUtil.getEntidadDelegate();
		    	Entidad tmp = delegate.obtenerEntidad( entidad.getIdentificador() );
		    	if ( tmp != null ) 		    	{
		    		errors.add("values.identificador", new ActionError("errors.entidad.duplicado", entidad.getIdentificador() ));
		    	} 
        	}
        
        }
	    catch (DelegateException e) 
	    {
	        log.error(e);
	    }	   
        
	    return errors;

	}

}
