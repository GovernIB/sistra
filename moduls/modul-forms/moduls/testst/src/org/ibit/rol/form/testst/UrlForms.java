package org.ibit.rol.form.testst;

import java.util.Properties;

public class UrlForms {

	private static String url = null;
	
	
	public static String getUrl(){
		try{
			if (url == null){
				Properties prop = new Properties();
				prop.load(UrlForms.class.getResourceAsStream("urlForms.properties"));
				url = prop.getProperty("url");			
			}
			return url;
		}catch(Exception ex){
			return null;
		}
	}
	
	
}
