package es.caib.sistra.front.storage;

import java.util.Map;

public abstract class StorageScopeImpl implements StorageScope
{
	public abstract void init(Object initParam);

	public void storeObject(String key, Storable storableObject)
	{
		getStorageZone().put( key, storableObject );
	}

	public Storable getObject(String key)
	{
		return ( Storable ) getStorageZone().get( key );
	}

	public void removeObject(String key)
	{
		getStorageZone().remove( key );
	}
	
	public abstract Map getStorageZone();
}
