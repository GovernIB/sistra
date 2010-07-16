package es.caib.sistra.back.form;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import es.caib.sistra.back.util.Util;
import es.caib.sistra.model.MensajePlataforma;
import es.caib.sistra.model.TraMensajePlataforma;


public class MensajePlataformaForm extends TraduccionValidatorForm  
{
	
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) 
    {
        ActionErrors errors = super.validate(mapping, request);
        if (errors == null) 
        {
            errors = new ActionErrors();
        }
        
        return errors;
    }
    
    public void validaTraduccion(ActionMapping mapping, HttpServletRequest request) 
	{
        super.validaTraduccion( mapping, request );
        MensajePlataforma mensajePlataforma = ( MensajePlataforma ) this.getValues();
        Map mTraducciones = mensajePlataforma.getTraducciones();
        HashSet removeSet = new HashSet(0);
        for ( Iterator it = mTraducciones.keySet().iterator(); it.hasNext(); )
        {
        	String lang = ( String ) it.next();
        	TraMensajePlataforma traduccion = ( TraMensajePlataforma ) mTraducciones.get( lang );
        	// Elimina la traducción si no tiene ningún campo no vacio, para que la actualización
            // posterior no de errores
            if ( Util.esCadenaVacia( traduccion.getDescripcion() ) )
            {
            	//this.getValues().setTraduccion( lang, null );
            	removeSet.add( lang );
            }
        }
        for ( Iterator it = removeSet.iterator(); it.hasNext(); )
        {
        	String lang = ( String ) it.next();
        	this.getValues().setTraduccion( lang, null );
        }
        
    }

}
