package es.caib.xml.oficioremision.factoria.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.Unmarshaller;

import es.caib.xml.CargaObjetoXMLException;
import es.caib.xml.EstablecerPropiedadException;
import es.caib.xml.GuardaObjetoXMLException;
import es.caib.xml.InicializacionFactoriaException;
import es.caib.xml.oficioremision.factoria.FactoriaObjetosXMLOficioRemision;
import es.caib.xml.oficioremision.modelo.OFICIOREMISION;
import es.caib.xml.oficioremision.modelo.ObjectFactory;

/** Implementa una factoría capaz de crear objetos que encapsulan el acceso
 * al documento XML de oficio remision
 * 
 * @author magroig
 *
 */
public class FactoriaObjetosXMLOficioRemisionImpl implements
		FactoriaObjetosXMLOficioRemision {
	
	private static final String FICHERO_PROPIEDADES_JAXB = "oficioremision_JAXB.properties"; 
	private static final String PAQUETE_FORMULARIO_PROP = "PAQUETE_MODELO_OFICIOREMISION_IMPL";	 
	
	private JAXBContext contextoJAXBOficioRemision;
	private Unmarshaller unmshOficioRemision;
	private Marshaller mshOficioRemision;
	private ObjectFactory ofOficioRemision;

	/** Crea la factoría. Se necesita que exista en el paquete un fichero properies (oficioremision_JAXB.properties),
	 * que debe contener la propiedad PAQUETE_MODELO_OFICIOREMISION_IMPL, cuyo valor indicará cual es el
	 * paquete donde se encuentra la jerarquía de clases generadas mediante JAXB para manejar el acceso
	 * a la creación/recuperación de contenido para el documento de DATOS_PROPIOS
	 * 
	 * @throws InicializacionFactoriaException Se ha producido un error inesperado al incializar la factoría
	 */
	public FactoriaObjetosXMLOficioRemisionImpl () throws InicializacionFactoriaException {
		try {
			
			Properties propsJAXB = obtenerPropiedadesJAXB ();
			
			contextoJAXBOficioRemision = JAXBContext.newInstance (obtenerPaqueteImplOficioRemision (propsJAXB));
			unmshOficioRemision = contextoJAXBOficioRemision.createUnmarshaller();
			mshOficioRemision = contextoJAXBOficioRemision.createMarshaller();	
			ofOficioRemision = new ObjectFactory ();
		} catch (JAXBException e) {
			throw new InicializacionFactoriaException (e.getClass().getName() + " -> " + e.getLocalizedMessage(), 
					"JAXB");
		}
		catch (IOException ioe){
			throw new InicializacionFactoriaException ("Se ha producido un error al leer propiedades JAXB -> " + ioe.getLocalizedMessage(), 
			"JAXB");
		}
	}
		
	
	/** Carga el fichero properties de configuración de la factoría
	 * 
	 * @return Fichero de properties para configuración de la factoría
	 * 
	 * @throws IOException Si se produce un error al acceder al fichero
	 * de properties y sus valores
	 */			
	private Properties obtenerPropiedadesJAXB () throws IOException{
		Properties prop = new Properties ();
		
		prop.load(getClass().getResourceAsStream (FICHERO_PROPIEDADES_JAXB));
		
		return prop;
	}
	
	/**A partir de un objeto properties, retorna el nombre del paquete donde estan las clases generadas
	 * mediante JAXB para acceder al documento
	 * @param propiedadesJAXB Properties de configuracion de la factoria
	 * @return Nombre del paquete donde estan las clases generadas por JAXB
	 * @throws IOException
	 */
	private String obtenerPaqueteImplOficioRemision (Properties propiedadesJAXB) throws IOException {		
		return propiedadesJAXB.getProperty(PAQUETE_FORMULARIO_PROP);
	}
	
	/* (non-Javadoc)
	 * @see es.caib.xml.oficioremision.factoria.FactoriaObjetosXMLOficioRemision#crearOficioRemision()
	 */
	public OficioRemision crearOficioRemision() {		
		return new OficioRemision ();					
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.oficioremision.factoria.FactoriaObjetosXMLOficioRemision#crearOficioRemision(java.io.InputStream)
	 */
	public OficioRemision crearOficioRemision(InputStream datosXMLOficioRemision)
			throws CargaObjetoXMLException {

		OficioRemision oficioRemision = null;
		
		try {			
			OFICIOREMISION oficioRemisionJAXB = (OFICIOREMISION) unmshOficioRemision.unmarshal(datosXMLOficioRemision);
			oficioRemision = crearOficioRemision ();
			cargarDatosDesdeJAXB (oficioRemisionJAXB, oficioRemision);
											
		} catch (Exception e) {
			throw new CargaObjetoXMLException (e.getClass().getName() + ": " + e.getLocalizedMessage(), "OficioRemision", datosXMLOficioRemision);
		}		
		
		return oficioRemision;			
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.oficioremision.factoria.FactoriaObjetosXMLOficioRemision#crearOficioRemision(java.io.File)
	 */
	public OficioRemision crearOficioRemision(File ficheroXMLOficioRemision)
			throws CargaObjetoXMLException {
		
		if (ficheroXMLOficioRemision == null){
			throw new CargaObjetoXMLException ("Se ha especificado un fichero nulo",
					"OficioRemision", "");
		}
		
		OficioRemision oficioRemision = null;
		try {
			oficioRemision = crearOficioRemision (new FileInputStream (ficheroXMLOficioRemision));		
		} catch (FileNotFoundException e) {
			throw new CargaObjetoXMLException (e.getClass().getName() + ": " + e.getLocalizedMessage(),
					"OficioRemision", ficheroXMLOficioRemision.getName());
		}
		
		return oficioRemision;	
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.oficioremision.factoria.FactoriaObjetosXMLOficioRemision#guardarOficioRemision(es.caib.xml.oficioremision.factoria.OficioRemision, java.io.OutputStream)
	 */
	public void guardarOficioRemision(OficioRemision oficioRemision,
			OutputStream datosXMLoficioRemision) 
	throws es.caib.xml.GuardaObjetoXMLException, EstablecerPropiedadException {
		
		// Validar que el output stream no es nulo
		if (datosXMLoficioRemision == null)
			throw new es.caib.xml.GuardaObjetoXMLException("Se ha especificado un flujo de salida nulo", 
					"OficioRemision", (OutputStream) null);
		
		// Validar que el justificante no es nulo
		if (oficioRemision == null)
			throw new es.caib.xml.GuardaObjetoXMLException("Se ha especificado un objeto formulario nulo", 
					"OficioRemision", (OutputStream) null);
		
		// Validar que al justificante tiene todos los datos requeridos
		oficioRemision.comprobarDatosRequeridos();
		
		// Crear objetos JAXB equivalentes
		OFICIOREMISION oficioRemisionImplInterno = null;
		
		try {
			oficioRemisionImplInterno = ofOficioRemision.createOFICIOREMISION();
		} catch (Exception e1) {
			throw new es.caib.xml.GuardaObjetoXMLException ("Se ha producido una excepción al crear un objetos OficioRemision JAXB", 
					"OficioRemision", (OutputStream) null);
		}
		
		
		cargarDatosHaciaJAXB (oficioRemision, oficioRemisionImplInterno);		
		
		
		//	Hemos obtenido el objeto JAXB equivalente al nodo oficio remision, podemos guardar el XML		
		try {
			mshOficioRemision.marshal (oficioRemisionImplInterno, datosXMLoficioRemision);
		} catch (JAXBException e) {
			throw new es.caib.xml.GuardaObjetoXMLException("Se ha producido un error al guardar oficio remision [" 
					+ e.getClass().getName() + ": " + e.getLocalizedMessage() + "]", 
					"OficioRemision", datosXMLoficioRemision);
		}
								
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.oficioremision.factoria.FactoriaObjetosXMLOficioRemision#guardarOficioRemision(es.caib.xml.oficioremision.factoria.OficioRemision, java.io.File)
	 */
	public void guardarOficioRemision(OficioRemision oficioRemision,
			File ficheroXMLOficioRemision) 
	throws es.caib.xml.GuardaObjetoXMLException, EstablecerPropiedadException {

		try {
			guardarOficioRemision (oficioRemision, new FileOutputStream (ficheroXMLOficioRemision));
		} catch (FileNotFoundException e) {
			throw new es.caib.xml.GuardaObjetoXMLException (e.getClass().getName() + ": " + e.getLocalizedMessage(),
					"OficioRemision", ficheroXMLOficioRemision.getName());
		} 		
		

	}

	/* (non-Javadoc)
	 * @see es.caib.xml.oficioremision.factoria.FactoriaObjetosXMLOficioRemision#guardarOficioRemision(es.caib.xml.oficioremision.factoria.OficioRemision)
	 */
	public String guardarOficioRemision(OficioRemision oficioRemision)
	throws es.caib.xml.GuardaObjetoXMLException, EstablecerPropiedadException 
	{
	
		ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream(); 
		guardarOficioRemision (oficioRemision, byteOutputStream);
		String encoding = getEncoding ();
		
		if ( (encoding != null) && (!encoding.trim().equals(""))){
			try {
				return byteOutputStream.toString(encoding);
			} catch (UnsupportedEncodingException e) {			
				throw new GuardaObjetoXMLException("La codificación " + encoding + " no está soportada", "OficioRemision", (OutputStream) null);
			}
		}
		else
			return byteOutputStream.toString();
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.FactoriaObjetosXML#getEncoding()
	 */
	public String getEncoding() {
		try {
			return mshOficioRemision.getProperty("jaxb.encoding").toString();
		} catch (PropertyException e) {
			//TODO quizá manejar mejor la excepción
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.FactoriaObjetosXML#setEncoding(java.lang.String)
	 */
	public void setEncoding(String encoding) {
		try {
			mshOficioRemision.setProperty("jaxb.encoding", encoding);
		} catch (PropertyException e) {
			//TODO manejar correctamente la excepción
			;
		}

	}

	/* (non-Javadoc)
	 * @see es.caib.xml.FactoriaObjetosXML#isIdentacion()
	 */
	public boolean isIdentacion() {
		try {
			return ((Boolean) mshOficioRemision.getProperty("jaxb.formatted.output")).booleanValue();
		} catch (PropertyException e) {
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.FactoriaObjetosXML#setIndentacion(boolean)
	 */
	public void setIndentacion(boolean indentacion) {
		try {
			mshOficioRemision.setProperty("jaxb.formatted.output", (indentacion) ? Boolean.TRUE : Boolean.FALSE);
		} catch (PropertyException e) {
			// TODO Manejar excepción de forma más conveniente
			;
		}

	}
	
	// Métodos para realizar la conversión JAXB -> Jerarquía propia de objetos
	private void cargarDatosDesdeJAXB (OFICIOREMISION oficioRemisionJAXB, OficioRemision oficioRemision) throws EstablecerPropiedadException{
		if ( (oficioRemision != null) && (oficioRemisionJAXB != null) ){
			oficioRemision.setTitulo(oficioRemisionJAXB.getTITULO());
			oficioRemision.setTexto(oficioRemisionJAXB.getTEXTO());
		}
	}
	
	
	//	Métodos para realizar la conversión Jerarquía propia de objetos -> JAXB  
	private void cargarDatosHaciaJAXB (OficioRemision oficioRemision, OFICIOREMISION oficioRemisionJAXB) throws EstablecerPropiedadException{
		if ( (oficioRemision != null) && (oficioRemisionJAXB != null) ){
			oficioRemisionJAXB.setTITULO(oficioRemision.getTitulo());
			oficioRemisionJAXB.setTEXTO(oficioRemision.getTexto());
		}
	}
	
}
