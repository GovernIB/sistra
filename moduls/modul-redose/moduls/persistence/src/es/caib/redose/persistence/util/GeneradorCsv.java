package es.caib.redose.persistence.util;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

/**
 * Generador de CSV.
 * 
 * @author Indra
 * 
 */
public final class GeneradorCsv {

    /**
     * Numero 3.
     */
    private static final int N3 = 3;

    /**
     * Numero 5.
     */
    private static final int N5 = 5;

    /**
     * Numero 8.
     */
    private static final int N8 = 8;

    /**
     * Numero 16.
     */
    private static final int N16 = 16;

    /**
     * Numero 24.
     */
    private static final int N24 = 24;

    /**
     * Numero 1000.
     */
    private static final int N1000 = 1000;

    /**
     * Numero 4000.
     */
    private static final int N4000 = 4000;

    /**
     * Numero 99999999.
     */
    private static final int N99999999 = 99999999;

    /**
     * Constructor.
     */
    private GeneradorCsv() {
        super();
    }

    /**
     * Tabla de transformacion.
     */
    private static String[] letras;

    /**
     * Genera id.
     * 
     * @return Id
     */
    public static String generarId() {

        final StringBuffer s = new StringBuffer();

        // Digitos 1-15 (15): anyo-mseg-mes-seg-dia-min-hora
        final SimpleDateFormat sdf = new SimpleDateFormat("yySSSMMssddmmHH");
        s.append(sdf.format(new Date()));

        // Digitos 16-36 (21): Genera 3 Randoms de 8 y quitamos un caracter
        final SecureRandom sr = new SecureRandom();
        String rn1 = Long.toString(sr.nextInt(N99999999));
        rn1 = StringUtils.leftPad(rn1, N8, "0");
        String rn2 = Long.toString(sr.nextInt(N99999999));
        rn2 = StringUtils.leftPad(rn1, N8, "0");
        String rn3 = Long.toString(sr.nextInt(N99999999));
        rn3 = StringUtils.leftPad(rn3, N8, "0");
        s.append(rn1).append(rn2).append(rn3.substring(0, N5));

        // Convertimos a tabla de caracteres (pasamos de 36 caracteres a 24)
        final String gen = s.toString();

        final StringBuffer s2 = new StringBuffer();
        String rp;
        for (int i = 0; i < gen.length(); i = i + N3) {
            rp = gen.substring(i, i + N3);
            s2.append(letras[Integer.parseInt(rp)]);
        }
        String id = s2.toString();
        id = id.substring(0, N8) + "-" + id.substring(N8, N16) + "-"
                + id.substring(N16, N24);
        return id;
    }

    /**
     * Genera tabla de transformacion.
     * 
     * @return tabla de transformacion (lista de cadenas separadas por -)
     */
    public static String generarTablaTransformacion() {
        final String[] alfabeto = { "A", "B", "C", "D", "E", "F", "G", "H",
                "I", "J", "K", "L", "M", "N", "P", "Q", "R", "S", "T", "U",
                "V", "Y", "X", "Z", "1", "2", "3", "4", "5", "6", "7", "8", "9" };

        final Map<String, String> cc = new HashMap<String, String>();
        for (final String letra : alfabeto) {
            for (final String letra2 : alfabeto) {
                cc.put(letra + letra2, letra + letra2);
            }
        }
        final Set<String> keyset = cc.keySet();
        final List<String> elementos = new ArrayList<String>();
        elementos.addAll(keyset);

        final SecureRandom sr = new SecureRandom();
        final List<String> tabla = new ArrayList<String>();
        while (tabla.size() < N1000) {
            final int ind = sr.nextInt(elementos.size());
            tabla.add(elementos.get(ind));
            elementos.remove(ind);
        }

        final StringBuffer sb = new StringBuffer(N4000);
        boolean primer = true;
        for (final String s : tabla) {
            if (!primer) {
                sb.append("-");
            }
            primer = false;
            sb.append(s);
        }

        final String res = sb.toString();
        return res;
    }

    /**
     * Establece tabla de transformacion.
     * 
     * @param tabla
     *            tabla de transformacion (lista de cadenas separadas por -)
     */
    public static void establecerTablaTransformacion(final String tabla) {
    	letras = tabla.split("-");
    }

    /**
     * Devuelve si ya esta inicializada latabla de transformacion.
     * 
     * @return true o false segun este inicializada o no la tabla
     */
    public static boolean existeTablaTransformacion() {
    	boolean existe = false;
    	existe = (letras == null) ? false : true;		
        return existe;
    }
}
