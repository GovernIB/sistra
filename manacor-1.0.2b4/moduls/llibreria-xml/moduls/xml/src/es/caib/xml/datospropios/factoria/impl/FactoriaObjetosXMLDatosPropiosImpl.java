package es.caib.xml.datospropios.factoria.impl;

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
import es.caib.xml.FormatoCampoNoSoportadoException;
import es.caib.xml.GuardaObjetoXMLException;
import es.caib.xml.InicializacionFactoriaException;
import es.caib.xml.datospropios.factoria.ConstantesDatosPropiosXML;
import es.caib.xml.datospropios.factoria.FactoriaObjetosXMLDatosPropios;
import es.caib.xml.datospropios.modelo.DATOSPROPIOS;
import es.caib.xml.datospropios.modelo.DATO;
import es.caib.xml.datospropios.modelo.DOCUMENTO;
import es.caib.xml.datospropios.modelo.DOCUMENTOSENTREGAR;
import es.caib.xml.datospropios.modelo.INSTRUCCIONES;
import es.caib.xml.datospropios.modelo.ObjectFactory;
import es.caib.xml.datospropios.modelo.SOLICITUD;


/** Implementa una factoría capaz de crear objetos que encapsulan el acceso
 * al documento XML de datos propios
 * 
 * @author magroig
 *
 */
public class FactoriaObjetosXMLDatosPropiosImpl implements
		FactoriaObjetosXMLDatosPropios {
	
	private static final String FICHERO_PROPIEDADES_JAXB = "datospropios_JAXB.properties"; 
	private static final String PAQUETE_FORMULARIO_PROP = "PAQUETE_MODELO_DATOSPROPIOS_IMPL";	 
	
	private JAXBContext contextoJAXBDatosPropios;
	private Unmarshaller unmshDatosPropios;
	private Marshaller mshDatosPropios;
	private ObjectFactory ofDatosPropios;

	/** Crea la factoría. Se necesita que exista en el paquete un fichero properies (datospropios_JAXB.properties),
	 * que debe contener la propiedad PAQUETE_MODELO_DATOSPROPIOS_IMPL, cuyo valor indicará cual es el
	 * paquete donde se encuentra la jerarquía de clases generadas mediante JAXB para manejar el acceso
	 * a la creación/recuperación de contenido para el documento de DATOS_PROPIOS
	 * 
	 * @throws InicializacionFactoriaException Se ha producido un error inesperado al incializar la factoría
	 */
	public FactoriaObjetosXMLDatosPropiosImpl () throws InicializacionFactoriaException {
		try {
			
			Properties propsJAXB = obtenerPropiedadesJAXB ();
			
			contextoJAXBDatosPropios = JAXBContext.newInstance (obtenerPaqueteImplDatosPropios (propsJAXB));
			unmshDatosPropios = contextoJAXBDatosPropios.createUnmarshaller();
			mshDatosPropios = contextoJAXBDatosPropios.createMarshaller();	
			ofDatosPropios = new ObjectFactory ();
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
	private String obtenerPaqueteImplDatosPropios (Properties propiedadesJAXB) throws IOException {		
		return propiedadesJAXB.getProperty(PAQUETE_FORMULARIO_PROP);
	}
	
	/* (non-Javadoc)
	 * @see es.caib.xml.datospropios.factoria.FactoriaObjetosXMLDatosPropios#crearDatosPropios()
	 */
	public DatosPropios crearDatosPropios() {		
		return new DatosPropios ();					
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.datospropios.factoria.FactoriaObjetosXMLDatosPropios#crearDatosPropios(java.io.InputStream)
	 */
	public DatosPropios crearDatosPropios(InputStream datosXMLDatosPropios)
			throws CargaObjetoXMLException {

		DatosPropios datosPropios = null;
		
		try {			
			DATOSPROPIOS datosPropiosJAXB = (DATOSPROPIOS) unmshDatosPropios.unmarshal(datosXMLDatosPropios);
			datosPropios = crearDatosPropios ();
			cargarDatosDesdeJAXB (datosPropiosJAXB, datosPropios);
											
		} catch (Exception e) {
			throw new CargaObjetoXMLException (e.getClass().getName() + ": " + e.getLocalizedMessage(), "DatosPropios", datosXMLDatosPropios);
		}		
		
		return datosPropios;			
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.datospropios.factoria.FactoriaObjetosXMLDatosPropios#crearDatosPropios(java.io.File)
	 */
	public DatosPropios crearDatosPropios(File ficheroXMLDatosPropios)
			throws CargaObjetoXMLException {
		
		if (ficheroXMLDatosPropios == null){
			throw new CargaObjetoXMLException ("Se ha especificado un fichero nulo",
					"DatosPropios", "");
		}
		
		DatosPropios datosPropios = null;
		try {
			datosPropios = crearDatosPropios (new FileInputStream (ficheroXMLDatosPropios));		
		} catch (FileNotFoundException e) {
			throw new CargaObjetoXMLException (e.getClass().getName() + ": " + e.getLocalizedMessage(),
					"DatosPropios", ficheroXMLDatosPropios.getName());
		}
		
		return datosPropios;	
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.datospropios.factoria.FactoriaObjetosXMLDatosPropios#guardarDatosPropios(es.caib.xml.datospropios.factoria.DatosPropios, java.io.OutputStream)
	 */
	public void guardarDatosPropios(DatosPropios datosPropios,
			OutputStream datosXMLdatosPropios) 
	throws es.caib.xml.GuardaObjetoXMLException, EstablecerPropiedadException {
		
		// Validar que el output stream no es nulo
		if (datosXMLdatosPropios == null)
			throw new es.caib.xml.GuardaObjetoXMLException("Se ha especificado un flujo de salida nulo", 
					"DatosPropios", (OutputStream) null);
		
		// Validar que el justificante no es nulo
		if (datosPropios == null)
			throw new es.caib.xml.GuardaObjetoXMLException("Se ha especificado un objeto formulario nulo", 
					"DatosPropios", (OutputStream) null);
		
		// Validar que al justificante tiene todos los datos requeridos
		datosPropios.comprobarDatosRequeridos();
		
		// Crear objetos JAXB equivalentes
		DATOSPROPIOS datosPropiosImplInterno = null;
		
		try {
			datosPropiosImplInterno = ofDatosPropios.createDATOSPROPIOS();
		} catch (Exception e1) {
			throw new es.caib.xml.GuardaObjetoXMLException ("Se ha producido una excepción al crear un objetos DatosPropios JAXB", 
					"DatosPropios", (OutputStream) null);
		}
		
		
		cargarDatosHaciaJAXB (datosPropios, datosPropiosImplInterno);		
		
		
		//	Hemos obtenido el objeto JAXB equivalente al nodo datos propios, podemos guardar el XML		
		try {
			mshDatosPropios.marshal (datosPropiosImplInterno, datosXMLdatosPropios);
		} catch (JAXBException e) {
			throw new es.caib.xml.GuardaObjetoXMLException("Se ha producido un error al guardar datos propios [" 
					+ e.getClass().getName() + ": " + e.getLocalizedMessage() + "]", 
					"DatosPropios", datosXMLdatosPropios);
		}
								
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.datospropios.factoria.FactoriaObjetosXMLDatosPropios#guardarDatosPropios(es.caib.xml.datospropios.factoria.DatosPropios, java.io.File)
	 */
	public void guardarDatosPropios(DatosPropios datosPropios,
			File ficheroXMLDatosPropios) 
	throws es.caib.xml.GuardaObjetoXMLException, EstablecerPropiedadException {

		try {
			guardarDatosPropios (datosPropios, new FileOutputStream (ficheroXMLDatosPropios));
		} catch (FileNotFoundException e) {
			throw new es.caib.xml.GuardaObjetoXMLException (e.getClass().getName() + ": " + e.getLocalizedMessage(),
					"DatosPropios", ficheroXMLDatosPropios.getName());
		} 		
		

	}

	/* (non-Javadoc)
	 * @see es.caib.xml.datospropios.factoria.FactoriaObjetosXMLDatosPropios#guardarDatosPropios(es.caib.xml.datospropios.factoria.DatosPropios)
	 */
	public String guardarDatosPropios(DatosPropios datosPropios)
	throws es.caib.xml.GuardaObjetoXMLException, EstablecerPropiedadException 
	{
	
		ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream(); 
		guardarDatosPropios (datosPropios, byteOutputStream);
		String encoding = getEncoding ();
		
		if ( (encoding != null) && (!encoding.trim().equals(""))){
			try {
				return byteOutputStream.toString(encoding);
			} catch (UnsupportedEncodingException e) {			
				throw new GuardaObjetoXMLException("La codificación " + encoding + " no está soportada", "DatosPropios", (OutputStream) null);
			}
		}
		else
			return byteOutputStream.toString();
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.datospropios.factoria.FactoriaObjetosXMLDatosPropios#crearSolicitud()
	 */
	public Solicitud crearSolicitud() {		
		return new Solicitud ();						
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.datospropios.factoria.FactoriaObjetosXMLDatosPropios#crearDato()
	 */
	public Dato crearDato() {
		return new Dato ();			
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.FactoriaObjetosXML#getEncoding()
	 */
	public String getEncoding() {
		try {
			return mshDatosPropios.getProperty("jaxb.encoding").toString();
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
			mshDatosPropios.setProperty("jaxb.encoding", encoding);
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
			return ((Boolean) mshDatosPropios.getProperty("jaxb.formatted.output")).booleanValue();
		} catch (PropertyException e) {
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.FactoriaObjetosXML#setIndentacion(boolean)
	 */
	public void setIndentacion(boolean indentacion) {
		try {
			mshDatosPropios.setProperty("jaxb.formatted.output", (indentacion) ? Boolean.TRUE : Boolean.FALSE);
		} catch (PropertyException e) {
			// TODO Manejar excepción de forma más conveniente
			;
		}

	}


	/* (non-Javadoc)
	 * @see es.caib.xml.datospropios.factoria.FactoriaObjetosXMLDatosPropios#crearInstrucciones()
	 */
	public Instrucciones crearInstrucciones() {	
		return new Instrucciones ();					
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.datospropios.factoria.FactoriaObjetosXMLDatosPropios#crearDocumento()
	 */
	public Documento crearDocumento() {	
		return new Documento ();					
	}


	/* (non-Javadoc)
	 * @see es.caib.xml.datospropios.factoria.FactoriaObjetosXMLDatosPropios#crearDocumentosEntregar()
	 */
	public DocumentosEntregar crearDocumentosEntregar() {		
		return new DocumentosEntregar ();						
	}
	
	// Métodos para realizar la conversión JAXB -> Jerarquía propia de objetos
	private void cargarDatosDesdeJAXB (DATOSPROPIOS datosPropiosJAXB, DatosPropios datosPropios) throws EstablecerPropiedadException{
		if ( (datosPropios != null) && (datosPropiosJAXB != null) ){
			datosPropios.setInstrucciones (crearInstrucciones (datosPropiosJAXB.getINSTRUCCIONES()));
			datosPropios.setSolicitud (crearSolicitud (datosPropiosJAXB.getSOLICITUD()));
		}
	}
	
	private Documento crearDocumento (DOCUMENTO documentoJAXB) throws EstablecerPropiedadException{
		Documento documento = null;
		
		if (documentoJAXB != null){
			documento = crearDocumento ();
			documento.setTitulo (documentoJAXB.getTITULO());
			documento.setIdentificador (documentoJAXB.getIDENTIFICADOR());
			
			if ( (documentoJAXB.getTIPO() != null) && (!documentoJAXB.getTIPO().trim().equals("")) ){
				documento.setTipo (new Character (documentoJAXB.getTIPO().charAt (0)));
			}			
			
			if ( (documentoJAXB.getTIPO() != null) && (!documentoJAXB.getTIPO().trim().equals ("")) ){
				char tipo = documentoJAXB.getTIPO().trim().charAt (0);
				
				if ( (tipo == ConstantesDatosPropiosXML.DOCUMENTOSENTREGAR_TIPO_JUSTIFICANTE) || 
					 (tipo == ConstantesDatosPropiosXML.DOCUMENTOSENTREGAR_TIPO_FORMULARIO) || 
					 (tipo == ConstantesDatosPropiosXML.DOCUMENTOSENTREGAR_TIPO_FORMULARIO_JUSTIFICANTE)){
					documento.setFirmar ( new Boolean (documentoJAXB.isFIRMAR()));
				}
				else if (tipo == ConstantesDatosPropiosXML.DOCUMENTOSENTREGAR_TIPO_ANEXO){
					documento.setCompulsar (new Boolean (documentoJAXB.isCOMPULSAR()));
					documento.setFotocopia (new Boolean (documentoJAXB.isFOTOCOPIA()));
				}
			}
		}
		
		return documento;
	}
	
	private DocumentosEntregar crearDocumentosEntregar (DOCUMENTOSENTREGAR documentosEntJAXB) throws EstablecerPropiedadException{
		DocumentosEntregar docs = null;
		
		if (documentosEntJAXB != null){
			docs = crearDocumentosEntregar ();
			Iterator  itDocsJAXB = documentosEntJAXB.getDOCUMENTO().iterator();
			while (itDocsJAXB.hasNext()){
				Documento doc = crearDocumento ((DOCUMENTO) itDocsJAXB.next());
				docs.getDocumento().add (doc);
			}
		}
		
		return docs;
	}
	
	private Instrucciones crearInstrucciones (INSTRUCCIONES instJAXB) throws EstablecerPropiedadException{
		Instrucciones instrucciones = null;
		
		if (instJAXB != null){
			instrucciones = crearInstrucciones ();
			
			instrucciones.setDocumentosEntregar (crearDocumentosEntregar (instJAXB.getDOCUMENTOSENTREGAR()));
			
			SimpleDateFormat sdt = new SimpleDateFormat ("yyyyMMddHHmmss");
			Date f = null;
			if (instJAXB.getFECHATOPEENTREGA() != null){
				try {
					f = sdt.parse(instJAXB.getFECHATOPEENTREGA());
				}
				catch (ParseException e){
					throw new FormatoCampoNoSoportadoException("yyyyMMddHHmmss","Instrucciones","FechaTopeEntrega",instJAXB.getFECHATOPEENTREGA());
				}
				instrucciones.setFechaTopeEntrega (f);
			}						
			
			if (instJAXB.getIDENTIFICADORPERSISTENCIA() != null){
				instrucciones.setIdentificadorPersistencia(instJAXB.getIDENTIFICADORPERSISTENCIA());
			}
			
			if (instJAXB.getTEXTOFECHATOPEENTREGA() != null){
				instrucciones.setTextoFechaTopeEntrega(instJAXB.getTEXTOFECHATOPEENTREGA());
			}
			
			instrucciones.setTextoInstrucciones (instJAXB.getTEXTOINSTRUCCIONES());
			
			if (instJAXB.getHABILITARNOTIFICACIONTELEMATICA() != null){
				instrucciones.setHabilitarNotificacionTelematica(instJAXB.getHABILITARNOTIFICACIONTELEMATICA());
			}
			
			if (instJAXB.getHABILITARAVISOS() != null){
				instrucciones.setHabilitarAvisos(instJAXB.getHABILITARAVISOS());
			}
			
			if (instJAXB.getAVISOSMS() != null){
				instrucciones.setAvisoSMS(instJAXB.getAVISOSMS());
			}
			
			if (instJAXB.getAVISOEMAIL() != null){
				instrucciones.setAvisoEmail(instJAXB.getAVISOEMAIL());
			}
		}
		
		return instrucciones;
	}
	
	private Dato crearDato (DATO datoJAXB) throws EstablecerPropiedadException {
		Dato dato = null;
		
		if (datoJAXB != null){
			dato = crearDato ();
			dato.setDescripcion (datoJAXB.getDESCRIPCION());
			if ( (datoJAXB.getTIPO() != null) && (!datoJAXB.getTIPO().trim().equals(""))){
				dato.setTipo (new Character (datoJAXB.getTIPO().trim().charAt (0)));
			}
			dato.setValor (datoJAXB.getVALOR());
		}
		
		return dato;
	}
	
	private Solicitud crearSolicitud (SOLICITUD solicitudJAXB) throws EstablecerPropiedadException {
		Solicitud solicitud = null;
		
		if (solicitudJAXB != null){
			solicitud = crearSolicitud ();
			Iterator itSolsJAXB = solicitudJAXB.getDATO().iterator();
			
			while (itSolsJAXB.hasNext()){
				DATO datoJAXB = (DATO) itSolsJAXB.next();
				Dato dato = crearDato (datoJAXB);
				solicitud.getDato().add (dato);
			}
		}
		
		return solicitud;
	}
	
	//	Métodos para realizar la conversión Jerarquía propia de objetos -> JAXB  
	private void cargarDatosHaciaJAXB (DatosPropios datosPropios, DATOSPROPIOS datosPropiosJAXB) throws EstablecerPropiedadException{
		if ( (datosPropios != null) && (datosPropiosJAXB != null) ){
			datosPropiosJAXB.setINSTRUCCIONES (crearInstruccionesJAXB (datosPropios.getInstrucciones()));
			datosPropiosJAXB.setSOLICITUD (crearSolicitudJAXB (datosPropios.getSolicitud()));
		}
	}
	
	private DOCUMENTO crearDocumentoJAXB (Documento documento){
		DOCUMENTO documentoJAXB = null;
		
		if (documento != null){
			documentoJAXB = new DOCUMENTO ();
			
			Character tipo = documento.getTipo(); 
			
			if ( tipo != null){
				documentoJAXB.setTIPO ("" + tipo.charValue());
				
				if ( (tipo.charValue() == ConstantesDatosPropiosXML.DOCUMENTOSENTREGAR_TIPO_JUSTIFICANTE) ||
						(tipo.charValue() == ConstantesDatosPropiosXML.DOCUMENTOSENTREGAR_TIPO_FORMULARIO_JUSTIFICANTE) || 	
					 (tipo.charValue() == ConstantesDatosPropiosXML.DOCUMENTOSENTREGAR_TIPO_FORMULARIO) && (documento.isFirmar() != null)
				   ){
					documentoJAXB.setFIRMAR (documento.isFirmar().booleanValue());
				}
				else if ((tipo.charValue() == ConstantesDatosPropiosXML.DOCUMENTOSENTREGAR_TIPO_ANEXO) && (documento.isCompulsar() != null) && (documento.isFotocopia() != null)){
					documentoJAXB.setCOMPULSAR (documento.isCompulsar().booleanValue());
					documentoJAXB.setFOTOCOPIA (documento.isFotocopia().booleanValue());
				}
			}
			
			if ( (documento.getTitulo() != null) && (!documento.getTitulo().equals("")) ){
				documentoJAXB.setTITULO (documento.getTitulo());
			}
			
			if ( (documento.getIdentificador() != null) && (!documento.getIdentificador().equals("")) ){
				documentoJAXB.setIDENTIFICADOR(documento.getIdentificador());
			}
														
		}
		
		return documentoJAXB;
	}
	
	private DOCUMENTOSENTREGAR crearDocumentosEntregarJAXB (DocumentosEntregar documentos) throws EstablecerPropiedadException{
		DOCUMENTOSENTREGAR docsJAXB = null;
		
		if (documentos != null){
			docsJAXB = new DOCUMENTOSENTREGAR ();
			List listaDocumentosJAXB = docsJAXB.getDOCUMENTO();
			Iterator itListaDocumentos = documentos.getDocumento().iterator();
			
			while (itListaDocumentos.hasNext()){
				Object obj =  itListaDocumentos.next();
				
				if (obj instanceof Documento){
					listaDocumentosJAXB.add (crearDocumentoJAXB ((Documento) obj));
				}
				else {
					throw new EstablecerPropiedadException ("El objeto no es de tipo Documento", "DocumentosEntregar", "Documento", obj);
				}
			}
		}
		
		return docsJAXB;
	}
	
	private INSTRUCCIONES crearInstruccionesJAXB (Instrucciones instrucciones) throws EstablecerPropiedadException{
		INSTRUCCIONES instruccionesJAXB = null;
		
		if (instrucciones != null){
			instruccionesJAXB = new INSTRUCCIONES ();
			
			if (instrucciones.getTextoInstrucciones() != null){
				instruccionesJAXB.setTEXTOINSTRUCCIONES (instrucciones.getTextoInstrucciones());
			}
			
			if (instrucciones.getFechaTopeEntrega() != null){
				SimpleDateFormat fmt = new SimpleDateFormat ("yyyyMMddHHmmss");
				instruccionesJAXB.setFECHATOPEENTREGA(fmt.format(instrucciones.getFechaTopeEntrega()));
			}
			
			if (instrucciones.getTextoFechaTopeEntrega() != null){
				instruccionesJAXB.setTEXTOFECHATOPEENTREGA(instrucciones.getTextoFechaTopeEntrega());
			}
			
			
			if (instrucciones.getIdentificadorPersistencia() != null){
				instruccionesJAXB.setIDENTIFICADORPERSISTENCIA(instrucciones.getIdentificadorPersistencia());
			}
			
			instruccionesJAXB.setDOCUMENTOSENTREGAR (crearDocumentosEntregarJAXB (instrucciones.getDocumentosEntregar()));
			
			if (instrucciones.getHabilitarNotificacionTelematica() != null){
				instruccionesJAXB.setHABILITARNOTIFICACIONTELEMATICA(instrucciones.getHabilitarNotificacionTelematica());
			}
			
			if (instrucciones.getHabilitarAvisos() != null){
				instruccionesJAXB.setHABILITARAVISOS(instrucciones.getHabilitarAvisos());
			}
			
			if (instrucciones.getAvisoSMS() != null){
				instruccionesJAXB.setAVISOSMS(instrucciones.getAvisoSMS());
			}
			
			if (instrucciones.getAvisoEmail() != null){
				instruccionesJAXB.setAVISOEMAIL(instrucciones.getAvisoEmail());
			}
		}
		
		return instruccionesJAXB;
	}	
	
	private DATO crearDatoJAXB (Dato dato){
		DATO datoJAXB = null;
		
		if (dato != null){
			datoJAXB = new DATO ();
			datoJAXB.setTIPO ("" + dato.getTipo());
			datoJAXB.setDESCRIPCION (dato.getDescripcion());
			datoJAXB.setVALOR (dato.getValor());
		}
		
		return datoJAXB;
		
	}
	
	private SOLICITUD crearSolicitudJAXB (Solicitud solicitud) throws EstablecerPropiedadException{
		SOLICITUD solicitudJAXB = null;
		
		if (solicitud != null){
			solicitudJAXB = new SOLICITUD ();
			
			Iterator itDatos = solicitud.getDato().iterator();
			
			while (itDatos.hasNext()){
				Object obj = itDatos.next();
				
				if (obj instanceof Dato){
					solicitudJAXB.getDATO().add (crearDatoJAXB ((Dato) obj));
				}
				else{
					throw new EstablecerPropiedadException ("El objeto no es de tipo Dato", "Solicitud", "Dato", obj);
				}
			}
		}
		
		return solicitudJAXB;
	}
	
}
