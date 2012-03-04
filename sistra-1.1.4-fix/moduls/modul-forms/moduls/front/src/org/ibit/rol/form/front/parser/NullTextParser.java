package org.ibit.rol.form.front.parser;

import java.util.Map;
import java.util.Iterator;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

/**
 * Parser que solo saca las propiedades en modo texto.
 */
public class NullTextParser implements Parser {

    private ByteArrayOutputStream baos;
    private PrintWriter writer;

    public String contentType() {
        return "text/plain";
    }

    public void load(byte[] data) throws ParserException {
        // Obviamos los datos pero preparamos para escribir.
        baos = new ByteArrayOutputStream();
        writer = new PrintWriter(baos);
    }

    public void populate(Map map) throws ParserException {
        for (Iterator iterator = map.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            Object value = map.get(key);
            writer.println("[" + key + "=" + value + "]");
        }
    }

    public void addBarcode(String barcode, int x, int y) throws ParserException {
        writer.println("BARCODE(" + x + "," + y + ")=[" + barcode + "]");
    }

    public byte[] write() throws ParserException {
        writer.close();
        return baos.toByteArray();
    }
}
