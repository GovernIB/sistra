package es.caib.sistra.plugins.firma.impl.mock;

import java.io.InputStream;
import java.util.Map;

import es.caib.sistra.plugins.firma.FirmaIntf;

/**
 * 
 * Utilizamos api gva para parsear la firma en cms
 * Utilizamos api caib para sacar el nombre 
 * 
 */
public class UtilFirmaMock {
	
	
	public static String getDNI(String signature, String formato)throws Exception {
		
		// Firma en cliente
		if (formato.equals("CMS")){
			/*
			MensajeFirmado m = new MensajeFirmado();
			m.cargarDeString(signature);
			Certificado c = m.getCertificado();			
			return c.getNIF();
			*/
			DatosFirma df = new DatosFirma(signature);
			DatosCertificado dc = new DatosCertificado(df.getCertificado());
			return dc.getNif();
			
		}
		
		// Firma en servidor (Fake)
		if (formato.equals("FAKE")){
			return "11111111H";
		}
		
		throw new Exception("Formato " + formato + " no soportado");
		
	}
	
	
	public static String getNombre(String signature, String formato)throws Exception {
		
		// Firma en cliente
		if (formato.equals("CMS")){
			DatosFirma df = new DatosFirma(signature);
			DatosCertificado dc = new DatosCertificado(df.getCertificado());
			return dc.getFullName();
		}
		
		// Firma en servidor (Fake)
		if (formato.equals("FAKE")){
			return "FIRMA MOCK";
		}
		
		throw new Exception("Formato " + formato + " no soportado");
	}


	public static FirmaIntf firmar(InputStream datos, String nombreCertificado, Map parametros) {
		// Firma fake en servidor
		FirmaMock f = new FirmaMock();
		f.setSignature("FIRMA FAKE");
		f.setFormato("FAKE");
		return f;
	}
	
	
	public static String unescapeChars64UrlSafe( String cad ){
	  	cad = cad.replaceAll( "-", "+" );
	   	cad = cad.replaceAll( "_", "/" );
	   	return cad;
    }
	
}
