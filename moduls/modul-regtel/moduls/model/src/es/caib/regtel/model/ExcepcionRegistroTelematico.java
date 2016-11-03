package es.caib.regtel.model;

import java.rmi.RemoteException;

/**
 * Excepción producida en el Registro : api telemática
 * 
 */
public class ExcepcionRegistroTelematico extends RemoteException {
		
		public ExcepcionRegistroTelematico() {
			super();
	    }

		public ExcepcionRegistroTelematico(String message) {
			super(message);
	    }
		
	    public ExcepcionRegistroTelematico(String message, Throwable cause) {
	        super(message, cause);
	    }
}
