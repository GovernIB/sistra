package es.caib.xml.documentoExternoNotificacion.factoria;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

import es.caib.xml.EstablecerPropiedadException;
import es.caib.xml.FactoriaObjetosXML;
import es.caib.xml.documentoExternoNotificacion.factoria.impl.DocumentoExternoNotificacion;

/** Objeto que crea una factoria capaz de crear objetos para manejar
 * datos del documento XML de envio al modulo de documento externo notificacion
 * 
 * @author magroig
 *
 */
public interface FactoriaObjetosXMLDocumentoExternoNotificacion extends FactoriaObjetosXML {
		
	/** Crea un objeto DocumentoExternoNotificacion  vacío
	 * @return Objeto de DocumentoExternoNotificacion
	 */
	public DocumentoExternoNotificacion crearDocumentoExternoNotificacion ();
		
	/** Crea un objeto de DocumentoExternoNotificacion que contenga los datos (XML) definidos en el
	 * flujo de datos
	 * @param datosXML Flujo de datos con el contenido XML origen
	 * @return Objeto de DocumentoExternoNotificacion
	 * @throws es.caib.xml.CargaObjetoXMLException Se ha producido un error al cargar los datos desde el flujo
	 */
	public DocumentoExternoNotificacion crearDocumentoExternoNotificacion (InputStream datosXML) 
	throws es.caib.xml.CargaObjetoXMLException;
	
	/** Crea ub objeto de DocumentoExternoNotificacion que contenga los datos (XML) definidos en el
	 * fichero de datos
	 * @param ficheroXML Fichero con el contenido XML origen
	 * @return Objeto de DocumentoExternoNotificacion
	 * @throws es.caib.xml.CargaObjetoXMLException Se ha producido un error al cargar los datos desde el fichero
	 */
	public DocumentoExternoNotificacion crearDocumentoExternoNotificacion (File ficheroXML)
	throws es.caib.xml.CargaObjetoXMLException;
	
	/** Guarda el contenido de un objeto de DocumentoExternoNotificacion en un flujo de datos de salida (en formato XML)
	 * @param envio Objeto de DocumentoExternoNotificacion
	 * @param datosXML Flujo de datos de salida
	 * @throws es.caib.xml.GuardaObjetoXMLException Se ha producido un error al guardar los datos en el flujo
	 * @throws EstablecerPropiedadException Los datos del objeto de DocumentoExternoNotificacion no son correctos
	 */
	public void guardarDocumentoExternoNotificacion (DocumentoExternoNotificacion envio, OutputStream datosXML) 
	throws es.caib.xml.GuardaObjetoXMLException, EstablecerPropiedadException;
	
	/** Guarda el contenido de un objeto de DocumentoExternoNotificacion en un fichero de datos de salida (en formato XML)
	 * @param envio Objeto de DocumentoExternoNotificacion
	 * @param ficheroXML Fichero de datos de salida
	 * @throws es.caib.xml.GuardaObjetoXMLException Se ha producido un error al guardar los datos en el fichero
	 * @throws EstablecerPropiedadException Los datos del objeto de DocumentoExternoNotificacion no son correctos
	 */
	public void guardarDocumentoExternoNotificacion (DocumentoExternoNotificacion envio, File ficheroXML)
	throws es.caib.xml.GuardaObjetoXMLException, EstablecerPropiedadException;
	
	/** Guarda el contenido del objeto de DocumentoExternoNotificacion en un string (en formato XML)
	 * @param documentoExternoNotificacion Objeto de DocumentoExternoNotificacion
	 * @return Cadena con el contenido del objeto de DocumentoExternoNotificacion en formato XML
	 * @throws es.caib.xml.GuardaObjetoXMLException Se ha producido un error al guardar los datos en el fichero
	 * @throws EstablecerPropiedadException Los datos del objeto de DocumentoExternoNotificacion no son correctos
	 */
	public String guardarDocumentoExternoNotificacion (DocumentoExternoNotificacion documentoExternoNotificacion) 
	throws es.caib.xml.GuardaObjetoXMLException, EstablecerPropiedadException;			
					
}
