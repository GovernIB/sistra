package org.ibit.rol.form.persistence.conector.xml;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Collection;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.DocumentException;

import org.ibit.rol.form.persistence.conector.xml.XmlDom4j;
import org.ibit.rol.form.persistence.conector.Conector;
import org.ibit.rol.form.persistence.conector.ConectorException;
import org.ibit.rol.form.persistence.conector.FileResult;
import org.ibit.rol.form.persistence.conector.Result;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class XmlConector implements Conector {

    private static Log log = LogFactory.getLog(XmlConector.class);

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
     * Indica si el conector puede generar c�digos de barras.
     *
     * @return <code>true</code> si el conector puede generar codigos de barras,
     *         <code>false</code> en caso contrario.
     */
    public boolean getSupportsBarcode() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    private String xml;

    public void setXml(String xml) {
        this.xml = xml;
    }

    private void logMap(Map m){
        if(m!=null){
            Set s = m.keySet();
            Iterator it = s.iterator();
            while(it.hasNext()){
                String id = (String)it.next();
                if (m.get(id)  instanceof Collection){
                    Iterator ite = ((Collection)m.get(id)).iterator();
                    while(ite.hasNext()){
                        String value = ((String)ite.next());
                        log.debug("-> "+ id + " = " + value);
                    }
                }else if (m.get(id).getClass()==(String.class)){
                    String value = (String)m.get(id);
                    log.debug("-> "+ id + " = " + value);
                }
            }
        }
    }

    public Result exec(Map formValues) throws ConectorException {

        logMap(formValues);

        XmlDom4j xm = new XmlDom4j();

        try{
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            Document document = DocumentHelper.parseText(xml);
            xm.writeFromMap(document,formValues);

            xm.createPrettyPrint(out,document);

            final FileResult fileResult = new FileResult();
            fileResult.setContentType("text/xml");
            fileResult.setBytes(out.toByteArray());
            fileResult.setLength(out.size());
            fileResult.setName("document.xml");
            return fileResult;
        }catch (IOException e) {
            throw new ConectorException(e.getMessage());
        }catch (DocumentException e) {
            throw new ConectorException(e.getMessage());
        }
    }



}
