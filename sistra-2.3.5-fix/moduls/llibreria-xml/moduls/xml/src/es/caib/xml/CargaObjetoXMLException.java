package es.caib.xml;

import java.io.InputStream;


/** Excepci�n producida al intentar cargar una jerarqu�a de objetos XML
 *  a partir de un documento
 *   
 * @author magroig
 *
 */
public class CargaObjetoXMLException extends XMLException {
	
	private String tipoDocumento;
	private transient InputStream contenido;
	private String nombreFichero;
	
	/** Crea la excepci�n, incluyendo informaci�n sobre el tipo de
	 * documento sobre el que se ha producido la excepci�n al cargar
	 * los objetos XML (pe, 'justificante'). Este constructor es
	 * privado y realiza acciones comunes al resto de constructores 
	 * 
	 * @param message Descripci�n del error producido
	 * 
	 * @param tipoDocumento Cadena identificando el tipo de documento que ha
	 * producido el error de carga del documento (pe, 'justificante')
	 */
	private CargaObjetoXMLException(String message, String tipoDocumento){
		super (message);
		this.tipoDocumento = tipoDocumento;
	}
	
	private CargaObjetoXMLException(String message, String tipoDocumento,Throwable cause){
		super (message,cause);
		this.tipoDocumento = tipoDocumento;
	}
	
	/** Crea la excepci�n, incluyendo informaci�n sobre el tipo de
	 * documento sobre el que se ha producido la excepci�n al cargar
	 * los objetos XML (pe, 'justificante'). Adem�s se incluye
	 * informaci�n sobre los contenidos que no se han podido cargar 
	 * 
	 * @param message Descripci�n del error producido
	 * 
	 * @param tipoDocumento Cadena identificando el tipo de documento que ha
	 * producido el error de carga del documento (pe, 'justificante')
	 * 
	 * @param contenido Contenido que no se ha podido cargar
	 */
	public CargaObjetoXMLException(String message, String tipoDocumento, InputStream contenido){
		this (message, tipoDocumento);		
		this.contenido = contenido;
	}
	
	public CargaObjetoXMLException(String message, String tipoDocumento, InputStream contenido,Throwable cause){
		this (message, tipoDocumento,cause);		
		this.contenido = contenido;
	}
	
	/** Crea la excepci�n, incluyendo informaci�n sobre el tipo de
	 * documento sobre el que se ha producido la excepci�n al cargar
	 * los objetos XML (pe, 'justificante'). Adem�s se incluye
	 * informaci�n sobre el nombre de fichero que no ha podido ser
	 * cargado 
	 * 
	 * @param message Descripci�n del error producido
	 * 
	 * @param tipoDocumento Cadena identificando el tipo de documento que ha
	 * producido el error de carga del documento (pe, 'justificante')
	 * 
	 * @param nombreFichero Nombre de fichero que no ha podido ser cargado
	 */
	public CargaObjetoXMLException(String message, String tipoDocumento, String nombreFichero){
		this (message, tipoDocumento);		
		this.nombreFichero = nombreFichero;		
	}
	public CargaObjetoXMLException(String message, String tipoDocumento, String nombreFichero,Throwable cause){
		this (message, tipoDocumento,cause);		
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
	public InputStream getContenido() {
		return contenido;
	}

	/** Obtiene el nombre de fichero XML que no ha podido ser cargado
	 * @return Nombre de fichero XML que no ha podido ser cargado
	 */
	public String nombreFichero() {
		return nombreFichero;
	}

}
