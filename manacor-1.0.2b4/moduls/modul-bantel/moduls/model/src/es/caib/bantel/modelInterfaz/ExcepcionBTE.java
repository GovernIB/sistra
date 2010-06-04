package es.caib.bantel.modelInterfaz;

import java.rmi.RemoteException;

/**
 * Excepci�n producida al operar con la Bandeja Telem�tica
 * 
 */
public class ExcepcionBTE extends RemoteException {
		
		public ExcepcionBTE() {
			super();
	    }

		public ExcepcionBTE(String message) {
			super(message);
	    }
		
	    public ExcepcionBTE(String message, Throwable cause) {
	        super(message, cause);
	    }
}
