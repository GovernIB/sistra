package es.caib.sistra.front.storage;

import java.util.HashMap;
import java.util.Map;

public class StorageFactory
{
	private static String DEFAULT_IMPLEMENTATION = "es.caib.sistra.front.storage.ServletContextStorageScope";
	private Map mTiposGestoresFlujo;
	private static StorageFactory instance;
	
	private StorageFactory()
	{
		mTiposGestoresFlujo = new HashMap();
	}
	
	public static StorageFactory getInstance()
	{
		if ( instance != null )
		{
			return instance;
		}
		instance = new StorageFactory();
		return instance;
	}
	
	public StorageScope getStorageScope( Object initParam ) throws Exception
	{
		return getStorageScope( DEFAULT_IMPLEMENTATION, initParam );
	}
	
	public StorageScope getStorageScope( String storageScopeClassImplementation, Object initParam ) throws Exception
	{
		Class clazz = ( Class ) mTiposGestoresFlujo.get( storageScopeClassImplementation );
		if ( clazz == null )
		{
			clazz = Class.forName( storageScopeClassImplementation );
			if ( !StorageScope.class.isAssignableFrom( clazz ) )
			{
				throw new Exception( "La clase " + clazz.getName() + " no implementa el interfaz " + StorageScope.class.getName() );
			}
			mTiposGestoresFlujo.put( storageScopeClassImplementation, clazz );
		}
		StorageScope object = ( StorageScope ) clazz.newInstance();
		object.init( initParam );
		return object;
	}
}
