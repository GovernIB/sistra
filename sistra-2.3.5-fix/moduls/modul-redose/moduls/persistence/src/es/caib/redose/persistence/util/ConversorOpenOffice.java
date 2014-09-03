package es.caib.redose.persistence.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.ConnectException;
import java.net.InetAddress;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.DocumentFormat;
import com.artofsolving.jodconverter.DocumentFormatRegistry;
import com.artofsolving.jodconverter.XmlDocumentFormatRegistry;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
//import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import com.artofsolving.jodconverter.openoffice.converter.StreamOpenOfficeDocumentConverter;

/**
 * Conversor de documentos con OpenOffice
 * 
 */
public class ConversorOpenOffice {

	private String host;
	private String puerto;
	
	private static Log log = LogFactory.getLog(ConversorOpenOffice.class);
	
	public ConversorOpenOffice(String host,String puerto){
		this.host = host;
		this.puerto = puerto;
	}
	
	
	public byte[] convertirFitxer(byte[] fitxer, String extensioFitxerEntrada, String extensioFitxerSortida) throws ConnectException, Exception{
		
		OpenOfficeConnection connection = null;
		ByteArrayOutputStream outputFile = new ByteArrayOutputStream();
		ByteArrayInputStream fitxerEntrada = new ByteArrayInputStream(fitxer);
		try {
			
			log.debug("Convirtiendo fichero con extension " + extensioFitxerEntrada + " a extension " + extensioFitxerSortida);
			
			// Si no hay cambio de extension devolvemos el mismo fichero
			if(extensioFitxerSortida.equals(extensioFitxerEntrada)) return fitxer;
			
			// Connexió a la instància d'OpenOffice.org que estigui corrent (cal tenir en compte de configurar el port al fitxer properties)
			log.debug("Conectando a OpenOffice: " + host + ":" + puerto + " ...");
			connection = new SocketOpenOfficeConnection(host,Integer.parseInt(puerto));
			connection.connect();
			log.debug("Conectado a OpenOffice");
			
			// Es realitza la conversió
			log.debug("Realizando conversion ...");
			DocumentFormatRegistry registry = new XmlDocumentFormatRegistry( this.getClass().getResourceAsStream("document-formats.xml"));
			DocumentConverter converter = null;
			if(isRemoteOpenOfficeHost(host)){
				converter = new StreamOpenOfficeDocumentConverter(connection,registry);
			}else{
				converter = new OpenOfficeDocumentConverter(connection,registry);
			}
			
			DocumentFormat inputFormat = registry.getFormatByFileExtension(extensioFitxerEntrada);
			DocumentFormat outputFormat = registry.getFormatByFileExtension(extensioFitxerSortida);
			converter.convert(fitxerEntrada, inputFormat, outputFile, outputFormat);
			
			log.debug("Conversion realizada ...");

			return outputFile.toByteArray();
			
		} catch (ConnectException e) {
			log.error("Error al conectar con OpenOffice",e);
			throw new Exception("Error al conectar con OpenOffice",e);
		} catch (Exception e) {
			log.error("Error al convertir documento con OpenOffice",e);
			throw new Exception("Error al conectar con OpenOffice",e);
		}finally{
			if (connection != null){
				try{
					// Tanca la connexió oberta
					connection.disconnect();
				}catch(Exception ex){
					log.error("Error cerrando conexion con OpenOffice",ex);
				}
			}
		}
	}
	
	/*
	 *Función auxiliar que devuelve un booleano indicando si el host del openOffice es remoto o no.
	 *Si la variable es localhost, 127.0.0.1, el nombre de host de la maquina local o la ip de la 
	 *maquina local se devuelve false en caso contrario devolvemos true indicando que el host es remoto. 
	 */
	private boolean isRemoteOpenOfficeHost(String host){
		try{
			if (host != null 
					&& !"".equals(host) 
					&& !host.toUpperCase().startsWith("LOCALHOST") 
					&& !host.equals("127.0.0.1") 
					&& !host.toUpperCase().startsWith(InetAddress.getLocalHost().getHostName().toUpperCase()) 
					&& !host.equals(InetAddress.getLocalHost().getHostAddress()) ) {
				return true;	
			}else {
	            return false;
	        }
		}catch(Exception e){
			return false;
		}
	}
	
}
