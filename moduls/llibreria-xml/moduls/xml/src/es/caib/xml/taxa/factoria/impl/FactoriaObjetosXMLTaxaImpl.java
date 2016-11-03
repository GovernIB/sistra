package es.caib.xml.taxa.factoria.impl;

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
import es.caib.xml.taxa.factoria.FactoriaObjetosXMLTaxa;
import es.caib.xml.taxa.modelo.CODIPOSTAL;
import es.caib.xml.taxa.modelo.CONCEPTE;
import es.caib.xml.taxa.modelo.DECLARANT;
import es.caib.xml.taxa.modelo.DOMICILI;
import es.caib.xml.taxa.modelo.ESCALA;
import es.caib.xml.taxa.modelo.FAX;
import es.caib.xml.taxa.modelo.IDTAXA;
import es.caib.xml.taxa.modelo.IMPORT;
import es.caib.xml.taxa.modelo.LLETRA;
import es.caib.xml.taxa.modelo.LOCALITAT;
import es.caib.xml.taxa.modelo.NIF;
import es.caib.xml.taxa.modelo.NOM;
import es.caib.xml.taxa.modelo.NOMVIA;
import es.caib.xml.taxa.modelo.NUMERO;
import es.caib.xml.taxa.modelo.ObjectFactory;
import es.caib.xml.taxa.modelo.PIS;
import es.caib.xml.taxa.modelo.PORTA;
import es.caib.xml.taxa.modelo.PROVINCIA;
import es.caib.xml.taxa.modelo.SIGLES;
import es.caib.xml.taxa.modelo.SUBCONCEPTE;
import es.caib.xml.taxa.modelo.TAXA;
import es.caib.xml.taxa.modelo.TELEFON;

public class FactoriaObjetosXMLTaxaImpl implements FactoriaObjetosXMLTaxa {
	private static final String FICHERO_PROPIEDADES_JAXB = "taxa_JAXB.properties"; 
	private static final String PAQUETE_TAXA_PROP = "PAQUETE_MODELO_TAXA_IMPL";	 
	
	private JAXBContext contextoJAXBTaxa;
	private Unmarshaller unmshTaxa;
	private Marshaller mshTaxa;
	private ObjectFactory ofTaxa;
	

	public FactoriaObjetosXMLTaxaImpl() throws InicializacionFactoriaException {
		try {
			
			Properties propsJAXB = obtenerPropiedadesJAXB ();
			
			contextoJAXBTaxa = JAXBContext.newInstance (obtenerPaqueteImplJustificante(propsJAXB));
			unmshTaxa = contextoJAXBTaxa.createUnmarshaller();
		
			
			mshTaxa = contextoJAXBTaxa.createMarshaller();	
			ofTaxa = new ObjectFactory ();
		} catch (Exception e) {
			throw new InicializacionFactoriaException (e.getClass().getName() + " -> " + e.getLocalizedMessage(), 
					"JAXB",e);
		}
	}

	public Taxa crearTaxa() {		
		return new Taxa();
	}
	
	private String obtenerPaqueteImplJustificante(Properties propiedadesJAXB) throws IOException {		
		return propiedadesJAXB.getProperty(PAQUETE_TAXA_PROP);
	}
	

	private Properties obtenerPropiedadesJAXB () throws IOException{
		Properties prop = new Properties ();
		
		prop.load(getClass().getResourceAsStream (FICHERO_PROPIEDADES_JAXB));
		
		return prop;
	}

	public Taxa crearTaxa(InputStream datosXMLTaxa) throws CargaObjetoXMLException {
		Taxa taxa = null;
		
		try {			
			TAXA taxaJAXB = (TAXA) unmshTaxa.unmarshal(datosXMLTaxa);
			taxa = crearTaxa ();
			
			// JAXB -> Objetos propios
			cargarDatosDesdeJAXB (taxaJAXB, taxa);											
		} catch (Exception e) {
			throw new CargaObjetoXMLException (e.getClass().getName() + ": " + e.getLocalizedMessage(), "formulario", datosXMLTaxa);
		}		
		
		return taxa;
	}

	public Taxa crearTaxa(File ficheroXMLTaxa) throws CargaObjetoXMLException {
		if (ficheroXMLTaxa == null){
			throw new CargaObjetoXMLException ("Se ha especificado un fichero nulo",
					"Taxa", "");
		}
		
		Taxa taxa = null;
		try {
			taxa = crearTaxa (new FileInputStream (ficheroXMLTaxa));		
		} catch (FileNotFoundException e) {
			throw new CargaObjetoXMLException (e.getClass().getName() + ": " + e.getLocalizedMessage(),
					"Taxa", ficheroXMLTaxa.getName());
		}
		
		return taxa;
	}

	public void guardarTaxa(Taxa taxa, OutputStream datosXMLTaxa)
	throws GuardaObjetoXMLException, EstablecerPropiedadException 
	{
		// Validar que el output stream no es nulo
		if (datosXMLTaxa == null)
			throw new es.caib.xml.GuardaObjetoXMLException("Se ha especificado un flujo de salida nulo", 
					"Taxa", (OutputStream) null);
		
		// Validar que el justificante no es nulo
		if (taxa == null)
			throw new es.caib.xml.GuardaObjetoXMLException("Se ha especificado un objeto Taxa nulo", 
					"Taxa", (OutputStream) null);
		
		// Si no hay subconcepte, ni concepte ni importe, establecemos un
		// un subconcepte vacÃ­o (por defecto)
		if ( (taxa.getCodiSubconcepte() == null) && (taxa.getSubconcepte() == null) 
				&& (taxa.getCodiImporte() == null) && (taxa.getImporte() == null) 
				&& (taxa.getCodiConcepte() == null) || (taxa.getConcepte() == null))
		{
			
			taxa.setSubconcepte("");
		}
		
		// Validar que al justificante tiene todos los datos requeridos
		taxa.comprobarDatosRequeridos();
		
		// Crear objetos JAXB equivalentes
		TAXA taxaImplInterno = null;
		
		try {
			taxaImplInterno = ofTaxa.createTAXA();
		} catch (Exception e1) {
			throw new es.caib.xml.GuardaObjetoXMLException ("Se ha producido una excepción al crear un objeto JustificantePago JAXB", 
					"Taxa", (OutputStream) null);
		}
				
				
		cargarDatosHaciaJAXB (taxa, taxaImplInterno);
		
		try {
			mshTaxa.marshal (taxaImplInterno, datosXMLTaxa);
		} catch (JAXBException e) {
			throw new es.caib.xml.GuardaObjetoXMLException("Se ha producido un error al guardar la Taxa [" 
					+ e.getClass().getName() + ": " + e.getLocalizedMessage() + "]", 
					"Taxa", datosXMLTaxa);
		}


	}

	public void guardarTaxa(Taxa taxa, File ficheroXMLTaxa)
	throws GuardaObjetoXMLException, EstablecerPropiedadException 
	{
		try {
			guardarTaxa (taxa, new FileOutputStream (ficheroXMLTaxa));
		} catch (FileNotFoundException e) {
			throw new es.caib.xml.GuardaObjetoXMLException (e.getClass().getName() + ": " + e.getLocalizedMessage(),
					"Taxa", ficheroXMLTaxa.getName());
		}

	}

	public String guardarTaxa(Taxa taxa) throws GuardaObjetoXMLException,
	EstablecerPropiedadException 
	{
		ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream(); 
		guardarTaxa (taxa, byteOutputStream);
		String encoding = getEncoding ();
		
		if ( (encoding != null) && (!encoding.trim().equals(""))){
			try {
				return byteOutputStream.toString(encoding);
			} catch (UnsupportedEncodingException e) {			
				throw new GuardaObjetoXMLException("La codificación " + encoding + " no estó soportada", "Justificante", (OutputStream) null);
			}
		}
		else
			return byteOutputStream.toString();
	}

	public boolean isIdentacion() {
		try {
			return ((Boolean) mshTaxa.getProperty("jaxb.formatted.output")).booleanValue();
		} catch (PropertyException e) {
			return false;
		}
	}

	public void setIndentacion(boolean indentacion) {
		try {
			mshTaxa.setProperty("jaxb.formatted.output", (indentacion) ? Boolean.TRUE : Boolean.FALSE);
		} catch (PropertyException e) {
			// TODO Manejar excepción de forma más conveniente
			;
		}
	}

	public Declarant crearDeclarant() {
		return new Declarant();
	}

	public Domicili crearDomicili() {
		return new Domicili ();
	}

	public String getEncoding() {
		try {
			return mshTaxa.getProperty("jaxb.encoding").toString();
		} catch (PropertyException e) {
			//TODO quizá manejar mejor la excepción
			return null;
		}
	}

	public void setEncoding(String encoding) {
		try {
			mshTaxa.setProperty("jaxb.encoding", encoding);
		} catch (PropertyException e) {
			//TODO manejar correctamente la excepción
			;
		}
	}
	
	// Métodos para realizar la conversión JAXB -> Jerarquía propia de objetos
	private void cargarDatosDesdeJAXB (TAXA taxaJAXB, Taxa taxa) throws EstablecerPropiedadException{
		if ( (taxa != null) && (taxaJAXB != null) ){
			taxa.setVersio (taxaJAXB.getVersio());
			taxa.setAccio (taxaJAXB.getAccio());
			taxa.setLocalizador (taxaJAXB.getLOCALIZADOR());
			taxa.setModelo (taxaJAXB.getMODELO());
			
			IDTAXA idtaxaJAXB = taxaJAXB.getIDTAXA(); 			
			if ( idtaxaJAXB != null){
				taxa.setCodiIdtaxa (idtaxaJAXB.getCodi());
				if ( (idtaxaJAXB.getContent() != null) && (idtaxaJAXB.getContent().trim().length() > 0) )
					taxa.setIdtaxa (idtaxaJAXB.getContent());				
			}
			
			SUBCONCEPTE subconcepteJAXB = taxaJAXB.getSUBCONCEPTE();
			if (subconcepteJAXB != null){
				taxa.setCodiSubconcepte (subconcepteJAXB.getCodi());
				if ( subconcepteJAXB.getContent()!=null && (subconcepteJAXB.getContent().trim().length() > 0))
					taxa.setSubconcepte (subconcepteJAXB.getContent());
			}
			
			IMPORT importJAXB = taxaJAXB.getIMPORT();
			if (importJAXB != null){
				taxa.setCodiImporte (importJAXB.getCodi());
				if ( importJAXB.getContent() != null && (importJAXB.getContent().trim().length() > 0) )
					taxa.setImporte (importJAXB.getContent());
			}
			
			CONCEPTE concepteJAXB = taxaJAXB.getCONCEPTE();
			if (concepteJAXB != null){
				taxa.setCodiConcepte (concepteJAXB.getCodi());
				if  ( concepteJAXB.getContent() != null && (concepteJAXB.getContent().trim().length()> 0))
					taxa.setConcepte (concepteJAXB.getContent());
			}
			
			taxa.setDeclarant (crearDeclarant (taxaJAXB.getDECLARANT()));							
		}
	}
	
	private Declarant crearDeclarant (DECLARANT declarantJAXB) throws EstablecerPropiedadException{
		Declarant declarant = null;
		
		if (declarantJAXB != null){
			declarant = crearDeclarant ();
			
			NIF nifJAXB = declarantJAXB.getNIF();
			if (nifJAXB != null){
				declarant.setCodiNIF (nifJAXB.getCodi());
				if ( nifJAXB.getContent() != null && (nifJAXB.getContent().trim().length() > 0)) 
					declarant.setNIF (nifJAXB.getContent());				
			}
			
			NOM nomJAXB = declarantJAXB.getNOM();
			if (nomJAXB != null){
				declarant.setCodiNom (nomJAXB.getCodi());
				if ( nomJAXB.getContent() != null && (nomJAXB.getContent().trim().length() > 0)) 
					declarant.setNom (nomJAXB.getContent());
			}
			
			TELEFON telefonJAXB = declarantJAXB.getTELEFON();
			if (telefonJAXB != null){
				declarant.setCodiTelefon (telefonJAXB.getCodi());
				if (telefonJAXB.getContent() != null && (telefonJAXB.getContent().trim().length() > 0))
					declarant.setTelefon (telefonJAXB.getContent());
			}
			
			FAX faxJAXB = declarantJAXB.getFAX();
			if (faxJAXB != null){
				declarant.setCodiFAX (faxJAXB.getCodi());
				if ( faxJAXB.getContent() != null && (faxJAXB.getContent().trim().length() > 0)) 
					declarant.setFAX(faxJAXB.getContent());
			}
			
			LOCALITAT localitatJAXB = declarantJAXB.getLOCALITAT();
			if (localitatJAXB != null){
				declarant.setCodiLocalitat (localitatJAXB.getCodi());
				if ( localitatJAXB.getContent() != null && (localitatJAXB.getContent().trim().length() > 0) ) 
					declarant.setLocalitat (localitatJAXB.getContent());
			}
			
			PROVINCIA provinciaJAXB = declarantJAXB.getPROVINCIA();
			if (provinciaJAXB != null){
				declarant.setCodiProvincia (provinciaJAXB.getCodi());
				if (provinciaJAXB.getContent() != null && (provinciaJAXB.getContent().trim().length() > 0) )
					declarant.setProvincia (provinciaJAXB.getContent());
			}
			
			CODIPOSTAL codiPostalJAXB = declarantJAXB.getCODIPOSTAL();
			if (codiPostalJAXB != null){
				declarant.setCodiCodiPostal (codiPostalJAXB.getCodi());
				if ( codiPostalJAXB.getContent() != null && (codiPostalJAXB.getContent().trim().length() > 0) )
					declarant.setCodiPostal (codiPostalJAXB.getContent());
			}
			
			declarant.setDomicili (crearDomicili (declarantJAXB.getDOMICILI()));
		}
		
		return declarant;
	}
	
	private Domicili crearDomicili (DOMICILI domiciliJAXB) throws EstablecerPropiedadException{
		Domicili domicili = null;
		
		if (domiciliJAXB != null){
			domicili = crearDomicili ();
			
			// SIGLES
			SIGLES siglesJAXB = domiciliJAXB.getSIGLES();  
			if ( siglesJAXB != null){
				domicili.setCodiSigles (siglesJAXB.getCodi());
				if ( siglesJAXB.getContent() != null && (siglesJAXB.getContent().trim().length() > 0) )
					domicili.setSigles (siglesJAXB.getContent().trim());
			}
			
			// NOMVIA
			NOMVIA nomviaJAXB = domiciliJAXB.getNOMVIA();
			if (nomviaJAXB != null){
				domicili.setCodiNomVia (nomviaJAXB.getCodi());
				if ( nomviaJAXB.getContent() != null && (nomviaJAXB.getContent().trim().length() > 0) )
					domicili.setNomVia (nomviaJAXB.getContent().trim());
			}
			
			// NUMERO
			NUMERO numeroJAXB = domiciliJAXB.getNUMERO();
			if (numeroJAXB != null){
				domicili.setCodiNumero (numeroJAXB.getCodi());
				if ( numeroJAXB.getContent() != null && (numeroJAXB.getContent().trim().length() > 0))
						domicili.setNumero (numeroJAXB.getContent().trim());
			}
			
			// LLETRA
			LLETRA lletraJAXB = domiciliJAXB.getLLETRA();
			if ( lletraJAXB != null ){
				domicili.setCodiLletra (lletraJAXB.getCodi());
				if ( lletraJAXB.getContent() != null && (lletraJAXB.getContent().trim().length() > 0))
					domicili.setLletra (lletraJAXB.getContent().trim());
			}
			
			// ESCALA
			ESCALA escalaJAXB = domiciliJAXB.getESCALA();
			if (escalaJAXB != null){
				domicili.setCodiEscala (escalaJAXB.getCodi());
				if ( escalaJAXB.getContent() != null && (escalaJAXB.getContent().trim().length() > 0))					
					domicili.setEscala (escalaJAXB.getContent().trim());
			}
			
			// PIS
			PIS pisJAXB = domiciliJAXB.getPIS();
			if (pisJAXB != null){
				domicili.setCodiPis (pisJAXB.getCodi());
				if ( pisJAXB.getContent() != null && (pisJAXB.getContent().trim().length() > 0))
					domicili.setPis (pisJAXB.getContent().trim());
			}
			
			// PORTA
			PORTA portaJAXB = domiciliJAXB.getPORTA();
			if (portaJAXB != null){
				domicili.setCodiPorta (portaJAXB.getCodi());
				if ( portaJAXB.getContent() != null && (portaJAXB.getContent().trim().length() > 0))
					domicili.setPorta (portaJAXB.getContent().trim());
			}
		}
		
		return domicili;
	}
	
	// Métodos para realizar la conversión Jerarquía propia de objetos -> JAXB 
	private void cargarDatosHaciaJAXB (Taxa taxa, TAXA taxaJAXB) throws EstablecerPropiedadException{
		if ( (taxa != null) && (taxaJAXB != null) ){
			taxaJAXB.setVersio (taxa.getVersio());
			taxaJAXB.setAccio (taxa.getAccio());
			taxaJAXB.setLOCALIZADOR (taxa.getLocalizador());
			taxaJAXB.setMODELO (taxa.getModelo());
			
			//IDTAXA
			IDTAXA idtaxaRaw = new IDTAXA ();
			idtaxaRaw.setCodi (taxa.getCodiIdtaxa());
			if (taxa.getIdtaxa() != null)
				idtaxaRaw.setContent(taxa.getIdtaxa());
			
			taxaJAXB.setIDTAXA (idtaxaRaw);
									
			
			//SUBCONCEPTE
			if ( (taxa.getCodiSubconcepte() != null) || (taxa.getSubconcepte() != null) ){
				SUBCONCEPTE subconcepteRaw = new SUBCONCEPTE ();
				subconcepteRaw.setCodi (taxa.getCodiSubconcepte());
				if (taxa.getSubconcepte() != null)
					subconcepteRaw.setContent(taxa.getSubconcepte());
				
				taxaJAXB.setSUBCONCEPTE (subconcepteRaw);
			}
			
			//IMPORT
			if ( (taxa.getCodiImporte() != null) || (taxa.getImporte() != null) ){
				IMPORT importeRaw = new IMPORT ();
				importeRaw.setCodi (taxa.getCodiImporte());
				if (taxa.getImporte() != null)					
					importeRaw.setContent(taxa.getImporte());
				
				taxaJAXB.setIMPORT (importeRaw);
			}
			
			//CONCEPTE
			if ( (taxa.getCodiConcepte() != null) || (taxa.getConcepte() != null) ){
				CONCEPTE concepteRaw = new CONCEPTE ();
				concepteRaw.setCodi (taxa.getCodiConcepte());
				if (taxa.getConcepte() != null)
					concepteRaw.setContent(taxa.getConcepte());
				
				taxaJAXB.setCONCEPTE (concepteRaw);
			}
			
			//DECLARANT
			taxaJAXB.setDECLARANT (crearDeclarantJAXB (taxa.getDeclarant()));
			
		}
	}
	
	private DOMICILI crearDomiciliJAXB (Domicili domicili){
		DOMICILI domiciliRaw = null;
		
		if (domicili != null){
			domiciliRaw = new DOMICILI ();
			
			// Sigles
			SIGLES siglesRaw = new SIGLES ();
			siglesRaw.setCodi (domicili.getCodiSigles());
			if ((domicili.getSigles() != null) && (!domicili.getSigles().trim().equals("")))
				siglesRaw.setContent(domicili.getSigles().trim());
			domiciliRaw.setSIGLES (siglesRaw);
			
			// NomVia
			NOMVIA nomViaRaw = new NOMVIA ();
			nomViaRaw.setCodi (domicili.getCodiNomVia());
			if ( (domicili.getNomVia() != null) && (!domicili.getNomVia().trim().equals("")))
				nomViaRaw.setContent(domicili.getNomVia().trim());
			domiciliRaw.setNOMVIA (nomViaRaw);
			
			// Numero
			NUMERO numeroRaw = new NUMERO ();
			numeroRaw.setCodi (domicili.getCodiNumero());
			if ( (domicili.getNumero() != null) && (!domicili.getNumero().trim().equals("")) )
				numeroRaw.setContent(domicili.getNumero().trim());
			domiciliRaw.setNUMERO (numeroRaw);
			
			// Lletra
			LLETRA lletraRaw = new LLETRA ();
			lletraRaw.setCodi (domicili.getCodiLletra());
			if (domicili.getLletra() != null)
				lletraRaw.setContent(domicili.getLletra());
			
			domiciliRaw.setLLETRA (lletraRaw);
			
			// Escala
			ESCALA escalaRaw = new ESCALA ();
			escalaRaw.setCodi (domicili.getCodiEscala());
			if ( (domicili.getEscala() != null) && (!domicili.getEscala().trim().equals("")) )
				escalaRaw.setContent(domicili.getEscala().trim());
			
			domiciliRaw.setESCALA (escalaRaw);
			
			// Pis
			PIS pisRaw = new PIS ();
			pisRaw.setCodi (domicili.getCodiPis());
			if ( (domicili.getPis() != null) && (!domicili.getPis().trim().equals("")) )
				pisRaw.setContent(domicili.getPis().trim());
				
			domiciliRaw.setPIS (pisRaw);
			
			// Porta
			PORTA portaRaw = new PORTA ();
			portaRaw.setCodi (domicili.getCodiPorta());
			if ( (domicili.getPorta() != null) && (!domicili.getPorta().trim().equals("")))
				portaRaw.setContent(domicili.getPorta().trim());
			
			domiciliRaw.setPORTA (portaRaw);			
		}
		
		return domiciliRaw;
	}
	
	private DECLARANT crearDeclarantJAXB (Declarant declarant){
		DECLARANT declarantRaw = null;
		
		if (declarant != null){
			declarantRaw = new DECLARANT ();
			
			// NIF
			NIF nifRaw = new NIF ();
			nifRaw.setCodi (declarant.getCodiNIF());
			if (declarant.getNIF() != null)
				nifRaw.setContent(declarant.getNIF());
			
			declarantRaw.setNIF (nifRaw);
			
			// NOM
			NOM nomRaw = new NOM ();
			nomRaw.setCodi (declarant.getCodiNom());
			if (declarant.getNom() != null)
				nomRaw.setContent(declarant.getNom());
				
			declarantRaw.setNOM (nomRaw);
			
			// TELEFON
			TELEFON telefonRaw = new TELEFON ();
			telefonRaw.setCodi (declarant.getCodiTelefon());
			if (declarant.getTelefon() != null)
				telefonRaw.setContent(declarant.getTelefon());
			
			declarantRaw.setTELEFON (telefonRaw);
			
			// FAX
			FAX faxRaw = new FAX ();
			faxRaw.setCodi (declarant.getCodiFAX());
			if (declarant.getFAX() != null)
				faxRaw.setContent(declarant.getFAX());
			
			declarantRaw.setFAX (faxRaw);
			
			// LOCALITAT
			LOCALITAT localitatRaw = new LOCALITAT ();
			localitatRaw.setCodi (declarant.getCodiLocalitat ());
			if (declarant.getLocalitat() != null)
				localitatRaw.setContent(declarant.getLocalitat());
			
			declarantRaw.setLOCALITAT (localitatRaw);
			
			// PROVINCIA
			PROVINCIA provinciaRaw = new PROVINCIA ();
			provinciaRaw.setCodi (declarant.getCodiProvincia());
			if (declarant.getProvincia() != null)
				provinciaRaw.setContent(declarant.getProvincia());
			
			declarantRaw.setPROVINCIA (provinciaRaw);
			
			// CODI_POSTAL
			CODIPOSTAL codipostalRaw = new CODIPOSTAL ();
			codipostalRaw.setCodi (declarant.getCodiCodiPostal());
			if (declarant.getCodiPostal() != null)
				codipostalRaw.setContent(declarant.getCodiPostal());
			
			declarantRaw.setCODIPOSTAL (codipostalRaw);
			
			// DOMICILI
			declarantRaw.setDOMICILI (crearDomiciliJAXB (declarant.getDomicili()));					
		}
		
		return declarantRaw;
	}
	
	
}
