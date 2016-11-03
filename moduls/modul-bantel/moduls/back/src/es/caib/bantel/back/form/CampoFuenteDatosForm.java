package es.caib.bantel.back.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import es.caib.bantel.back.taglib.Constants;
import es.caib.bantel.model.CampoFuenteDatos;
import es.caib.util.StringUtil;


public class CampoFuenteDatosForm extends BantelForm implements InitForm
{

	protected static Log log = LogFactory.getLog(CampoFuenteDatosForm.class);
	
	
	private String idFuenteDatos;
	private String identificadorOld;
	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) 
	{		
        ActionErrors errors = super.validate(mapping, request);
        
       
        if (errors == null) 
        {
            errors = new ActionErrors();
        }
       
    	CampoFuenteDatos campoFD = ( CampoFuenteDatos ) this.getValues();

    	if (!StringUtil.validarFormatoIdentificador(campoFD.getIdentificador()) ){
    		errors.add("values.identificador", new ActionError("errors.identificador.novalido"));        	
		}
    	
    	if ( request.getParameter(Constants.ALTA_PROPERTY) != null  ) {
    		 
    	}
        	
	    return errors;

	}

	public String getIdFuenteDatos() {
		return idFuenteDatos;
	}

	public void setIdFuenteDatos(String idFuenteDatos) {
		this.idFuenteDatos = idFuenteDatos;
	}

	public String getIdentificadorOld() {
		return identificadorOld;
	}

	public void setIdentificadorOld(String identificadorOld) {
		this.identificadorOld = identificadorOld;
	}
	
}
