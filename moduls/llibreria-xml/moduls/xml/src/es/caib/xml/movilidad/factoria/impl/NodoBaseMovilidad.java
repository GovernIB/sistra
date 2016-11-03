package es.caib.xml.movilidad.factoria.impl;

import java.math.BigInteger;
import java.text.SimpleDateFormat;

import es.caib.xml.NodoBase;

/** Objeto que sirve de base para la implementación de objetos
 * de datos del api de datos propios
 * 
 * @author magroig
 *
 */
public abstract class NodoBaseMovilidad extends NodoBase {	
				
	/*public Object getObjetoImplementador (){
		return null;
	}*/
	
	/**
	 * Valida si es una fecha valida: yyyymmddhhmiss
	 */
	public boolean validaFecha(String fecha){
		try{
	        if ( fecha == null )
	            return true;
	        SimpleDateFormat sdf = new SimpleDateFormat( "yyyyMMddHH" );               
	        sdf.parse( fecha );
	        return true;
		}catch(Exception ex){
			return false;
		}		
	}
	
	/**
	 * Devuelve el valor como cadena de un objeto o nulo si es nulo
	 */
	public static String stringOrNull(Object value){
		return (value!=null?value.toString():null);		
	}
	
	/**
	 * Devuelve el valor como BigInteger de un objeto o nulo si es nulo o cadena vacia
	 */
	public static BigInteger bigIntegerOrNull(String value){		
		if (value == null || value.trim().equals("")) return null;
		return new BigInteger(value);		
	}
	
}
