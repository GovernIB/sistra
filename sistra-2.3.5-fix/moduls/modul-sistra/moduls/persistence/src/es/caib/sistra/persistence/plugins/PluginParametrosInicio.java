package es.caib.sistra.persistence.plugins;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Plugin que permite tracear scripts
 */
public class PluginParametrosInicio {
	private static Log log = LogFactory.getLog(PluginParametrosInicio.class);	
	
	Map parametrosInicio;
	
	public PluginParametrosInicio(Map parametros){		
		parametrosInicio = parametros;		
		if (parametrosInicio == null) parametrosInicio = new HashMap();
	}
	
	public String getParametro(String name){
		String valor = (String) parametrosInicio.get(name);
		if (valor == null) valor = "";
		log.debug("Accediendo a parametro inicio " + name + ". Valor parametro: " + valor);		
		return valor;
	}
}
