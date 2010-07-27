package es.caib.sistra.front.storage;

import java.io.Serializable;

public interface Storable extends Serializable
{
	public long getExpirationTime();
	public void setExpirationTime( long expirationTime );
}
