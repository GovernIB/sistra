package es.caib.sistra.front.storage;

public interface StorageScope
{
	public void init( Object initParam );
	public void storeObject( String key, Storable storableObject );
	public Storable getObject( String key  );
	public void removeObject( String key );
	public long getExpirationInterval();
}
