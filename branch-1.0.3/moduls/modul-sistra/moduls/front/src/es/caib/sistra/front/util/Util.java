package es.caib.sistra.front.util;
import javax.naming.InitialContext;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.sistra.persistence.delegate.DelegateUtil;


public class Util
{
	private static String version = null;	
	private static String urlMantenimientoSesion = null;
	
	private static Log log = LogFactory.getLog(Util.class);
	
	/**
	 * Obtiene version (en web.xml)
	 */
	public static String getVersion(){
		if (version == null) {
			try{
				InitialContext ic = new InitialContext();
				version = (String) ic.lookup("java:comp/env/release.cvs.tag");
			}catch(Exception ex){
				log.error("Error obteniendo propiedad 'release.cvs.tag'",ex);
				version = null;
			}		
		}
		return version;
	}
	

	/**
	 * Genera un token
	 * @return Token
	 */
    public static String generateToken()
    {
    	String token = null;
    	try
    	{
	    	 byte[] bytes = String.valueOf(System.currentTimeMillis()).getBytes();
	    	 byte[] encBytes = Base64.encodeBase64(bytes);
		     token = new String(encBytes, "UTF-8");
    	}
    	catch ( Exception exc )
    	{
    		log.error("Error generando token",exc);
    		return null;
    	}
    	return token;
    }
    
	/**
	 * Genera una url para mantener la sesion abierta cuando se salta a otra aplicación (Forms, pagos, etc.)
	 * @param request Request
	 * @return Url mantenimiento sesion
	 */
	public static String generaUrlMantenimientoSesion(String idInstancia){
		if (urlMantenimientoSesion == null) {
			try{
				urlMantenimientoSesion = DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion().getProperty("sistra.url");
				urlMantenimientoSesion += "/sistrafront/protected/mantenimientoSesionForm?"+InstanciaManager.ID_INSTANCIA+"=";
			}catch(Exception ex){
				log.error("Error obteniendo propiedad 'sistra.url'",ex);
				urlMantenimientoSesion = null;
			}		
		}
		return (urlMantenimientoSesion + idInstancia);
		
	}
}
