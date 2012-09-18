package org.ibit.rol.form.admin.util;

import java.util.Map;
import java.util.HashMap;

import org.ibit.rol.form.model.TextBox;
import org.ibit.rol.form.model.Label;
import org.ibit.rol.form.model.CheckBox;
import org.ibit.rol.form.model.FileBox;
import org.ibit.rol.form.model.ComboBox;
import org.ibit.rol.form.model.ListBox;
import org.ibit.rol.form.model.RadioButton;

/**
 *
 */
public class ComponenteConfig {

    private final static Map tipoMap;

    static {
        tipoMap = new HashMap(7);
        tipoMap.put(TextBox.class, "textbox");
        tipoMap.put(Label.class, "label");
        tipoMap.put(CheckBox.class, "checkbox");
        tipoMap.put(FileBox.class, "filebox");
        tipoMap.put(ComboBox.class, "combobox");
        tipoMap.put(ListBox.class, "listbox");
        tipoMap.put(RadioButton.class, "radiobutton");
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
        mappingMap.put(TextBox.class, "/admin/textbox/editar");
        mappingMap.put(Label.class, "/admin/label/editar");
        mappingMap.put(CheckBox.class, "/admin/checkbox/editar");
        mappingMap.put(FileBox.class, "/admin/filebox/editar");
        mappingMap.put(ComboBox.class, "/admin/combobox/editar");
        mappingMap.put(ListBox.class, "/admin/listbox/editar");
        mappingMap.put(RadioButton.class, "/admin/radiobutton/editar");
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
