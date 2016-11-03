package es.caib.bantel.back.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import es.caib.bantel.back.taglib.Constants;
import es.caib.bantel.model.FuenteDatos;
import es.caib.bantel.persistence.delegate.DelegateException;
import es.caib.bantel.persistence.delegate.DelegateUtil;
import es.caib.bantel.persistence.delegate.FuenteDatosDelegate;
import es.caib.util.StringUtil;


public class FuenteDatosForm extends BantelForm implements InitForm
{

	protected static Log log = LogFactory.getLog(FuenteDatosForm.class);
	
	private String idProcedimiento;
	
	private String identificadorOld;
	
	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) 
	{		
        ActionErrors errors = super.validate(mapping, request);
        
       
        if (errors == null) 
        {
            errors = new ActionErrors();
        }
        try 
        {
        	FuenteDatos fuenteDatos = ( FuenteDatos ) this.getValues();
        	
        	if (!StringUtil.validarFormatoIdentificador(fuenteDatos.getIdentificador()) ){
        		errors.add("values.identificador", new ActionError("errors.identificador.novalido"));        	
    		}
        	
        	if (StringUtils.isBlank(fuenteDatos.getDescripcion()) ){
        		errors.add("values.descripcion", new ActionError("errors.required", "Descripcion"));        	
    		}
        	
        	// Comprobamos que no exista otro trámite con ese código
        	if ( request.getParameter(Constants.ALTA_PROPERTY) != null  ) {
        		FuenteDatosDelegate delegate = DelegateUtil.getFuenteDatosDelegate();
		    	FuenteDatos fuenteDatosTmp = delegate.obtenerFuenteDatos(fuenteDatos.getIdentificador());
		    	if ( fuenteDatosTmp != null ) 		    	{
		    		errors.add("values.identificador", new ActionError("errors.fuenteDatos.duplicado", fuenteDatosTmp.getIdentificador() ));
		    	} 
        	}
        	
        	
        }
	    catch (DelegateException e) 
	    {
	        log.error(e);
	    }	   
        
	    return errors;

	}


	public String getIdProcedimiento() {
		return idProcedimiento;
	}


	public void setIdProcedimiento(String idProcedimiento) {
		this.idProcedimiento = idProcedimiento;
	}


	public String getIdentificadorOld() {
		return identificadorOld;
	}


	public void setIdentificadorOld(String identificadorOld) {
		this.identificadorOld = identificadorOld;
	}

}
