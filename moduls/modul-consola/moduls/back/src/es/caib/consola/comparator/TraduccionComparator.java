package es.caib.consola.comparator;

import java.lang.reflect.Method;
import java.util.Comparator;

import org.apache.commons.lang.StringUtils;

import es.caib.consola.model.Usuario;
import es.caib.sistra.model.Traduccion;


/**
 * Class TraduccionComparator.
 */
public class TraduccionComparator implements Comparator<Object> {

    /** Atributo ascendente de TraduccionComparator. */
    private final boolean ascendente;

    /** Atributo campo de TraduccionComparator. */
    private final String campo;
    
    /** Atributo usuario logado de TraduccionComparator. */
    private final Usuario usuarioLogado;

    /**
     * Instancia un nuevo traduccion comparator de TraduccionComparator.
     * 
     * @param pCampo
     *            Parámetro campo
     * @param pAscendente
     *            Parámetro ascendente
     * @param pUsuarioLogado
     *            Parámetro usuario logado
     */
    public TraduccionComparator(final String pCampo, final boolean pAscendente, final Usuario pUsuarioLogado) {
        campo = capitalizar(pCampo);
        ascendente = pAscendente;
        usuarioLogado = pUsuarioLogado;
    }

    /**
     * Método para Capitalizar de la clase TraduccionComparator.
     * 
     * @param pCampo
     *            Parámetro campo
     * @return el string
     */
    private String capitalizar(final String pCampo) {
        return StringUtils.capitalize(pCampo);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    public final int compare(final Object tra1, final Object tra2) {
        final String literal1 = obtenerTraduccion(tra1);
        final String literal2 = obtenerTraduccion(tra2);
        int ret;
        if (ascendente) {
            ret = literal1.compareTo(literal2);
        } else {
            ret = literal2.compareTo(literal1);
        }
        return ret;
    }

    /**
     * Método para Obtener traduccion de la clase TraduccionComparator.
     * 
     * @param tra
     *            Parámetro tra
     * @return el traduccion
     */
    private String obtenerTraduccion(final Object tra) {
        Class<? extends Object> clase = tra.getClass();
        Object[] args;
        Class<?>[] parameterTypes;
        Method metodo = null;
        Object invoke = null;
        String resultado = null; 
        try {
        	// Obtiene traducciones
        	parameterTypes = new Class<?>[1];
        	args = new Object[1];
        	args[0] = usuarioLogado.getIdioma();
        	parameterTypes[0] = usuarioLogado.getIdioma().getClass();
        	metodo = clase.getMethod("getTraduccion", parameterTypes);
            invoke = metodo.invoke(tra, args);
            Traduccion traduccion = (Traduccion) invoke;
            
            // Obtiene literal
            clase = traduccion.getClass();
            parameterTypes = new Class<?>[0];
            args = new Object[0];
            metodo = clase.getMethod("get" + campo, parameterTypes);
            invoke = metodo.invoke(traduccion, args);
            resultado = (String) invoke;
        } catch (Exception e) {
            // TODO CAPTURAR EXCEPCION!!
        	resultado = null;
        }
        return resultado;
       
    }

}

