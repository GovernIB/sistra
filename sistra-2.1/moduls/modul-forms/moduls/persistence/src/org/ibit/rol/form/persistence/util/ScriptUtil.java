package org.ibit.rol.form.persistence.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.bsf.BSFEngine;
import org.apache.bsf.BSFException;
import org.apache.bsf.BSFManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ibit.rol.form.model.LogScript;
import org.ibit.rol.form.model.LogsScripts;
import org.ibit.rol.form.persistence.plugins.PluginLog;

/**
 * Utilidades para manejar scripts.
 */
public final class ScriptUtil {

	protected static Log log = LogFactory.getLog(ScriptUtil.class);
	
//	 ---- INDRA: VBLE ESPECIAL PARA LOGEAR SCRIPTS
	public static final String ID_LOG_SCRIPT="ID_LOG_SCRIPT";
	public static final String ID_PLUGIN_LOG="PLUGIN_LOG";	
//	 ---- INDRA: VBLE ESPECIAL PARA LOGEAR SCRIPTS
	
	
    private ScriptUtil() {

    }

    public static Object evalScript(String script, Map params) {
        log.debug("- Evaluando script: " + script);
        log.debug("- Parametros: " + params);
        
        String strParams = parametrosToString(params);
        
        LogScript logScript = new LogScript();
        logScript.setScript(script);
        logScript.setParametros(strParams);
        
        // --- INDRA: objeto logScript ----------------------                   
        // Buscamos en los parametros el objeto ScriptLog  
        LogsScripts logsScripts = (LogsScripts) params.get(ID_LOG_SCRIPT);        
        if (logsScripts!=null) logsScripts.addLog(logScript);
        // --- INDRA: objeto logScript ----------------------
        
        try {
            BSFManager manager = new BSFManager();

            
            // --- INDRA ----------------------
            // Preparamos plugin para log                   
            PluginLog plgLog = new PluginLog(logScript);
            if (params == null){
				params = new HashMap();
			}
            params.put(ID_PLUGIN_LOG,plgLog);
            // --- INDRA ---------------------- 			
			
            
            List nullValueNames = new ArrayList();
            if (params != null) {
                for (Iterator iterator = params.keySet().iterator(); iterator.hasNext();) {
                	String name = (String) iterator.next();
                	
                	// -- INDRA: NO PASAMOS OBJETO LOGSCRIPT
                	if (name.equals(ID_LOG_SCRIPT)) continue;
                	// -- INDRA: NO PASAMOS OBJETO LOGSCRIPT
                	
                    Object value = params.get(name);
                    if (value != null) {
                        manager.declareBean(name, value, value.getClass());
                    } else {
                        nullValueNames.add(name);
                    }
                }
            }

            BSFEngine engine = manager.loadScriptingEngine("javascript");

            // Declarar variables que son null como undefined
            for (int i = 0; i < nullValueNames.size(); i++) {
                String name = (String) nullValueNames.get(i);
                engine.exec("", 0, 0, "var " + name + ";");
            }

            // Imports per defecte.
            engine.exec(null, 0, 0, "importPackage(Packages.org.ibit.rol.form.persistence.plugins)");

            Object result = engine.eval(null, 0, 0, script);

            engine.terminate();
            manager.terminate();

            log.debug("- Resultado: " + result);
            logScript.setResultado(result);            
            
            return result;

        } catch (BSFException be) {
            log.error("Error ejecutando script: " + be.getMessage() + "\nScript: " + script + "\nParametros: " + strParams, be.getTargetException());
            logScript.setExcepcion(be.getMessage()); 	
            return null;
        }
    }

    public static boolean evalBoolScript(String script, Map params) {
        Object result = evalScript(script, params);
        if (result == null || !(result instanceof Boolean) ) {
            return false;
        }

        return ((Boolean) result).booleanValue();
    }

    public static Map prefixMap(Map map, String prefix) {
        if (map == null) return new HashMap();

        Map prefixedMap = new HashMap(map.size());
        for (Iterator iterator = map.keySet().iterator(); iterator.hasNext();) {
            String name = (String) iterator.next();
            prefixedMap.put(prefix + name, map.get(name));
        }
        return prefixedMap;
    }

    /**
     * Pattern que identifica els noms de variables.
     */
    private static final Pattern ID_PATTERN = Pattern.compile("f_\\w+");

    /**
     * Retorna una cadena amb els noms de les variables que es troben
     * dins l'expresió indicada segons el patró {@link #ID_PATTERN} separades per espais.
     * @param script Expresió.
     * @return cadena amb els noms de variables que compleixen amb {@link #ID_PATTERN} separats
     * per espais.
     */
    public static String dependencias(String script) {
        String deps = "";
        if (script == null || script.trim().length() == 0) {
            return deps;
        }

        Matcher matcher = ID_PATTERN.matcher(script);
        while (matcher.find()) {
            deps += matcher.group() + " ";
        }
        return deps;
    }
    
    /**
     * Convierte a string la lista de parametros
     * @param params
     * @return
     */
    private static String parametrosToString(Map params){
    	String key;
    	StringBuffer sb = new StringBuffer(params.size() * 50);
    	sb.append("{");
    	boolean primer=true;    	
    	for (Iterator it=params.keySet().iterator();it.hasNext();){
    		key = (String) it.next();    		
    		if (key.equals(ScriptUtil.ID_LOG_SCRIPT) || key.equals(ScriptUtil.ID_PLUGIN_LOG)) continue;
    		if (primer){
    			primer = false;
    		}else{
    			sb.append(", ");
    		}
    		sb.append(key);
    		sb.append("=");
    		sb.append(params.get(key));    		
    	}
    	sb.append("}");
        return sb.toString();  
    }
}
