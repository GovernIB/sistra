package org.ibit.rol.form.front.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.util.List;
import java.util.ArrayList;

/**
 * Funciones de ayuda para el manejo de RTFS
 */
public class RTFUtil {

    private static Log log = LogFactory.getLog(RTFUtil.class);

    /**
     * Convierte datos hexadecimales en base 64
     *
     * @param data Datos en hexadecimal
     * @return Un String con los datos en base 64
     */
    public static String toHexadecimal64(byte[] data) {
        String resultat = "";
        ByteArrayInputStream input = new ByteArrayInputStream(data);
        String cadAux;
        int llegit = input.read();
        int base64 = 0;
        while (llegit != -1) {
            if (base64 % 64 == 0) {
                resultat += "\n";
            }
            cadAux = Integer.toHexString(llegit);
            if (cadAux.length() < 2) //Hay que añadir un 0
                resultat += "0";
            resultat += cadAux;
            llegit = input.read();
            base64 += 1;
        }
        return resultat;
    }

    /**
     * Elimina la duplicidad de imagenes que el MS Word inserta en
     * los documentos RTF.
     * MS Word inserta después de cada imagen un duplicado de la misma en otro formato
     * que empieza con la marca <code>nonshppict</code>. Este método procesa un documento o fragmento RTF
     * eliminando estos duplicados.
     *
     * @param data contenido (o fragmento) de un documento RTF
     * @return contenido del RTF sin los duplicados de imagenes <code>nonshppict</code>.
     */
    public static byte[] removeNonshppict(byte[] data) {

        ByteArrayOutputStream out = new ByteArrayOutputStream(data.length);
        try {
            out.write(data);
            String document = out.toString();

            boolean more = true;
            boolean nonshppict = true;
            String docTemp = document;
            String newDocument = "";
            int end = 0;
            while (more) {
                docTemp = docTemp.substring(end);
                int init = docTemp.indexOf("{\\nonshppict");
                if (init == -1) {
                    more = false;
                    if (end != 0) {
                        newDocument = newDocument.concat(docTemp.substring(0, docTemp.length()));
                    } else {
                        /* El documento no contiene ningun nonshppict */
                        nonshppict = false;
                    }
                } else {
                    end = docTemp.indexOf("}}", init);
                    end += 2;
                    newDocument = newDocument.concat(docTemp.substring(0, init));
                }
            }

            /* Retornamos el nuevo contenido del fichero */
            if (nonshppict) {
                return newDocument.getBytes();
            } else {
                log.debug("No hay ninguna imagen duplicada");
                return data;
            }

        } catch (IOException e) {
            log.error(e);
            return null;
        }
    }

    /**
     * Extrae las imagenes de un documento RTF
     *
     * @param data El documento RTF en array de bytes
     * @return Una lista con las imagenes que contiene el documento
     */
    public static List imagesRTF(byte[] data) {

        ByteArrayOutputStream out = new ByteArrayOutputStream(data.length);
        try {
            out.write(data);
            String document = out.toString();
            boolean more = true;
            List images = new ArrayList();
            int end = 0;
            String docTemp = document;
            while (more) {
                docTemp = docTemp.substring(end);
                int init = docTemp.indexOf("{\\*\\shppict{");
                if (init == -1) { // No hay más imagenes
                    more = false;
                } else {
                    char[] image = docTemp.substring(init + 1).toCharArray();
                    end = 0;
                    int count = 1;
                    while (count != 0) {
                        if (image[end] == '{') {
                            count ++;
                        }
                        if (image[end] == '}') {
                            count --;
                        }
                        end++;
                    }
                    end++;
                    end += init;
                    images.add(docTemp.substring(init, end));
                }
            }

            return images;
        } catch (IOException e) {
            log.error("Error extrayendo las imagenes", e);
            return null;
        }
    }


    /**
     * Sustituye un texto especificado en un documento RTF
     * Restricción: Las imagenes no pueden contener texto en la misma linea
     *
     * @param data Datos del archivo RTF en el cual hay que sustituir las imagenes por tags
     */
    public static byte[] rtfImageTags(byte[] data) {

        ByteArrayOutputStream out = new ByteArrayOutputStream(data.length);
        try {
            out.write(data);
            String document = out.toString();

            int nImage = 0;
            int end = 0;
            boolean more = true, shppictPresent = true;
            String docTemp = document;
            String newDocument = "";
            while (more) {
                docTemp = docTemp.substring(end);
                int init = docTemp.indexOf("{\\*\\shppict{");
                if (init == -1) { // No hay más imagenes
                    more = false;
                    if (end != 0) {
                        newDocument = newDocument.concat(docTemp.substring(0, docTemp.length()));
                    } else {
                        /* El documento no contiene ninguna imagen */
                        shppictPresent = false;
                    }
                } else {
                    char[] image = docTemp.substring(init + 1).toCharArray();
                    end = 0;
                    int count = 1;
                    while (count != 0) {
                        if (image[end] == '{') {
                            count ++;
                        }
                        if (image[end] == '}') {
                            count --;
                        }
                        end++;
                    }
                    end++;
                    end += init;

                    newDocument = newDocument.concat(docTemp.substring(0, init));
                    newDocument = newDocument.concat("<image" + nImage + ">");
                }
                nImage++;
            }

            if (shppictPresent) {
                return newDocument.getBytes();
            } else {
                log.error("El documento no contiene ninguna imagen");
                return data;
            }

        } catch (Exception e) {
            log.error("Error insertando los tags", e);
            return null;
        }
    }


    /**
     * Sustituye un texto especificado en un documento RTF
     * Restricción: Las imagenes no pueden contener texto en la misma linea
     *
     * @param data Datos del archivo RTF en el cual hay que sustituir las imagenes por tags
     */
    public static byte[] rtfTagsImatge(byte[] data, List images) {

        ByteArrayOutputStream out = new ByteArrayOutputStream(data.length);
        try {
            out.write(data);
            String document = out.toString();

            int nImage = 0;
            int end = 0;
            boolean more = true, tagImagesPresent = true;
            String docTemp = document;
            String newDocument = "";
            while (more) {
                docTemp = docTemp.substring(end);
                String image = "<image" + nImage + ">";
                int init = docTemp.indexOf(image);
                if (init == -1) { // No hay más imagenes
                    more = false;
                    if (end != 0) {
                        newDocument = newDocument.concat(docTemp.substring(0, docTemp.length()));
                    } else {
                        /* El documento no contiene ningun tag imagen */
                        tagImagesPresent = false;
                    }
                } else {
                    end = init + image.length();

                    newDocument = newDocument.concat(docTemp.substring(0, init));
                    newDocument = newDocument.concat((String) images.get(nImage));
                }
                nImage++;
            }

            if (tagImagesPresent) {
                return newDocument.getBytes();
            } else {
                log.debug("El documento no contiene ningun tag imagen");
                return data;
            }

        } catch (Exception e) {
            log.error("Error insertando las imagenes", e);
            return null;
        }
    }


    /**
     * Sustituye un texto de un documento RTF
     *
     * @param data       Los datos del documento RTF
     * @param textInput  El texto a sustituir
     * @param textOutput El texto sustituto
     * @return datos modificados del documento RTF.
     */
    public static byte[] replace(byte[] data, String textInput, String textOutput) {

        ByteArrayOutputStream out = new ByteArrayOutputStream(data.length);
        try {
            out.write(data);
            String document = out.toString();
            String newDocument = StringUtils.replace(document, escapeRTF(textInput), escapeRTF(textOutput));
            return newDocument.getBytes();
        } catch (IOException e) {
            log.error("Error sustituyendo el texto", e);
            return null;
        }
    }

    /**
     * Converteix un text a RTF segons l'apartat <em>Escaped Expressions</em> de l'especificació
     * de RTF 1.7. A més substitueix els bots de línea (LF, CR-LF, CR) per el paràgraf de RTF: \par
     * @param input Text original
     * @return text convertit.
     */
    private static String escapeRTF(String input) {
        char[] inputArray = input.toCharArray();
        StringBuffer output = new StringBuffer();
        for (int i = 0; i < inputArray.length; i++) {
            int b = (int) inputArray[i];
            if  ( (b >= 0x00 && b < 0x20) )  {
                output.append('\\').append('\'');
                if (b < 0x10) output.append('0');
                output.append(Integer.toHexString(b));
            } else if  ( (b >= 0x80 && b <= 0xFF) )  {
                output.append('\\').append('\'').append(Integer.toHexString(b));
            } else if ( b == '\\' || b == '{' || b == '}') {
                output.append('\\').append((char)b);
            } else if ( b == '\r') {
                // Si es el darrer caràcter o el pròxim no és un LF, és a dir
                // es tracta d'un CF totsol, és un bot en format mac, afegim \par.
                // TODO - Un bot de línea és un "\line", un bot de paràgraf és un "\par"
                // TODO - hauriem d'emprar "\line" en lloc de "\par"?
                final int j = i + 1;
                if (j >= inputArray.length || inputArray[j] != '\n') {
                    output.append("\\par ");
                }
            } else if ( b == '\n') {
                output.append("\\par ");
            } else {
                output.append((char)b);
            }
        }
        return output.toString();
    }
}
