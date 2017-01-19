package es.caib.firmaweb.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

public class Base64UrlSafe {

	public static String encodeB64UrlSafe(byte [] data) {
		byte[] dataB64 = Base64.encodeBase64(data);
		try {
			return escapeChars64UrlSafe(new String(dataB64, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}
	
	public static String encodeB64UrlSafe(File file) {
		try {
			byte[] data = IOUtils.toByteArray(new FileInputStream(file));
			byte[] dataB64 = Base64.encodeBase64(data);
			return escapeChars64UrlSafe(new String(dataB64, "UTF-8"));
		} catch (Exception e) {
			return null;
		}
	}
	
	public static byte[] decodeB64UrlSafe(String dataB64UrlSafe) {
		try {
			return Base64.decodeBase64(unescapeChars64UrlSafe(dataB64UrlSafe).getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	} 
	
	 private static String escapeChars64UrlSafe( String cad )  
	    {
	    	cad = cad.replaceAll( "\\+", "-" );
	    	cad = cad.replaceAll( "/", "_" );
	    	cad = cad.replaceAll( "[\\n\\r]", "" );
	    	return cad;
	    }
	    
	    private static String unescapeChars64UrlSafe( String cad )
	    {
	    	cad = cad.replaceAll( "-", "+" );
	    	cad = cad.replaceAll( "_", "/" );
	    	return cad;
	    }
	
}
