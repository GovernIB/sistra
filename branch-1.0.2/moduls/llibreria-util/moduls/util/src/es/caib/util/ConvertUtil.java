package es.caib.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.codec.binary.Hex;

/**
 * Utilidades de conversion.
 * Para la conversión de cadenas a bytes y viceversa en los métodos para convertir a base 64 o hex 
 * se emplea el charset UTF-8
 * 
 */
public class ConvertUtil {
	
	private static String CHARSET = "UTF-8";
	
	/**
	 * Copia el contenido de un InputStream en un OutputStream
	 * 
	 * @param input
	 * @param output
	 * @return
	 * @throws IOException
	 */
	public static int copy(InputStream input, OutputStream output) throws IOException{
	      byte buffer[] = new byte[4096];
	      int count = 0;
	      for(int n = 0; -1 != (n = input.read(buffer));)
	      {
	          output.write(buffer, 0, n);
	          count += n;
	      }
	
	      return count;
	  }
	
	/**
	 * Realiza el encode de una cadena a HEX
	 */
	public static String encodeHex(String cadena) throws Exception{
		byte [] contentBytes = cadena.getBytes(CHARSET);
		char[] contentChars = Hex.encodeHex(contentBytes);
		return new String(contentChars);
	}
	
	/**
	 * Realiza el decode de HEX a una cadena
	 */
	public static String decodeHex(String cadenaHex) throws Exception{
		byte [] contentBytes =  Hex.decodeHex(cadenaHex.toCharArray());		
		return new String(contentBytes,CHARSET);
	}
	/**
     * 
     * @param cadena
     * @return
     * @throws Exception
     */
	public static String cadenaToBase64UrlSafe( String cadena ) throws Exception
    {
    	return bytesToBase64UrlSafe( getBytes( cadena ) );
    }
	/**
	 * Decodifica una cadena en B64 Url Safe a texto
	 * @param cadenaB64
	 * @return Cadena
	 * @throws Exception
	 */
	public static String base64UrlSafeToCadena( String cadenaB64 ) throws Exception
    {
    	return getString( base64UrlSafeToBytes( cadenaB64 ));
    }
	
	 /**
     * Codifica en B64 un texto
     * @param cadena
     * @return
     * @throws Exception
     */
	public static String cadenaToBase64( String cadena ) throws Exception
    {
    	return bytesToBase64( getBytes( cadena ) );
    }	
	/**
	 * Decodifica una cadena en B64 a bytes a texto
	 * @param cadenaB64
	 * @return
	 * @throws Exception
	 */
	public static String base64ToCadena( String cadenaB64 ) throws Exception
    {
    	return getString( base64ToBytes( cadenaB64 ));
    }
    
    /**
     * Decodifica una cadena en B64 a bytes
     * @param cadena
     * @return
     */
    public static byte[] base64ToBytes(String cadenaB64) throws Exception
    {
    	return base64ToBytes( cadenaB64, false );
    }
	
    public static String bytesToBase64(byte[] bytes) throws Exception
    {
    	return bytesToBase64( bytes, false );
    }
    
	// -------------------------------------------------------------------------------
	//  FUNCIONES PRIVADAS
	// -------------------------------------------------------------------------------
	    
    private static String bytesToBase64(byte[] bytes, boolean safe) throws Exception
    {    
     	String b64 = new sun.misc.BASE64Encoder().encode( bytes );    	
    	if ( safe ) b64 = escapeChars64UrlSafe( b64 );
    	return b64; 
    }
    private static String bytesToBase64UrlSafe(byte[] bytes) throws Exception
    {
    	return bytesToBase64( bytes, true );
    }
   private static byte[] base64UrlSafeToBytes(String cadenaB64) throws Exception
    {
    	return base64ToBytes( cadenaB64, true );
    }
    
    private static byte[] base64ToBytes(String cadenaB64, boolean safe ) throws Exception
    {    	
    	if ( safe ) cadenaB64 = unescapeChars64UrlSafe( cadenaB64 );
    	return new sun.misc.BASE64Decoder().decodeBuffer(cadenaB64);    
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
    private static byte [] getBytes( String cadena ) throws Exception
    {
    	return cadena.getBytes( CHARSET );
    }
    
    private static String getString( byte[] bytes ) throws Exception
    {
    	return new String( bytes, CHARSET );
    }
}
