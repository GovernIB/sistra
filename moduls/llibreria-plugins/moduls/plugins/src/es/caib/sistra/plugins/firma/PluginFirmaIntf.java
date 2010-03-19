package es.caib.sistra.plugins.firma;

import java.io.InputStream;
import java.util.Map;

import es.caib.sistra.plugins.PluginSistraIntf;

/**
 * Interfaz modulo de firma
 */
public interface PluginFirmaIntf extends PluginSistraIntf{

	/**
	 * Proveedor @Firma
	 */
	public static String PROVEEDOR_AFIRMA = "@FIRMA";
	/**
	 * Proveedor CAIB
	 */
	public static String PROVEEDOR_CAIB = "CAIB";
	
	/**
	 * Obtiene proveedor de firma (ver constantes PROVEEDOR)
	 * @return Devuelve el proveedor de firma
	 */	
	public String getProveedor();
		
	/**
	 * Firma datos con un certificado
	 * 
	 * @param datos Datos a firmar
	 * @param certificado Nombre del certificado
	 * @param parametros Parametros especificos proveedor (para CAIB el content-type)
	 * @return Firma digital
	 */
	public FirmaIntf firmar(InputStream datos,String nombreCertificado, Map parametros) throws Exception;
	
	/**
	 * Verifica una firma 
	 * 
	 * @param datos Datos firmados
	 * @param firma Firma digital
	 * @return Devuelve si la firma es correcta
	 */
	public boolean verificarFirma(InputStream datos, FirmaIntf firma) throws Exception;
	
	/**
	 * Crea una firma a partir de los datos recibidos desde formulario HTML
	 * 
	 * @param signHtmlForm Firma serializada a cadena seg�n proveedor
	 * @return Datos de la firma
	 */
	public FirmaIntf parseFirmaFromHtmlForm(String signatureHtmlForm) throws Exception;
	
	/**
	 * Crea una firma a partir de un array de bytes
	 * 
	 * @param firmaBytes Firma serializada en bytes
	 * @param formatoFirma Formato de la firma
	 * @return Firma serializada en bytes
	 */
	public FirmaIntf parseFirmaFromBytes(byte[] firmaBytes, String formatoFirma) throws Exception;
	
	/**
	 * Serializa la firma en un byte array para almacenar en BBDD
	 * @return contenido de la firma en bytes
	 */
	public byte[] parseFirmaToBytes(FirmaIntf firma) throws Exception;
	
	/**
	 * Serializa la firma digital en un fichero descargable por el usuario 
	 * @param datosFirmados Datos firmados
	 * @param firma Firma digital
	 * @return Fichero que contiene la firma digital
	 */
	public FicheroFirma parseFirmaToFile(InputStream datosFirmados,FirmaIntf firma) throws Exception;
	
}
