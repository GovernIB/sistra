package es.caib.consola;

import java.lang.reflect.Field;

import javax.servlet.http.HttpSession;

import org.zkoss.xel.VariableResolver;
import org.zkoss.xel.XelException;
import org.zkoss.zk.ui.Executions;
/**
 * Class GTTVariableResolver.
 */
public class GTTVariableResolver implements VariableResolver {

    /*
     * (non-Javadoc)
     * 
     * @see org.zkoss.xel.VariableResolver#resolveVariable(java.lang.String)
     */
   // @Override
    public final Object resolveVariable(final String name) {
        Object value = null;

        // Acceso a constantes desde los zul (hay que cambiar el . por un _ en
        // el zul)
        if (name.startsWith(ConstantesWEB.class.getSimpleName())) {
            value = recuperarConstante(name, ConstantesWEB.class);
        } else if (name.startsWith(ConstantesMaxlength.class.getSimpleName())) {
            value = recuperarConstante(name, ConstantesMaxlength.class);
        } else if ("idSession".equals(name)) {
            final HttpSession session = (HttpSession) (Executions.getCurrent())
                    .getDesktop().getSession().getNativeSession();
            value = session.getId();
        }

        return value; // Valor no encontrado
    }

    /**
     * Método para Recuperar constante de la clase GTTVariableResolver.
     * 
     * @param name
     *            Parámetro name
     * @param pClass
     *            Parámetro class
     * @return el object
     */
    private Object recuperarConstante(final String name, final Class<?> pClass) {
        Object value = null;
        final int finClase = name.indexOf("_");
        final String constante = name
                .substring(finClase + 1);
        if (constante.length() > 0) {
            final Field[] fields = pClass.getFields();
            for (final Field field : fields) {
                if (field.getName().equals(constante)) {
                    try {
                        value = field.get(pClass.getClass());
                    } catch (final IllegalArgumentException e) {
                        throw new XelException(e);
                    } catch (final IllegalAccessException e) {
                        throw new XelException(e);
                    }
                }
            }
        }
        return value;
    }

}
