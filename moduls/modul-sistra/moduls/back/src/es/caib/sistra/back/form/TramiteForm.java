package es.caib.sistra.back.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.apache.commons.lang.StringUtils;

import es.caib.sistra.back.util.MensajesUtil;
import es.caib.sistra.model.TraTramite;
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
            
            if(isAlta(request) || isModificacion(request)){
            	TraTramite traTra = (TraTramite)tramite.getTraduccion("es");
            	if (traTra != null ){
            		if(StringUtils.isEmpty(traTra.getDescripcion())){
            			errors.add("values.traduccion", new ActionError("errors.descripcion.vacio",MensajesUtil.getValue("es")));
            		}
            	}else{
            		errors.add("values.traduccion", new ActionError("errors.descripcion.vacio", MensajesUtil.getValue("es") ));
            	}
            	traTra = (TraTramite)tramite.getTraduccion("ca");
            	if(traTra != null){
            		if(StringUtils.isEmpty(traTra.getDescripcion())){
            			errors.add("values.traduccion", new ActionError("errors.descripcion.vacio", MensajesUtil.getValue("ca") ));
            		}
            	}else{
            		errors.add("values.traduccion", new ActionError("errors.descripcion.vacio", MensajesUtil.getValue("ca") ));
            	}
            	
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
