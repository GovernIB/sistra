package es.caib.mobtratel.front.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.ValidatorForm;

public class EditarEnvioFicheroForm extends ValidatorForm
{

	protected static Log log = LogFactory.getLog(EditarEnvioFicheroForm.class);
	
	private transient FormFile fichero;



	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = super.validate(mapping, request);
        if (errors == null) 
        {
            errors = new ActionErrors();
        }

        if (!archivoValido(getFichero())) 
        {
    		errors.add(null, new ActionError("error.ficheroVacio"));
        }

    	
        return errors;
    }
	
	private void reset()
	{
	}
	
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		reset();
        super.reset(mapping, request);
    }
	
	public void destroy(ActionMapping mapping, HttpServletRequest request) {
		reset();
    }

	public FormFile getFichero() {
		return fichero;
	}

	public void setFichero(FormFile fichero) {
		this.fichero = fichero;
	}


    protected boolean archivoValido(FormFile formFile) {
        return (formFile != null && formFile.getFileSize() > 0);
    }





}
