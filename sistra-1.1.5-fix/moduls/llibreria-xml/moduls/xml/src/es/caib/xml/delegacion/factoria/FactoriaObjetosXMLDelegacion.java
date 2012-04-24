package es.caib.xml.delegacion.factoria;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

import es.caib.xml.EstablecerPropiedadException;
import es.caib.xml.FactoriaObjetosXML;
import es.caib.xml.delegacion.factoria.impl.AutorizacionDelegacion;

/** Objeto que crea una factoria capaz de crear objetos para manejar
 * datos del documento XML de autorizacion delegacion
 * 
 * @author magroig
 *
 */
public interface FactoriaObjetosXMLDelegacion extends FactoriaObjetosXML {
		
	/** Crea un objeto de autorizacion de delegacion  vacío
	 * @return Objeto de contratos
	 */
	public AutorizacionDelegacion crearAutorizacionDelegacion ();
		
	/** Crea un objeto de autorizacion delegacion que contenga los datos (XML) definidos en el
	 * flujo de datos
	 * @param datosXMLEnvio Flujo de datos con el contenido XML origen
	 * @return Objeto de autorizacion delegacion
	 * @throws es.caib.xml.CargaObjetoXMLException Se ha producido un error al cargar los datos desde el flujo
	 */
	public AutorizacionDelegacion crearAutorizacionDelegacion (InputStream datosXML) 
	throws es.caib.xml.CargaObjetoXMLException;
	
	/** Crea ub objeto de autorizacion delegacion que contenga los datos (XML) definidos en el
	 * fichero de datos
	 * @param ficheroXML Fichero con el contenido XML origen
	 * @return Objeto de autorizacion delegacion
	 * @throws es.caib.xml.CargaObjetoXMLException Se ha producido un error al cargar los datos desde el fichero
	 */
	public AutorizacionDelegacion crearAutorizacionDelegacion (File ficheroXML)
	throws es.caib.xml.CargaObjetoXMLException;
	
	/** Guarda el contenido de un objeto de AutorizacionDelegacion en un flujo de datos de salida (en formato XML)
	 * @param delegacion Objeto de AutorizacionDelegacion
	 * @param datosXML Flujo de datos de salida
	 * @throws es.caib.xml.GuardaObjetoXMLException Se ha producido un error al guardar los datos en el flujo
	 * @throws EstablecerPropiedadException Los datos del objeto de Envio no son correctos
	 */
	public void guardarAutorizacionDelegacion (AutorizacionDelegacion delegacion, OutputStream datosXML) 
	throws es.caib.xml.GuardaObjetoXMLException, EstablecerPropiedadException;
	
	/** Guarda el contenido de un objeto de AutorizacionDelegacion en un fichero de datos de salida (en formato XML)
	 * @param delegacion Objeto de AutorizacionDelegacion
	 * @param ficheroXML Fichero de datos de salida
	 * @throws es.caib.xml.GuardaObjetoXMLException Se ha producido un error al guardar los datos en el fichero
	 * @throws EstablecerPropiedadException Los datos del objeto de Envio no son correctos
	 */
	public void guardarAutorizacionDelegacion (AutorizacionDelegacion delegacion, File ficheroXML)
	throws es.caib.xml.GuardaObjetoXMLException, EstablecerPropiedadException;
	
	/** Guarda el contenido del objeto de AutorizacionDelegacion en un string (en formato XML)
	 * @param delegacion Objeto de AutorizacionDelegacion
	 * @return Cadena con el contenido del objeto de AutorizacionDelegacion en formato XML
	 * @throws es.caib.xml.GuardaObjetoXMLException Se ha producido un error al guardar los datos en el fichero
	 * @throws EstablecerPropiedadException Los datos del objeto de Envio no son correctos
	 */
	public String guardarAutorizacionDelegacion (AutorizacionDelegacion delegacion) 
	throws es.caib.xml.GuardaObjetoXMLException, EstablecerPropiedadException;
	
	
	
					
}
