package es.caib.sistra.front.storage;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

public class ServletContextStorageScope extends StorageScopeImpl
{
	private static long STORAGE_INTERVAL = 30 * 3600 * 1000; 
	private static String FORMS_STORAGE_ZONE = "caib.sistra.FORMS_STORAGE_ZONE";
	private ServletContext servletContext = null;
	
	public void init(Object initParam)
	{
		servletContext = ( ServletContext ) initParam;
	}

	public Map getStorageZone()
	{
		Map storageZone = ( Map ) servletContext.getAttribute( FORMS_STORAGE_ZONE ); 
		if ( storageZone == null )
		{
			storageZone = new HashMap();
			servletContext.setAttribute( FORMS_STORAGE_ZONE, storageZone );
		}
		return storageZone;
	}
	 
	public long getExpirationInterval()
	{
		return STORAGE_INTERVAL;
	}

}
