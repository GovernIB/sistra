package es.caib.xml.analiza.formdoc;

/**
 * Excepci�n al analizar documento.
 * @author rsanz
 *
 */
public class AnalizadorDocException extends Exception {
	

	/**
	 * Constructor.
	 * @param message Mensaje excepci�n
	 */
	public AnalizadorDocException(String message) {
		super(message);
	}
	
	/**
	 * Constructor.
	 * @param message Mensaje excepci�n
	 * @param origen Excepci�n origen
	 */
	public AnalizadorDocException(String message, Throwable origen) {
		super(message, origen);
	}
	
}
