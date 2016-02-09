package es.caib.sistra.configuracionutil;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringEscapeUtils;


/**
 * Genera SAR en base a los ficheros de configuracion.
 * @author Indra
 *
 */
public class ConfiguracionSAR {

	
	public static void main(String arg[]) throws Exception{
		
		System.out.println("Generando SAR configuracion...");
		
		// Nombre MBean
		String mbeanName = arg[0];
		// Path jboss-service.xml
		String filePath = arg[1] + "/jboss-service.xml";
		// Prefijo propiedades
		String prefixSAR = arg[2];
		// Path dir configuracion
		String confPath = arg[3];
		// Ficheros de propiedades a tratar (separados por coma)
		String propFilesStr = arg[4];
		
		// Fichero propiedades a incluir tal cual
		String propFilesIncludeStr = "";
		if (arg.length > 5) {			
			propFilesIncludeStr = arg[5];
		}
		
		System.out.println("Generando " + filePath + " a partif configuracion de " + confPath);
		
		File f = new File(filePath);
		
		if (f.exists()) {
			f.delete();
		}
		
		StringBuffer sb = new StringBuffer(8192);
		sb.append("<server>\n");
		sb.append("<mbean code=\"org.jboss.varia.property.SystemPropertiesService\" name=\"jboss:type=Service,name=" + mbeanName + "\">\n");
		sb.append("<attribute name=\"Properties\">\n");		
		
		// Fics de propiedades
		String[] propFiles = propFilesStr.split(",");
		for (int i=0; i < propFiles.length; i++) {
			String pf = propFiles[i];
			sb.append(generarContentProps(prefixSAR, pf, confPath, true));
		}
		
		// Fic a incluir contenido tal cual
		if (propFilesIncludeStr.length() > 0) {
			String[] propFilesInclude = propFilesIncludeStr.split(",");
			for (int i=0; i < propFilesInclude.length; i++) {
				String pf = propFilesInclude[i];
				sb.append(generarContentProps(prefixSAR, pf, confPath, false));
			}
		}
		
		
		sb.append("</attribute>\n");
		sb.append("</mbean>\n");
		sb.append("</server>\n");
		String content = sb.toString();
		ByteArrayInputStream bis = new ByteArrayInputStream(content.getBytes("UTF-8"));
		FileOutputStream fos = new FileOutputStream(f);
		IOUtils.copy(bis, fos);
		bis.close();
		fos.close();
		
		System.out.println("Generado SAR configuracion");
	}

	private static String generarContentProps(String prefixSAR, String modulo, 
			String confPath, boolean includePrefix) throws IOException,
			FileNotFoundException {
		
		String fileProps = modulo + ".properties";
		
		StringBuffer sbp = new StringBuffer(8192);
		sbp.append("\n\n <!--  " + modulo + "  -->\n");
		Properties properties;
		properties = new OrderedProperties();
		properties.load(new FileInputStream(confPath + "/" + fileProps));
		
		String prefix = "";
		if (includePrefix) {
			prefix = prefixSAR + modulo + ".";
		}
		
		for (Enumeration e = properties.propertyNames(); e.hasMoreElements();) {
			String key = (String) e.nextElement();
			String value = properties.getProperty(key);
			sbp.append(prefix + key + "=" + StringEscapeUtils.escapeXml(value) + "\n");			
		}
		
		String contentProps = sbp.toString();
		return contentProps;
	}

	
}
