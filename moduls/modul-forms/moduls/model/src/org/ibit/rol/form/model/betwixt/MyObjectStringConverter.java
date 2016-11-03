package org.ibit.rol.form.model.betwixt;

import org.apache.commons.betwixt.expression.Context;
import org.apache.commons.betwixt.strategy.DefaultObjectStringConverter;


/**
 * Delega amb l'implemetnació per defecte excepte pel tipus array de bytes, que
 * empra un string base64 per escriure i llegir.
 */
public class MyObjectStringConverter extends DefaultObjectStringConverter {

    public String objectToString(Object object, Class type, String flavour, Context context) {
        if (object != null) {
            if (object instanceof byte[]) {
                return new String(Base64Coder.encode((byte[]) object));
            } else {
                // use Default implementation
                return super.objectToString(object, type, flavour, context);
            }
        }
        return "";
    }

    public Object stringToObject(String value, Class type, String flavour, Context context) {
        if (type.equals(byte[].class)) {
            return Base64Coder.decode(value.toCharArray());
        } else {
            // use default implementation
            return super.stringToObject(value, type, flavour, context);
        }
    }
}
