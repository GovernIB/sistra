package es.caib.audita.admin.scheduler.conf;

import java.util.Map;
import java.util.Properties;

import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionException;

import es.caib.audita.admin.util.UsernamePasswordCallbackHandler;
import es.caib.audita.persistence.delegate.DelegateUtil;
import es.caib.audita.persistence.util.ConfigurationUtil;



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
			// Realizamos login JAAS con usuario para proceso automatico
            //String user = SchedulerConfiguration.getInstance().get("auto.user");
            //String pass = SchedulerConfiguration.getInstance().get("auto.pass");
			String user = ConfigurationUtil.getInstance().obtenerPropiedades().getProperty("auto.user");
			String pass = ConfigurationUtil.getInstance().obtenerPropiedades().getProperty("auto.pass");
			
            CallbackHandler handler = new UsernamePasswordCallbackHandler( user, pass );
            
            lc = new LoginContext("client-login", handler);
            lc.login();

			log.info("******** ACCEDEMOS A CAPA EJB PARA RECUPERACION DE PROPIEDADES ********");
			properties = DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion();
			log.info("++++++++ SALIMOS DE CAPA EJB PARA RECUPERACION DE PROPIEDADES ++++++++");
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
