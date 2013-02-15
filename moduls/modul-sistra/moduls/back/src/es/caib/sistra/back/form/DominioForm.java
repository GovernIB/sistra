package es.caib.sistra.back.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import es.caib.sistra.back.util.Util;
import es.caib.sistra.model.Dominio;
import es.caib.sistra.persistence.delegate.DelegateException;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.sistra.persistence.delegate.DominioDelegate;


public class DominioForm extends TraForm implements InitForm
{

	protected static Log log = LogFactory.getLog(DominioForm.class);
	private Long idOrgano = null;
	
	private String userPlain,passPlain;
	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) 
	{
        ActionErrors errors = super.validate(mapping, request);
        if (errors == null) 
        {
            errors = new ActionErrors();
        }
        try 
        {
        	Dominio dominio = ( Dominio ) this.getValues();
        	DominioDelegate delegate = DelegateUtil.getDominioDelegate();
        	Dominio dominioTmp = delegate.obtenerDominio( dominio.getIdentificador() );
        	if ( dominioTmp != null && !dominioTmp.getCodigo().equals( dominio.getCodigo() ) )
        	{
        		errors.add("values.identificador", new ActionError("errors.dominio.duplicado", dominio.getIdentificador() ));
        	}        	
        	
        	// Si el dominio es de tipo SQL se debe especificar la SQL
        	if (dominio.getTipo() == Dominio.DOMINIO_SQL && Util.esCadenaVacia(dominio.getSql())){
        		errors.add("values.sql", new ActionError("errors.dominio.sqlVacia"));
        	}
        	
        	// Debe indicarse la url en el caso de ser de tipo SQL, de tipo EJB y además remoto o de tipo webservice
        	if ( (dominio.getTipo() == Dominio.DOMINIO_EJB && dominio.getLocalizacionEJB() == Dominio.EJB_REMOTO)  ||        		
        		  dominio.getTipo() == Dominio.DOMINIO_WEBSERVICE ||
        		  dominio.getTipo() == Dominio.DOMINIO_SQL ||
        		  dominio.getTipo() == Dominio.DOMINIO_FUENTE_DATOS
        		  ){
        		if (Util.esCadenaVacia(dominio.getUrl())){
        			errors.add("values.url", new ActionError("errors.dominio.urlVacia"));
        		}
        	}
        	
        	// Debe indicarse la url en el caso de ser de tipo SQL, de tipo EJB y además remoto o de tipo webservice
        	if ( dominio.getTipo() == Dominio.DOMINIO_WEBSERVICE ){
        		if (Util.esCadenaVacia(dominio.getVersionWS())){
        			errors.add("values.versionWS", new ActionError("errors.dominio.versionWSVacia"));
        		}
        	}
        	
        	// Debe indicarse el usuario/password en caso de tener autenticacion explicita estandard
        	if ( dominio.getAutenticacionExplicita() == Dominio.AUTENTICACION_EXPLICITA_ESTANDAR){
          		if (Util.esCadenaVacia(userPlain)){
          			errors.add("userPlain", new ActionError("errors.dominio.usrVacia"));
          		}
          		if (Util.esCadenaVacia(passPlain)){
          			errors.add("passPlain", new ActionError("errors.dominio.pwdVacia"));
          		}
          	}
        }
	    catch (DelegateException e) 
	    {
	        log.error(e);
	    }
	    return errors;

	}

	public Long getIdOrgano()
	{
		return idOrgano;
	}

	public void setIdOrgano(Long idOrgano)
	{
		this.idOrgano = idOrgano;
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
