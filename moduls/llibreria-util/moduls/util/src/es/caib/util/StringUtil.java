package es.caib.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.StringUtils;

/**
 * Utilidades con cadenas
 *
 */
public class StringUtil {

	public final static String FORMATO_FECHA = "dd/MM/yyyy";
	public final static String FORMATO_TIMESTAMP = "dd/MM/yyyy HH:mm:ss";
	public final static String FORMATO_REGISTRO = "yyyyMMddHHmmss";
	public final static String LANG_CA = "ca";
	public final static String PATRON_IDENTIFICADOR = "[A-Z0-9\\-_]{1,20}";
	public final static String PATRON_USUARIO = "[a-zA-Z0-9\\-_]{1,20}";
	
	
	// --- FUNCIONES PARA OBTENER MODELO/VERSION PARA IDENTIFICADOR CON FORMATO "MODELO-VERSION"
	
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
	
	
	//	--- FUNCIONES PARA MANEJO DE FECHA
	
	/**
	 * Convierte fecha a un string con formato FORMATO_FECHA
	 * @param datFecha
	 * @return
	 */
	public static String fechaACadena( Date datFecha )
    {
        return fechaACadena( datFecha, FORMATO_FECHA );
    }
	
	/**
	 * Convierte fecha a un string con formato FORMATO_TIMESTAMP
	 * 
	 * @param datFecha
	 * @return
	 */
	public static String timestampACadena( Date datFecha )
    {
        return fechaACadena( datFecha, FORMATO_TIMESTAMP );
    }
	
	/**
	 * Formatea fecha
	 * @param fecha
	 * @param strFormatoFecha
	 * @return
	 */
	public static String fechaACadena( Date fecha, String strFormatoFecha )
    {
        if ( fecha == null )
            return null;
        SimpleDateFormat sdf = new SimpleDateFormat( strFormatoFecha );               
        return sdf.format( fecha );
    }
	
	/**
	 * Deformatea fecha (la fecha debe ir en formato FORMATO_FECHA)
	 * @param fecha
	 * @return
	 */
	public static Date cadenaAFecha( String strFecha )
    {
        return cadenaAFecha( strFecha, FORMATO_FECHA );
    }
	
	/**
	 * Deformatea fecha (la fecha debe ir en formato FORMATO_TIMESTAMP)
	 * 
	 * @param strFecha
	 * @return
	 */
	public static Date cadenaATimestamp( String strFecha )
    {
        return cadenaAFecha( strFecha, FORMATO_TIMESTAMP );
    }
	
	/**
	 * Deformatea fecha (indicando el formato de la fecha)
	 * @param fecha
	 * @param strFormatoFecha
	 * @return
	 */
	public static Date cadenaAFecha( String fecha, String strFormatoFecha )
    {
		try{
	        if ( fecha == null )
	            return null;
	        SimpleDateFormat sdf = new SimpleDateFormat( strFormatoFecha );               
	        return sdf.parse( fecha );
		}catch(Exception ex){
			return null;
		}
	        
    }
	
	 public static String sqlTimestampACadena( Timestamp timestamp, String strFormatoFecha )
	    {
	    	return ( timestamp != null ) ? fechaACadena( new Date( timestamp.getTime() ), strFormatoFecha  ) : null;
	    }
	    
	    public static String sqlTimestampACadena( Timestamp timestamp )
	    {
	    	return sqlTimestampACadena( timestamp, FORMATO_TIMESTAMP );
	    }
	    
	    public static Timestamp cadenaASqlTimestamp( String strFecha, String strFormatoFecha )
	    {
	    	Date date = cadenaAFecha( strFecha, strFormatoFecha );
	    	
	    	return date != null ? new Timestamp( date.getTime() ) : null; 
	    }
	    
	    
	    public static Timestamp cadenaASqlTimestamp( String strFecha )
	    {
	    	return cadenaASqlTimestamp( strFecha, FORMATO_TIMESTAMP );
	    }
	    
	    public static String sqlDateACadena( java.sql.Date timestamp, String strFormatoFecha )
	    {
	    	return ( timestamp != null ) ? fechaACadena( new Date( timestamp.getTime() ), strFormatoFecha  ) : null;
	    }
	    
	    public static String sqlDateACadena( java.sql.Date timestamp )
	    {
	    	return sqlDateACadena( timestamp, FORMATO_TIMESTAMP );
	    }
	    
	    public static java.sql.Date cadenaASqlDate( String strFecha, String strFormatoFecha )
	    {
	    	Date date = cadenaAFecha( strFecha, strFormatoFecha );
	    	
	    	return date != null ? new java.sql.Date( date.getTime() ) : null; 
	    }
	    
	    
	    public static java.sql.Date cadenaASqlDate( String strFecha )
	    {
	    	return cadenaASqlDate( strFecha, FORMATO_TIMESTAMP );
	    }
	
	// -- FUNCIONES DE MANEJO DE CADENAS
	/**
	 * Función de relleno
	 */
	public static String lpad(String as_texto, int ai_longMinima, char ac_relleno)
	  {
	  	int longStr = as_texto.length();
		if (as_texto == null) return as_texto;
	  	if (longStr < ai_longMinima)
	  	for (int i=0; i< ai_longMinima - longStr;i++)
	  	{
	  		as_texto = ac_relleno +as_texto;
	  	}
	  	return as_texto;
	  }
	 
	 /**
	   * Método usada para reemplazar todas las ocurrencias de determinada cadena de texto
	   * por otra cadena de texto
	   * @param String Texto origen
	   * @param String Fragmento de texto a reemplazar
	   * @param String  Fragmento de texto con el que se reemplaza
	   * @return String Cadena de texto con el reemplazo de cadenas completado
	   **/
	  public static String replace(String s, String one, String another)
	  throws Exception 
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
		 * Funcion que identifica si la cadena es nula o es una cadena vacia
		 * @param string
		 * @return
		 */
		public static boolean esCadenaVacia( String string )
	    {
	        return string == null ? true : "".equals( string.trim() );
	    }
		
		/**
	     * Método que devuelve la cadena que se le pasa como primer parametro
	     * o la cadena por defecto que se le pasa como segundo parametro si
	     * la primera es nula o esta vacía.
	     * @param strCadena Cadena a chequear
	     * @param strPorDefecto Cadena a devolver si la primera cadena es nula.
	     * @return String
	     */
	    public static String obtenerCadenaPorDefecto( String strCadena, String strPorDefecto )
	    {
	      return !esCadenaVacia ( strCadena ) ? strCadena : strPorDefecto;
	    }
	    
	    /**
		 * Descompone caracteres de un String en un array de String
		 * 
		 * @param str
		 * @return
		 */
	    public static String[] splitString( String str )
	    {
	    	int iSize = str.length();
	    	String [] arrResult = new String[ iSize ];
	    	for ( int i = 0; i < iSize; i++ )
	    	{
	    		arrResult[ i ] = str.substring( i, i + 1 );  
	    	}
	    	return arrResult;
	    }
	    
	    /**
	     * Concatena un array de Strings en un único String
	     * 
	     * @param arrStr
	     * @return
	     */
	    public static String concatArrString( String[] arrStr )
	    {
	    	StringBuffer sb = new StringBuffer();
	    	for ( int i = 0; i < arrStr.length; i++ )
	    	{
	    		sb.append( arrStr[i] );
	    	}
	    	return sb.toString();
	    }
	    
	    /**
	     * Reemplaza los parametros especificados en la cadena original marcados por <x> (x: numero de parametro)
	     * por el consiguiente parametro en la lista
	     * 
	     * @param cadena
	     * @param lstParametros
	     * @return
	     */
	    public static String expansion( String cadena, List lstParametros )
	    {
	    	for ( int i = 0; i < lstParametros.size(); i++ )
	    	{
	    		cadena = cadena.replaceAll( "<" + i + ">", ( String ) lstParametros.get( i ) );
	    	}
	    	return cadena;
	    }
	  	 
	  
	  // --- FUNCION DE NORMALIZACION DE NOMBRE DE FICHEROS
	    /**
	     * Obtiene extension fichero.
	     */
	    public static String getExtension(String filename){
			if(filename.lastIndexOf(".") != -1){
				return filename.substring(filename.lastIndexOf(".") + 1);
			}else{
				return "";
			}
		}
	    
		/**
		 * Normaliza nombre fichero (sin extensión) para que no de problemas eliminando carácteres que suelen dar problemas
		 * 
		 * @param as_fic
		 * @return
		 * @throws Exception
		 */
		public static String normalizarNombreFichero(String as_fic)
				throws Exception {
			
					
			String ls_fic = as_fic;
			
			// Limitamos extensión a 100 caracteres
			if (ls_fic.length() > 100){
				ls_fic = ls_fic.substring(0,99);
			}
			
			ls_fic = StringUtil.replace(ls_fic, "\\", "");
			ls_fic = StringUtil.replace(ls_fic, "/", "");
			ls_fic = StringUtil.replace(ls_fic, ":", "");
			ls_fic = StringUtil.replace(ls_fic, "\"", "");
			ls_fic = StringUtil.replace(ls_fic, "<", "");
			ls_fic = StringUtil.replace(ls_fic, ">", "");
			ls_fic = StringUtil.replace(ls_fic, "*", "");
			ls_fic = StringUtil.replace(ls_fic, "|", "");
			ls_fic = StringUtil.replace(ls_fic, "?", "");
			return ls_fic;
		}		
		
		
		/**
		 * Quita los acentos,dieresis,etc de las vocales de la cadena
		 * 
		 * @param as_texto
		 * @return
		 * @throws Throwable
		 */
		public static String quitaAcentos(String as_texto)
			throws Throwable
		{

			String ls_textoNormalizado = as_texto;
			
			// Mayúsculas con acento grave
			ls_textoNormalizado = StringUtil.replace(ls_textoNormalizado, "À","A");
			ls_textoNormalizado = StringUtil.replace(ls_textoNormalizado, "È","E");
			ls_textoNormalizado = StringUtil.replace(ls_textoNormalizado, "Ì","I");
			ls_textoNormalizado = StringUtil.replace(ls_textoNormalizado, "Ò","O");
			ls_textoNormalizado = StringUtil.replace(ls_textoNormalizado, "Ù","U");


			// Mayúsculas con acento agudo
			ls_textoNormalizado = StringUtil.replace(ls_textoNormalizado, "Á","A");
			ls_textoNormalizado = StringUtil.replace(ls_textoNormalizado, "É","E");
			ls_textoNormalizado = StringUtil.replace(ls_textoNormalizado, "Í","I");
			ls_textoNormalizado = StringUtil.replace(ls_textoNormalizado, "Ó","O");
			ls_textoNormalizado = StringUtil.replace(ls_textoNormalizado, "Ú","U");
			ls_textoNormalizado = StringUtil.replace(ls_textoNormalizado, "Ý","Y");

			// Minúsculas con acento grave
			ls_textoNormalizado = StringUtil.replace(ls_textoNormalizado, "à","a");
			ls_textoNormalizado = StringUtil.replace(ls_textoNormalizado, "è","e");
			ls_textoNormalizado = StringUtil.replace(ls_textoNormalizado, "ì","i");
			ls_textoNormalizado = StringUtil.replace(ls_textoNormalizado, "ò","o");
			ls_textoNormalizado = StringUtil.replace(ls_textoNormalizado, "ù","u");

			
			// Minúsculas con acento agudo
			ls_textoNormalizado = StringUtil.replace(ls_textoNormalizado, "á","a");
			ls_textoNormalizado = StringUtil.replace(ls_textoNormalizado, "é","e");
			ls_textoNormalizado = StringUtil.replace(ls_textoNormalizado, "í","i");
			ls_textoNormalizado = StringUtil.replace(ls_textoNormalizado, "ó","o");
			ls_textoNormalizado = StringUtil.replace(ls_textoNormalizado, "ú","u");
			ls_textoNormalizado = StringUtil.replace(ls_textoNormalizado, "ý","y");

			// Minúsculas con diéresis
			ls_textoNormalizado = StringUtil.replace(ls_textoNormalizado, "ä","a");
			ls_textoNormalizado = StringUtil.replace(ls_textoNormalizado, "ë","e");
			ls_textoNormalizado = StringUtil.replace(ls_textoNormalizado, "ï","i");
			ls_textoNormalizado = StringUtil.replace(ls_textoNormalizado, "ö","o");
			ls_textoNormalizado = StringUtil.replace(ls_textoNormalizado, "ü","u");
			ls_textoNormalizado = StringUtil.replace(ls_textoNormalizado, "ÿ","y");

			// Mayúsculas con diéresis
			ls_textoNormalizado = StringUtil.replace(ls_textoNormalizado, "Ä","A");
			ls_textoNormalizado = StringUtil.replace(ls_textoNormalizado, "Ë","E");
			ls_textoNormalizado = StringUtil.replace(ls_textoNormalizado, "Ï","I");
			ls_textoNormalizado = StringUtil.replace(ls_textoNormalizado, "Ö","O");
			ls_textoNormalizado = StringUtil.replace(ls_textoNormalizado, "Ü","U");
			
			
			// Mayúsculas con acento ^
			ls_textoNormalizado = StringUtil.replace(ls_textoNormalizado, "Â","A");
			ls_textoNormalizado = StringUtil.replace(ls_textoNormalizado, "Ê","E");
			ls_textoNormalizado = StringUtil.replace(ls_textoNormalizado, "Î","I");
			ls_textoNormalizado = StringUtil.replace(ls_textoNormalizado, "Ô","O");
			ls_textoNormalizado = StringUtil.replace(ls_textoNormalizado, "Û","U");
			
			// Minúsculas con acento ^
			ls_textoNormalizado = StringUtil.replace(ls_textoNormalizado, "â","a");
			ls_textoNormalizado = StringUtil.replace(ls_textoNormalizado, "ê","e");
			ls_textoNormalizado = StringUtil.replace(ls_textoNormalizado, "î","i");
			ls_textoNormalizado = StringUtil.replace(ls_textoNormalizado, "ô","o");
			ls_textoNormalizado = StringUtil.replace(ls_textoNormalizado, "û","u");
			
			// Mayusculas con tilde
			ls_textoNormalizado = StringUtil.replace(ls_textoNormalizado, "Ã","A");
			ls_textoNormalizado = StringUtil.replace(ls_textoNormalizado, "Õ","O");
					
			// Minusculas con tilde
			ls_textoNormalizado = StringUtil.replace(ls_textoNormalizado, "ã","a");
			ls_textoNormalizado = StringUtil.replace(ls_textoNormalizado, "õ","o");
			
			// Otros
			ls_textoNormalizado = StringUtil.replace(ls_textoNormalizado, "Å","A");
			ls_textoNormalizado = StringUtil.replace(ls_textoNormalizado, "å","a");
			ls_textoNormalizado = StringUtil.replace(ls_textoNormalizado, "ð","o");
			ls_textoNormalizado = StringUtil.replace(ls_textoNormalizado, "õ","o");
			
			return ls_textoNormalizado;	

		}
		
		/**
		 * Elimina los carácteres que no son alfanumericos
		 * @param cadena
		 * @return
		 */
	    public static String escapeBadCharacters( String cadena )
	    {
	    	return cadena.replaceAll( "[^\\w]", "" );
	    }
	    
	    /**
	     * Reemplaza un caracter de retorno de carro (opcional) + un caracter de nueva linea por un <br/>
	     * @param value
	     * @return
	     */
	    public static String newLineToBr( String value )
		{
			return value.replaceAll( "\\r*\\n", "<br/>" );
		}
	  
	    /**
	     * Calcula un digito de control a partir de un texto
	     * @param texto
	     * @return
	     */
	    public static String calculaDC(String texto) throws Exception{	    	
		    	byte[] datos = texto.getBytes("UTF-8");
		    	MessageDigest dig = MessageDigest.getInstance( "SHA-512" );
		    	String hex = new String(Hex.encodeHex(dig.digest(datos)));
		    	
		    	BigInteger bi = new BigInteger(hex,16);
		    	String bis = bi.toString();
		    	int num = Integer.parseInt(bis.substring(bis.length() - 2)) % 99;
		    	String snum = Integer.toString(num);
		    	if (snum.length()<2) snum = "0" + snum;
		    	return snum;
	    }
	    
	    
	    /**
	     * Convierte lo que saca 'printStackTrace()' en una cadena.
	     * 
	     * @param Throwable :  la excepcion
	     */
	    public static String stackTraceToString ( Throwable e )
	    {
	        if ( e == null )
	        {
	            return "";
	        }

	        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
	        try
	        {
	            PrintWriter writer = new PrintWriter( bytes, true );
	            e.printStackTrace( writer );
	        }
	        catch ( Exception ex )
	        {
	        }

	        return bytes.toString();
	    }
	    	 
	    /**
	     * Serializa list de strings en un string 
	     * @param list
	     * @param separador
	     * @return
	     * @throws Exception
	     */
	    public static String serializarList(List list) throws Exception{
	    	
	    	String separador = "#-@";
	    	
			if (list == null) return null;		
			String str="";		
			boolean primer = true;
			String value;
			
			StringBuffer sb = new StringBuffer(list.size() * 100);
			
			for (Iterator it = list.iterator();it.hasNext();){
				value = (String) it.next();				
				if (!primer) {
					//str = str + separador;
					sb.append(separador);
				}else{
					primer = false;
				}
				
				if (value == null) 
					value ="";	
				
				sb.append(str).append(value);
			}
			return sb.toString();
			//return str;
		}
	    
	    /**
	     * Deserializa list de strings 
	     * @param listStr
	     * @return
	     * @throws Exception
	     */
	    public static List deserializarList(String listStr) throws Exception{
	    	String separador = "#-@";
	    	if (listStr == null || listStr.length() <= 0) return null;
	    	List list = new ArrayList();
			StringTokenizer st = new StringTokenizer(listStr,separador);		
			String value;
			while (st.hasMoreElements()){
				value = (String) st.nextElement();			
				list.add(value);
			}
			return list;
		}
	    
	    
	    
	    /**
	     * Serializa map en un string 
	     * @param map
	     * @param separador
	     * @return
	     * @throws Exception
	     */
	    public static String serializarMap(Map map) throws Exception{
	    	
	    	String separador = "#-@";
	    	
			if (map == null) return null;		
			String str="";		
			boolean primer = true;
			String name,value;
			
			StringBuffer sb = new StringBuffer(map.size() * 100);
			
			for (Iterator it = map.keySet().iterator();it.hasNext();){
				name = it.next().toString();				
				if (!primer) {
					//str = str + separador;
					sb.append(separador);
				}else{
					primer = false;
				}
				
				if (map.get(name) != null) 
					value = map.get(name).toString();
				else
					value ="";	
				
				sb.append(str).append(name).append(separador).append(value);
				//str = str +  name + separador + value;
			}
			return sb.toString();
			//return str;
		}
	    
	    /**
	     * Deserializa map 
	     * @param mapStr
	     * @return
	     * @throws Exception
	     */
	    public static Map deserializarMap(String mapStr) throws Exception{
	    	String separador = "#-@";
	    	if (mapStr == null || mapStr.length() <= 0) return null;
			HashMap map = new HashMap();
			StringTokenizer st = new StringTokenizer(mapStr,separador);		
			String key,value;
			while (st.hasMoreElements()){
				key = (String) st.nextElement();			
				if (st.hasMoreElements()) value = (String) st.nextElement();
					else value=null;
				map.put(key,value);
			}
			return map;
		}
	    
	    
	    /**
	     * Formatea nombre y apellidos según formato indicado:
	     * 	AN: Apellidos, Nombre
	     *  NA: Nombre Apellido1 Apellido2
	     *  
	     * @param formato
	     * @param nombre
	     * @param apellido1
	     * @param apellido2
	     * @return
	     * @throws Exception
	     */
	    public static String formatearNombreApellidos(String formato, String nombre, String apellido1, String apellido2) throws Exception{
	    	if ("AN".equals(formato)){
	    		String ape = ((StringUtils.isNotEmpty(apellido1))?apellido1:"") + 
	    					 ((StringUtils.isNotEmpty(apellido2))?" " + apellido2:"");
	    		if (ape.length() > 0){
	    			ape += ", ";
	    		}
	    		return ape + (StringUtils.isNotEmpty(nombre)?nombre:"") ;	    		
	    	}else if ("NA".equals(formato)){
	    		return (StringUtils.isNotEmpty(nombre)?nombre:"") +
			    		 ((StringUtils.isNotEmpty(apellido1))?" " + apellido1:"") + 
						 ((StringUtils.isNotEmpty(apellido2))?" " + apellido2:"");
	    	}else{
	    		throw new Exception("Formato " + formato + " no soportado");
	    	}
	    }
	    
	    /**
	     * Valida formato identificador usado en la aplicacion: carácteres alfanumericos y con los caracteres especiales: - _
	     * @param identificador
	     * @return true si cumple formato
	     */
	    public static boolean validarFormatoIdentificador(String identificador){
	    	return  StringUtils.isNotBlank(identificador) && Pattern.matches(PATRON_IDENTIFICADOR, identificador);
	    }
	    
	    /**
	     * Valida formato usuario usado en la aplicacion: carácteres alfanumericos y con los caracteres especiales: - _
	     * @param identificador
	     * @return true si cumple formato
	     */
	    public static boolean validarFormatoUsuario(String usuario){
	    	return  StringUtils.isNotBlank(usuario) && Pattern.matches(PATRON_USUARIO, usuario);
	    }
	    

	    /**
	     * Genera nombre fichero justificante.
	     * @param nr Num reg
	     * @param dr Fec reg
	     * @return Nombre fichero
	     * @throws Exception
	     */
	    public static  String generarNombreFicheroJustificante(String lang, String nr, Date dr)
				throws Exception {
			String nomfic = "Justificant_";
			if ("es".equals(lang))  {
				nomfic = "Justificante_";
			}
			nomfic = StringUtil.normalizarNombreFichero(nomfic +
					StringUtil.replace(nr,"/","-") + "_" +
					StringUtil.fechaACadena(dr, StringUtil.FORMATO_REGISTRO) +
					".pdf");
			return nomfic;
		}
	    
}
