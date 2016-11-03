package es.caib.redose.back.form;

import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import es.caib.redose.model.Version;
import es.caib.redose.persistence.delegate.DelegateException;
import es.caib.redose.persistence.delegate.DelegateUtil;

public class VersionForm extends RdsForm
{
	protected static Log log = LogFactory.getLog(VersionForm.class);
	
	private Long idModelo = null;
	
	/*
	public void reset(ActionMapping mapping, HttpServletRequest request) 
	{
		super.reset(mapping, request);
		Version values = ( Version ) this.getValues();
        if ( values != null )
        {
        	Modelo modelo = values.getModelo();
        	if ( modelo != null )
        	{
        		setIdModelo( modelo.getCodigo() );
        	}
        }
    }
    */
	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) 
	{
        ActionErrors errors = super.validate(mapping, request);
        if (errors == null) {
            errors = new ActionErrors();
        }

        try {
            Version version = (Version) getValues();
            Set versions = DelegateUtil.getVersionDelegate().listarVersionesModelo( idModelo );
            
            Iterator it = versions.iterator();

            // Comprobar que la versión no está repetida
            int ver = version.getVersion();
            
            while( it.hasNext() )
            {
                Version p = ( Version ) it.next();
                if ( ( p.getVersion() == ver ) && !p.getCodigo().equals(version.getCodigo())) {
                    errors.add("values.nombre", new ActionError("errors.version.duplicado", String.valueOf( ver ) ));
                    break;
                }
            }
        } catch (DelegateException e) {
            log.error(e);
        }

        return errors;
    }

	public Long getIdModelo() {
		return idModelo;
	}
	public void setIdModelo(Long idModelo) {
		this.idModelo = idModelo;
	}
}
