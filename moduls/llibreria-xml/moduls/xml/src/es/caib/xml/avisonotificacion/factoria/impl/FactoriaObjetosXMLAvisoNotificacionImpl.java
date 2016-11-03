package es.caib.xml.avisonotificacion.factoria.impl;

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
//import javax.xml.bind.Validator;
import javax.xml.bind.util.ValidationEventCollector;

import es.caib.xml.CargaObjetoXMLException;
import es.caib.xml.EstablecerPropiedadException;
import es.caib.xml.GuardaObjetoXMLException;
import es.caib.xml.InicializacionFactoriaException;
import es.caib.xml.avisonotificacion.factoria.FactoriaObjetosXMLAvisoNotificacion;
import es.caib.xml.avisonotificacion.modelo.AVISONOTIFICACION;
import es.caib.xml.avisonotificacion.modelo.EXPEDIENTE;
import es.caib.xml.avisonotificacion.modelo.ObjectFactory;

/** Implementa una factoría capaz de crear objetos que encapsulan el acceso
 * al documento XML de aviso notificacion
 * 
 * @author magroig
 *
 */
public class FactoriaObjetosXMLAvisoNotificacionImpl implements
		FactoriaObjetosXMLAvisoNotificacion {
	
	private static final String FICHERO_PROPIEDADES_JAXB = "avisonotificacion_JAXB.properties"; 
	private static final String PAQUETE_FORMULARIO_PROP = "PAQUETE_MODELO_AVISONOTIFICACION_IMPL";	 
	
	private JAXBContext contextoJAXBAvisoNotificacion;
	private Unmarshaller unmshAvisoNotificacion;
	private Marshaller mshAvisoNotificacion;
	private ObjectFactory ofAvisoNotificacion;

	/** Crea la factoría. Se necesita que exista en el paquete un fichero properies (avisonotificacion_JAXB.properties),
	 * que debe contener la propiedad PAQUETE_MODELO_AVISONOTIFICACION_IMPL, cuyo valor indicará cual es el
	 * paquete donde se encuentra la jerarquía de clases generadas mediante JAXB para manejar el acceso
	 * a la creación/recuperación de contenido para el documento de DATOS_PROPIOS
	 * 
	 * @throws InicializacionFactoriaException Se ha producido un error inesperado al incializar la factoría
	 */
	public FactoriaObjetosXMLAvisoNotificacionImpl () throws InicializacionFactoriaException {
		try {
			
			Properties propsJAXB = obtenerPropiedadesJAXB ();
			
			contextoJAXBAvisoNotificacion = JAXBContext.newInstance (obtenerPaqueteImplAvisoNotificacion (propsJAXB));
			unmshAvisoNotificacion = contextoJAXBAvisoNotificacion.createUnmarshaller();
			// Habilitamos validacion en el unmarshaller
			//unmshAvisoNotificacion.setValidating(true);
			mshAvisoNotificacion = contextoJAXBAvisoNotificacion.createMarshaller();	
			ofAvisoNotificacion = new ObjectFactory ();
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
	private String obtenerPaqueteImplAvisoNotificacion (Properties propiedadesJAXB) throws IOException {		
		return propiedadesJAXB.getProperty(PAQUETE_FORMULARIO_PROP);
	}
	
	/* (non-Javadoc)
	 * @see es.caib.xml.avisonotificacion.factoria.FactoriaObjetosXMLAvisoNotificacion#crearAvisoNotificacion()
	 */
	public AvisoNotificacion crearAvisoNotificacion() {		
		return new AvisoNotificacion ();					
	}

	public Expediente crearExpediente() {		
		return new Expediente ();					
	}
	
	/* (non-Javadoc)
	 * @see es.caib.xml.avisonotificacion.factoria.FactoriaObjetosXMLAvisoNotificacion#crearAvisoNotificacion(java.io.InputStream)
	 */
	public AvisoNotificacion crearAvisoNotificacion(InputStream datosXMLAvisoNotificacion)
			throws CargaObjetoXMLException {

		AvisoNotificacion avisoNotificacion = null;
		
		try {			
			AVISONOTIFICACION avisoNotificacionJAXB = (AVISONOTIFICACION) unmshAvisoNotificacion.unmarshal(datosXMLAvisoNotificacion);
			
			//Validation
			/*
			Validator validator = contextoJAXBAvisoNotificacion.createValidator();
			validator.setEventHandler(new ValidationEventCollector());
			validator.validate(avisoNotificacionJAXB);
			*/
			
			avisoNotificacion = crearAvisoNotificacion ();
			cargarDatosDesdeJAXB (avisoNotificacionJAXB, avisoNotificacion);
											
		} catch (Exception e) {
			throw new CargaObjetoXMLException (e.getClass().getName() + ": " + e.getLocalizedMessage(), "AvisoNotificacion", datosXMLAvisoNotificacion);
		}		
		
		return avisoNotificacion;			
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.avisonotificacion.factoria.FactoriaObjetosXMLAvisoNotificacion#crearAvisoNotificacion(java.io.File)
	 */
	public AvisoNotificacion crearAvisoNotificacion(File ficheroXMLAvisoNotificacion)
			throws CargaObjetoXMLException {
		
		if (ficheroXMLAvisoNotificacion == null){
			throw new CargaObjetoXMLException ("Se ha especificado un fichero nulo",
					"AvisoNotificacion", "");
		}
		
		AvisoNotificacion avisoNotificacion = null;
		try {
			avisoNotificacion = crearAvisoNotificacion (new FileInputStream (ficheroXMLAvisoNotificacion));		
		} catch (FileNotFoundException e) {
			throw new CargaObjetoXMLException (e.getClass().getName() + ": " + e.getLocalizedMessage(),
					"AvisoNotificacion", ficheroXMLAvisoNotificacion.getName());
		}
		
		return avisoNotificacion;	
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.avisonotificacion.factoria.FactoriaObjetosXMLAvisoNotificacion#guardarAvisoNotificacion(es.caib.xml.avisonotificacion.factoria.AvisoNotificacion, java.io.OutputStream)
	 */
	public void guardarAvisoNotificacion(AvisoNotificacion avisoNotificacion,
			OutputStream datosXMLavisoNotificacion) 
	throws es.caib.xml.GuardaObjetoXMLException, EstablecerPropiedadException {
		
		// Validar que el output stream no es nulo
		if (datosXMLavisoNotificacion == null)
			throw new es.caib.xml.GuardaObjetoXMLException("Se ha especificado un flujo de salida nulo", 
					"AvisoNotificacion", (OutputStream) null);
		
		// Validar que el justificante no es nulo
		if (avisoNotificacion == null)
			throw new es.caib.xml.GuardaObjetoXMLException("Se ha especificado un objeto formulario nulo", 
					"AvisoNotificacion", (OutputStream) null);
		
		// Validar que al justificante tiene todos los datos requeridos
		avisoNotificacion.comprobarDatosRequeridos();
		
		// Crear objetos JAXB equivalentes
		AVISONOTIFICACION avisoNotificacionImplInterno = null;
		
		try {
			avisoNotificacionImplInterno = ofAvisoNotificacion.createAVISONOTIFICACION();
		} catch (Exception e1) {
			throw new es.caib.xml.GuardaObjetoXMLException ("Se ha producido una excepción al crear un objetos AvisoNotificacion JAXB", 
					"AvisoNotificacion", (OutputStream) null);
		}
		
		
		cargarDatosHaciaJAXB (avisoNotificacion, avisoNotificacionImplInterno);		
		
		
		//	Hemos obtenido el objeto JAXB equivalente al nodo aviso notificacion, podemos guardar el XML		
		try {
			mshAvisoNotificacion.marshal (avisoNotificacionImplInterno, datosXMLavisoNotificacion);
		} catch (JAXBException e) {
			throw new es.caib.xml.GuardaObjetoXMLException("Se ha producido un error al guardar aviso notificacion [" 
					+ e.getClass().getName() + ": " + e.getLocalizedMessage() + "]", 
					"AvisoNotificacion", datosXMLavisoNotificacion);
		}
								
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.avisonotificacion.factoria.FactoriaObjetosXMLAvisoNotificacion#guardarAvisoNotificacion(es.caib.xml.avisonotificacion.factoria.AvisoNotificacion, java.io.File)
	 */
	public void guardarAvisoNotificacion(AvisoNotificacion avisoNotificacion,
			File ficheroXMLAvisoNotificacion) 
	throws es.caib.xml.GuardaObjetoXMLException, EstablecerPropiedadException {

		try {
			guardarAvisoNotificacion (avisoNotificacion, new FileOutputStream (ficheroXMLAvisoNotificacion));
		} catch (FileNotFoundException e) {
			throw new es.caib.xml.GuardaObjetoXMLException (e.getClass().getName() + ": " + e.getLocalizedMessage(),
					"AvisoNotificacion", ficheroXMLAvisoNotificacion.getName());
		} 		
		

	}

	/* (non-Javadoc)
	 * @see es.caib.xml.avisonotificacion.factoria.FactoriaObjetosXMLAvisoNotificacion#guardarAvisoNotificacion(es.caib.xml.avisonotificacion.factoria.AvisoNotificacion)
	 */
	public String guardarAvisoNotificacion(AvisoNotificacion avisoNotificacion)
	throws es.caib.xml.GuardaObjetoXMLException, EstablecerPropiedadException 
	{
	
		ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream(); 
		guardarAvisoNotificacion (avisoNotificacion, byteOutputStream);
		String encoding = getEncoding ();
		
		if ( (encoding != null) && (!encoding.trim().equals(""))){
			try {
				return byteOutputStream.toString(encoding);
			} catch (UnsupportedEncodingException e) {			
				throw new GuardaObjetoXMLException("La codificación " + encoding + " no está soportada", "AvisoNotificacion", (OutputStream) null);
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
			return mshAvisoNotificacion.getProperty("jaxb.encoding").toString();
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
			mshAvisoNotificacion.setProperty("jaxb.encoding", encoding);
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
			return ((Boolean) mshAvisoNotificacion.getProperty("jaxb.formatted.output")).booleanValue();
		} catch (PropertyException e) {
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.FactoriaObjetosXML#setIndentacion(boolean)
	 */
	public void setIndentacion(boolean indentacion) {
		try {
			mshAvisoNotificacion.setProperty("jaxb.formatted.output", (indentacion) ? Boolean.TRUE : Boolean.FALSE);
		} catch (PropertyException e) {
			// TODO Manejar excepción de forma más conveniente
			;
		}

	}
	
	// Métodos para realizar la conversión JAXB -> Jerarquía propia de objetos
	private void cargarDatosDesdeJAXB (AVISONOTIFICACION avisoNotificacionJAXB, AvisoNotificacion avisoNotificacion) throws EstablecerPropiedadException{
		if ( (avisoNotificacion != null) && (avisoNotificacionJAXB != null) ){
			avisoNotificacion.setTitulo(avisoNotificacionJAXB.getTITULO());
			avisoNotificacion.setTexto(avisoNotificacionJAXB.getTEXTO());
			avisoNotificacion.setTextoSMS(avisoNotificacionJAXB.getTEXTOSMS());
			avisoNotificacion.setAcuseRecibo( new Boolean(avisoNotificacionJAXB.getACUSERECIBO().equals("SI")));
			
			if (avisoNotificacionJAXB.getACCESIBLEPORCLAVE() != null) {
				avisoNotificacion.setAccesiblePorClave(new Boolean(avisoNotificacionJAXB.getACCESIBLEPORCLAVE().equals("SI")));
			}	
			
			if (avisoNotificacionJAXB.getPLAZO() != null && avisoNotificacionJAXB.getPLAZO().length() > 0) {
				avisoNotificacion.setPlazo(new Integer(avisoNotificacionJAXB.getPLAZO()));
			}	
			
			avisoNotificacion.setExpediente(crearExpediente(avisoNotificacionJAXB.getEXPEDIENTE()));
		}
	}
	
	
	//	Métodos para realizar la conversión Jerarquía propia de objetos -> JAXB  
	private void cargarDatosHaciaJAXB (AvisoNotificacion avisoNotificacion, AVISONOTIFICACION avisoNotificacionJAXB) throws EstablecerPropiedadException{
		if ( (avisoNotificacion != null) && (avisoNotificacionJAXB != null) ){
			avisoNotificacionJAXB.setTITULO(avisoNotificacion.getTitulo());
			avisoNotificacionJAXB.setTEXTO(avisoNotificacion.getTexto());			
			avisoNotificacionJAXB.setTEXTOSMS(avisoNotificacion.getTextoSMS());
			avisoNotificacionJAXB.setACUSERECIBO( (avisoNotificacion.getAcuseRecibo().booleanValue()?"SI":"NO"));
			if (avisoNotificacion.getAccesiblePorClave() != null) {
				avisoNotificacionJAXB.setACCESIBLEPORCLAVE((avisoNotificacion.getAccesiblePorClave().booleanValue()?"SI":"NO"));
			}	
			if (avisoNotificacion.getPlazo() != null) {
				avisoNotificacionJAXB.setPLAZO(avisoNotificacion.getPlazo().toString());
			}	
			avisoNotificacionJAXB.setEXPEDIENTE(crearExpediente(avisoNotificacion.getExpediente()));			
		}
	}
	
	
	
	private Expediente crearExpediente (EXPEDIENTE expJAXB) throws EstablecerPropiedadException{
		Expediente exp = null;	
		if (expJAXB != null){
			exp = new Expediente ();			
			exp.setClaveExpediente(expJAXB.getCLAVEEXPEDIENTE());
			exp.setIdentificadorExpediente(expJAXB.getIDENTIFICADOREXPEDIENTE());
			exp.setUnidadAdministrativa(expJAXB.getUNIDADADMINISTRATIVA());			
			exp.setTituloExpediente(expJAXB.getTITULOEXPEDIENTE());
		}		
		return exp;
	}
	
	private EXPEDIENTE crearExpediente ( Expediente exp) throws EstablecerPropiedadException{
		EXPEDIENTE expJAXB = null;	
		if (exp != null){
			expJAXB = new EXPEDIENTE ();			
			expJAXB.setCLAVEEXPEDIENTE(exp.getClaveExpediente());
			expJAXB.setIDENTIFICADOREXPEDIENTE(exp.getIdentificadorExpediente());
			expJAXB.setUNIDADADMINISTRATIVA(exp.getUnidadAdministrativa());		
			expJAXB.setTITULOEXPEDIENTE(exp.getTituloExpediente());
		}		
		return expJAXB;
	}
}
