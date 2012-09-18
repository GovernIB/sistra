package org.ibit.rol.form.model.betwixt;

import org.apache.commons.betwixt.strategy.PropertySuppressionStrategy;
import org.ibit.rol.form.model.Archivo;
import org.ibit.rol.form.model.AyudaPantalla;
import org.ibit.rol.form.model.CheckBox;
import org.ibit.rol.form.model.FileBox;
import org.ibit.rol.form.model.ComboBox;
import org.ibit.rol.form.model.Formulario;
import org.ibit.rol.form.model.Label;
import org.ibit.rol.form.model.ListBox;
import org.ibit.rol.form.model.Mascara;
import org.ibit.rol.form.model.Pantalla;
import org.ibit.rol.form.model.Patron;
import org.ibit.rol.form.model.PerfilUsuario;
import org.ibit.rol.form.model.RadioButton;
import org.ibit.rol.form.model.TextBox;
import org.ibit.rol.form.model.Traducible;
import org.ibit.rol.form.model.TreeBox;
import org.ibit.rol.form.model.ListaElementos;
import org.ibit.rol.form.model.Validacion;
import org.ibit.rol.form.model.ValorPosible;
import org.ibit.rol.form.model.FormularioSeguro;
import org.ibit.rol.form.model.Salida;
import org.ibit.rol.form.model.PropiedadSalida;
import org.ibit.rol.form.model.PuntoSalida;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Controla les propietats que es suprimiran del model.
 */
public class ModelSuppressionStrategy extends PropertySuppressionStrategy {

    private Map propiedadesInvalidas = new HashMap();

    protected void addSupressionList(Class clazz, String[] properties) {
        Set set = new HashSet(Arrays.asList(properties));
        propiedadesInvalidas.put(clazz, set);
    }

    public ModelSuppressionStrategy() {
        addSupressionList(Formulario.class, new String[]{"id"});
        addSupressionList(FormularioSeguro.class, new String[]{"id"});
        addSupressionList(Archivo.class, new String[]{"id"});
        addSupressionList(Pantalla.class, new String[]{"id", "formulario", "campos"});

        String[] propsComponente = new String[]{"id", "paleta", "pantalla", "natural", "real", "imagen", "valorPosible", "allValoresPosibles"};
        addSupressionList(Label.class, propsComponente);
        addSupressionList(CheckBox.class, propsComponente);
        addSupressionList(FileBox.class, propsComponente);
        addSupressionList(ComboBox.class, propsComponente);
        addSupressionList(ListBox.class, propsComponente);
        addSupressionList(RadioButton.class, propsComponente);
        addSupressionList(TextBox.class, propsComponente);
        addSupressionList(TreeBox.class, propsComponente);
        addSupressionList(ListaElementos.class, propsComponente);

        addSupressionList(AyudaPantalla.class, new String[]{"id", "pantalla"});
        addSupressionList(PerfilUsuario.class, new String[]{});
        addSupressionList(Patron.class, new String[]{"campos"});
        addSupressionList(Validacion.class, new String[]{"id", "campo"});
        addSupressionList(ValorPosible.class, new String[]{"id", "campo"});
        addSupressionList(Mascara.class, new String[]{"validaciones", "allVariables", "variablesOld", "variables"});

        addSupressionList(Salida.class, new String[] {"id", "formulario"});
        addSupressionList(PropiedadSalida.class, new String[] {"id", "salida"});
        addSupressionList(PuntoSalida.class, new String[] {"salidas"});
    }

    public boolean suppressProperty(Class clazz, Class tipus, String nom) {
        // si es un traducible eliminam directament una serie de propietats.
        if (Traducible.class.isAssignableFrom(clazz)) {
            if (nom.equals("langs") || nom.equals("currentLang") || nom.equals("traduccion")) {
                return true;
            }
        }

        // si nó, miram les llistes.
        if (propiedadesInvalidas.containsKey(clazz)) {
            if (((Set) propiedadesInvalidas.get(clazz)).contains(nom)) {
                return true;
            }
        }

        return false;
    }
}
