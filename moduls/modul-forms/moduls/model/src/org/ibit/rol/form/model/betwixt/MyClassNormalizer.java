package org.ibit.rol.form.model.betwixt;

import org.apache.commons.betwixt.strategy.ClassNormalizer;

/**
 * Extensió de ClassNormalizer per evitar proxys CGLIB.
 */
public class MyClassNormalizer extends ClassNormalizer {

    public Class getNormalizedClass(Object o) {
        if (o == null) return null;
        return super.getNormalizedClass(o);
    }

    public Class normalize(Class clazz) {
        if (clazz == null) return null;
        String name = clazz.getName();
        int index = name.indexOf("$$EnhancerByCGLIB$$");
        if (index > -1) {
            String newName = name.substring(0, index);
            Class newClazz;
            try {
                newClazz = Class.forName(newName);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Invalid proxy " + name, e);
            }
            return newClazz;
        }
        return clazz;
    }
}
