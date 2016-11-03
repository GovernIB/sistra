package es.caib.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.StringUtils;

/**
 * Utilidades para cifrar
 *
 */
public class CifradoUtil {
	
	private static final String CHARSET = "UTF-8";

	public static String cifrar(String key,String value) throws Exception{		
		try{
			
			if (StringUtils.isEmpty(value)) return value;			
			
			KeyGenerator keyGen = KeyGenerator.getInstance("DES");
			keyGen.init(56);
			
			DESKeySpec dk = new DESKeySpec(key.getBytes(CHARSET)); 
			SecretKey secretKey = SecretKeyFactory.getInstance("DES").generateSecret(dk);
						
			
			Cipher cifrador= Cipher.getInstance("DES/ECB/PKCS5Padding"); 
			cifrador.init(Cipher.ENCRYPT_MODE, secretKey); 
			byte[] mensajeCifrado = cifrador.doFinal(value.getBytes(CHARSET));
	
			char[] contentChars = Hex.encodeHex(mensajeCifrado);
			String strCifrado = new String(contentChars);
			
			return strCifrado;
			
		}catch(Exception ex){
			throw new Exception("Excepcion en proceso de cifrado: " + ex.getMessage(),ex);
		}
	}
	
	
	public static String descifrar(String key,String valueCiphered) throws Exception{		
		try{
			if (StringUtils.isEmpty(valueCiphered)) return valueCiphered;
			
			KeyGenerator keyGen = KeyGenerator.getInstance("DES");
			keyGen.init(56);
			
			DESKeySpec dk = new DESKeySpec(key.getBytes(CHARSET)); 
			SecretKey secretKey = SecretKeyFactory.getInstance("DES").generateSecret(dk);
									
			Cipher cifrador= Cipher.getInstance("DES/ECB/PKCS5Padding"); 
			cifrador.init(Cipher.DECRYPT_MODE, secretKey); 
			byte[] mensajeDescifrado = cifrador.doFinal(Hex.decodeHex(valueCiphered.toCharArray()));				
			String strCifrado = new String(mensajeDescifrado,CHARSET);
			
			return strCifrado;
			
		}catch(Exception ex){
			throw new Exception("Excepcion en proceso de descifrado: " + ex.getMessage(),ex);
		}
	}
}
