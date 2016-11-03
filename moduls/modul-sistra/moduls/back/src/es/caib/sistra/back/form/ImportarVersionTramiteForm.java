package es.caib.sistra.back.form;

import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.ValidatorForm;

import es.caib.sistra.model.TramiteVersion;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.sistra.persistence.delegate.TramiteVersionDelegate;

public class ImportarVersionTramiteForm extends ValidatorForm
{
	private static Log log = LogFactory.getLog( ImportarVersionTramiteForm.class );
	
	private transient FormFile fitxer;
	private Long codigoTramite;
	private int version;
	public Long getCodigoTramite()
	{
		return codigoTramite;
	}
	public void setCodigoTramite(Long codigoTramite)
	{
		this.codigoTramite = codigoTramite;
	}
	public int getVersion()
	{
		return version;
	}
	public void setVersion(int version)
	{
		this.version = version;
	}
	public FormFile getFitxer()
	{
		return fitxer;
	}
	public void setFitxer(FormFile fitxer)
	{
		this.fitxer = fitxer;
	}
	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) 
    {
        ActionErrors errors = super.validate(mapping, request);
        if (errors == null) 
        {
            errors = new ActionErrors();
        }
		 try 
	        {
	            // Comprovar que la versión no está repetida            
	            TramiteVersionDelegate delegate = DelegateUtil.getTramiteVersionDelegate();
	            Set listaVersiones = delegate.listarTramiteVersiones( getCodigoTramite() );
	            for ( Iterator it = listaVersiones.iterator(); it.hasNext(); )
	            {
	            	TramiteVersion tmp = ( TramiteVersion ) it.next();
	            	if ( tmp.getVersion() == getVersion() )
	                {
	            		errors.add("tramite.version", new ActionError("errors.tramiteVersion.duplicado", "" + getVersion() ));
	                }
	            }
	        
	    	}
	    	catch (Exception e) 
	    	{
	            log.error(e);
	        } 
	        return errors;
    }
}
