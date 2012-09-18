package es.caib.sistra.plugins.firma.impl.mock;

import java.io.InputStream;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.sistra.plugins.firma.FicheroFirma;
import es.caib.sistra.plugins.firma.FirmaIntf;
import es.caib.sistra.plugins.firma.PluginFirmaIntf;

public class PluginFirmaMock implements PluginFirmaIntf{
	
	private static Log log = LogFactory.getLog(PluginFirmaMock.class);
	
	/**
	 * Obtiene proveedor
	 */
	public String getProveedor() {
		return PluginFirmaIntf.PROVEEDOR_AFIRMA;
	}
	
	/**
	 * Realiza firma
	 */
	public FirmaIntf firmar(InputStream datos,String nombreCertificado, Map parametros) throws Exception {
		return UtilFirmaMock.firmar(datos,nombreCertificado,parametros);		
	}

	/**
	 * Verifica firma: Fake devolvemos siempre true
	 */
	public boolean verificarFirma(InputStream datos, FirmaIntf firma) throws Exception {
		return true;
	}

	/**
	 * Parsea la firma proveniente del html
	 */
	public FirmaIntf parseFirmaFromHtmlForm(String signatureHtmlForm) throws Exception {
		// Devolvemos firma
		FirmaMock firma = new FirmaMock();
		firma.setSignature(UtilFirmaMock.unescapeChars64UrlSafe(signatureHtmlForm));
		firma.setFormato("CMS");
		return firma;
	}

	/**
	 * Deserializa firma y la convierte en un objeto de firma
	 */
	public FirmaIntf parseFirmaFromBytes(byte[] firmaBytes, String formatoFirma) throws Exception {
		// Devolvemos firma
		FirmaMock firma = new FirmaMock();
		firma.setSignature(new String(firmaBytes,"UTF-8"));
		firma.setFormato(formatoFirma);
		return firma;
	}

	/**
	 * Serializa firma para ser almacenada como un conjunto de bytes
	 */
	public byte[] parseFirmaToBytes(FirmaIntf firma)  throws Exception {		
		FirmaMock f = (FirmaMock) firma;
		return f.getSignature().getBytes("UTF-8");		
	}

	/**
	 * Genera fichero con la firma. Creamos un fic de texto con la firma.
	 */
	public FicheroFirma parseFirmaToFile(InputStream datosFirmados,FirmaIntf firma)  throws Exception {
		// Devolvemos fichero
		byte[] data = ((FirmaMock) firma).getSignature().getBytes("UTF-8");
		FicheroFirma fic = new FicheroFirma();
		fic.setContenido(data);
		fic.setNombreFichero("firma.txt");
		return fic;
	}
	
	public FirmaIntf parseFirmaFromWS(byte[] firmaBytes, String formatoFirma) throws Exception {
		return parseFirmaFromBytes(firmaBytes, formatoFirma);
	}

	public byte[] parseFirmaToWS(FirmaIntf firma) throws Exception {
		return parseFirmaToBytes(firma);
	}
	
}
