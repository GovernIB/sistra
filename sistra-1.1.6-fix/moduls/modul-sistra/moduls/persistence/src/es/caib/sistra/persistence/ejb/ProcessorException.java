package es.caib.sistra.persistence.ejb;

import java.rmi.RemoteException;

/**
 * Excepción producida en el procesamiento del tramite 
 * 
 */
public class ProcessorException extends RemoteException {
		
		/**
		 * Codigo del error
		 */
		private String codigoError;
		
		/**
		 * Mensaje dinamico error (si no esta vacio tendra preferencia sobre el codigo error)
		 */
		private String mensajeError;
		
		public ProcessorException(String message,String codError) {
			super(message);
			this.codigoError = codError;			
	    }
		
		public ProcessorException(String message,String codError,String mensajeError) {
			super(message);
			this.codigoError = codError;
			this.mensajeError = mensajeError;
	    }
		
	    public ProcessorException(String message,String codError, Throwable cause) {
	    	super(message, cause);
	    	this.codigoError = codError;	        
	    }

		public String getCodigoError() {
			return codigoError;
		}

		public String getMensajeError() {
			return mensajeError;
		}
}