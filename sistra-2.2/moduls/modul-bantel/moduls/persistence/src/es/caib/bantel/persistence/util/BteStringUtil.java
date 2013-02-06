package es.caib.bantel.persistence.util;


/**
 * Utilidades con cadenas
 *
 */
public class BteStringUtil {

	/**
	 * Devuelve modelo de un identificador con formato: modelo-version
	 * @param identificador
	 * @return modelo
	 */
	public static String getModelo(String identificador){
		int pos = identificador.lastIndexOf('-');
		return identificador.substring(0,pos);
	}
	
	/**
	 * Devuelve version de un identificador con formato: modelo-version
	 * @param identificador
	 * @return version
	 */
	public static  int getVersion(String identificador){
		int pos = identificador.lastIndexOf('-');
		return Integer.parseInt(identificador.substring(pos + 1));
	}
	
	/**
	 * Convierte un array de numeros de entradas en un String formateado según un separador 
	 * @param numeroEntradas
	 * @return String
	 */
	public static String numeroEntradasToString(String numeroEntradas[]){
		StringBuffer sb = new StringBuffer(numeroEntradas.length * 25);
		for (int i=0;i<numeroEntradas.length;i++){
			sb.append(numeroEntradas[i]);
			if (i<numeroEntradas.length - 1){
				sb.append(":-:");
			}
		}
		return sb.toString();
	}
	
	/**
	 * Convierte un array de numeros de entradas en un String formateado según un separador 
	 * @param numeroEntradas
	 * @return String
	 */
	public static String numeroEntradasAddEntrada(String numeroEntradas,String numeroEntrada){		
		if (numeroEntradas == null || numeroEntradas.length() <= 0) return numeroEntrada;
		return numeroEntradas + ":-:" + numeroEntrada;
	}
	
	/**
	 * Convierte un String formateado según un separador en un array de numeros de entradas
	 * @param numeroEntradas
	 * @return String Devuelve null si no hay entradas
	 */
	public static String[] stringToNumeroEntradas(String numeroEntradas){
		if (numeroEntradas == null || numeroEntradas.length() <= 0) return null;
		return numeroEntradas.split(":-:");	
	}
	
}
