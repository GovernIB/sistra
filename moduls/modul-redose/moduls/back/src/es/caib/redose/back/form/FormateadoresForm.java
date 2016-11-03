package es.caib.redose.back.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import es.caib.redose.model.Formateador;
import es.caib.redose.model.Ubicacion;
import es.caib.redose.persistence.delegate.DelegateException;
import es.caib.redose.persistence.delegate.DelegateUtil;

public class FormateadoresForm extends RdsForm
{
	protected static Log log = LogFactory.getLog(FormateadoresForm.class);
	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) 
	{
        ActionErrors errors = super.validate(mapping, request);
        if (errors == null) {
            errors = new ActionErrors();
        }

        try {
            Formateador formateador = (Formateador) getValues();

            // Comprovar que el formateador no existe
            Formateador formAux = DelegateUtil.getFormateadorDelegate().obtenerFormateador( formateador.getClase() );
            if (formAux != null && !formAux.getIdentificador().equals(formateador.getIdentificador())) {
                errors.add("modelo", new ActionError("errors.ubicacion.duplicado", formateador));
            }

        } catch (DelegateException e) 
        {
            log.error(e);
        }
        
        return errors;
    }

}
