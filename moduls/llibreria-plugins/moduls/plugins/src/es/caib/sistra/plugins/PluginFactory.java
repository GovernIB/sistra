package es.caib.sistra.plugins;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import es.caib.sistra.plugins.login.PluginAutenticacionExplicitaIntf;
import es.caib.sistra.plugins.custodia.PluginCustodiaIntf;
import es.caib.sistra.plugins.email.PluginEmailIntf;
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
	
	public static final String ID_PLUGIN_DEFECTO = ".";
	
	private static Map clasesPlugins = new HashMap();
	private static Properties props = null;
	private static PluginFactory pluginFactory=null;
	private static final String PREFIX_SAR = "es.caib.sistra.configuracion.sistra.";
	
	/**
	 * Constructor. Lee fichero de propiedades global donde se han definido los plugins.
	 */
	private PluginFactory() throws Exception{
		
		try{
			String sar = System.getProperty(PREFIX_SAR + "sar");
			if (sar != null && "true".equals(sar)) {
				readPropertiesFromSAR();
			} else {
				readPropertiesFromFilesystem();
			}						
		}catch(Exception ex){
			throw new Exception("Error accediendo a propiedades globales",ex);
		}
	}
	
	/**
	 * Lee propiedades desde SAR.
	 * @throws Exception
	 */
	private void readPropertiesFromSAR() throws Exception {
		props = new Properties();
		Properties propSystem = System.getProperties();
		for (Iterator it = propSystem.keySet().iterator(); it.hasNext();) {
			String key = (String) it.next();
			String value = propSystem.getProperty(key);
			if (key.startsWith(PREFIX_SAR + "global")) {
				props.put(key.substring((PREFIX_SAR + "global").length() + 1), value);
			}	
		}
	}

	private void readPropertiesFromFilesystem() throws Exception {
		FileInputStream fis = new FileInputStream(System.getProperty("ad.path.properties") + "sistra/global.properties");
		props = new Properties();
		props.load(fis);
		fis.close();
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
	private PluginPagosIntf getPluginPagos() throws Exception{
		return  (PluginPagosIntf) getPlugin("plugin.pagos"); 
	}
	
	/**
	 * Obtiene plugin de pagos adicional
	 * @return Plugin de pagos
	 * @throws Exception
	 */
	public PluginPagosIntf getPluginPagos(String idPluginAdicional) throws Exception{
		if (ID_PLUGIN_DEFECTO.equals(idPluginAdicional)) {
			return getPluginPagos();
		} else {		
			return  (PluginPagosIntf) getPlugin("plugin.pagos." + idPluginAdicional);
		}
	}
	
	/**
	 * Obtiene ids plugins adicionales pago
	 * @return Ids plugins adicionales pago
	 * @throws Exception
	 */
	public String[] getIdsPluginsAdicionalesPago() throws Exception{
		return getIdsPluginsAdicionales("plugin.pagos");
	}
	
	
	/**
	 * Obtiene ids plugins adicionales
	 * @return Ids plugins adicionales
	 * @throws Exception
	 */
	public String[] getIdsPluginsAdicionales(String pluginName) throws Exception{
		List plugins = new ArrayList();
		for (Iterator it = props.keySet().iterator(); it.hasNext();) {
			String key = (String) it.next();
			if (key.startsWith(pluginName + ".")) {
				plugins.add(key.substring((pluginName + ".").length()));
			}
		}
		String[] ids = new String[plugins.size()];
		int i = 0;
		for (Iterator it = plugins.iterator(); it.hasNext();) {
			ids[i] = (String) it.next();
			i++;
		}
		return  ids; 
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
	
	/**
	 * Obtiene plugin de envio email
	 * @return Plugin de envio email
	 * @throws Exception
	 */
	public PluginEmailIntf getPluginEnvioEmail() throws Exception{
		return  (PluginEmailIntf) getPlugin("plugin.envioEmail");		
	}
	
}
