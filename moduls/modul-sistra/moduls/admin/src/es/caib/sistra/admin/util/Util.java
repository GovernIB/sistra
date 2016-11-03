package es.caib.sistra.admin.util;

import java.util.HashMap;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class Util
{
	private static Log log = LogFactory.getLog( Util.class );
	private static String ROLE_AUDITOR_PARAM = "role.audit";
	private static String ROLE_ADMIN_PARAM = "role.admin";
		
	private static HashMap _hsmParams = new HashMap();	
	
	
	/**
	 * Obtiene un parámetro del contexto jndi ( web.xml )
	 * @param paramName
	 * @return
	 */
	public static String getJNDIParam( String paramName )
	{
		String paramValue = ( String ) _hsmParams.get( paramName );
		if ( paramValue != null )
		{
			return paramValue;
		}
		try{
			InitialContext ic = new InitialContext();
			paramValue = (String) ic.lookup("java:comp/env/" + paramName );
			_hsmParams.put( paramName, paramValue );
		}catch(Exception ex){
			log.error( "Intentando obtener parámetro " + paramName + " del contexto web",  ex );
		}
		return paramValue;
	}
	
	public static String getRoleAuditor()
	{
		return getJNDIParam( ROLE_AUDITOR_PARAM );
	}
	
	public static String getRoleAdmin()
	{
		return getJNDIParam( ROLE_ADMIN_PARAM );
	}
	
    
	public static boolean hasRoleAuditor( HttpServletRequest request )
    {
    	return request.isUserInRole( Util.getRoleAuditor() );
    }
	
	public static boolean hasRoleAdmin( HttpServletRequest request )
    {
		return request.isUserInRole( Util.getRoleAdmin() );
    }
	
    public static void setAuditorMode( HttpServletRequest request )
    {
    	request.setAttribute( "isAuditor", new Boolean( true ) );
    	request.setAttribute( "isAdmin", new Boolean( false ) );
    }
    
    public static void setDevelopperMode( HttpServletRequest request )
    {
    	request.setAttribute( "isAuditor", new Boolean( false ) );
    	request.setAttribute( "isAdmin", new Boolean( true ) );
    }
		
}
