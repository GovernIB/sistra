package es.caib.pagosMOCK.persistence.util;

import java.util.HashMap;
import java.util.Map;

import es.caib.pagosMOCK.model.SesionPagoMOCK;
import es.caib.pagosMOCK.model.TokenAccesoMOCK;

/**
 * Simula acceso a BBDD
 *
 */
public class DatabaseMOCK {

	private static Map sesionesPago = new HashMap();
	
	private static Map tokensAcceso = new HashMap();
	
	
	public static void guardarSesionPago(SesionPagoMOCK sesionPago){
		sesionesPago.put(sesionPago.getLocalizador(),sesionPago);
	}
	
	public static SesionPagoMOCK obtenerSesionPago(String localizador){
		return (SesionPagoMOCK) sesionesPago.get(localizador);
	}
	
	public static void borrarSesionPago(String localizador){
		if (sesionesPago.containsKey(localizador)){
			sesionesPago.remove(localizador);
		}
	}
	
	
	public static void guardarTokenAcceso(TokenAccesoMOCK token){
		tokensAcceso.put(token.getToken(),token);
	}
	
	public static TokenAccesoMOCK obtenerTokenAcceso(String token){
		return (TokenAccesoMOCK) tokensAcceso.get(token);
	}
	
	public static void borrarTokenAcceso(String token){
		if (tokensAcceso.containsKey(token)){
			tokensAcceso.remove(token);
		}
	}
	
	
}
