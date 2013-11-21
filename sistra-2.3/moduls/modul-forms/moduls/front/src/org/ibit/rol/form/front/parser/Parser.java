package org.ibit.rol.form.front.parser;

import java.util.Map;

/**
 * Este interface define los m�todes para a partir procesar a�adir a
 * un array de bytes (presumiblemente un PDF, RTF ...) informaci�n sobre valors
 * de campos y un codigo de barras.
 */
public interface Parser {

    /**
     * Devuelve el tipo de fichero generado.
     */
    public String contentType();

    /**
     * Carga los datos del array de bytes.
     */
    public void load(byte[] data) throws ParserException;

    /**
     * Define sobre el objecto los valores para las variables definidas.
     */
    public void populate(Map map) throws ParserException;

    /**
     * A�ade informaci�n sobre un determinado tipo de codigo de barras.
     */
    public void addBarcode(String barcode, int x, int y) throws ParserException;

    /**
     * Retorna los datos del objecto debidamente modificado.
     */
    public byte[] write() throws ParserException;

}
