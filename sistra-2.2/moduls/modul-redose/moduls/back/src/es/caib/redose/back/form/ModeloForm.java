package es.caib.redose.back.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import es.caib.redose.model.Modelo;
import es.caib.redose.persistence.delegate.DelegateException;
import es.caib.redose.persistence.delegate.DelegateUtil;


public class ModeloForm extends RdsForm implements InitForm
{
	protected static Log log = LogFactory.getLog(ModeloForm.class);
		
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = super.validate(mapping, request);
        if (errors == null) {
            errors = new ActionErrors();
        }

        try {
            Modelo formulario = (Modelo) getValues();

            // Comprovar que el modelo no existe.
            String modelo = formulario.getModelo();
            Modelo formAux = DelegateUtil.getModeloDelegate().obtenerModelo( Long.valueOf( modelo ) );
            if (formAux != null && !formAux.getCodigo().equals(formulario.getCodigo())) {
                errors.add("modelo", new ActionError("errors.modelo.duplicado", modelo));
            }

        } catch (DelegateException e) {
            log.error(e);
        }
        
        return errors;
    }
}
