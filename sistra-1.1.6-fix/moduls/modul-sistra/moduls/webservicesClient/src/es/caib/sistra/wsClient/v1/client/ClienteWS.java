package es.caib.sistra.wsClient.v1.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.soap.SOAPFaultException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.sistra.model.DatosFormulario;
import es.caib.sistra.modelInterfaz.DocumentoConsulta;
import es.caib.sistra.modelInterfaz.ValoresDominio;
import es.caib.util.StringUtil;
import es.caib.util.ws.client.WsClientSistraUtil;

/**
 * Cliente de WS para interfaz version 1 de backoffices para Sistra
 * 
 */
public class ClienteWS {
	
	private static Log log = LogFactory.getLog(ClienteWS.class);
	private static final QName SERVICE_NAME = new QName("urn:es:caib:sistra:ws:v1:services", "SistraFacadeService");
	private static final QName PORT_NAME = new QName("urn:es:caib:sistra:ws:v1:services", "SistraFacade");
	
	/**
	 * Resuelve dominio a traves interfaz de ws
	 * 
	 * @param url Endpoint del ws
	 * @param user Usuario autenticación (nulo si no autenticación)
	 * @param pass Password autenticación (nulo si no autenticación)
	 * @param idDominio Identificador de dominio
	 * @param parametros Parámetros dominio
	 * @return Valores del dominio 
	 * @throws Exception
	 */
	public static ValoresDominio obtenerDominio(String url, String user, String pass, String idDominio,List parametros) throws Exception{
		
	   log.debug("Obteniendo dominio por webservice v1: url=" + url + " idDominio=" + idDominio);
		
	   // Generamos port para acceder al ws
	   es.caib.sistra.wsClient.v1.services.SistraFacade port = generarPort(url,user,pass);

	   // Accedemos al ws y realizamos traduccion de parametros/resultado
       try {
        	es.caib.sistra.wsClient.v1.model.ParametrosDominio pd = new es.caib.sistra.wsClient.v1.model.ParametrosDominio();
           for(int i=0;i<parametros.size();i++){
        	   pd.getParametro().add((String)parametros.get(i));
           }
           es.caib.sistra.wsClient.v1.model.ValoresDominio vd = port.obtenerDominio(idDominio,pd);
           ValoresDominio vdInterfaz = valoresDominioWSToValoresDominioInt(vd);
           return vdInterfaz;
        } catch (es.caib.sistra.wsClient.v1.services.SistraFacadeException e) { 
        	log.error( "Error resolviendo Dominio: " + e.getMessage(), e );
			throw e;
        } catch(SOAPFaultException e){
        	log.error( "Error resolviendo Dominio: " + e.getMessage(), e );
			throw e;
        }			
	}
	
	/**
	 * Realiza trámite de tipo consulta a través de ws 
	 * @param url Endpoint del ws
	 * @param user Usuario autenticación (nulo si no autenticación)
	 * @param pass Password autenticación (nulo si no autenticación)
	 * @param datosFormulario Map con los formularios
	 * @return Lista de documentos retornados
	 * @throws Exception
	 */
	public static List realizarConsulta(String url,String user,String pass,String identificadorTramite, Map datosFormulario) throws Exception {
		
		 log.debug("Realizando tramite consulta por webservice v1: url=" + url);
			
		 // Generamos port para acceder al ws
		 es.caib.sistra.wsClient.v1.services.SistraFacade port = generarPort(url,user,pass);

		 // Accedemos al ws y realizamos traduccion de parametros/resultado
		 try {
        	//transformamos el map con los datos del formulario a un objecto de la clase FormulariosConsulta del WS
           es.caib.sistra.wsClient.v1.model.FormulariosConsulta forms = new es.caib.sistra.wsClient.v1.model.FormulariosConsulta();
           es.caib.sistra.wsClient.v1.model.FormularioConsulta form;
           for (Iterator it = datosFormulario.keySet().iterator();it.hasNext();){				
				String key = (String) it.next();
				form = new es.caib.sistra.wsClient.v1.model.FormularioConsulta();
				form.setIdentificador(StringUtil.getModelo(key));
				form.setNumeroInstancia(StringUtil.getVersion(key));
				form.setXml( ((DatosFormulario) datosFormulario.get(key)).getString() );
				forms.getFormularios().add(form);
			}	
           es.caib.sistra.wsClient.v1.model.DocumentosConsulta docs = port.realizarConsulta(identificadorTramite,forms);
           DocumentoConsulta doc;
           // Devolvemos resultado
            //convertimos el DocumentosConsulta que nos devuelve el WS y lo convertimos a DocumentoConsulta de la interfaz.
            //los añadimos todos dentro de un ResultadoRegistrar. 
    		ArrayList docRes = new ArrayList();
    		
    		for (int i=0;i<docs.getDocumento().size();i++){
   				doc = new DocumentoConsulta();
   				if(docs.getDocumento().get(i).getClaveRDS() != null){
   					doc.setClaveRDS(docs.getDocumento().get(i).getClaveRDS().getValue());
   				}
   				if(docs.getDocumento().get(i).getCodigoRDS() != null){
   					doc.setCodigoRDS(docs.getDocumento().get(i).getCodigoRDS().getValue());
   				}
   				if(docs.getDocumento().get(i).getContenidoFichero() != null){
   					doc.setContenidoFichero(docs.getDocumento().get(i).getContenidoFichero().getValue());
   				}
   				if(docs.getDocumento().get(i).getModelo() != null){
   					doc.setModelo(docs.getDocumento().get(i).getModelo().getValue());
   				}
 				doc.setNombreDocumento(docs.getDocumento().get(i).getNombreDocumento());
   				if(docs.getDocumento().get(i).getNombreFichero() != null){
   					doc.setNombreFichero(docs.getDocumento().get(i).getNombreFichero().getValue());
   				}
   				if(docs.getDocumento().get(i).getPlantilla() != null){
   					doc.setPlantilla(docs.getDocumento().get(i).getPlantilla().getValue());
   				}
   				if(docs.getDocumento().get(i).getTipoDocumento() != null){
   					doc.setTipoDocumento(docs.getDocumento().get(i).getTipoDocumento().charAt(0));
   				}
   				if(docs.getDocumento().get(i).getUrlAcceso() != null){
   					doc.setUrlAcceso(docs.getDocumento().get(i).getUrlAcceso().getValue());
   				}
   				if(docs.getDocumento().get(i).getUrlNuevaVentana() != null){
   					doc.setUrlNuevaVentana(docs.getDocumento().get(i).getUrlNuevaVentana().getValue());
   				}
   				if(docs.getDocumento().get(i).getVersion() != null){
   					doc.setVersion(docs.getDocumento().get(i).getVersion().getValue());
   				}
   				if(docs.getDocumento().get(i).getXml() != null){
   					doc.setXml(docs.getDocumento().get(i).getXml().getValue());
   				}
   				docRes.add(doc);
    		}
    		
    		return docRes;
    		
        } catch (es.caib.sistra.wsClient.v1.services.SistraFacadeException exc) { 
        	log.error( "Error realizando consulta: " + exc.getMessage(), exc );
			throw exc;
        } catch(SOAPFaultException e){
        	log.error( "Error realizando consulta: " + e.getMessage(), e );
			throw e;
       }
	}
	
	
	
	// ----------------------------------------------------------------------------------------------------
	//	 FUNCIONES PRIVADAS	
	//----------------------------------------------------------------------------------------------------
	
	private static es.caib.sistra.wsClient.v1.services.SistraFacade generarPort(String url,String user,String pass) throws Exception{
		javax.xml.ws.Service service =javax.xml.ws.Service.create(SERVICE_NAME); 
		service.addPort(PORT_NAME,javax.xml.ws.soap.SOAPBinding.SOAP11HTTP_BINDING, url);
		es.caib.sistra.wsClient.v1.services.SistraFacade port = service.getPort(PORT_NAME,es.caib.sistra.wsClient.v1.services.SistraFacade.class);
          
		// Configura puerto para autenticacion y paso por proxy
		WsClientSistraUtil.configurePort((BindingProvider)port,url,user,pass);
		
        return port;
	}
	
	
	/**
	 * Convierte modelo ws a modelo interfaz
	 * @param vd
	 * @return
	 */
	private static ValoresDominio valoresDominioWSToValoresDominioInt(es.caib.sistra.wsClient.v1.model.ValoresDominio vd){
		Map columnas;
		es.caib.sistra.wsClient.v1.model.Fila fila;
		ValoresDominio vdInterfaz = new ValoresDominio();
		try{
			if(vd.getDescripcionError() != null){
				vdInterfaz.setDescripcionError(vd.getDescripcionError().getValue());
			}
			vdInterfaz.setError(vd.isError());
			List filas = new ArrayList();
			if(vd.getFilas() != null){
				for(int i=0;i<vd.getFilas().getFila().size();i++){
					columnas = new HashMap();
					fila = vd.getFilas().getFila().get(i);
					for(int j=0;j<fila.getColumna().size();j++){
						columnas.put(fila.getColumna().get(j).getCodigo(), fila.getColumna().get(j).getValor());
					}
					filas.add(columnas);
				}
			}
			vdInterfaz.setFilas(filas);
		}catch(Exception e){
			e.printStackTrace();
			log.error( "Error pasando el Dominio WS a Dominio Interfaz: " + e.getMessage(), e );
		}
		return vdInterfaz;
	}
	
}
