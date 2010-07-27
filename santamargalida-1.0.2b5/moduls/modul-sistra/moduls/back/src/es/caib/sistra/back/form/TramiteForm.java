package es.caib.sistra.back.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import es.caib.sistra.model.Tramite;
import es.caib.sistra.persistence.delegate.DelegateException;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.sistra.persistence.delegate.TramiteDelegate;


public class TramiteForm extends TraduccionValidatorForm  
{
	private Long idOrgano = null;
	private transient FormFile tramite;

    public Long getIdOrgano() {
        return this.idOrgano;
    }

    public void setIdOrgano(Long idOrgano) {
        this.idOrgano = idOrgano;
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
            Tramite tramite = (Tramite) getValues();
            // Comprobar que el nombre de tramite no esta repetido.
            
            TramiteDelegate delegate = DelegateUtil.getTramiteDelegate();
            Tramite tmp = delegate.obtenerTramite( tramite.getIdentificador() );
            if (tmp != null &&  tmp.getIdentificador().equals( tramite.getIdentificador() ) && !tmp.getCodigo().equals( tramite.getCodigo() ) )
        	{
        		errors.add("values.identificador", new ActionError("errors.tramite.duplicado", tramite.getIdentificador() ));
        	}
            
            /*
            Set itTramites = delegate.listarTramitesOrganoResponsable( getIdOrgano() );
            for ( Iterator it = itTramites.iterator(); it.hasNext(); )
            {
            	Tramite tmp = ( Tramite ) it.next();
            	if ( tmp.getIdentificador().equals( tramite.getIdentificador() ) && !tmp.getCodigo().equals( tramite.getCodigo() ) )
            	{
            		errors.add("values.identificador", new ActionError("errors.tramite.duplicado", tramite.getIdentificador() ));
            	}
            }
            */
    	}
    	
    	catch (DelegateException e) 
    	{
            log.error(e);
        }
        
        return errors;
    }

}
