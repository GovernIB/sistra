package es.caib.zonaper.persistence.util;

/**
 * Métodos de utilidad.
 */
public class ClassUtils {

    public static Object getInstance(Class clazz) {
        try {
            Object o = clazz.newInstance();
            return o;
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
