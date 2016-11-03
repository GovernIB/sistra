package es.caib.xml.analiza.formdoc;

/**
 * Excepción al analizar documento.
 * @author rsanz
 *
 */
public class AnalizadorDocException extends Exception {
	

	/**
	 * Constructor.
	 * @param message Mensaje excepción
	 */
	public AnalizadorDocException(String message) {
		super(message);
	}
	
	/**
	 * Constructor.
	 * @param message Mensaje excepción
	 * @param origen Excepción origen
	 */
	public AnalizadorDocException(String message, Throwable origen) {
		super(message, origen);
	}
	
}
