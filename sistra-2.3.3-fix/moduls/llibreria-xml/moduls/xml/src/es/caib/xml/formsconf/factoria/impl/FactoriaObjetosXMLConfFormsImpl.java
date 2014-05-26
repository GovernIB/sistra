package es.caib.xml.formsconf.factoria.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
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

import es.caib.xml.formsconf.modelo.ObjectFactory;
import es.caib.xml.formsconf.factoria.FactoriaObjetosXMLConfForms;

import es.caib.xml.util.HashtableIterable;

public class FactoriaObjetosXMLConfFormsImpl implements
		FactoriaObjetosXMLConfForms {
	
	private static final String FICHERO_PROPIEDADES_JAXB = "formsconf_JAXB.properties"; 
	private static final String PAQUETE_FORMULARIO_PROP = "PAQUETE_MODELO_FORMSCONF_IMPL";	 
	
	private JAXBContext contextoJAXBConfForms;
	private Unmarshaller unmshConfForms;
	private Marshaller mshConfForms;
	private ObjectFactory ofConfForms;
	
	public FactoriaObjetosXMLConfFormsImpl () throws InicializacionFactoriaException {
		try {
			
			Properties propsJAXB = obtenerPropiedadesJAXB ();
			
			contextoJAXBConfForms = JAXBContext.newInstance (obtenerPaqueteImplDatosPropios (propsJAXB));
			unmshConfForms = contextoJAXBConfForms.createUnmarshaller();
			mshConfForms = contextoJAXBConfForms.createMarshaller();	
			ofConfForms = new ObjectFactory ();
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

	public ConfiguracionForms crearConfiguracionForms() {		
		return new ConfiguracionForms ();		
	}

	public ConfiguracionForms crearConfiguracionForms(
			InputStream datosXMLConfForms) throws CargaObjetoXMLException {
		
		ConfiguracionForms configuracionForms = null;
		
		try {			
			es.caib.xml.formsconf.modelo.Configuracion configuracionFormsJAXB = (es.caib.xml.formsconf.modelo.Configuracion) unmshConfForms.unmarshal (datosXMLConfForms);			
			configuracionForms = crearConfiguracionForms ();
			try {
				// JAXB -> Objetos propios
				cargarDatosDesdeJAXB (configuracionFormsJAXB, configuracionForms);
			
			} catch (Exception e) {
				throw new CargaObjetoXMLException (e.getClass().getName() + ": " + e.getLocalizedMessage(), "ConfiguracionForms", datosXMLConfForms);
			}			
											
		} catch (JAXBException e) {
			throw new CargaObjetoXMLException (e.getClass().getName() + ": " + e.getLocalizedMessage(), "ConfguracionForms", datosXMLConfForms);
		}		
		
		return configuracionForms;
		
	}

	public ConfiguracionForms crearConfiguracionForms(
			File ficheroXMLConfiguracionForms) throws CargaObjetoXMLException {
		
		if (ficheroXMLConfiguracionForms == null){
			throw new CargaObjetoXMLException ("Se ha especificado un fichero nulo",
					"ConfiguracionForms", "");
		}
		
		ConfiguracionForms configuracionForms = null;
		try {
			configuracionForms = crearConfiguracionForms (new FileInputStream (ficheroXMLConfiguracionForms));		
		} catch (FileNotFoundException e) {
			throw new CargaObjetoXMLException (e.getClass().getName() + ": " + e.getLocalizedMessage(),
					"ConfiguracionForms", ficheroXMLConfiguracionForms.getName());
		}
		
		return configuracionForms;
	}

	public void guardarConfiguracionForms(ConfiguracionForms confForms,
			OutputStream datosXMLConfiguracionForms)
			throws GuardaObjetoXMLException, EstablecerPropiedadException {
		
		// Validar que el output stream no es nulo
		if (datosXMLConfiguracionForms == null)
			throw new es.caib.xml.GuardaObjetoXMLException("Se ha especificado un flujo de salida nulo", 
					"ConfiguracionForms", (OutputStream) null);
		
		// Validar que el justificante no es nulo
		if (confForms == null)
			throw new es.caib.xml.GuardaObjetoXMLException("Se ha especificado un objeto conf forms nulo", 
					"ConfiguracionForms", (OutputStream) null);
		
		// Validar que al justificante tiene todos los datos requeridos
		confForms.comprobarDatosRequeridos();
		
		es.caib.xml.formsconf.modelo.Configuracion confFormsImplInterno = null;
		try {
			confFormsImplInterno = ofConfForms.createConfiguracion();
		} catch (Exception e) {
			throw new es.caib.xml.GuardaObjetoXMLException ("Se ha producido una excepción al crear un objeto Configuracion JAXB", 
					"Configuracion", (OutputStream) null);
		}
				
		cargarDatosHaciaJAXB (confForms, confFormsImplInterno);
		
		//	Hemos obtenido el objeto JAXB equivalente al nodo datos propios, podemos guardar el XML
		try {
			mshConfForms.marshal (confFormsImplInterno, datosXMLConfiguracionForms);
		} catch (JAXBException e) {
			throw new es.caib.xml.GuardaObjetoXMLException("Se ha producido un error al guardar conf forms [" 
					+ e.getClass().getName() + ": " + e.getLocalizedMessage() + "]", 
					"ConfiguracionForms", datosXMLConfiguracionForms);
		}
						
		/*if (confForms instanceof ConfiguracionForms){
			ConfiguracionForms configuracionFormsImpl = (ConfiguracionForms) confForms;
			Object objImplementador = configuracionFormsImpl.getObjetoImplementador();
			
			if (objImplementador instanceof ConfiguracionType){
				ConfiguracionType confFormsImplInterno = (ConfiguracionType) objImplementador;
				
				// Hemos obtenido el objeto JAXB equivalente al nodo datos propios, podemos guardar el XML
				try {
					mshConfForms.marshal (confFormsImplInterno, datosXMLConfiguracionForms);
				} catch (JAXBException e) {
					throw new es.caib.xml.GuardaObjetoXMLException("Se ha producido un error al guardar conf forms [" 
							+ e.getClass().getName() + ": " + e.getLocalizedMessage() + "]", 
							"ConfiguracionForms", datosXMLConfiguracionForms);
				}
			}
			else
				// No conocemos el tipo de implementación interno, no podemos escribir el objeto
				throw new es.caib.xml.GuardaObjetoXMLException("No se conoce el tipo de implementación interno del objeto de configuracion forms [" + objImplementador.getClass().getName() + "]", 
						"ConfiguracionForms", (OutputStream) null);
		}
		else {
			// No conocemos el tipo de implementación, no podemos escribir el objeto
			throw new es.caib.xml.GuardaObjetoXMLException("No se conoce el tipo de implementación del objeto de datos propios [" + confForms.getClass().getName() + "]", 
					"ConfiguracionForms", (OutputStream) null);
		}*/

	}

	public void guardarConfiguracionForms(ConfiguracionForms confForms,
			File ficheroXMLConfiguracionForms) throws GuardaObjetoXMLException,
			EstablecerPropiedadException {
		
		try {
			guardarConfiguracionForms (confForms, new FileOutputStream (ficheroXMLConfiguracionForms));
		} catch (FileNotFoundException e) {
			throw new es.caib.xml.GuardaObjetoXMLException (e.getClass().getName() + ": " + e.getLocalizedMessage(),
					"ConfiguracionForms", ficheroXMLConfiguracionForms.getName());
		}

	}

	public String guardarConfiguracionForms(ConfiguracionForms confForms)
			throws GuardaObjetoXMLException, EstablecerPropiedadException {
		
		ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream(); 
		guardarConfiguracionForms (confForms, byteOutputStream);
		String encoding = getEncoding ();
		
		if ( (encoding != null) && (!encoding.trim().equals(""))){
			try {
				return byteOutputStream.toString(encoding);
			} catch (UnsupportedEncodingException e) {			
				throw new GuardaObjetoXMLException("La codificación " + encoding + " no está soportada", "ConfiguracionForms", (OutputStream) null);
			}
		}
		else
			return byteOutputStream.toString();
	}

	public boolean isIdentacion() {
		try {
			return ((Boolean) mshConfForms.getProperty("jaxb.formatted.output")).booleanValue();
		} catch (PropertyException e) {
			return false;
		}
	}

	public void setIndentacion(boolean indentacion) {
		try {
			mshConfForms.setProperty("jaxb.formatted.output", (indentacion) ? Boolean.TRUE : Boolean.FALSE);
		} catch (PropertyException e) {
			// TODO Manejar excepción de forma más conveniente
			;
		}

	}

	public Datos crearDatos() {		
		return new Datos ();		
	}

	public Propiedad crearPropiedad() {		
		return new Propiedad ();		
	}

	public String getEncoding() {
		try {
			return mshConfForms.getProperty("jaxb.encoding").toString();
		} catch (PropertyException e) {
			//TODO quizá manejar mejor la excepción
			return null;
		}
	}

	public void setEncoding(String encoding) {
		try {
			mshConfForms.setProperty("jaxb.encoding", encoding);
		} catch (PropertyException e) {
			//TODO manejar correctamente la excepción
			;
		}
	}
	
	//	 Métodos para realizar la conversión JAXB -> Jerarquía propia de objetos
	private void cargarDatosDesdeJAXB (es.caib.xml.formsconf.modelo.Configuracion configuracionJAXB, ConfiguracionForms confForms) throws EstablecerPropiedadException{
		if ( (configuracionJAXB != null) && (confForms != null) ){
			
			// Datos
			confForms.setDatos (crearDatos (configuracionJAXB.getDatos()));
			
			// Bloqueo
			List lstBloqueo = confForms.getBloqueo();
			Iterator itXPATH = (configuracionJAXB.getBloqueo() != null) ? configuracionJAXB.getBloqueo().getXpath().iterator() : null;
			
			if (itXPATH != null) {
				while (itXPATH.hasNext()){
					lstBloqueo.add (itXPATH.next().toString());
				}
			}
			
			// Propiedades			
			Iterator itPropsJAXB = (configuracionJAXB.getPropiedades() != null) ? configuracionJAXB.getPropiedades().getPropiedad().iterator() : null;
			HashtableIterable hstiPropiedades = confForms.getPropiedades();
			
			if (itPropsJAXB != null){
				while (itPropsJAXB.hasNext()){
					es.caib.xml.formsconf.modelo.Propiedad propiedad = (es.caib.xml.formsconf.modelo.Propiedad) itPropsJAXB.next();				
					hstiPropiedades.put (propiedad.getNombre(), crearPropiedad (propiedad));
				}
			}			
		}
	}
		
	
	private Propiedad crearPropiedad (es.caib.xml.formsconf.modelo.Propiedad propiedadJAXB) throws EstablecerPropiedadException{
		Propiedad propiedad = null;
		
		if (propiedadJAXB != null){
			propiedad = crearPropiedad ();
			propiedad.setNombre (propiedadJAXB.getNombre());
			propiedad.setValor (propiedadJAXB.getValor());
		}
		
		return propiedad;
	}
	
	private Datos crearDatos (es.caib.xml.formsconf.modelo.Datos datosJAXB) throws EstablecerPropiedadException{
		Datos datos = null;
		
		if (datosJAXB != null){
			datos = crearDatos ();
			datos.setCodigoPerfil (datosJAXB.getCodigoPerfil());
			datos.setIdioma (datosJAXB.getIdioma());
			datos.setLayout (datosJAXB.getLayout());
			datos.setModelo (datosJAXB.getModelo());			
			datos.setNomParamTokenRetorno (datosJAXB.getNomParamTokenRetorno());
			datos.setNomParamXMLDatosFin (datosJAXB.getNomParamXMLDatosFin());
			datos.setNomParamXMLDatosIni (datosJAXB.getNomParamXMLDatosIni());
			datos.setUrlRedireccionCancel (datosJAXB.getUrlRedireccionCancel());
			datos.setUrlRedireccionOK (datosJAXB.getUrlRedireccionOK());
			datos.setUrlSisTraCancel (datosJAXB.getUrlSisTraCancel());
			datos.setUrlSisTraOK (datosJAXB.getUrlSisTraOK());
			datos.setUrlSisTraMantenimientoSesion(datosJAXB.getUrlSisTraMantenimientoSesion());
			datos.setVersion (new Integer (datosJAXB.getVersion()));
			datos.setGuardarSinTerminar("S".equals(datosJAXB.getGuardarSinTerminar()));
			datos.setNomParamXMLSinTerminar(datosJAXB.getNomParamXMLSinTerminar());
		}
		
		return datos;
	}
	
	// Métodos para realizar la conversión Jerarquía propia de objetos -> JAXB  
	private void cargarDatosHaciaJAXB (ConfiguracionForms confForms, es.caib.xml.formsconf.modelo.Configuracion confJAXB) throws EstablecerPropiedadException{
		if ( (confForms != null) && (confJAXB != null) ){
			
			// Bloqueo
			es.caib.xml.formsconf.modelo.Bloqueo bloqueoRaw = new es.caib.xml.formsconf.modelo.Bloqueo ();
			List lstBloqueoRaw = bloqueoRaw.getXpath();
			Iterator itBloqueos = (confForms.getBloqueo() != null) ? confForms.getBloqueo().iterator() : null;
			
			if (itBloqueos != null){
				while (itBloqueos.hasNext()){
					lstBloqueoRaw.add (itBloqueos.next().toString());
				}
			}
			confJAXB.setBloqueo (bloqueoRaw);
			
			// Datos
			confJAXB.setDatos (crearDatosJAXB (confForms.getDatos()));
			
			// Propiedades
			es.caib.xml.formsconf.modelo.Propiedades props = new es.caib.xml.formsconf.modelo.Propiedades ();
			List lstProps = props.getPropiedad();
			Iterator itProps = (confForms.getPropiedades() != null) ? confForms.getPropiedades().iterator() : null;
			
			if (itProps != null){
				while (itProps.hasNext()){
					lstProps.add (crearPropiedadJAXB ( (Propiedad) itProps.next()));
				}
			}
			confJAXB.setPropiedades (props);
		}
	}	
	
	protected es.caib.xml.formsconf.modelo.Datos crearDatosJAXB (Datos datos){
		 es.caib.xml.formsconf.modelo.Datos datosRaw = null;
		
		if (datos != null){
			datosRaw = new  es.caib.xml.formsconf.modelo.Datos ();
			
			// Idioma
			if (datos.getIdioma() != null) datosRaw.setIdioma (datos.getIdioma());
			
			// Modelo
			if (datos.getModelo() != null) datosRaw.setModelo (datos.getModelo());
			
			// Version
			datosRaw.setVersion ((datos.getVersion() != null) ?  datos.getVersion().intValue() : 0);
			
			// CodigoPerfil
			if (datos.getCodigoPerfil() != null) datosRaw.setCodigoPerfil (datos.getCodigoPerfil());
			
			// Layout
			if (datos.getLayout() != null) datosRaw.setLayout (datos.getLayout());
			
			// UrlSisTraOK
			if (datos.getUrlSisTraOK() != null) datosRaw.setUrlSisTraOK (datos.getUrlSisTraOK());
			
			// UrlRedireccionOK
			if (datos.getUrlRedireccionOK() != null) datosRaw.setUrlRedireccionOK (datos.getUrlRedireccionOK());
			
			// UrlSistraCancel
			if (datos.getUrlSisTraCancel() != null) datosRaw.setUrlSisTraCancel (datos.getUrlSisTraCancel());
			
			// UrlRedireccionCancel
			if (datos.getUrlRedireccionCancel() != null) datosRaw.setUrlRedireccionCancel (datos.getUrlRedireccionCancel());
			
			// UrlSisTraMantenimientoSesion
			if (datos.getUrlSisTraMantenimientoSesion() != null) datosRaw.setUrlSisTraMantenimientoSesion(datos.getUrlSisTraMantenimientoSesion());
			
			
			// NomParamXMLDatosIni
			if (datos.getNomParamXMLDatosIni() != null) datosRaw.setNomParamXMLDatosIni (datos.getNomParamXMLDatosIni());
			
			// NomParamXMLDatosFin
			if (datos.getNomParamXMLDatosFin() != null) datosRaw.setNomParamXMLDatosFin (datos.getNomParamXMLDatosFin());
			
			// NomParamTokenRetorno
			if (datos.getNomParamTokenRetorno() != null) datosRaw.setNomParamTokenRetorno (datos.getNomParamTokenRetorno());		
			
			// NomParamXMLSinTerminar
			if (datos.getNomParamXMLSinTerminar() != null) datosRaw.setNomParamXMLSinTerminar( datos.getNomParamXMLSinTerminar());
			
			// Guardar sin terminar
			datosRaw.setGuardarSinTerminar(datos.isGuardarSinTerminar()?"S":"N");
			
			
		}
		
		return datosRaw;
	}
	
	protected  es.caib.xml.formsconf.modelo.Propiedad crearPropiedadJAXB (Propiedad propiedad){
		es.caib.xml.formsconf.modelo.Propiedad propiedadRaw = null;
		
		if (propiedad != null){
			propiedadRaw = new es.caib.xml.formsconf.modelo.Propiedad ();
			
			if (propiedad.getNombre() != null) propiedadRaw.setNombre (propiedad.getNombre());
			if (propiedad.getValor() != null) propiedadRaw.setValor (propiedad.getValor());
		}
		
		return propiedadRaw;
	}

}
