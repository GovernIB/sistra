package org.ibit.rol.form.front.parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Obtiene un procesador de documentos segun el tipo mime.
 */
public class ParserFactory {

    private static Log log = LogFactory.getLog("ParserFactory");
    private static Map parsers = new HashMap();

    static {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        InputStream is = cl.getResourceAsStream("parser.properties");
        if (is == null) {
            log.debug("parser.properties no encontrado, usando por defecto");
            parsers.put("application/pdf", ITextParser.class);
            parsers.put("application/rtf", RTFParser.class);
            parsers.put("text/rtf", RTFParser.class);
            parsers.put("text/richtext", RTFParser.class);
            parsers.put("application/msword", RTFParser.class);
            parsers.put("*/*", NullTextParser.class);
            log.debug("Configuración por defecto:" + parsers);
        } else {
            try {
                Properties props = new Properties();
                props.load(is);
                for (Iterator iterator = props.keySet().iterator(); iterator.hasNext();) {
                    String key = (String) iterator.next();
                    String value = props.getProperty(key);
                    Class clazz = cl.loadClass(value);
                    parsers.put(key, clazz);
                }
            } catch (IOException e) {
                throw new RuntimeException("Error de configuración de ParserFactory", e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Error de configuración de ParserFactory", e);
            }
        }
    }

    public static Parser getParser(String mime) {
        Class clazz = (Class) parsers.get(mime);
        if (clazz == null) {
            log.debug("mime " + mime + ", no encontrado, usando mime */*");
            clazz = (Class) parsers.get("*/*");
            if (clazz == null) {
                log.debug("mime */*, no encontrado, usando NullTextParser");
                clazz = NullTextParser.class;
            }
        }

        try {
            Parser parser = (Parser) clazz.newInstance();
            return parser;
        } catch (InstantiationException e) {
            log.error("Error creando parser", e);
            return new NullTextParser();
        } catch (IllegalAccessException e) {
            log.error("Error creando parser", e);
            return new NullTextParser();
        }
    }
}
