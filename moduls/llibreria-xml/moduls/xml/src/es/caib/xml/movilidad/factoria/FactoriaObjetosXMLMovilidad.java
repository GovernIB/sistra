package es.caib.xml.movilidad.factoria;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

import es.caib.xml.EstablecerPropiedadException;
import es.caib.xml.FactoriaObjetosXML;
import es.caib.xml.movilidad.factoria.impl.Envio;
import es.caib.xml.movilidad.factoria.impl.MensajeEMAIL;
import es.caib.xml.movilidad.factoria.impl.MensajeSMS;

/** Objeto que crea una factoria capaz de crear objetos para manejar
 * datos del documento XML de envio al modulo de movilidad
 * 
 * @author magroig
 *
 */
public interface FactoriaObjetosXMLMovilidad extends FactoriaObjetosXML {
		
	/** Crea un objeto de envio  vacío
	 * @return Objeto de contratos
	 */
	public Envio crearEnvio ();
		
	/** Crea un objeto de envio que contenga los datos (XML) definidos en el
	 * flujo de datos
	 * @param datosXMLEnvio Flujo de datos con el contenido XML origen
	 * @return Objeto de envio
	 * @throws es.caib.xml.CargaObjetoXMLException Se ha producido un error al cargar los datos desde el flujo
	 */
	public Envio crearEnvio (InputStream datosXMLEnvio) 
	throws es.caib.xml.CargaObjetoXMLException;
	
	/** Crea ub objeto de Envio que contenga los datos (XML) definidos en el
	 * fichero de datos
	 * @param ficheroXMLEnvio Fichero con el contenido XML origen
	 * @return Objeto de Envio
	 * @throws es.caib.xml.CargaObjetoXMLException Se ha producido un error al cargar los datos desde el fichero
	 */
	public Envio crearEnvio (File ficheroXMLEnvio)
	throws es.caib.xml.CargaObjetoXMLException;
	
	/** Guarda el contenido de un objeto de Envio en un flujo de datos de salida (en formato XML)
	 * @param envio Objeto de Envio
	 * @param datosXMLEnvio Flujo de datos de salida
	 * @throws es.caib.xml.GuardaObjetoXMLException Se ha producido un error al guardar los datos en el flujo
	 * @throws EstablecerPropiedadException Los datos del objeto de Envio no son correctos
	 */
	public void guardarEnvio (Envio envio, OutputStream datosXMLEnvio) 
	throws es.caib.xml.GuardaObjetoXMLException, EstablecerPropiedadException;
	
	/** Guarda el contenido de un objeto de Envio en un fichero de datos de salida (en formato XML)
	 * @param envio Objeto de Envio
	 * @param ficheroXMLEnvio Fichero de datos de salida
	 * @throws es.caib.xml.GuardaObjetoXMLException Se ha producido un error al guardar los datos en el fichero
	 * @throws EstablecerPropiedadException Los datos del objeto de Envio no son correctos
	 */
	public void guardarEnvio (Envio envio, File ficheroXMLEnvio)
	throws es.caib.xml.GuardaObjetoXMLException, EstablecerPropiedadException;
	
	/** Guarda el contenido del objeto de Envio en un string (en formato XML)
	 * @param envio Objeto de Envio
	 * @return Cadena con el contenido del objeto de Envio en formato XML
	 * @throws es.caib.xml.GuardaObjetoXMLException Se ha producido un error al guardar los datos en el fichero
	 * @throws EstablecerPropiedadException Los datos del objeto de Envio no son correctos
	 */
	public String guardarEnvio (Envio envio) 
	throws es.caib.xml.GuardaObjetoXMLException, EstablecerPropiedadException;
	
	/**
	 * Crea objeto MensajeSMS
	 * @return
	 */
	public MensajeSMS crearMensajeSMS ();
	
	/**
	 * Crea objeto MensajeEMAIL
	 * @return
	 */
	public MensajeEMAIL crearMensajeEMAIL ();
	
	
	
					
}
