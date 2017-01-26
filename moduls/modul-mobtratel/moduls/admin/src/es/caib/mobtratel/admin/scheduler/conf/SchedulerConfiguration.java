package es.caib.mobtratel.admin.scheduler.conf;

import java.util.Map;

import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.mobtratel.admin.util.UsernamePasswordCallbackHandler;
import es.caib.mobtratel.persistence.util.ConfigurationUtil;
import es.caib.mobtratel.persistence.delegate.DelegateUtil;

public class SchedulerConfiguration
{
	Log log = LogFactory.getLog( SchedulerConfiguration.class );
	Map properties;
	
	private static SchedulerConfiguration _instance = null;
	
	public static SchedulerConfiguration getInstance()
	{
		return _instance == null ? _instance = new SchedulerConfiguration() : _instance; 
	}
	
	public String get( String name )
	{
		return ( String ) properties.get( name );
	}
	
	private SchedulerConfiguration()
	{
		
		LoginContext lc = null;
		
		try
		{
			String user = ConfigurationUtil.getInstance().obtenerPropiedades().getProperty("auto.user");
			String pass = ConfigurationUtil.getInstance().obtenerPropiedades().getProperty("auto.pass");
			
            CallbackHandler handler = new UsernamePasswordCallbackHandler( user, pass );
            
            lc = new LoginContext("client-login", handler);
            lc.login();
			
			properties = DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion();
		}catch (Exception ex){
			// Informamos al log                    
			log.error("Error obteniendo configuracion: " + ex.getMessage(),ex);
		}finally{
			if ( lc != null ){
				try{lc.logout();}catch(Exception exl){}
			}                                 
		}
	}		
}
