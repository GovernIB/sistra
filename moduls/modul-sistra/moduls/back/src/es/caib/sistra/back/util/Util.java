package es.caib.sistra.back.util;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.naming.InitialContext;


public class Util
{
	private static String version = null;	
	public final static String FORMATO_TIMESTAMP = "dd/MM/yyyy HH:mm:ss";
	public final static String FORMATO_FECHA = "dd/MM/yyyy";
	
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
	
	public static Date cadenaAFecha( String strFecha, String strFormatoFecha )
    {
        if ( strFecha == null )
            return null;
        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat( strFormatoFecha );
            return sdf.parse( strFecha );
        }
        catch ( ParseException pExc)
        {
            return null;
        }
    }

    public static Date cadenaAFecha( String strFecha )
    {
        return cadenaAFecha( strFecha, FORMATO_FECHA );
    }

    public static Date cadenaATimestamp( String strFecha )
    {
        return cadenaAFecha( strFecha, FORMATO_TIMESTAMP );
    }

    public static String fechaACadena( Date fecha, String strFormatoFecha )
    {
        if ( fecha == null )
            return null;
        SimpleDateFormat sdf = new SimpleDateFormat( strFormatoFecha );
        return sdf.format( fecha );
    }

    public static String fechaACadena( Date datFecha )
    {
        return fechaACadena( datFecha, FORMATO_FECHA );
    }

    public static String timestampACadena( Date datFecha )
    {
        return fechaACadena( datFecha, FORMATO_TIMESTAMP );
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
	 * Obtiene version (en web.xml)
	 */
	public static String getVersion(){
		if (version == null) {
			try{
				InitialContext ic = new InitialContext();
				version = (String) ic.lookup("java:comp/env/release.cvs.tag");
			}catch(Exception ex){
				version = null;
			}		
		}
		return version;
	}
}
