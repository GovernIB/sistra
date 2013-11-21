package es.caib.sistra.back.form;

import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import es.caib.sistra.back.util.Util;
import es.caib.sistra.model.ConstantesSTR;
import es.caib.sistra.model.Dominio;
import es.caib.sistra.model.TramiteVersion;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.sistra.persistence.delegate.TramiteVersionDelegate;


public class TramiteVersionForm extends  TraForm implements InitForm
{
	private Long idTramite = null;
	
	private String inicioPlazo;
	private String finPlazo;
	
	private String[] idiomas;
	
	private String userPlain;
	private String passPlain;
	
	public Long getIdTramite()
	{
		return idTramite;
	}

	public void setIdTramite(Long idTramite)
	{
		this.idTramite = idTramite;
	}
	
	public void destroy(ActionMapping mapping, HttpServletRequest request) 
	{
        super.destroy( mapping, request );
        this.setInicioPlazo( null );
        this.setFinPlazo( null );
        this.setIdiomas(null);
       
    }
	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) 
    {
        ActionErrors errors = super.validate(mapping, request);
        if (errors == null) 
        {
            errors = new ActionErrors();
        }
                     
        // Inicio y fin de plazo
        if ( !Util.esCadenaVacia( inicioPlazo  ) )
        {
        	if ( Util.cadenaASqlTimestamp( inicioPlazo ) == null )
        	{
        		errors.add( "tramiteVersion.inicioPlazo", new ActionError("errors.tramiteVersion.inicioPlazo"  ) );
        	}
        }
        if ( !Util.esCadenaVacia( finPlazo  ) )
        {
        	if ( Util.cadenaASqlTimestamp( finPlazo ) == null )
        	{
        		errors.add( "tramiteVersion.finPlazo", new ActionError("errors.tramiteVersion.finPlazo" ) );
        	}
        }
        
        // Verificamos lista idiomas        
        if (this.getIdiomas() == null || this.getIdiomas().length <= 0){
        	errors.add( "tramiteVersion.idiomas", new ActionError("errors.tramiteVersion.noIdiomas" ) );
        }
        
        // Obtenemos valores del formulario
        TramiteVersion tramiteVersion = (TramiteVersion) getValues();
        
        /*
        // Comprobamos en caso de que el destino de trámite sea registro se indique que el trámite debe firmarse        
        if (tramiteVersion.getDestino() == ConstantesSTR.DESTINO_REGISTRO) {
        	if (tramiteVersion.getFirmar() == 'N')
        	{
        		errors.add( "tramiteVersion.firmar", new ActionError("errors.tramiteVersion.rellenarFirmarRegistro" ) );
        	}
        }
        */
        
        // Comprobamos en caso de que el destino de trámite sea consulta se rellenen los parametros de consulta
        if (tramiteVersion.getDestino() == ConstantesSTR.DESTINO_CONSULTA) {
        	
        	// Debe indicarse la url en el caso de ser de tipo EJB y remoto o de ser webservice
        	if ( (tramiteVersion.getConsultaTipoAcceso() == TramiteVersion.CONSULTA_EJB && tramiteVersion.getConsultaLocalizacion() == TramiteVersion.EJB_REMOTO)  ||        		
        		  tramiteVersion.getConsultaTipoAcceso() == TramiteVersion.CONSULTA_WEBSERVICE){
        		if (Util.esCadenaVacia(tramiteVersion.getConsultaUrl())){
        			errors.add("values.consultaUrl", new ActionError("errors.tramiteVersion.urlVacia"));
        		}
        	}
        	
        	//Debe indicarse la versión en el caso de ser de tipo webservice
        	if (tramiteVersion.getConsultaTipoAcceso() == TramiteVersion.CONSULTA_WEBSERVICE){
        		if (Util.esCadenaVacia(tramiteVersion.getConsultaWSVersion())){
        			errors.add("values.consultaWSVersion", new ActionError("errors.tramiteVersion.wsVersionVacia"));
        		}
        	}
        	
        	// Debe indicarse el usuario/password en caso de tener autenticacion explicita estandard
        	if ( tramiteVersion.getConsultaAuth() == Dominio.AUTENTICACION_EXPLICITA_ESTANDAR){
          		if (Util.esCadenaVacia(userPlain)){
          			errors.add("userPlain", new ActionError("errors.tramiteVersion.usrVacia"));
          		}
          		if (Util.esCadenaVacia(passPlain)){
          			errors.add("passPlain", new ActionError("errors.tramiteVersion.pwdVacia"));
          		}
          	}
        	
        }
        

        try 
        {
            // Comprovar que la versión no está repetida            
            TramiteVersionDelegate delegate = DelegateUtil.getTramiteVersionDelegate();
            Set listaVersiones = delegate.listarTramiteVersiones( getIdTramite() );
            for ( Iterator it = listaVersiones.iterator(); it.hasNext(); )
            {
            	TramiteVersion tmp = ( TramiteVersion ) it.next();
            	if ( tmp.getVersion() == tramiteVersion.getVersion()  && !tmp.getCodigo().equals( tramiteVersion.getCodigo() ) )
                {
            		errors.add("tramite.version", new ActionError("errors.tramiteVersion.duplicado", "" + tramiteVersion.getVersion() ));
                }
            }
        
    	}
    	catch (Exception e) 
    	{
            log.error(e);
        } 
        /*
    	catch (DelegateException e) 
    	{
            log.error(e);
        }
        */
        return errors;
    }
	
	 
	public String getFinPlazo()
	{
		return finPlazo;
	}

	public void setFinPlazo(String finPlazo)
	{
		this.finPlazo = finPlazo;
	}

	public String getInicioPlazo()
	{
		return inicioPlazo;
	}

	public void setInicioPlazo(String inicioPlazo)
	{
		this.inicioPlazo = inicioPlazo;
	}

	public void reset(ActionMapping mapping, HttpServletRequest request) 
	{
		super.reset( mapping, request );
		this.setInicioPlazo(null);
		this.setFinPlazo(null);
		this.setIdiomas(null);
		
		TramiteVersion tv = (TramiteVersion) getValues();
		if (tv != null) tv.setPreenvioConfirmacionAutomatica('N');
		
	}

	public String[] getIdiomas() {
		return idiomas;
	}

	public void setIdiomas(String[] idiomas) {
		this.idiomas = idiomas;
	}

	public String getPassPlain() {
		return passPlain;
	}

	public void setPassPlain(String passPlain) {
		this.passPlain = passPlain;
	}

	public String getUserPlain() {
		return userPlain;
	}

	public void setUserPlain(String userPlain) {
		this.userPlain = userPlain;
	}
	

}

