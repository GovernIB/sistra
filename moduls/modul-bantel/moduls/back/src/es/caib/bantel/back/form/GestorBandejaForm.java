package es.caib.bantel.back.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import es.caib.bantel.back.taglib.Constants;
import es.caib.bantel.model.GestorBandeja;
import es.caib.bantel.persistence.delegate.DelegateException;
import es.caib.bantel.persistence.delegate.DelegateUtil;
import es.caib.bantel.persistence.delegate.GestorBandejaDelegate;
import es.caib.util.StringUtil;
import es.caib.util.ValidacionesUtil;

public class GestorBandejaForm extends BantelForm implements InitForm
{
	protected static Log log = LogFactory.getLog(GestorBandejaForm.class);
	
	private String[] tramites;
	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) 
	{
        ActionErrors errors = super.validate(mapping, request);
        if (errors == null) 
        {
            errors = new ActionErrors();
        }
        try 
        {
        	GestorBandeja gestorBandeja = ( GestorBandeja ) this.getValues();
        	GestorBandejaDelegate delegate = DelegateUtil.getGestorBandejaDelegate();
        	
        	// if is alta && gestorBandejaTmp
        	if (  request.getParameter(Constants.ALTA_PROPERTY) != null  )
        	{
        		GestorBandeja gestorBandejaTmp = delegate.findGestorBandeja( gestorBandeja.getSeyconID() );
        		if ( gestorBandejaTmp != null )
        		{
        			errors.add("values.identificador", new ActionError("errors.gestorBandeja.duplicado", gestorBandeja.getSeyconID() ));
        		}
        		if (!StringUtil.validarFormatoUsuario(gestorBandeja.getSeyconID())) {
        			errors.add("values.identificador", new ActionError("errors.gestorBandeja.identificadorNoValido"));
        		}        		
        	}
        	
        	if (  request.getParameter(Constants.ALTA_PROPERTY) != null  || request.getParameter(Constants.MODIFICACIO_PROPERTY) != null ) {
        		if (!ValidacionesUtil.validarEmail(gestorBandeja.getEmail())) {
        			errors.add("values.identificador", new ActionError("errors.email", gestorBandeja.getEmail()));
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
		this.setTramites( null );
		super.reset( mapping, request );
	}

	public String[] getTramites() {
		return tramites;
	}

	public void setTramites(String[] tramites) {
		this.tramites = tramites;
	}

	
}
