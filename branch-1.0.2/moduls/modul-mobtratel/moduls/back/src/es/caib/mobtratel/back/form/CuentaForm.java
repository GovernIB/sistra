package es.caib.mobtratel.back.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import es.caib.mobtratel.back.taglib.Constants;
import es.caib.mobtratel.model.Cuenta;
import es.caib.mobtratel.persistence.delegate.CuentaDelegate;
import es.caib.mobtratel.persistence.delegate.DelegateException;
import es.caib.mobtratel.persistence.delegate.DelegateUtil;




public class CuentaForm extends MobtratelForm implements InitForm
{

	protected static Log log = LogFactory.getLog(CuentaForm.class);
	
	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) 
	{		
        ActionErrors errors = super.validate(mapping, request);
        
       
        if (errors == null) 
        {
            errors = new ActionErrors();
        }
        try 
        {
        	Cuenta cuenta = ( Cuenta ) this.getValues();
        	
        	
	    	CuentaDelegate delegate = DelegateUtil.getCuentaDelegate();
	    	Cuenta cuentaTmp = null;
        	// Comprobamos que no exista otra cuenta con ese código
        	if (  request.getParameter(Constants.ALTA_PROPERTY) != null  ) {
		    	cuentaTmp = delegate.obtenerCuenta( cuenta.getCodigo() );
		    	if ( cuentaTmp != null ) 		    	{
		    		errors.add("values.identificador", new ActionError("errors.cuenta.duplicada", cuenta.getCodigo() ));
		    	}
        	}
        	// Comprobamos que no exista otra cuenta por defecto
        	if (  (request.getParameter(Constants.ALTA_PROPERTY) != null ) ||
        		  (request.getParameter(Constants.MODIFICACIO_PROPERTY) != null )){
		    	if((cuenta.getEmail() != null) && 
		    	   (!cuenta.getEmail().equals("")) &&
		    	   (cuenta.getDefecto() == Cuenta.DEFECTO))
		    	{
		    		cuentaTmp = delegate.obtenerCuentaDefectoEmail();
		    		if((cuentaTmp != null) && (!cuentaTmp.getCodigo().equals(cuenta.getCodigo())))
			    		errors.add("values.identificador", new ActionError("errors.cuenta.defecto", "EMAIL" ));
		    	}
		    	if((cuenta.getSms() != null) &&
		    	   (!cuenta.getSms().equals("")) &&
		    	   (cuenta.getDefecto() == Cuenta.DEFECTO))
		    	{
		    		cuentaTmp = delegate.obtenerCuentaDefectoSMS();
		    		if((cuentaTmp != null) && (!cuentaTmp.getCodigo().equals(cuenta.getCodigo())))
			    		errors.add("values.identificador", new ActionError("errors.cuenta.defecto", "SMS" ));
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
