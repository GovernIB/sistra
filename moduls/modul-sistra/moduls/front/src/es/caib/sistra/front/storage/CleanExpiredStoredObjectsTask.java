package es.caib.sistra.front.storage;

import java.util.TimerTask;


// TODO: Considerar si se deben de crear varias TimerTask y adicionar al Timer para que los objetos
// del storage expiren por si mismo o por el contrario solo hay que crear una TimerTask
// que se recorra cada cierto tiempo 
public abstract class CleanExpiredStoredObjectsTask extends TimerTask
{
	public abstract String getObjectKey();
	public abstract StorageScope getScope(); 
	
	public void run()
	{
		getScope().removeObject( getObjectKey() );
	}

}
