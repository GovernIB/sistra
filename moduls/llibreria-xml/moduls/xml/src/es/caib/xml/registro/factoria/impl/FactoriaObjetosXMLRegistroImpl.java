package es.caib.xml.registro.factoria.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
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
import es.caib.xml.registro.factoria.ConstantesAsientoXML;
import es.caib.xml.registro.factoria.FactoriaObjetosXMLRegistro;
import es.caib.xml.registro.modelo.ASIENTOREGISTRAL;
import es.caib.xml.registro.modelo.DATOSANEXODOCUMENTACION;
import es.caib.xml.registro.modelo.DATOSASUNTO;
import es.caib.xml.registro.modelo.DATOSDESTINO;
import es.caib.xml.registro.modelo.DATOSINTERESADO;
import es.caib.xml.registro.modelo.DATOSORIGEN;
import es.caib.xml.registro.modelo.DIRECCIONCODIFICADA;
import es.caib.xml.registro.modelo.IDENTIFICACIONINTERESADODESGLOSADA;
import es.caib.xml.registro.modelo.JUSTIFICANTE;
import es.caib.xml.registro.modelo.ObjectFactory;

/** Clase de implementación para la creación de objetos representando
 * la información de los ficheros XML de justificante de registro y
 * de registro de entrada. Para el mapeo se usa actualmente el modelo
 * creado de forma automática por JAXB
 * 
 * @author magroig
 *
 */
public class FactoriaObjetosXMLRegistroImpl 	
	implements FactoriaObjetosXMLRegistro {
	
	private static final String FICHERO_PROPIEDADES_JAXB = "registro_JAXB.properties";
	private static final String PAQUETE_JUSTIFICACION_PROP = "PAQUETE_MODELO_JUSTIFICANTE_IMPL";
	
	private JAXBContext contextoJAXBJustificante;
	private Unmarshaller unmshJustificante;
	private Marshaller mshJustificante;
	private ObjectFactory ofJustificante;
			
	/**
	 * Para la implentación se usa el modelo creado por JAXB
	 * @throws JAXBException 
	 */		
	public FactoriaObjetosXMLRegistroImpl() throws InicializacionFactoriaException {		
		try {
			
			Properties propsJAXB = obtenerPropiedadesJAXB ();
			
			contextoJAXBJustificante = JAXBContext.newInstance (obtenerPaqueteImplJustificante(propsJAXB));
			unmshJustificante = contextoJAXBJustificante.createUnmarshaller();
			mshJustificante = contextoJAXBJustificante.createMarshaller();
			ofJustificante = new ObjectFactory ();
		} catch (JAXBException e) {
			throw new InicializacionFactoriaException (e.getClass().getName() + " -> " + e.getLocalizedMessage(), 
					"JAXB");
		}
		catch (IOException ioe){
			throw new InicializacionFactoriaException ("Se ha producido un error al leer propiedades JAXB -> " + ioe.getLocalizedMessage(), 
			"JAXB");
		}
	}
	
	public String getEncoding() {
		try {
			return mshJustificante.getProperty("jaxb.encoding").toString();
		} catch (PropertyException e) {
			return null;
		}
	}

	public void setEncoding(String encoding) {
		try {
			mshJustificante.setProperty("jaxb.encoding", encoding);
		} catch (PropertyException e) {
			//TODO manejar correctamente la excepción
			;
		}		
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.registro.factoria.IFactoriaObjetosXML#crearJustificanteRegistro()
	 */
	public Justificante crearJustificanteRegistro() {								
		return new Justificante ();		
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.registro.factoria.IFactoriaObjetosXML#crearJustificanteRegistro(java.io.InputStream)
	 */
	public Justificante crearJustificanteRegistro  (
			InputStream datosXMLJustificante) throws CargaObjetoXMLException 
	{
		Justificante justificante = null;			
		Object obj = null;
		try {			
			obj = unmshJustificante.unmarshal(datosXMLJustificante);
			JUSTIFICANTE justificanteJAXB = (JUSTIFICANTE) obj;
			justificante = new Justificante ();
			
			try {
				cargarDatosDesdeJAXB (justificanteJAXB, justificante);
			} catch (EstablecerPropiedadException e) {
				throw new CargaObjetoXMLException (e.getClass().getName() + ": " + e.getLocalizedMessage(), "Justificante", datosXMLJustificante);
			}
											
		} catch (JAXBException e) {			
			throw  new CargaObjetoXMLException (e.getClass().getName() + ": " + e.getLocalizedMessage(), "justificante", datosXMLJustificante);
		}		
		
		return justificante;
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.registro.factoria.IFactoriaObjetosXML#crearJustificanteRegistro(java.io.File)
	 */
	public Justificante crearJustificanteRegistro(
			File ficheroXMLJustificante) throws CargaObjetoXMLException 
	{
		if (ficheroXMLJustificante == null){
			throw new CargaObjetoXMLException ("Se ha especificado un fichero nulo",
					"Justificante", "");
		}
		
		Justificante justificante = null;
		try {
			justificante = crearJustificanteRegistro (new FileInputStream (ficheroXMLJustificante));		
		} catch (FileNotFoundException e) {
			throw new CargaObjetoXMLException (e.getClass().getName() + ": " + e.getLocalizedMessage(),
					"Justificante", ficheroXMLJustificante.getName());
		}
		
		return justificante;
	}
		
	
	public boolean isIdentacion (){
		try {
			return ((Boolean) mshJustificante.getProperty("jaxb.formatted.output")).booleanValue();
		} catch (PropertyException e) {
			return false;
		}
	}
	
	public void setIndentacion (boolean identacion){
		try {
			mshJustificante.setProperty("jaxb.formatted.output", (identacion) ? Boolean.TRUE : Boolean.FALSE);
		} catch (PropertyException e) {
			// TODO Manejar excepción de forma más conveniente
			;
		}
	}
	
	public void guardarJustificanteRegistro(Justificante justificante, OutputStream datosXMLJustificante) 
	throws es.caib.xml.GuardaObjetoXMLException, EstablecerPropiedadException {
		// Validar que el output stream no es nulo
		if (datosXMLJustificante == null)
			throw new es.caib.xml.GuardaObjetoXMLException("Se ha especificado un flujo de salida nulo", 
					"Justificante", (OutputStream) null);
		
		// Validar que el justificante no es nulo
		if (justificante == null)
			throw new es.caib.xml.GuardaObjetoXMLException("Se ha especificado un objeto justificante nulo", 
					"Justificante", (OutputStream) null);
		
		// Validar que al justificante tiene todos los datos requeridos
		justificante.comprobarDatosRequeridos();
		
		JUSTIFICANTE justImplInterno = null;
		try {
			justImplInterno = this.ofJustificante.createJUSTIFICANTE();
		} catch (Exception e) {
			throw new es.caib.xml.GuardaObjetoXMLException ("Se ha producido una excepción al crear un objeto Justificante JAXB", 
					"Justificante", (OutputStream) null);
		}
		
		cargarDatosHaciaJAXB (justificante, justImplInterno);
		
		// Hemos obtenido el objeto JAXB equivalente al nodo Justificante, podemos guardar el XML
		try {
			mshJustificante.marshal (justImplInterno, datosXMLJustificante);
		} catch (JAXBException e) {
			throw new es.caib.xml.GuardaObjetoXMLException("Se ha producido un error al guardar el justificante [" 
					+ e.getClass().getName() + ": " + e.getLocalizedMessage() + "]", 
					"Justificante", datosXMLJustificante);
		}									
	}

	public void guardarJustificanteRegistro(Justificante justificante, File ficheroXMLJustificante) 
	throws es.caib.xml.GuardaObjetoXMLException, EstablecerPropiedadException
	{
		try {
			guardarJustificanteRegistro(justificante, new FileOutputStream (ficheroXMLJustificante));
		} catch (FileNotFoundException e) {
			throw new es.caib.xml.GuardaObjetoXMLException (e.getClass().getName() + ": " + e.getLocalizedMessage(),
					"Justificante", ficheroXMLJustificante.getName());
		} 
	}	
	
	
	/** Obtiene el nombre de paquete que implementa el modelo de clases
	 * XML JAXB del documento de justificante de registro (necesario para
	 * inicializar librería JAXB)
	 * 
	 * @param propiedadesJAXB Properties con la configuración de la factoría
	 * 
	 * @return paquete que implementa el modelo de clases 
	 * para el documento de justificante de registro creado por JAXB
	 * 
	 * @throws IOException Si se produce un problema al acceder al fichero
	 * properties de configuración de la factoría
	 */
	private String obtenerPaqueteImplJustificante(Properties propiedadesJAXB) throws IOException {		
		return propiedadesJAXB.getProperty(PAQUETE_JUSTIFICACION_PROP);
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

	public AsientoRegistral crearAsientoRegistral() {				
		return new AsientoRegistral ();									
	}

	public DatosAnexoDocumentacion crearDatosAnexoDocumentacion() {
		return new DatosAnexoDocumentacion ();
	}

	public DatosAsunto crearDatosAsunto() {		
		return new DatosAsunto ();
	}

	public DatosDestino crearDatosDestino() {		
		return new DatosDestino ();
	}

	public DatosInteresado crearDatosInteresado() {
		return new DatosInteresado ();
	}

	public DatosOrigen crearDatosOrigen() {		
		return new DatosOrigen ();
	}

	public DireccionCodificada crearDireccionCodificada() {		
		return new DireccionCodificada ();
	}
	
	public IdentificacionInteresadoDesglosada crearIdentificacionInteresadoDesglosada() {		
		return new IdentificacionInteresadoDesglosada ();
	}

	public String guardarJustificanteRegistro(Justificante justificante) 
	throws GuardaObjetoXMLException, EstablecerPropiedadException 
	{
		ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream(); 
		guardarJustificanteRegistro (justificante, byteOutputStream);
		String encoding = getEncoding ();
		
		if ( (encoding != null) && (!encoding.trim().equals(""))){
			try {
				return byteOutputStream.toString(encoding);
			} catch (UnsupportedEncodingException e) {			
				throw new GuardaObjetoXMLException("La codificación " + encoding + " no está soportada", "Justificante", (OutputStream) null);
			}
		}
		else
			return byteOutputStream.toString();
	}

	public AsientoRegistral crearAsientoRegistral(InputStream datosXMLAsientoRegistral) throws CargaObjetoXMLException {
		AsientoRegistral asiento = null;			
		
		try {			
			ASIENTOREGISTRAL asientoJAXB = (ASIENTOREGISTRAL) unmshJustificante.unmarshal(datosXMLAsientoRegistral);			
			asiento = crearAsientoRegistral (asientoJAXB);																
		} catch (Exception e) {
			throw  new CargaObjetoXMLException (e.getClass().getName() + ": " + e.getLocalizedMessage(), "AsientoRegistral", datosXMLAsientoRegistral);
		}		
		
		return asiento;
	}

	public AsientoRegistral crearAsientoRegistral(File ficheroXMLAsientoRegistral) throws CargaObjetoXMLException {
		if (ficheroXMLAsientoRegistral == null){
			throw new CargaObjetoXMLException ("Se ha especificado un fichero nulo",
					"AsientoRegistral", "");
		}
		
		try {
			return crearAsientoRegistral (new FileInputStream (ficheroXMLAsientoRegistral));		
		} catch (FileNotFoundException e) {
			throw new CargaObjetoXMLException (e.getClass().getName() + ": " + e.getLocalizedMessage(),
					"AsientoRegistral", ficheroXMLAsientoRegistral.getName());
		}
	}

	public void guardarAsientoRegistral(AsientoRegistral asiento, OutputStream datosXMLAsientoRegistral) 
	throws GuardaObjetoXMLException, EstablecerPropiedadException 
	{
		// Validar que el output stream no es nulo
		if (datosXMLAsientoRegistral == null)
			throw new es.caib.xml.GuardaObjetoXMLException("Se ha especificado un flujo de salida nulo", 
					"AsientoRegistral", (OutputStream) null);
		
		// Validar que el justificante no es nulo
		if (asiento == null)
			throw new es.caib.xml.GuardaObjetoXMLException("Se ha especificado un objeto asiento registral nulo", 
					"AsientoRegistral", (OutputStream) null);
		
		// Validar que al justificante tiene todos los datos requeridos
		asiento.comprobarDatosRequeridos();
		
		ASIENTOREGISTRAL asientoImplInterno;
		
		try {
			asientoImplInterno = ofJustificante.createASIENTOREGISTRAL();
		} catch (Exception e1) {
			throw new es.caib.xml.GuardaObjetoXMLException ("Se ha producido una excepción al crear un objeto Asiento Registral JAXB", 
					"AsientoRegistral", (OutputStream) null);
		}
		
		cargarDatosHaciaJAXB (asiento, asientoImplInterno);
		
		// Hemos obtenido el objeto JAXB equivalente al nodo Justificante, podemos guardar el XML
		try {
			mshJustificante.marshal (asientoImplInterno, datosXMLAsientoRegistral);
		} catch (JAXBException e) {
			throw new es.caib.xml.GuardaObjetoXMLException("Se ha producido un error al guardar el asiento registral [" 
					+ e.getClass().getName() + ": " + e.getLocalizedMessage() + "]", 
					"AsientoRegistral", datosXMLAsientoRegistral);
		}
					
	}

	public void guardarAsientoRegistral(AsientoRegistral asiento, File ficheroXMLAsientoRegistral) 
	throws es.caib.xml.GuardaObjetoXMLException, EstablecerPropiedadException 
	{
		try {
			guardarAsientoRegistral (asiento, new FileOutputStream (ficheroXMLAsientoRegistral));		
		} catch (FileNotFoundException e) {
			throw new es.caib.xml.GuardaObjetoXMLException (e.getClass().getName() + ": " + e.getLocalizedMessage(),
					"AsientoRegistral", ficheroXMLAsientoRegistral.getName());
		}
		
	}

	public String guardarAsientoRegistral(AsientoRegistral asiento) 
	throws GuardaObjetoXMLException, EstablecerPropiedadException 
	{
		ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream(); 
		guardarAsientoRegistral (asiento, byteOutputStream);
		String encoding = getEncoding ();
		
		if ( (encoding != null) && (!encoding.trim().equals(""))){
			try {
				return byteOutputStream.toString(encoding);
			} catch (UnsupportedEncodingException e) {			
				throw new GuardaObjetoXMLException("La codificación " + encoding + " no está soportada", "AsientoRegistral", (OutputStream) null);
			}
		}
		else
			return byteOutputStream.toString();
	}	
	
	// Métodos para realizar la conversión JAXB -> Jerarquía propia de objetos
	private void cargarDatosDesdeJAXB (JUSTIFICANTE justificanteJAXB, Justificante justificante) throws EstablecerPropiedadException{
		if ( (justificanteJAXB != null) && (justificante != null)){
			justificante.setVersion (justificanteJAXB.getVersion());
			justificante.setNumeroRegistro (justificanteJAXB.getNUMEROREGISTRO());
			
			if ( (justificanteJAXB.getFECHAREGISTRO() != null) && (!justificanteJAXB.getFECHAREGISTRO().trim().equals(""))){
			
				SimpleDateFormat fmt = new SimpleDateFormat (ConstantesAsientoXML.FECHA_REGISTRO_FORMATO);
				Date f = null;
				try {
					f = fmt.parse (justificanteJAXB.getFECHAREGISTRO().trim());
				} catch (ParseException e) {
					throw new EstablecerPropiedadException ("La fecha de registro tiene un formato inválido", 
							"Justificante", "FechaRegistro", justificanteJAXB.getFECHAREGISTRO());
				}
				
				justificante.setFechaRegistro (f);
			}
			
			 
			justificante.setAsientoRegistral (
			crearAsientoRegistral (justificanteJAXB.getASIENTOREGISTRAL()));
		}
	}
			
	
	private AsientoRegistral crearAsientoRegistral (ASIENTOREGISTRAL asientoJAXB) throws EstablecerPropiedadException{
		AsientoRegistral asiento = null;
		
		if (asientoJAXB != null){
			asiento = crearAsientoRegistral ();
			
			// versión
			asiento.setVersion (asientoJAXB.getVersion());
			
			// datos origen			 
			asiento.setDatosOrigen (crearDatosOrigen(asientoJAXB.getDATOSORIGEN()));
			
			// datos destino			
			asiento.setDatosDestino(crearDatosDestino (asientoJAXB.getDATOSDESTINO()));
			
			// datos asunto			
			asiento.setDatosAsunto(crearDatosAsunto (asientoJAXB.getDATOSASUNTO()));
			
			// datos interesado
			Iterator itDInteresadoRaw = asientoJAXB.getDATOSINTERESADO().iterator();
			while (itDInteresadoRaw.hasNext()){
				Object obj = itDInteresadoRaw.next ();
				if (obj instanceof DATOSINTERESADO){
					DatosInteresado datosInteresado = crearDatosInteresado ((DATOSINTERESADO) obj); 
					asiento.getDatosInteresado().add (datosInteresado);
				}
				else{
					throw new EstablecerPropiedadException ("El objeto no es de tipo DatosInteresado", 
							"AsientoRegistral", "DatosInteresado", obj);
				}
			}
			
			// datos anexo documentacion
			Iterator itDAnexoDocRaw = asientoJAXB.getDATOSANEXODOCUMENTACION().iterator();
			while (itDAnexoDocRaw.hasNext()){
				Object obj = itDAnexoDocRaw.next ();
				if (obj instanceof DATOSANEXODOCUMENTACION){
					DatosAnexoDocumentacion datosAnexo = crearDatosAnexoDocumentacion ((DATOSANEXODOCUMENTACION) obj);
					asiento.getDatosAnexoDocumentacion().add (datosAnexo);
				}
				else{
					throw new EstablecerPropiedadException ("El objeto no es de tipo DatosInteresado", 
							"AsientoRegistral", "DatosInteresado", obj);
				}
			}
			
		}
		
		return asiento;
	}
	
	private DatosOrigen crearDatosOrigen (DATOSORIGEN datosOrigenJAXB) throws EstablecerPropiedadException{
		DatosOrigen datosOrigen = null;
		
		if (datosOrigenJAXB != null){
			datosOrigen = crearDatosOrigen ();
			
			datosOrigen.setCodigoEntidadRegistralOrigen (datosOrigenJAXB.getCODIGOENTIDADREGISTRALORIGEN());
			datosOrigen.setNumeroRegistro (datosOrigenJAXB.getNUMEROREGISTRO());
			
			if ( (datosOrigenJAXB.getTIPOREGISTRO() != null) && (!datosOrigenJAXB.getTIPOREGISTRO().trim().equals("")) ){
				
				datosOrigen.setTipoRegistro (new Character (datosOrigenJAXB.getTIPOREGISTRO().trim().charAt(0)));
			}
			
			
			if ( (datosOrigenJAXB.getFECHAENTRADAREGISTRO() != null) && (!datosOrigenJAXB.getFECHAENTRADAREGISTRO().trim().equals("")) ){
				Date f = null;
				
				SimpleDateFormat fmt = new SimpleDateFormat (ConstantesAsientoXML.FECHA_REGISTRO_FORMATO);
				
				try {
					f = fmt.parse (datosOrigenJAXB.getFECHAENTRADAREGISTRO());
				} catch (ParseException e) {
					throw new EstablecerPropiedadException ("El formato de la fecha no es correcto", 
							"DatosOrigen", "FechaEntradaRegistro", datosOrigenJAXB.getFECHAENTRADAREGISTRO());
				}
				
				datosOrigen.setFechaEntradaRegistro (f);
			}
		}
		
		return datosOrigen;
	}
	
	private DatosDestino crearDatosDestino (DATOSDESTINO datosDestinoJAXB) throws EstablecerPropiedadException{
		DatosDestino datosDestino = null;
		
		if (datosDestinoJAXB != null){
			datosDestino.setDecodificacionEntidadRegistralDestino (datosDestinoJAXB.getCODIGOENTIDADREGISTRALDESTINO());
		}
		
		return datosDestino;
	}
	
	private DireccionCodificada crearDireccionCodificada (DIRECCIONCODIFICADA direccionJAXB) throws EstablecerPropiedadException{
		DireccionCodificada direccion = null;
		
		if (direccionJAXB != null){
			direccion = crearDireccionCodificada ();
			direccion.setCodigoMunicipio (direccionJAXB.getCODIGOMUNICIPIO());
			direccion.setCodigoPoblacion (direccionJAXB.getCODIGOPOBLACION());
			direccion.setCodigoPostal (direccionJAXB.getCODIGOPOSTAL ());
			direccion.setCodigoProvincia (direccionJAXB.getCODIGOPROVINCIA ());
			direccion.setDomicilio (direccionJAXB.getDOMICILIO ());
			direccion.setNombreMunicipio (direccionJAXB.getNOMBREMUNICIPIO ());
			direccion.setNombrePoblacion (direccionJAXB.getNOMBREPOBLACION ());
			direccion.setNombreProvincia (direccionJAXB.getNOMBREPROVINCIA ());
			direccion.setPaisOrigen (direccionJAXB.getPAISORIGEN ());
			direccion.setTelefono (direccionJAXB.getTELEFONO ());
			direccion.setFAX (direccionJAXB.getFAX ());		
			direccion.setEmail(direccionJAXB.getEMAIL());
		}
		
		return direccion;
	}
	
	private IdentificacionInteresadoDesglosada crearIdentificacionInteresadoDesglosada (IDENTIFICACIONINTERESADODESGLOSADA identJAXB) throws EstablecerPropiedadException{
		IdentificacionInteresadoDesglosada ident = null;
		
		if (identJAXB != null){
			ident = crearIdentificacionInteresadoDesglosada();
			ident.setNombre(identJAXB.getNOMBREINTERESADO());
			ident.setApellido1(identJAXB.getAPELLIDO1INTERESADO());
			ident.setApellido2(identJAXB.getAPELLIDO2INTERESADO());						
		}
		
		return ident;
	}
	
	private DatosInteresado crearDatosInteresado (DATOSINTERESADO datosInteresadoJAXB) throws EstablecerPropiedadException{
		DatosInteresado datosInteresado = null;
		
		if (datosInteresadoJAXB != null){
			datosInteresado = crearDatosInteresado ();
			if ( (datosInteresadoJAXB.getNIVELAUTENTICACION() != null) && (!datosInteresadoJAXB.getNIVELAUTENTICACION().trim().equals("")))
				datosInteresado.setNivelAutenticacion (
						new Character (datosInteresadoJAXB.getNIVELAUTENTICACION().trim().charAt(0))
				);
			
			datosInteresado.setUsuarioSeycon (datosInteresadoJAXB.getUSUARIOSEYCON());
			datosInteresado.setTipoInteresado (datosInteresadoJAXB.getTIPOINTERESADO());
			
			if (!datosInteresadoJAXB.getTIPOIDENTIFICACION().trim().equals(""))
				datosInteresado.setTipoIdentificacion (
						new Character (datosInteresadoJAXB.getTIPOIDENTIFICACION().trim().charAt(0))
				);
			
			datosInteresado.setNumeroIdentificacion (datosInteresadoJAXB.getNUMEROIDENTIFICACION());
			datosInteresado.setFormatoDatosInteresado (datosInteresadoJAXB.getFORMATODATOSINTERESADO());
			datosInteresado.setIdentificacionInteresado (datosInteresadoJAXB.getIDENTIFICACIONINTERESADO());
			datosInteresado.setIdentificacionInteresadoDesglosada(
					crearIdentificacionInteresadoDesglosada(datosInteresadoJAXB.getIDENTIFICACIONINTERESADODESGLOSADA())	
				);
			datosInteresado.setDireccionCodificada (
				crearDireccionCodificada (datosInteresadoJAXB.getDIRECCIONCODIFICADA())	
			);
		}
		
		return datosInteresado;
	}
	
	private DatosAsunto crearDatosAsunto (DATOSASUNTO datosAsuntoJAXB) throws EstablecerPropiedadException{
		DatosAsunto datosAsunto = null;
		
		if (datosAsuntoJAXB != null){
			datosAsunto = crearDatosAsunto ();
			
			if ( (datosAsuntoJAXB.getFECHAASUNTO() != null) && (!datosAsuntoJAXB.getFECHAASUNTO().trim().equals("")) ){
					Date f = null;
					SimpleDateFormat fmt = new SimpleDateFormat (ConstantesAsientoXML.FECHA_REGISTRO_FORMATO);
					try {
						f = fmt.parse (datosAsuntoJAXB.getFECHAASUNTO());
					} catch (ParseException e) {
						throw new EstablecerPropiedadException ("El formato de la fecha no es correcto", 
								"DatosAsunto", "FechaAsunto", datosAsuntoJAXB.getFECHAASUNTO());
					}					
					datosAsunto.setFechaAsunto(f);
			}			
			
			datosAsunto.setCodigoOrganoDestino (datosAsuntoJAXB.getCODIGOORGANODESTINO());
			datosAsunto.setCodigoUnidadAdministrativa (datosAsuntoJAXB.getCODIGOUNIDADADMINISTRATIVA());
			datosAsunto.setDescripcionOrganoDestino(datosAsuntoJAXB.getDESCRIPCIONORGANODESTINO());
			datosAsunto.setExtractoAsunto (datosAsuntoJAXB.getEXTRACTOASUNTO());
			datosAsunto.setIdentificadorTramite (datosAsuntoJAXB.getIDENTIFICADORTRAMITE());
			datosAsunto.setIdiomaAsunto (datosAsuntoJAXB.getIDIOMAASUNTO());
			datosAsunto.setTipoAsunto (datosAsuntoJAXB.getTIPOASUNTO());
		}
		
		return datosAsunto;
	}
	
	private DatosAnexoDocumentacion crearDatosAnexoDocumentacion (DATOSANEXODOCUMENTACION datosAnexoJAXB) throws EstablecerPropiedadException{
		DatosAnexoDocumentacion datosAnexo = null;
		
		if (datosAnexoJAXB != null){
			datosAnexo = crearDatosAnexoDocumentacion ();
			datosAnexo.setCodigoNormalizado (datosAnexoJAXB.getCODIGONORMALIZADO());
			datosAnexo.setCodigoRDS (datosAnexoJAXB.getCODIGORDS());
			
			if (datosAnexoJAXB.getESTRUCTURADO() != null){
				String strEstructurado = datosAnexoJAXB.getESTRUCTURADO().trim();
				
				if (strEstructurado.equalsIgnoreCase("" + ConstantesAsientoXML.DATOSANEXO_ESTRUCTURADO)){
					datosAnexo.setEstucturado (Boolean.TRUE);
				}
				else if (strEstructurado.equalsIgnoreCase("" + ConstantesAsientoXML.DATOSANEXO_NO_ESTRUCTURADO)){
					datosAnexo.setEstucturado (Boolean.FALSE);
				}								
			}
			datosAnexo.setExtractoDocumento (datosAnexoJAXB.getEXTRACTODOCUMENTO());
			
			if (datosAnexoJAXB.getFIRMATERCEROS() != null){
				String strFirmaTerceros = datosAnexoJAXB.getFIRMATERCEROS().trim();
				
				if (strFirmaTerceros.equalsIgnoreCase ("" + ConstantesAsientoXML.DATOSANEXO_FIRMADO_POR_TERCEROS)){
					datosAnexo.setFirmaTerceros (Boolean.TRUE);
				}
				else if (strFirmaTerceros.equalsIgnoreCase ("" + ConstantesAsientoXML.DATOSANEXO_NO_FIRMADO_POR_TERCEROS)){
					datosAnexo.setFirmaTerceros (Boolean.FALSE);
				}
			}
			
			datosAnexo.setHashDocumento (datosAnexoJAXB.getHASHDOCUMENTO());
			datosAnexo.setIdentificadorDocumento (datosAnexoJAXB.getIDENTIFICADORDOCUMENTO());
			datosAnexo.setNombreDocumento (datosAnexoJAXB.getNOMBREDOCUMENTO());
			
			if ( (datosAnexoJAXB.getTIPODEDOCUMENTO() != null) && (!datosAnexoJAXB.getTIPODEDOCUMENTO().trim().equals("")) ){
				datosAnexo.setTipoDocumento (new Character (datosAnexoJAXB.getTIPODEDOCUMENTO().trim().charAt(0)));
			}		
			
			datosAnexo.setIdentificadorDocumento (datosAnexoJAXB.getIDENTIFICADORDOCUMENTO());
		}
		
		return datosAnexo;
	}
	
	// Métodos para realizar la conversión Jerarquía propia de objetos -> JAXB  
	private void cargarDatosHaciaJAXB (Justificante justificante, JUSTIFICANTE justificanteJAXB){
		if ((justificante != null) && (justificanteJAXB != null)){
			justificanteJAXB.setVersion (justificante.getVersion());
			justificanteJAXB.setNUMEROREGISTRO (justificante.getNumeroRegistro());
			
			if (justificante.getFechaRegistro () != null){
				
				SimpleDateFormat fmt = new SimpleDateFormat (ConstantesAsientoXML.FECHA_REGISTRO_FORMATO);											
				justificanteJAXB.setFECHAREGISTRO (fmt.format (justificante.getFechaRegistro ()));				
			}
			justificanteJAXB.setASIENTOREGISTRAL (crearAsientoRegistralJAXB (justificante.getAsientoRegistral()));			
		}		
	}
	
	private void cargarDatosHaciaJAXB (AsientoRegistral asiento, ASIENTOREGISTRAL asientoJAXB){
		if ( (asiento != null) && (asientoJAXB != null)){			
			asientoJAXB.setDATOSASUNTO (crearDatosAsuntoJAXB (asiento.getDatosAsunto ()));
			
			asientoJAXB.setDATOSDESTINO (crearDatosDestinoJAXB (asiento.getDatosDestino ()));
			asientoJAXB.setDATOSORIGEN (crearDatosOrigenJAXB (asiento.getDatosOrigen ()));
						
			// Establecer lista de datos anexo documentacion						
			List lDatosAsientoDocumentacion = asientoJAXB.getDATOSANEXODOCUMENTACION();											
			lDatosAsientoDocumentacion.clear();
			Iterator eAnexos = asiento.getDatosAnexoDocumentacion().iterator();
			
			while (eAnexos.hasNext()){
				lDatosAsientoDocumentacion.add (crearDatosAnexoDocumentacionJAXB ((DatosAnexoDocumentacion) eAnexos.next()));
			}			
			
			// Establecer lista de datos interesado
			Iterator eDatosInteresado = asiento.getDatosInteresado().iterator();
			List lDatosInteresado = asientoJAXB.getDATOSINTERESADO();											
			lDatosInteresado.clear();
						
			while (eDatosInteresado.hasNext()){					
					lDatosInteresado.add (crearDatosInteresadoJAXB ((DatosInteresado) eDatosInteresado.next()));
			}
			
						
			asientoJAXB.setVersion (asiento.getVersion());

		}
	}
	
	/** Crea un objeto JAXB de DatosOrigen (implementación específica) a partir de interfaz DatosOrigen genérica)
	 * @param datosOrigen Objetos que cumple la interfaz DatosOrigen genérica
	 * @return Objeto específico de implementación basado en JAXB
	 */
	protected DATOSORIGEN crearDatosOrigenJAXB (DatosOrigen datosOrigen){
		DATOSORIGEN dOrigenRaw = null;
		
		if (datosOrigen != null){
		
			SimpleDateFormat fmt = new SimpleDateFormat (ConstantesAsientoXML.FECHA_REGISTRO_FORMATO);
			
			dOrigenRaw = new DATOSORIGEN ();
			Date fechaEntradaRegistro = datosOrigen.getFechaEntradaRegistro();
			
			
			dOrigenRaw.setCODIGOENTIDADREGISTRALORIGEN (datosOrigen.getCodigoEntidadRegistralOrigen());
			dOrigenRaw.setFECHAENTRADAREGISTRO ( ( (fechaEntradaRegistro != null) ? fmt.format (fechaEntradaRegistro) : "") );
			dOrigenRaw.setNUMEROREGISTRO (datosOrigen.getNumeroRegistro());
			
			
			if ( (datosOrigen.getTipoRegistro().charValue() == ConstantesAsientoXML.TIPO_REGISTRO_ENTRADA)
					|| (datosOrigen.getTipoRegistro().charValue() == ConstantesAsientoXML.TIPO_REGISTRO_SALIDA)
					|| (datosOrigen.getTipoRegistro().charValue() == ConstantesAsientoXML.TIPO_ACUSE_RECIBO)
					|| (datosOrigen.getTipoRegistro().charValue() == ConstantesAsientoXML.TIPO_ENVIO)
					|| (datosOrigen.getTipoRegistro().charValue() == ConstantesAsientoXML.TIPO_PREENVIO)
					|| (datosOrigen.getTipoRegistro().charValue() == ConstantesAsientoXML.TIPO_PREREGISTRO)
				)
			{
				dOrigenRaw.setTIPOREGISTRO ("" + datosOrigen.getTipoRegistro());
			}
		}
		
		return dOrigenRaw;
	}
	
	/** Crea un objeto JAXB de DireccionCodificada (implementación específica) a partir de interfaz DireccionCodificada genérica)
	 * @param direccionCodificada Objeto que cumple la interfaz DirecionCodificada genérica
	 * @return Objeto específico de implementación basado en JAXB
	 */
	protected DIRECCIONCODIFICADA crearDireccionCodificadaJAXB (DireccionCodificada direccionCodificada){
		DIRECCIONCODIFICADA direccionCodificadaRaw = null;
		
		if (direccionCodificada != null){
			direccionCodificadaRaw = new DIRECCIONCODIFICADA ();
			direccionCodificadaRaw.setCODIGOMUNICIPIO (direccionCodificada.getCodigoMunicipio());
			direccionCodificadaRaw.setCODIGOPOBLACION (direccionCodificada.getCodigoPoblacion());
			direccionCodificadaRaw.setCODIGOPOSTAL (direccionCodificada.getCodigoPostal());
			direccionCodificadaRaw.setCODIGOPROVINCIA (direccionCodificada.getCodigoProvincia());
			direccionCodificadaRaw.setDOMICILIO (direccionCodificada.getDomicilio());
			direccionCodificadaRaw.setFAX (direccionCodificada.getFAX());
			direccionCodificadaRaw.setNOMBREMUNICIPIO (direccionCodificada.getNombreMunicipio());
			direccionCodificadaRaw.setNOMBREPOBLACION (direccionCodificada.getNombrePoblacion());
			direccionCodificadaRaw.setNOMBREPROVINCIA (direccionCodificada.getNombreProvincia());
			direccionCodificadaRaw.setPAISORIGEN (direccionCodificada.getPaisOrigen());
			direccionCodificadaRaw.setTELEFONO (direccionCodificada.getTelefono());
			direccionCodificadaRaw.setEMAIL(direccionCodificada.getEmail());	
		}
		
		return direccionCodificadaRaw;
	}
	
	/** Crea un objeto JAXB de IdentificacionInteresadoDesglosada (implementación específica) a partir de interfaz DireccionCodificada genérica)
	 * @param identDesglosada Objeto que cumple la interfaz DirecionCodificada genérica
	 * @return Objeto específico de implementación basado en JAXB
	 */
	protected IDENTIFICACIONINTERESADODESGLOSADA crearIdentificacionInteresadoDesglosadaJAXB (IdentificacionInteresadoDesglosada identDesglosada){
		IDENTIFICACIONINTERESADODESGLOSADA identDesglosadaRaw = null;
		
		if (identDesglosada != null){
			identDesglosadaRaw = new IDENTIFICACIONINTERESADODESGLOSADA ();
			identDesglosadaRaw.setNOMBREINTERESADO( identDesglosada.getNombre());
			identDesglosadaRaw.setAPELLIDO1INTERESADO(identDesglosada.getApellido1());
			identDesglosadaRaw.setAPELLIDO2INTERESADO(identDesglosada.getApellido2());
		}
		
		return identDesglosadaRaw;
	}
	
	/** Crea un objeto JAXB de DatosInteresado (implementación específica) a partir de interfaz DatosInteresado genérica)
	 * @param datosInteresado Objetos que cumple la interfaz DatosInteresado genérica
	 * @return Objeto específico de implemantción basado en JAXB
	 */
	protected DATOSINTERESADO crearDatosInteresadoJAXB (DatosInteresado datosInteresado){
		DATOSINTERESADO dInteresadoRaw = null;
		
		if (datosInteresado != null){
			
			dInteresadoRaw = new DATOSINTERESADO ();		
			
			// Crear objeto de dirección codificada
			DIRECCIONCODIFICADA rawDir = crearDireccionCodificadaJAXB (datosInteresado.getDireccionCodificada());
			dInteresadoRaw.setDIRECCIONCODIFICADA (rawDir);
			
			// Crear objeto de datos de interesado			
			if (datosInteresado.getFormatoDatosInteresado() != null) {
				dInteresadoRaw.setFORMATODATOSINTERESADO (datosInteresado.getFormatoDatosInteresado());
			}
			dInteresadoRaw.setIDENTIFICACIONINTERESADO (datosInteresado.getIdentificacionInteresado());

			// Crear objeto de identificacion desglosada
			IDENTIFICACIONINTERESADODESGLOSADA rawId = crearIdentificacionInteresadoDesglosadaJAXB (datosInteresado.getIdentificacionInteresadoDesglosada());
			dInteresadoRaw.setIDENTIFICACIONINTERESADODESGLOSADA(rawId);					
			
			dInteresadoRaw.setNUMEROIDENTIFICACION (datosInteresado.getNumeroIdentificacion());		
			if (datosInteresado.getTipoIdentificacion() != null) {
				dInteresadoRaw.setTIPOIDENTIFICACION ("" + datosInteresado.getTipoIdentificacion());
			}
			dInteresadoRaw.setTIPOINTERESADO (datosInteresado.getTipoInteresado());
			if (datosInteresado.getNivelAutenticacion() != null)
				dInteresadoRaw.setNIVELAUTENTICACION("" + datosInteresado.getNivelAutenticacion());			
			dInteresadoRaw.setUSUARIOSEYCON(datosInteresado.getUsuarioSeycon());
		}
		
		return dInteresadoRaw;
	}
	
	/** Crea un objeto JAXB de DatosAsunto (implementación específica) a partir de interfaz DatosAsunto genérica)
	 * @param datosAsunto Objetos que cumple la interfaz DatosAsunto genérica
	 * @return Objeto específico de implemantación basado en JAXB
	 */
	protected DATOSASUNTO crearDatosAsuntoJAXB (DatosAsunto datosAsunto){
		DATOSASUNTO datosAsuntoRaw = null;
		if (datosAsunto != null){
			datosAsuntoRaw = new DATOSASUNTO ();
			
			if (datosAsunto.getFechaAsunto() != null){
				SimpleDateFormat fmt = new SimpleDateFormat (ConstantesAsientoXML.FECHA_REGISTRO_FORMATO);
				datosAsuntoRaw.setFECHAASUNTO(fmt.format (datosAsunto.getFechaAsunto()));
			}
			
			datosAsuntoRaw.setCODIGOORGANODESTINO (datosAsunto.getCodigoOrganoDestino ());
			datosAsuntoRaw.setDESCRIPCIONORGANODESTINO(datosAsunto.getDescripcionOrganoDestino());
			datosAsuntoRaw.setEXTRACTOASUNTO (datosAsunto.getExtractoAsunto ());
			datosAsuntoRaw.setIDIOMAASUNTO ("" + datosAsunto.getIdiomaAsunto());
			
			datosAsuntoRaw.setTIPOASUNTO (datosAsunto.getTipoAsunto());			
			datosAsuntoRaw.setIDENTIFICADORTRAMITE (datosAsunto.getIdentificadorTramite());
			datosAsuntoRaw.setCODIGOUNIDADADMINISTRATIVA (datosAsunto.getCodigoUnidadAdministrativa());
		}
		
		return datosAsuntoRaw;
	}
	
	/** Crea un objeto JAXB de DatosAnexoDocumentacion (implementación específica) a partir de interfaz DatosAnexoDocumentacion genérica)
	 * @param datosAnexoDocumentacion Objetos que cumple la interfaz DatosAnexoDocumentacion genérica
	 * @return Objeto específico de implemantación basado en JAXB
	 */
	protected DATOSANEXODOCUMENTACION crearDatosAnexoDocumentacionJAXB (DatosAnexoDocumentacion datosAnexoDocumentacion){
		DATOSANEXODOCUMENTACION datosAnexoRaw = null;
		
		if (datosAnexoDocumentacion != null){
			datosAnexoRaw = new DATOSANEXODOCUMENTACION ();
			datosAnexoRaw.setCODIGONORMALIZADO (datosAnexoDocumentacion.getCodigoNormalizado ());
			
			if (datosAnexoDocumentacion.isEstructurado() != null){
				datosAnexoRaw.setESTRUCTURADO ( ((datosAnexoDocumentacion.isEstructurado().booleanValue()) ? 
						"" + ConstantesAsientoXML.DATOSANEXO_ESTRUCTURADO : 
						"" + ConstantesAsientoXML.DATOSANEXO_NO_ESTRUCTURADO) 
				);
			}
			
			datosAnexoRaw.setEXTRACTODOCUMENTO (datosAnexoDocumentacion.getExtractoDocumento());
			
			if (datosAnexoDocumentacion.isFirmaTerceros() != null){
				datosAnexoRaw.setFIRMATERCEROS ( ((datosAnexoDocumentacion.isFirmaTerceros().booleanValue()) ? 
						"" + ConstantesAsientoXML.DATOSANEXO_FIRMADO_POR_TERCEROS: 
						"" + ConstantesAsientoXML.DATOSANEXO_NO_FIRMADO_POR_TERCEROS) 
				);
			}
			
			datosAnexoRaw.setHASHDOCUMENTO (datosAnexoDocumentacion.getHashDocumento());
			datosAnexoRaw.setIDENTIFICADORDOCUMENTO (datosAnexoDocumentacion.getIdentificadorDocumento());
			datosAnexoRaw.setCODIGORDS(datosAnexoDocumentacion.getCodigoRDS());
			datosAnexoRaw.setNOMBREDOCUMENTO (datosAnexoDocumentacion.getNombreDocumento());
			
			Character tipoDocumento = datosAnexoDocumentacion.getTipoDocumento();
			datosAnexoRaw.setTIPODEDOCUMENTO ("" + tipoDocumento);
			
			/* NO HACE FALTA, YA SE COMPRUEBA LISTA DE VALORES POSIBLES
			if (tipoDocumento != null){
				if ( (tipoDocumento.charValue() == ConstantesAsientoXML.DATOSANEXO_ACUSE_RECIBO) ||
						(tipoDocumento.charValue() == ConstantesAsientoXML.DATOSANEXO_DATOS_PROPIOS) ||
						(tipoDocumento.charValue() == ConstantesAsientoXML.DATOSANEXO_OTROS)
					)
				{
					datosAnexoRaw.setTIPODEDOCUMENTO ("" + tipoDocumento);
				}
			}
			*/
			
			
		}
		
		return datosAnexoRaw;
	}
	
	/** Crea un objeto JAXB de DatosDestino (implementación específica) a partir de interfaz DatosDestino genérica)
	 * @param datosDestino Objeto que cumple la interfaz DatosDestino genérica
	 * @return Objeto específico de implemantación basado en JAXB
	 */
	protected DATOSDESTINO crearDatosDestinoJAXB (DatosDestino datosDestino){
		DATOSDESTINO datosDestinoRaw = null;
		
		if (datosDestino != null){
			datosDestinoRaw = new DATOSDESTINO ();
			datosDestinoRaw.setCODIGOENTIDADREGISTRALDESTINO (datosDestino.getCodigoEntidadRegistralDestino ());
			datosDestinoRaw.setDECODIFICACIONENTIDADREGISTRALDESTINO (datosDestino.getDecodificacionEntidadRegistralDestino ());
		}		
		
		return datosDestinoRaw;
	}
	
	/** Crea un objeto JAXB de AsientoRegistral (implementación específica) a partir de interfaz AsientoRegistral genérica)
	 * @param asientoRegistral Objeto que cumple la interfaz AsientoRegistral genérica
	 * @return Objeto específico de implemantación basado en JAXB
	 */
	protected ASIENTOREGISTRAL crearAsientoRegistralJAXB (AsientoRegistral asientoRegistral){
		ASIENTOREGISTRAL asientoRegistralRaw = null;
		
		if (asientoRegistral != null){
			asientoRegistralRaw = new ASIENTOREGISTRAL ();
			asientoRegistralRaw.setDATOSASUNTO (crearDatosAsuntoJAXB (asientoRegistral.getDatosAsunto ()));
			
			asientoRegistralRaw.setDATOSDESTINO (crearDatosDestinoJAXB (asientoRegistral.getDatosDestino ()));
			asientoRegistralRaw.setDATOSORIGEN (crearDatosOrigenJAXB (asientoRegistral.getDatosOrigen ()));
						
			// Establecer lista de datos anexo documentacion						
			List lDatosAsientoDocumentacion = asientoRegistralRaw.getDATOSANEXODOCUMENTACION();											
			lDatosAsientoDocumentacion.clear();
			Iterator eAnexos = asientoRegistral.getDatosAnexoDocumentacion().iterator();
			
			while (eAnexos.hasNext()){
				lDatosAsientoDocumentacion.add (crearDatosAnexoDocumentacionJAXB ((DatosAnexoDocumentacion) eAnexos.next()));
			}			
			
			// Establecer lista de datos interesado
			Iterator eDatosInteresado = asientoRegistral.getDatosInteresado().iterator();
			List lDatosInteresado = asientoRegistralRaw.getDATOSINTERESADO();											
			lDatosInteresado.clear();
						
			while (eDatosInteresado.hasNext()){					
					lDatosInteresado.add (crearDatosInteresadoJAXB ((DatosInteresado) eDatosInteresado.next()));
			}
			
						
			asientoRegistralRaw.setVersion (asientoRegistral.getVersion());
		}
		return asientoRegistralRaw;
	}	
	
	

}
