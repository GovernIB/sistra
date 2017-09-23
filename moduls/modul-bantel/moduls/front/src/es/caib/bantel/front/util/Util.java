package es.caib.bantel.front.util;

import javax.naming.InitialContext;

import es.caib.bantel.model.Procedimiento;
import es.caib.bantel.model.TraProcedimiento;

public class Util {
	private static String version = null;	
	
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
