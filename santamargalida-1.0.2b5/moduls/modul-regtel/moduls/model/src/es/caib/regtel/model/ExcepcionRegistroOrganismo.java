package es.caib.regtel.model;

import java.rmi.RemoteException;

/**
 * Excepci�n producida en el Registro : api registro organismo
 * 
 */
public class ExcepcionRegistroOrganismo extends RemoteException {
		
		public ExcepcionRegistroOrganismo() {
			super();
	    }

		public ExcepcionRegistroOrganismo(String message) {
			super(message);
	    }
		
	    public ExcepcionRegistroOrganismo(String message, Throwable cause) {
	        super(message, cause);
	    }
}
