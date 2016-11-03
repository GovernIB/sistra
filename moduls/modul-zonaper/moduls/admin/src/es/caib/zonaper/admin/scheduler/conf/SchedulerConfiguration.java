package es.caib.zonaper.admin.scheduler.conf;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.zonaper.persistence.delegate.DelegateUtil;

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
		try
		{
			properties = DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion();
		}
		catch( Exception exc )
		{
			log.error( exc );
		}
	}
		
}
