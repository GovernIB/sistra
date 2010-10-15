package es.caib.sistra.back.form;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.sistra.persistence.delegate.OrganoResponsableDelegate;
import es.caib.sistra.persistence.delegate.DelegateException;
import javax.servlet.http.HttpServletRequest;


import es.caib.sistra.model.OrganoResponsable;

public class OrganoForm extends TraForm implements InitForm
{
	protected static Log log = LogFactory.getLog(OrganoForm.class);
		
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = super.validate(mapping, request);
        if (errors == null) 
        {
            errors = new ActionErrors();
        }
        try 
        {
        	OrganoResponsable organo = ( OrganoResponsable ) this.getValues();
	        OrganoResponsableDelegate delegate = DelegateUtil.getOrganoResponsableDelegate();
	        List lstOrganos = delegate.listarOrganoResponsables();
	        for ( int i = 0; i < lstOrganos.size(); i++ )
	        {
	        	OrganoResponsable tmp = ( OrganoResponsable ) lstOrganos.get( i );
	        	if (  organo.getDescripcion().equals( tmp.getDescripcion() ) && !tmp.getCodigo().equals( organo.getCodigo() ) )
    			{
	        		errors.add("values.descripcion", new ActionError("errors.organo.duplicado", organo.getDescripcion() ));
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
