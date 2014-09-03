package org.ibit.rol.form.back.util;

import org.ibit.rol.form.model.*;

import java.util.Map;
import java.util.HashMap;

/**
 *
 */
public class ComponenteConfig {
    private final static Map tipoMap;

    static {
        tipoMap = new HashMap(7);
        tipoMap.put(TextBox.class, "textbox");
        tipoMap.put(Captcha.class, "captcha");
        tipoMap.put(Label.class, "label");
        tipoMap.put(CheckBox.class, "checkbox");
        tipoMap.put(FileBox.class, "filebox");
        tipoMap.put(ComboBox.class, "combobox");
        tipoMap.put(ListBox.class, "listbox");
        tipoMap.put(TreeBox.class, "treebox");
        tipoMap.put(ListaElementos.class, "listaelementos");
        tipoMap.put(RadioButton.class, "radiobutton");
        tipoMap.put(Seccion.class, "seccion");
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
        mappingMap = new HashMap(11);
        mappingMap.put(TextBox.class, "/back/textbox/editar");
        mappingMap.put(Captcha.class, "/back/captcha/editar");
        mappingMap.put(Label.class, "/back/label/editar");
        mappingMap.put(CheckBox.class, "/back/checkbox/editar");
        mappingMap.put(FileBox.class, "/back/filebox/editar");
        mappingMap.put(ComboBox.class, "/back/combobox/editar");
        mappingMap.put(ListBox.class, "/back/listbox/editar");
        mappingMap.put(TreeBox.class, "/back/treebox/editar");
        mappingMap.put(ListaElementos.class, "/back/listaelementos/editar");
        mappingMap.put(RadioButton.class, "/back/radiobutton/editar");
        mappingMap.put(Seccion.class, "/back/seccion/editar");       
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
