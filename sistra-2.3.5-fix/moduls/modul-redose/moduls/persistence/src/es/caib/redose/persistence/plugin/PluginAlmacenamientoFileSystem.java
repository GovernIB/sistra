package es.caib.redose.persistence.plugin;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;


import es.caib.redose.persistence.util.ConfigurationUtil;
import es.caib.util.GeneradorId;

/**
 * Plugin que almacena los ficheros en el filesystem.
 * 
 * Los ficheros se almacenaran creando la siguiente estructura de carpetas:	modelo/version/anyo/mes/file
 * 
 * @author Indra
 *
 */
public class PluginAlmacenamientoFileSystem extends PluginAlmacenamientoRDSExterno {

	/**
	 * Path de disco donde se almacenan los ficheros.
	 */
	private static String ROOT_PATH = "";
	
	protected boolean eliminarFicheroExterno(String referenciaExterna)
			throws Exception {
		File file = new File(getFilePath(referenciaExterna));
		if (!file.exists()) {
			return true;
		}
		if (!file.isFile()) {
			throw new Exception("El fichero no es un file");
		}
		return file.delete();		
	}

	
	protected String guardarFicheroExterno(Long id, byte[] datos,
			MetadaAlmacenamiento metadata) throws Exception {
		
		// Generamos path directorio
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(metadata.getFecha() != null? metadata.getFecha() : new Date());
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		String pathDir = "/" + metadata.getModelo() + "/" + metadata.getVersion() + "/" + year + "/" + month;
		File directorio = new File(getRootPath() + pathDir);
		if (!directorio.exists()) {
			FileUtils.forceMkdir(directorio);
		}
				
		// Generamos fichero
		String pathFile = GeneradorId.generarId() + "." + metadata.getExtension();
		String referenciaFile = pathDir + "/" + pathFile;
		File fic = new File(getRootPath() + referenciaFile);
		if (fic.exists()) {
			throw new Exception("Error generando referencia unica");
		}
		ByteArrayInputStream bis = null;
		FileOutputStream fos = null;
		try {
			bis = new ByteArrayInputStream(datos);
			fos = new FileOutputStream(fic);
			IOUtils.copy(bis, fos);
		} finally {
			IOUtils.closeQuietly(bis);
			IOUtils.closeQuietly(fos);
		}
		
		// Devolvemos referencia externa (path fichero relativo)
		return referenciaFile;
	}

	
	protected byte[] obtenerFicheroExterno(String referenciaExterna)
			throws Exception {
		File fic = new File(getRootPath() + referenciaExterna);
		if (!fic.exists() || !fic.isFile()) {
			throw new Exception("No existe fichero");
		}
		
		byte content[] = null;
		ByteArrayOutputStream bos = null;
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(fic);
			bos = new ByteArrayOutputStream(fis.available());
			IOUtils.copy(fis, bos);
			content = bos.toByteArray();
		} finally {
			IOUtils.closeQuietly(fis);
			IOUtils.closeQuietly(bos);
		}
		
		return content;
	}
	
	

	
	/**
	 * Obtiene path fichero.
	 * @param referenciaExterna Ref externa
	 * @return  path fichero.
	 * @throws Exception 
	 */
	private String getFilePath(String referenciaExterna) throws Exception {
		return getRootPath() + "/" + referenciaExterna;
	}
	
	/**
	 * Obtiene path raiz.
	 * @return path raiz.
	 * @throws Exception
	 */
	private String getRootPath() throws Exception {
		if (ROOT_PATH == "") {
			synchronized (ROOT_PATH) {
				try {
					ROOT_PATH = ConfigurationUtil.getInstance().obtenerPropiedades().getProperty("plugin.filesystem.rootPath");					
				} catch (Exception e) {
					throw new Exception("No se ha establecido la propiedad plugin.filesystem.rootPath");
				}
			}
		}
		if (StringUtils.isBlank(ROOT_PATH)) {
			throw new Exception("No se ha establecido la propiedad plugin.filesystem.rootPath");
		}
		return ROOT_PATH;
	}

}
