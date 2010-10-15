package es.caib.zonaper.ws.v1.services;


import org.apache.commons.lang.StringUtils;

import es.caib.zonaper.modelInterfaz.DocumentoExpedientePAD;
import es.caib.zonaper.modelInterfaz.EventoExpedientePAD;
import es.caib.zonaper.modelInterfaz.ExpedientePAD;
import es.caib.zonaper.persistence.delegate.PadBackOfficeDelegate;
import es.caib.zonaper.persistence.delegate.PadBackOfficeUtil;
import es.caib.zonaper.ws.v1.model.DocumentoExpediente;
import es.caib.zonaper.ws.v1.model.EventoExpediente;
import es.caib.zonaper.ws.v1.model.Expediente;


@javax.jws.WebService(portName = "BackofficeFacade", serviceName = "BackofficeFacadeService", 
        targetNamespace = "urn:es:caib:zonaper:ws:v1:services", 
        endpointInterface = "es.caib.zonaper.ws.v1.services.BackofficeFacade")
public class BackofficeFacadeImpl implements BackofficeFacade {

	public void altaEventoExpediente(long unidadAdministrativa, String identificadorExpediente, String claveExpediente, EventoExpediente evento) throws es.caib.zonaper.ws.v1.services.BackofficeFacadeException{		
		try{					
			EventoExpedientePAD evPAD = eventoWSToEventoPAD(evento);
			
			PadBackOfficeDelegate pad = PadBackOfficeUtil.getBackofficeExpedienteDelegate();
			pad.altaEvento(unidadAdministrativa, identificadorExpediente, claveExpediente, evPAD);
		}catch( Exception exc ){
			exc.printStackTrace();
		     throw new es.caib.zonaper.ws.v1.services.BackofficeFacadeException(exc.getMessage(),new BackofficeFacadeException());
		}
		 
		 
	}

	public String altaExpediente(Expediente expediente) throws es.caib.zonaper.ws.v1.services.BackofficeFacadeException{	
		try {
			ExpedientePAD expPAD = expedienteWSToExpedientePAD(expediente);
			PadBackOfficeDelegate pad = PadBackOfficeUtil.getBackofficeExpedienteDelegate();
			String id = pad.altaExpediente(expPAD);
			return id;		
		} catch (Exception exc) {
			exc.printStackTrace();
		    throw new es.caib.zonaper.ws.v1.services.BackofficeFacadeException(exc.getMessage(),new BackofficeFacadeException());
		}
	}
	
	// --------------------------------------------------------------
	//		FUNCIONES AUXILIARES
	// --------------------------------------------------------------
	private DocumentoExpedientePAD documentoWSToDocumentoPAD(DocumentoExpediente docWS) throws Exception{
		DocumentoExpedientePAD docPAD = new DocumentoExpedientePAD();
		if(docWS != null){
			if(docWS.getClaveRDS() != null){
				docPAD.setClaveRDS(StringUtils.defaultIfEmpty(docWS.getClaveRDS().getValue(),null));
			}
			if(docWS.getCodigoRDS() != null){
				if(docWS.getCodigoRDS().getValue() != null && docWS.getCodigoRDS().getValue() != 0){
					docPAD.setCodigoRDS(docWS.getCodigoRDS().getValue());
				}
			}
			if(docWS.getContenidoFichero() != null){
				docPAD.setContenidoFichero(docWS.getContenidoFichero().getValue());
			}
			if(docWS.getEstructurado() != null){
				docPAD.setEstructurado(docWS.getEstructurado().getValue());
			}
			if(docWS.getModeloRDS() != null){
				docPAD.setModeloRDS(StringUtils.defaultIfEmpty(docWS.getModeloRDS().getValue(),null));
			}
			if(docWS.getNombre() != null){
				docPAD.setNombre(StringUtils.defaultIfEmpty(docWS.getNombre().getValue(),null));
			}
			if(docWS.getTitulo() != null){
				docPAD.setTitulo(StringUtils.defaultIfEmpty(docWS.getTitulo().getValue(),null));
			}
			if(docWS.getVersionRDS() != null){
				docPAD.setVersionRDS(docWS.getVersionRDS().getValue());		
			}
		}
		return docPAD;	
	}
	
	private EventoExpedientePAD eventoWSToEventoPAD(EventoExpediente evWS) throws Exception{
		EventoExpedientePAD evPAD = new EventoExpedientePAD();
		if(evWS != null){
			evPAD.setTitulo(StringUtils.defaultIfEmpty(evWS.getTitulo(),null));
			if(evWS.getEnlaceConsulta() != null){
				evPAD.setEnlaceConsulta(StringUtils.defaultIfEmpty(evWS.getEnlaceConsulta().getValue(),null));		
			}
			evPAD.setTexto(StringUtils.defaultIfEmpty(evWS.getTexto(),null));
			if(evWS.getTextoSMS() != null){
				evPAD.setTextoSMS(StringUtils.defaultIfEmpty(evWS.getTextoSMS().getValue(),null));
			}
			
			// Copiamos documentos		
			if (evWS.getDocumentos() != null && evWS.getDocumentos().getValue() != null && evWS.getDocumentos().getValue().getDocumento() != null){
				for (int i=0;i<evWS.getDocumentos().getValue().getDocumento().size();i++){
					DocumentoExpedientePAD docPAD = documentoWSToDocumentoPAD(evWS.getDocumentos().getValue().getDocumento().get(i));
					evPAD.addDocumento(docPAD);			
				}				
			}
		}
		
		return evPAD;		
	}		
	
	private ExpedientePAD expedienteWSToExpedientePAD(Expediente exWS) throws Exception{
		ExpedientePAD exPAD = new ExpedientePAD();
		if(exWS != null){
			exPAD.setAutenticado(exWS.isAutenticado());
			exPAD.setClaveExpediente(StringUtils.defaultIfEmpty(exWS.getClaveExpediente(),null));
			exPAD.setDescripcion(StringUtils.defaultIfEmpty(exWS.getDescripcion(),null));
			exPAD.setIdentificadorExpediente(StringUtils.defaultIfEmpty(exWS.getIdentificadorExpediente(),null));
			if(exWS.getIdentificadorUsuario() != null){
				exPAD.setIdentificadorUsuario(StringUtils.defaultIfEmpty(exWS.getIdentificadorUsuario().getValue(),null));
			}
			exPAD.setIdioma(StringUtils.defaultIfEmpty(exWS.getIdioma(),null));
			if(exWS.getNifRepresentado() != null){
				exPAD.setNifRepresentado(StringUtils.defaultIfEmpty(exWS.getNifRepresentado().getValue(),null));
			}
			if(exWS.getNombreRepresentado() != null){
				exPAD.setNombreRepresentado(StringUtils.defaultIfEmpty(exWS.getNombreRepresentado().getValue(),null));
			}
			if(exWS.getNumeroEntradaBTE() != null){
				exPAD.setNumeroEntradaBTE(StringUtils.defaultIfEmpty(exWS.getNumeroEntradaBTE().getValue(),null));
			}
			exPAD.setUnidadAdministrativa(exWS.getUnidadAdministrativa());
			// Copiamos eventos	
			if (exWS.getEventos() != null && exWS.getEventos().getValue() != null && exWS.getEventos().getValue().getEvento() != null ){
				for (int i=0;i<exWS.getEventos().getValue().getEvento().size();i++){
					EventoExpedientePAD evPAD = eventoWSToEventoPAD(exWS.getEventos().getValue().getEvento().get(i));
					exPAD.getElementos().add(evPAD);			
				}
			}
			
			// Copiamos configuracion avisos
			if (exWS.getConfiguracionAvisos() != null && exWS.getConfiguracionAvisos().getValue() != null){
				if(exWS.getConfiguracionAvisos().getValue().getAvisoEmail() != null){
					exPAD.getConfiguracionAvisos().setAvisoEmail(StringUtils.defaultIfEmpty(exWS.getConfiguracionAvisos().getValue().getAvisoEmail().getValue(),null));
				}
				if(exWS.getConfiguracionAvisos().getValue().getAvisoSMS() != null){
					exPAD.getConfiguracionAvisos().setAvisoSMS(StringUtils.defaultIfEmpty(exWS.getConfiguracionAvisos().getValue().getAvisoSMS().getValue(),null));
				}
				if(exWS.getConfiguracionAvisos().getValue().getHabilitarAvisos() != null){
					exPAD.getConfiguracionAvisos().setHabilitarAvisos(exWS.getConfiguracionAvisos().getValue().getHabilitarAvisos().getValue());
				}
			}
		}
		return exPAD;		
	}	

}