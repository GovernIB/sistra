package es.caib.firmaweb.util;

import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.fundaciobit.plugins.signatureweb.api.SignaturesSetWeb;

/**
 * Store almacenamiento firmas.
 * 
 * TODO Se almacena en memoria. Solo funciona para unica instancia. Ver de almacenar en BD??
 * 
 * @author Indra
 *
 */
public class SignatureSetStore {
	
	public static final ConcurrentHashMap<String, SignaturesSetWeb> almacen = new ConcurrentHashMap<String, SignaturesSetWeb>();
	
	private static final long TIMEOUT = 60L * 60L * 1000L; // 60 minutos en ms
	
	public static void store(SignaturesSetWeb ss) {
		 purgar();
		 almacen.put(ss.getSignaturesSetID(), ss);
	}

	public static SignaturesSetWeb load(String id) {
		 SignaturesSetWeb res = null;
		 res = (SignaturesSetWeb) almacen.get(id);		 
		 return res;
	}
	
	public static void remove(String id) {
		if (almacen.containsKey(id)) {
			almacen.remove(id);
		}
	}
	
	public static String generateSignaturesSetID() {
		// ID: timestamp + cadena aleatoria
		String signaturesSetID = System.currentTimeMillis() + "@";
		
		SecureRandom sr = new SecureRandom();
		String rn = "" + sr.nextInt(99999999);
		if (rn.length() < 8) {
			for (int i=rn.length();i<8;i++){
				rn += "0";
			}			
		}
		
		signaturesSetID = signaturesSetID + rn;
		
		return signaturesSetID;
		
	}
	
	private static void purgar() {
		Date fechaActual = new Date();
		long timeFechaActual = fechaActual.getTime();
		for (String key : almacen.keySet()) {
			SignaturesSetWeb ss = almacen.get(key);
			if (ss.getExpiryDate() != null) {
				long timeFechaSS = ss.getExpiryDate().getTime();
				if ( (timeFechaActual - timeFechaSS) > TIMEOUT) {
					almacen.remove(key);
				}
			}			
		}
	}
}
