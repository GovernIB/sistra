package es.caib.redose.back.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import es.caib.redose.model.Ubicacion;
import es.caib.redose.persistence.delegate.DelegateException;
import es.caib.redose.persistence.delegate.DelegateUtil;

public class UbicacionForm extends RdsForm
{
	protected static Log log = LogFactory.getLog(UbicacionForm.class);
	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) 
	{
        ActionErrors errors = super.validate(mapping, request);
        if (errors == null) {
            errors = new ActionErrors();
        }

        try {
            Ubicacion ubicacion = (Ubicacion) getValues();

            // Comprovar que la ubicación no existe
            String codigoAlt = ubicacion.getCodigoUbicacion();
            Ubicacion formAux = DelegateUtil.getUbicacionDelegate().obtenerUbicacion( codigoAlt );
            if (formAux != null && !formAux.getCodigo().equals(ubicacion.getCodigo())) {
                errors.add("modelo", new ActionError("errors.ubicacion.duplicado", ubicacion));
            }

        } catch (DelegateException e) 
        {
            log.error(e);
        }
        
        return errors;
    }

}
