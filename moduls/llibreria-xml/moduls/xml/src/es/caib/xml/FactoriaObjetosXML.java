package es.caib.xml;

/** Interfaz generica para el modelado de una factoria de creacion
 *  de objeros XML
 * @author magroig
 *
 */
public interface FactoriaObjetosXML {
	/** Obtiene el encoding que se usará para generar contenido XML
	 * @return Encoding con el que se genera contenido XML
	 */
	public String getEncoding();
	
	/** Establece la codificacion que se usará para generar contenido XML
	 * @param encoding Codificacion que se usará para generar contenido XML
	 */
	public void setEncoding (String encoding);
	
	/** Obtiene propiedad de si se identan los XML generados
	 * @return Si se identan los XML generados
	 */
	public boolean isIdentacion ();
	
	/** Establece si se identan los XML generados
	 * @param indentacion Si se identan los XML generados
	 */
	public void setIndentacion (boolean indentacion);
}
