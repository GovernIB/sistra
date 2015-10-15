package es.caib.sistra.persistence.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.bsf.BSFEngine;
import org.apache.bsf.BSFException;
import org.apache.bsf.BSFManager;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.sistra.model.DatosSesion;
import es.caib.sistra.model.MensajeFront;
import es.caib.sistra.model.TramiteVersion;
import es.caib.sistra.persistence.ejb.ProcessorException;
import es.caib.sistra.persistence.plugins.ErrorScript;
import es.caib.sistra.persistence.plugins.PluginAnexos;
import es.caib.sistra.persistence.plugins.PluginDatosSesion;
import es.caib.sistra.persistence.plugins.PluginDatosTramite;
import es.caib.sistra.persistence.plugins.PluginDominio;
import es.caib.sistra.persistence.plugins.PluginFormularios;
import es.caib.sistra.persistence.plugins.PluginLog;
import es.caib.sistra.persistence.plugins.PluginMensajes;
import es.caib.sistra.persistence.plugins.PluginParametrosInicio;
import es.caib.xml.ConstantesXML;
import es.caib.zonaper.modelInterfaz.TramitePersistentePAD;

/**
 * Utilidades para manejar scripts.
 */
public final class ScriptUtil {

    protected static Log log = LogFactory.getLog(ScriptUtil.class);

    private ScriptUtil() {

    }

    /**
     * Evalua Script devolviendo resultado
     * 
     * @param script	Cadena con el script a ejecutar
     * @param params	Parámetros del script
     * @return Objeto con el resultado (normalmente será un String)
     */
    public static Object evalScript(String script, Map params) {
        log.debug("Evaluando script:\n" + script);
        log.debug("Parametros:\n" + params);
        try {
            BSFManager manager = new BSFManager();

            List nullValueNames = new ArrayList();
            if (params != null) {
                for (Iterator iterator = params.keySet().iterator(); iterator.hasNext();) {
                    String name = (String) iterator.next();
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
            engine.exec(null, 0, 0, "importPackage(Packages.es.caib.sistra.persistence.plugins)");

            Object result = engine.eval(null, 0, 0, script);

            engine.terminate();
            manager.terminate();

            log.debug("Resultado: " + result);

            return result;

        } catch (BSFException be) {
            log.error("Error ejecutando script: " + be.getMessage(), be.getTargetException());
            return null;
        }
    }

    /**
     * Evalua script suponiendo que el resultado será un booleano
     * 
     * @param script	Cadena con el script a ejecutar
     * @param params	Parámetros del script
     * @return booleano
     */
    public static boolean evalBoolScript(String script, Map params) {
        Object result = evalScript(script, params);
        if (result == null || !(result instanceof Boolean) ) {
            return false;
        }

        return ((Boolean) result).booleanValue();
    }
    
    /**
     * Devuelve charset utilizado para serializar los strings en BBDD
     * @return
     */
    public static String getCharset(){
    	return ConstantesXML.ENCODING;
    }
    
    /**
     * Convierte script a bytes
     * @param script
     * @return
     * @throws Exception
     */
    public static byte[] scriptToBytes(String script) throws Exception{
    	return script.getBytes(getCharset());
    }
    
    /**
     * Convierte bytes a script
     * @param script
     * @return
     * @throws Exception
     */
    public static String scriptToString(byte[] script) throws Exception{    	
    	return new String(script,getCharset());
    }
    
    
    /**
     * Evalua script de tramitación (sin permitir modificar formularios a través del plugin) 
     * 
     * @param script
     * @param params
     * @param tramiteVersion
     * @param datosFormularios
     * @param infoPersistencia
     * @param infoSesion
     * @return
     * @throws ProcessorException
     */
    public static String evaluarScriptTramitacion(byte[] script, HashMap params, TramiteVersion tramiteVersion,HashMap datosFormularios,
    		TramitePersistentePAD infoPersistencia,DatosSesion infoSesion) throws ProcessorException{    
    	return evaluarScriptSistra(script,params,tramiteVersion,datosFormularios,
    	    		infoPersistencia,infoSesion,true,null);
    } 	
    
    
    /**
     * Evalua script de tramitación (permitiendo modificar formularios a través del plugin) 
     * 
     * @param script
     * @param params
     * @param tramiteVersion
     * @param datosFormularios
     * @param infoPersistencia
     * @param infoSesion
     * @return
     * @throws ProcessorException
     */
    public static String evaluarScriptTramitacion(byte[] script, HashMap params, TramiteVersion tramiteVersion,HashMap datosFormularios,
    		TramitePersistentePAD infoPersistencia,DatosSesion infoSesion,boolean readOnlyPluginForms,List formulariosModificados) throws ProcessorException{    
    	return evaluarScriptSistra(script,params,tramiteVersion,datosFormularios,
    	    		infoPersistencia,infoSesion,readOnlyPluginForms,formulariosModificados);
    }
    
    	
   private static String evaluarScriptSistra(byte[] script, HashMap params, TramiteVersion tramiteVersion,HashMap datosFormularios,
    	    		TramitePersistentePAD infoPersistencia,DatosSesion infoSesion,boolean readOnlyPluginForms,List formulariosModificados) throws ProcessorException{	
    	Object result;
    	ErrorScript err = new ErrorScript();
    	
    	try{
			// Convertimos script a String
			String ls_script;
			ls_script = ScriptUtil.scriptToString(script);
			
			// Si no existe la hash de parametros la creamos
			if (params == null){
				params = new HashMap();
			}
			
			// Preparamos variable para indicar error			    	
    		params.put("ERRORSCRIPT",err);
			
    		// Preparamos plugin para acceso a parametros inicio (si no hay  información de persistencia
    		// este plugin sera pasado en los parametros. Esto pasará al iniciar el trámite.)
    		if (infoPersistencia != null){
    			PluginParametrosInicio plgIni = new PluginParametrosInicio(infoPersistencia.getParametrosInicio());
    			params.put("PLUGIN_PARAMETROSINICIO",plgIni);
    		}
    		
    		// Preparamos plugin para acceso dominios
			PluginDominio plgDom = new PluginDominio();
			params.put("PLUGIN_DOMINIOS",plgDom);
			
			// Preparamos plugin para acceso a datos formularios
			PluginFormularios plgForms = new PluginFormularios(readOnlyPluginForms);
			plgForms.setDatosFormularios(datosFormularios);
			plgForms.setEstadoPersistencia(infoPersistencia);
			params.put("PLUGIN_FORMULARIOS",plgForms);
			
			// Generamos plugin para acceder a anexos
			PluginAnexos plgAnexos = new PluginAnexos();
			plgAnexos.setEstadoPersistencia(infoPersistencia);
			params.put("PLUGIN_ANEXOS",plgAnexos);
			
			// Preparamos plugin para acceso a datos sesion
			PluginDatosSesion plgSes = new PluginDatosSesion();
			plgSes.setDatosSesion( (infoPersistencia != null? infoPersistencia.getIdPersistencia() : null), infoSesion);
			params.put("PLUGIN_DATOSSESION",plgSes);
			
			// Preparamos plugin para acceso a datos tramite
			PluginDatosTramite plgTram = new PluginDatosTramite();
			plgTram.setTramiteVersion(tramiteVersion);
			params.put("PLUGIN_TRAMITE",plgTram);
			
			// Preparamos plugin para log
			PluginLog plgLog = new PluginLog();
			params.put("PLUGIN_LOG",plgLog);	
			
			// Preparamos plugin para acceso a mensajes validación
			PluginMensajes plgMensajes = new PluginMensajes(tramiteVersion);
			params.put("PLUGIN_MENSAJES",plgMensajes);
			
			// Ejecutamos script			
			result = ScriptUtil.evalScript(ls_script,params);
			if (result == null){
				throw new Exception("Excepción BSF (ver logs)");
			}
			
			// Establecemos formularios modificados
			if (!readOnlyPluginForms){				
				for (int i=0;i<plgForms.getFormulariosDatosModificados().size();i++) 
					formulariosModificados.add(plgForms.getFormulariosDatosModificados().get(i));								
			}
			
		}catch (Exception e){
			log.error("Excepción no controlada ejecutando script de validacion",e);
			throw new ProcessorException("Excepción evaluando script: " + script,MensajeFront.MENSAJE_EXCEPCIONSCRIPT,e);
		}		 
		
		// Si existe error generamos excepción
		if (err.isExisteError()){			
			if (StringUtils.isNotEmpty(err.getMensajeError()) || StringUtils.isNotEmpty(err.getMensajeDinamicoError())){
				log.error("Script marcado con error de proceso. Mensaje error: " + (StringUtils.isNotEmpty(err.getMensajeDinamicoError())?err.getMensajeDinamicoError():err.getMensajeError()) + ".\n Script:" + script);
				throw new ProcessorException("Script marcado con error de proceso. Mensaje error: " + (StringUtils.isNotEmpty(err.getMensajeDinamicoError())?err.getMensajeDinamicoError():err.getMensajeError()),err.getMensajeError(),err.getMensajeDinamicoError());
			}else{
				log.error("Script marcado con error de proceso. No hay mensaje error.\n Script:" + script);
				throw new ProcessorException("Script marcado con error de proceso. No hay mensaje error.",MensajeFront.MENSAJE_EXCEPCIONSCRIPT);
			}
		}			
		
		// Devolvemos resultado		
		return result.toString();    	
    }
    
    
   public static byte[] getScriptVersionOrNivel(byte[] scriptVersion, byte[] scriptNivel) {
	   byte[] res = null;
	    if (scriptNivel != null && scriptNivel.length > 0){
	    	res = scriptNivel;
   		}else{
   			res = scriptVersion;
   		} 
	   return res;
   }
   
   public static boolean existeScript(byte[] script) {
	   return  (script != null && script.length > 0);
   }
    
    
    //TODO: Quitar estas funciones
    /*
    private static final Pattern ID_PATTERN = Pattern.compile("f_\\w+");
    public static String dependencias(String script) {
        String deps = "";
        if (script == null || script.trim().length() == 0) {
            return deps;
        }

        Matcher matcher = ID_PATTERN.matcher(script);
        while (matcher.find()) {
            deps += matcher.group();
        }
        return deps;
    }
    
            
     * Filtra una Map con las entradas que tienen el prefijo indicado
     * 
     * @param map	Map
     * @param prefix	Prefijo
     * @return	Map filtrada por el prefijo     
    public static Map prefixMap(Map map, String prefix) {
        if (map == null) return new HashMap();

        Map prefixedMap = new HashMap(map.size());
        for (Iterator iterator = map.keySet().iterator(); iterator.hasNext();) {
            String name = (String) iterator.next();
            prefixedMap.put(prefix + name, map.get(name));
        }
        return prefixedMap;
    }
    */            
}
