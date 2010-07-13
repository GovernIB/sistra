package org.ibit.rol.form.back.util;

import org.ibit.rol.form.model.Formulario;
import org.ibit.rol.form.model.FormularioSeguro;

import java.util.Map;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: mgonzalez
 * Date: 21-dic-2005
 * Time: 11:51:36
 */
public class FormularioConfig {
     private final static Map tipoMap;

    static {
        tipoMap = new HashMap(7);
        tipoMap.put(Formulario.class, "formulario");
        tipoMap.put(FormularioSeguro.class, "formularioseguro");
    }

    public static void addTipo(Class clazz, String tipo) {
        tipoMap.put(clazz, tipo);
    }

    public static String getTipo(Class clazz) {
        return (String) tipoMap.get(clazz);
    }

    public static String getTipo(Object o) {
        return getTipo(o.getClass());
    }

    private final static Map mappingMap;

    static {
        mappingMap = new HashMap(7);
        mappingMap.put(Formulario.class, "/back/formulario/editar");
        mappingMap.put(FormularioSeguro.class, "/back/formularioseguro/editar");
    }

    public static void addMapping(Class clazz, String path) {
        mappingMap.put(clazz, path);
    }

    public static String getMapping(Class clazz) {
        return (String) mappingMap.get(clazz);
    }

    public static String getMapping(Object o) {
        return getMapping(o.getClass());
    }
}
