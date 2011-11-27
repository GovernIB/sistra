package es.caib.util;

import java.util.HashMap;
import java.util.regex.Pattern;

/**
 * 
 * @author Pablo
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class NifCif {

	
	public static final int DOCUMENTO_NO_VALIDO = -1;
	public static final int TIPO_DOCUMENTO_NIF = 1;
	public static final int TIPO_DOCUMENTO_CIF = 2;
	public static final int TIPO_DOCUMENTO_NIE = 3;
	public static final int TIPO_DOCUMENTO_NSS = 4;
	
	public static String LETRAS = "TRWAGMYFPDXBNJZSQVHLCKE";
	public static String SIN_NIF = "[0-9]{0,8}[" + LETRAS + "]{1}";
	public static String SIN_CIAS = "[0-9]{0,13}[" + LETRAS + "]{1}";
	public static String SIN_CIF = "[ABCDEFGHJKLMNPQRSUVW]{1}[0-9]{7}([0-9]||[ABCDEFGHIJ]){1}";	                                 
	//public static String SIN_NIE = "^\\s*[X|Y][\\/|\\s\\-]?[0-9]{1,8}[\\/|\\s\\-]?[A-Z]{1}\\s*$";
	public static String SIN_NIE = "[X|Y][0-9]{1,8}[A-Z]{1}";
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
			
			String letra = getLetras(valor);

			String letraNIF = getLetraNIF(  getDigitos(valor) );
			if ( letraNIF == null )
				return false;

			return letraNIF.equals(letra);

		} catch (Exception e) {
			e.printStackTrace();
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
			String[] v2 = { "J", "A", "B", "C", "D", "E", "F", "G", "H", "I" };
			int suma = 0;

			for (int i = 2; i <= 6; i += 2) {
				suma += v1[Integer.parseInt(valor.substring(i - 1, i))];
				suma += Integer.parseInt(valor.substring(i, i + 1));
			}

			suma += v1[Integer.parseInt(valor.substring(7, 8))];

			suma = (10 - (suma % 10));

			if (suma == 10)
				suma = 0;
			
			//System.out.println( "DC [" + codigoControl + "] se coge [" + v2[suma] + "]"  );
			
			if (codigoControl.equals("" + suma)
					|| codigoControl.toUpperCase().equals( v2[suma] ) )
				return true;
		} catch (Exception e) {
			e.printStackTrace();
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
			exc.printStackTrace();
		}
		return false;
	}
	
	private static boolean esNIEDC( String valor )
	{
		if ( !Pattern.matches( "^[X|Y]{1}[0-9]{1,8}[" + LETRAS + "]{1}$", valor ) )
		{
			return false;
		}
		String numero =  valor.replaceAll("[a-zA-Z]", "");
		
		if (valor.startsWith("Y")) numero = "1" + numero;
		if (valor.startsWith("Z")) numero = "2" + numero;		
		
		String letra = valor.substring(1).replaceAll("[^a-z^A-Z]", "");
		//System.out.println ( letra + ": " + getLetraNIF(numero) );
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
			exc.printStackTrace();
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
		//System.out.print( valor + " : :" );
		String part1 =  valor.substring(0,2);
		String part2 =  valor.substring( 2, valor.length() - 2 );
		String part3 =  valor.substring( valor.length() - 2 );
		
		//System.out.println( "part1 " + part1 + " part2: " + part2 + " part3 " + part3 );
		
		if ( part2.length() == 8 && part2.charAt( 0 ) == '0' )
		{
			part2 = part2.substring( 1 );
		}
		
		String numero = part1 + part2;
		String dc = part3;
		

		
		//System.out.println( "NUMERO [" + numero + "]" );
		
		//int iNumero = Integer.parseInt( numero, 10 );
		//int iDc = Integer.parseInt( dc, 10 );
		long iNumero = Long.parseLong( numero, 10 );
		long iDc = Long.parseLong( dc, 10 );
		
				
		// System.out.println( "numero " + numero + "dc: " + dc + " dc calculado " + ( iNumero % 97 ) );
		
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
		return DOCUMENTO_NO_VALIDO;
	}
	

	/**
	 * Normaliza documento de identificación pasando a mayusculas y quitando espacios,/,\ y -
	 * @param doc
	 * @return
	 */
	public static String normalizarDocumento(String doc){
		//TODO QUITAR CEROS AL PRINCIPIO??		
		doc = doc.toUpperCase();
		return doc.replaceAll("[\\/\\s\\-]", "" );
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
			e.printStackTrace();
		}
		return false;
	}
	
}


