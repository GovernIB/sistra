package org.ibit.rol.form.persistence.conector;

import java.util.Map;

/**
 * TODO documentar
 */
public class TestConector implements Conector {

    /**
     * Indica si el conector soporta imagenes.
     *
     * @return <code>true</code> si el conector soporta imagenes,
     *         <code>false</code> en caso contrario.
     */
    public boolean getSupportsImages() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Indica si el conector puede generar códigos de barras.
     *
     * @return <code>true</code> si el conector puede generar codigos de barras,
     *         <code>false</code> en caso contrario.
     */
    public boolean getSupportsBarcode() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }


    private String test;

    public void setTest(String test) {
        this.test = test;
    }

    /**
     * Ejecuta el conector con los valores especificados y devuelve un resultado. En caso de error
     * en el proceso lanzarà una {@link ConectorException}.
     *
     * @param formValues Valores del formulario con los que ejecutar el conector.
     * @return resultado de la ejecución.
     * @throws ConectorException
     *
     */
    public Result exec(Map formValues) throws ConectorException {
        return new MessageResult("{0}", test); 
    }
}
