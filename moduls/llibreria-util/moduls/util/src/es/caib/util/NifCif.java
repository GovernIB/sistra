package es.caib.util;

import java.util.HashMap;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author Pablo
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class NifCif {

	private static Log log = LogFactory.getLog(NifCif.class);
	
	public static final int DOCUMENTO_NO_VALIDO = -1;
	public static final int TIPO_DOCUMENTO_NIF = 1;
	public static final int TIPO_DOCUMENTO_CIF = 2;
	public static final int TIPO_DOCUMENTO_NIE = 3;
	public static final int TIPO_DOCUMENTO_NSS = 4;
	public static final int TIPO_DOCUMENTO_PASAPORTE = 5;
	
	//public static String LETRAS_ESPECIAL = "ABCDEFGHIJ";
	//public static String SIN_NIF_ESPECIAL = "[K|L][0-9]{7}[" + LETRAS_ESPECIAL + "]{1}";
	
	public static String LETRAS = "TRWAGMYFPDXBNJZSQVHLCKE";	
	public static String SIN_NIF = "[K|L|M]?[0-9]{0,8}[" + LETRAS + "]{1}";	
	public static String SIN_CIAS = "[0-9]{0,13}[" + LETRAS + "]{1}";
	public static String SIN_CIF = "[ABCDEFGHJKLMNPQRSUVW]{1}[0-9]{7}([0-9]||[ABCDEFGHIJ]){1}";
	
	// TODO PASAPORTE VERIFICAR EXP REGULAR
	public static String SIN_PASAPORTE_PREFIX = "[A-Z]{3}/"; // CODPAIS/ 
	public static String SIN_PASAPORTE = SIN_PASAPORTE_PREFIX + ".{1,20}"; // Formato CODPAIS/NUMPASAPORTE  CODPAIS (3 letras) y NUMPASAPORTE (20 caracteres)
	
	//public static String SIN_NIE = "^\\s*[X|Y][\\/|\\s\\-]?[0-9]{1,8}[\\/|\\s\\-]?[A-Z]{1}\\s*$";
	public static String SIN_NIE = "[X|Y|Z][0-9]{1,7}[A-Z]{1}";
	public static String SIN_SS = "^\\s*[0-9]{2}[\\/|\\s\\-]?[0-9]{7,8}[\\/|\\s\\-]?[0-9]{2}\\s*$";
	
	public static HashMap sociedad = new HashMap();

	public NifCif() {}

	static {
		sociedad.put("A", "Sociedades Anónimas");
		sociedad.put("B", "Sociedades de responsabilidad limitada");
		sociedad.put("C", "Sociedades colectivas");
		sociedad.put("D", "Sociedades comanditarias");
		sociedad.put("E", "Comunidades de bienes");
		sociedad.put("F", "Sociedades cooperativas");
		sociedad.put("G", "Asociaciones y otros tipos no definidos");
		sociedad.put("H", "Comunidades de propietarios");
		sociedad.put("J", "Sociedades Civiles con o sin personalidad jurídica");
		sociedad.put("K", "seguramente para compatibilidad con formatos antiguos");
		sociedad.put("L", "seguramente para compatibilidad con formatos antiguos");
		sociedad.put("M", "seguramente para compatibilidad con formatos antiguos");
		sociedad.put("N", "Entidades extranjeras");
		sociedad.put("P", "Corporaciones locales");
		sociedad.put("Q", "Organismos autónomos");
		sociedad.put("R", "Congregaciones e instituciones religiosas");
		sociedad.put("S", "Organos de la administración");
		sociedad.put("U", "Uniones temporales de Empresas");
		sociedad.put("V", "Otros tipos no definidos");
		sociedad.put("X", "Extranjeros");
		sociedad.put("Y", "Extranjeros");
	}

	/**
	 * Obtiene los dígitos de una cadena
	 * 
	 * @param valor
	 * @return
	 */
	private static String getDigitos(String cadena) {
		String digitos = new String();

		for (int i = 0; i < cadena.length(); i++) {
			char c = cadena.substring(i, i + 1).toCharArray()[0];
			if (Character.isDigit(c))
				digitos += c;
		}

		return digitos;
	}

	/**
	 * Obtiene las letras de una cadena
	 * 
	 * @param valor
	 * @return
	 */
	private static String getLetras(String cadena) {
		String letras = new String();

		for (int i = 0; i < cadena.length(); i++) {
			char c = cadena.substring(i, i + 1).toCharArray()[0];
			if (Character.isLetter(c))
				letras += c;
		}

		return letras;
	}

	/**
	 * Determina si es un NIF
	 * 
	 * @param valor
	 * @return
	 */
	public static boolean esNIF(String valor) {
		try {
			if ( !Pattern.matches(SIN_NIF, valor)) return false;
			
			if (valor.startsWith("K") || valor.startsWith("L") || valor.startsWith("M")) {
				valor = "0" + valor.substring(1);
			}
			
			String letra = getLetras(valor);
			String letraNIF = getLetraNIF(  getDigitos(valor) );
			if ( letraNIF == null )
				return false;

			return letraNIF.equals(letra);
		} catch (Exception e) {
			log.error(e);
		}
		return false;
	}
	
	private static String getLetraNIF( String DNINumerico )
	{
		int DNI = Integer.parseInt( DNINumerico, 10 );
		int baremo;

		String letras = LETRAS;
		baremo = DNI % 23;
		/*
		if (baremo == 0)
			return null;
		*/
		return letras.substring(baremo, baremo + 1);

	}

	/**
	 * Determina si es un CIF
	 * 
	 * @param valor
	 * @return
	 */
	public static boolean esCIF(String valor) {
		try {
			//valor = valor.replaceAll("[\\/\\s\\-]", "" );
			if ( !Pattern.matches(SIN_CIF, valor)) return false;

			String codigoControl = valor.substring(valor.length() - 1, valor.length());

			int[] v1 = { 0, 2, 4, 6, 8, 1, 3, 5, 7, 9 };
			String[] v2 = { "J", "A", "B", "C", "D", "E", "F", "G", "H", "I"};
			int suma = 0;

			for (int i = 2; i <= 6; i += 2) {
				suma += v1[Integer.parseInt(valor.substring(i - 1, i))];
				suma += Integer.parseInt(valor.substring(i, i + 1));
			}

			suma += v1[Integer.parseInt(valor.substring(7, 8))];

			suma = (10 - (suma % 10));

			if (suma == 10)
				suma = 0;						
			
			if (codigoControl.equals("" + suma)
					|| codigoControl.toUpperCase().equals( v2[suma] ) )
				return true;
		} catch (Exception e) {
			log.error(e);
		}

		return false;
	}
	
	public static boolean esNIE( String valor )
	{
		try
		{
			if ( !Pattern.matches(SIN_NIE, valor)) return false;
			//valor = valor.replaceAll("[\\/\\s\\-]", "" );
			return esNIEDC(valor);
		}
		catch( Exception exc )
		{
			log.error(exc);
		}
		return false;
	}
	
	
	public static boolean esPasaporte( String valor )
	{
		boolean res = false;
		try
		{
			res = Pattern.matches(SIN_PASAPORTE, valor);			
		}
		catch( Exception exc )
		{
			log.error(exc);
		}
		return res;
	}
	
	private static boolean esNIEDC( String valor )
	{
		if ( !Pattern.matches( "^[X|Y|Z]{1}[0-9]{1,8}[" + LETRAS + "]{1}$", valor ) )
		{
			return false;
		}
		String numero =  valor.replaceAll("[a-zA-Z]", "");
		
		if (valor.startsWith("Y")) numero = "1" + numero;
		if (valor.startsWith("Z")) numero = "2" + numero;		
		
		String letra = valor.substring(1).replaceAll("[^a-z^A-Z]", "");
		if (!letra.equals( getLetraNIF(numero) ) )
		{
			return false;
		}
		return true;
	}
	
	public static boolean esNumeroSeguridadSocial( String valor )
	{
		try
		{
			if ( !Pattern.matches(SIN_SS, valor)) return false;
			valor = valor.replaceAll("[\\/\\s\\-]", "" );
			return esNumeroSSDC( valor ); 
		}
		catch( Exception exc )
		{
			log.error(exc);
		}
		return false;
	}
	
	private static boolean esNumeroSSDC( String valor )
	{
		if (! Pattern.matches(  "^[0-9]{11,12}$", valor ) )
		{
			return false;
		}

		/*
		if ( valor.charAt(0) == '0' )
		{
			valor = valor.substring( 1 );
		}
		*/
	
		String part1 =  valor.substring(0,2);
		String part2 =  valor.substring( 2, valor.length() - 2 );
		String part3 =  valor.substring( valor.length() - 2 );		
		
		if ( part2.length() == 8 && part2.charAt( 0 ) == '0' )
		{
			part2 = part2.substring( 1 );
		}
		
		String numero = part1 + part2;
		String dc = part3;
		
	
		
		//int iNumero = Integer.parseInt( numero, 10 );
		//int iDc = Integer.parseInt( dc, 10 );
		long iNumero = Long.parseLong( numero, 10 );
		long iDc = Long.parseLong( dc, 10 );
		
				
		
		if (iNumero % 97 != iDc)
		{
			return false;
		}

		//return nuevaValidacionSS( valor );
		return true;
	}
	
	public static int validaDocumento( String valor )
	{
		if ( esNIF( valor ) ) return TIPO_DOCUMENTO_NIF;
		if ( esCIF( valor ) ) return TIPO_DOCUMENTO_CIF;
		if ( esNIE( valor ) ) return TIPO_DOCUMENTO_NIE;
		if ( esNumeroSeguridadSocial( valor ) ) return TIPO_DOCUMENTO_NSS;
		if ( esPasaporte(valor)) return TIPO_DOCUMENTO_PASAPORTE;
		return DOCUMENTO_NO_VALIDO;
	}
	
	/**
	 * Extrae número pasaporte CODPAIS/NUMPASAPORTE a partir del formato interno usado por Sistra (P/CODPAIS/NUMPASAPORTE).
	 * @param pasaporte
	 * @return
	 */
	public static String obtieneNumeroPasaporte(String pasaporte) {
		String numPasaporte = pasaporte.substring(2);
		return numPasaporte;
	}
	

	/**
	 * Normaliza documento de identificación pasando a mayusculas y quitando espacios,/,\ y -
	 * @param doc
	 * @return
	 */
	public static String normalizarDocumento(String nif){
		String doc = null;		
        if (nif != null && !StringUtils.isEmpty(nif)) {
        	
        	// Pasamos a mayusculas y hacemos trim
        	doc = nif.toUpperCase();
        	doc = doc.trim();
        	
        	if (Pattern.matches(SIN_PASAPORTE_PREFIX, doc.substring(0, 4))) {
            	// TODO PASAPORTE Ver si es necesaria alguna normalizacion
            	doc = doc;
            } else {
	        	// Quitamos espacios y otros caracteres            
	            doc = doc.replaceAll("[\\/\\s\\-]", "");
	            // Rellenamos con 0
	            if (doc.length() == 0) {
	            	return "";
	            }            
	            if (Pattern.matches("[^A-Z]", doc.substring(0, 1))) {
	                // Es nif
	                doc = StringUtils.leftPad(doc, 9, '0');
	            } else {
	                // es cif o nie
	                final String letraInicio = doc.substring(0, 1);
	                final String resto = doc.substring(1);
	                doc = letraInicio + StringUtils.leftPad(resto, 8, '0');
	            }
            }
        }
        return doc;
	}
	
	/**
	 * Determina si es un CIAS. Es como un NIF pero con 13 digitos
	 * 
	 * @param valor
	 * @return
	 */
	public static boolean esCIAS(String valor) {
		try {
			if ( !Pattern.matches(SIN_CIAS, valor)) return false;
			
			String letra = getLetras(valor);

			String letraNIF = getLetraNIF(  getDigitos(valor) );
			if ( letraNIF == null )
				return false;

			return letraNIF.equals(letra);

		} catch (Exception e) {
			log.error(e);			
		}
		return false;
	}
	
}


