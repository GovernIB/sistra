package es.caib.mobtratel.persistence.util;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.mobtratel.model.Constantes;
import es.caib.mobtratel.persistence.delegate.DelegateUtil;
import es.caib.util.ValidacionesUtil;
import es.caib.xml.ConstantesXML;

/**
 * Métodos de utilidad.
 */
public class MobUtils {
	
	private static MobUtils instance = null;
	
	private Log log = LogFactory.getLog( MobUtils.class  );
	
	private Integer maxErroresSMS = null;
	private Integer maxDestinatariosEmail = null;
	private Integer maxDestinatariosSms= null;
	private Integer maxCaracteres= null;
	private Integer pagina= null;
	private Long limiteTiempo = null;
	private Integer smsDelay = null;

	protected MobUtils(){
		try{
			Properties properties = DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion();
			maxErroresSMS = new Integer(Integer.parseInt(properties.getProperty("sms.maxErroresSMS")));
			maxCaracteres = new Integer(Integer.parseInt(properties.getProperty("sms.maxCaracteres")));
			limiteTiempo = new Long(Long.parseLong(properties.getProperty("envio.limiteTiempo")) * 60 * 1000);
			maxDestinatariosEmail = new Integer(Integer.parseInt(properties.getProperty("email.maxDestinatarios")));
			pagina = new Integer(Integer.parseInt(properties.getProperty("email.pagina")));
			maxDestinatariosSms = new Integer(Integer.parseInt(properties.getProperty("sms.maxDestinatarios")));
			smsDelay = new Integer(Integer.parseInt(properties.getProperty("sms.delay")));
		}catch(Exception ex){
			log.error("No se puede acceder al fichero de definicion de propiedades de SMS",ex);
		}
		
	}
	
	public static MobUtils getInstance(){
		
		if(instance == null)
		{
			instance = new MobUtils();
		}
		return instance;
	}
	
	
	

	public Long getLimiteTiempo() {
		return limiteTiempo;
	}

	public void setLimiteTiempo(Long limiteTiempo) {
		this.limiteTiempo = limiteTiempo;
	}

	public Integer getMaxErroresSMS() {
		return maxErroresSMS;
	}

	public void setMaxErroresSMS(Integer maxErroresSMS) {
		this.maxErroresSMS = maxErroresSMS;
	}

	
	public Integer getMaxDestinatariosEmail() {
		return maxDestinatariosEmail;
	}

	public void setMaxDestinatariosEmail(Integer maxDestinatariosEmail) {
		this.maxDestinatariosEmail = maxDestinatariosEmail;
	}

	public Integer getMaxDestinatariosSms() {
		return maxDestinatariosSms;
	}

	public void setMaxDestinatariosSms(Integer maxDestinatariosSms) {
		this.maxDestinatariosSms = maxDestinatariosSms;
	}

	
	public Integer getMaxCaracteres() {
		return maxCaracteres;
	}

	public void setMaxCaracteres(Integer maxCaracteres) {
		this.maxCaracteres = maxCaracteres;
	}

	public Integer getPagina() {
		return pagina;
	}

	public void setPagina(Integer pagina) {
		this.pagina = pagina;
	}
	


	
	// Metodos de utilidad


	
	/**
	 * Metodo que compone los destinatarios, insertando el caracter ";" entre ellos 
	 * @param destinatarios
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static byte[] compoundDestinatarios(Set destinatarios) throws UnsupportedEncodingException
	{
		StringBuffer sb = new StringBuffer(destinatarios.size() * 25);
		for(Iterator it=destinatarios.iterator(); it.hasNext();)
		{
			sb.append(((String)it.next()).trim());
			sb.append(Constantes.SEPARADOR_DESTINATARIOS);			
		}
		
		String result = sb.toString();
		// Quitamos ultimo ;
		if(!result.equals(""))
			result = result.substring(0,result.length()-1);
		return result.getBytes(ConstantesXML.ENCODING);
	}

	public static byte[] compoundDestinatarios(List destinatarios) throws UnsupportedEncodingException
	{
		StringBuffer sb = new StringBuffer(destinatarios.size() * 25);		
		for(Iterator it=destinatarios.iterator(); it.hasNext();)
		{
			sb.append((String)it.next());
			sb.append(Constantes.SEPARADOR_DESTINATARIOS);	
		}
		String result = sb.toString();
		// Quitamos ultimo ;
		if(!result.equals(""))
			result = result.substring(0,result.length()-1);
		return result.getBytes(ConstantesXML.ENCODING);
	}

	


	/**
	 * Realiza la validacion de los telefonos moviles
	 * @param destinatarios
	 * @return
	 */
	public static String validarTelefonos(Set destinatarios)
	{  
		String result = "";
		for(Iterator it=destinatarios.iterator(); it.hasNext();)
		{
			String telefono = (String)it.next();
			if(!ValidacionesUtil.validarMovil(telefono)) result += "Telefono " + telefono + " erroneo.";
		}
		return result;
	}

	/**
	 * Realiza la validacion de los emails
	 * @param destinatarios
	 * @return
	 */
	public static String validarEmails(Set destinatarios)
	{  
		String result = "";
		for(Iterator it=destinatarios.iterator(); it.hasNext();)
		{
			String email = (String)it.next();
			if(!ValidacionesUtil.validarEmail(email)) result += "Email " + email + " erroneo.";
		}
		return result;
	}

	public Integer getSmsDelay() {
		return smsDelay;
	}

	public void setSmsDelay(Integer smsDelay) {
		this.smsDelay = smsDelay;
	}

	/**
	 * Quita los acentos del texto
	 * @param String as_texto Cadena de texto a normalizar
	 * @return String Cadena de texto normalizada
	 **/
	/*
	public static String quitaAcentos(String as_texto)
	throws Throwable
	{

		String ls_textoNormalizado = as_texto;
		
		// Mayúsculas con acento grave
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "À","A");
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "È","E");
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "Ì","I");
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "Ò","O");
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "Ù","U");


		// Mayúsculas con acento agudo
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "Á","A");
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "É","E");
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "Í","I");
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "Ó","O");
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "Ú","U");
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "Ý","Y");

		// Minúsculas con acento grave
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "à","a");
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "è","e");
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "ì","i");
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "ò","o");
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "ù","u");

		
		// Minúsculas con acento agudo
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "á","a");
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "é","e");
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "í","i");
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "ó","o");
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "ú","u");
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "ý","y");

		// Minúsculas con diéresis
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "ä","a");
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "ë","e");
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "ï","i");
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "ö","o");
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "ü","u");
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "ÿ","y");

		// Mayúsculas con diéresis
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "Ä","A");
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "Ë","E");
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "Ï","I");
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "Ö","O");
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "Ü","U");
		
		
		// Mayúsculas con acento ^
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "Â","A");
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "Ê","E");
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "Î","I");
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "Ô","O");
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "Û","U");
		
		// Minúsculas con acento ^
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "â","a");
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "ê","e");
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "î","i");
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "ô","o");
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "û","u");
		
		// Mayusculas con tilde
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "Ã","A");
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "Õ","O");
				
		// Minusculas con tilde
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "ã","a");
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "õ","o");
		
		// Otros
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "Å","A");
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "å","a");
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "ð","o");
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "õ","o");
		
		return ls_textoNormalizado;	

	}  		
	
	/**
	   * Método usada para reemplazar todas las ocurrencias de determinada cadena de texto
	   * por otra cadena de texto
	   * @param String Texto origen
	   * @param String Fragmento de texto a reemplazar
	   * @param String  Fragmento de texto con el que se reemplaza
	   * @return String Cadena de texto con el reemplazo de cadenas completado
	   **/
	/*
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

*/





}
