package es.caib.firmaweb.util;

import java.io.File;
import java.util.Collection;

import eu.medsea.mimeutil.MimeType;
import eu.medsea.mimeutil.MimeUtil;

/**
 * Utilidades
 * @author Indra
 *
 */
public class Utils {
	
	/**
	 * Obtiene mime type
	 * @param f File
	 * @return mime type
	 */
	public static String getMimeType(File f) {
		  String result = "application/octet-stream";
		  MimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.MagicMimeMimeDetector");
		  Collection mimeTypes = MimeUtil.getMimeTypes(f);
		  if (mimeTypes != null) {
			  MimeType mt =  (MimeType) mimeTypes.iterator().next();
			  result = mt.getMediaType();
		  }
	      return result;
	}
	

}
