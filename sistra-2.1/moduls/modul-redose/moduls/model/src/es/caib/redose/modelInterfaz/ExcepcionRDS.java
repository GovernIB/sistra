package es.caib.redose.modelInterfaz;

import java.rmi.RemoteException;

/**
 * Excepción producida en el RDS 
 * 
 */
public class ExcepcionRDS extends RemoteException {
		
		public ExcepcionRDS() {
			super();
	    }

		public ExcepcionRDS(String message) {
			super(message);
	    }
		
	    public ExcepcionRDS(String message, Throwable cause) {
	        super(message, cause);
	    }
}
