package es.caib.regtel.ws.v2.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBElement;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.redose.modelInterfaz.DocumentoRDS;
import es.caib.redose.modelInterfaz.ReferenciaRDS;
import es.caib.regtel.model.ResultadoRegistroTelematico;
import es.caib.regtel.model.ws.Aviso;
import es.caib.regtel.model.ws.OficioRemision;
import es.caib.regtel.model.ws.TramiteSubsanacion;
import es.caib.regtel.persistence.delegate.DelegateUtil;
import es.caib.regtel.persistence.delegate.RegistroTelematicoWsDelegate;
import es.caib.regtel.ws.v2.model.AcuseRecibo;
import es.caib.regtel.ws.v2.model.AnexoItem;
import es.caib.regtel.ws.v2.model.AnexosMap;
import es.caib.regtel.ws.v2.model.DatosAsunto;
import es.caib.regtel.ws.v2.model.DatosExpediente;
import es.caib.regtel.ws.v2.model.DatosInteresado;
import es.caib.regtel.ws.v2.model.DatosNotificacion;
import es.caib.regtel.ws.v2.model.DatosRegistroEntrada;
import es.caib.regtel.ws.v2.model.DatosRegistroSalida;
import es.caib.regtel.ws.v2.model.DatosRepresentado;
import es.caib.regtel.ws.v2.model.Documento;
import es.caib.regtel.ws.v2.model.Documentos;
import es.caib.regtel.ws.v2.model.FirmaWS;
import es.caib.regtel.ws.v2.model.FirmasWS;
import es.caib.regtel.ws.v2.model.OficinaRegistral;
import es.caib.regtel.ws.v2.model.ParametroTramite;
import es.caib.regtel.ws.v2.model.ReferenciaRDSAsientoRegistral;
import es.caib.regtel.ws.v2.model.ResultadoRegistro;
import es.caib.regtel.ws.v2.model.DetalleAvisos;
import es.caib.regtel.ws.v2.model.DetalleAcuseRecibo;
import es.caib.regtel.ws.v2.model.TipoAviso;
import es.caib.regtel.ws.v2.model.TipoConfirmacionAviso;
import es.caib.regtel.ws.v2.model.TipoEstadoNotificacion;
import es.caib.sistra.plugins.PluginFactory;
import es.caib.sistra.plugins.firma.FirmaIntf;
import es.caib.sistra.plugins.firma.PluginFirmaIntf;
import es.caib.util.StringUtil;
import es.caib.zonaper.modelInterfaz.DetalleAviso;


@javax.jws.WebService(portName = "BackofficeFacade", serviceName = "BackofficeFacadeService", 
        targetNamespace = "urn:es:caib:regtel:ws:v2:services", 
        endpointInterface = "es.caib.regtel.ws.v2.services.BackofficeFacade")
public class BackofficeFacadeImpl implements BackofficeFacade {
	
	private static Log log = LogFactory.getLog(BackofficeFacadeImpl.class);
	
	public ReferenciaRDSAsientoRegistral prepararRegistroEntrada(DatosRegistroEntrada entrada, String diasPersistencia) throws BackofficeFacadeException {
		ReferenciaRDSAsientoRegistral raWS = null;
		try{
			RegistroTelematicoWsDelegate delegate = DelegateUtil.getRegistroTelematicoWsDelegate();
			es.caib.regtel.model.ws.DatosRegistroEntrada dEnt = prepararDatosRegistroEntradaIntf(entrada);
			
			int dp = 0;
			if (StringUtils.isNotEmpty(diasPersistencia)) {
				try {
					dp = Integer.parseInt(diasPersistencia);
				} catch (NumberFormatException nfe) {
					throw new Exception("El valor indicado en dias de persistencia no es un entero: " + diasPersistencia);
				}
			}
			
			es.caib.regtel.model.ReferenciaRDSAsientoRegistral ar = delegate.prepararRegistroEntrada(dEnt, dp);
			raWS = referenciaRDSAsientoRegistralIntfToReferenciaRDSAsientoRegistralWS(ar);
		} catch (Exception ex) {
			log.error(ex);
			// ex.printStackTrace();
			throw new es.caib.regtel.ws.v2.services.BackofficeFacadeException(ex.getMessage(), new es.caib.regtel.ws.v2.model.BackofficeFacadeException());
		}
		return raWS;
	}


	public ResultadoRegistro registroEntradaConFirma(ReferenciaRDSAsientoRegistral referenciaRDS, FirmaWS firma) throws BackofficeFacadeException {
		ResultadoRegistro rr = null;
		try {
			RegistroTelematicoWsDelegate delegate = DelegateUtil.getRegistroTelematicoWsDelegate();
			es.caib.regtel.model.ReferenciaRDSAsientoRegistral ar = referenciaRDSAsientoRegistralWSToReferenciaRDSAsientoRegistralIntf(referenciaRDS);
			ResultadoRegistroTelematico res = delegate.registroEntradaConFirma(ar,firmaWSToFirmaIntf(firma));
			rr = resultadoRegistroTelematicoToResultadoRegistroNotificacion(res);
		} catch (Exception ex) {
			log.error(ex);
			// ex.printStackTrace();
			throw new es.caib.regtel.ws.v2.services.BackofficeFacadeException(ex.getMessage(), new es.caib.regtel.ws.v2.model.BackofficeFacadeException());
		}
		return rr;
	}
	
	public ResultadoRegistro registroEntrada(DatosRegistroEntrada entrada) throws BackofficeFacadeException {
		ResultadoRegistro rr = null;
		try {
			RegistroTelematicoWsDelegate delegate = DelegateUtil.getRegistroTelematicoWsDelegate();
			es.caib.regtel.model.ws.DatosRegistroEntrada rEnt = prepararDatosRegistroEntradaIntf(entrada);
			ResultadoRegistroTelematico res = delegate.registroEntrada(rEnt);
			rr = resultadoRegistroTelematicoToResultadoRegistroNotificacion(res);
		} catch (Exception ex) {
			log.error(ex);
			// ex.printStackTrace();
			throw new es.caib.regtel.ws.v2.services.BackofficeFacadeException(ex.getMessage(), new es.caib.regtel.ws.v2.model.BackofficeFacadeException());
		}
		return rr;
	}

	
	public ResultadoRegistro registroSalida(DatosRegistroSalida notificacion) throws BackofficeFacadeException {
		ResultadoRegistro rr = null;
		try {
			RegistroTelematicoWsDelegate delegate = DelegateUtil.getRegistroTelematicoWsDelegate();
			es.caib.regtel.model.ws.DatosRegistroSalida rSal = prepararDatosRegistroSalidaIntf(notificacion);
			ResultadoRegistroTelematico res = delegate.registroSalida(rSal);
			rr = resultadoRegistroTelematicoToResultadoRegistroNotificacion(res);
		} catch (Exception ex) {
			log.error(ex);
			// ex.printStackTrace();
			throw new es.caib.regtel.ws.v2.services.BackofficeFacadeException(ex.getMessage(), new es.caib.regtel.ws.v2.model.BackofficeFacadeException());
		}
		return rr;
	}

	public AcuseRecibo obtenerAcuseRecibo(String numeroRegistro) throws BackofficeFacadeException {
		AcuseRecibo acuse = null;
		try{
			RegistroTelematicoWsDelegate rtd = DelegateUtil.getRegistroTelematicoWsDelegate();
			es.caib.regtel.model.ws.AcuseRecibo acuseIntf = rtd.obtenerAcuseRecibo(numeroRegistro);
			acuse = acuseIntfToAcuseWS(acuseIntf);
			return acuse;
		}catch(Exception ex){
			log.error(ex);
			// ex.printStackTrace();
			throw new es.caib.regtel.ws.v2.services.BackofficeFacadeException(ex.getMessage(), new es.caib.regtel.ws.v2.model.BackofficeFacadeException());
		}
	}
	
	public DetalleAcuseRecibo obtenerDetalleAcuseRecibo(String numeroRegistro) throws BackofficeFacadeException {
		DetalleAcuseRecibo acuse = null;
		try{
			RegistroTelematicoWsDelegate rtd = DelegateUtil.getRegistroTelematicoWsDelegate();
			es.caib.zonaper.modelInterfaz.DetalleAcuseRecibo acuseIntf = rtd.obtenerDetalleAcuseRecibo(numeroRegistro);
			if (acuseIntf == null)  {
				throw new Exception("No existe notificacion: " + numeroRegistro);
			}	
			acuse = detalleAcuseIntfToDetalleAcuseWS(acuseIntf);
			return acuse;
		}catch(Exception ex){
			log.error(ex);
			// ex.printStackTrace();
			throw new es.caib.regtel.ws.v2.services.BackofficeFacadeException(ex.getMessage(), new es.caib.regtel.ws.v2.model.BackofficeFacadeException());
		}
	}

	//	 --------------------------------------------------------------
	//		FUNCIONES AUXILIARES
	// --------------------------------------------------------------


	private DetalleAcuseRecibo detalleAcuseIntfToDetalleAcuseWS(
			es.caib.zonaper.modelInterfaz.DetalleAcuseRecibo acuse) throws Exception {
		DetalleAcuseRecibo da = new DetalleAcuseRecibo();
		
		// Estado notificacion
		if (acuse.getEstado().equals(es.caib.zonaper.modelInterfaz.DetalleAcuseRecibo.ESTADO_ENTREGADA)) {
			da.setEstado(TipoEstadoNotificacion.ENTREGADA);
		} else if (acuse.getEstado().equals(es.caib.zonaper.modelInterfaz.DetalleAcuseRecibo.ESTADO_RECHAZADA)) {
			da.setEstado(TipoEstadoNotificacion.RECHAZADA);
		} else {
			da.setEstado(TipoEstadoNotificacion.PENDIENTE);
		}
		
		// Fecha acuse recibo
		if (acuse.getFechaAcuseRecibo() != null) {
			GregorianCalendar c = new GregorianCalendar();
			c.setTime(acuse.getFechaAcuseRecibo());
			XMLGregorianCalendar fcAcuse = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
			JAXBElement<XMLGregorianCalendar> fechaAcuse = new JAXBElement<XMLGregorianCalendar>(
						new QName("fechaAcuseRecibo"),
						XMLGregorianCalendar.class,
						fcAcuse
						);
			da.setFechaAcuseRecibo(fechaAcuse);
		}
		
		// Fichero acuse recibo
		if (acuse.getCodigoRdsAcuseRecibo() != null && acuse.getCodigoRdsAcuseRecibo().longValue() > 0) {
			es.caib.regtel.ws.v2.model.ReferenciaRDS refRDS = new es.caib.regtel.ws.v2.model.ReferenciaRDS();
			refRDS.setCodigo(acuse.getCodigoRdsAcuseRecibo());
			refRDS.setClave(acuse.getClaveRdsAcuseRecibo());
			JAXBElement<es.caib.regtel.ws.v2.model.ReferenciaRDS> value = new JAXBElement<es.caib.regtel.ws.v2.model.ReferenciaRDS>(
					new QName("ficheroAcuseRecibo"),
					es.caib.regtel.ws.v2.model.ReferenciaRDS.class,
					refRDS
					);
			da.setFicheroAcuseRecibo(value);
		}

		// Avisos
		if (acuse.getAvisos() != null && acuse.getAvisos().size() > 0) {
			DetalleAvisos avisos = new DetalleAvisos();
			for (Iterator it = acuse.getAvisos().iterator(); it.hasNext();) {
				DetalleAviso a = (DetalleAviso) it.next();
				es.caib.regtel.ws.v2.model.DetalleAviso aviso = new es.caib.regtel.ws.v2.model.DetalleAviso();
				if (a.getTipo().equals(DetalleAviso.TIPO_EMAIL)) {
					aviso.setTipo(TipoAviso.EMAIL);
				} else {
					aviso.setTipo(TipoAviso.SMS);	
				}
				aviso.setDestinatario(a.getDestinatario());
				aviso.setEnviado(a.isEnviado());
				if (a.getFechaEnvio() != null) {
					GregorianCalendar c = new GregorianCalendar();
					c.setTime(a.getFechaEnvio());
					XMLGregorianCalendar fcAviso = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
					JAXBElement<XMLGregorianCalendar> fechaAviso = new JAXBElement<XMLGregorianCalendar>(
							new QName("fechaEnvio"),
							XMLGregorianCalendar.class,
							fcAviso
							);
					aviso.setFechaEnvio(fechaAviso);
				}
				
				aviso.setConfirmarEnvio(a.isConfirmarEnvio());
				if (a.isConfirmarEnvio()) {
					TipoConfirmacionAviso confirmadoEnvio = TipoConfirmacionAviso.DESCONOCIDO;
					if (a.getConfirmadoEnvio() == DetalleAviso.CONFIRMADO_ENVIADO) {
						confirmadoEnvio = TipoConfirmacionAviso.ENVIADO;
					} else if (a.getConfirmadoEnvio() == DetalleAviso.CONFIRMADO_NO_ENVIADO) {
						confirmadoEnvio = TipoConfirmacionAviso.NO_ENVIADO;
					}
					JAXBElement<TipoConfirmacionAviso> confEnvio = new JAXBElement<TipoConfirmacionAviso>(
							new QName("confirmadoEnvio"),
							TipoConfirmacionAviso.class,
							confirmadoEnvio
							);					
					aviso.setConfirmadoEnvio(confEnvio  );
				}				
				avisos.getAviso().add(aviso);
			}
			
			JAXBElement<DetalleAvisos> value = new JAXBElement<DetalleAvisos>(
					new QName("avisos"),
					DetalleAvisos.class,
					avisos
					);
			da.setAvisos(value );
		}
		
		return da;
	}

	

	

	private AcuseRecibo  acuseIntfToAcuseWS(es.caib.regtel.model.ws.AcuseRecibo acuseIntf) throws Exception{
		AcuseRecibo acuse = null;
		if(acuseIntf != null && acuseIntf.getFechaAcuseRecibo() != null){
			acuse = new AcuseRecibo();
			GregorianCalendar c = new GregorianCalendar();
			c.setTime(acuseIntf.getFechaAcuseRecibo());
			acuse.setFechaAcuseRecibo(DatatypeFactory.newInstance().newXMLGregorianCalendar(c));
		}
		return acuse;
	}
	
	private es.caib.regtel.model.ws.DatosRegistroEntrada prepararDatosRegistroEntradaIntf(DatosRegistroEntrada entrada) throws BackofficeFacadeException{
		es.caib.regtel.model.ws.DatosRegistroEntrada rEnt = null;
		try {
			if(entrada != null){
				rEnt = new es.caib.regtel.model.ws.DatosRegistroEntrada();
				if(entrada.getOficinaRegistral() != null){
					OficinaRegistral or = entrada.getOficinaRegistral();
					es.caib.regtel.model.ws.OficinaRegistral orIntf = new es.caib.regtel.model.ws.OficinaRegistral();
					orIntf.setCodigoOficina(or.getCodigoOficina());
					orIntf.setCodigoOrgano(or.getCodigoOrgano());
					rEnt.setOficinaRegistral(orIntf);
				}
				if(entrada.getDatosInteresado() != null){
					DatosInteresado di = entrada.getDatosInteresado();
					es.caib.regtel.model.ws.DatosInteresado diIntf = new es.caib.regtel.model.ws.DatosInteresado();
					diIntf.setCodigoLocalidad((di.getCodigoLocalidad() != null)? di.getCodigoLocalidad().getValue() : "");
					diIntf.setCodigoPais((di.getCodigoPais() != null)? di.getCodigoPais().getValue() : "");
					diIntf.setCodigoProvincia((di.getCodigoProvincia() != null)? di.getCodigoProvincia().getValue() : "");
					diIntf.setAutenticado((di.getAutenticado() != null)?di.getAutenticado().getValue() : false);
					diIntf.setNif(di.getNif());
					diIntf.setNombreApellidos(di.getNombreApellidos());
					diIntf.setNombreLocalidad((di.getNombreLocalidad() != null)? di.getNombreLocalidad().getValue() : "");
					diIntf.setNombrePais((di.getNombrePais() != null)? di.getNombrePais().getValue() : "");
					diIntf.setNombreProvincia((di.getNombreProvincia() != null)? di.getNombreProvincia().getValue() : "");
					rEnt.setDatosInteresado(diIntf);
				}
				if(entrada.getDatosRepresentado() != null){
					DatosRepresentado dr = entrada.getDatosRepresentado();
					es.caib.regtel.model.ws.DatosRepresentado drIntf = new es.caib.regtel.model.ws.DatosRepresentado();
					drIntf.setNif(dr.getNif());
					drIntf.setNombreApellidos(dr.getNombreApellidos());
					rEnt.setDatosRepresentado(drIntf);
				}
				if(entrada.getDatosAsunto() != null){
					DatosAsunto da = entrada.getDatosAsunto();
					es.caib.regtel.model.ws.DatosAsunto daIntf = new es.caib.regtel.model.ws.DatosAsunto();
					daIntf.setAsunto(da.getAsunto());
					daIntf.setCodigoUnidadAdministrativa(da.getCodigoUnidadAdministrativa());
					daIntf.setIdioma(da.getIdioma());
					daIntf.setTipoAsunto(da.getTipoAsunto());
					rEnt.setDatosAsunto(daIntf);
				}
				if(entrada.getDocumentos() != null && entrada.getDocumentos().getValue() != null){
					Documentos docsNot =  entrada.getDocumentos().getValue();
					ArrayList docs = new ArrayList();
					for(int i=0;i<docsNot.getDocumentos().size();i++){
						Documento dne = docsNot.getDocumentos().get(i);
						if(dne.getReferenciaRDS() != null && dne.getReferenciaRDS().getValue() != null && dne.getReferenciaRDS().getValue().getClave() != null && !"".equals(dne.getReferenciaRDS().getValue().getClave()) && dne.getReferenciaRDS().getValue().getCodigo() > 0){
							ReferenciaRDS ref = referenciaWSToReferenciaIntf(dne.getReferenciaRDS().getValue());
							docs.add(ref);
						}else{
							DocumentoRDS doc = new DocumentoRDS();
							doc.setModelo((dne.getModelo() != null)? dne.getModelo().getValue() : "");
							doc.setVersion((dne.getVersion() != null)? dne.getVersion().getValue() : 0);		
							String nombre = (dne.getNombre() != null)? dne.getNombre().getValue() : "";
							String extension = (dne.getExtension() != null)? dne.getExtension().getValue() : "";
							String nomfic = ""; 
							try{
								nomfic = StringUtil.normalizarNombreFichero(nombre)+"."+extension;
							}catch (Exception ex){
								nomfic = nombre+"."+extension;
							}		
							doc.setNombreFichero(nomfic);
							doc.setExtensionFichero(extension);
							doc.setDatosFichero((dne.getDatosFichero() != null)? dne.getDatosFichero().getValue() : null);
							FirmaIntf[] firmas = null;
							if(dne.getFirmas() != null){
								firmas = firmasWSToFirmasIntf(dne.getFirmas().getValue());
							}
							doc.setFirmas(firmas);
							doc.setPlantilla((dne.getPlantilla() != null)? dne.getPlantilla().getValue() : "");
							doc.setTitulo(nombre);
							docs.add(doc);	
						}
					}
					rEnt.setDocumentos(docs);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new es.caib.regtel.ws.v2.services.BackofficeFacadeException(ex.getMessage(), new es.caib.regtel.ws.v2.model.BackofficeFacadeException());
		}
		return rEnt;
	}
	
	private es.caib.regtel.model.ws.DatosRegistroSalida prepararDatosRegistroSalidaIntf(DatosRegistroSalida notificacion) throws BackofficeFacadeException {
		es.caib.regtel.model.ws.DatosRegistroSalida rSal = null;
		try {
			if(notificacion != null){
				rSal = new es.caib.regtel.model.ws.DatosRegistroSalida();
				if(notificacion.getDatosExpediente() != null){
					DatosExpediente de = notificacion.getDatosExpediente();
					es.caib.regtel.model.ws.DatosExpediente deIntf = new es.caib.regtel.model.ws.DatosExpediente();
					deIntf.setClaveExpediente(de.getClaveExpediente());
					deIntf.setIdentificadorExpediente(de.getIdentificadorExpediente());
					deIntf.setUnidadAdministrativa(de.getUnidadAdministrativa());
					rSal.setDatosExpediente(deIntf);
				}
				if(notificacion.getOficinaRegistral() != null){
					OficinaRegistral or = notificacion.getOficinaRegistral();
					es.caib.regtel.model.ws.OficinaRegistral orIntf = new es.caib.regtel.model.ws.OficinaRegistral();
					orIntf.setCodigoOficina(or.getCodigoOficina());
					orIntf.setCodigoOrgano(or.getCodigoOrgano());
					rSal.setOficinaRegistral(orIntf);
				}
				if(notificacion.getDatosInteresado() != null){
					DatosInteresado di = notificacion.getDatosInteresado();
					es.caib.regtel.model.ws.DatosInteresado diIntf = new es.caib.regtel.model.ws.DatosInteresado();
					diIntf.setCodigoLocalidad((di.getCodigoLocalidad() != null)? di.getCodigoLocalidad().getValue() : "");
					diIntf.setCodigoPais((di.getCodigoPais() != null)? di.getCodigoPais().getValue() : "");
					diIntf.setCodigoProvincia((di.getCodigoProvincia() != null)? di.getCodigoProvincia().getValue() : "");
					diIntf.setAutenticado((di.getAutenticado()!= null)? di.getAutenticado().getValue() : false);
					diIntf.setNif(di.getNif());
					diIntf.setNombreApellidos(di.getNombreApellidos());
					diIntf.setNombreLocalidad((di.getNombreLocalidad() != null)? di.getNombreLocalidad().getValue() : "");
					diIntf.setNombrePais((di.getNombrePais() != null)? di.getNombrePais().getValue() : "");
					diIntf.setNombreProvincia((di.getNombreProvincia() != null)? di.getNombreProvincia().getValue() : "");
					rSal.setDatosInteresado(diIntf);
				}
				if(notificacion.getDatosRepresentado() != null){
					DatosRepresentado dr = notificacion.getDatosRepresentado();
					es.caib.regtel.model.ws.DatosRepresentado drIntf = new es.caib.regtel.model.ws.DatosRepresentado();
					drIntf.setNif(dr.getNif());
					drIntf.setNombreApellidos(dr.getNombreApellidos());
					rSal.setDatosRepresentado(drIntf);
				}
				if(notificacion.getDatosNotificacion() != null){
					DatosNotificacion dn = notificacion.getDatosNotificacion();
					if(dn.getAviso() != null && dn.getOficioRemision() != null){
						es.caib.regtel.model.ws.DatosNotificacion dnIntf = new es.caib.regtel.model.ws.DatosNotificacion();
						dnIntf.setAcuseRecibo(dn.isAcuseRecibo());
						Aviso av = new Aviso();
						av.setTexto(dn.getAviso().getTexto());
						av.setTextoSMS((dn.getAviso().getTextoSMS() != null)? dn.getAviso().getTextoSMS().getValue() : "");
						av.setTitulo(dn.getAviso().getTitulo());
						dnIntf.setAviso(av);
						dnIntf.setIdioma(dn.getIdioma());
						OficioRemision or = new OficioRemision();
						or.setTexto(dn.getOficioRemision().getTexto());
						or.setTitulo(dn.getOficioRemision().getTitulo());
						dnIntf.setOficioRemision(or);
						dnIntf.setTipoAsunto(dn.getTipoAsunto());
						
						if (dn.getAccesiblePorClave() != null) {
							dnIntf.setAccesiblePorClave(dn.getAccesiblePorClave().getValue());
						}
						
						if (dn.getPlazo() != null) {
							dnIntf.setPlazo(dn.getPlazo().getValue());
						}
						
						if (notificacion.getDatosNotificacion().getOficioRemision().getTramiteSubsanacion() != null &&
							notificacion.getDatosNotificacion().getOficioRemision().getTramiteSubsanacion().getValue() != null 	){
								TramiteSubsanacion ts = new TramiteSubsanacion();
								ts.setDescripcionTramite(notificacion.getDatosNotificacion().getOficioRemision().getTramiteSubsanacion().getValue().getDescripcionTramite());
								ts.setIdentificadorTramite(notificacion.getDatosNotificacion().getOficioRemision().getTramiteSubsanacion().getValue().getIdentificadorTramite());
								ts.setVersionTramite(new Integer(notificacion.getDatosNotificacion().getOficioRemision().getTramiteSubsanacion().getValue().getVersionTramite()));
								if ( notificacion.getDatosNotificacion().getOficioRemision().getTramiteSubsanacion().getValue().getParametrosTramite() != null &&
									 notificacion.getDatosNotificacion().getOficioRemision().getTramiteSubsanacion().getValue().getParametrosTramite().getValue() != null ){
										List params = notificacion.getDatosNotificacion().getOficioRemision().getTramiteSubsanacion().getValue().getParametrosTramite().getValue().getParametroTramite();
										Map<String,String> parametros = new LinkedHashMap<String,String>();
										for (Iterator it = params.iterator();it.hasNext();){
											ParametroTramite pt = (ParametroTramite) it.next();
											parametros.put(pt.getParametro(),pt.getValor());
										}
										ts.setParametrosTramite(parametros);	
								}
								
								dnIntf.getOficioRemision().setTramiteSubsanacion(ts );
						}
						rSal.setDatosNotificacion(dnIntf);
					}
				}
				
				if(notificacion.getDocumentos() != null && notificacion.getDocumentos().getValue() != null){
					Documentos docsNot =  notificacion.getDocumentos().getValue();
					ArrayList docs = new ArrayList();
					for(int i=0;i<docsNot.getDocumentos().size();i++){
						Documento dne = docsNot.getDocumentos().get(i);
						if(dne.getReferenciaRDS() != null && dne.getReferenciaRDS().getValue() != null && dne.getReferenciaRDS().getValue().getClave() != null && !"".equals(dne.getReferenciaRDS().getValue().getClave()) && dne.getReferenciaRDS().getValue().getCodigo() > 0){
							ReferenciaRDS ref = referenciaWSToReferenciaIntf(dne.getReferenciaRDS().getValue());
							docs.add(ref);
						}else{
							DocumentoRDS doc = new DocumentoRDS();
							doc.setModelo((dne.getModelo() != null)? dne.getModelo().getValue() : "");
							doc.setVersion((dne.getVersion() != null)? dne.getVersion().getValue() : null);		
							String nombre = (dne.getNombre() != null)? dne.getNombre().getValue() : "";
							String extension = (dne.getExtension() != null)? dne.getExtension().getValue() : "";
							String nomfic = ""; 
							try{
								nomfic = StringUtil.normalizarNombreFichero(nombre)+"."+extension;
							}catch (Exception ex){
								nomfic = nombre+"."+extension;
							}		
							doc.setNombreFichero(nomfic);
							doc.setExtensionFichero(extension);
							doc.setDatosFichero((dne.getDatosFichero() != null)? dne.getDatosFichero().getValue() : null);
							FirmaIntf[] firmas = null;
							if(dne.getFirmas() != null){
								firmas = firmasWSToFirmasIntf(dne.getFirmas().getValue());
							}
							doc.setFirmas(firmas);
							doc.setPlantilla((dne.getPlantilla() != null)? dne.getPlantilla().getValue() : "");
							doc.setTitulo(nombre);
							docs.add(doc);	
						}
					}
					rSal.setDocumentos(docs);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new es.caib.regtel.ws.v2.services.BackofficeFacadeException(ex.getMessage(), new es.caib.regtel.ws.v2.model.BackofficeFacadeException());
		}
		return rSal;
	}
	
	private ResultadoRegistro resultadoRegistroTelematicoToResultadoRegistroNotificacion(ResultadoRegistroTelematico res) throws Exception{
		ResultadoRegistro rrn= null; 
		if(res != null && res.getResultadoRegistro() != null && res.getReferenciaRDSJustificante() != null) {
			rrn = new ResultadoRegistro();
			rrn.setFechaRegistro(stringToXmlGregorianCalendar(res.getResultadoRegistro().getFechaRegistro()));
			rrn.setNumeroRegistro(res.getResultadoRegistro().getNumeroRegistro());
			es.caib.regtel.ws.v2.model.ReferenciaRDS refWS = new es.caib.regtel.ws.v2.model.ReferenciaRDS();
			refWS.setClave(res.getReferenciaRDSJustificante().getClave());
			refWS.setCodigo(res.getReferenciaRDSJustificante().getCodigo());
			rrn.setReferenciaRDSJustificante(refWS);
		}
		return rrn;
	}
	private es.caib.regtel.model.ReferenciaRDSAsientoRegistral referenciaRDSAsientoRegistralWSToReferenciaRDSAsientoRegistralIntf(ReferenciaRDSAsientoRegistral referenciaRDS){
		es.caib.regtel.model.ReferenciaRDSAsientoRegistral ra = null;
		if(referenciaRDS != null){
			ra = new es.caib.regtel.model.ReferenciaRDSAsientoRegistral();
			if(referenciaRDS.getAsientoRegistral() != null){
				ra.setAsientoRegistral(referenciaWSToReferenciaIntf(referenciaRDS.getAsientoRegistral()));
			}
			if(referenciaRDS.getAnexos() != null){
				ra.setAnexos(anexosMapWSToAnexosMapIntf(referenciaRDS.getAnexos()));
			}
		}
		return ra;
	}
	private ReferenciaRDSAsientoRegistral referenciaRDSAsientoRegistralIntfToReferenciaRDSAsientoRegistralWS(es.caib.regtel.model.ReferenciaRDSAsientoRegistral raIntf) throws Exception{
		ReferenciaRDSAsientoRegistral ra= null; 
		if(raIntf != null) {
			ra = new ReferenciaRDSAsientoRegistral();
			if(raIntf.getAsientoRegistral() != null){
				ra.setAsientoRegistral(referenciaIntfToReferenciaWS(raIntf.getAsientoRegistral()));
			}
			if(raIntf.getAnexos() != null){
				ra.setAnexos(anexosMapIntfToAnexosMapWS(raIntf.getAnexos()));
			}
		}
		return ra;
	}
	
	private FirmaIntf[] firmasWSToFirmasIntf(FirmasWS firmas) throws Exception{
		FirmaIntf[] firmasIntf = null;
		if(firmas != null && firmas.getFirmas() != null){
			firmasIntf = new FirmaIntf[firmas.getFirmas().size()];
			PluginFirmaIntf plgFirma = PluginFactory.getInstance().getPluginFirma();
			for(int i=0;i<firmas.getFirmas().size();i++){
				firmasIntf[i] = firmaWSToFirmaIntf(firmas.getFirmas().get(i));
			}
		}
		return firmasIntf;
	}
	
	private FirmaIntf firmaWSToFirmaIntf(FirmaWS firma) throws Exception{
		FirmaIntf firmaIntf = null;
		if(firma != null){
			PluginFirmaIntf plgFirma = PluginFactory.getInstance().getPluginFirma();
			firmaIntf = plgFirma.parseFirmaFromWS(firma.getFirma(), firma.getFormato()!=null?firma.getFormato().getValue():null);			
		}
		return firmaIntf;
	}
	
	private XMLGregorianCalendar stringToXmlGregorianCalendar(String dateString) throws Exception{
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(StringUtil.FORMATO_REGISTRO);
			if(dateString != null && !"".equals(dateString)){
				Date date = sdf.parse(dateString);
				GregorianCalendar c = new GregorianCalendar();
				c.setTime(date);
				return DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
			}
		} catch (DatatypeConfigurationException e) {}
		return null;
	}
	
	
	private ReferenciaRDS referenciaWSToReferenciaIntf(es.caib.regtel.ws.v2.model.ReferenciaRDS refRds){
		ReferenciaRDS refInt = new ReferenciaRDS();
		if(refRds != null){
			refInt.setClave(refRds.getClave());
			refInt.setCodigo(refRds.getCodigo());
		}
		return refInt;
	}
	
	private	es.caib.regtel.ws.v2.model.ReferenciaRDS referenciaIntfToReferenciaWS(ReferenciaRDS refRds){
		es.caib.regtel.ws.v2.model.ReferenciaRDS refWS = new es.caib.regtel.ws.v2.model.ReferenciaRDS();
		if(refRds != null){
			refWS.setClave(refRds.getClave());
			refWS.setCodigo(refRds.getCodigo());
		}
		return refWS;
	}

	private Map anexosMapWSToAnexosMapIntf(AnexosMap anexos){
		Map referencias = null;
		if(anexos != null){
			referencias = new HashMap();
			for(int i=0;i<anexos.getAnexos().size();i++){
				referencias.put(anexos.getAnexos().get(i).getIdentificadorDocumento(), referenciaWSToReferenciaIntf(anexos.getAnexos().get(i).getReferenciaRDS()));
			}
		}
		return referencias;
	}

	private AnexosMap anexosMapIntfToAnexosMapWS(Map referencias){
		AnexosMap anexos = null;
		if(referencias != null){
			anexos = new AnexosMap();
			for ( Iterator it = referencias.keySet().iterator(); it.hasNext(); ){
				AnexoItem ai = new AnexoItem();
				ai.setIdentificadorDocumento((String)it.next());
				ai.setReferenciaRDS(referenciaIntfToReferenciaWS((ReferenciaRDS)referencias.get(ai.getIdentificadorDocumento())));
				anexos.getAnexos().add(ai);
			}
		}
		return anexos;
	}
	
}