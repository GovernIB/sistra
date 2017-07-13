package es.caib.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import es.caib.sistra.plugins.PluginFactory;
import es.caib.sistra.plugins.firma.PluginFirmaIntf;

/**
 * Constantes y utilidades firma 
 * 
 */
public class FirmaUtil {
	
	/**
	 * Charset empleado para convertir a bytes las cadenas
	 */
	public final static String CHARSET = "UTF-8";
	
	/*  
	 * FORMATOS DE CONTENT TYPE DE LA CAIB UTILIZADOS
	 */
	public final static String CAIB_REGISTRE_ENTRADA_SIGNATURE_CONTENT_TYPE = "contentType.registroEntrada";
	public final static String CAIB_ACUSE_NOTIFICACIO_CONTENT_TYPE = "contentType.acuseNotificacion";
	public final static String CAIB_JUSTIFICANT_ENTRADA_CONTENT_TYPE = "contentType.justificanteEntrada";
	public final static String CAIB_JUSTIFICANT_EIXIDA_CONTENT_TYPE = "contentType.justificanteSalida";
	public final static String CAIB_DOCUMENT_NOTIFICACIO_CONTENT_TYPE = "contentType.documentoNotificacion";
	
	// Cacheamos los tipos de content type
	private final static Map contentTypesCAIB = new HashMap();
	
	/*
	 * PARAMETROS PLUGIN CAIB
	 * 
	 */
	public final static String CAIB_PARAMETER_PIN = "pin";
	public final static String CAIB_PARAMETER_CONTENT_TYPE = "contentType";
	
	
	/*
	 * PARAMETROS PLUGIN AFirma
	 * 
	 */
	public final static String AFIRMA_PARAMETER_ARCHIVO = "archivo";
	
	
	/**
	 * Prepara una cadena para ser firmada. La convierte a bytes con el charset UTF8
	 * @param cadena
	 * @return InputStream
	 * @throws Exception 
	 */
	public static InputStream cadenaToInputStream(String cadena) throws Exception{
		InputStream is = new ByteArrayInputStream(cadena.getBytes(FirmaUtil.CHARSET));
		return is;
	}
	
	/**
	 * Verifica si el plugin es de CAIB (busca metodo getContentType).
	 * @return
	 * @throws Exception
	 */
	public static boolean isPluginCAIB() throws Exception{
		boolean result = false;
		PluginFirmaIntf plgFirma = PluginFactory.getInstance().getPluginFirma();
		Method[] methods = plgFirma.getClass().getDeclaredMethods();
		if (methods != null){
			for (int i=0; i<methods.length; i++){
				if (methods[i].getName().equals("getContentType")) {
					result = true;
					break;
				}
			}
		}
		return result;
	}
	
	
	/**
	 * Obtiene tipo de content type de CAIB. Solo para plugin de CAIB.
	 * @param tipoContentType
	 * @return
	 * @throws Exception 
	 */
	public static String obtenerContentTypeCAIB(String tipoContentType) throws Exception{
		
		if (!contentTypesCAIB.containsKey(tipoContentType)){
			//	Invocamos por reflection al metodo de obtener content type del plugin de firma caib
			PluginFirmaIntf plgFirma = PluginFactory.getInstance().getPluginFirma();				
			Method m = null;
			try{
				Class[] params = new Class[] {String.class};
				m = plgFirma.getClass().getDeclaredMethod("getContentType", params);
			}catch (java.lang.NoSuchMethodException ex){
				throw new Exception ("El plugin no tiene la funcion getContentType");
			}
			Object[] values = new Object[] {tipoContentType};
			Object result = m.invoke(plgFirma,values);
			
			contentTypesCAIB.put(tipoContentType,(String) result);
			
		}
		
		return  (String) contentTypesCAIB.get(tipoContentType);
		 
	}
	
		
}
