package org.ibit.rol.form.front.util;

import org.ibit.rol.form.model.InstanciaBean;
import org.ibit.rol.form.model.Anexo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.InputStream;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.beans.DefaultPersistenceDelegate;

/**
 * Mètodes d'utilitat per guardar/recuperar instancies en format zip.
 */
public class InstanciaZipCodec {

    protected static Log log = LogFactory.getLog(InstanciaZipCodec.class);

    private static final String FORM_DATA_FILE = "form-data.xml";
    private static final String ANEXO_SUBDIR = "anexo/";

    public static InstanciaBean decodeInstancia(InputStream inputStream) throws IOException {
        InstanciaBean bean = null;
        Map fileAnexos = new HashMap();

        ZipInputStream zipis = new ZipInputStream(inputStream);
        ZipEntry zipEntry = zipis.getNextEntry();
        while (zipEntry != null) {
            log.debug("Processant " + zipEntry.getName());
            if (zipEntry.getName().equals(FORM_DATA_FILE)) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                int n;
                while ((n = zipis.read()) > -1) baos.write(n);
                ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
                XMLDecoder decoder = new XMLDecoder(bais);
                bean = (InstanciaBean) decoder.readObject();
                decoder.close();
                zipis.closeEntry();
            } else if (zipEntry.getName().startsWith(ANEXO_SUBDIR)) {
                String name = zipEntry.getName().substring(ANEXO_SUBDIR.length());
                String contentType = zipEntry.getComment();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                int n;
                while ((n = zipis.read()) > -1) baos.write(n);
                log.debug("llegits " + baos.size() + " bytes");
                Anexo anexo = new Anexo(name, contentType, baos.toByteArray());
                fileAnexos.put(zipEntry.getName(), anexo);
                zipis.closeEntry();
            } else {
                log.warn("Entrada " + zipEntry.getName() + " descartada");
                zipis.closeEntry();
            }
            zipEntry = zipis.getNextEntry();
        }
        zipis.close();

        if (bean == null) {
            log.error("No s'ha trobat l'entrada " + FORM_DATA_FILE);
            throw new IOException(FORM_DATA_FILE + " no trobat");
        }

        // Actualizar anexos.
        Map anexos = bean.getAnexos();
        for (Iterator iterAnexo = anexos.keySet().iterator(); iterAnexo.hasNext();) {
            String key = (String) iterAnexo.next();
            String entryName = (String) anexos.get(key);
            anexos.put(key, fileAnexos.get(entryName));
        }
        return bean;
    }

    public static void encodeInstancia(InstanciaBean bean, OutputStream result) throws IOException {
        // Preparamos un zip
        ZipOutputStream zipos = new ZipOutputStream(result);
        zipos.setMethod(ZipOutputStream.DEFLATED);

        // Afegim els anexes.
        for (Iterator iterAnexo = bean.getAnexos().keySet().iterator(); iterAnexo.hasNext();) {
            String key = (String) iterAnexo.next();
            Anexo anexo = (Anexo) bean.getAnexos().get(key);
            String entryName = ANEXO_SUBDIR + anexo.getName();
            ZipEntry zipEntry = new ZipEntry(entryName);
            zipEntry.setTime(System.currentTimeMillis());
            zipEntry.setSize(anexo.getData().length);
            zipEntry.setComment(anexo.getContentType());
            zipos.putNextEntry(zipEntry);
            zipos.write(anexo.getData(), 0, anexo.getData().length);
            zipos.closeEntry();
            bean.getAnexos().put(key, entryName);
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        XMLEncoder encoder = new XMLEncoder(baos);
        encoder.setPersistenceDelegate(Locale.class,
                new DefaultPersistenceDelegate(new String[]{"language", "country", "variant"}));
        encoder.writeObject(bean);
        encoder.close();
        byte[] content = baos.toByteArray();

        ZipEntry zipEntry = new ZipEntry(FORM_DATA_FILE);
        zipEntry.setSize(baos.size());
        zipEntry.setTime(System.currentTimeMillis());
        zipos.putNextEntry(zipEntry);
        zipos.write(content, 0, content.length);
        zipos.closeEntry();
        zipos.close();
    }
}
