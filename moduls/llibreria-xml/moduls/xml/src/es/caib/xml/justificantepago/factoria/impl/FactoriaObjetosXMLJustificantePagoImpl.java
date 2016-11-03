package es.caib.xml.justificantepago.factoria.impl;

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
import java.util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;

import es.caib.xml.CargaObjetoXMLException;
import es.caib.xml.EstablecerPropiedadException;
import es.caib.xml.FormatoCampoNoSoportadoException;
import es.caib.xml.GuardaObjetoXMLException;
import es.caib.xml.InicializacionFactoriaException;
import es.caib.xml.justificantepago.factoria.ConstantesJustificantePagoXML;
import es.caib.xml.justificantepago.factoria.FactoriaObjetosXMLJustificantePago;
import es.caib.xml.justificantepago.modelo.DATOSPAGO;
import es.caib.xml.justificantepago.modelo.JUSTIFICANTEPAGO;
import es.caib.xml.justificantepago.modelo.ObjectFactory;

import javax.xml.bind.Unmarshaller;

public class FactoriaObjetosXMLJustificantePagoImpl implements
		FactoriaObjetosXMLJustificantePago {
	
	private static final String FICHERO_PROPIEDADES_JAXB = "justificante_pago_JAXB.properties"; 
	private static final String PAQUETE_FORMULARIO_PROP = "PAQUETE_MODELO_JUSTIFICANTE_PAGO_IMPL";	 
	
	private JAXBContext contextoJAXBJustificantePago;
	private Unmarshaller unmshJustificantePago;
	private Marshaller mshJustificantePago;
	private ObjectFactory ofJustificantePago;

	public FactoriaObjetosXMLJustificantePagoImpl() throws InicializacionFactoriaException {
		try {
			
			Properties propsJAXB = obtenerPropiedadesJAXB ();
			
			contextoJAXBJustificantePago = JAXBContext.newInstance (obtenerPaqueteImplJustificante(propsJAXB));
			unmshJustificantePago = contextoJAXBJustificantePago.createUnmarshaller();
			mshJustificantePago = contextoJAXBJustificantePago.createMarshaller();	
			ofJustificantePago = new ObjectFactory ();
		} catch (JAXBException e) {
			throw new InicializacionFactoriaException (e.getClass().getName() + " -> " + e.getLocalizedMessage(), 
					"JAXB");
		}
		catch (IOException ioe){
			throw new InicializacionFactoriaException ("Se ha producido un error al leer propiedades JAXB -> " + ioe.getLocalizedMessage(), 
			"JAXB");
		}
	}
	
	private String obtenerPaqueteImplJustificante(Properties propiedadesJAXB) throws IOException {		
		return propiedadesJAXB.getProperty(PAQUETE_FORMULARIO_PROP);
	}
	

	private Properties obtenerPropiedadesJAXB () throws IOException{
		Properties prop = new Properties ();
		
		prop.load(getClass().getResourceAsStream (FICHERO_PROPIEDADES_JAXB));
		
		return prop;
	}

	public JustificantePago crearJustificantePago() {		
		return new JustificantePago ();
	}

	public JustificantePago crearJustificantePago(InputStream datosXMLJustificantePago) 
	throws CargaObjetoXMLException 
	{
		JustificantePago justificantePago = null;
		
		try {			
			JUSTIFICANTEPAGO justificantePagoJAXB = (JUSTIFICANTEPAGO) unmshJustificantePago.unmarshal(datosXMLJustificantePago);
			justificantePago = crearJustificantePago ();
			// JAXB -> Objetos propios
			cargarDatosDesdeJAXB (justificantePagoJAXB, justificantePago);											
		} catch (Exception e) {
			throw new CargaObjetoXMLException (e.getClass().getName() + ": " + e.getLocalizedMessage(), "formulario", datosXMLJustificantePago);
		}		
		
		return justificantePago;	
	}

	public JustificantePago crearJustificantePago( File ficheroXMLJustificantePago) 
	throws CargaObjetoXMLException 
	{
		if (ficheroXMLJustificantePago == null){
			throw new CargaObjetoXMLException ("Se ha especificado un fichero nulo",
					"justificantePago", "");
		}
		
		JustificantePago justificantePago = null;
		try {
			justificantePago = crearJustificantePago (new FileInputStream (ficheroXMLJustificantePago));		
		} catch (FileNotFoundException e) {
			throw new CargaObjetoXMLException (e.getClass().getName() + ": " + e.getLocalizedMessage(),
					"JustificantePago", ficheroXMLJustificantePago.getName());
		}
		
		return justificantePago;		
	}

	public void guardarJustificantePago(JustificantePago justificantePago,OutputStream datosXMLJustificantePago)
	throws GuardaObjetoXMLException, EstablecerPropiedadException 
	{
		// Validar que el output stream no es nulo
		if (datosXMLJustificantePago == null)
			throw new es.caib.xml.GuardaObjetoXMLException("Se ha especificado un flujo de salida nulo", 
					"JustificantePago", (OutputStream) null);
		
		// Validar que el justificante no es nulo
		if (justificantePago == null)
			throw new es.caib.xml.GuardaObjetoXMLException("Se ha especificado un objeto JustificantePago nulo", 
					"JustificantePago", (OutputStream) null);
		
		// Validar que al justificante tiene todos los datos requeridos
		justificantePago.comprobarDatosRequeridos();
		
		// Crear objetos JAXB equivalentes
		JUSTIFICANTEPAGO justificantePagoImplInterno = null;
		
		try {
			justificantePagoImplInterno = ofJustificantePago.createJUSTIFICANTEPAGO();
		} catch (Exception e1) {
			throw new es.caib.xml.GuardaObjetoXMLException ("Se ha producido una excepción al crear un objeto JustificantePago JAXB", 
					"JustificantePago", (OutputStream) null);
		}
				
		cargarDatosHaciaJAXB (justificantePago, justificantePagoImplInterno);
		
		try {
			mshJustificantePago.marshal (justificantePagoImplInterno, datosXMLJustificantePago);
		} catch (JAXBException e) {
			throw new es.caib.xml.GuardaObjetoXMLException("Se ha producido un error al guardar el JustificantePago [" 
					+ e.getClass().getName() + ": " + e.getLocalizedMessage() + "]", 
					"JustificantePago", datosXMLJustificantePago);
		}

	}

	public void guardarJustificantePago(JustificantePago justificantePago, File ficheroXMLJustificantePago) 
	throws GuardaObjetoXMLException, EstablecerPropiedadException 
	{
		try {
			guardarJustificantePago (justificantePago, new FileOutputStream (ficheroXMLJustificantePago));
		} catch (FileNotFoundException e) {
			throw new es.caib.xml.GuardaObjetoXMLException (e.getClass().getName() + ": " + e.getLocalizedMessage(),
					"justificantePago", ficheroXMLJustificantePago.getName());
		} 
	}

	public String guardarJustificantePago(JustificantePago justificantePago) throws GuardaObjetoXMLException, EstablecerPropiedadException {
		ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream(); 
		guardarJustificantePago (justificantePago, byteOutputStream);
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

	public boolean isIdentacion() {
		try {
			return ((Boolean) mshJustificantePago.getProperty("jaxb.formatted.output")).booleanValue();
		} catch (PropertyException e) {
			return false;
		}
	}

	public void setIndentacion(boolean indentacion) {
		try {
			mshJustificantePago.setProperty("jaxb.formatted.output", (indentacion) ? Boolean.TRUE : Boolean.FALSE);
		} catch (PropertyException e) {
			// TODO Manejar excepción de forma más conveniente
			;
		}
	}

	public DatosPago crearDatosPago() {		
		return new DatosPago ();
	}

	public String getEncoding() {
		try {
			return mshJustificantePago.getProperty("jaxb.encoding").toString();
		} catch (PropertyException e) {
			//TODO quizá manejar mejor la excepción
			return null;
		}
	}

	public void setEncoding(String encoding) {
		try {
			mshJustificantePago.setProperty("jaxb.encoding", encoding);
		} catch (PropertyException e) {
			//TODO manejar correctamente la excepción
			;
		}
	}
	
	//	Métodos para realizar la conversión JAXB -> Jerarquía propia de objetos
	private void cargarDatosDesdeJAXB (JUSTIFICANTEPAGO justificantePagoJAXB, JustificantePago justificantePago) throws EstablecerPropiedadException{
		if ( (justificantePago != null) && (justificantePagoJAXB != null) ){
			justificantePago.setFirma (justificantePagoJAXB.getFIRMA());
			justificantePago.setDatosPago (crearDatosPago (justificantePagoJAXB.getDATOSPAGO()));
		}
	}
	
	private DatosPago crearDatosPago (DATOSPAGO datosPagoJAXB) throws EstablecerPropiedadException{
		DatosPago datosPago = null;
		
		if (datosPagoJAXB != null){
			datosPago = crearDatosPago ();			
			datosPago.setLocalizador (datosPagoJAXB.getLOCALIZADOR());
			datosPago.setDui (datosPagoJAXB.getDUI());	
			
			SimpleDateFormat fmt = new SimpleDateFormat (ConstantesJustificantePagoXML.FECHA_PAGO_FORMATO);
			Date f = null;
			try {
				f = fmt.parse (datosPagoJAXB.getFECHAPAGO().trim());
			} catch (ParseException e) {
				throw new FormatoCampoNoSoportadoException (ConstantesJustificantePagoXML.FECHA_PAGO_FORMATO, 
						"DatosPago", "FechaPago", datosPagoJAXB.getFECHAPAGO().trim());
			}
			
			datosPago.setFechaPago (f);
		}
		
		return datosPago;
	}
	
	// Métodos para realizar la conversión Jerarquía propia de objetos -> JAXB 
	private void cargarDatosHaciaJAXB (JustificantePago justificantePago, JUSTIFICANTEPAGO justificantePagoJAXB) throws EstablecerPropiedadException{
		if ( (justificantePago != null) && (justificantePagoJAXB != null) ){
			justificantePagoJAXB.setFIRMA (justificantePago.getFirma());
			justificantePagoJAXB.setDATOSPAGO (crearDatosPagoJAXB (justificantePago.getDatosPago()));
		}
	}
	
	private DATOSPAGO crearDatosPagoJAXB (DatosPago datosPago){
		DATOSPAGO datosPagoJAXB = null;
		
		if (datosPago != null){
			datosPagoJAXB = new DATOSPAGO();
			datosPagoJAXB.setLOCALIZADOR (datosPago.getLocalizador());
			datosPagoJAXB.setDUI (datosPago.getDui());
			
			String strFechaPago = null;
			Date fechaPago = datosPago.getFechaPago();
			if (fechaPago != null){				
				
				if (fechaPago != null){
					SimpleDateFormat sdt = new SimpleDateFormat (ConstantesJustificantePagoXML.FECHA_PAGO_FORMATO);					
					strFechaPago = sdt.format (fechaPago);					
				}
				 
			}
			
			datosPagoJAXB.setFECHAPAGO (strFechaPago);			
		}
		
		return datosPagoJAXB;
	}
}
