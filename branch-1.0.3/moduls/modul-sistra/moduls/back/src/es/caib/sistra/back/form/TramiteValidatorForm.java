package es.caib.sistra.back.form;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.util.RequestUtils;

import es.caib.sistra.back.action.TramiteFormBeanConfig;
import es.caib.sistra.back.util.Util;
import es.caib.sistra.model.EspecTramiteNivel;
import es.caib.sistra.model.TraEspecTramiteNivel;
import es.caib.sistra.model.Traducible;
import es.caib.sistra.model.TramiteEspecificado;

public class TramiteValidatorForm extends TraduccionValidatorForm
{
	protected String tramiteClassName;
	
	private TramiteEspecificado tramite;
	
	private String validacionInicioScript;
	
	public void init(ActionMapping mapping, HttpServletRequest request)
	{
		if (!inicialitat) 
        {
            ModuleConfig config = RequestUtils.getModuleConfig(request, getServlet().getServletContext());
            TramiteFormBeanConfig beanConfig = (TramiteFormBeanConfig) config.findFormBeanConfig(mapping.getName());
            tramiteClassName = beanConfig.getTramiteClassName();
            log.info("tramiteClassName=" + tramiteClassName);
        }
		super.init( mapping, request );
		
	}
	
	public void destroy(ActionMapping mapping, HttpServletRequest request) 
	{
        super.destroy( mapping, request );
        setTramite( null );
    }
	
	public TramiteEspecificado getTramite()
	{
		if ( tramite == null )
		{
			try
			{
				tramite = ( TramiteEspecificado ) RequestUtils.applicationInstance(tramiteClassName);
			}
			catch ( Throwable t )
			{
				log.error("No se ha podido crear el tramite ", t);
				return null;
			}
		}
		return tramite;
	}
	
	public void setTramite( TramiteEspecificado tramite )
	{
		this.tramite = tramite;
	}
	
	public Traducible getValues() 
	{
        Traducible values = getTramite().getEspecificaciones();
        if (values == null) 
        {
            try 
            {
                values = (Traducible) RequestUtils.applicationInstance(valuesClassName);
                this.setValues( values );
            } catch (Throwable t) 
            {
                log.error("No se ha podido crear el value ", t);
                return null;
            }
        }
        return values;
    }

    public void setValues(Traducible values) 
    {
        getTramite().setEspecificaciones( ( EspecTramiteNivel ) values );
        super.setValues( values );
    }
    
    public String getValidacionInicioScript()
	{
		return validacionInicioScript;
	}

	public void setValidacionInicioScript(String validacionInicioScript)
	{
		this.validacionInicioScript = validacionInicioScript;
	}
	
	public void validaTraduccion(ActionMapping mapping, HttpServletRequest request) 
	{
        super.validaTraduccion( mapping, request );
        EspecTramiteNivel espec = ( EspecTramiteNivel ) this.getValues();
        Map mTraducciones = espec.getTraducciones();
        HashSet removeSet = new HashSet(0);
        for ( Iterator it = mTraducciones.keySet().iterator(); it.hasNext(); )
        {
        	String lang = ( String ) it.next();
        	TraEspecTramiteNivel traduccion = ( TraEspecTramiteNivel ) mTraducciones.get( lang );
//	        	 Elimina la traducción si no tiene ningún campo no vacio, para que la actualización
            // posterior no de errores
        	if ( traduccion != null )
        	{
	            if ( Util.esCadenaVacia( traduccion.getInstruccionesEntrega() ) &&
	            	 Util.esCadenaVacia( traduccion.getInstruccionesInicio() )	&&          	 
	            	 Util.esCadenaVacia( traduccion.getInstruccionesFin() ) &&
	            	 Util.esCadenaVacia( traduccion.getMensajeInactivo() ))
	            {
	            	//this.getValues().setTraduccion( lang, null );
	            	removeSet.add( lang );
	            }
        	}
            
            
        	
        }
        for ( Iterator it = removeSet.iterator(); it.hasNext(); )
        {
        	String lang = ( String ) it.next();
        	this.getValues().setTraduccion( lang, null );
        }
        
    }

}
