package es.caib.bantel.persistence.delegate;

import es.caib.bantel.persistence.delegate.DelegateFactory;
import es.caib.bantel.persistence.delegate.BteDelegate;

/**
 * Define métodos estáticos para obtener delegates del interfaz de la BTE.
 */
public class DelegateBTEUtil {
	
	    private DelegateBTEUtil() {

	    }
	    
	    public static BteDelegate getBteDelegate() {
	        return (BteDelegate) DelegateFactory.getDelegate(BteDelegate.class);
	    }
	    
	    public static BteSistraDelegate getBteSistraDelegate() {
	        return (BteSistraDelegate) DelegateFactory.getDelegate(BteSistraDelegate.class);
	    }
	        
	}
