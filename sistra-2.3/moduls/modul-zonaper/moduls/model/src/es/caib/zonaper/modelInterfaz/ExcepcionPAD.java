package es.caib.zonaper.modelInterfaz;

import java.rmi.RemoteException;

/**
 * Excepci�n producida en el Zona Personal 
 * 
 */
public class ExcepcionPAD extends RemoteException {
		
		public ExcepcionPAD() {
			super();
	    }

		public ExcepcionPAD(String message) {
			super(message);
	    }
		
	    public ExcepcionPAD(String message, Throwable cause) {
	        super(message, cause);
	    }
}
