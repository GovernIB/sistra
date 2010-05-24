package es.caib.bantel.back.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import es.caib.bantel.back.taglib.Constants;
import es.caib.bantel.model.Tramite;
import es.caib.bantel.persistence.delegate.DelegateException;
import es.caib.bantel.persistence.delegate.DelegateUtil;
import es.caib.bantel.persistence.delegate.TramiteDelegate;


public class TramiteForm extends BantelForm implements InitForm
{

	protected static Log log = LogFactory.getLog(TramiteForm.class);
	
	private transient FormFile ficheroExportacion;
	
	private String userPlain;
	private String passPlain;
	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) 
	{		
        ActionErrors errors = super.validate(mapping, request);
        
       
        if (errors == null) 
        {
            errors = new ActionErrors();
        }
        try 
        {
        	Tramite tramite = ( Tramite ) this.getValues();
        	
        	// Comprobamos restricciones
        	if (tramite.getIntervaloInforme() != null && tramite.getIntervaloInforme().longValue() > 0){

        		// Url
        		if ( (tramite.getTipoAcceso() == Tramite.ACCESO_EJB && tramite.getLocalizacionEJB() == Tramite.EJB_REMOTO) 
        			|| 
        			tramite.getTipoAcceso() == Tramite.ACCESO_WEBSERVICE){
		    			if (StringUtils.isEmpty(tramite.getUrl())){
		        			errors.add("values.url", new ActionError("errors.url.vacia"));
		        		}        			
        		}
        		
        		// Usr y pswd
        		if (tramite.getAutenticacionEJB() == Tramite.AUTENTICACION_ESTANDAR){        		
        			if (StringUtils.isEmpty(userPlain) || StringUtils.isEmpty(passPlain))
        				errors.add("userPlain", new ActionError("errors.userpasswd.vacio"));
        		}
        		
        	}
        	        	
        	// Comprobamos que no exista otro trámite con ese código
        	if (  request.getParameter(Constants.ALTA_PROPERTY) != null  ) {
		    	TramiteDelegate delegate = DelegateUtil.getTramiteDelegate();
		    	Tramite tramiteTmp = delegate.obtenerTramitePorId( tramite.getIdentificador() );
		    	if ( tramiteTmp != null ) 		    	{
		    		errors.add("values.identificador", new ActionError("errors.tramite.duplicado", tramite.getIdentificador() ));
		    	} 
        	}
        
        }
	    catch (DelegateException e) 
	    {
	        log.error(e);
	    }	   
        
	    return errors;

	}


	public FormFile getFicheroExportacion() {
		return ficheroExportacion;
	}


	public void setFicheroExportacion(FormFile ficheroExportacion) {
		this.ficheroExportacion = ficheroExportacion;
	}


	public String getPassPlain() {
		return passPlain;
	}


	public void setPassPlain(String passPlain) {
		this.passPlain = passPlain;
	}


	public String getUserPlain() {
		return userPlain;
	}


	public void setUserPlain(String userPlain) {
		this.userPlain = userPlain;
	}

}
