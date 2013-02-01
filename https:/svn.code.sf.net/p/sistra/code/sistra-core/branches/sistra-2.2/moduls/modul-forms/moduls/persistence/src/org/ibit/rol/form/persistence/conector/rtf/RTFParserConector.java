package org.ibit.rol.form.persistence.conector.rtf;

import org.ibit.rol.form.persistence.conector.FileParserConector;
import org.ibit.rol.form.persistence.conector.Result;
import org.ibit.rol.form.persistence.conector.ConectorException;
import org.ibit.rol.form.persistence.conector.FileResult;
import org.ibit.rol.form.model.Anexo;
import org.apache.commons.lang.StringUtils;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import java.io.IOException;

/**
 * TODO documentar
 */
public class RTFParserConector extends FileParserConector {

    /**
     * Indica si el conector soporta imagenes.
     * @return <code>true</code> si el conector soporta imagenes,
     *         <code>false</code> en caso contrario.
     */
    public boolean getSupportsImages() {
        return true;
    }

    /**
     * Indica si el conector puede generar códigos de barras.
     * @return <code>true</code> si el conector puede generar codigos de barras,
     *         <code>false</code> en caso contrario.
     */
    public boolean getSupportsBarcode() {
        return false;
    }

    /**
     * Ejecuta el conector con los valores especificados y devuelve un {@link org.ibit.rol.form.persistence.conector.FileResult}. En caso de error
     * en el proceso lanzarà una {@link org.ibit.rol.form.persistence.conector.ConectorException}.
     *
     * @param formValues Valores del formulario con los que ejecutar el conector.
     * @return resultado de la ejecución, en este caso un {@link org.ibit.rol.form.persistence.conector.FileResult}
     * @throws org.ibit.rol.form.persistence.conector.ConectorException
     *
     */
    public Result exec(Map formValues) throws ConectorException {
        try {
            if (plantilla == null) throw new ConectorException("'plantilla' es null");
            byte[] rtfBytes = RTFUtil.removeNonshppict(plantilla.getDatos());
            List images = RTFUtil.imagesRTF(rtfBytes);
            rtfBytes = RTFUtil.rtfImageTags(rtfBytes);

            for (Iterator iterator = formValues.keySet().iterator(); iterator.hasNext();) {
                String key = (String) iterator.next();
                Object value = formValues.get(key);
                if (StringUtils.isNotEmpty(key)) {
                    if (value instanceof byte[]) {
                        setImage(images, Integer.parseInt(key), RTFUtil.toHexadecimal64((byte[]) value));
                    } else if (value instanceof Anexo) {
                        byte[] data = ((Anexo) value).getData();
                        setImage(images, Integer.parseInt(key), RTFUtil.toHexadecimal64(data));
                    } else {
                        String textValue = (String) value;
                        rtfBytes = RTFUtil.replace(rtfBytes, key, textValue);
                    }
                }
            }
            rtfBytes = RTFUtil.rtfTagsImatge(rtfBytes, images);

            final FileResult fileResult = new FileResult();
            fileResult.setContentType("text/rtf");
            fileResult.setBytes(rtfBytes);
            fileResult.setLength(rtfBytes.length);
            fileResult.setName(plantilla.getNombre());
            return fileResult;
        } catch (IOException e) {
            throw new ConectorException(e.getMessage());
        }
    }

    private void setImage(List images, int index, String imageHex) {
        String antiga = (String) images.get(index);
        int indexBlip = antiga.indexOf("\\bliptag");

        char[] image = antiga.substring(indexBlip + 1).toCharArray();
        int end = 0;
        int count = -1;
        while (count != 0) {
            if (image[end] == '\n') {
                count = 0;
            }
            if (image[end] == '{') {
                if (count == -1) {
                    count = 0;
                }
                count ++;
            }
            if (image[end] == '}') {
                count --;
            }
            end++;
        }
        end++;
        int indexInici = indexBlip + end;
        int indexFinal = antiga.indexOf('}', indexInici);
        String nova = antiga.substring(0, indexInici) + imageHex + antiga.substring(indexFinal);
        images.set(index, nova);
    }

}
