package org.ibit.rol.form.front.parser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.Barcode128;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;

/**
 * Trata formularios pdf con la libreria ITEXT
 */
public class ITextParser implements Parser {

    private PdfStamper stamp;
    private ByteArrayOutputStream baos;

    public String contentType() {
        return "application/pdf";
    }

    public void load(byte[] data) throws ParserException {
        try {
            PdfReader reader = new PdfReader(data);
            baos = new ByteArrayOutputStream();
            stamp = new PdfStamper(reader, baos);
        } catch (IOException e) {
            throw new ParserException("Could not load document", e);
        } catch (DocumentException e) {
            throw new ParserException("Could not create stamper", e);
        }
    }

    public void populate(Map map) throws ParserException {
        try {
            AcroFields fields = stamp.getAcroFields();
            Map fieldValues = fields.getFields();

            for (Iterator iterator = fieldValues.keySet().iterator(); iterator.hasNext();) {
                String key = (String) iterator.next();
                if (map.containsKey(key)) {
                    fields.setField(key, (String) map.get(key));
                } else {
                    fields.setField(key, "");
                }
            }
        } catch (IOException e) {
            throw new ParserException("Could not update fields", e);
        } catch (DocumentException e) {
            throw new ParserException("Could not update fields", e);
        }
    }

    public void addBarcode(String barcode, int x, int y) throws ParserException {
        try {
            Barcode128 code128 = new Barcode128();
            code128.setCode(barcode);

            PdfContentByte cb = stamp.getOverContent(1);
            Image image128 = Image.getInstance(code128.createImageWithBarcode(cb, null, null));

            cb.addImage(image128, image128.width(), 0, 0, image128.height(), x, y);
            cb.moveTo(0, 0);
        } catch (DocumentException e) {
            throw new ParserException("Could not add barcode", e);
        }
    }

    public byte[] write() throws ParserException {
        try {
            stamp.close();
            return baos.toByteArray();
        } catch (DocumentException e) {
            throw new ParserException("Could not close document", e);
        } catch (IOException e) {
            throw new ParserException("Could not close document", e);
        }
    }


}

