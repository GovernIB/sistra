package es.caib.sistra.back.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import es.caib.sistra.back.taglib.Constants;
import es.caib.sistra.model.GestorFormulario;
import es.caib.sistra.persistence.delegate.DelegateException;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.sistra.persistence.delegate.GestorFormularioDelegate;


public class GestorFormulariosForm extends TraForm implements InitForm
{

	protected static Log log = LogFactory.getLog(GestorFormulariosForm.class);
	//private String idFormulario = null;
	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) 
	{
        ActionErrors errors = super.validate(mapping, request);
        if (errors == null) 
        {
            errors = new ActionErrors();
        }
        try 
        {
        	GestorFormulario gFormulario = ( GestorFormulario ) this.getValues();
        	GestorFormularioDelegate delegate = DelegateUtil.getGestorFormularioDelegate();
        	GestorFormulario gFormularioTmp = delegate.obtener( gFormulario.getIdentificador() );
        	if(request.getParameter(Constants.ALTA_PROPERTY) != null){
        		if ( gFormularioTmp != null && gFormularioTmp.getIdentificador().equals( gFormulario.getIdentificador() ) )
            	{
            		errors.add("values.identificador", new ActionError("errors.gestorFormularios.duplicado", gFormulario.getIdentificador() ));
            		gFormulario.setIdentificador(null);
            	}        		
        	}else if(request.getParameter(Constants.MODIFICACIO_PROPERTY) != null){
        		if(gFormularioTmp == null){
            		errors.add("values.identificador", new ActionError("errors.gestorFormularios.inexistente", gFormulario.getIdentificador() ));
        		}
        	}
        }
	    catch (DelegateException e) 
	    {
	        log.error(e);
	    }
	    return errors;

	}

//	public String getIdFormulario()
//	{
//		return idFormulario;
//	}
//
//	public void setIdFormulario(String idFormulario)
//	{
//		this.idFormulario = idFormulario;
//	}

}
