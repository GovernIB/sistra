package es.caib.xml.oficioremision.factoria;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import es.caib.xml.EstablecerPropiedadException;
import es.caib.xml.FactoriaObjetosXML;
import es.caib.xml.oficioremision.factoria.impl.OficioRemision;
import es.caib.xml.oficioremision.factoria.impl.TramiteSubsanacion;

/** Objeto que crea una factoria capaz de crear objetos para manejar
 * datos del documento XML de oficio remision
 * 
 * @author magroig
 *
 */
public interface FactoriaObjetosXMLOficioRemision extends FactoriaObjetosXML {
		
	/** Crea un objeto de oficio remision vacío
	 * @return Objeto de oficio remision
	 */
	public OficioRemision crearOficioRemision ();
		
	/** Crea ub objeto de oficio remision que contenga los datos (XML) definidos en el
	 * flujo de datos
	 * @param datosXMLOficioRemision Flujo de datos con el contenido XML origen
	 * @return Objeto de oficio remision
	 * @throws es.caib.xml.CargaObjetoXMLException Se ha producido un error al cargar los datos desde el flujo
	 */
	public OficioRemision crearOficioRemision (InputStream datosXMLOficioRemision) 
	throws es.caib.xml.CargaObjetoXMLException;
	
	/** Crea ub objeto de oficio remision que contenga los datos (XML) definidos en el
	 * fichero de datos
	 * @param ficheroXMLOficioRemision Fichero con el contenido XML origen
	 * @return Objeto de oficio remision
	 * @throws es.caib.xml.CargaObjetoXMLException Se ha producido un error al cargar los datos desde el fichero
	 */
	public OficioRemision crearOficioRemision (File ficheroXMLOficioRemision)
	throws es.caib.xml.CargaObjetoXMLException;
	
	/** Guarda el contenido de un objeto de oficio remision en un flujo de datos de salida (en formato XML)
	 * @param oficioRemision Objeto de oficio remision
	 * @param datosXMLoficioRemision Flujo de datos de salida
	 * @throws es.caib.xml.GuardaObjetoXMLException Se ha producido un error al guardar los datos en el flujo
	 * @throws EstablecerPropiedadException Los datos del objeto de oficio remision no son correctos
	 */
	public void guardarOficioRemision (OficioRemision oficioRemision, OutputStream datosXMLoficioRemision) 
	throws es.caib.xml.GuardaObjetoXMLException, EstablecerPropiedadException;
	
	/** Guarda el contenido de un objeto de oficio remision en un fichero de datos de salida (en formato XML)
	 * @param oficioRemision Objeto de oficio remision
	 * @param ficheroXMLOficioRemision Fichero de datos de salida
	 * @throws es.caib.xml.GuardaObjetoXMLException Se ha producido un error al guardar los datos en el fichero
	 * @throws EstablecerPropiedadException Los datos del objeto de oficio remision no son correctos
	 */
	public void guardarOficioRemision (OficioRemision oficioRemision, File ficheroXMLOficioRemision)
	throws es.caib.xml.GuardaObjetoXMLException, EstablecerPropiedadException;
	
	/** Guarda el contenido del objeto de oficio remision en un string (en formato XML)
	 * @param oficioRemision Objeto de oficio remision
	 * @return Cadena con el contenido del objeto de oficio remision en formato XML
	 * @throws es.caib.xml.GuardaObjetoXMLException Se ha producido un error al guardar los datos en el fichero
	 * @throws EstablecerPropiedadException Los datos del objeto de oficio remision no son correctos
	 */
	public String guardarOficioRemision (OficioRemision oficioRemision) 
	throws es.caib.xml.GuardaObjetoXMLException, EstablecerPropiedadException;
					
	/** Crea ub objeto de tramite de subsanacion 
	 */
	public TramiteSubsanacion crearTramiteSubsanacion() ;
					
}
