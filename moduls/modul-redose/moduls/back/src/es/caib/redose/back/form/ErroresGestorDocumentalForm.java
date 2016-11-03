package es.caib.redose.back.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

public class ErroresGestorDocumentalForm extends RdsForm
{
protected static Log log = LogFactory.getLog(ErroresGestorDocumentalForm.class);
	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) 
	{
       return super.validate(mapping, request);
	}

}
