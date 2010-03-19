package es.caib.sistra.back.form;

import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import es.caib.sistra.model.DatoJustificante;
import es.caib.sistra.model.TraDatoJustificante;
import es.caib.sistra.model.TramiteVersion;
import es.caib.sistra.persistence.delegate.DelegateException;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.sistra.persistence.delegate.EspecTramiteNivelDelegate;

public class DatoJustificanteForm extends TraduccionValidatorForm
{
	private Long idEspecTramiteNivel;
	private Long idTramiteNivel;
	private String valorCampoScript;
	private String visibleScript;

	public Long getIdEspecTramiteNivel()
	{
		return idEspecTramiteNivel;
	}


	public void setIdEspecTramiteNivel(Long idEspecTramiteNivel)
	{
		this.idEspecTramiteNivel = idEspecTramiteNivel;
	}
	
	public String getVisibleScript()
	{
		return visibleScript;
	}


	public void setVisibleScript(String visibleScript)
	{
		this.visibleScript = visibleScript;
	}
	
	public Long getIdTramiteNivel()
	{
		return idTramiteNivel;
	}


	public void setIdTramiteNivel(Long idTramiteNivel)
	{
		this.idTramiteNivel = idTramiteNivel;
	}

	public void destroy(ActionMapping mapping, HttpServletRequest request) 
	{
        super.destroy( mapping, request );
        this.setVisibleScript( null );
    }


	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) 
    {
        ActionErrors errors = super.validate(mapping, request);
        if (errors == null) 
        {
            errors = new ActionErrors();
        }
                
        if ( isAlta( request ) || isModificacion( request ) )
        {        	        	
	        try 
	        {
	        	// Valida idiomas requeridos según la versión del trámite
	        	EspecTramiteNivelDelegate espd =  DelegateUtil.getEspecTramiteNivelDelegate();
	        	TramiteVersion tv = espd.obtenerTramiteVersion(this.getIdEspecTramiteNivel());
	        	String idiomas = tv.getIdiomasSoportados();
	        	StringTokenizer st = new StringTokenizer(idiomas,",");	        	
	        	while (st.hasMoreTokens()){	        		
	        		String lang = ( String ) st.nextToken();
	        		TraDatoJustificante traduccion = ( TraDatoJustificante ) this.getValues().getTraduccion( lang );
	        		if (  traduccion == null || ( traduccion != null && StringUtils.isEmpty( traduccion.getDescripcion() ) ) )
	        		{
	        			errors.add( "traduccion.descripcion", new ActionError( "errors.traduccion", lang ) );
	        		}
	        	}
	        	
	        	// Valida que si es de tipo campo este rellenado la referencia o el script
	        	DatoJustificante dj = (DatoJustificante) getValues();
	        	if (dj.getTipo() == DatoJustificante.TIPO_CAMPO){
		        	if ( StringUtils.isEmpty(this.getValorCampoScript()) && StringUtils.isEmpty(dj.getReferenciaCampo()) ){
		        		errors.add( "traduccion.descripcion", new ActionError( "errors.datoJustificante.campo.noValor") );
		        	}
		        	if ( StringUtils.isNotEmpty(this.getValorCampoScript()) && StringUtils.isNotEmpty(dj.getReferenciaCampo()) ){
		        		errors.add( "traduccion.descripcion", new ActionError( "errors.datoJustificante.campo.unicoValor") );
		        	}
	        	}
	        	
	        }
	    	catch (DelegateException e) 
	    	{
	    		errors.add("traduccion.descripcion",new ActionError("errors.validacion"));
	            log.error(e);
	        }
        }
        
        return errors;
    }


	public String getValorCampoScript() {
		return valorCampoScript;
	}


	public void setValorCampoScript(String valorCampoScript) {
		this.valorCampoScript = valorCampoScript;
	}

}
