package es.caib.sistra.plugins;

/**
 * 
 * Excepcion que indica que no se ha configurado el plugin  en el properties
 * 
 */
public class NoExistePluginException extends Exception{

	public NoExistePluginException(String message) {
		super(message);
	}
	
}
