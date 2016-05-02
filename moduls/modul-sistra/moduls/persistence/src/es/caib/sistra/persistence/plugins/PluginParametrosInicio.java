package es.caib.sistra.persistence.plugins;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Plugin que permite tracear scripts
 */
public class PluginParametrosInicio {

	Map parametrosInicio;
	private static Log log = LogFactory.getLog(PluginParametrosInicio.class);
	private boolean debugEnabled;

	public PluginParametrosInicio(Map parametros, boolean pdebugEnabled){
		parametrosInicio = parametros;
		if (parametrosInicio == null) parametrosInicio = new HashMap();
		debugEnabled = pdebugEnabled;
	}

	public String getParametro(String name){
		String valor = (String) parametrosInicio.get(name);
		if (valor == null) valor = "";
		if (debugEnabled) {
			log.debug("Accediendo a parametro inicio " + name + ". Valor parametro: " + valor);
		}
		return valor;
	}
}
