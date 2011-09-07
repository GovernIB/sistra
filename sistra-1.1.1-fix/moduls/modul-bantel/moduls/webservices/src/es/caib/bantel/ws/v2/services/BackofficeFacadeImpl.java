package es.caib.bantel.ws.v2.services;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Set;

import javax.xml.bind.JAXBElement;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

import es.caib.bantel.modelInterfaz.ReferenciaEntradaBTE;
import es.caib.bantel.persistence.delegate.BteDelegate;
import es.caib.bantel.persistence.delegate.DelegateBTEUtil;
import es.caib.bantel.ws.v2.model.DatosDocumentoPresencial;
import es.caib.bantel.ws.v2.model.DatosDocumentoTelematico;
import es.caib.bantel.ws.v2.model.TramiteSubsanacion;
import es.caib.sistra.plugins.PluginFactory;
import es.caib.sistra.plugins.firma.FirmaIntf;
import es.caib.sistra.plugins.firma.PluginFirmaIntf;



@javax.jws.WebService(portName = "BackofficeFacade", serviceName = "BackofficeFacadeService", 
        targetNamespace = "urn:es:caib:bantel:ws:v2:services", 
        endpointInterface = "es.caib.bantel.ws.v2.services.BackofficeFacade")
public class BackofficeFacadeImpl implements BackofficeFacade {
		
	public void establecerResultadoProceso(es.caib.bantel.ws.v2.model.ReferenciaEntrada numeroEntrada, String procesada, String resultadoProcesamiento) throws es.caib.bantel.ws.v2.services.BackofficeFacadeException {
		try{
			BteDelegate bd = DelegateBTEUtil.getBteDelegate();
			ReferenciaEntradaBTE r = new ReferenciaEntradaBTE();
			r.setNumeroEntrada(numeroEntrada.getNumeroEntrada());
			if(numeroEntrada.getClaveAcceso() != null){
				r.setClaveAcceso(numeroEntrada.getClaveAcceso().getValue());
			}
			bd.procesarEntrada(r,procesada,resultadoProcesamiento);
			
		}catch(Exception ex){
			throw new es.caib.bantel.ws.v2.services.BackofficeFacadeException(ex.getMessage(),new es.caib.bantel.ws.v2.model.BackofficeFacadeException());
		}
	}

	public es.caib.bantel.ws.v2.model.TramiteBTE obtenerEntrada(es.caib.bantel.ws.v2.model.ReferenciaEntrada numeroEntrada) throws es.caib.bantel.ws.v2.services.BackofficeFacadeException {
		try{
			BteDelegate bd = DelegateBTEUtil.getBteDelegate();
			ReferenciaEntradaBTE r = new ReferenciaEntradaBTE();
			r.setNumeroEntrada(numeroEntrada.getNumeroEntrada());
			if(numeroEntrada.getClaveAcceso() != null){
				r.setClaveAcceso(numeroEntrada.getClaveAcceso().getValue());
			}
			es.caib.bantel.modelInterfaz.TramiteBTE tramBte = bd.obtenerEntrada(r);
			return tramiteBTEIntfToTramiteBTEWS(tramBte);
		}catch(Exception ex){
			throw new es.caib.bantel.ws.v2.services.BackofficeFacadeException(ex.getMessage(),new es.caib.bantel.ws.v2.model.BackofficeFacadeException());
		}
	}

	public es.caib.bantel.ws.v2.model.ReferenciasEntrada obtenerNumerosEntradas(String identificadorTramite, String procesada, XMLGregorianCalendar desde, XMLGregorianCalendar hasta) throws es.caib.bantel.ws.v2.services.BackofficeFacadeException {
		try{
			Date desdeDate=null;
			Date hastaDate=null;
			BteDelegate bd = DelegateBTEUtil.getBteDelegate();
			if(desde != null){
				desdeDate = desde.toGregorianCalendar().getTime();
			}if(hasta != null){
				hastaDate = hasta.toGregorianCalendar().getTime();
			}
			ReferenciaEntradaBTE[] entradas = bd.obtenerReferenciasEntradas(identificadorTramite,procesada,desdeDate, hastaDate);
			return referenciasEntradaIntfToReferenciasEntradaWS(entradas);
		}catch(Exception ex){
			throw new es.caib.bantel.ws.v2.services.BackofficeFacadeException(ex.getMessage(),new es.caib.bantel.ws.v2.model.BackofficeFacadeException());
		}
	}

	

	//	 --------------------------------------------------------------
	//		FUNCIONES AUXILIARES
	// --------------------------------------------------------------
	
	
	
	private es.caib.bantel.ws.v2.model.TramiteBTE tramiteBTEIntfToTramiteBTEWS(es.caib.bantel.modelInterfaz.TramiteBTE tramBte) throws Exception{
		es.caib.bantel.ws.v2.model.TramiteBTE tramWS = new es.caib.bantel.ws.v2.model.TramiteBTE();
		if(tramBte != null){
			if(tramBte.getAvisoEmail()!=null){
				tramWS.setAvisoEmail(new JAXBElement<String>(new QName("avisoEmail"),String.class,tramBte.getAvisoEmail()));
			}
			if(tramBte.getAvisoSMS()!=null){
				tramWS.setAvisoSMS(new JAXBElement<String>(new QName("avisoSMS"),String.class,tramBte.getAvisoSMS()));
			}
			tramWS.setClaveReferenciaRDSAsiento(tramBte.getClaveReferenciaRDSAsiento());
			tramWS.setClaveReferenciaRDSJustificante(tramBte.getClaveReferenciaRDSJustificante());
			tramWS.setCodigoEntrada(tramBte.getCodigoEntrada());
			tramWS.setCodigoReferenciaRDSAsiento(tramBte.getCodigoReferenciaRDSAsiento());
			tramWS.setCodigoReferenciaRDSJustificante(tramBte.getCodigoReferenciaRDSJustificante());
			tramWS.setDescripcionTramite(tramBte.getDescripcionTramite());
			tramWS.setDocumentos(documentosIntfToDocumentosWS(tramBte.getDocumentos()));
			tramWS.setFecha(dateToXmlGregorianCalendar(tramBte.getFecha()));
			if(tramBte.getFechaPreregistro()!=null){
				tramWS.setFechaPreregistro(new JAXBElement<XMLGregorianCalendar>(new QName("fechaPreregistro"),XMLGregorianCalendar.class,dateToXmlGregorianCalendar(tramBte.getFechaPreregistro())));
			}
			tramWS.setFechaRegistro(dateToXmlGregorianCalendar(tramBte.getFechaRegistro()));
			tramWS.setFirmadaDigitalmente(tramBte.isFirmadaDigitalmente());
			if(tramBte.getHabilitarAvisos()!=null){
				tramWS.setHabilitarAvisos(new JAXBElement<String>(new QName("habilitarAvisos"),String.class,tramBte.getHabilitarAvisos()));
			}
			if(tramBte.getHabilitarNotificacionTelematica()!=null){
				tramWS.setHabilitarNotificacionTelematica(new JAXBElement<String>(new QName("habilitarNotificacionTelematica"),String.class,tramBte.getHabilitarNotificacionTelematica()));
			}
			tramWS.setIdentificadorTramite(tramBte.getIdentificadorTramite());
			tramWS.setIdioma(tramBte.getIdioma());
			tramWS.setNivelAutenticacion(tramBte.getNivelAutenticacion()+"");
			tramWS.setNumeroEntrada(tramBte.getNumeroEntrada());
			if(tramBte.getNumeroPreregistro()!=null){
				tramWS.setNumeroPreregistro(new JAXBElement<String>(new QName("numeroPreregistro"),String.class,tramBte.getNumeroPreregistro()));
			}
			tramWS.setNumeroRegistro(tramBte.getNumeroRegistro());
			tramWS.setProcesada(tramBte.getProcesada()+"");
			if(tramBte.getRepresentadoNombre()!=null){
				tramWS.setRepresentadoNombre(new JAXBElement<String>(new QName("representadoNombre"),String.class,tramBte.getRepresentadoNombre()));
			}
			if(tramBte.getRepresentadoNif()!=null){
				tramWS.setRepresentadoNif(new JAXBElement<String>(new QName("representadoNif"),String.class,tramBte.getRepresentadoNif()));
			}
			tramWS.setTipo(tramBte.getTipo()+"");
			if(tramBte.getTipoConfirmacionPreregistro()!=null){
				tramWS.setTipoConfirmacionPreregistro(new JAXBElement<String>(new QName("tipoConfirmacionPreregistro"),String.class,tramBte.getTipoConfirmacionPreregistro()));
			}
			tramWS.setUnidadAdministrativa(tramBte.getUnidadAdministrativa());
			if(tramBte.getUsuarioNif()!=null){
				tramWS.setUsuarioNif(new JAXBElement<String>(new QName("usuarioNif"),String.class,tramBte.getUsuarioNif()));
			}
			if(tramBte.getUsuarioNombre()!=null){
				tramWS.setUsuarioNombre(new JAXBElement<String>(new QName("usuarioNombre"),String.class,tramBte.getUsuarioNombre()));
			}
			if(tramBte.getUsuarioSeycon()!=null){
				tramWS.setUsuarioSeycon(new JAXBElement<String>(new QName("usuarioSeycon"),String.class,tramBte.getUsuarioSeycon()));
			}
			tramWS.setVersionTramite(tramBte.getVersionTramite());
			tramWS.setDelegadoNif(new JAXBElement<String>(new QName("delegadoNif"),String.class,tramBte.getDelegadoNif()));
			tramWS.setDelegadoNombre(new JAXBElement<String>(new QName("delegadoNombre"),String.class,tramBte.getDelegadoNombre()));
			if(tramBte.getSubsanacionExpedienteCodigo() != null && tramBte.getSubsanacionExpedienteUnidadAdministrativa() != null){
				TramiteSubsanacion tramiteSubsanacion =  new TramiteSubsanacion();
				tramiteSubsanacion.setExpedienteCodigo(tramBte.getSubsanacionExpedienteCodigo());
				tramiteSubsanacion.setExpedienteUnidadAdministrativa(tramBte.getSubsanacionExpedienteUnidadAdministrativa().longValue());
				tramWS.setTramiteSubsanacion(new JAXBElement<TramiteSubsanacion>(new QName("tramiteSubsanacion"),TramiteSubsanacion.class,tramiteSubsanacion));				
			}
			if(tramBte.getReferenciaGestorDocumentalAsiento() != null){
				tramWS.setReferenciaGestorDocumentalAsiento(new JAXBElement<String>(new QName("referenciaGestorDocumentalAsiento"),String.class,tramBte.getReferenciaGestorDocumentalAsiento()));
			}
			if(tramBte.getReferenciaGestorDocumentalJustificante() != null){
				tramWS.setReferenciaGestorDocumentalJustificante(new JAXBElement<String>(new QName("referenciaGestorDocumentalJustificante"),String.class,tramBte.getReferenciaGestorDocumentalJustificante()));		
			}
			if(tramBte.getCodigoDocumentoCustodiaAsiento() != null){
				tramWS.setCodigoDocumentoCustodiaAsiento(new JAXBElement<String>(new QName("codigoDocumentoCustodiaAsiento"),String.class,tramBte.getCodigoDocumentoCustodiaAsiento()));
			}
			if(tramBte.getCodigoDocumentoCustodiaJustificante() != null){
				tramWS.setCodigoDocumentoCustodiaJustificante(new JAXBElement<String>(new QName("codigoDocumentoCustodiaJustificante"),String.class,tramBte.getCodigoDocumentoCustodiaJustificante()));
			}
		}
		return tramWS;
	}
	
	private es.caib.bantel.ws.v2.model.ReferenciasEntrada referenciasEntradaIntfToReferenciasEntradaWS(ReferenciaEntradaBTE[] entradas)  throws Exception{
		es.caib.bantel.ws.v2.model.ReferenciasEntrada refsEntWS = new es.caib.bantel.ws.v2.model.ReferenciasEntrada();
		if(entradas != null){
			for(int i=0;i<entradas.length;i++){
				es.caib.bantel.ws.v2.model.ReferenciaEntrada refEntWS = new es.caib.bantel.ws.v2.model.ReferenciaEntrada();
				if(entradas[i].getClaveAcceso() != null){
					refEntWS.setClaveAcceso(new JAXBElement<String>(new QName("claveAcceso"),String.class,entradas[i].getClaveAcceso()));
				}
				refEntWS.setNumeroEntrada(entradas[i].getNumeroEntrada());
				refsEntWS.getReferenciaEntrada().add(refEntWS);
			}
		}
		return refsEntWS;
	}
	
	private es.caib.bantel.ws.v2.model.DocumentosBTE documentosIntfToDocumentosWS(Set documentos) throws Exception{
		es.caib.bantel.ws.v2.model.DocumentosBTE docs = new es.caib.bantel.ws.v2.model.DocumentosBTE();
		boolean presencial = false;
		if(documentos != null){
			for (Iterator it = documentos.iterator();it.hasNext();){    		
				es.caib.bantel.modelInterfaz.DocumentoBTE doc = (es.caib.bantel.modelInterfaz.DocumentoBTE) it.next();
				es.caib.bantel.ws.v2.model.DocumentoBTE docWS = new es.caib.bantel.ws.v2.model.DocumentoBTE();
				docWS.setNombre(doc.getNombre());
				if(doc.getPresentacionPresencial() != null){
					docWS.setPresentacionPresencial(new JAXBElement<DatosDocumentoPresencial>(new QName("presentacionPresencial"),DatosDocumentoPresencial.class,presentacionPresencialIntfToPresentacionPresenciaWS(doc.getPresentacionPresencial())));
					docWS.setNumeroInstancia(doc.getPresentacionPresencial().getNumeroInstancia());
					docWS.setIdentificador(doc.getPresentacionPresencial().getIdentificador());
					presencial = true;
				}
				if(doc.getPresentacionTelematica() != null){
					docWS.setPresentacionTelematica(new JAXBElement<DatosDocumentoTelematico>(new QName("presentacionTelematica"),DatosDocumentoTelematico.class,presentacionTelematicaIntfToPresentacionTelematicaWS(doc.getPresentacionTelematica())));
					if(!presencial){
						docWS.setNumeroInstancia(doc.getPresentacionTelematica().getNumeroInstancia());
						docWS.setIdentificador(doc.getPresentacionTelematica().getIdentificador());
					}
				}
				docs.getDocumento().add(docWS);
			}
		}
		return docs;
	}
	
	private es.caib.bantel.ws.v2.model.DatosDocumentoPresencial presentacionPresencialIntfToPresentacionPresenciaWS(es.caib.bantel.modelInterfaz.DatosDocumentoPresencial datos){
		es.caib.bantel.ws.v2.model.DatosDocumentoPresencial datosWS = new es.caib.bantel.ws.v2.model.DatosDocumentoPresencial();
		if(datos != null){
			datosWS.setCompulsarDocumento(datos.getCompulsarDocumento()+"");
			datosWS.setFirma(datos.getFirma()+"");
			datosWS.setFotocopia(datos.getFotocopia()+"");
			datosWS.setTipoDocumento(datos.getTipoDocumento()+"");
		}
		return datosWS;		
	}
	
	private es.caib.bantel.ws.v2.model.DatosDocumentoTelematico presentacionTelematicaIntfToPresentacionTelematicaWS(es.caib.bantel.modelInterfaz.DatosDocumentoTelematico datos) throws Exception{
		es.caib.bantel.ws.v2.model.DatosDocumentoTelematico datosWS = new es.caib.bantel.ws.v2.model.DatosDocumentoTelematico();
		if(datos != null){
			datosWS.setClaveReferenciaRds(datos.getClaveReferenciaRds());
			datosWS.setCodigoReferenciaRds(datos.getCodigoReferenciaRds());
			datosWS.setContent(datos.getContent());
			datosWS.setEstructurado(datos.isEstructurado());
			datosWS.setExtension(datos.getExtension());
			datosWS.setFirmas(firmasIntfToFirmasWS(datos.getFirmas()));
			datosWS.setNombre(datos.getNombre());
			datosWS.setReferenciaGestorDocumental(new JAXBElement<String>(new QName("referenciaGestorDocumental"),String.class,datos.getReferenciaGestorDocumental()));
			datosWS.setCodigoDocumentoCustodia(new JAXBElement<String>(new QName("codigoDocumentoCustodia"),String.class,datos.getCodigoDocumentoCustodia()));
		}
		return datosWS;		
	}
	
	private es.caib.bantel.ws.v2.model.FirmasWS firmasIntfToFirmasWS(FirmaIntf[] firmas) throws Exception{
		es.caib.bantel.ws.v2.model.FirmasWS firmasWS = new es.caib.bantel.ws.v2.model.FirmasWS();
		if(firmas != null){
			PluginFirmaIntf plgFirma = PluginFactory.getInstance().getPluginFirma();
			for(int i=0;i<firmas.length;i++){
				es.caib.bantel.ws.v2.model.FirmaWS firma = new es.caib.bantel.ws.v2.model.FirmaWS();
				firma.setFirma(plgFirma.parseFirmaToWS(firmas[i]));
				if(firmas[i].getFormatoFirma() != null){
					firma.setFormato(new JAXBElement<String>(new QName("formato"),String.class,firmas[i].getFormatoFirma()));
				}
				firmasWS.getFirmas().add(firma);
			}
		}
		return firmasWS;
	}
	
	private XMLGregorianCalendar dateToXmlGregorianCalendar(Date date) throws Exception{
		try {
			if(date != null){
				GregorianCalendar c = new GregorianCalendar();
				c.setTime(date);
				return DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
			}
		} catch (DatatypeConfigurationException e) {}
		return null;
	}

}