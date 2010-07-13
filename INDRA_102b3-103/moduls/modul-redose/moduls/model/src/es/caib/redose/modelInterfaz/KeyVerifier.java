package es.caib.redose.modelInterfaz;

import java.security.MessageDigest;

import org.apache.commons.codec.binary.Hex;

import es.caib.util.FirmaUtil;


/**
 * 
 * Implementa clase que sirve para generar keys utilizadas en el verificador de documentos
 *
 */

public class KeyVerifier implements java.io.Serializable {
	
	/**
	 * Datos codificados
	 */
	String keyEncoded;
	
	/**
	 * Datos decodificados
	 */
	private Long idDocumento;
	private String claveDocumento;
	private String plantillaDocumento;
	private String idiomaDocumento;
	
	/**
	 * Constructor a partir de la clave codificada
	 * @param keyEncoded
	 */	
	public KeyVerifier(String keyEncoded)throws Exception{		
		
		this.keyEncoded = keyEncoded;
		
		// Decodificamos valores		
		String [] values = keyEncoded.split("-");		
		this.idDocumento=new Long(values[0]);
		this.claveDocumento=values[1];
		this.plantillaDocumento=fromHex(values[2]);
		this.idiomaDocumento=fromHex(values[3]);
								
	}
	
	
	/**
	 * Constructor a partir de los datos decodificados
	 * @param keyEncoded
	 */	
	public KeyVerifier(ReferenciaRDS refRDS,String plantillaDocumento,String idiomaDocumento)throws Exception{		
		
		this.idDocumento=new Long(refRDS.getCodigo());
		this.claveDocumento=generaClaveVerifier(refRDS.getClave());
		this.plantillaDocumento=plantillaDocumento;
		this.idiomaDocumento=idiomaDocumento;
		
		// Codificamos key
		String cadena = this.idDocumento 	+ "-" +
						this.claveDocumento + "-" +
						toHex(this.plantillaDocumento) + "-" +
						toHex(this.idiomaDocumento);
		this.keyEncoded=cadena;
	}
	
	
	/**
	 * Verifica si la clave RDS de acceso al documento coincide con la clave de acceso al documento del verificador
	 * @param claveRDS
	 * @return
	 * @throws Exception
	 */
	public boolean verifyClaveRDS(String claveRDS) throws Exception{
		return (generaClaveVerifier(claveRDS).equals(this.claveDocumento));
	}
	
	/**
	 * Generamos clave verifier para no hacer pública la clave del RDS
	 * 
	 * @param claveRDS
	 * @return
	 */
	private String generaClaveVerifier(String claveRDS)throws Exception{
		String hash = generaHashMD5(claveRDS);
		
		// Reducimos la clave a 10 digitos
		return  (hash.substring(0,5) + hash.substring(hash.length() - 5));
	}
	
	
	private String generaHashMD5(String plainText) throws Exception{		
		MessageDigest mdAlgorithm = MessageDigest.getInstance("MD5");
		mdAlgorithm.update(plainText.getBytes(FirmaUtil.CHARSET));
		byte[] digest = mdAlgorithm.digest();					
		return toHex(digest);		
	}

	private String toHex(byte[] data) throws Exception{
		return new String(Hex.encodeHex(data)).toUpperCase();
	}
	
	private String toHex(String plainText) throws Exception{
		return new String(Hex.encodeHex(plainText.getBytes(FirmaUtil.CHARSET))).toUpperCase();
	}
	
	private String fromHex(String hexText) throws Exception{			
		return new String(Hex.decodeHex(hexText.toUpperCase().toCharArray()));
	}

	public String getClaveDocumento() {
		return claveDocumento;
	}


	public void setClaveDocumento(String claveDocumento) {
		this.claveDocumento = claveDocumento;
	}


	public Long getIdDocumento() {
		return idDocumento;
	}


	public void setIdDocumento(Long idDocumento) {
		this.idDocumento = idDocumento;
	}


	public String getIdiomaDocumento() {
		return idiomaDocumento;
	}


	public void setIdiomaDocumento(String idiomaDocumento) {
		this.idiomaDocumento = idiomaDocumento;
	}


	public String getKeyEncoded() {
		return keyEncoded;
	}


	public void setKeyEncoded(String keyEncoded) {
		this.keyEncoded = keyEncoded;
	}


	public String getPlantillaDocumento() {
		return plantillaDocumento;
	}


	public void setPlantillaDocumento(String plantillaDocumento) {
		this.plantillaDocumento = plantillaDocumento;
	}
	
}
