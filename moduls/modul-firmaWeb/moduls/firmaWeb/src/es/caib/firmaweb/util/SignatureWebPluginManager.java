package es.caib.firmaweb.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fundaciobit.plugins.signatureweb.api.ISignatureWebPlugin;

/**
 * 
 * @author anadal
 *
 */
public class SignatureWebPluginManager {
  
  private static final SignaturePluginManager<ISignatureWebPlugin> pluginManager;
  
  protected static Log log = LogFactory.getLog(SignatureWebPluginManager.class);
    
  static {
  
    String pluginPropertiesPath = System.getProperty("es.caib.firmaweb.signaturewebplugins.path");
    
    if (pluginPropertiesPath == null) {
      log.error("La propietat 'es.caib.firmaweb.signaturewebplugins.path' "
          + "no te valor assignat.", new Exception());
    }

    pluginManager = new SignaturePluginManager<ISignatureWebPlugin>(
        pluginPropertiesPath, "signaturewebplugins");
  }
  
  
  
  public static SignaturePluginManager<ISignatureWebPlugin> getInstance() {
    return pluginManager;
  };

}
