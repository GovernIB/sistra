package es.caib.sistra.plugins;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import es.caib.sistra.plugins.login.PluginAutenticacionExplicitaIntf;
import es.caib.sistra.plugins.custodia.PluginCustodiaIntf;
import es.caib.sistra.plugins.firma.PluginFirmaIntf;
import es.caib.sistra.plugins.gestionDocumental.PluginGestorDocumentalIntf;
import es.caib.sistra.plugins.login.PluginLoginIntf;
import es.caib.sistra.plugins.pagos.PluginPagosIntf;
import es.caib.sistra.plugins.regtel.PluginRegistroIntf;
import es.caib.sistra.plugins.sms.PluginSmsIntf;

/**
 * Factoria para crear plugins
 */
public class PluginFactory {
	
	private static Map clasesPlugins = new HashMap();
	private static Properties props = null;
	private static PluginFactory pluginFactory=null;
	
	/**
	 * Constructor. Lee fichero de propiedades global donde se han definido los plugins.
	 */
	private PluginFactory() throws Exception{
		FileInputStream fis=null;
		try{
			fis = new FileInputStream(System.getProperty("ad.path.properties") + "sistra/global.properties");
			props = new Properties();
			props.load(fis);
		}catch(Exception ex){
			throw new Exception("Error accediendo a fichero global de propiedades 'sistra/global.properties'",ex);
		}finally{
			try{if(fis!=null) fis.close();}catch(Exception ex2){}
		}
	}
	
	/**
	 * Implementa singleton
	 * @return Factory para crear plugins
	 * @throws Exception 
	 */
	public static PluginFactory getInstance() throws Exception{
		if (pluginFactory == null){
			pluginFactory = new PluginFactory();
		}
		return pluginFactory;
	}
	
	
	/**
	 * Crea instancia del plugin
	 * @return Plugin implementador
	 * @throws Exception
	 */
	public PluginSistraIntf getPlugin(String pluginName) throws Exception{
		return (PluginSistraIntf) getClassPlugin(pluginName).newInstance();		
	}
	
	/**
	 * Obtiene clase implementadora del plugin
	 * @return Class Clase implementadora del plugin
	 * @throws Exception
	 */
	private Class getClassPlugin(String pluginName) throws Exception{
			Class pluginClass = (Class) clasesPlugins.get(pluginName); 
			if (pluginClass != null) return pluginClass;		
			synchronized (clasesPlugins){
				String className = props.getProperty(pluginName);
				if (className == null || className.length()<=0) {
					throw new NoExistePluginException("No hay ningun classname configurado para el plugin '" + pluginName + "'");
				}
				try{
					pluginClass = Class.forName(className);
					clasesPlugins.put(pluginName,pluginClass);
				}catch (Exception ex){
					throw new Exception("Excepcion cargando classname para plugin '"+ pluginName + "': " + ex.getMessage(),ex);
				}
				
			}
			return pluginClass;		
	}
	
	/**
	 * Obtiene plugin de registro
	 * @return Plugin de registro
	 * @throws Exception
	 */
	public PluginRegistroIntf getPluginRegistro() throws Exception{
		return  (PluginRegistroIntf) getPlugin("plugin.registro"); 
	}
	
	/**
	 * Obtiene plugin de firma
	 * @return Plugin de firma
	 * @throws Exception
	 */
	public PluginFirmaIntf getPluginFirma() throws Exception{
		return  (PluginFirmaIntf) getPlugin("plugin.firma"); 
	}
	
	/**
	 * Obtiene plugin de envio SMS
	 * @return Plugin de envio SMS
	 * @throws Exception
	 */
	public PluginSmsIntf getPluginEnvioSMS() throws Exception{
		return  (PluginSmsIntf) getPlugin("plugin.envioSMS"); 
	}
	
	/**
	 * Obtiene plugin de pagos
	 * @return Plugin de pagos
	 * @throws Exception
	 */
	public PluginPagosIntf getPluginPagos() throws Exception{
		return  (PluginPagosIntf) getPlugin("plugin.pagos"); 
	}
	
	/**
	 * Obtiene plugin de login
	 * @return Plugin de login
	 * @throws Exception
	 */
	public PluginLoginIntf getPluginLogin() throws Exception{
		return  (PluginLoginIntf) getPlugin("plugin.login"); 
	}
	
	/**
	 * Obtiene plugin de autenticacion
	 * @return Plugin de autenticacion
	 * @throws Exception
	 */
	public PluginAutenticacionExplicitaIntf getPluginAutenticacionExplicita() throws Exception{
		return  (PluginAutenticacionExplicitaIntf) getPlugin("plugin.autenticacionExplicita"); 
	}
	
	/**
	 * Obtiene plugin de custodia
	 * @return Plugin de custodia
	 * @throws Exception
	 */
	public PluginCustodiaIntf getPluginCustodia() throws Exception{
		return  (PluginCustodiaIntf) getPlugin("plugin.custodia");		
	}
	
	/**
	 * Obtiene plugin de gestion documental
	 * @return Plugin de gestion documental
	 * @throws Exception
	 */
	public PluginGestorDocumentalIntf getPluginGestionDocumental() throws Exception{
		return  (PluginGestorDocumentalIntf) getPlugin("plugin.gestionDocumental");		
	}
	
	
	
}
