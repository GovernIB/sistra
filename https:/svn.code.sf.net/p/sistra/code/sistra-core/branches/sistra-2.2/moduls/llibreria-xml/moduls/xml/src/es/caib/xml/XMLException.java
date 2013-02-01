package es.caib.xml;

/** 
 * @author magroig
 * Clase base para modelizar excepciones referentes a la manipulación
 * de objetos representando información de los ficheros XML de registro
 */
public class XMLException extends Exception {
	
	protected XMLException (String msg){
		super (msg);
	}
	
	protected XMLException (String msg,Throwable cause){
		super (msg,cause);
	}
}
