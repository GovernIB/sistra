package org.ibit.rol.form.persistence.conector.xml;

import org.apache.commons.codec.binary.Base64;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.ibit.rol.form.model.Anexo;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class XmlDom4j {

    private Element getParent(Document document, String xpath) {
        String[] s = xpath.split("/");
        Element element = document.getRootElement();
        if (element.getName().equals(s[1])) {
            for (int i = 2; i < s.length - 1; i++) {
                Element tmp = element.element(s[i]);
                if (tmp == null) {
                    element = element.addElement(s[1]);
                } else {
                    element = tmp;
                }
            }
        }
        return element;
    }

    public Map readFromMap(Document document, Map m) throws DocumentException {
        Map result = new HashMap();
        for (Iterator it = m.keySet().iterator(); it.hasNext();) {
            String key = (String) it.next();
            String xpath = (String) m.get(key);
            List list = document.selectNodes(xpath);
            Collection c = new Vector();
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                Element attribute = (Element) iter.next();

                c.add(attribute.getData());
            }
            if (c.size() > 0) {
                result.put(key, c);
            }
        }
        return result;
    }

    public void writeFromMap(Document document, Map m) {

        for (Iterator it = m.keySet().iterator(); it.hasNext();) {

            String xpath = (String) it.next();

            Collection data;
            if (m.get(xpath) instanceof Collection) {
                data = (Collection) m.get(xpath);
            } else {
                data = new ArrayList();
                data.add(m.get(xpath));
            }

            String[] s = xpath.split("/");
            String name = s[s.length - 1];

            List list = document.selectNodes(xpath);
            Element reference = null;
            Element parent;

            if (list.size() > 0) {
                parent = ((Element) list.get(0)).getParent();

                for (Iterator iter = list.iterator(); iter.hasNext();) {
                    Element attribute = (Element) iter.next();
                    if (iter.hasNext())
                        parent.remove(attribute);
                    else
                        reference = attribute;
                }
            } else {
                parent = getParent(document, xpath);
            }

            for (Iterator iter = data.iterator(); iter.hasNext();) {
                Object value = iter.next();
                String strValue;
                if (value instanceof byte[]) {
                    strValue = new String(Base64.encodeBase64((byte[])value));
                } else if (value instanceof Anexo) {
                    strValue = new String(Base64.encodeBase64(((Anexo)value).getData()));
                } else {
                    strValue = (String) value;
                }

                if (reference != null) {
                    reference.setText(strValue);
                    reference = null;
                } else {
                    parent.addElement(name).setText(strValue);
                }
            }
        }
    }

    public void createPrettyPrint(OutputStream out, Document document) throws IOException {
        OutputFormat of = OutputFormat.createPrettyPrint();
        of.setEncoding("UTF-8");
        XMLWriter writer = new XMLWriter(out, of);
        writer.write(document);
        writer.close();
    }
}