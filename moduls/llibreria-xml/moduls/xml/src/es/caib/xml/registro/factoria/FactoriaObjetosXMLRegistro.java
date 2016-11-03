package es.caib.xml.registro.factoria;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import es.caib.xml.EstablecerPropiedadException;
import es.caib.xml.FactoriaObjetosXML;
import es.caib.xml.registro.factoria.impl.AsientoRegistral;
import es.caib.xml.registro.factoria.impl.DatosAnexoDocumentacion;
import es.caib.xml.registro.factoria.impl.DatosAsunto;
import es.caib.xml.registro.factoria.impl.DatosDestino;
import es.caib.xml.registro.factoria.impl.DatosInteresado;
import es.caib.xml.registro.factoria.impl.DatosOrigen;
import es.caib.xml.registro.factoria.impl.DireccionCodificada;
import es.caib.xml.registro.factoria.impl.IdentificacionInteresadoDesglosada;
import es.caib.xml.registro.factoria.impl.Justificante;


/** Objeto que crea una factoria capaz de crear objetos para manejar
 * datos del documento XML de justificante
 *  
 * @author magroig
 */
public interface FactoriaObjetosXMLRegistro extends FactoriaObjetosXML {
		
	/** Crea un objeto de justificante vacío
	 * @return Objeto de justificante vacío
	 */
	public Justificante crearJustificanteRegistro ();	
		
	/** Crea un objeto de justificante que contenga los datos (XML) definidos en el
	 * flujo de datos
	 * @param datosXMLJustificante Flujo de datos XML
	 * @return Objeto de justificante con los datos cargados
	 * @throws es.caib.xml.CargaObjetoXMLException Se ha producido un error 
	 * al cargar los datos desde el flujo
	 */
	public Justificante crearJustificanteRegistro (InputStream datosXMLJustificante) 
	throws es.caib.xml.CargaObjetoXMLException;
		
	/**Crea un objeto de justificante que contenga los datos (XML) definidos en un
	 * fichero
	 * @param ficheroXMLJustificante Fichero de datos XML
	 * @return Objeto de justificante con los datos cargadoss
	 * @throws es.caib.xml.CargaObjetoXMLException Se ha producido un error 
	 * al cargar los datos desde el fichero
	 */
	public Justificante crearJustificanteRegistro (File ficheroXMLJustificante)
	throws es.caib.xml.CargaObjetoXMLException;	
		
	/** Guarda el contenido de un objeto de justificante en un flujo de datos de salida (en formato XML)
	 * @param justificante Objeto de justificante con los datos cargados
	 * @param datosXMLJustificante Flujo de salida
	 * @throws es.caib.xml.GuardaObjetoXMLException Se ha producido un error al guardar los datos en el flujo
	 * @throws EstablecerPropiedadException Los datos del objeto de justificante no son correctos
	 */
	public void guardarJustificanteRegistro (Justificante justificante, OutputStream datosXMLJustificante) 
	throws es.caib.xml.GuardaObjetoXMLException, EstablecerPropiedadException;
	
	/** Guarda el contenido de un objeto de justificante en un fichero (en formato XML) 
	 * @param justificante Objeto de justificante con los datos cargados
	 * @param ficheroXMLJustificante Fichero de salida
	 * @throws es.caib.xml.GuardaObjetoXMLException Se ha producido un error al guardar los datos en el fichero 
	 * @throws EstablecerPropiedadException Los datos del objeto de justificante no son correctos
	 */
	public void guardarJustificanteRegistro (Justificante justificante, File ficheroXMLJustificante)
	throws es.caib.xml.GuardaObjetoXMLException, EstablecerPropiedadException;
				
	/** Retorna el contenido de un objeto de justificante en un String (en formato XML)
	 * @param justificante Objeto de justificante con los datos cargados
	 * @return String con los datos en formato XML
	 * @throws es.caib.xml.GuardaObjetoXMLException Se ha producido un error al guardar los datos en el String
	 * @throws EstablecerPropiedadException Los datos del objeto de justificante no son correctos
	 */
	public String guardarJustificanteRegistro (Justificante justificante)
	throws es.caib.xml.GuardaObjetoXMLException, EstablecerPropiedadException;
	
	/* (non-Javadoc)
	 * @see es.caib.xml.FactoriaObjetosXML#isIdentacion()
	 */
	public boolean isIdentacion ();
		
	/* (non-Javadoc)
	 * @see es.caib.xml.FactoriaObjetosXML#setIndentacion(boolean)
	 */
	public void setIndentacion (boolean indentacion);
				
	/** Crea un objeto asiento registral vacío (sin datos)
	 * @return Objeto asiento registral
	 */
	public AsientoRegistral crearAsientoRegistral ();
		
	/** Crea un objeto DatosAnexoDocumentacion vacío (sin datos)
	 * @return Objeto DatosAnexoDocumentacion
	 */
	public DatosAnexoDocumentacion crearDatosAnexoDocumentacion ();
		
	/** Crea un objeto DatosAsunto vacío (sin datos)
	 * @return Objeto DatosAsunto
	 */
	public DatosAsunto crearDatosAsunto ();
		
	/** Crea un objeto DatosDestino vacío (sin datos)
	 * @return Objeto DatosDestino
	 */
	public DatosDestino crearDatosDestino ();
		
	/** Crea un objeto DatosInteresado vacío (sin datos)
	 * @return Objeto DatosInteresado
	 */
	public DatosInteresado crearDatosInteresado ();	
	
	/** Crea un objeto DatosOrigen vacío (sin datos)
	 * @return Objeto DatosOrigen vacío (sin datos)
	 */
	public DatosOrigen crearDatosOrigen ();	
	
	/** Crea un objeto DireccionCodificada vacío (sin datos)
	 * @return Objeto DireccionCodificada
	 */
	public DireccionCodificada crearDireccionCodificada ();
	
	/** Crea un objeto IdentificacionInteresadoDesglosada vacío (sin datos)
	 * @return Objeto IdentificacionInteresadoDesglosada
	 */
	public IdentificacionInteresadoDesglosada crearIdentificacionInteresadoDesglosada ();
	
	
	/** Crea un objeto de asiento registral que contenga los datos (XML) definidos en el
	 * flujo de datos
	 * @param ficheroXMLAsientoRegistral Flujo de datos XML
	 * @return Asiento Reggistral con los datos cargados
	 * @throws es.caib.xml.CargaObjetoXMLException Se ha producido un error al obtener
	 * datos desde el flujo
	 */
	public AsientoRegistral crearAsientoRegistral (InputStream ficheroXMLAsientoRegistral) 
	throws es.caib.xml.CargaObjetoXMLException;	
	
	/** Crea un objeto de asiento registral que contenga los datos (XML) definidos en el
	 * fichero
	 * @param ficheroXMLAsientoRegistral Fichero XML
	 * @return Asiento Registral con los datos cargados
	 * @throws es.caib.xml.CargaObjetoXMLException Se ha producido un error al obtener
	 * datos desde el fichero
	 */
	public AsientoRegistral crearAsientoRegistral (File ficheroXMLAsientoRegistral)
	throws es.caib.xml.CargaObjetoXMLException;	
	
	/** Guarda el contenido de un objeto de asiento registral en un flujo de datos de salida (en formato XML)
	 * @param asiento Asiento Registral con los datos cargados
	 * @param datosXMLAsientoRegistral Flujo de salida
	 * @throws es.caib.xml.GuardaObjetoXMLException Se ha producido un error al guardar
	 * los datos en el flujo
	 * @throws EstablecerPropiedadException Algún dato del asiento registral es inválido
	 */
	public void guardarAsientoRegistral (AsientoRegistral asiento, OutputStream datosXMLAsientoRegistral) 
	throws es.caib.xml.GuardaObjetoXMLException, EstablecerPropiedadException;
		
	/** Guarda el contenido de un objeto de asiento registral en un fichero (en formato XML)
	 * @param asiento Asiento registral con los datos cargados
	 * @param ficheroXMLAsientoRegistral Fichero de salida
	 * @throws es.caib.xml.GuardaObjetoXMLException Se ha producido un error al guardar
	 * los datos en el fichero 
	 * @throws EstablecerPropiedadException Algún dato del asiento registral es inválido
	 */
	public void guardarAsientoRegistral (AsientoRegistral asiento, File ficheroXMLAsientoRegistral)
	throws es.caib.xml.GuardaObjetoXMLException, EstablecerPropiedadException;		
	
	/** Guarda y retorna el contenido de un objeto de asiento registral en un String (en formato XML)
	 * @param asiento Asiento Registral con los datos cargados
	 * @return String con el contenido XML
	 * @throws es.caib.xml.GuardaObjetoXMLException Se ha producido un error al guardar
	 * los datos en el String
	 * @throws EstablecerPropiedadException Algún dato del asiento registral es inválido
	 */
	public String guardarAsientoRegistral (AsientoRegistral asiento) 
	throws es.caib.xml.GuardaObjetoXMLException, EstablecerPropiedadException;
}
