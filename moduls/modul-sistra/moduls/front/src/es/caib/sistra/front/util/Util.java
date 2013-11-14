package es.caib.sistra.front.util;
import java.security.SecureRandom;

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
	private static Boolean entornoDesarrollo = null;
	
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
    		// Generamos token: time + random
    		SecureRandom sr = new SecureRandom();
	 		String rn = "" + sr.nextInt(99999999);
	 		if (rn.length() < 8) {
	 			for (int i=rn.length();i<8;i++){
	 				rn += "0";
	 			}			
	 		}
    		String tokenStr = String.valueOf(System.currentTimeMillis())  + rn;
    		
    		// Pasamos a B64 
			byte[] bytes = tokenStr.getBytes("UTF-8");
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
	
	/**
	 * Indica si es entorno de desarrollo
	 * @return true: entorno desarrollo / false: entorno produccion
	 */
	public static boolean esEntornoDesarrollo(){
		if (entornoDesarrollo  == null) {
			try{
				entornoDesarrollo = new Boolean ("DESARROLLO".equals(DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion().getProperty("entorno")));				
			}catch(Exception ex){
				log.error("Error obteniendo propiedad 'entorno'",ex);
				entornoDesarrollo = null;
			}		
		}
		return (entornoDesarrollo != null?entornoDesarrollo.booleanValue():false);
		
	}
}
