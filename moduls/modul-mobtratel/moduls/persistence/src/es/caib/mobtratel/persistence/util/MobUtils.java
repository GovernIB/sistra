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
 * M�todos de utilidad.
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
		
		// May�sculas con acento grave
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "�","A");
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "�","E");
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "�","I");
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "�","O");
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "�","U");


		// May�sculas con acento agudo
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "�","A");
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "�","E");
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "�","I");
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "�","O");
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "�","U");
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "�","Y");

		// Min�sculas con acento grave
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "�","a");
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "�","e");
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "�","i");
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "�","o");
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "�","u");

		
		// Min�sculas con acento agudo
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "�","a");
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "�","e");
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "�","i");
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "�","o");
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "�","u");
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "�","y");

		// Min�sculas con di�resis
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "�","a");
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "�","e");
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "�","i");
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "�","o");
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "�","u");
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "�","y");

		// May�sculas con di�resis
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "�","A");
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "�","E");
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "�","I");
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "�","O");
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "�","U");
		
		
		// May�sculas con acento ^
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "�","A");
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "�","E");
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "�","I");
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "�","O");
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "�","U");
		
		// Min�sculas con acento ^
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "�","a");
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "�","e");
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "�","i");
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "�","o");
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "�","u");
		
		// Mayusculas con tilde
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "�","A");
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "�","O");
				
		// Minusculas con tilde
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "�","a");
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "�","o");
		
		// Otros
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "�","A");
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "�","a");
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "�","o");
		ls_textoNormalizado = MobUtils.replace(ls_textoNormalizado, "�","o");
		
		return ls_textoNormalizado;	

	}  		
	
	/**
	   * M�todo usada para reemplazar todas las ocurrencias de determinada cadena de texto
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
