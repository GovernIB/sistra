package es.caib.xml.avisonotificacion.factoria;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import es.caib.xml.EstablecerPropiedadException;
import es.caib.xml.FactoriaObjetosXML;
import es.caib.xml.avisonotificacion.factoria.impl.AvisoNotificacion;
import es.caib.xml.avisonotificacion.factoria.impl.Expediente;

/** Objeto que crea una factoria capaz de crear objetos para manejar
 * datos del documento XML de aviso notificacion
 * 
 * @author magroig
 *
 */
public interface FactoriaObjetosXMLAvisoNotificacion extends FactoriaObjetosXML {
		
	/** Crea un objeto de aviso notificacion vacío
	 * @return Objeto de aviso notificacion
	 */
	public AvisoNotificacion crearAvisoNotificacion ();
	
	/** Crea un objeto de expediente vacío
	 * @return Objeto de expediente
	 */
	public Expediente crearExpediente ();
		
	/** Crea ub objeto de aviso notificacion que contenga los datos (XML) definidos en el
	 * flujo de datos
	 * @param datosXMLAvisoNotificacion Flujo de datos con el contenido XML origen
	 * @return Objeto de aviso notificacion
	 * @throws es.caib.xml.CargaObjetoXMLException Se ha producido un error al cargar los datos desde el flujo
	 */
	public AvisoNotificacion crearAvisoNotificacion (InputStream datosXMLAvisoNotificacion) 
	throws es.caib.xml.CargaObjetoXMLException;
	
	/** Crea ub objeto de aviso notificacion que contenga los datos (XML) definidos en el
	 * fichero de datos
	 * @param ficheroXMLAvisoNotificacion Fichero con el contenido XML origen
	 * @return Objeto de aviso notificacion
	 * @throws es.caib.xml.CargaObjetoXMLException Se ha producido un error al cargar los datos desde el fichero
	 */
	public AvisoNotificacion crearAvisoNotificacion (File ficheroXMLAvisoNotificacion)
	throws es.caib.xml.CargaObjetoXMLException;
	
	/** Guarda el contenido de un objeto de aviso notificacion en un flujo de datos de salida (en formato XML)
	 * @param avisoNotificacion Objeto de aviso notificacion
	 * @param datosXMLavisoNotificacion Flujo de datos de salida
	 * @throws es.caib.xml.GuardaObjetoXMLException Se ha producido un error al guardar los datos en el flujo
	 * @throws EstablecerPropiedadException Los datos del objeto de aviso notificacion no son correctos
	 */
	public void guardarAvisoNotificacion (AvisoNotificacion avisoNotificacion, OutputStream datosXMLavisoNotificacion) 
	throws es.caib.xml.GuardaObjetoXMLException, EstablecerPropiedadException;
	
	/** Guarda el contenido de un objeto de aviso notificacion en un fichero de datos de salida (en formato XML)
	 * @param avisoNotificacion Objeto de aviso notificacion
	 * @param ficheroXMLAvisoNotificacion Fichero de datos de salida
	 * @throws es.caib.xml.GuardaObjetoXMLException Se ha producido un error al guardar los datos en el fichero
	 * @throws EstablecerPropiedadException Los datos del objeto de aviso notificacion no son correctos
	 */
	public void guardarAvisoNotificacion (AvisoNotificacion avisoNotificacion, File ficheroXMLAvisoNotificacion)
	throws es.caib.xml.GuardaObjetoXMLException, EstablecerPropiedadException;
	
	/** Guarda el contenido del objeto de aviso notificacion en un string (en formato XML)
	 * @param avisoNotificacion Objeto de aviso notificacion
	 * @return Cadena con el contenido del objeto de aviso notificacion en formato XML
	 * @throws es.caib.xml.GuardaObjetoXMLException Se ha producido un error al guardar los datos en el fichero
	 * @throws EstablecerPropiedadException Los datos del objeto de aviso notificacion no son correctos
	 */
	public String guardarAvisoNotificacion (AvisoNotificacion avisoNotificacion) 
	throws es.caib.xml.GuardaObjetoXMLException, EstablecerPropiedadException;
					
}
