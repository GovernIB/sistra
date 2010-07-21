package org.ibit.rol.form.persistence.conector;

import java.util.Map;

/**
 * Interfaz que define los conectores que podran ser usados como puntos de salida
 * de un formulario.
 */
public interface Conector {

    /**
     * Indica si el conector soporta imagenes.
     * @return <code>true</code> si el conector soporta imagenes,
     * <code>false</code> en caso contrario.
     */
    boolean getSupportsImages();

    /**
     * Indica si el conector puede generar códigos de barras.
     * @return <code>true</code> si el conector puede generar codigos de barras,
     * <code>false</code> en caso contrario.
     */
    boolean getSupportsBarcode();

    /**
     * Ejecuta el conector con los valores especificados y devuelve un resultado. En caso de error
     * en el proceso lanzarà una {@link ConectorException}.
     * @param formValues Valores del formulario con los que ejecutar el conector.
     * @return resultado de la ejecución.
     * @throws ConectorException
     */
    Result exec(Map formValues) throws ConectorException;

}
