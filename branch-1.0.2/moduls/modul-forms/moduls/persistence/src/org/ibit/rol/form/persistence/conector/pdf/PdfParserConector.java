package org.ibit.rol.form.persistence.conector.pdf;

import org.ibit.rol.form.persistence.conector.FileParserConector;
import org.ibit.rol.form.persistence.conector.Result;
import org.ibit.rol.form.persistence.conector.ConectorException;
import org.ibit.rol.form.persistence.conector.FileResult;

import java.util.Map;
import java.util.Iterator;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.DocumentException;

/**
 * TODO documentar
 */
public class PdfParserConector extends FileParserConector {

    /**
     * Indica si el conector soporta imagenes.
     * @return <code>true</code> si el conector soporta imagenes,
     *         <code>false</code> en caso contrario.
     */
    public boolean getSupportsImages() {
        return false;
    }

    /**
     * Indica si el conector puede generar códigos de barras.
     *
     * @return <code>true</code> si el conector puede generar codigos de barras,
     *         <code>false</code> en caso contrario.
     */
    public boolean getSupportsBarcode() {
        return true;
    }

    /**
     * Ejecuta el conector con los valores especificados y devuelve un resultado. En caso de error
     * en el proceso lanzarà una {@link org.ibit.rol.form.persistence.conector.ConectorException}.
     *
     * @param formValues Valores del formulario con los que ejecutar el conector.
     * @return resultado de la ejecución.
     * @throws org.ibit.rol.form.persistence.conector.ConectorException
     *
     */
    public Result exec(Map formValues) throws ConectorException {
        try {
            if (plantilla == null) throw new ConectorException("'plantilla' es null");
            PdfReader reader = new PdfReader(plantilla.getDatos());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfStamper stamp = new PdfStamper(reader, baos);

            AcroFields fields = stamp.getAcroFields();
            Map fieldValues = fields.getFields();

            for (Iterator iterator = fieldValues.keySet().iterator(); iterator.hasNext();) {
                String key = (String) iterator.next();
                if (formValues.containsKey(key) && (formValues.get(key) instanceof String)) {                    
                    fields.setField(key, (String) formValues.get(key));
                } else {
                    fields.setField(key, "");
                }
            }

            stamp.close();

            final FileResult fileResult = new FileResult();
            fileResult.setContentType("application/pdf");
            fileResult.setBytes(baos.toByteArray());
            fileResult.setLength(baos.size());
            fileResult.setName(plantilla.getNombre());
            return fileResult;

        } catch (IOException e) {
            throw new ConectorException(e.getMessage());
        } catch (DocumentException e) {
            throw new ConectorException(e.getMessage());
        }
    }
}
