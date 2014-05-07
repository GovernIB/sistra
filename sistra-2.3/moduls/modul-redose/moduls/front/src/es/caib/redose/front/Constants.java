package es.caib.redose.front;

import java.io.Serializable;

/**
 * Constants globals.
 */
public class Constants implements Serializable 
{
	/**
     * Atributo de contexto donde se guarda el lenguaje por defecto.
     */
    public static final String DEFAULT_LANG_KEY 	= "es.caib.redose.front.DEFAULT_LANG";
    /**
     * Atributo de contexto donde se guarda la implementacion de la firma
     */
    public static final String IMPLEMENTACION_FIRMA_KEY 			= "es.caib.redose.front.implementacionFirma";
    /**
     * Atributo de contexto donde se guarda la info del organismo
     */
    public static final String ORGANISMO_INFO_KEY 			= "es.caib.redose.front.infoOrganismo";
    
    
    public static final String DEFAULT_LANG 		= "ca";	
	public static final String DATOS_SESION_KEY		= "es.caib.redose.front.DATOS_SESION";
	public static String DESTINOFICHERO_KEY 			= "destinofichero";
	public static String NOMBREFICHERO_KEY 			= "nombrefichero";
	public static String DATOSFICHERO_KEY 			= "datosfichero";
	public static String URLFICHERO_KEY 			= "urlAcceso";
	
	public static String DESTINOFICHERO_PDFNAVEGADOR_KEY 			= "pdfnavegador";
	public static String DESTINOFICHERO_DOWNLOAD_KEY 			= "download";
	
	public static String MESSAGE_KEY = "mensaje";
	
	public static String DEFAULT_FORMATEADOR = "es.caib.redose.persistence.formateadores.FormateadorPdfFormularios";	
}
