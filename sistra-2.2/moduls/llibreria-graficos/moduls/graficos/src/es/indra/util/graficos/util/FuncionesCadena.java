package es.indra.util.graficos.util;

import java.io.File;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Locale;
import java.util.Date;
import java.util.Vector;

import java.text.DecimalFormatSymbols;
import java.text.DecimalFormat;
import java.text.NumberFormat;



/**
 * @author user
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class FuncionesCadena {

	public static String generaVentanaMensaje(String as_mensaje, String as_boton, String as_estilo)
	{
	  String ls_respuesta = "	<html>\n"+
		  "<head>\n"+
		  "	<title>Información</title>\n"+
		  "	<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">\n"+
		  "	<meta http-equiv=\"Cache-Control\" content=\"no-cache\">\n"+
		  "	<meta http-equiv=\"Pragma\" content=\"no-cache\">\n"+
		  "	<link rel=\"stylesheet\" href=\""+ as_estilo +"\" type=\"text/css\"/>\n"+
		  "</head>\n"+
		  "<body>\n"+
		  "<div align=\"center\">\n "+
		  "	<form name=\"confirmar\" method=\"post\">\n"+
		  "		<pre class=\"texto\">"+ as_mensaje + "</pre>\n"+
		  "		<input class=\"enviar\" type=\"button\" name=\"cancelar\" value=\"" + as_boton + "\" onClick=\"window.close();\">\n"+
		  "	</form>\n"+
		  "</div>\n "+

		  "</body>\n"+
		  "</html>";	 
	
	  return ls_respuesta; 
	}

  public static String generaCapaMensaje(String as_mensaje, String as_boton, String as_estilo)
  {
	String ls_respuesta = 
		"<div align=\"center\">\n "+
		"	<form name=\"confirmar\" method=\"post\">\n"+
		"		<pre class=\"texto\">"+ as_mensaje + "</pre>\n"+
		"		<input class=\"enviar\" type=\"button\" name=\"cancelar\" value=\"" + as_boton + "\" onClick=\"window.close();\">\n"+
		"	</form>\n"+
		"</div>\n ";	 
	
	return ls_respuesta; 
  }
  
  public static String generaVentanaVolver(String as_mensaje, String as_boton, String as_estilo)
	  {
		String ls_respuesta = "	<html>\n"+
			"<head>\n"+
			"	<title>Información</title>\n"+
			"	<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">\n"+
			"	<meta http-equiv=\"Cache-Control\" content=\"no-cache\">\n"+
			"	<meta http-equiv=\"Pragma\" content=\"no-cache\">\n"+
			"	<link rel=\"stylesheet\" href=\""+ as_estilo +"\" type=\"text/css\"/>\n"+
			"</head>\n"+
			"<body>\n"+
			"<div align=\"center\">\n "+
			"	<form name=\"confirmar\" method=\"post\">\n"+
			"		<pre class=\"texto\">"+ as_mensaje + "</pre>\n"+
			"		<input class=\"enviar\" type=\"button\" name=\"volver\" value=\"" + as_boton + "\" onClick=\"history.go(-1);\">\n"+
			"	</form>\n"+
			"</div>\n "+

			"</body>\n"+
			"</html>";	 
	
		return ls_respuesta; 
	  }

  /**
   * Método usada para reemplazar todas las ocurrencias de determinada cadena de texto
   * por otra cadena de texto
   * @param String s de texto origen
   * @param String one Fragmento de texto a reemplazar
   * @param String another Fragmento de texto con el que se reemplaza
   * @return String Cadena de texto con el reemplazo de cadenas completado
   **/
  public static String replace(String s, String one, String another)
  throws Throwable 
  {
  // En el String 's' sustituye 'one' por 'another'
    if (s == null) 
    {
    	if (one == null && another != null)
    	{
    		return another; 
    	}
    	return null;
    }
     
     
   	
	if (s.length() == 0) 
	{
		if (one != null && one.length() == 0)
		{
			return another; 
		}
		return "";
	} 
	
	if (one == null || one.length()==0)
	{
		return s;
	}

	
	String res = "";
    int i = s.indexOf(one,0);
    int lastpos = 0;
    while (i != -1) {
      res += s.substring(lastpos,i) + another;
      lastpos = i + one.length();
      i = s.indexOf(one,lastpos);
    }
    res += s.substring(lastpos);  // the rest
    return res;
  }

  /**
   * Método usada para reemplazar la última ocurrencia de determinada cadena de texto
   * por otra cadena de texto
   * @param String s de texto origen
   * @param String one Fragmento de texto a reemplazar
   * @param String another Fragmento de texto con el que se reemplaza
   * @return String Cadena de texto con el reemplazo de cadenas completado
   **/
  public static String replaceLast(String s, String one, String another) {
  // En el String 's' sustituye 'one' por 'another'
    if (s.equals("")) return "";
    String res = "";
    int i = s.lastIndexOf(one,0);
    int lastpos = 0;
    if (i != -1) {
      res += s.substring(lastpos,i) + another;
      lastpos = i + one.length();
      i = s.indexOf(one,lastpos);
    }
    res += s.substring(lastpos);  // the rest
    return res;
  }

  public static String rpad(String as_texto, int ai_longMinima, char ac_relleno)
  {
	if (as_texto == null) return as_texto;
	if (as_texto.length() < ai_longMinima)
	for (int i=0; i< ai_longMinima - as_texto.length();i++)
	{
		as_texto += ac_relleno ;
	}
	return as_texto;
  }
  
  public static String lpad(String as_texto, int ai_longMinima, char ac_relleno)
  {
  	if (as_texto == null) return as_texto;
  	if (as_texto.length() < ai_longMinima)
  	for (int i=0; i< ai_longMinima - as_texto.length();i++)
  	{
  		as_texto = ac_relleno +as_texto;
  	}
  	return as_texto;
  }

  public static int trunc(float numero)
  {
	  //	X = (!X ? 2 : X);
	  String j = String.valueOf(numero);
	  if (j == null) return 0; 
	  int posPunto = j.indexOf('.');
	  if (posPunto < 0) return new Integer(j).intValue();
	  if (posPunto == 0) return 0;
	  return Integer.parseInt(j.substring(0, posPunto));
  }
  
  public static String arreglaNormalizacion(String as_texto)
  {  	
	  String ls_res = as_texto;
	   if (ls_res == null) return ls_res;
	   //int li_inicioNorm = ls_res.lastIndexOf("&#");
	   int li_inicioNorm = ls_res.lastIndexOf("&");
	   int li_finNorm = ls_res.lastIndexOf(";");
	 
	   if (li_inicioNorm > -1)
	   {
		  li_finNorm = ls_res.lastIndexOf(";");

		  if (li_finNorm <= li_inicioNorm)
		  {
			  ls_res = ls_res.substring(0, li_inicioNorm);
		  }
	   }  	
	   return ls_res;
  }
  
	/**
	 * Normaliza el texto pasado como parámetro, sustituyendo caracteres correspondientes a letras 
	 * acentuadas, 
	 * que pueden ser interpretados incorrectamente, convirtiendo dichos caracteres al
	 * estándar ISO-8859-1
	 * @param String as_texto Cadena de texto a normalizar
	 * @return String Cadena de texto normalizada
	 **/
	public static String normalizaAcentos(String as_texto)
	throws Throwable
	{

		String ls_textoNormalizado = as_texto;
		
		// Mayúsculas con acento grave
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "À","&#192;");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "È","&#200;");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "Ì","&#204;");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "Ò","&#210;");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "Ù","&#217;");

		// Mayúsculas con acento agudo
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "Á","&#193;");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "É","&#201;");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "Í","&#205;");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "Ó","&#211;");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "Ú","&#218;");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "Ý","&#221;");

		// Minúsculas con acento grave
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "à","&#224;");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "è","&#232;");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "ì","&#236;");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "ò","&#242;");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "ù","&#249;");
		
		// Minúsculas con acento agudo
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "á","&#225;");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "é","&#233;");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "í","&#237;");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "ó","&#243;");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "ú","&#250;");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "ý","&#253;");

		// Minúsculas con diéresis
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "ä","&#228;");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "ë","&#235;");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "ï","&#239;");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "ö","&#246;");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "ü","&#252;");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "ÿ","&#255;");

		// Mayúsculas con diéresis
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "Ä","&#196;");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "Ë","&#203;");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "Ï","&#207;");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "Ö","&#214;");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "Ü","&#220;");
		
		
		// Mayúsculas con acento ^
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "Â","&#194;");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "Ê","&#202;");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "Î","&#206;");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "Ô","&#212;");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "Û","&#219;");
		
		// Minúsculas con acento ^
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "â","&#226;");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "ê","&#234;");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "î","&#238;");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "ô","&#244;");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "û","&#251;");
		
		// Mayusculas con tilde
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "Ã","&#195;");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "Õ","&#213;");
				
		// Minusculas con tilde
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "ã","&#227;");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "õ","&#245;");
		
		// Otros
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "Å","&#197;");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "å","&#229;");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "ð","&#240;");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "õ","&#245;");
		
		return ls_textoNormalizado;
	
	}
	
	/**
	 * Quita los acentos del texto
	 * @param String as_texto Cadena de texto a normalizar
	 * @return String Cadena de texto normalizada
	 **/
	public static String quitaAcentos(String as_texto)
	throws Throwable
	{

		String ls_textoNormalizado = as_texto;
		
		// Mayúsculas con acento grave
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "À","A");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "È","E");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "Ì","I");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "Ò","O");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "Ù","U");


		// Mayúsculas con acento agudo
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "Á","A");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "É","E");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "Í","I");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "Ó","O");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "Ú","U");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "Ý","Y");

		// Minúsculas con acento grave
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "à","a");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "è","e");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "ì","i");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "ò","o");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "ù","u");

		
		// Minúsculas con acento agudo
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "á","a");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "é","e");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "í","i");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "ó","o");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "ú","u");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "ý","y");

		// Minúsculas con diéresis
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "ä","a");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "ë","e");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "ï","i");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "ö","o");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "ü","u");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "ÿ","y");

		// Mayúsculas con diéresis
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "Ä","A");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "Ë","E");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "Ï","I");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "Ö","O");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "Ü","U");
		
		
		// Mayúsculas con acento ^
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "Â","A");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "Ê","E");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "Î","I");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "Ô","O");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "Û","U");
		
		// Minúsculas con acento ^
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "â","a");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "ê","e");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "î","i");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "ô","o");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "û","u");
		
		// Mayusculas con tilde
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "Ã","A");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "Õ","O");
				
		// Minusculas con tilde
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "ã","a");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "õ","o");
		
		// Otros
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "Å","A");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "å","a");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "ð","o");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "õ","o");
		
		return ls_textoNormalizado;	

	}  		

	/**
	 * Normaliza el texto pasado como parámetro, sustituyendo caracteres correspondientes a letras 
	 * acentuadas en el estándar ISO-8859-1 por sus correspondientes caracteres
	 * @param String as_texto Cadena de texto a denormalizar
	 * @return String Cadena de texto denormalizada
	 **/
	public static String denormalizaAcentos(String as_texto)
	throws Throwable
	{

		String ls_textoNormalizado = as_texto;
		
		// Mayúsculas con acento grave
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado,"&#192;", "À");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado,"&#200;", "È");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado,"&#204;", "Ì");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado,"&#210;", "Ò");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado,"&#217;", "Ù");


		// Mayúsculas con acento agudo
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado,"&#193;", "Á");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado,"&#201;", "É");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado,"&#205;", "Í");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado,"&#211;", "Ó");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado,"&#218;", "Ú");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado,"&#221;", "Ý");

		// Minúsculas con acento grave
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado,"&#224;", "à");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado,"&#232;", "è");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado,"&#236;", "ì");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado,"&#242;", "ò");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado,"&#249;", "ù");

		
		// Minúsculas con acento agudo
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado,"&#225;", "á");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado,"&#233;", "é");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado,"&#237;", "í");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado,"&#243;", "ó");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado,"&#250;", "ú");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado,"&#253;", "ý");

		// Minúsculas con diéresis
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado,"&#228;", "ä");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado,"&#235;", "ë");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado,"&#239;", "ï");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado,"&#246;", "ö");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado,"&#252;", "ü");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado,"&#255;", "ÿ");

		// Mayúsculas con diéresis
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado,"&#196;", "Ä");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado,"&#203;", "Ë");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado,"&#207;", "Ï");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado,"&#214;", "Ö");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado,"&#220;", "Ü");


		// Mayúsculas con acento ^
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "&#194;","Â");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "&#202;","Ê");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "&#206;","Î");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "&#212;","Ô");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "&#219;","Û");
	
		// Minúsculas con acento ^
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "&#226;","â");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "&#234;","ê");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "&#238;","î");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "&#244;","ô");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "&#251;","û");
		
		// Mayusculas con tilde
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "&#195;","Ã");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "&#213;","Õ");
				
		// Minusculas con tilde
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "&#227;","ã");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "&#245;","õ");
		
		// Otros
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "&#197;","Å");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "&#229;","å");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "&#240;","ð");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "&#245;","õ");

		return ls_textoNormalizado;
	
	}  
	
	/**
	 * Normaliza el texto pasado como parámetro, sustituyendo caracteres
	 * que pueden ser interpretados incorrectamente, convirtiendo dichos caracteres al
	 * estándar ISO-8859-1
	 * @param String as_texto Cadena de texto a normalizar
	 * @return String Cadena de texto normalizada
	 **/
	public static String normaliza(String as_texto)
	throws Throwable
	{
		if (as_texto == null || as_texto.length() == 0)
			return as_texto;
		String ls_textoNormalizado = as_texto;
		ls_textoNormalizado = FuncionesCadena.normalizaAcentos(ls_textoNormalizado);
		
		// Comparaciones y otros símbolos
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "&","&#38;");
			// Si lo hemos reemplazado en un caracter normalizado lo deshacemos
			// (Hay q hacerlo pq al hacer el xml.toString se vuelve a normalizar!!!)
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "&#38;#","&#"); 

		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, ">","&#62;");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "<","&#60;");				
				
		// Letras especiales(ñ, ç)				
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "ñ","&#241;");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "Ñ","&#209;");		
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "ç","&#231;");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "Ç","&#199;");
	
		// Otros
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "º","&#186;");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "ª","&#170;");		
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "½","&#189;");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "¼","&#188;");		
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "®","&#174;");				
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "©","&#169;");						
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "«","&#171;");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "»","&#187;");		
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "¿","&#191;");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "¡","&#161;");		
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "¼","&#188;");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "¶","&#182;");				
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "µ","&#181;");						
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "×","&#215;");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "Ø","&#216;");				
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "æ","&#230;");	
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "Æ","&#198;");			
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "£","&#163;");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "Ð","&#208;");				
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "¦","&#166;");	
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "´","&#180;");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "±","&#177;");				
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "¾","&#190;");	
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "÷","&#247;");				
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "·","&#183;");	
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "°","&#176;");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "¹","&#185;");				
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "³","&#179;");										
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "²","&#178;");				
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "¢","&#162;");				
		// ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "?","&#8364;");	
		
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "¥","&#165;");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "§","&#167;");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "¨","&#168;");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "Þ","&#222;");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "ß","&#223;");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "þ","&#254;");
		
		
		
		return ls_textoNormalizado;
	}

	/**
	 * Normaliza el texto pasado como parámetro, sustituyendo caracteres
	 * del estándar ISO-8859-1, convirtiendo dichos caracteres a
	 * caracteres propios
	 * @param String as_texto Cadena de texto a denormalizar
	 * @return String Cadena de texto denormalizada
	 **/
	public static String denormaliza(String as_texto)
	throws Throwable
	{
		String ls_textoNormalizado = as_texto;

		ls_textoNormalizado = FuncionesCadena.denormalizaAcentos(ls_textoNormalizado);
		
		// Comparaciones y otros símbolos
		
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado, "&#38;","&");		
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado,"&#62;", ">");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado,"&#60;", "<");				
				
		// Letras especiales(ñ, ç)				
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado,"&#241;", "ñ");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado,"&#209;", "Ñ");		
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado,"&#231;", "ç");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado,"&#199;", "Ç");
	
		// Otros
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado,"&#186;", "º");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado,"&#170;", "ª");		
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado,"&#189;", "½");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado,"&#188;", "¼");		
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado,"&#174;", "®");				
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado,"&#169;", "©");						
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado,"&#171;", "«");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado,"&#187;", "»");		
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado,"&#191;", "¿");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado,"&#161;", "¡");		
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado,"&#188;", "¼");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado,"&#182;", "¶");				
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado,"&#181;", "µ");						
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado,"&#215;", "×");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado,"&#216;", "Ø");				
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado,"&#230;", "æ");	
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado,"&#198;", "Æ");			
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado,"&#163;", "£");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado,"&#208;", "Ð");				
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado,"&#166;", "¦");	
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado,"&#180;", "´");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado,"&#177;", "±");				
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado,"&#190;", "¾");	
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado,"&#247;", "÷");				
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado,"&#183;", "·");	
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado,"&#176;", "°");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado,"&#185;", "¹");				
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado,"&#179;", "³");										
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado,"&#178;", "²");				
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado,"&#162;", "¢");				
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado,"&#8364;", "?");
		
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado,"&#165;", "¥");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado,"&#167;", "§");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado,"&#168;", "¨");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado,"&#222;", "Þ");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado,"&#223;", "ß");
		ls_textoNormalizado = FuncionesCadena.replace(ls_textoNormalizado,"&#254;", "þ");
		
		return ls_textoNormalizado;
	}

  
  
  public static String getHora(Timestamp a_datetime){
		 	if (a_datetime == null) return "";
			Calendar l_fecha = Calendar.getInstance(new Locale("es","ES"));
			l_fecha.setTime(a_datetime);									

			String ls_hora = String.valueOf(l_fecha.get(Calendar.HOUR_OF_DAY));
			if (ls_hora.length() == 1) ls_hora = "0"+ls_hora;

			String ls_minuto = String.valueOf(l_fecha.get(Calendar.MINUTE));
			if (ls_minuto.length() == 1) ls_minuto = "0"+ls_minuto;		

			String ls_second = String.valueOf(l_fecha.get(Calendar.SECOND));
			if (ls_second.length() == 1) ls_second = "0"+ls_second;	

			return ls_hora+":"+ls_minuto+":"+ls_second;
	}

	public static String convertirFechaMastinAFecha(String as_tipoConexion, String as_fechaMastin)
	throws Throwable
	{
		if (as_tipoConexion != null && as_tipoConexion.equalsIgnoreCase("SQLSERVER"))
		{ 
			return convertirFechaMastinAFechaSQLServer(as_fechaMastin);
		}
		return convertirFechaMastinAFechaOracle(as_fechaMastin);
	}
	  
  	public static String convertirFechaMastinAFechaSQLServer(String as_fechaMastin)
  	throws Throwable
  	{
  		String ls_fecha = FuncionesCadena.getFecha(as_fechaMastin, "YYYYMMDDHH24MISS", "yyyymmdd hh24:mi:ss");
  		ls_fecha = "convert(datetime, '"+ ls_fecha+ "',112)";
  		return ls_fecha; 
  	}

	public static String formateaErrorFichero(String as_mensaje)
	{
		String ls_mensaje = as_mensaje;
		try
		{
			if (ls_mensaje == null || ls_mensaje.trim().length() == 0)
			{
				return as_mensaje;
			}
			ls_mensaje = FuncionesCadena.replace(ls_mensaje, "<b>Método:</b>", "\tMétodo:");
			ls_mensaje = FuncionesCadena.replace(ls_mensaje, "<b>Clase:</b>", "\tClase:");
			ls_mensaje = FuncionesCadena.replace(ls_mensaje, "<b>Archivo Origen:</b>", "\tArchivo Origen:");
			ls_mensaje = FuncionesCadena.replace(ls_mensaje, "<b>Línea:</b>", "\tLínea:");
			ls_mensaje = FuncionesCadena.replace(ls_mensaje, "<b>Mensaje:</b>", "\tMensaje:");
		}
		catch (Throwable ex)
		{
			ls_mensaje = as_mensaje;
		}
		return ls_mensaje;
	}
  	
	public static String convertirFechaMastinAFechaOracle(String as_fechaMastin)
	{
		String ls_fecha = "TO_DATE('"+ as_fechaMastin+ "','YYYYMMDDHH24MISS')";
		return ls_fecha; 
	}  	
  
    public static String getHoraCorta(Timestamp a_datetime){
		 	if (a_datetime == null) return "";
			Calendar l_fecha = Calendar.getInstance(new Locale("es","ES"));
			l_fecha.setTime(a_datetime);									

			String ls_hora = String.valueOf(l_fecha.get(Calendar.HOUR_OF_DAY));
			if (ls_hora.length() == 1) ls_hora = "0"+ls_hora;

			String ls_minuto = String.valueOf(l_fecha.get(Calendar.MINUTE));
			if (ls_minuto.length() == 1) ls_minuto = "0"+ls_minuto;		


			return ls_hora+":"+ls_minuto;
	}
	
	public static String getFecha(Timestamp a_datetime){
		 	if (a_datetime == null) return "";
			Calendar l_fecha = Calendar.getInstance(new Locale("es","ES"));
			l_fecha.setTime(a_datetime);						
							
		String ls_dia = FuncionesCadena.lpad(Integer.toString(l_fecha.get(Calendar.DAY_OF_MONTH)), 2,'0');
			
			
		String ls_mes = FuncionesCadena.lpad(Integer.toString(1 + l_fecha.get(Calendar.MONTH)), 2,'0');		
												
			String ls_anyo = Integer.toString(l_fecha.get(Calendar.YEAR));					
			
			return ls_dia + "/" + ls_mes + "/" + ls_anyo ;
	}

	/**
	 * Obtiene la fecha en formato de cadena utilizada por Mastin: AAAAMMDDHHMM
	 * 
	 */
	public static String getFechaMastin(Timestamp a_datetime)
	throws Throwable
	{
		 	if (a_datetime == null) return "";

			return FuncionesCadena.getFecha(a_datetime, "YYYYMMDDHH24MISS");
		 	
	}

	/** 
	 * Recoge la fecha contenida en el Timestamp a_datetime y la convierte al 
	 * formato de salida formato
	 * @param Timestamp a_datetime Fecha de entrada
	 * @param String as_formato Formato en el que se devuelve la fecha
	 * @return String Cadena que contiene la fecha contenida en a_datetime con el formato as_formatoOUT
	 **/
	public static String getFecha(Timestamp a_datetime, String as_formato)
	throws Throwable
	{
		 	if (a_datetime == null) return "";
			Calendar l_fecha = Calendar.getInstance(new Locale("es","ES"));
			l_fecha.setTime(a_datetime);						
									
			String ls_dia = FuncionesCadena.lpad(Integer.toString(l_fecha.get(Calendar.DAY_OF_MONTH)), 2,'0');
			
			
			String ls_mes = FuncionesCadena.lpad(Integer.toString(1 + l_fecha.get(Calendar.MONTH)), 2,'0');
												
			String ls_anyo = FuncionesCadena.lpad(Integer.toString(l_fecha.get(Calendar.YEAR)), 4,'0');
        
            String ls_anyo2 = ls_anyo.substring(2, 4);     					
			
			String ls_hora24 = FuncionesCadena.lpad(Integer.toString(l_fecha.get(Calendar.HOUR_OF_DAY)), 2,'0');
			
			String ls_minuto = FuncionesCadena.lpad(Integer.toString(l_fecha.get(Calendar.MINUTE)), 2,'0');
			
			String ls_segundo = FuncionesCadena.lpad(Integer.toString(l_fecha.get(Calendar.SECOND)), 2,'0');			
			
			String ls_hora = FuncionesCadena.lpad(Integer.toString(l_fecha.get(Calendar.HOUR)), 2,'0');
			
			//String ls_posMeridian = Integer.toString(l_fecha.get(Calendar.AM_PM));
			
			String ls_res = as_formato.toUpperCase(); 
			ls_res = replace(ls_res, "YYYY", ls_anyo);
            ls_res = replace(ls_res, "YY", ls_anyo2);
			ls_res = replace(ls_res, "MM", ls_mes);
			ls_res = replace(ls_res, "DD", ls_dia);
			ls_res = replace(ls_res, "HH24", ls_hora24);
			ls_res = replace(ls_res, "HH", ls_hora);
			ls_res = replace(ls_res, "MI", ls_minuto);
			ls_res = replace(ls_res, "SS", ls_segundo);
			return ls_res;
	}

	/** 
	 * Recoge la fecha contenida en el Timestamp a_datetime y la convierte al 
	 * formato de salida formato
	 * @param String as_datetime Fecha de entrada en formato de cadena
	 * @return Timestamp Fecha de entrada
	 **/
	public static Timestamp fechaMastinToTimestamp(String as_datetime){
		 	if (as_datetime == null) return null;
		 	Timestamp lf_fecha = null;
			Calendar l_fecha = Calendar.getInstance(new Locale("es","ES"));
			
			try
			{
				// Año
				l_fecha.set(Calendar.YEAR, Integer.parseInt(as_datetime.substring(0,4)));
				// Mes
				l_fecha.set(Calendar.MONTH, Integer.parseInt(as_datetime.substring(4,6)) - 1);				
				// Dia
				l_fecha.set(Calendar.DATE, Integer.parseInt(as_datetime.substring(6,8)));
				// Hora
				l_fecha.set(Calendar.HOUR_OF_DAY, Integer.parseInt(as_datetime.substring(8,10)));
				// Minute
				l_fecha.set(Calendar.MINUTE, Integer.parseInt(as_datetime.substring(10,12)));												
				// Segundo
				l_fecha.set(Calendar.SECOND, Integer.parseInt(as_datetime.substring(12,14)));												
				// MiliSegundo
				l_fecha.set(Calendar.MILLISECOND, 0);									
				
					
				//lf_fecha = new Timestamp(l_fecha.getTimeInMillis());
				lf_fecha = new Timestamp(l_fecha.getTime().getTime());				
				return lf_fecha;
			}
			catch (Throwable e)
			{
				return null;
			}
	}
		

	/** Función que devuelve el código asociado a un determinado número de tabulaciones
	 * @param ai_nivel Número de tabulaciones a generar
	 * @return String Código java asociado al nivel de tabulaciones pasado como parámetro
	 **/
	public static String tabula(int ai_nivel)
	{
		String ls_tab  = "";
		for (int i=0; i<ai_nivel; i++)
		{
			ls_tab += "\t";
			//ls_tab += "    ";
		}
		return ls_tab;
	}

	public static String getFechaActual(String as_formato)
	throws Throwable
	{
		  return FuncionesCadena.getFecha(new Timestamp(System.currentTimeMillis()), as_formato) +" ";
	}

	/** 
	 * Recoge la cadena as_fecha con el formato de entrada as_formatoIN y la convierte al 
	 * formato de salida formatoOUT
	 * @param String as_fecha Fecha de entrada
	 * @param String as_formatoIN Formato en el que viene la fecha
	 * @param String as_formatoOUT Formato en el que se devuelve la fecha
	 * @return String Cadena que contiene la fecha contenida en as_fecha con el formato as_formatoOUT
	 **/
	public static String getFecha(String as_fecha, String as_formatoIN, String as_formatoOUT)
	throws Throwable
	{
		 	if (as_fecha == null) return "";
			Calendar l_fecha = Calendar.getInstance(new Locale("es","ES"));
		
			if (as_formatoIN.equalsIgnoreCase("YYYYMMDDHH24MI"))
			{
				l_fecha.set(Calendar.DAY_OF_MONTH, Integer.parseInt(as_fecha.substring(6,8)));
				l_fecha.set(Calendar.MONTH, Integer.parseInt(as_fecha.substring(4,6)) - 1);
				l_fecha.set(Calendar.YEAR, Integer.parseInt(as_fecha.substring(0,4)));
				l_fecha.set(Calendar.HOUR_OF_DAY, Integer.parseInt(as_fecha.substring(8,10)));
				l_fecha.set(Calendar.MINUTE, Integer.parseInt(as_fecha.substring(10,12)));												
				
			}
			if (as_formatoIN.equalsIgnoreCase("YYYYMMDDHH24MISS"))
			{
				l_fecha.set(Calendar.DAY_OF_MONTH, Integer.parseInt(as_fecha.substring(6,8)));
				l_fecha.set(Calendar.MONTH, Integer.parseInt(as_fecha.substring(4,6)) - 1);
				l_fecha.set(Calendar.YEAR, Integer.parseInt(as_fecha.substring(0,4)));
				l_fecha.set(Calendar.HOUR_OF_DAY, Integer.parseInt(as_fecha.substring(8,10)));
				l_fecha.set(Calendar.MINUTE, Integer.parseInt(as_fecha.substring(10,12)));												
				l_fecha.set(Calendar.SECOND, Integer.parseInt(as_fecha.substring(12,14)));																
				
			}	
			if (as_formatoIN.equalsIgnoreCase("DD/MM/YYYY"))
			{
						l_fecha.set(Calendar.DAY_OF_MONTH, Integer.parseInt(as_fecha.substring(0,2)));
						l_fecha.set(Calendar.MONTH, Integer.parseInt(as_fecha.substring(3,5)) - 1);
						l_fecha.set(Calendar.YEAR, Integer.parseInt(as_fecha.substring(6,10)));															
				
			}			
			Date ld_fecha = new Date();
			ld_fecha = l_fecha.getTime();
			return FuncionesCadena.getFecha(new Timestamp(ld_fecha.getTime()), as_formatoOUT);
	}
	
		/** 
		 * Recoge la cadena as_fecha con el formato de entrada as_formatoIN y la convierte en un 
		 * Timestamp
		 * @param String as_fecha Fecha de entrada
		 * @param String as_formatoIN Formato en el que viene la fecha		 
		 * @return String Cadena que contiene la fecha contenida en as_fecha con el formato as_formatoOUT
		 **/
		public static Timestamp getFecha(String as_fecha, String as_formatoIN)
		throws Throwable
		{				
				if (as_fecha == null) return null;
				Calendar l_fecha = Calendar.getInstance(new Locale("es","ES"));
		
				if (as_formatoIN.equalsIgnoreCase("YYYYMMDDHH24MI"))
				{
					l_fecha.set(Calendar.DAY_OF_MONTH, Integer.parseInt(as_fecha.substring(6,8)));
					l_fecha.set(Calendar.MONTH, Integer.parseInt(as_fecha.substring(4,6)) - 1);
					l_fecha.set(Calendar.YEAR, Integer.parseInt(as_fecha.substring(0,4)));
					l_fecha.set(Calendar.HOUR_OF_DAY, Integer.parseInt(as_fecha.substring(8,10)));
					l_fecha.set(Calendar.MINUTE, Integer.parseInt(as_fecha.substring(10,12)));
					l_fecha.set(Calendar.SECOND,0);												
					l_fecha.set(Calendar.MILLISECOND,0);
				}else if (as_formatoIN.equalsIgnoreCase("YYYYMMDDHH24MISS"))
				{
					l_fecha.set(Calendar.DAY_OF_MONTH, Integer.parseInt(as_fecha.substring(6,8)));
					l_fecha.set(Calendar.MONTH, Integer.parseInt(as_fecha.substring(4,6)) - 1);
					l_fecha.set(Calendar.YEAR, Integer.parseInt(as_fecha.substring(0,4)));
					l_fecha.set(Calendar.HOUR_OF_DAY, Integer.parseInt(as_fecha.substring(8,10)));
					l_fecha.set(Calendar.MINUTE, Integer.parseInt(as_fecha.substring(10,12)));												
					l_fecha.set(Calendar.SECOND, Integer.parseInt(as_fecha.substring(12,14)));																
					l_fecha.set(Calendar.MILLISECOND,0);
				}else if (as_formatoIN.equalsIgnoreCase("DD/MM/YYYY"))
				{
					l_fecha.set(Calendar.DAY_OF_MONTH, Integer.parseInt(as_fecha.substring(0,2)));
					l_fecha.set(Calendar.MONTH, Integer.parseInt(as_fecha.substring(3,5)) - 1);
					l_fecha.set(Calendar.YEAR, Integer.parseInt(as_fecha.substring(6,10)));															
					l_fecha.set(Calendar.HOUR_OF_DAY, 0);
					l_fecha.set(Calendar.MINUTE, 0);												
					l_fecha.set(Calendar.SECOND, 0);																
					l_fecha.set(Calendar.MILLISECOND,0);
				}else {
					// Formato no soportado
					return null;		
				}
											
				Timestamp l_fec = new Timestamp(l_fecha.getTimeInMillis());
				return l_fec;				
		}
		
	/**
	 * Funcion que devuelve la fecha actual en formato 'nombre del dia', 'dd' de 'nombre del mes' de 'aaaa'
	 * Ejemplo: Miércoles, 1 de Diciembre de 2004 
	 * @param al_fecha
	 * @return
	 */
	public static String getFechaActualLarga()
	{
		Calendar l_fecha = Calendar.getInstance(new Locale("es","ES"));
		int diaSemana = l_fecha.get(Calendar.DAY_OF_WEEK);
		int diaMes = l_fecha.get(Calendar.DAY_OF_MONTH);
		int mes = l_fecha.get(Calendar.MONTH);
		String [] diasArray = new String[] {"Domingo","Lunes","Martes","Miércoles","Jueves","Viernes","Sábado"};
		String [] mesArray = new String[] {"Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"};
		return diasArray[diaSemana-1]+ ", " + diaMes + " de " + mesArray[mes] + " de " + l_fecha.get(Calendar.YEAR);
	}

	/**
	 *  Funcion que devuelve un long con la fecha pasada como parametro truncada al día
	 * @param Timestamp al_fecha Fecha a truncar
	 * @return long Fecha en milisegundos truncada al día
	 **/
	public static long truncaFecha(Timestamp al_fecha)
	{
			Calendar l_truncFecha = Calendar.getInstance();
			l_truncFecha.setTime(new Date(al_fecha.getTime()));
			l_truncFecha.set(Calendar.HOUR_OF_DAY,0);
			l_truncFecha.set(Calendar.MINUTE,0);
			l_truncFecha.set(Calendar.SECOND,0);
			l_truncFecha.set(Calendar.MILLISECOND,0);		
			return (l_truncFecha.getTime().getTime());
	}
	
	/** 
	 * Funcion que para una excepcion determinada y una clase, obtiene el
	 * elemento de la traza asociada a la excepcion producido en la clase
	 * @param Class a_clase Clase en la que se busca el paso de la excepcion
	 * @param Exception a_excepcion Excepcion en la que se busca el elemento 
	 * @return StackTraceElement Elemento de la traza de la excepcion asociado
	 * 										a la clase pasada como parametro
	 **/
	public static String getElementoTraza(Class a_clase, Throwable a_excepcion)
	{
		try
		{
    		String st = "";
			StackTraceElement[] l_traza = StackTraceElement.getStackTrace(a_excepcion);
			int li_tam = l_traza.length -1;

			for (int i=0; i<li_tam;i++)
			{
				if (l_traza[i].getClase().equals(a_clase.getName()))
				{
					st = l_traza[i].toString()+ "\n";			
							return st;
	
				}
			}    		
    		if (!esBlanco(st)) return st;     

   			if (l_traza != null &&
				l_traza.length > 0 &&
				l_traza[0] != null)
			{
				    st = l_traza[0].toString();
			}
			return st;
 		
					
    		
		}
		catch (Throwable ex)
		{			
			ex.printStackTrace();
			return (" No se pudo recuperar el error. " + ex.toString());			
		}
	}
	
	/**
	 * Obtener charset del contexto
	 * @return String Juego de caracteres configurado en la aplicación
	 */
	public static String getCharset()
	{
		try{
			String ls_charset = DatosEntorno.getVariableConfiguracion("charset");
			if (ls_charset != null && ls_charset.length() >0) return ls_charset;
			return "iso-8859-1";
		}catch(Throwable t){
			return "iso-8859-1";
		}
	}
	


	public static String[] splitTrim(String as_cadena, String as_separador)
	throws Throwable
	{
		if (as_cadena == null) return null;
		Vector lv_splitted = new Vector();
		int li_beginIndex = 0;
		int li_endIndex = 0;
		int li_auxIndex = 0; 
		String ls_aux = null;
		boolean lb_inicial = true;
		while (li_beginIndex > -1)
		{
			if (!lb_inicial)
			{
				li_auxIndex = li_beginIndex + as_separador.length();
			}
			lb_inicial = false;
			if (li_auxIndex > as_cadena.length() - 1)
			{
				break;
			}
			li_endIndex = as_cadena.indexOf(as_separador, li_auxIndex);
			if (li_endIndex == -1)
			{
				ls_aux = as_cadena.substring(li_auxIndex);
				lv_splitted.addElement(ls_aux);
				break;
			} 
			ls_aux = as_cadena.substring(li_auxIndex, li_endIndex);
			if (!esBlanco(ls_aux))
				lv_splitted.addElement(ls_aux.trim());
			li_beginIndex = li_endIndex;
			
		}

		if (lv_splitted.size() > 0)
		{
			String[] ls_splitted = new String[lv_splitted.size()];
			for (int i=0; i<lv_splitted.size();i++)
			{
				ls_splitted[i] = (String) lv_splitted.elementAt(i);
			}
			return ls_splitted;
		}
		 
		return null;
	}

	public static String[] split(String as_cadena, String as_separador)
	throws Throwable
	{
		if (as_cadena == null) return null;
		Vector lv_splitted = new Vector();
		int li_beginIndex = 0;
		int li_endIndex = 0;
		int li_auxIndex = 0; 
		String ls_aux = null;
		boolean lb_inicial = true;
		while (li_beginIndex > -1)
		{
			if (!lb_inicial)
			{
				li_auxIndex = li_beginIndex + as_separador.length();
			}
			lb_inicial = false;
			if (li_auxIndex > as_cadena.length() - 1)
			{
				break;
			}
			li_endIndex = as_cadena.indexOf(as_separador, li_auxIndex);
			if (li_endIndex == -1)
			{
				ls_aux = as_cadena.substring(li_auxIndex);
				lv_splitted.addElement(ls_aux);
				break;
			} 
			ls_aux = as_cadena.substring(li_auxIndex, li_endIndex);
			lv_splitted.addElement(ls_aux);
			li_beginIndex = li_endIndex;
			
		}

		if (lv_splitted.size() > 0)
		{
			String[] ls_splitted = new String[lv_splitted.size()];
			for (int i=0; i<lv_splitted.size();i++)
			{
				ls_splitted[i] = (String) lv_splitted.elementAt(i);
			}
			return ls_splitted;
		}
		 
		return null;
	}

	public static boolean esBlanco(String as_valor)
	{
		if (as_valor == null || as_valor.trim().length() == 0)	return true;
		return false;
	}

  public static String deformateaNumero(String as_valorIN, char as_sepDec)
  { 
  		try
  		{
			DecimalFormat es = (DecimalFormat) DecimalFormat.getInstance();
			DecimalFormatSymbols es_sb = new DecimalFormatSymbols();
			es_sb.setDecimalSeparator(as_sepDec);
			es.setDecimalFormatSymbols(es_sb);
			return es.parse(as_valorIN).toString();
  		}
  		catch (Throwable e)
  		{
  			return null;
  		}
  }
  
  
  /**
   * Convierte en un vector de valores una cadena de parametros codificada como:
   * 	
   * 
   * 	{{A1#,#A2}{B1#,#C1[-]C2} ... }   
   * 
   * (Formato old: {{A1#,#A2}{B1#,#B2} ... } )
   * 
   * @param as_cadena Cadena a descomponer
   * @return Vector Vector compuesto por vectores de valores
   */
  public static Vector descomponerCadenaParametros(String as_cadena) {
	
	  try{
		  Vector l_parametros = new Vector();
			
		  // Obtener vector de campos
			  // Eliminamos espacios		
		  String ls_cadena = as_cadena.trim().substring(1,as_cadena.length()-1);
			  // Normalizar para que solo halla un elemento por el que trocear la cadena
		  ls_cadena = FuncionesCadena.replace(ls_cadena,"},","");	
		  ls_cadena = FuncionesCadena.replace(ls_cadena,"}","");
  
			  // Partir la cadena de validaciones
		  String[] l_cadenaParam = FuncionesCadena.split(ls_cadena,"{");				  		 		    		  
		  int li_numeroElementos = l_cadenaParam.length;		 
		  		  				  
		  for (int li_indexElement=1;li_indexElement<li_numeroElementos;li_indexElement++){
			  Vector l_valores = new Vector();			  			  
			  String ls_nextElement = l_cadenaParam[li_indexElement];
			  if ( (ls_nextElement != null) && (ls_nextElement.trim().length() > 0) ){			  			  		
				  String[] l_campos = FuncionesCadena.split(ls_nextElement,"#,#");
				  for (int i=0;i < l_campos.length;i++){
				  	if (l_campos[i].indexOf("[-]") > 0) {				  	
				  	  String[] l_campos2 = FuncionesCadena.split(l_campos[i],"[-]");				  	
				  	  Vector lv_campos2 = new Vector();
				  	  for (int j=0;j < l_campos2.length;j++){				  	  		
				  	  	lv_campos2.add(l_campos2[j]);
				  	  }
				  	  l_valores.add(lv_campos2);
				  	}else{				  	
				  	  	l_valores.add(l_campos[i]);
				  	}
				  	
				  	  
				  }
			  }
			  l_parametros.add(l_valores);				 			  
		  }
		
		  return l_parametros;
	  } catch(Throwable t){
	  	t.printStackTrace();
		  return null;
	  }														
	
  }
  
  /**
   * Devuelve el nombre de documento preparado para asiento registral mastin 
   * (Normalizado y longitud < 50) 
   * @param as_nombre	Nombre documento
   * @param as_ext	Extensión (3 letras)		
   * @return
   */
  public static String getNombreDocumentoMastin(String as_nombre,String as_ext){
	try{	
		// Normalizamos
		String ls_normalizado = FuncionesCadena.normaliza(as_nombre,46);
		return  ls_normalizado + "." + as_ext ;
	}catch (Throwable t){
		return null;
	}
  }
  
  
  /**
   *  Normaliza restringiendo a un tamaño
   * @param as_desc Texto
   * @param ai_tam Tamaño máximo
   * @return
   */
  public static String normaliza(String as_desc,int ai_tam){
  	try{	
		// Normalizamos
		String ls_normalizado = FuncionesCadena.normaliza(as_desc);
		int li_endIndex = ai_tam;
		if (ls_normalizado.length() < ai_tam) {
			li_endIndex = ls_normalizado.length();
		}
		
		// Truncamos
		ls_normalizado = ls_normalizado.substring(0,li_endIndex);
			
		// Arreglamos normalización (por si se queda cortado algún carácter)
		ls_normalizado = FuncionesCadena.arreglaNormalizacion(ls_normalizado);
				 
		return  ls_normalizado ;
	}catch (Throwable t){
		return null;
	}  	
  }
  
  
  
  /**
   * Devuelve la descripcion de documento preparado para asiento registral mastin 
   * (Normalizado y longitud menor 80) 
   * @param as_nombre	Nombre documento		
   * @return
   */
  public static String getDescripcionDocumentoMastin(String as_desc){			
		return FuncionesCadena.normaliza(as_desc,80);
  }
  
  /**
   * Devuelve resumen asunto preparado para asiento registral mastin 
   * (Normalizado y longitud menor de 250) 
   * @param as_nombre	Nombre documento		
   * @return
   */
  public static String getResumenAsuntoMastin(String as_desc){
  	return FuncionesCadena.normaliza(as_desc,250);
  }


  public static String getExtensionFichero(String as_nombre){
	 try{		
		
		int li_ind = 0;
		int li_indiceExt = -1;
								
		// Obtenemos ultimo punto						
		while ((li_ind = as_nombre.indexOf(".",li_ind)) >= 0) {			
			li_indiceExt = li_ind;
			li_ind++;
		} 		
		
		// Si no hay punto o detras del punto no hay nada devolvemos extension vacia
		if (li_indiceExt < 0 || li_indiceExt >= as_nombre.length() - 1)
		{			
			return "";
		}
		
		String ls_extFichero = as_nombre.substring(li_indiceExt + 1).trim();
		if (ls_extFichero.trim().length() == 0){
			return "";
		}
		
		if (ls_extFichero == null)
		{			
			return null;
		}
		
		return ls_extFichero; 
	 }catch (Throwable t){
		 return null;
	 }
   }
   
   /**
	 * Comprueba si el valor pasado como argumento es un NIF.
	 * @param p_valor
	 * @return
	 */
	public static boolean isNIF(String p_valor)
	{
		String ls_cadena = "TRWAGMYFPDXBNJZSQVHLCKET";
		
		// comprobamos q tenga la longitud correcta
		if (p_valor.length()!=9)
			return false;
		
		String ls_dni = p_valor.substring(0,8);
		String ls_letra = p_valor.substring(8,9).toUpperCase();
		
		// q la parte del dni sea numerica
		int li_dni;
		try{
			li_dni = Integer.parseInt(ls_dni);
		} catch (NumberFormatException e){
			return false;
		}
		
		int li_posicion = li_dni % 23;
		
		if (!ls_letra.equals(ls_cadena.subSequence(li_posicion,li_posicion+1)))
		{
			return false;
		}
		return true;
	}	
	
	/**
	 * Comprueba si el valor pasado como argumento es un CIF.
	 * @param p_valor
	 * @return
	 */
	public static boolean isCIF(String p_valor)
	{
		// comprobamos q tenga la longitud correcta
		if (p_valor.length()!=9)
			return false;
		
		// El primer caracter ha de ser uno de esos ABCDEFGHKLMNPQS
		if ("ABCDEFGHKLMNPQS".indexOf(p_valor.substring(0,1)) == -1)
			return false;
		
		// Los siete siguiente digitos seran numeros
		for (int i=1; i<8; i++)
		{
			if ("0123456789".indexOf(p_valor.substring(i,i+1)) == -1)
				return false;
		}
		
		// El digito de control será uno de estos caracteres ABCDEFGHIJ0123456789
		if ("ABCDEFGHIJ0123456789".indexOf(p_valor.substring(8,9)) == -1)
			return false;
		
		int[] l_nums = {0,2,4,6,8,1,3,5,7,9}; 
		char[] l_chars = {'J','A','B','C','D','E','F','G','H','I'};
		int temp = 0;			
			  
		for( int i=2; i<=6; i+= 2 ) 
		{
			temp = temp + l_nums[ Integer.parseInt(p_valor.substring(i-1,i)) ];
			temp = temp + Integer.parseInt(p_valor.substring(i,i+1));
		}
			
		temp = temp + l_nums[ Integer.parseInt(p_valor.substring(7,8)) ];
			
		temp = (10 - ( temp % 10));
			
		if( temp == 10 )
			temp = 0;
		  
		if (p_valor.substring(8,9).equals(String.valueOf(temp)) || p_valor.substring(8,9).equalsIgnoreCase(String.valueOf(l_chars[temp])))
			return true;
		
		return false;
	}
	
	public static boolean contieneCaracter(String s1, String s2) {
		for(int i=0;i<s1.length();i++) {
			if (s2.indexOf(s1.charAt(i)) != -1) return true;
		}
		
		return false;
	}
	
	// Obtiene el id de formulario referenciado en una expresión del tipo: DOCUMENTO.FORMX.CAMPOX
	public static int getIdFormulario(String as_referenciaCampo){
		 try{			
			 int li_pos = as_referenciaCampo.indexOf(".");			
			 if (li_pos == -1){				 
				 return -1;												
			 }					
			 int li_id  = Integer.parseInt(as_referenciaCampo.substring(0,li_pos));							 				
			 if (li_id <= 0){					 
				return -1; 
			}
			return li_id;
		 }catch(Throwable e){			 
			 return -1;
		 }						
	}
	
	//	Obtiene el campo referenciado en una expresión del tipo: DOCUMENTO.FORMX.CAMPOX
	 public static String getCampoFormulario(String as_referenciaCampo){
		  try{			
			  int li_pos = as_referenciaCampo.indexOf(".");			
			  if (li_pos == -1){				 
				  return null;												
			  }								
			String ls_campo = as_referenciaCampo.substring(li_pos + 1);					
			if (ls_campo.length() <= 0) {					
				return null; 
			}						 
			return ls_campo;
		  }catch(Throwable e){			 
			  return null;
		  }						
	 }
		 
	public static String getDescripcionMes(String as_mes){
		  try{	
		  	int li_mes;
		  	if (as_mes.startsWith("0")){
		  		li_mes = Integer.parseInt(as_mes.substring(1));
		  	}else{
		  		li_mes = Integer.parseInt(as_mes);
		  	}
			return getDescripcionMes(li_mes);
		  }catch(Throwable e){			 
			  return null;
		  }						
	 }
	
	public static String getDescripcionMes(int ai_mes){
		  try{			
			switch (ai_mes){
				case 1: return "Enero";					
				case 2: return "Febrero";
				case 3: return "Marzo";
				case 4: return "Abril";
				case 5: return "Mayo";
				case 6: return "Junio";
				case 7: return "Julio";
				case 8: return "Agosto";
				case 9: return "Septiembre";
				case 10: return "Octubre";
				case 11: return "Noviembre";
				case 12: return "Diciembre";
				default: return null;					
			}
		  }catch(Throwable e){			 
			  return null;
		  }						
	 }
	 
	public static String formateaNumero(String as_num){
		if (as_num == null || as_num.equals("")) return "";
		return formateaNumero(Integer.parseInt(as_num));
	}
	
	
	public static String formateaNumero(int valor)
	{						
		return formateaNumero(new Double(valor));
	}
	
	public static String formateaNumero(Double valor)
	{						
		NumberFormat nf = NumberFormat.getInstance();
		return nf.format(valor);		 
	}
	
	/** 
	 * Recoge la cadena as_fecha con el formato de entrada as_formatoIN y la convierte en un 
	 * Timestamp
	 * @param String as_fecha Fecha de entrada
	 * @param String as_formatoIN Formato en el que viene la fecha		 
	 * @return String Cadena que contiene la fecha contenida en as_fecha con el formato as_formatoOUT
	 **/
	public static Timestamp getTimestamp(String as_fecha, String as_formatoIN)
	throws Exception
	{				
			if (as_fecha == null) return null;
			Calendar l_fecha = Calendar.getInstance(new Locale("es","ES"));
	
			if (as_formatoIN.equalsIgnoreCase("YYYYMMDDHH24MI"))
			{
				l_fecha.set(Calendar.DAY_OF_MONTH, Integer.parseInt(as_fecha.substring(6,8)));
				l_fecha.set(Calendar.MONTH, Integer.parseInt(as_fecha.substring(4,6)) - 1);
				l_fecha.set(Calendar.YEAR, Integer.parseInt(as_fecha.substring(0,4)));
				l_fecha.set(Calendar.HOUR_OF_DAY, Integer.parseInt(as_fecha.substring(8,10)));
				l_fecha.set(Calendar.MINUTE, Integer.parseInt(as_fecha.substring(10,12)));
				l_fecha.set(Calendar.SECOND,0);												
				l_fecha.set(Calendar.MILLISECOND,0);
			}else if (as_formatoIN.equalsIgnoreCase("YYYYMMDDHH24MISS"))
			{
				l_fecha.set(Calendar.DAY_OF_MONTH, Integer.parseInt(as_fecha.substring(6,8)));
				l_fecha.set(Calendar.MONTH, Integer.parseInt(as_fecha.substring(4,6)) - 1);
				l_fecha.set(Calendar.YEAR, Integer.parseInt(as_fecha.substring(0,4)));
				l_fecha.set(Calendar.HOUR_OF_DAY, Integer.parseInt(as_fecha.substring(8,10)));
				l_fecha.set(Calendar.MINUTE, Integer.parseInt(as_fecha.substring(10,12)));												
				l_fecha.set(Calendar.SECOND, Integer.parseInt(as_fecha.substring(12,14)));																
				l_fecha.set(Calendar.MILLISECOND,0);
			}else if (as_formatoIN.equalsIgnoreCase("DD/MM/YYYY"))
			{
				l_fecha.set(Calendar.DAY_OF_MONTH, Integer.parseInt(as_fecha.substring(0,2)));
				l_fecha.set(Calendar.MONTH, Integer.parseInt(as_fecha.substring(3,5)) - 1);
				l_fecha.set(Calendar.YEAR, Integer.parseInt(as_fecha.substring(6,10)));															
				l_fecha.set(Calendar.HOUR_OF_DAY, 0);
				l_fecha.set(Calendar.MINUTE, 0);												
				l_fecha.set(Calendar.SECOND, 0);																
				l_fecha.set(Calendar.MILLISECOND,0);
			}else {
				// Formato no soportado
				return null;		
			}
										
			Timestamp l_fec = new Timestamp(l_fecha.getTimeInMillis());
			return l_fec;				
	}
	
	public static String getPathAbsoluto(String as_pathRelativo)
	{
		try{
			if (as_pathRelativo.startsWith(File.separator)) return as_pathRelativo;
			return DatosEntorno.getPathAplicacion() + File.separator + as_pathRelativo;			
		}catch(Throwable t){
			return as_pathRelativo;
		}
	}
	
	}
