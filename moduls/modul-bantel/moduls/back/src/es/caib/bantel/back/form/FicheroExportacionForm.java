package es.caib.bantel.back.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import es.caib.bantel.back.taglib.Constants;
import es.caib.bantel.model.FicheroExportacion;
import es.caib.bantel.persistence.delegate.DelegateException;
import es.caib.bantel.persistence.delegate.DelegateUtil;


public class FicheroExportacionForm extends BantelForm implements InitForm
{

	protected static Log log = LogFactory.getLog(FicheroExportacionForm.class);
	
	private transient FormFile fichero;

	public FormFile getFichero() {
		return fichero;
	}

	public void setFichero(FormFile fichero) {
		this.fichero = fichero;
	}

	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) 
	{		
        ActionErrors errors = super.validate(mapping, request);
        
        FicheroExportacion ficExp= (FicheroExportacion) this.getValues();
        if (  request.getParameter(Constants.ALTA_PROPERTY) != null  ) {
        	try {
				if (DelegateUtil.getFicheroExportacionDelegate().findFicheroExportacion(ficExp.getIdentificadorTramite()) != null) {
					errors.add("values.codigo", new ActionError("errors.ficheroExportacion.duplicado", ficExp.getIdentificadorTramite() ));
				}
			} catch (DelegateException e) {
				errors.add("values.codigo", new ActionError("errors.delegate", e.getMessage() ));
			}
        }
        
        return errors;
	}
	

}
