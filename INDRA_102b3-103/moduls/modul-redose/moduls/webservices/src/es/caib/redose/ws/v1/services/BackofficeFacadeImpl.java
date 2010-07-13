package es.caib.redose.ws.v1.services;

import java.util.GregorianCalendar;

import javax.xml.bind.JAXBElement;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

import org.apache.commons.lang.StringUtils;

import es.caib.redose.persistence.delegate.DelegateRDSUtil;
import es.caib.redose.persistence.delegate.RdsDelegate;
import es.caib.redose.ws.v1.model.BackofficeFacadeException;
import es.caib.redose.ws.v1.model.DocumentoRDS;
import es.caib.redose.ws.v1.model.FirmaWS;
import es.caib.redose.ws.v1.model.FirmasWS;
import es.caib.redose.ws.v1.model.ReferenciaRDS;
import es.caib.redose.ws.v1.model.TransformacionRDS;
import es.caib.sistra.plugins.PluginFactory;
import es.caib.sistra.plugins.firma.FirmaIntf;
import es.caib.sistra.plugins.firma.PluginFirmaIntf;


@javax.jws.WebService(portName = "BackofficeFacade", serviceName = "BackofficeFacadeService", 
        targetNamespace = "urn:es:caib:redose:ws:v1:services", 
        endpointInterface = "es.caib.redose.ws.v1.services.BackofficeFacade")
public class BackofficeFacadeImpl implements BackofficeFacade {

	
	public DocumentoRDS consultarDocumento(ReferenciaRDS referencia) throws es.caib.redose.ws.v1.services.BackofficeFacadeException {		
		try{
			es.caib.redose.modelInterfaz.ReferenciaRDS refInter = referenciaWSToReferenciaIntf(referencia);
			RdsDelegate rdsD = DelegateRDSUtil.getRdsDelegate();
			es.caib.redose.modelInterfaz.DocumentoRDS doc = rdsD.consultarDocumento(refInter);
			DocumentoRDS docWS = documentoIntToDocumentoWS(doc);
			return docWS;
		}catch( Exception exc ){
			exc.printStackTrace();
		     throw new es.caib.redose.ws.v1.services.BackofficeFacadeException(exc.getMessage(),new BackofficeFacadeException());
		}
	}

	public DocumentoRDS consultarDocumentoFormateado(ReferenciaRDS referencia, String tipoPlantilla, String idioma) throws es.caib.redose.ws.v1.services.BackofficeFacadeException {
		try{
			es.caib.redose.modelInterfaz.ReferenciaRDS refInter = referenciaWSToReferenciaIntf(referencia);
			RdsDelegate rdsD = DelegateRDSUtil.getRdsDelegate();
			es.caib.redose.modelInterfaz.DocumentoRDS doc = rdsD.consultarDocumentoFormateado(refInter,(StringUtils.isEmpty(tipoPlantilla))?null:tipoPlantilla,(StringUtils.isEmpty(idioma))?null:idioma);
			DocumentoRDS docWS = documentoIntToDocumentoWS(doc);
			return docWS;
		}catch( Exception exc ){
			exc.printStackTrace();
		     throw new es.caib.redose.ws.v1.services.BackofficeFacadeException(exc.getMessage(),new BackofficeFacadeException());
		}
	}
	
	public ReferenciaRDS insertarDocumento(DocumentoRDS documento) throws es.caib.redose.ws.v1.services.BackofficeFacadeException {
		try{
			es.caib.redose.modelInterfaz.DocumentoRDS docInter = documentoWSToDocumentoInt(documento);
			RdsDelegate rdsD = DelegateRDSUtil.getRdsDelegate();
			es.caib.redose.modelInterfaz.ReferenciaRDS refInt = rdsD.insertarDocumento(docInter);
			ReferenciaRDS refWS = referenciaIntfToReferenciaWS(refInt);
			return refWS;
		}catch( Exception exc ){
			exc.printStackTrace();
		     throw new es.caib.redose.ws.v1.services.BackofficeFacadeException(exc.getMessage(),new BackofficeFacadeException());
		}
	}

	public ReferenciaRDS insertarDocumentoConTransformacion(DocumentoRDS documento, TransformacionRDS transformacion) throws es.caib.redose.ws.v1.services.BackofficeFacadeException {
		try{
			es.caib.redose.modelInterfaz.DocumentoRDS docInter = documentoWSToDocumentoInt(documento);
			es.caib.redose.modelInterfaz.TransformacionRDS transInter = transformacionWSToTransformacionInt(transformacion);
			RdsDelegate rdsD = DelegateRDSUtil.getRdsDelegate();
			es.caib.redose.modelInterfaz.ReferenciaRDS refInt = rdsD.insertarDocumento(docInter, transInter);
			ReferenciaRDS refWS = referenciaIntfToReferenciaWS(refInt);
			return refWS;
		}catch( Exception exc ){
			exc.printStackTrace();
		     throw new es.caib.redose.ws.v1.services.BackofficeFacadeException(exc.getMessage(),new BackofficeFacadeException());
		}
	}

	//	 --------------------------------------------------------------
	//		FUNCIONES AUXILIARES
	// --------------------------------------------------------------
	
	
	private	es.caib.redose.modelInterfaz.ReferenciaRDS referenciaWSToReferenciaIntf(ReferenciaRDS refRds){
		es.caib.redose.modelInterfaz.ReferenciaRDS refInt = new es.caib.redose.modelInterfaz.ReferenciaRDS();
		if(refRds != null){
			refInt.setClave(refRds.getClave());
			refInt.setCodigo(refRds.getCodigo());
		}
		return refInt;
	}
	
	private DocumentoRDS documentoIntToDocumentoWS(es.caib.redose.modelInterfaz.DocumentoRDS doc) throws Exception{
		DocumentoRDS docWS = new DocumentoRDS();
		if(doc != null){
			docWS.setDatosFichero(doc.getDatosFichero());
			docWS.setEstructurado(new JAXBElement<Boolean>(new QName("estructurado"),Boolean.class,doc.isEstructurado()));
			docWS.setExtensionFichero(doc.getExtensionFichero());
			if(doc.getFechaRDS() != null){
				GregorianCalendar c = new GregorianCalendar();
				c.setTime(doc.getFechaRDS());
				docWS.setFechaRDS(new JAXBElement<XMLGregorianCalendar>(new QName("fechaRDS"),XMLGregorianCalendar.class,DatatypeFactory.newInstance().newXMLGregorianCalendar(c)));
			}
			if(doc.getFirmas() != null){
				docWS.setFirmas(new JAXBElement<FirmasWS>(new QName("firmas"),FirmasWS.class,firmasIntfToFirmasWS(doc.getFirmas())));
			}
			if(doc.getHashFichero()!=null){
				docWS.setHashFichero(new JAXBElement<String>(new QName("hashFichero"),String.class,doc.getHashFichero()));
			}
			docWS.setModelo(doc.getModelo());
			if(doc.getNif() != null){
				docWS.setNif(new JAXBElement<String>(new QName("nif"),String.class,doc.getNif()));
			}
			docWS.setNombreFichero(doc.getNombreFichero());
			if(doc.getPlantilla() != null){
				docWS.setPlantilla(new JAXBElement<String>(new QName("plantilla"),String.class,doc.getPlantilla()));
			}
			if(doc.getReferenciaRDS() != null){
				docWS.setReferenciaRDS(new JAXBElement<ReferenciaRDS>(new QName("referenciaRDS"),ReferenciaRDS.class,referenciaIntfToReferenciaWS(doc.getReferenciaRDS())));
			}
			docWS.setTitulo(doc.getTitulo());
			docWS.setUnidadAdministrativa(doc.getUnidadAdministrativa());
			if(doc.getUrlVerificacion() != null){
				docWS.setUrlVerificacion(new JAXBElement<String>(new QName("urlVerificacion"),String.class,doc.getUrlVerificacion()));
			}
			if(doc.getUsuarioSeycon() != null){
				docWS.setUsuarioSeycon(new JAXBElement<String>(new QName("usuarioSeycon"),String.class,doc.getUsuarioSeycon()));
			}
			docWS.setVersion(doc.getVersion());
		}
		return docWS;
	}
	
	private	ReferenciaRDS referenciaIntfToReferenciaWS(es.caib.redose.modelInterfaz.ReferenciaRDS refRds){
		ReferenciaRDS refWS = new ReferenciaRDS();
		if(refRds != null){
			refWS.setClave(refRds.getClave());
			refWS.setCodigo(refRds.getCodigo());
		}
		return refWS;
	}
	
	private es.caib.redose.modelInterfaz.DocumentoRDS documentoWSToDocumentoInt(DocumentoRDS doc) throws Exception{
		es.caib.redose.modelInterfaz.DocumentoRDS docInt = new es.caib.redose.modelInterfaz.DocumentoRDS();
		if(doc != null){
			docInt.setDatosFichero(doc.getDatosFichero());
			if(doc.getEstructurado() != null){
				docInt.setEstructurado(doc.getEstructurado().getValue());
			}
			docInt.setExtensionFichero(doc.getExtensionFichero());
			if(doc.getFechaRDS() != null){
				docInt.setFechaRDS(doc.getFechaRDS().getValue().toGregorianCalendar().getTime());
			}
			if(doc.getFirmas() != null){
				docInt.setFirmas(firmasWSToFirmasIntf(doc.getFirmas().getValue()));
			}
			if(doc.getHashFichero() != null ){
				docInt.setHashFichero(doc.getHashFichero().getValue());
			}
			docInt.setModelo(doc.getModelo());
			if(doc.getNif() != null){
				docInt.setNif(doc.getNif().getValue());
			}
			docInt.setNombreFichero(doc.getNombreFichero());
			if(doc.getPlantilla() != null){
				docInt.setPlantilla(doc.getPlantilla().getValue());
			}
			if(doc.getReferenciaRDS() != null){
				docInt.setReferenciaRDS(referenciaWSToReferenciaIntf(doc.getReferenciaRDS().getValue()));	
			}
			docInt.setTitulo(doc.getTitulo());
			docInt.setUnidadAdministrativa(doc.getUnidadAdministrativa());
			if(doc.getUrlVerificacion() != null){
				docInt.setUrlVerificacion(doc.getUrlVerificacion().getValue());
			}
			if(doc.getUsuarioSeycon() != null){
				docInt.setUsuarioSeycon(doc.getUsuarioSeycon().getValue());
			}
			docInt.setVersion(doc.getVersion());
		}
		return docInt;
	}

	private FirmasWS firmasIntfToFirmasWS(FirmaIntf[] firmas) throws Exception{
		PluginFirmaIntf plgFirma = PluginFactory.getInstance().getPluginFirma();
		FirmasWS firmasWS = new FirmasWS();
		if(firmas != null){
			for(int i=0;i<firmas.length;i++){
				FirmaWS firma = new FirmaWS();
				firma.setFirma(plgFirma.parseFirmaToBytes(firmas[i]));
				if(firmas[i].getFormatoFirma() != null){
					firma.setFormato(new JAXBElement<String>(new QName("formatoFirma"),String.class,firmas[i].getFormatoFirma()));
				}
				firmasWS.getFirmas().add(firma);
			}
		}
		return firmasWS;
	}
	
	private FirmaIntf[] firmasWSToFirmasIntf(FirmasWS firmas) throws Exception{
		FirmaIntf[] firmasIntf = null;
		if(firmas != null){
			firmasIntf = new FirmaIntf[firmas.getFirmas().size()];
			PluginFirmaIntf plgFirma = PluginFactory.getInstance().getPluginFirma();
			for(int i=0;i<firmas.getFirmas().size();i++){
				if(firmas.getFirmas().get(i).getFormato() != null){
					firmasIntf[i] = plgFirma.parseFirmaFromBytes(firmas.getFirmas().get(i).getFirma(),firmas.getFirmas().get(i).getFormato().getValue());
				}
			}
		}
		return firmasIntf;
	}
	
	private es.caib.redose.modelInterfaz.TransformacionRDS transformacionWSToTransformacionInt(TransformacionRDS transformacion){
		es.caib.redose.modelInterfaz.TransformacionRDS transInt = new es.caib.redose.modelInterfaz.TransformacionRDS();
		if(transformacion != null){
			transInt.setBarcodePDF(transformacion.isBarcodePDF());
			transInt.setConvertToPDF(transformacion.isConvertToPDF());
		}
		return transInt;
	}
	
	
}