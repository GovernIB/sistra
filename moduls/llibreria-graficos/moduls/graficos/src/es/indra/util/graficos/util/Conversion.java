package es.indra.util.graficos.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.sql.Blob;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Conversion
{
	String lastError = null;
	  public byte[] InputStreamB64toBytes(InputStream a_cadena)
	  throws Throwable
	  {
			BASE64Decoder l_deco = new BASE64Decoder();
			byte[] l_res = null;
			l_res = l_deco.decodeBuffer(a_cadena);
			return l_res;

	  }
	  public String InputStreamB64toString(InputStream a_cadena)
	  throws Throwable
	  {
	  		return InputStreamB64toString(a_cadena, FuncionesCadena.getCharset());
	  }
	  
	  public String InputStreamB64toString(InputStream a_cadena, String as_charset)
	  throws Throwable
	  {
			BASE64Decoder l_deco = new BASE64Decoder();
			
			return new String(l_deco.decodeBuffer(a_cadena), as_charset);
	  }
	  public OutputStream InputStreamtoB64OutputStream(InputStream a_cadena)
	  throws Throwable
	  {
		BASE64Encoder l_enco = new BASE64Encoder();
		ByteArrayOutputStream l_stream = new ByteArrayOutputStream();
		l_enco.encodeBuffer(a_cadena, l_stream);
		return l_stream;


	  }
	  public int InputStreamtoB64OutputStream(InputStream a_cadena, OutputStream a_salida)
	  throws Throwable
	  {

			  BASE64Encoder l_enco = new BASE64Encoder();
			  l_enco.encodeBuffer(a_cadena, a_salida);
			  return 0;

	  }
	 /**
	   * Método que convierte el InputStream pasado como parámetro a una cadena
	   * @param InputStream a_mensaje
	   * @return String Cadena de texto con el mensaje original
	   **/
	  public String InputStreamtoString(InputStream a_mensaje)
	  throws Throwable
	  {
			return InputStreamtoString(a_mensaje, FuncionesCadena.getCharset());
			  
	  }

		/**
		  * Método que convierte el InputStream pasado como parámetro a una cadena
		  * @param InputStream a_mensaje
		  * @return String Cadena de texto con el mensaje original
		  **/
		 public String InputStreamtoString(InputStream a_mensaje, String as_charset)
		 throws Throwable
		 {
		 	if (a_mensaje == null) return null;
			String ls_aux = "";
			int li_tamLecturaConversion = 8192;
			if (DatosEntorno.getVariableConfiguracion("LongitudBufferLecturaStream") != null)
				li_tamLecturaConversion = Integer.parseInt(DatosEntorno.getVariableConfiguracion("LongitudBufferLecturaStream"));			
			int lenT = a_mensaje.available();
			if (lenT <= 1) lenT = li_tamLecturaConversion;
			byte buffer[] = new byte[lenT];
			int len = 0;
			while  ((len=a_mensaje.read(buffer, 0, lenT)) >= 0)
			{
				ls_aux += new String(buffer, 0, len, as_charset);
			}
			return ls_aux;

			
			  
		 }
		 
	/**
	  * Método que convierte el InputStream pasado como parámetro a una cadena
	  * @param InputStream a_mensaje
	  * @return OutStream a_salida
	  **/
	 public void InputStreamtoOutputStream(InputStream a_mensaje, OutputStream a_salida)
	 throws Throwable
	 {
		
		if (a_mensaje == null) return;
		int li_tamLecturaConversion = 8192;
		if (DatosEntorno.getVariableConfiguracion("LongitudBufferLecturaStream") != null)
			li_tamLecturaConversion = Integer.parseInt(DatosEntorno.getVariableConfiguracion("LongitudBufferLecturaStream"));			
		
		int lenT = a_mensaje.available();
		if (lenT <= 1) lenT = li_tamLecturaConversion;
		byte buffer[] = new byte[lenT];
		int len = 0;
		while  ((len=a_mensaje.read(buffer, 0, lenT)) >= 0)
		{
			a_salida.write(buffer, 0, len);
		}

			
			  
	 }		 
	 
		 
	/**
	  * Método que convierte el InputStream pasado como parámetro a una cadena
	  * @param InputStream a_mensaje
	  * @return String Cadena de texto con el mensaje original
	  **/
	 public String StringReadertoString(StringReader a_mensaje, String as_charset)
	 throws Throwable
	 {
		if (a_mensaje == null) return null;
			
		int li_tamLecturaConversion = 8192;
		if (DatosEntorno.getVariableConfiguracion("LongitudBufferLecturaStream") != null)
			li_tamLecturaConversion = Integer.parseInt(DatosEntorno.getVariableConfiguracion("LongitudBufferLecturaStream"));			

		char buffer[] = new char[li_tamLecturaConversion];
		int len = 0;
		String ls_aux = null;				
		while  ((len=a_mensaje.read(buffer, 0, li_tamLecturaConversion)) >= 0)
		{
			ls_aux += new String(buffer, 0, len);
		}
			
		return ls_aux;
	 }		 	   

  	 public  InputStream OutputStreamtoInputStream(ByteArrayOutputStream a_cadena)
  	 throws Throwable
	  {
			InputStream l_is = new ByteArrayInputStream(a_cadena.toByteArray());
			return l_is;
	  }
	  	
	public OutputStream StringToB64OutputStream(String as_cadena)
	throws Throwable  
	{
			BASE64Encoder l_enco = new BASE64Encoder();
			ByteArrayOutputStream l_stream = new ByteArrayOutputStream();
			l_enco.encodeBuffer(as_cadena.getBytes(FuncionesCadena.getCharset()), l_stream);
			return l_stream;
	}
	
	public OutputStream StringToB64OutputStream(String as_cadena, String as_charset)
	throws Throwable
	{
			BASE64Encoder l_enco = new BASE64Encoder();
			ByteArrayOutputStream l_stream = new ByteArrayOutputStream();
			l_enco.encodeBuffer(as_cadena.getBytes(as_charset), l_stream);
			return l_stream;
	}
	/**
	 * Convierte una cadena de texto plano a una cadena en formato BASE64
	 * @param String arg0 Cadena de texto plano
	 * @return String Cadena en formato BASE64
	 **/		
	public String StringtoBase64 (String arg0)
	throws Throwable
	{
		return StringtoBase64(arg0, FuncionesCadena.getCharset());
	}
	

	/**
	 * Convierte una cadena de texto plano a una cadena en formato BASE64
	 * @param String arg0 Cadena de texto plano
	 * @return String Cadena en formato BASE64
	 **/		
	public String StringtoBase64 (String arg0, String as_charset)
	throws Throwable
	{
		if (arg0 == null) return null;
		byte aux[] = arg0.getBytes(as_charset);
		return BytestoBase64(aux);
	
	}	

	  /**
	   * Método que convierte la cadena pasada como parámetro a un objeto de tipo InputStream
	   * @param String Cadena de texto a convertir
	   * @return InputStream InputStream equivalente a la cadena de texto pasada como parámetros
	   **/
	  public InputStream StringToInputStream(String as_cadena)
	  throws Throwable
	  {
	  		return StringToInputStream(as_cadena, FuncionesCadena.getCharset());
	  }
	    /**
	   * Método que convierte la cadena pasada como parámetro a un objeto de tipo InputStream
	   * @param String Cadena de texto a convertir
	   * @return InputStream InputStream equivalente a la cadena de texto pasada como parámetros
	   **/
	  public InputStream StringToInputStream(String as_cadena, String as_charset)
		throws Throwable
	  {
			return new ByteArrayInputStream(as_cadena.getBytes(as_charset));
	  }
	  
	/**
	 * Método que convierte la cadena pasada como parámetro a un objeto de tipo InputStream
	 * @param String Cadena de texto a convertir
	 * @return InputStream InputStream equivalente a la cadena de texto pasada como parámetros
	 **/
	public StringReader StringToStringReader(String as_cadena, String as_charset)
	throws Throwable
	{
		  StringReader l_ret = new StringReader (as_cadena);
			
		  return l_ret;
	}
	  
	public OutputStream StringToOutputStream(String as_cadena)
	throws Throwable
	{
	    return StringToOutputStream(as_cadena, FuncionesCadena.getCharset());
	}
	
	public OutputStream StringToOutputStream(String as_cadena, String as_charset)
	throws Throwable
	{
	   ByteArrayOutputStream bos = new ByteArrayOutputStream(8164);
	   int len = 0;
	   InputStream bis = StringToInputStream(as_cadena, as_charset);
	   int li_long = bis.available();
	   if (li_long <= 1) li_long = 8192;
	   byte[] buf = new byte[li_long];
	   while ((len=bis.read(buf, 0 , li_long)) > -1)
	   {
		   bos.write(buf, 0 ,len);
	   }
	    
	   return bos;
 	} 

	/**
	 * Convierte una cadena en formato BASE64 a una cadena de texto sin codificar
	 * @param String arg0 Cadena en formato BASE64
	 * @return String Cadena decodificada
	 **/	
	public String Base64toString (String arg0, String as_charset)
	throws Throwable	
	{
			if (arg0 == null) return null;
			BASE64Decoder l_dec = new BASE64Decoder();
			byte[] lb_dec = l_dec.decodeBuffer(arg0);
			return BytestoString(lb_dec, as_charset);
	}

	public String BlobtoString(Blob a_lob)
	throws Throwable
	{
	  	
	  		return BlobtoString(a_lob, FuncionesCadena.getCharset());
	  	
	}
	  
	public String BlobtoString(Blob a_lob, String as_charset)
	throws Throwable
	{
	  		return InputStreamtoString(a_lob.getBinaryStream(), as_charset);
	}  
	
	/**
	 * Convierte un array de bytes a una cadena en formato BASE64
	 * @param byte[] arg0 Array de bytes a codificar
	 * @return String Cadena en formato BASE64
	 **/		
	public String BytestoBase64 (byte[] arg0)
	throws Throwable
	{
		BASE64Encoder enco = new BASE64Encoder();
		String ls_res = enco.encode(arg0);			
		return ls_res;
	}
	/**
	 * Convierte un array de bytes a cadena
	 * @param byte[] arg0 Array de bytes
	 * @return String Cadena equivalente al array de bytes pasado como parámetro
	 **/	
	public String BytestoString (byte[] arg0)
	throws Throwable
	{
			return new String(arg0, FuncionesCadena.getCharset());
	}
	/**
	 * Convierte un array de bytes a cadena
	 * @param byte[] arg0 Array de bytes
	 * @return String Cadena equivalente al array de bytes pasado como parámetro
	 **/	
	public String BytestoString (byte[] arg0, String as_charset)
	throws Throwable
	{

			return new String(arg0, as_charset);
	}
	/**
	 * Convierte una cadena en formato BASE64 a una cadena de texto sin codificar
	 * @param String arg0 Cadena en formato BASE64
	 * @return String Cadena decodificada
	 **/	
	public String Base64toString (String arg0)
	throws Throwable
	{
		return Base64toString(arg0, FuncionesCadena.getCharset());
	}

	/**
	 * Convierte una cadena en formato BASE64 a un array de bytes sin codificar
	 * @param String arg0 Cadena en formato BASE64
	 * @return byte[] Array de bytes decodificado
	 **/	
	public byte[] Base64toBytes (InputStream arg0)
	throws Throwable
	{
		if (arg0 == null) return null;
		
		BASE64Decoder deco = new BASE64Decoder();
		return deco.decodeBuffer(arg0);
	}	
	
	/**
	 * Convierte una cadena en formato BASE64 a un array de bytes sin codificar
	 * @param String arg0 Cadena en formato BASE64
	 * @return byte[] Array de bytes decodificado
	 **/	
	public byte[] Base64toBytes (String arg0)
	throws Throwable
	{
		if (arg0 == null) return null;
		
		BASE64Decoder deco = new BASE64Decoder();
		return deco.decodeBuffer(arg0);
	}
	/**
	 * @return
	 */
	public String getLastError()
	{
		return lastError;
	}

	/**
	 * @param a_string
	 */
	public void setLastError(String a_string)
	{
		lastError = a_string;
	}

}
