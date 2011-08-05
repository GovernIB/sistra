package es.caib.sistra.plugins.gestorDocumental.impl.mock;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.redose.modelInterfaz.ConstantesRDS;
import es.caib.redose.modelInterfaz.DocumentoRDS;
import es.caib.redose.modelInterfaz.UsoRDS;
import es.caib.redose.persistence.delegate.DelegateRDSUtil;
import es.caib.redose.persistence.delegate.RdsDelegate;
import es.caib.sistra.plugins.PluginFactory;
import es.caib.sistra.plugins.firma.FirmaIntf;
import es.caib.sistra.plugins.firma.PluginFirmaIntf;
import es.caib.sistra.plugins.gestionDocumental.PluginGestorDocumentalIntf;

/**
 * 	
 * 	Objeto MOCK para simular la gestión de los documentos por un gestor documental.
 *
 */
public class PluginGestorDocumentalMock implements PluginGestorDocumentalIntf{
	private static Log log = LogFactory.getLog(PluginGestorDocumentalMock.class);
	
	public String consolidarDocumento(DocumentoRDS documento, List usos) throws Exception {
		if(usos != null && usos.size() > 0){
			HashMap<String,UsoRDS> usosMap = new HashMap<String,UsoRDS>();
			for(int i=0;i<usos.size();i++){
				UsoRDS uso = (UsoRDS)usos.get(i);
				usosMap.put(uso.getTipoUso(),uso);
			}
			try{
				/*
				 Documentos asociados a trámites (BANDEJA_ENTRADA)
				 
				 	Documento perteneciente a un trámite sistra de tipo registro CREAR UNA CARPETA (NOMBRE = NUM BANDEJA ENTRADA) EN BANDEJA_ENTRADA Y DENTRO LOS DOCS: 
				 		Uso para documentos asociados a un registro telemático de entrada (ConstantesRDS.TIPOUSO_REGISTROENTRADA) 
				 		Uso para documentos asociados a una entrada en la bandeja telemática (ConstantesRDS.TIPOUSO_BANDEJA) 
			        Documento perteneciente a un trámite sistra de tipo envío CREAR UNA CARPETA (NOMBRE = NUM BANDEJA ENTRADA) EN BANDEJA_ENTRADA Y DENTRO LOS DOCS: 
			        	Uso para documentos asociados a un envío (ConstantesRDS.TIPOUSO_ENVIO) 
			        	Uso para documentos asociados a una entrada en la bandeja telemática (ConstantesRDS.TIPOUSO_BANDEJA) 
			        Documento perteneciente a un trámite sistra de tipo preregistro CREAR UNA CARPETA (NOMBRE = NUM BANDEJA ENTRADA) EN BANDEJA_ENTRADA Y DENTRO LOS DOCS: 
			        	Uso para documentos asociados a un preregistro (ConstantesRDS.TIPOUSO_PREREGISTRO) 
			        	Uso para documentos asociados a una entrada en la bandeja telemática (ConstantesRDS.TIPOUSO_BANDEJA) 
					Documento perteneciente a un trámite NO sistra, es decir, un registro telemático de entrada realizado por otra aplicación distinta a sistra CREAR UNA CARPETA (NOMBRE = NUM REGISTRO ENTRADA) EN BANDEJA_ENTRADA Y DENTRO LOS DOCS: 
						Uso para documentos asociados a un registro telemático de entrada (ConstantesRDS.TIPOUSO_REGISTROENTRADA) 
			     
			     
			     Documentos asociados a expedientes y notificaciones  (BANDEJA_SALIDA)
			     	
			     	Documento perteneciente a un evento de expediente CREAR UNA CARPETA (NOMBRE = NUM REGISTRO SALIDA) EN BANDEJA_SALIDA Y DENTRO LOS DOCS: 
			        	Uso para documentos asociados a un evento de expediente (ConstantesRDS.TIPOUSO_EXPEDIENTE) 
			        Documento perteneciente a una notificación CREAR UNA CARPETA (NOMBRE = NUM REGISTRO SALIDA) EN BANDEJA_SALIDA Y DENTRO LOS DOCS: 
			        	Uso para documentos asociados a un registro telemático de salida (ConstantesRDS.TIPOUSO_REGISTROSALIDA) 
				*/
				
				if(usosMap.containsKey(ConstantesRDS.TIPOUSO_BANDEJA) 
					&& (usosMap.containsKey(ConstantesRDS.TIPOUSO_REGISTROENTRADA) || usosMap.containsKey(ConstantesRDS.TIPOUSO_ENVIO) || usosMap.containsKey(ConstantesRDS.TIPOUSO_PREREGISTRO))){
					log.debug("Consolidamos documento con TIPOUSO_BANDEJA");
					return consolidarDocumentoConUso(documento,usosMap.get(ConstantesRDS.TIPOUSO_BANDEJA),true);
				}else if(usosMap.containsKey(ConstantesRDS.TIPOUSO_REGISTROENTRADA)){
					log.debug("Consolidamos documento con TIPOUSO_REGISTROENTRADA");
					return consolidarDocumentoConUso(documento,usosMap.get(ConstantesRDS.TIPOUSO_REGISTROENTRADA),true);
				}else if(usosMap.containsKey(ConstantesRDS.TIPOUSO_EXPEDIENTE)){
					log.debug("Consolidamos documento con TIPOUSO_EXPEDIENTE");
					return consolidarDocumentoConUso(documento,usosMap.get(ConstantesRDS.TIPOUSO_EXPEDIENTE),false);
				}else if(usosMap.containsKey(ConstantesRDS.TIPOUSO_REGISTROSALIDA)){
					log.debug("Consolidamos documento con TIPOUSO_REGISTROSALIDA");
					return consolidarDocumentoConUso(documento,usosMap.get(ConstantesRDS.TIPOUSO_REGISTROSALIDA),false);
				}else{
					log.debug("El documento no tiene los usos asociados correctos");
					throw new Exception("El documento no tiene los usos asociados correctos");
				}
			}catch(Exception e){
				log.debug("Error al consolidar el documento. ",e);
				throw e;
			
			}
		}else{
			log.debug("El documento no tiene usos asociados");
			throw new Exception("El documento no tiene usos asociados");
		}
	}

	private String consolidarDocumentoConUso(DocumentoRDS documento, UsoRDS uso, boolean tipoEntrada) throws Exception{
		//Si el directorio raiz no existe lo creamos.
		String path = Constants.DIRECTORIO_RAIZ;
		File dir = new File(path);
		File carpeta;
		if (!dir.exists()) {
			if(!dir.mkdir()){
				log.debug("Error: No se puede crear el directorio "+path);
				throw new Exception("Error: No se puede crear el directorio.");
			}
		}
		if (dir.isDirectory()) {
			//segun el tipo de uso creamos si no existe la carpeta bandeja de entrada o bandeja de salida
			if(tipoEntrada){
				FilenameFilter filter = new FilenameFilter() {
					public boolean accept(File dir, String name) {
						return name.equals(Constants.BANDEJA_ENTRADA);
					}
				}; 
				carpeta = crearCarpeta(dir,path,Constants.BANDEJA_ENTRADA,filter);
				path = path+"/"+Constants.BANDEJA_ENTRADA;
			}else{
				FilenameFilter filter = new FilenameFilter() {
					public boolean accept(File dir, String name) {
						return name.equals(Constants.BANDEJA_SALIDA);
					}
				}; 
				carpeta = crearCarpeta(dir,path,Constants.BANDEJA_SALIDA,filter);
				path = path+"/"+Constants.BANDEJA_SALIDA;
			}
			log.debug("Estamos en el directorio: "+path);
			//una vez ya tenemos creada la carpeta de la bandeja, crearemos una carpeta con nombre el atributo de su referencia y dentro
			//de esta otra con el codigo del documento.
			if(carpeta != null){
				
				crearCarpeta(carpeta,path,uso.getReferencia().replaceAll("/","-"),null);
				path = path + "/" + uso.getReferencia().replaceAll("/","-");
				crearCarpeta(carpeta,path,documento.getReferenciaRDS().getCodigo()+"",null);
				path = path + "/"+documento.getReferenciaRDS().getCodigo()+"";
				log.debug("Hemos creado el directorio: "+path);
				guardarDocumento(documento.getDatosFichero(), documento.getNombreFichero(), "",path);
				if(documento.getFirmas() != null && documento.getFirmas().length > 0){
					guardarFirmas(documento, path);
				}
				if(documento.isEstructurado()){
					guardarVersionFormateada(documento, path);
				}
				return path.substring(Constants.DIRECTORIO_RAIZ.length());
			}else{
				log.debug("Error: El Directorio "+path+" no existe. ");
				throw new Exception("Error: El Directorio no existe.");
			}
		}else{
			log.debug("Error: El Directorio "+path+" no existe. ");
			throw new Exception("Error: El Directorio no existe.");
		}
				
	}

	private File crearCarpeta(File dir, String path, String nuevaCarpeta, FilenameFilter filter) throws Exception{
		//Si la carpeta nuevaCarpeta no existe la creamos.
		File[] carpeta = null;
		if(filter != null){
			carpeta = dir.listFiles(filter);
		}
		if (carpeta == null || carpeta.length == 0) {
			carpeta = new File[1];
			carpeta[0] = new File(path+"/"+nuevaCarpeta);
			if (!carpeta[0].exists()) {
				if(!carpeta[0].mkdir()){
					throw new Exception("Error: No se puede crear el directorio.");
				}
			}
		}
		return carpeta[0];
	}
	
	private void guardarDocumento(byte[] contenidoDocumento, String name, String extension, String path) throws Exception{
		//guardamos el documento en el path que nos indican y con el nombre que nos pasan
		File f;
		if(extension != null &&  !"".equals(extension)){
			f=new File(path+"/"+name+"."+extension);
		}else{
			f=new File(path+"/"+name);
		}
		FileOutputStream fo;
		try {
			fo = new FileOutputStream(f);
			fo.write(contenidoDocumento);
			fo.close();
		} catch (FileNotFoundException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		}
	}
	
	private void guardarFirmas(DocumentoRDS doc, String path) throws Exception{
		PluginFirmaIntf plgFirma = PluginFactory.getInstance().getPluginFirma();
		FirmaIntf[] firmas = doc.getFirmas();
    	for(int i=0;i<firmas.length;i++){
    		guardarDocumento(plgFirma.parseFirmaToBytes(firmas[i]),"signature"+i,"",path);
    	}
		
	}
	
	private void guardarVersionFormateada(DocumentoRDS doc, String path) throws Exception{
		RdsDelegate dlg = DelegateRDSUtil.getRdsDelegate();
		DocumentoRDS docFormateado = dlg.consultarDocumentoFormateado(doc.getReferenciaRDS(),doc.getPlantilla(), null);
		guardarDocumento(docFormateado.getDatosFichero(),docFormateado.getNombreFichero(),"",path );
	}
	
	
}
