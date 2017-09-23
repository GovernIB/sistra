package es.caib.sistra.plugins.firma.impl.mock;

import java.io.UnsupportedEncodingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.sistra.plugins.firma.FirmaIntf;

public class FirmaMock implements FirmaIntf{

	private static Log log = LogFactory.getLog(FirmaMock.class);
	
	/**
	 * Firma
	 */
	private String signature;
	
	/**
	 * Formato
	 */
	private String formato;
	
	/**
	 * Obtiene el nif del certificado
	 */
	public String getNif() {
		try {
			return UtilFirmaMock.getDNI(signature,formato);
		} catch (Exception e) {
			log.error("Error al obtener nif de la firma",e);
			return null;
		}
	}

	/**
	 * Obtiene nombre y apellidos del certificado
	 */
	public String getNombreApellidos() {		
		try {
			return UtilFirmaMock.getNombre(signature,formato);
		} catch (Exception e) {
			log.error("Error al obtener nombre y apellidos de la firma",e);
			return null;
		} 
	}

	/**
	 * Formato firma
	 */
	public String getFormatoFirma() {
		return formato;
	}

	/**
	 * Obtiene firma 
	 * @return
	 */
	protected String getSignature() {
		return signature;
	}

	/**
	 * Establece firma
	 * @param signature
	 */
	protected void setSignature(String signature) {
		this.signature = signature;
	}

	protected String getFormato() {
		return formato;
	}

	protected void setFormato(String formato) {
		this.formato = formato;
	}

	public byte[] getContenidoFirma() {
		try {
			return signature.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			return null;
		}	
	}

	public String getNifRepresentante() {
		// No implementado
		return null;
	}

	public String getNombreApellidosRepresentante() {
		// No implementado
		return null;
	}

}
