package es.caib.sistra.back.form;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import es.caib.sistra.back.util.Util;
import es.caib.sistra.model.MensajeTramite;
import es.caib.sistra.model.TraMensajeTramite;
import es.caib.sistra.model.TramiteVersion;
import es.caib.sistra.persistence.delegate.DelegateException;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.sistra.persistence.delegate.MensajeTramiteDelegate;
import es.caib.sistra.persistence.delegate.TramiteVersionDelegate;

public class MensajeTramiteForm extends TraduccionValidatorForm
{
	private Long idTramiteVersion = null;

	public Long getIdTramiteVersion()
	{
		return idTramiteVersion;
	}

	public void setIdTramiteVersion(Long idTramiteVersion)
	{
		this.idTramiteVersion = idTramiteVersion;
	}
	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) 
    {
        ActionErrors errors = super.validate(mapping, request);
        if (errors == null) 
        {
            errors = new ActionErrors();
        }
        try 
        {
        	MensajeTramite mensajeTramite = (MensajeTramite) getValues();
            // Comprobar que el nombre de tramite no esta repetido.
            
            MensajeTramiteDelegate delegate = DelegateUtil.getMensajeTramiteDelegate();
            Map itMensajeTramites = delegate.listarMensajeTramites( getIdTramiteVersion() );
            for ( Iterator it = itMensajeTramites.keySet().iterator(); it.hasNext(); )
            {            	
            	MensajeTramite tmp = ( MensajeTramite ) itMensajeTramites.get(it.next());
            	if ( tmp.getIdentificador().equals( mensajeTramite.getIdentificador() ) && !tmp.getCodigo().equals( mensajeTramite.getCodigo() ) )
            	{
            		errors.add("values.identificador", new ActionError("errors.mensajeTramite.duplicado", mensajeTramite.getIdentificador() ));
            	}
            }                                                
        }
    	catch (DelegateException e) 
    	{
            log.error(e);
        }
    	
    	// Valida idiomas requeridos según la versión del trámite
    	if ( isAlta( request ) || isModificacion( request ) )
        {  
	        try 
	        {  
	        	TramiteVersionDelegate delegate = DelegateUtil.getTramiteVersionDelegate();
	        	TramiteVersion tv = delegate.obtenerTramiteVersion(this.getIdTramiteVersion());
	        	String idiomas = tv.getIdiomasSoportados();
	        	StringTokenizer st = new StringTokenizer(idiomas,",");	        	
	        	while (st.hasMoreTokens()){	        		
	        		String lang = ( String ) st.nextToken();
	        		TraMensajeTramite traduccion = ( TraMensajeTramite ) this.getValues().getTraduccion( lang );
	        		if (  traduccion == null || ( traduccion != null && StringUtils.isEmpty( traduccion.getMensaje() ) ) )
	        		{
	        			errors.add( "traduccion.descripcion", new ActionError( "errors.traduccion", lang ) );
	        		}
	        	}
	        }
	    	catch (DelegateException e) 
	    	{
	            log.error(e);
	        }
        }
    	return errors;
    }
	
	public void validaTraduccion(ActionMapping mapping, HttpServletRequest request) 
	{
        super.validaTraduccion( mapping, request );
        MensajeTramite docNivel = ( MensajeTramite ) this.getValues();
        Map mTraducciones = docNivel.getTraducciones();
        HashSet removeSet = new HashSet(0);
        for ( Iterator it = mTraducciones.keySet().iterator(); it.hasNext(); )
        {
        	String lang = ( String ) it.next();
        	TraMensajeTramite traduccion = ( TraMensajeTramite ) mTraducciones.get( lang );
//	        	 Elimina la traducción si no tiene ningún campo no vacio, para que la actualización
            // posterior no de errores
            if ( Util.esCadenaVacia( traduccion.getMensaje() ) )
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
