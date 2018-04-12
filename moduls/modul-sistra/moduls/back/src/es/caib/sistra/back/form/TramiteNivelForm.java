package es.caib.sistra.back.form;

import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import es.caib.sistra.back.taglib.Constants;
import es.caib.sistra.back.util.Util;
import es.caib.sistra.model.TramiteNivel;
import es.caib.sistra.persistence.delegate.DelegateException;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.sistra.persistence.delegate.TramiteNivelDelegate;

public class TramiteNivelForm extends TramiteValidatorForm
{
	private Long idTramiteVersion = null;
	
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

	private String [] nivelesAutenticacionSelected = null;

	public Long getIdTramiteVersion()
	{
		return idTramiteVersion;
	}

	public void setIdTramiteVersion(Long idTramiteVersion)
	{
		this.idTramiteVersion = idTramiteVersion;
	}
	
	 /** Retorna true si se ha pulsado un botón submit de alta */
    protected boolean isAlta(HttpServletRequest request) {
      return (request.getParameter(Constants.ALTA_PROPERTY) != null);
    }
    
    public void destroy(ActionMapping mapping, HttpServletRequest request) 
	{
        super.destroy( mapping, request );
        this.setValidacionInicioScript( null );
        this.setNivelesAutenticacionSelected( null );
        this.setLocalidadScript( null );
        this.setProvinciaScript( null );
        this.setPaisScript( null );
        this.setCampoRteNif( null );
        this.setCampoRdoNif( null );
        this.setCampoRteNom( null );
        this.setCampoRdoNom( null );
        this.setDatosRpte( null );
        this.setDatosRpdo( null );
        this.setUrlFin( null );
        this.setAvisoEmail(null);
        this.setAvisoSMS(null);
        this.setCheckEnvio(null);
        this.setDestinatarioTramite(null);
        this.setProcedimientoDestinoTramite(null);
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
        	// if ( this.isAlta( request ) )
        	// {
        	
	        	TramiteNivel tramiteNivel = (TramiteNivel) getTramite();
	            TramiteNivelDelegate delegate = DelegateUtil.getTramiteNivelDelegate();
	            //String nivelesAutenticacion
	            Set listaVersiones = delegate.listarTramiteNiveles( getIdTramiteVersion() );
	            
	            // Comprobamos que no esten duplicados los niveles de autenticacion
	            String niveles = "";
	            for (Iterator it = listaVersiones.iterator();it.hasNext();){
	            	TramiteNivel tn = (TramiteNivel) it.next();
	            	if ( tramiteNivel.getCodigo() != null &&  tn.getCodigo().longValue() == tramiteNivel.getCodigo().longValue()) continue;
	            	niveles = niveles + tn.getNivelAutenticacion();
	            }
	            niveles = niveles + Util.concatArrString( this.getNivelesAutenticacionSelected() );
	            
	            
	            if  (
	        			niveles.indexOf(TramiteNivel.AUTENTICACION_ANONIMO) != niveles.lastIndexOf(TramiteNivel.AUTENTICACION_ANONIMO) ||
	        			niveles.indexOf(TramiteNivel.AUTENTICACION_CERTIFICADO) != niveles.lastIndexOf(TramiteNivel.AUTENTICACION_CERTIFICADO) ||
	        			niveles.indexOf(TramiteNivel.AUTENTICACION_USUARIOPASSWORD) != niveles.lastIndexOf(TramiteNivel.AUTENTICACION_USUARIOPASSWORD) 
	        		)
	        	{

	        		errors.add("tramite.nivelAutenticacion", new ActionError("errors.tramiteNivel.duplicado", Util.concatArrString( this.getNivelesAutenticacionSelected() ) ));
	        	}	        	
        	// }
        
    	}
    	catch (DelegateException e) 
    	{
            log.error(e);
        }
        return errors;
    }

	public String[] getNivelesAutenticacionSelected()
	{
		return nivelesAutenticacionSelected;
	}

	public void setNivelesAutenticacionSelected(
			String[] nivelesAutenticacionSelected)
	{
		this.nivelesAutenticacionSelected = nivelesAutenticacionSelected;
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

	public String getLocalidadScript()
	{
		return localidadScript;
	}

	public void setLocalidadScript(String localidadScript)
	{
		this.localidadScript = localidadScript;
	}

	public String getPaisScript()
	{
		return paisScript;
	}

	public void setPaisScript(String paisScript)
	{
		this.paisScript = paisScript;
	}

	public String getProvinciaScript()
	{
		return provinciaScript;
	}

	public void setProvinciaScript(String provinciaScript)
	{
		this.provinciaScript = provinciaScript;
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
