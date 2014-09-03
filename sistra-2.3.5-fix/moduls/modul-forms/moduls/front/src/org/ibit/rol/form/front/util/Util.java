package org.ibit.rol.form.front.util;
import javax.naming.InitialContext;

import org.ibit.rol.form.persistence.delegate.DelegateUtil;


public class Util
{
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
	
	private static Boolean debugScript = null;	
	/**
	 * Obtiene si se permite debug script 
	 */
	public static boolean permitirDebugScript(){
		if (debugScript == null) {
			try{
				String entorno = DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion().getProperty("entorno");
				if ("DESARROLLO".equals(entorno))				
					debugScript = new Boolean(true);
				else 
					debugScript = new Boolean(false);
			}catch(Exception ex){
				debugScript = new Boolean("false");
			}		
		}
		return debugScript.booleanValue();
	}
	
	 public static String unescape(String s) {
		    if (s==null) return null;
		    
	        int len = s.length();
	        StringBuffer b = new StringBuffer();
	        for (int i = 0; i < len; ++i) {
	            char c = s.charAt(i);
	            if (c == '+') {
	                c = ' ';
	            } else if (c == '%' && i + 2 < len) {
	                int d = dehexchar(s.charAt(i + 1));
	                int e = dehexchar(s.charAt(i + 2));
	                if (d >= 0 && e >= 0) {
	                    c = (char)(d * 16 + e);
	                    i += 2;
	                }
	            }
	            b.append(c);
	        }
	        return b.toString();
	    }

	  
	  public static int dehexchar(char c) {
	        if (c >= '0' && c <= '9') {
	            return c - '0';
	        }
	        if (c >= 'A' && c <= 'F') {
	            return c + 10 - 'A';
	        }
	        if (c >= 'a' && c <= 'f') {
	            return c + 10 - 'a';
	        }
	        return -1;
	    }
	  
	
}
