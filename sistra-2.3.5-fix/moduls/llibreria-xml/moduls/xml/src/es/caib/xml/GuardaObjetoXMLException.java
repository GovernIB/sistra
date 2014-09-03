package es.caib.xml;

import java.io.OutputStream;

/** Excepción producida al intentar guardar una jerarquía de objetos XML
 *  a partir de un objeto de datos XML
 *   
 * @author magroig
 *
 */
public class GuardaObjetoXMLException extends Exception {

	private String tipoDocumento;
	private OutputStream contenido;
	private String nombreFichero;
	
	/** Crea la excepción, incluyendo información sobre el tipo de
	 * documento sobre el que se ha producido la excepción al guardar
	 * los objetos XML (pe, 'justificante'). Este constructor es
	 * privado y realiza acciones comunes al resto de constructores 
	 * 
	 * @param message Descripción del error producido
	 * 
	 * @param tipoDocumento Cadena identificando el tipo de documento que ha
	 * producido el error al guardar documento (pe, 'justificante')
	 */
	private GuardaObjetoXMLException(String message, String tipoDocumento){
		super (message);
		this.tipoDocumento = tipoDocumento;
	}
	
	/** Crea la excepción, incluyendo información sobre el tipo de
	 * documento sobre el que se ha producido la excepción al guardar
	 * los objetos XML (pe, 'justificante'). Además se incluye
	 * información sobre los contenidos que no se han podido cargar 
	 * 
	 * @param message Descripción del error producido
	 * 
	 * @param tipoDocumento Cadena identificando el tipo de documento que ha
	 * producido el error al guardar el documento (pe, 'justificante')
	 * 
	 * @param contenido Contenido que no se ha podido guardar
	 */
	public GuardaObjetoXMLException(String message, String tipoDocumento, OutputStream contenido){
		this (message, tipoDocumento);		
		this.contenido = contenido;
	}
	
	public GuardaObjetoXMLException(String message, String tipoDocumento, OutputStream contenido,Throwable e){
		super (message,e);
		this.tipoDocumento = tipoDocumento;
		this.contenido = contenido;
	}
	
	/** Crea la excepción, incluyendo información sobre el tipo de
	 * documento sobre el que se ha producido la excepción al guardar
	 * los objetos XML (pe, 'justificante'). Además se incluye
	 * información sobre el nombre de fichero que no ha podido ser
	 * cargado 
	 * 
	 * @param message Descripción del error producido
	 * 
	 * @param tipoDocumento Cadena identificando el tipo de documento que ha
	 * producido el error al guardar el documento (pe, 'justificante')
	 * 
	 * @param nombreFichero Nombre de fichero que no ha podido ser cargado
	 */
	public GuardaObjetoXMLException(String message, String tipoDocumento, String nombreFichero){
		this (message, tipoDocumento);		
		this.nombreFichero = nombreFichero;
	}

	/** Obtiene cadena identificando tipo de documento que ha dado el problema
	 * @return Tipo de documento que ha oacasionado el problema
	 */
	public String getTipoDocumento() {
		return tipoDocumento;
	}

	/** Obtiene el contenido XML que no ha podido ser cargado
	 * @return Contenido XML que no ha podido ser cargado
	 */
	public OutputStream getContenido() {
		return contenido;
	}

	/** Obtiene el nombre de fichero XML que no ha podido ser cargado
	 * @return Nombre de fichero XML que no ha podido ser cargado
	 */
	public String nombreFichero() {
		return nombreFichero;
	}
}
