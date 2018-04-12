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
import es.caib.sistra.model.EspecTramiteNivel;
import es.caib.sistra.model.TraEspecTramiteNivel;
import es.caib.sistra.model.TramiteVersion;
import es.caib.sistra.persistence.delegate.DelegateException;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.sistra.persistence.delegate.EspecTramiteNivelDelegate;

public class EspecificacionesGenericasForm extends TraduccionValidatorForm
{
	private String validacionInicioScript;
	private String localidadScript;
	private String provinciaScript;
	private String paisScript;	
	private String campoRteNif;
	private String campoRteNom;
	private String campoRdoNif;
	private String campoRdoNom;
	private String datosRpte;
	private String datosRpdo;
	private String urlFin;
    private String avisoSMS;
    private String avisoEmail;
    private String checkEnvio;
    private String destinatarioTramite;
    private String procedimientoDestinoTramite;
    

	public String getDestinatarioTramite() {
		return destinatarioTramite;
	}

	public void setDestinatarioTramite(String destinatarioTramite) {
		this.destinatarioTramite = destinatarioTramite;
	}
	
	public String getProcedimientoDestinoTramite() {
		return procedimientoDestinoTramite;
	}

	public void setProcedimientoDestinoTramite(String procedimientoDestinoTramite) {
		this.procedimientoDestinoTramite = procedimientoDestinoTramite;
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
        	// Elimina la traducción si no tiene ningún campo no vacio, para que la actualización
            // posterior no de errores
        	if ( traduccion != null )
        	{
	            if ( Util.esCadenaVacia( traduccion.getInstruccionesEntrega() ) &&
	            	 Util.esCadenaVacia( traduccion.getInstruccionesInicio() )	&&
	            	 Util.esCadenaVacia( traduccion.getInstruccionesFin() ) &&
	            	 Util.esCadenaVacia( traduccion.getMensajeInactivo() ) &&
	            	 Util.esCadenaVacia( traduccion.getMensajeFechaLimiteEntregaPresencial() ))
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
	
	public void reset(ActionMapping mapping, HttpServletRequest request) 
	{
		super.reset( mapping, request );
		setValidacionInicioScript( null );
		setLocalidadScript(null);
		setProvinciaScript(null);
		setPaisScript(null);
		setCampoRteNif(null);
		setCampoRteNom(null);
		setCampoRdoNif(null);
		setCampoRdoNom(null);
		setDatosRpte(null);
		setDatosRpdo(null);
		setUrlFin(null);
		setAvisoEmail(null);
		setAvisoSMS(null);
		setCheckEnvio(null);
		setDestinatarioTramite(null);
		setProcedimientoDestinoTramite(null);
	}

	public String getLocalidadScript() {
		return localidadScript;
	}

	public void setLocalidadScript(String localidadScript) {
		this.localidadScript = localidadScript;
	}

	public String getPaisScript() {
		return paisScript;
	}

	public void setPaisScript(String paisScript) {
		this.paisScript = paisScript;
	}

	public String getProvinciaScript() {
		return provinciaScript;
	}

	public void setProvinciaScript(String provinciaScript) {
		this.provinciaScript = provinciaScript;
	}

	public String getCampoRdoNif()
	{
		return campoRdoNif;
	}

	public void setCampoRdoNif(String campoRdoNif)
	{
		this.campoRdoNif = campoRdoNif;
	}

	public String getCampoRdoNom()
	{
		return campoRdoNom;
	}

	public void setCampoRdoNom(String campoRdoNom)
	{
		this.campoRdoNom = campoRdoNom;
	}

	public String getCampoRteNif()
	{
		return campoRteNif;
	}

	public void setCampoRteNif(String campoRteNif)
	{
		this.campoRteNif = campoRteNif;
	}

	public String getCampoRteNom()
	{
		return campoRteNom;
	}

	public void setCampoRteNom(String campoRteNom)
	{
		this.campoRteNom = campoRteNom;
	}
	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) 
    {
        ActionErrors errors = super.validate(mapping, request);
        if (errors == null) 
        {
            errors = new ActionErrors();
        }
        //	 Valida idiomas requeridos según la versión del trámite
		if ( isAlta( request ) || isModificacion( request ) )
	    {  
	        try 
	        {  
	        	EspecTramiteNivel espec = (EspecTramiteNivel) getValues();	        	
	        	EspecTramiteNivelDelegate delegate = DelegateUtil.getEspecTramiteNivelDelegate();
	        	TramiteVersion tv = delegate.obtenerTramiteVersion(espec.getCodigo());
	        	String idiomas = tv.getIdiomasSoportados();
	        	StringTokenizer st = new StringTokenizer(idiomas,",");	        	
	        		        	
	        	int instruccionesInicio=0;
	        	int mensajeInactivo=0;
	        	int instruccionesFin=0;
	        	int instruccionesEntrega=0;
	        	int mensajeFecha = 0;
	        	int numLangs = 0;
	        	
	        	while (st.hasMoreTokens()){	        		
	        		String lang = ( String ) st.nextToken();
	        		numLangs ++;
	        		TraEspecTramiteNivel traduccion = ( TraEspecTramiteNivel ) this.getValues().getTraduccion( lang );
	        			        		
	        		// Para campos opcionales comprueba si estan metidos en un idioma y para otro no
	        		if ( traduccion != null ) {
	        			if (StringUtils.isNotEmpty( traduccion.getInstruccionesEntrega())) instruccionesEntrega ++;
	        			if (StringUtils.isNotEmpty( traduccion.getInstruccionesFin())) instruccionesFin ++;
	        			if (StringUtils.isNotEmpty( traduccion.getInstruccionesInicio())) instruccionesInicio ++;	        			
	        			if (StringUtils.isNotEmpty( traduccion.getMensajeInactivo())) mensajeInactivo ++;	     
	        			if (StringUtils.isNotEmpty( traduccion.getMensajeFechaLimiteEntregaPresencial())) mensajeFecha ++;
	        		}	        		
	        	}
	        	
	        	if (instruccionesEntrega > 0 && instruccionesEntrega != numLangs){	        		
	        		errors.add( "traduccion.informacion", new ActionError("errors.revisarTraducciones","Instrucciones Entrega") );
	        	}	
	        	if (instruccionesFin > 0 && instruccionesFin != numLangs){	        		
	        		errors.add( "traduccion.instruccionesFin", new ActionError("errors.revisarTraducciones","Instrucciones Fin") );
	        	}	
	        	if (instruccionesInicio > 0 && instruccionesInicio != numLangs){	        		
	        		errors.add( "traduccion.instruccionesInicio", new ActionError("errors.revisarTraducciones","Instrucciones Inicio") );
	        	}	        	
	        	if (mensajeInactivo > 0 && mensajeInactivo != numLangs){	        		
	        		errors.add( "traduccion.mensajeInactivo", new ActionError("errors.revisarTraducciones","Mensaje Inactivo") );
	        	}	
	        	if (mensajeFecha > 0 && mensajeFecha != numLangs){	        		
	        		errors.add( "traduccion.mensajeFechaLimiteEntregaPresencial", new ActionError("errors.revisarTraducciones","Mensaje fecha límite") );
	        	}	        		        
	        	
	        }
	    	catch (DelegateException e) 
	    	{
	            log.error(e);
	        }
	    }
		
		return errors;
    }

	public String getUrlFin() {
		return urlFin;
	}

	public void setUrlFin(String urlFin) {
		this.urlFin = urlFin;
	}

	public String getAvisoEmail() {
		return avisoEmail;
	}

	public void setAvisoEmail(String avisoEmail) {
		this.avisoEmail = avisoEmail;
	}

	public String getAvisoSMS() {
		return avisoSMS;
	}

	public void setAvisoSMS(String avisoSMS) {
		this.avisoSMS = avisoSMS;
	}

	public String getCheckEnvio() {
		return checkEnvio;
	}

	public void setCheckEnvio(String checkEnvio) {
		this.checkEnvio = checkEnvio;
	}

	public String getDatosRpte() {
		return datosRpte;
	}

	public void setDatosRpte(String datosRpte) {
		this.datosRpte = datosRpte;
	}

	public String getDatosRpdo() {
		return datosRpdo;
	}

	public void setDatosRpdo(String datosRpdo) {
		this.datosRpdo = datosRpdo;
	}	

}
